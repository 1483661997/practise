package DataStruct.BASE.Struct;

import java.util.*;

/**
 *  * 731. 我的日程安排表 II
 * 中等
 * 实现一个程序来存放你的日程安排。如果要添加的时间内不会导致三重预订时，则可以存储这个新的日程安排。
 * 当三个日程安排有一些时间上的交叉时（例如三个日程安排都在同一时间内），就会产生 三重预订。
 * 事件能够用一对整数 startTime 和 endTime 表示，在一个半开区间的时间 [startTime, endTime) 上预定。实数 x 的范围为  startTime <= x < endTime。
 * 实现 MyCalendarTwo 类：
 * MyCalendarTwo() 初始化日历对象。
 * boolean book(int startTime, int endTime) 如果可以将日程安排成功添加到日历中而不会导致三重预订，返回 true。否则，返回 false 并且不要将该日程安排添加到日历中。
 * 示例 1：
 * 输入：
 * ["MyCalendarTwo", "book", "book", "book", "book", "book", "book"]
 * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
 * 输出：
 * [null, true, true, true, false, true, true]
 * 解释：
 * MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
 * myCalendarTwo.book(10, 20); // 返回 True，能够预定该日程。
 * myCalendarTwo.book(50, 60); // 返回 True，能够预定该日程。
 * myCalendarTwo.book(10, 40); // 返回 True，该日程能够被重复预定。
 * myCalendarTwo.book(5, 15);  // 返回 False，该日程导致了三重预定，所以不能预定。
 * myCalendarTwo.book(5, 10); // 返回 True，能够预定该日程，因为它不使用已经双重预订的时间 10。
 * myCalendarTwo.book(25, 55); // 返回 True，能够预定该日程，因为时间段 [25, 40) 将被第三个日程重复预定，时间段 [40, 50) 将被单独预定，而时间段 [50, 55) 将被第二个日程重复预定。
 * 提示：
 * 0 <= start < end <= 109
 * 最多调用 book 1000 次。
 */
class MyCalendarTwo {
    public static void main(String[] args) {
        MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
        // System.out.println(myCalendarTwo.book(10, 20));
        // System.out.println(myCalendarTwo.book(50, 60));
        // System.out.println(myCalendarTwo.book(10, 40));
        // System.out.println(myCalendarTwo.book(5, 15));
        // System.out.println(myCalendarTwo.book(5, 10));
        // System.out.println(myCalendarTwo.book(25, 55));

        /*
         * [[],[26,35],[26,32],[25,32],[18,26],[40,45],[19,26],[48,50],[1,6],[46,50],[11,18]]
         * [null,true,true,false,true,true,true,true,true,true,true]
         */

        //  System.out.println(myCalendarTwo.book(26,35));
        //  System.out.println(myCalendarTwo.book(26,32));
        //  System.out.println(myCalendarTwo.book(25,32));
        //  System.out.println(myCalendarTwo.book(18,26));
        //  System.out.println(myCalendarTwo.book(40,45));
        //  System.out.println(myCalendarTwo.book(19,26));//* */
        //  System.out.println(myCalendarTwo.book(48,50));
        //  System.out.println(myCalendarTwo.book(1,6));
        //  System.out.println(myCalendarTwo.book(46,50));
        //  System.out.println(myCalendarTwo.book(11,18));//* */


         /**
          * [[],[24,40],[43,50],[27,43],[5,21],[30,40],[14,29],[3,19],[3,14],[25,39],[6,19]]
          */

          System.out.println(myCalendarTwo.book(24,40 ));
          System.out.println(myCalendarTwo.book(43,50 ));
          System.out.println(myCalendarTwo.book(27,43 ));
          System.out.println(myCalendarTwo.book(2,21 ));
          System.out.println(myCalendarTwo.book(30,40 ));
          System.out.println(myCalendarTwo.book(14,29 ));
          System.out.println(myCalendarTwo.book(3,19 ));
          System.out.println(myCalendarTwo.book(3,14 ));
          System.out.println(myCalendarTwo.book(25,39 ));
          System.out.println(myCalendarTwo.book(6,19 ));
  
          for(int[] i : myCalendarTwo.rebook ){
            System.out.println(i[0] + " " + i[1]);
        }

        // System.out.println("???");
        // for(int[] i : myCalendarTwo.list){
        //     System.out.println(i[0] + " " + i[1]);
        // }

    }

    List<int[]> list;
    List<int[]> repeat;
    public MyCalendarTwo() {
        list = new ArrayList<>();
        repeat = new ArrayList<>();
    }
    
<<<<<<< HEAD
//    public boolean book(int startTime, int endTime) {
//
//    }
=======
    public boolean book(int startTime, int endTime) {
        int len = list.size();
        int len1 = rebook.size();
        int pos = 0;
        int pos1 = 0;
        while (pos < len && startTime >= list.get(pos)[1]) {
            pos++;
        }
        
        while (pos1 < len1 && startTime >= rebook.get(pos1)[1]) {
            pos1++;
        }
        if(pos1 < len1 && endTime > rebook.get(pos1)[0]){
            return false;
        }


        while( pos < len && ( endTime > list.get(pos)[0])){
            rebook.add(pos1, new int[]{Math.max(startTime, list.get(pos)[0]), Math.min(endTime, list.get(pos)[1])});
            pos++;
        }


        list.add(pos, new int[]{startTime, endTime});
        return true;
    }
>>>>>>> 138e55fe98568d6abe51f63aeb562d97d7c05adf
}
