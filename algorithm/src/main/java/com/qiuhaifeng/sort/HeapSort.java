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
 *  堆排序
 *  1，先让整个数组都变成大根堆结构，建立堆的过程:
 *     1)从上到下的方法，时间复杂度为O(N*logN)
 *     2)从下到上的方法，时间复杂度为O(N)
 *  2，把堆的最大值和堆末尾的值交换，然后减少堆的大小之后，再去调整堆，一直周而复始，时间复杂度为O(N*logN),额外空间复杂度O(1)
 *  3，堆的大小减小成0之后，排序完成
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-20
 **/
public class HeapSort implements ISortable {
    public static void main(String[] args) {
        new HeapSort().check();
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

        // 整个数组都变成大根堆结构
        // 1)从上到下的方法，时间复杂度为O(N*logN)
        int heapSize = arr.length;
        // 遍历整个数组，时间复杂度：O(N)
//        for (int i = 0; i < heapSize; i++) {
//            heapInsert(arr, i);
//        }

        // 2)从下到上的方法，时间复杂度为O(N)
        // 忽略最后一层，从倒数第二层最后一个数开始heapify
        int cursor = heapSize;
        while ((cursor & (cursor - 1)) != 0) {
            cursor &= cursor - 1;
        }

        for (int i = (cursor - 1) & (~1); i >= 0; i--) {
            this.heapify(arr, i, heapSize);
        }

        do {
            // 堆的最大值和堆末尾的值交换, 时间复杂度： O(1)
            swap2(arr, 0, --heapSize);
            this.heapify(arr, 0, heapSize);
            // 遍历整个数组，时间复杂度：O(N)
        } while (heapSize > 0);
    }

    /**
     * <pre>
     *     新加进来的数，现在停在了index位置，请依次往上移动
     *     时间复杂度：O(logN)
     * </pre>
     *
     * @param arr   堆
     * @param index 当前位置
     */
    private void heapInsert(int[] arr, int index) {
        while (arr[index] > arr[(index - 1) / 2]) {
            swap(arr, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * <pre>
     *      从index位置，往下看，不断的下沉
     *      时间复杂度：O(logN)
     * </pre>
     *
     * @param arr      堆
     * @param index    需要比较的下标
     * @param heapSize 当前堆大小
     */
    private void heapify(int[] arr, int index, int heapSize) {
        for (int left = 1 + (index << 1); left != -1 && left < heapSize; left = 1 + (index << 1)) {
            int right = left + 1;
            // 两个子节点中，谁的值大，把下标给largest
            // 1）只有左子节点，left -> largest
            // 2) 同时有左子节点和右子节点，右子节点的值<= 左子节点的值，left -> largest
            // 3) 同时有左子节点和右子节点并且右子节点的值> 左子节点的值， right -> largest
            int largest = right < heapSize && arr[right] > arr[left] ? right : left;
            if (arr[index] >= arr[largest]) {
                return;
            }
            swap(arr, index, largest);
            index = largest;
        }
    }

}
