package Test;
import java.util.*;

public class Test {
    int money;
    
    public static void main(String[] args) {
        Test person = new Test();
        person.money = 1;
        System.out.println(person.money);
    
        person.check(person);
        System.out.println(person.money);

        // ReflectionUtils


        Deque<Integer> list1 = new LinkedList<>();
        List<Integer> list2 = new LinkedList<>();

        

    }

    public void check(Test person){
        person.money = 0;
        person = null;
    }

    
    
}
