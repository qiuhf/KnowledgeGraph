/*
 *  Copyright 2021-2021. the original qiuhaifeng .
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.qiuhaifeng.sort;

import java.util.Objects;

/**
 * <pre>
 *   快速排序
 *     1.快速排序1.0和2.0的时间复杂度分析：
 *        数组已经有序的时候就是复杂度最高的时候，时间复杂度O(N^2)
 *     2.随机快排的时间复杂度分析:
 *        1）通过分析知道，划分值越靠近中间，性能越好；越靠近两边，性能越差
 *        2）随机选一个数进行划分的目的就是让好情况和差情况都变成概率事件
 *        3）把每一种情况都列出来，会有每种情况下的时间复杂度，但概率都是1/N
 *        4）那么所有情况都考虑，时间复杂度就是这种概率模型下的长期期望！
 *     时间复杂度O(N*logN)，额外空间复杂度O(logN)都是这么来的
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-16
 **/
public class QuickSort implements ISortable {
    public static void main(String[] args) {
        new QuickSort().check();
    }

    /**
     * <p>指定数组，按照从小到大排序</p>
     *
     * @param arr 数组
     */
    @Override
    public void sort(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }

        // 快速排序3.0版本
        quickSort3(arr, 0, arr.length - 1);

        // 快速排序1.0版本
        quickSort1(arr, 0, arr.length - 1);

        // 快速排序2.0版本
        quickSort2(arr, 0, arr.length - 1);
    }

    /**
     * <pre>
     *     快速排序3.0版本(随机快排 + 荷兰国旗技巧优化)
     *     在arr[L..R]范围上，进行快速排序的过程：
     *       1）在这个范围上，随机选一个数记为num，
     *       1）用num对该范围做partition，< num的数在左部分，== num的数中间，>num的数在右部分。假设== num的数所在范围是[a,b]
     *       2）对arr[L..a-1]进行快速排序(递归)
     *       3）对arr[b+1..R]进行快速排序(递归)
     *     因为每一次partition都会搞定一批数的位置且不会再变动，所以排序能完成
     * </pre>
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 有边界
     */
    private void quickSort3(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }
        // 随机选一个数记为num与数组最后一个值交换
        swap2(arr, left + (int) (Math.random() * (right - left + 1)), right);

        // 每一次partition（荷兰国旗）都会搞定一批数的位置且不会再变动
        int[] equalArea = netherLandFlag(arr, left, right);
        this.quickSort3(arr, left, equalArea[0] - 1);
        this.quickSort3(arr, equalArea[1] + 1, right);
    }

    /**
     * <pre>
     *     快速排序1.0版本
     *     在arr[L..R]范围上，进行快速排序的过程：
     *        1）用arr[R]对该范围做partition，<= arr[R]的数在左部分并且保证arr[R]最后来到左部分的最后一个位置，记为M； <= arr[R]的数在右部分（arr[M+1..R]）
     *        2）对arr[L..M-1]进行快速排序(递归)
     *        3）对arr[M+1..R]进行快速排序(递归)
     *      因为每一次partition都会搞定一个数的位置且不会再变动，所以排序能完成
     * </pre>
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 有边界
     */
    private void quickSort1(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        // 每一次partition都会搞定一个数的位置且不会再变动
        int equals = partition(arr, left, right);
        this.quickSort1(arr, left, equals - 1);
        this.quickSort1(arr, equals + 1, right);
    }

    /**
     * <pre>
     *     快速排序2.0版本
     *     在arr[L..R]范围上，进行快速排序的过程：
     *       1）用arr[R]对该范围做partition，< arr[R]的数在左部分，== arr[R]的数中间，>arr[R]的数在右部分。假设== arr[R]的数所在范围是[a,b]
     *       2）对arr[L..a-1]进行快速排序(递归)
     *       3）对arr[b+1..R]进行快速排序(递归)
     *      因为每一次partition（荷兰国旗）都会搞定一批数的位置且不会再变动，所以排序能完成
     * </pre>
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 有边界
     */
    private void quickSort2(int[] arr, int left, int right) {
        if (left >= right) {
            return;
        }

        // 每一次partition（荷兰国旗）都会搞定一批数的位置且不会再变动
        int[] equalArea = netherLandFlag(arr, left, right);
        this.quickSort2(arr, left, equalArea[0] - 1);
        this.quickSort2(arr, equalArea[1] + 1, right);
    }

    private int[] netherLandFlag(int[] arr, int left, int right) {
        if (left == right) {
            return new int[]{left, right};
        }
        int less = left - 1;
        int more = right;
        int index = left;
        do {
            if (arr[index] == arr[right]) {
                index++;
            } else if (arr[index] < arr[right]) {
                swap2(arr, index++, ++less);
            } else {
                swap2(arr, index, --more);
            }
        } while (index < more);

        swap2(arr, more, right);
        return new int[]{++less, more};
    }

    private int partition(int[] arr, int left, int right) {
        if (left > right) {
            return -1;
        }
        if (left == right) {
            return left;
        }
        int lessEquals = left - 1;
        int index = left;
        do {
            if (arr[index] <= arr[right]) {
                // 存在index == lessEquals, 故不用异或方式交换
                swap2(arr, index, ++lessEquals);
            }
            index++;
        } while (index < right);

        swap2(arr, ++lessEquals, right);
        return lessEquals;
    }
}
