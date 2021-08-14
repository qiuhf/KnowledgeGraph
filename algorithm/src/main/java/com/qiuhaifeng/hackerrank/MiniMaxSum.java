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

package com.qiuhaifeng.hackerrank;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *    Given five positive integers, find the minimum and maximum values that can be calculated by summing exactly four
 *  of the five integers. Then print the respective minimum and maximum values as a single line of two space-separated
 * long integers.
 *
 *  Example
 *      arr = [1, 3, 5, 7, 9]
 *  The minimum sum is 1 + 3 + 5 + 7 = 16 and the maximum sum is 3 + 5 + 7 + 9 = 24. The function prints
 *  16 24
 *
 *  Constraints
 *  1 < arr[i] <= 10^9
 *
 *  Hints: Beware of integer overflow! Use 64-bit Integer.
 * 来源：本题为hackerrank原题
 * 链接：https://www.hackerrank.com/challenges/one-week-preparation-kit-plus-minus/problem
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-14
 **/
public class MiniMaxSum {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(AuxiliaryUtil.randomPositiveNum((int) Math.pow(10, 9)));
        }
        System.out.printf(Locale.ROOT, "List: %s\nSize: %d\n", list, list.size());
        miniMaxSum(list);
    }

    /*
     * Complete the 'miniMaxSum' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
    public static void miniMaxSum(List<Integer> arr) {
        // Write your code here
        int max = -1;
        int min = Integer.MAX_VALUE;
        long sum = 0;
        for (Integer num : arr) {
            max = Math.max(max, num);
            min = Math.min(min, num);
            sum += num;
        }
        System.out.printf(Locale.ROOT, "%d %d", sum - max, sum - min);
    }

}
