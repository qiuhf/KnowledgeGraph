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
 *     基数排序
 *     时间复杂度为O(N)，额外空间负载度O(M)
 *     Note: 要求样本是10进制的(正)整数
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-28
 **/
public class RadixSort implements ISortable {
    private static final int RADIX = 10;

    /**
     * <pre>
     *     指定数组，按照从小到大排序
     *     Note: 只实现正整数基数排序，如需同时支持负整数，可参照计数排序的 {@link CountSort#integerSort}
     *           思想修改
     * </pre>
     *
     * @param arr 数组
     */
    @Override
    public void sort(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }

        // 获取位数
        int seat = getNumberOfDigits(arr);

        this.process(arr, 0, arr.length - 1, seat);
    }

    /**
     * <p>指定范围内做基数排序</p>
     *
     * @param arr   数组
     * @param start 起始位置
     * @param end   结束位置
     * @param seat  位数
     */
    private void process(int[] arr, int start, int end, int seat) {
        int[] helper = new int[end - start + 1];
        for (int digit = 0; digit < seat; digit++) {
            int[] bucket = new int[RADIX];
            // 统计0到9共出现多少次
            for (int index = start; index <= end; index++) {
                bucket[this.getDigits(arr[index], digit)]++;
            }
            // 计数桶转换前缀和，用于计数[0, j]有多少个数
            for (int j = 1; j < RADIX; j++) {
                bucket[j] += bucket[j - 1];
            }
            // 将数据导入到helper数组
            for (int i = end; i >= 0; i--) {
                helper[--bucket[this.getDigits(arr[i], digit)]] = arr[i];
            }
            // 用helper数组数据覆盖原始数组数据
            for (int i = start, j = 0; i <= end; i++, j++) {
                arr[i] = helper[j];
            }
        }
    }

    /**
     * <p>获取指定位数的值</p>
     *
     * @param num   数字
     * @param digit 当前位： 个/十/百...
     * @return <code>int</code>
     */
    private int getDigits(int num, int digit) {
        return (num / ((int) Math.pow(10, digit))) % 10;
    }

    /**
     * <p>遍历获取最大值，并返回位数</p>
     *
     * @param arr 数组
     * @return <code>int</code>
     */
    private int getNumberOfDigits(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int num : arr) {
            if (num < 0) {
                throw new IllegalArgumentException(num + ", the number less than 0");
            }
            max = Math.max(max, num);
        }

        int seat = 0;
        while (max != 0) {
            seat++;
            max /= RADIX;
        }
        return seat;
    }

    public static void main(String[] args) {
        ISortable.logarithm(aVoid -> new RadixSort());
    }
}
