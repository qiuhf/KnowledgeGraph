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

/**
 * <pre>
 *   荷兰国旗问题
 *      给定一个数组arr，和一个整数num。请把小于num的数放在数组的左边，等于num的数放在中间，大于num的数放在数组的右边。
 *   要求额外空间复杂度O(1)，时间复杂度O(N)
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-16
 **/
public class NetherlandsFlag {
    public static void main(String[] args) {
        int[] array = AuxiliaryUtil.generateRandomArray(20, 10);
        // array = new int[]{-3, 3, 2, 0, -2, 3, 0, 6, 0, -2};
        if (array.length == 0) {
            return;
        }
        int num = Math.random() > 0.5 ? array[(int) (Math.random() * array.length)] : (int) (Math.random() * array.length);
        // num = 6;
        System.out.printf(Locale.ROOT, "Array: %s, num: %d\n", Arrays.toString(array), num);
        int[] partitions = partitions(array, num);
        System.out.printf(Locale.ROOT, "Array: %s => %s\n", Arrays.toString(array), Arrays.toString(partitions));
    }

    /**
     * <p>以指定数字对数组进行划分（< num, = num, > num）, 返回等于num的区间下标</p>
     *
     * @param arr 数组
     * @param num 值
     * @return <code>int[]</code>
     */
    public static int[] partitions(int[] arr, int num) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return new int[]{-1, -1};
        }

        int less = -1;
        int bigger = arr.length;
        int index = 0;
        do {
            if (arr[index] < num) {
                swap(arr, index++, ++less);
            } else if (arr[index] == num) {
                index++;
            } else {
                swap(arr, index, --bigger);
            }
        } while (index < bigger);
        // 没有对应的num
        if (less >= bigger - 1 || bigger <= 0) {
            return new int[]{-1, -1};
        }
        return new int[]{less + 1, bigger - 1};
    }

    private static void swap(int[] arr, int i, int j) {
        // 额外空间复杂度O(1)
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
