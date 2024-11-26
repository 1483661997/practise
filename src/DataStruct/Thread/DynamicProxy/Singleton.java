package DataStruct.Thread.DynamicProxy;

import java.io.Serializable;

public class Singleton implements Serializable, Cloneable {
    private  volatile  static  Singleton singleton;
    private Singleton(){
        if (singleton != null) {
            throw new RuntimeException("Instance already exists!");
        }
    }

    public static  Singleton getSingleton(){
        if(singleton == null){
            synchronized (Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }

//    @Serial
//    protected Object readResolve(){
//        return  getSingleton();
//    }
}
