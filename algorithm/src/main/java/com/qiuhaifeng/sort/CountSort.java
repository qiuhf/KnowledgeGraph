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
 *     计数排序
 *     时间复杂度为O(N)，额外空间负载度O(M)
 *     Note: 要求样本是整数，且范围比较窄的场景
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public class CountSort implements ISortable {
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

        // 支持整数计数排序
        this.integerSort(arr);

        // 经典计数排序（样本数据都是正整数）
        this.positiveSort(arr);
    }

    private void integerSort(int[] arr) {
        // 正整数最大值
        int max = 0;
        // 负整数最小值
        int min = 0;
        for (int num : arr) {
            if (num < 0) {
                min = Math.min(min, num);
            } else {
                max = Math.max(max, num);
            }
        }
        min = Math.abs(min);
        int[] bucket = this.count(arr, min, max);

        // 将桶中的数遍历导出
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- != 0) {
                arr[index++] = i - min;
            }
        }
    }

    private void positiveSort(int[] arr) {
        // 获取最大值
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num < 0) {
                throw new IllegalArgumentException(num + ", the number less than 0");
            }
            max = Math.max(max, num);
        }
        int[] bucket = new int[max + 1];
        // 对每个元素计数
        for (int num : arr) {
            bucket[num]++;
        }
        // 将桶中的数遍历导出
        int index = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- != 0) {
                arr[index++] = i;
            }
        }
    }

    private int[] count(int[] arr, int min, int max) {
        int len = max + 1 + min;
        int[] bucket = new int[len];
        // 对每个元素计数
        for (int num : arr) {
            bucket[num + min]++;
        }
        return bucket;
    }

    public static void main(String[] args) {
        ISortable.logarithm(aVoid -> new CountSort());
    }
}
