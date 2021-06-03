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
        System.out.println("a = " + a + ", b = " + b);
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("a = " + a + ", b = " + b);
    }
}
