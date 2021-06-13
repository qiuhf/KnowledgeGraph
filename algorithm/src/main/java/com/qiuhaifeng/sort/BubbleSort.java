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
 *    过程：
 *     在arr[0～N-1]范围上：
 *     arr[0]和arr[1]，谁大谁来到1位置；arr[1]和arr[2]，谁大谁来到2位置…arr[N-2]和arr[N-1]，谁大谁来到N-1位置
 *
 *     在arr[0～N-2]范围上，重复上面的过程，但最后一步是arr[N-3]和arr[N-2]，谁大谁来到N-2位置
 *     在arr[0～N-3]范围上，重复上面的过程，但最后一步是arr[N-4]和arr[N-3]，谁大谁来到N-3位置
 *     …
 *     最后在arr[0～1]范围上，重复上面的过程，但最后一步是arr[0]和arr[1]，谁大谁来到1位置
 *
 *     估算：
 *     很明显，如果arr长度为N，每一步常数操作的数量，依然如等差数列一般
 *     所以，总的常数操作数量 = a*(N^2) + b*N + c (a、b、c都是常数)
 *
 *     所以冒泡排序的时间复杂度为O(N^2)。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-18
 **/
public class BubbleSort implements ISortable {
    public static void main(String[] args) {
        new BubbleSort().check();
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
        // [0, len-1] 找到最大值，放到len-1位置
        // [0, len-2] 找到最大值，放到len-2位置
        // [0, len-3] 找到最大值，放到len-3位置
        // ...
        // [0, 1] 找到最大值，放到1位置
        for (int n = len - 1; n > 0; n--) {
            for (int i = 0; i < n; i++) {
                if (arr[i] > arr[i + 1]) {
                    swap(arr, i, i + 1);
                }
            }
        }
    }
}
