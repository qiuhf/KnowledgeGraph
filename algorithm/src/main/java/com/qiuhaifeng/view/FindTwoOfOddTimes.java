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

package com.qiuhaifeng.view;

import java.util.Arrays;
import java.util.Objects;

/**
 * <pre>
 *    一个数组中有两种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这两种数
 * </pre>
 * FindTwoOfOddTimes
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-19
 **/
public class FindTwoOfOddTimes {
    public static void main(String[] args) {
        int[] arr = {1454, 0, 20000, 0, 3, 222, 222, 3};
        System.out.println(Arrays.toString(lookup(arr)));
    }

    private static int[] lookup(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 1) {
            throw new IllegalArgumentException("数组不能为空且长度必须大于0");
        }
        // 由题意可知，eor肯定不为0，只有有一位不相同
        int eor = 0;
        // 去除出现两次次数的数字，遍历后只剩余两个奇数
        for (int num : arr) {
            eor ^= num;
        }
        // 提取最右侧的1
        int rightOne = eor & (-eor);
        int eor2 = 0;
        for (int num : arr) {
            // 只异或最右侧等于1的数字
            // rightOne:  0000 0010 0000
            //      num:  0100 0010 0000
            if ((num & rightOne) == rightOne) {
                eor2 ^= num;
            }
        }

        return new int[]{eor2, eor ^ eor2};
    }
}
