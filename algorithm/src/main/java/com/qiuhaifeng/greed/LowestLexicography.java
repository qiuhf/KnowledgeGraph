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

package com.qiuhaifeng.greed;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.TreeSet;

/**
 * <pre>
 *      给定一个由字符串组成的数组arr， 必须把所有的字符串拼接起来，返回所有可能的拼接结果中，字典序最小的结果
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-21
 **/
public class LowestLexicography {
    public static void main(String[] args) {
        int maxLen = 5;
        int range = 12;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = AuxiliaryUtil.generateRandomStringArray(range, maxLen);
            String result = lowestString(AuxiliaryUtil.copyArray(arr));
            String ans = verify(AuxiliaryUtil.copyArray(arr));
            if (!Objects.equals(ans, result)) {
                System.err.println("Oops! String[] arr = " + Arrays.toString(arr));
                System.err.printf(Locale.ROOT, "Actual: %s, Expect: %s", result, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static String lowestString(String[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return null;
        }

        Arrays.sort(arr, (str1, str2) -> (str1 + str2).compareTo(str2 + str1));

        StringBuilder builder = new StringBuilder();
        Arrays.stream(arr).forEach(builder::append);

        return builder.toString();
    }

    private static String verify(String[] arr) {
        if (Objects.isNull(arr) || arr.length == 0) {
            return null;
        }

        TreeSet<String> treeSet = new TreeSet<>();
        process(arr, 0, treeSet);
        return treeSet.first();
    }

    private static void process(String[] arr, int begin, TreeSet<String> treeSet) {
        // 如果已经到了数组的最后一个元素，前面的元素已经排好，输出。
        if (begin == arr.length - 1) {
            StringBuilder builder = new StringBuilder();
            Arrays.stream(arr).forEach(builder::append);
            treeSet.add(builder.toString());
        }

        for (int i = begin; i <= arr.length - 1; i++) {
            // 把第一个元素分别与后面的元素进行交换
            AuxiliaryUtil.swap(arr, i, begin);
            // 递归的调用其子数组进行排序
            process(arr, begin + 1, treeSet);
            // 子数组排序返回后要将第一个元素交换回来。
            // 如果不交换回来会出错，比如说第一次1、2交换，第一个位置为2，子数组排序返回后如果不将1、2
            // 交换回来第二次交换的时候就会将2、3交换，因此必须将1、2交换使1还是在第一个位置
            AuxiliaryUtil.swap(arr, i, begin);
        }
    }
}
