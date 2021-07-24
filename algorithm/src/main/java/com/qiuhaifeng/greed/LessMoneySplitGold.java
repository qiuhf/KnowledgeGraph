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
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <pre>
 *     一块金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不管怎么切，都要花费20个铜板。
 * 一群人想整分整块金条，怎么分最省铜板?（不考虑顺序）
 *
 * 例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为60，金条要分成10，20，30三个部分。
 * 如果先把长度60的金条分成10和50，花费60; 再把长度50的金条分成20和30，花费50;一共花费110铜板。
 * 但如果先把长度60的金条分成30和30，花费60;再把长度30金条分成10和20， 花费30;一共花费90铜板。
 * 输入一个数组，返回分割的最小代价。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-23
 **/
public class LessMoneySplitGold {
    public static void main(String[] args) {
        int size = 6;
        int range = 20;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            int[] golds = AuxiliaryUtil.generateRandomArray(size, range, 1);
            int result = lessMoneySplitGold(golds);
            int ans = verify(golds);
            if (!Objects.equals(ans, result)) {
                System.err.println("Golds: " + Arrays.toString(golds));
                System.err.printf(Locale.ROOT, "Actual: %s, Expect: %s", result, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int lessMoneySplitGold(int[] golds) {
        if (Objects.isNull(golds) || golds.length < 2) {
            return 0;
        }
        // 小根堆
        Queue<Integer> queue = new PriorityQueue<>();
        Arrays.stream(golds).forEach(queue::add);

        int cost = 0;
        int cur;
        // 哈夫曼编码
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            cost += cur;
            queue.add(cur);
        }
        return cost;
    }

    private static int verify(int[] golds) {
        if (Objects.isNull(golds) || golds.length < 2) {
            return 0;
        }

        return process(golds, 0);
    }

    /**
     * <p>遍历获取最小的总代价</p>
     *
     * @param golds   待合并的金条
     * @param preCost 之前分割金条产生的费用
     * @return <code>int</code>
     */
    private static int process(int[] golds, int preCost) {
        int len = golds.length;
        if (golds.length == 1) {
            return preCost;
        }

        int cost = Integer.MAX_VALUE;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                // 列举所有可能，取最小值
                cost = Math.min(cost, process(copyAndMergeTwo(golds, i, j), preCost + golds[i] + golds[j]));
            }
        }
        return cost;
    }

    private static int[] copyAndMergeTwo(int[] golds, int i, int j) {
        int[] arr = new int[golds.length - 1];
        int index = 0;
        for (int p = 0; p < golds.length; p++) {
            if (p != i && p != j) {
                arr[index++] = golds[p];
            }
        }
        arr[index] = golds[i] + golds[j];
        return arr;
    }

}
