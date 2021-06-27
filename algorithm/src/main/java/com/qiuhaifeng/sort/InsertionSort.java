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
 *    想让arr[0~0]上有序，这个范围只有一个数，当然是有序的。
 *    想让arr[0~1]上有序，所以从arr[1]开始往前看，如果arr[1]<arr[0]，就交换。否则什么也不做。
 *    …
 *    想让arr[0~i]上有序，所以从arr[i]开始往前看，arr[i]这个数不停向左移动，一直移动到左边的数字不再比自己大，停止移动。
 *    最后一步，想让arr[0~N-1]上有序， arr[N-1]这个数不停向左移动，一直移动到左边的数字不再比自己大，停止移动。
 *
 *    估算时发现这个算法流程的复杂程度，会因为数据状况的不同而不同。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-18
 **/
public class InsertionSort implements ISortable {
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
        for (int n = 1; n < len; n++) {
            // [0, n] 区间有序
            for (int i = n - 1; i >= 0 && arr[i] > arr[i + 1]; i--) {
                swap(arr, i, i + 1);
            }
        }
    }

    public static void main(String[] args) {
        ISortable.logarithm(aVoid -> new InsertionSort());
    }
}
