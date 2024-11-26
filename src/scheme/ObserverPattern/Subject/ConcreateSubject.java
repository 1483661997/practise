package scheme.ObserverPattern.Subject;

import java.util.ArrayList;

import scheme.ObserverPattern.Observer.Observer;

public class ConcreateSubject implements Subject{

    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer observer) {
        observers.add(observer);    
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() {
        for(Observer observer : observers)
            observer.update();
    }

}
