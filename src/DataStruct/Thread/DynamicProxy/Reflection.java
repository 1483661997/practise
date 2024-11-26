package DataStruct.Thread.DynamicProxy;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Reflection {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException, ClassNotFoundException {
        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        map.computeIfAbsent(1, k -> new ArrayList<>()).add(1);

//        SharedStaticVariable sharedStaticVariable = new SharedStaticVariable();
//
//        Object obj = sharedStaticVariable;
//
//        Class<?> clazz = obj.getClass();
//
//
//        //获取成员变量
//        Field[] fields = clazz.getDeclaredFields();
//        for(Field field : fields){
//            System.out.println(field.getName());
//        }
//
//        //获取成员方法
//        Method[] methods = clazz.getMethods();
//        for(Method method : methods){
//            System.out.println(method.getName());
//        }
//
//        System.out.println(SharedStaticVariable.sharedCount);
//        Method method = clazz.getDeclaredMethod("incrementCount");
//        method.setAccessible(false);
//        method.invoke(obj);
//
//        System.out.println(SharedStaticVariable.sharedCount);
//
//        Object obj1 = clazz.newInstance();
//
//        Constructor<?> constructor = clazz.getConstructor();
//        Object obj2 = constructor.newInstance();

        Singleton singleton = Singleton.getSingleton();

        try {
            Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Singleton singleton1 = constructor.newInstance();  // 这里将抛出异常
            System.out.println(singleton1 == singleton);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //序列化和反序列化破坏单例
//        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
//        oos.writeObject(Singleton.getSingleton());
//        File file = new File("tempFile");
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
//        Singleton newInstance = (Singleton) ois.readObject();
//        System.out.println(newInstance == Singleton.getSingleton());
//

    }



}
