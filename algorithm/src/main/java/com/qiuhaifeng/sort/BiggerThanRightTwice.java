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
 *   在一个数组中，求数组中有多少个数比右边大两倍。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-14
 **/
public class BiggerThanRightTwice {
    public static void main(String[] args) {
        int len = 200;
        int range = 1_000;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            int length = (int) (Math.random() * len);
            int[] randomArray = AuxiliaryUtil.generateRandomArray(length, range);
            int checkSum = check(randomArray);
            int sum = biggerTwice(randomArray);
            if (checkSum != sum) {
                System.err.printf(Locale.ROOT, "Actual: %d, Expect: %d\n", sum, checkSum);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int biggerTwice(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return 0;
        }

        return process(arr, 0, arr.length - 1);
    }

    private static int count(int[] arr, int left, int mid, int right) {
        // 目前囊括进来的数，左闭右开: [mid + 1, windowR)
        // 指针不回退，
        int ans = 0;
        int windowR = mid + 1;
        // left: 0, mid: 3, right: 7
        // 2 3 5 9 | 1 1 5 6
        // ans: 2
        for (int i = left; i <= mid; i++) {
            while (windowR <= right && arr[i] > (arr[windowR] << 1)) {
                windowR++;
            }
            ans += windowR - mid - 1;
        }
        return ans;
    }

    private static int process(int[] arr, int left, int right) {
        if (left == right) {
            return 0;
        }

        int mid = left + ((right - left) >> 1);
        return process(arr, left, mid) + process(arr, mid + 1, right) + merge(arr, left, mid, right);
    }

    private static int merge(int[] arr, int left, int mid, int right) {
        // 左右组merge前先统计比右边大两倍的数
        int ans = count(arr, left, mid, right);
        int[] helper = new int[right - left + 1];
        int index = 0;
        int lPos = left;
        int rPos = mid + 1;
        while (lPos <= mid && rPos <= right) {
            helper[index++] = arr[lPos] <= arr[rPos] ? arr[lPos++] : arr[rPos++];
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

    private static int check(int[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return 0;
        }
        int len = arr.length;
        int ans = 0;
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (arr[i] > (arr[j] << 1)) {
                    ans++;
                }
            }
        }

        return ans;
    }
}
