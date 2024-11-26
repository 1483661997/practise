package scheme.ObserverPattern;

import scheme.ObserverPattern.Observer.ConcreateServer1;
import scheme.ObserverPattern.Observer.ConcreateServer2;
import scheme.ObserverPattern.Subject.ConcreateSubject;

public class Client {
    public static void main(String[] args) {
        ConcreateSubject subject = new ConcreateSubject();

        subject.attach(new ConcreateServer1());
        subject.attach(new ConcreateServer2());

        subject.notifyAllObservers();
    }
}
