from functools import cache


class Solution:
    def countSpecialNumber(self, n : int) -> int:
        # 数位 DP
        s = str(n)

        @cache
        def f(i : int , mask : int, is_limit : bool, is_num : bool) -> int:
            if i == len(s):
                return int(is_num)
            res = 0
            if not is_num: #选择跳过
                res = f(i+1, mask, False, False)
            up = int(s[i]) if is_limit else 9
            for d in range(1 if is_num else 9):
                if mask >> d & 1 == 0 : # mask 里面没有d
                    res += f(i+1, mask|(1<<d), is_limit and d == up, True)
            return res
        return f(0, 0, True, False )