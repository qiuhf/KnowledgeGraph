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

/**
 * <pre>
 *    怎么把一个int类型的数，提取出最右侧的1来
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-19
 **/
public class FindMoreRightOne {
    public static void main(String[] args) {
        int num = 7;
        System.out.println(num & (-num));
    }
}
