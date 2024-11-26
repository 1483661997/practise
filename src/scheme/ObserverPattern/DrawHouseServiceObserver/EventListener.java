package scheme.ObserverPattern.DrawHouseServiceObserver;

import scheme.ObserverPattern.DrawHouseService.LotteryResult;

public interface EventListener {
    void doEvent(LotteryResult result);
}
