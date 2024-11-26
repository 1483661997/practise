package scheme.ObserverPattern.DrawHouseServiceObserver;

import scheme.ObserverPattern.DrawHouseService.LotteryResult;

/**
 * 短信发送事件
 **/
public class MessageEventListener implements EventListener {

    @Override
    public void doEvent(LotteryResult result) {
        System.out.println("发送短信通知用户ID为: " + result.getuId() +
                ",您的摇号结果如下: " + result.getMsg());
    }
}

