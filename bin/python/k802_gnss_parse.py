#!/usr/bin/env python3
# Copyright @2024 Pony AI Inc. All rights reserved.
# Authors: kunhe@pony.ai (Kun He)

import copy
import struct


_SOCKET_TIMEOUT_SECONDS = 2
_SYNC1 = 170
_SYNC2 = 68
_SYNC3 = 18
_CRC_LEN = 4
_PACKET_SIZE = 40960
_TEST_TIMEOUT = 30
_TOTAL_PACKET_NUM = 1000
_GPS_QUAL_NUM = 6
_RTK_FIXED_SOLUTION = '4'  # RTK fixed solution
_GPGGA_START = bytes([0x24, 0x47, 0x50, 0x47, 0x47, 0x41])  # $GPGGA
_ASCII_MSG_START = bytes([0x23, 0x24])  # '#' or '$'
_ASCII_MSG_END = bytes([0x0D, 0x0A])  # CR LF
_FORWARD_GPGGA_CONFIG = 'log com1 gpgga ontime 1'


class K802MsgHeader:
    def __init__(
        self,
        sync1,
        sync2,
        sync3,
        header_len,
        msg_id,
        msg_type,
        port_addr,
        msg_len,
        sequence,
        idle_time,
        time_status,
        week,
        milli_seconds,
        receiver_status,
        reserved,
        sw_version,
    ):
        self.sync1 = sync1
        self.sync2 = sync2
        self.sync3 = sync3
        self.header_len = header_len
        self.msg_id = msg_id
        self.msg_type = msg_type
        self.port_addr = port_addr
        self.msg_len = msg_len
        self.sequence = sequence
        self.idle_time = idle_time
        self.time_status = time_status
        self.week = week
        self.milli_seconds = milli_seconds
        self.receiver_status = receiver_status
        self.reserved = reserved
        self.sw_version = sw_version

    def __str__(self):
        return (
            f'sync1: {self.sync1}, sync2: {self.sync2}, sync3: {self.sync3}, '
            f'header_len: {self.header_len}, msg_id: {self.msg_id}, msg_type: {self.msg_type}, '
            f'port_addr: {self.port_addr}, msg_len: {self.msg_len}, sequence: {self.sequence}, '
            f'idle_time: {self.idle_time}, time_status: {self.time_status}, week: {self.week}, '
            f'milli_seconds: {self.milli_seconds}, receiver_status: {self.receiver_status}, sw_version: {self.sw_version}'
        )


class K802GnssValidationTest(HwIntegrationTestBase):
    def __init__(self, *args):
        super().__init__(*args)

    def setup_class(self):
        self.sub_system_configs = self._config.get_distributed_system_configs()
        self.main_system_config = self._config.get_main_system_config()
        self.enabled_modules = list(self._config.enable_modules_config.module)
        self.gnss_device: list = []
        sinognss_config = self._config.sinognss_config
        if not sinognss_config:
            return
        for device_config in sinognss_config.device_config:
            gnss_ip = (
                device_config.tcp_provider.device_ip
                if (device_config and device_config.HasField('tcp_provider'))
                else ''
            )
            gnss_port = (
                device_config.tcp_provider.device_port
                if (device_config and device_config.HasField('tcp_provider'))
                else 0
            )
            if gnss_ip and gnss_port:
                self.gnss_device.append({'gnss_ip': gnss_ip, 'gnss_port': gnss_port})

    def get_description(self):
        return 'The k802 gnss validation tests'

    def _config_gnss(self, gnss_device, original_device_config):
        """Check the GNSS device by network."""
        gnss_config = copy.deepcopy(original_device_config)
        gnss_config.hardware_config.append(_FORWARD_GPGGA_CONFIG)
        for hardware_config in gnss_config.hardware_config:
            is_success, result = gnss_device.set_gnss_config(hardware_config)
            if not is_success:
                error_info = f'Failed to set {gnss_config.device_name} config: {hardware_config}, result: {result}.'
                self._logger.error(error_info)
                return error_info
            self._logger.info(
                f'Successfully set {gnss_config.device_name} config: {hardware_config}.'
            )
        if not gnss_config.device_info.is_primary:
            error_info = 'This is not a primary gnss device, skip ntrip config.'
            self._logger.info(error_info)
            return error_info
        is_success, result = gnss_device.set_ntrip_config(
            self._config.ntrip_config.SerializeToString()
        )
        if not is_success:
            error_info = f'Failed to set ntrip config for {self._config.ntrip_config.gnss_device_info.device_name}, result: {result}.'
            self._logger.info(error_info)
            return error_info
        self._logger.info('Successfully set gnss config.')
        return None

    def _check_gnss_config_by_network(self):
        for device_config in self._config.sinognss_config.device_config:
            if (
                not device_config.HasField('is_config_via_hwprotocol')
                or not device_config.is_config_via_hwprotocol
            ):
                continue
            if not device_config.HasField('tcp_provider'):
                error_info = 'tcp_provider is not set in gnss_config'
                self._logger.error(error_info)
                return error_info
            # activate gpgga forwarding by following config
            gnss_ip = device_config.tcp_provider.device_ip
            gnss_port = device_config.tcp_provider.device_port
            gnss_device_id = device_config.device_info.gnss_device_id
            mcu_interface = TcpClientMcuInterface(gnss_ip, gnss_port, _SOCKET_TIMEOUT_SECONDS)
            gnss_device = GnssDevice(DeviceId.DEVICE_ID_GNSS_ONBOARD, gnss_device_id, mcu_interface)
            error_info = self._config_gnss(gnss_device, device_config)
            gnss_device.force_stop()
            if error_info:
                return error_info
        return None

    def _unpack_packet(self, data: bytes) -> None:
        format_str = '<4B1H1c1B2H2B1H2I2H'
        header_size = struct.calcsize(format_str)
        self.processed_packets = 0
        self.crc_fail_packets = 0
        self.dropped_bytes = 0
        self._logger.info(f'header_size: {header_size}')
        while len(data) >= header_size:
            header_data = data[:header_size]
            unpacked_data = struct.unpack(format_str, header_data)
            k802_header = K802MsgHeader(*unpacked_data)
            if (
                k802_header.sync1 != _SYNC1
                or k802_header.sync2 != _SYNC2
                or k802_header.sync3 != _SYNC3
            ):
                # if find an ascii terminator, consider it is an ascii packet
                end_index = data.find(_ASCII_MSG_END)
                if end_index != -1 and data[0] in _ASCII_MSG_START:
                    self._logger.info(f'skip a ascii message, length:{end_index} bytes')
                    data = data[end_index + len(_ASCII_MSG_END) :]
                    continue
                data = data[1:]
                self.dropped_bytes += 1
                continue
            packet_size = k802_header.header_len + k802_header.msg_len + _CRC_LEN
            if len(data) < packet_size:
                break
            compute_crc = _novatel_binary_message_crc32(data[0 : packet_size - _CRC_LEN])
            packet_chksum = struct.unpack('<I', data[packet_size - _CRC_LEN : packet_size])
            if compute_crc != packet_chksum[0]:
                self.crc_fail_packets += 1
                data = data[1:]
                self._logger.error(
                    f'crc mismatch! compute crc: {compute_crc} packet crc: {packet_chksum[0]} \n {k802_header}'
                )
            else:
                data = data[packet_size:]
                self.processed_packets += 1

    def _receive_message(self) -> bytearray:
        buffer = bytearray()
        while len(buffer) < _PACKET_SIZE:
            recv_data = self.tcp_interface.read()
            if not recv_data:
                return None
            buffer.extend(recv_data)
        return buffer

    def _verify_k802_packet(self):
        for device_info in self.gnss_device:
            self.tcp_interface = McuTcpInterface(
                device_info['gnss_ip'], device_info['gnss_port'], _SOCKET_TIMEOUT_SECONDS
            )
            try:
                self.tcp_interface.connect()
            except OSError as e:
                self._logger.error(
                    f'Failed to connect to k802-gnss {device_info["gnss_ip"]}:{device_info["gnss_port"]}: {e}'
                )
                return TestResult.FAIL, 'Failed to connect to k802-gnss'

            self._logger.info(
                f'vehicle id: {self._config.vehicle_id()} start to test k802-gnss: {device_info["gnss_ip"]}:{device_info["gnss_port"]}'
            )
            recv_packet = self._receive_message()
            if not recv_packet:
                return TestResult.FAIL, 'fail to receive k802 gnss packets'
            self._unpack_packet(recv_packet)

            self._logger.info(
                f'total packets: {self.processed_packets}, crc check fail: {self.crc_fail_packets}, drop bytes: {self.dropped_bytes}'
            )

            if self.crc_fail_packets != 0:
                return TestResult.FAIL, 'exist crc check fail packets'
        return TestResult.PASS, ''

    def _check_gga_packets(self):
        gga_packet_received = False
        more_packet_flag = False
        packet_data = b''
        for num in range(_TOTAL_PACKET_NUM):
            cal_checksum = 0
            checksum_index = 0
            try:
                recv_data = self.tcp_interface.read()
            except Exception as e:
                error_info = f'An error occurred: {e}'
                self._logger.error(error_info)
                continue
            try:
                # find position of ascii part
                start_index = recv_data.find(_GPGGA_START)
                if start_index != -1:
                    packet_data = recv_data
                elif start_index == -1 and more_packet_flag:
                    packet_data += recv_data
                else:
                    continue
                start_index = packet_data.find(_GPGGA_START)
                end_index = packet_data.find(_ASCII_MSG_END, start_index)
                # may be split to 2 packets, more_packet_flag is used to connect 2 message
                if end_index == -1:
                    more_packet_flag = ~more_packet_flag
                    continue
                more_packet_flag = False
                msg = packet_data[start_index:end_index].decode('ascii')
                self._logger.info(msg)
                msg_split = msg.split(',')
                # checksum check
                for index in range(1, len(msg)):
                    if msg[index] == '*':
                        checksum_index = index
                        break
                    cal_checksum ^= ord(msg[index])
                if msg[checksum_index + 1 : checksum_index + 3].lower() != hex(cal_checksum)[2:]:
                    error_info = f'checksum fail, calculated:{hex(cal_checksum)[2:]}, message:{msg[checksum_index + 1:checksum_index + 3]}'
                    self._logger.error(error_info)
                    return TestResult.FAIL, error_info
                # RTK status check
                if msg_split[_GPS_QUAL_NUM] != _RTK_FIXED_SOLUTION:
                    error_info = f'RTK check fail, GPS qual:{msg_split[_GPS_QUAL_NUM]}, expect :{_RTK_FIXED_SOLUTION}'
                    self._logger.error(error_info)
                    return TestResult.FAIL, error_info
                gga_packet_received = True
            except Exception as e:
                packet_data = b''
                self._logger.error(f'an error occurred in {num} packet:{e}')
                self._logger.error(recv_data.hex())
                continue
        # receive check
        if gga_packet_received:
            return TestResult.PASS, ''
        return TestResult.FAIL, 'None GGA packets received.'

    def _verify_rtk_status(self):
        # set gnss config
        error_info = self._check_gnss_config_by_network()
        if error_info:
            return TestResult.FAIL, error_info
        for device_info in self.gnss_device:
            self.tcp_interface = McuTcpInterface(
                device_info['gnss_ip'], device_info['gnss_port'], _SOCKET_TIMEOUT_SECONDS
            )
            try:
                self.tcp_interface.connect()
            except OSError as e:
                self._logger.error(
                    f'Failed to connect to k802-gnss {device_info["gnss_ip"]}:{device_info["gnss_port"]}: {e}'
                )
                return TestResult.FAIL, 'Failed to connect to k802-gnss'

            self._logger.info(
                f'vehicle id: {self._config.vehicle_id()} start to test k802-gnss: {device_info["gnss_ip"]}:{device_info["gnss_port"]}'
            )
            result = self._check_gga_packets()
            if result[0] == TestResult.FAIL:
                return result[
                    0
                ], f'device ip:{device_info["gnss_ip"]}:{device_info["gnss_port"]}, ' + result[1]
        return TestResult.PASS, ''

    def test_k802_packet(self):
        if not self.gnss_device:
            return TestResult.SKIPPED, 'skipped due to k802 gnss not enabled.'

        self._logger.info(f'vehicle id: {self._config.vehicle_id()}')
        if any(s.endswith('SinognssDriver') for s in self.enabled_modules):
            return self._verify_k802_packet()

        if self._config.vehicle_id() != self.main_system_config.vehicle_id:
            return TestResult.SKIPPED, ''

        for config in self.sub_system_configs:
            self._logger.info(f'config.ip_addr: {config.ip_addr}')
            cli_config = self._config.cli_config
            client = TestClient(self._logger, config.ip_addr, cli_config.server_mode_port)
            # step1: make sure there is hw_integration_test_package and works in server mode in subsystem
            response = self.ping_subsystem_server(client)
            if response[0] == TestResult.FAIL:
                return response
            # step2: send test whitelist to server
            test_result, test_info = self.handle_request(
                'K802GnssValidationTest:test_k802_packet', client, _TEST_TIMEOUT
            )
            if test_result == TestResult.FAIL:
                return TestResult.FAIL, f'{test_info}'

        return TestResult.PASS, ''

    def test_rtk_status(self):
        if not self.gnss_device:
            return TestResult.SKIPPED, 'skipped due to k802 gnss not enabled.'
        self._logger.info(f'vehicle id: {self._config.vehicle_id()}')

        if any(s.endswith('SinognssDriver') for s in self.enabled_modules):
            return self._verify_rtk_status()

        if self._config.vehicle_id() != self.main_system_config.vehicle_id:
            return TestResult.SKIPPED, ''

        for config in self.sub_system_configs:
            self._logger.info(f'config.ip_addr: {config.ip_addr}')
            cli_config = self._config.cli_config
            client = TestClient(self._logger, config.ip_addr, cli_config.server_mode_port)
            # step1: make sure there is hw_integration_test_package and works in server mode in subsystem
            response = self.ping_subsystem_server(client)
            if response[0] == TestResult.FAIL:
                return response
            # step2: send test whitelist to server
            test_result, test_info = self.handle_request(
                'K802GnssValidationTest:test_rtk_status', client, _TEST_TIMEOUT
            )
            if test_result == TestResult.FAIL:
                return TestResult.FAIL, f'{test_info}'

        return TestResult.PASS, ''