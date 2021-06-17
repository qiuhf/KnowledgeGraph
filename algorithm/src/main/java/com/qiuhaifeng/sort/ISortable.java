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

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Arrays;
import java.util.Locale;

/**
 * ISortable
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-18
 **/
public interface ISortable {
    /**
     * <p>指定数组，按照从小到大排序</p>
     *
     * @param arr 数组
     */
    void sort(int[] arr);

    /**
     * <p>检验结果是否正确</p>
     */
    default void check() {
        int testTime = 100_000;
        int maxSize = 200;
        int maxValue = 2_000;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = AuxiliaryUtil.generateRandomArray(maxSize, maxValue);
            int[] arr1 = AuxiliaryUtil.copyArray(arr);
            int[] arr2 = AuxiliaryUtil.copyArray(arr);
            // 自定义排查
            this.sort(arr1);
            // JDK自带排序
            Arrays.sort(arr2);
            if (!Arrays.equals(arr1, arr2)) {
                succeed = false;
                System.err.printf(Locale.ROOT, "Origin = %s\nActual = %s\nExpect = %s\n", Arrays.toString(arr),
                        Arrays.toString(arr1), Arrays.toString(arr2));
                break;
            }
        }

        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }

    /**
     * <p>交换指定坐标值</p>
     *
     * @param arr 数据
     * @param i   下标i
     * @param j   下标j
     */
    default void swap(int[] arr, int i, int j) {
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
    }

    /**
     * <p>交换指定坐标值</p>
     *
     * @param arr 数据
     * @param i   下标i
     * @param j   下标j
     */
    default void swap2(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
