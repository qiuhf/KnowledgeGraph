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
 *   在一个数组中，一个数左边比它小的数的总和，叫数的小和，所有数的小和累加起来，叫数组小和。求数组小和。
 *   例子： [1,3,4,2,5]
 *      1左边比1小的数：没有
 *      3左边比3小的数：1
 *      4左边比4小的数：1、3
 *      2左边比2小的数：1
 *      5左边比5小的数：1、3、4、 2
 *      所以数组的小和为1+1+3+1+1+3+4+2=16
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-14
 **/
public class SmallSum {
    public static void main(String[] args) {
        int len = 100;
        int range = 1_000;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            int length = (int) (Math.random() * len);
            int[] randomArray = AuxiliaryUtil.generateRandomArray(length, range);
            int checkSum = checkSum(randomArray);
            int sum = sum(randomArray);
            if (checkSum != sum) {
                System.err.printf(Locale.ROOT, "Actual: %d, Expect: %d\n", sum, checkSum);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int sum(int[] arr) {
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
        int index = 0;
        int lPos = left;
        int rPos = mid + 1;
        int ans = 0;
        while (lPos <= mid && rPos <= right) {
            // 相等优先copy右组
            if (arr[lPos] < arr[rPos]) {
                // 右组大产生小和
                ans += arr[lPos] * (right - rPos + 1);
                helper[index++] = arr[lPos++];
            } else {
                helper[index++] = arr[rPos++];
            }
        }

        while (lPos <= mid) {
            helper[index++] = arr[lPos++];
        }

        while (rPos <= right) {
            helper[index++] = arr[rPos++];
        }

        System.arraycopy(helper, 0, arr, left, helper.length);
        return ans;
    }

    public static int checkSum(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return 0;
        }

        int ans = 0;
        int len = arr.length - 1;
        for (int i = len; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] < arr[i]) {
                    ans += arr[j];
                }
            }
        }

        return ans;
    }
}
