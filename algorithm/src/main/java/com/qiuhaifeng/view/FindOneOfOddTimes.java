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

import java.util.Objects;

/**
 * <pre>
 *     一个数组中有一种数出现了奇数次，其他数都出现了偶数次，怎么找到并打印这种数
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-19
 **/
public class FindOneOfOddTimes {
    public static void main(String[] args) {
        int[] arr = {2, 2, 2, 3, 3};
        System.out.println(lookup(arr));
    }

    private static int lookup(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 1) {
            throw new IllegalArgumentException("数组不能为空且长度必须大于0");
        }
        int num = 0;
        for (int i : arr) {
            // 0^N == N      N^N == 0
            // 异或运算满足交换律和结合率
            num ^= i;
        }
        return num;
    }
}
