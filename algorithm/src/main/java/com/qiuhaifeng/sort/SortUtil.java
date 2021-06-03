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

import java.util.Arrays;

/**
 * @author sz_qiuhf@163.com
 * @since 2021-05-18
 **/
public class SortUtil {
    private SortUtil() {
    }

    public static void check(ISortable sortable) {
        int testTime = 500000;
        int maxSize = 100;
        int maxValue = 100;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = SortUtil.generateRandomArray(maxSize, maxValue);
            int[] arr1 = SortUtil.copyArray(arr);
            int[] arr2 = SortUtil.copyArray(arr);
            // 自定义排查
            sortable.sort(arr1);
            // JDK自带排序
            Arrays.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                succeed = false;
                printArray("src = ", arr);
                printArray("actual = ", arr1);
                printArray("expect = ", arr2);
                break;
            }
        }

        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    /**
     * <p>根据指定条件，生成随机数组</p>
     *
     * @param maxLen   数组最大长度
     * @param maxValue 最大值
     * @return <code>int[]</code> 数组
     */
    private static int[] generateRandomArray(int maxLen, int maxValue) {
        // Math.random()   [0,1)
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int len = (int) ((maxLen + 1) * Math.random());
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            ints[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
        }
        return ints;
    }

    /**
     * <p>交换指定坐标值</p>
     *
     * @param arr 数据
     * @param i   下标i
     * @param j   下标j
     */
    static void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /**
     * <p>打印数组</p>
     *
     * @param prefix 前缀
     * @param arr    数组
     */
    private static void printArray(String prefix, int[] arr) {
        System.out.println(prefix + ": " + Arrays.toString(arr));
    }

    /**
     * <p>打印数组</p>
     *
     * @param arr 数组
     */
    public static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    /**
     * <p>拷贝数组</p>
     *
     * @param arr1 arr1
     * @return <code>int[]</code> 数组
     */
    private static int[] copyArray(int[] arr1) {
        int[] arr2 = new int[arr1.length];
        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        return arr2;
    }
}
