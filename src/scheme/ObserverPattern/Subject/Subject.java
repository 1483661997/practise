package scheme.ObserverPattern.Subject;

import scheme.ObserverPattern.Observer.Observer;

public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notifyAllObservers();
    
}
