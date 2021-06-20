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

package com.qiuhaifeng.view;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *   用递归实现求数组arr[L..R]中的最大值。
 *      1）将[L..R]范围分成左右两半。左：[L..Mid]  右[Mid+1..R]
 *      2）左部分求最大值，右部分求最大值
 *      3） [L..R]范围上的最大值，是max{左部分最大值，右部分最大值}
 *    注意：2）是个递归过程，当范围上只有一个数，就可以不用再递归了
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-13
 **/
public class GetMaxNum {
    public static void main(String[] args) {
        int[] arr = AuxiliaryUtil.generateRandomArray(20, 30);
        System.out.println("arr = " + Arrays.toString(arr));
        int maxNum = getMaxNum(arr, 0, arr.length - 1);
        System.out.println("maxNum = " + maxNum);
    }

    /**
     * <p>获取[L..R]范围上的最大值</p>
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     * @return <code>int</code> 最大值
     */
    public static int getMaxNum(int[] arr, int left, int right) {
        if (Objects.isNull(arr) || arr.length == 0) {
            throw new IllegalArgumentException("Array may not be null or empty!");
        }

        if (right >= arr.length) {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ROOT, "Capacity: %d, right index: %d",
                    arr.length, right));
        }

        if (left < 0) {
            throw new ArrayIndexOutOfBoundsException(left);
        }

        return process(arr, left, right);
    }

    /**
     * <p>递归求[L..R]范围上的最大值</p>
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     * @return <code>int</code> 最大值
     */
    private static int process(int[] arr, int left, int right) {
        // arr[L..R]范围上只有一个数，直接返回，base case
        if (left == right) {
            return arr[left];
        }
        // L...R 不只一个数
        // mid = (L + R) / 2, 存在值相加溢出情况，故改写成
        int mid = left + ((right - left) >> 1);
        int lMax = process(arr, left, mid);
        int rMax = process(arr, mid + 1, right);
        return Math.max(lMax, rMax);
    }
}
