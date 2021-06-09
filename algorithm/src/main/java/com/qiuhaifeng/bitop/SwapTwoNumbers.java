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

package com.qiuhaifeng.bitop;

import java.util.Locale;

/**
 * <pre>
 *   如何不用额外变量交换两个数
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-19
 **/
public class SwapTwoNumbers {
    public static void main(String[] args) {
        int a = 9;
        int b = 7;
        System.out.format(Locale.ROOT, "a = %d, b = %d\n", a, b);
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.format(Locale.ROOT, "a = %d, b = %d\n", a, b);

        int[] arr = {3, 1, 100};
        int i = 0;
        int j = 0;
        System.out.format(Locale.ROOT, "arr[%d] = %d, arr[%d] = %d\n", i, arr[i], j, arr[j]);
        arr[i] = arr[i] ^ arr[j];
        arr[j] = arr[i] ^ arr[j];
        arr[i] = arr[i] ^ arr[j];
        System.out.format(Locale.ROOT, "arr[%d] = %d, arr[%d] = %d\n", i, arr[i], j, arr[j]);
    }
}
