/*
 * Copyright 2020-2021. the original qiuhaifeng .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qiuhaifeng.sort;

import java.util.Objects;

/**
 * <pre>
 *   过程：
 *      arr[0～N-1]范围上，找到最小值所在的位置，然后把最小值交换到0位置。
 *      arr[1～N-1]范围上，找到最小值所在的位置，然后把最小值交换到1位置。
 *      arr[2～N-1]范围上，找到最小值所在的位置，然后把最小值交换到2位置。
 *      …
 *      arr[N-1～N-1]范围上，找到最小值位置，然后把最小值交换到N-1位置。
 *
 *    估算：
 *      很明显，如果arr长度为N，每一步常数操作的数量，如等差数列一般
 *      所以，总的常数操作数量 = a*(N^2) + b*N + c (a、b、c都是常数)
 *
 * 所以选择排序的时间复杂度为O(N^2)。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-18
 **/
public class SelectionSort implements ISortable {
    public static void main(String[] args) {
        SortUtil.check(new SelectionSort());
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

        int len = arr.length;
        // [0, len-1] 找到最小值，在哪，放到0位置上
        // [1, len-1] 找到最小值，在哪，放到1位置上
        // [2, len-1] 找到最小值，在哪，放到2位置上
        // ...
        for (int n = 0; n < len - 1; n++) {
            int minIndex = n;
            for (int i = n + 1; i < len; i++) {
                // n ~ len -1 上找最小值的下标
                minIndex = arr[i] < arr[minIndex] ? i : minIndex;
            }
            if (n != minIndex) {
                SortUtil.swap(arr, n, minIndex);
            }
        }
    }
}
