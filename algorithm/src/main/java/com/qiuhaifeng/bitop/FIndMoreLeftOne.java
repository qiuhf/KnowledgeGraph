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

package com.qiuhaifeng.bitop;

import com.qiuhaifeng.util.AuxiliaryUtil;

/**
 * <pre>
 *    怎么把一个int类型的数，提取出最左侧的1来
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-20
 **/
public class FIndMoreLeftOne {
    public static void main(String[] args) {
        int num = AuxiliaryUtil.randomNumber(20);
        System.out.println("num = " + num);
        while ((num & (num - 1)) != 0) {
            num &= num - 1;
        }

        System.out.println("More left one = " + num);
    }
}
