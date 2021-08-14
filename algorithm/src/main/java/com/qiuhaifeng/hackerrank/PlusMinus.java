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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <pre>
 *     Given an array of integers, calculate the ratios of its elements that are positive, negative, and zero. Print
 * the decimal value of each fraction on a new line with  places after the decimal.
 *
 * Note: This challenge introduces precision problems. The test cases are scaled to six decimal places, though answers
 * with absolute error of up to  are acceptable.
 *
 * Example
 * arr = [1, 1, 0, -1, -1]
 * There are n = 5 elements, two positive, two negative and one zero. Their ratios are 2/5, 2/5 and 1/5. Results are
 * printed as:
 *      0.400000
 *      0.400000
 *      0.200000
 *
 * Function Description
 * Print the ratios of positive, negative and zero values in the array. Each value should be printed on a separate line
 * with  digits after the decimal. The function should not return a value.
 *
 * Input Format
 *
 * The first line contains an integer, , the size of the array.
 * The second line contains  space-separated integers that describe .
 *
 * Constraints
 * 0 < n <= 100
 * -100 < arr[i] <= 100
 *
 *
 * 来源：本题为hackerrank原题
 * 链接：https://www.hackerrank.com/challenges/one-week-preparation-kit-plus-minus/problem
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-14
 **/
public class PlusMinus {
    public static void main(String[] args) {
        int[] ints = AuxiliaryUtil.generateRandomArray(100, 100);
        ArrayList<Integer> list = new ArrayList<>();
        for (int anInt : ints) {
            list.add(anInt);
        }
        System.out.printf(Locale.ROOT, "List: %s\nSize: %d\n", list, list.size());
        plusMinus(list);
    }

    /*
     * Complete the 'plusMinus' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */
    public static void plusMinus(List<Integer> arr) {
        // Write your code here
        int size = arr.size();
        double zero = 0;
        double positive = 0;
        for (Integer num : arr) {
            if (num == 0) {
                zero++;
                continue;
            }
            if (num > 0) {
                positive++;
            }
        }
        // 指定保留小数点后几位
        DecimalFormat decimalFormat = new DecimalFormat("#0.000000");
        System.out.println(decimalFormat.format(positive / size));
        System.out.println(decimalFormat.format((size - positive - zero) / size));
        System.out.println(decimalFormat.format(zero / size));
    }

}
