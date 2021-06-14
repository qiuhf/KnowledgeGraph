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

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *   归并排序，题型2： 右边有多少个数比自己小（降序）
 *   在一个数组中，一个右边比它小的数的，则称之为“逆序对”。求数组中有多少对逆序对。
 *   例子： [1,3,4,2,5]
 *      1右边比1小的数：没有
 *      3右边比3小的数：(3, 2)
 *      4右边比4小的数：(4, 2)
 *      2右边比2小的数：没有
 *      5右边比5小的数：没有
 *      所以数组的逆序对个数为1+1=2
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-14
 **/
public class ReversePair {
    public static void main(String[] args) {
        int len = 200;
        int range = 10_000;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            int length = (int) (Math.random() * len);
            int[] randomArray = AuxiliaryUtil.generateRandomArray(length, range);
            int checkPair = checkReversePair(randomArray);
            int pair = reversePairNum(randomArray);
            if (checkPair != pair) {
                System.err.printf(Locale.ROOT, "Actual: %d, Expect: %d\n", pair, checkPair);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int reversePairNum(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);
        return process(arr, left, mid) + process(arr, mid + 1, right) + merge(arr, left, mid, right);
    }

    private static int merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int index = helper.length - 1;
        int lPos = mid;
        int rPos = right;
        int ans = 0;
        while (lPos >= left && rPos > mid) {
            if (arr[lPos] > arr[rPos]) {
                ans += rPos - mid;
                helper[index--] = arr[lPos--];
            } else {
                helper[index--] = arr[rPos--];
            }
        }

        while (lPos >= left) {
            helper[index--] = arr[lPos--];
        }

        while (rPos > mid) {
            helper[index--] = arr[rPos--];
        }

        System.arraycopy(helper, 0, arr, left, helper.length);
        return ans;
    }

    private static int checkReversePair(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return 0;
        }

        int len = arr.length;
        int pair = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (arr[i] > arr[j]) {
                    pair++;
                }
            }
        }

        return pair;
    }
}
