package DataStruct.BASE.Stack;


import javax.sound.sampled.Line;
import java.util.Deque;
import java.util.LinkedList;

/**
 * 155. 最小栈
 * 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。
 * 实现 MinStack 类:
 * MinStack() 初始化堆栈对象。
 * void push(int val) 将元素val推入堆栈。
 * void pop() 删除堆栈顶部的元素。
 * int top() 获取堆栈顶部的元素。
 * int getMin() 获取堆栈中的最小元素。
 *
 */
public class MinStack {
    Deque<Integer> stack1;
    Deque<Integer> stack2;
    int min;
    public MinStack() {
        stack1 = new LinkedList<>();
        stack2 = new LinkedList<>();
        min = Integer.MAX_VALUE;
    }

    public void push(int val) {
        stack1.push(val);
        if(val < min) min = val;
        stack2.push(min);
    }

    public void pop() {
        stack1.pop();
        stack2.pop();
        if(!stack2.isEmpty())
            min = stack2.peek();
        else min = Integer.MAX_VALUE;
    }

    public int top() {
        return stack1.peek();
    }

    public int getMin() {
        return stack2.peek();

    }

}
