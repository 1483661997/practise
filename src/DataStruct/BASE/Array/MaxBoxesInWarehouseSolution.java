package DataStruct.BASE.Array;

import java.util.Arrays;

/**
 * 1564. 把箱子放进仓库里 I
 * 给定两个正整数数组 boxes 和 warehouse ，分别包含单位宽度的箱子的高度，以及仓库中 n 个房间各自的高度。仓库的房间分别从 0 到 n - 1 自左向右编号， warehouse[i] （索引从 0 开始）是第 i 个房间的高度。
 * 箱子放进仓库时遵循下列规则：
 * 箱子不可叠放。
 * 你可以重新调整箱子的顺序。
 * 箱子只能从左向右推进仓库中。
 * 如果仓库中某房间的高度小于某箱子的高度，则这个箱子和之后的箱子都会停在这个房间的前面。
 * 你最多可以在仓库中放进多少个箱子？
 *
 * boxes = [4,3,4,1], warehouse = [5,3,3,4,1]  5 3 3 3 1
 *
 */
public class MaxBoxesInWarehouseSolution {
    public int maxBoxesInWarehouse(int[] boxes, int[] warehouse) {
        int lenBox = boxes.length, lenHouse = warehouse.length;
        int[] left = new int[lenHouse];
        left[0] = warehouse[0];
        for(int i = 1; i < lenHouse; i++){
            left[i] = Math.min(left[i-1], warehouse[i]);
        }
        Arrays.sort(boxes);

        int box = 0, house = lenHouse-1;
        int num = 0;
        while (box < lenBox && house >= 0){
            if(left[house] >= boxes[box]){
                num++;
                house--;
                box++;
            }else{
                house--;
            }
        }
        return num;

    }
}
