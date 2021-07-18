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

package com.qiuhaifeng.sort.view;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * <pre>
 *     已知一个几乎有序的数组。几乎有序是指，如果把数组排好顺序的话，每个元素移动的距离一定不超过k，并且k相对于数组长度来说是比较小的。
 *  请选择一个合适的排序策略，对这个数组进行排序。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-22
 **/
public class SortArrayDistanceLessK {
    public static void main(String[] args) {
        int testTime = 100_000;
        int maxSize = 200;
        int maxValue = 2_000;
        int distanceK = 12;
        for (int i = 0; i < testTime; i++) {
            int[] randomArray = AuxiliaryUtil.generateRandomArray(maxSize, maxValue);
            int[] arr = AuxiliaryUtil.copyArray(randomArray);
            Arrays.sort(arr);
            int k = randomArrayMoveLessK(randomArray, distanceK);
            int[] arr2 = AuxiliaryUtil.copyArray(randomArray);
            sort(arr2, k);
            if (!Arrays.equals(arr, arr2)) {
                System.err.printf(Locale.ROOT, "Origin = %s\nActual = %s\nExpect = %s\n",
                        Arrays.toString(randomArray), Arrays.toString(arr), Arrays.toString(arr2));
                return;
            }
        }
        System.out.println("Nice!");
    }

    /**
     * <p>时间复杂度：O(N*logK)</p>
     *
     * @param arr       数组
     * @param distanceK 最大移动距离
     */
    public static void sort(int[] arr, int distanceK) {
        if (Objects.isNull(arr) || arr.length < 2 || distanceK == 0) {
            return;
        }
        int len = arr.length;
        // 默认小根堆
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(len);
        int index = 0;
        // [0, k]变小根堆
        while (index < distanceK) {
            minHeap.add(arr[index++]);
        }
        // [1, k + 1]变小根堆，弹出堆顶给arr[0]
        // [2, k + 2]变小根堆，弹出堆顶给arr[1]
        // 依次类推
        int cursor = 0;
        while (index < len) {
            minHeap.add(arr[index++]);
            arr[cursor++] = minHeap.poll();
        }

        // 最后一组小根堆依次弹出
        while (!minHeap.isEmpty()) {
            arr[cursor++] = minHeap.poll();
        }
    }

    private static int randomArrayMoveLessK(int[] arr, int distanceK) {
        // 排好序后开始随意交换，但是保证每个数距离不超过K
        Arrays.sort(arr);
        int len = arr.length;
        // swap[i] == true/false, 表示i位置已交换/未交换
        boolean[] isSwap = new boolean[len];
        int k = Math.min((int) (Math.random() * (distanceK + 1)), len - 1);
        for (int i = 0; i < len; i++) {
            int j = Math.min(i + (int) (Math.random() * k), len - 1);
            if (!isSwap[i] && !isSwap[j]) {
                isSwap[i] = true;
                isSwap[j] = true;
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
            }
        }
        return k;
    }
}
