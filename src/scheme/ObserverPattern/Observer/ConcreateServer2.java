package scheme.ObserverPattern.Observer;

public class ConcreateServer2 implements Observer{

    @Override
    public void update() {
        System.out.println("ConcreateServer2222 收到消息！！！" );
    }

}
