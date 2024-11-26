package scheme.ObserverPattern.DrawHouseServiceObserver;

import scheme.ObserverPattern.DrawHouseService.LotteryResult;

public class Test {
    public static void main(String[] args) {
        LotteryService ls = new LotteryServiceImpl();
        LotteryResult result  = ls.lotteryAndMsg("1234567887654322");
        System.out.println(result);  
    }
}
