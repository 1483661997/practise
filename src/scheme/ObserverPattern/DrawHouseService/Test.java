package scheme.ObserverPattern.DrawHouseService;
public class Test {
    public static void main(String[] args) {
        LotteryService ls = new LotteryServiceImpl();
        String result  = ls.lottery("1234567887654322").toString();
        System.out.println(result);
    }
}
