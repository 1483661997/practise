package scheme.ObserverPattern.DrawHouseServiceObserver;

import scheme.ObserverPattern.DrawHouseService.LotteryResult;

/**
 * MQ消息发送事件
 **/
public class MQEventListener implements EventListener {

    @Override
    public void doEvent(LotteryResult result) {
        System.out.println("记录用户摇号结果(MQ), 用户ID:" +  result.getuId() +
                ",摇号结果:" + result.getMsg());
    }
}
