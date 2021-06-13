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

package com.qiuhaifeng.util;

/**
 * 辅助工具
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-13
 **/
public class AuxiliaryUtil {
    private AuxiliaryUtil() {
    }

    /**
     * <p>随机生成范围中的一个数：{range, -range}</p>
     *
     * @param range 范围
     * @return <code>int</code>
     */
    public static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    /**
     * <p>根据指定条件，生成随机数组</p>
     *
     * @param maxLen 数组最大长度
     * @param range  范围
     * @return <code>int[]</code> 数组
     */
    public static int[] generateRandomArray(int maxLen, int range) {
        // Math.random()   [0,1)
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int len = (int) ((maxLen + 1) * Math.random());
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            ints[i] = randomNumber(range);
        }
        return ints;
    }


    /**
     * <p>拷贝数组</p>
     *
     * @param arr1 arr1
     * @return <code>int[]</code> 数组
     */
    public static int[] copyArray(int[] arr1) {
        int[] arr2 = new int[arr1.length];
        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        return arr2;
    }
}
