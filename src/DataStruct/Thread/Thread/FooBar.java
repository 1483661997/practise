package DataStruct.Thread.Thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * 1115. 交替打印 FooBar
给你一个类：

class FooBar {
  public void foo() {
    for (int i = 0; i < n; i++) {
      print("foo");
    }
  }

  public void bar() {
    for (int i = 0; i < n; i++) {
      print("bar");
    }
  }
}
两个不同的线程将会共用一个 FooBar 实例：

线程 A 将会调用 foo() 方法，而
线程 B 将会调用 bar() 方法
请设计修改程序，以确保 "foobar" 被输出 n 次。

示例 1：

输入：n = 1
输出："foobar"
解释：这里有两个线程被异步启动。其中一个调用 foo() 方法, 另一个调用 bar() 方法，"foobar" 将被输出一次。
示例 2：

输入：n = 2
输出："foobarfoobar"
解释："foobar" 将被输出两次。
提示：

1 <= n <= 1000
 */
public class FooBar {
    private int n;
    private static final ReadWriteLock lock = new ReentrantReadWriteLock();
    private static final Lock writeLock = lock.writeLock();
    // private static final Lock readLock = lock.readLock();
    private static final Condition condition = writeLock.newCondition();
    private static boolean isCompleted = false;

    public FooBar(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            writeLock.lock();

            while(isCompleted){
                condition.await();
            }
            
        	// printFoo.run() outputs "foo". Do not change or remove this line.
        	printFoo.run();
          
            System.out.print("foo");
            isCompleted = true;
            writeLock.unlock();
            
            writeLock.lock();
            condition.signal();
            writeLock.unlock();

            
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        
        for (int i = 0; i < n; i++) {
            writeLock.lock();
            while (!isCompleted) {
                condition.await();
            }
            // printBar.run() outputs "bar". Do not change or remove this line.
        	printBar.run();
            
            
            System.out.print("bar");
            isCompleted = false;

            writeLock.unlock();
            

            writeLock.lock();
            condition.signal();
            writeLock.unlock();

        }
    }
}
