package scheme.ObserverPattern.Observer;

public class ConcreateServer1 implements Observer{
    @Override
    public void update(){
        System.out.println("ConcreateServer1111 收到消息！！！" );

    }

}
