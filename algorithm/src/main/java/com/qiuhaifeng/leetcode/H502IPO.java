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

package com.qiuhaifeng.leetcode;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * <pre>
 *     假设 即将开始其 IPO。为了以更高的价格将股票卖给风险投资公司，力扣 希望在 IPO 之前开展一些项目以增加其资本。 由于资源有限，
 * 它只能在IPO之前完成最多 k 个不同的项目。帮助设计完成最多k个不同项目后得到最大总资本的方式。
 *
 *    给定若干个项目。对于每个项目i，它都有一个纯利润 Pi，并且需要最小的资本 Ci 来启动相应的项目。最初，你有 W 资本。当你完成一个
 * 项目时，你将获得纯利润，且利润将被添加到你的总资本中。总而言之，从给定项目中选择最多 k 个不同项目的列表，以最大化最终资本，并输出
 * 最终可获得的最多资本。
 *
 * 示例：
 *   输入：k=2, W=0, Profits=[1,2,3], Capital=[0,1,1].
 *   输出：4
 *   解释：
 *     由于你的初始资本为 0，你仅可以从 0 号项目开始。
 *     在完成后，你将获得 1 的利润，你的总资本将变为 1。
 *     此时你可以选择开始 1 号或 2 号项目。
 *     由于你最多可以选择两个项目，所以你需要完成 2 号项目以获得最大的资本。
 *   因此，输出最后最大化的资本，为 0 + 1 + 3 = 4。
 *  
 * 提示：
 *   假设所有输入数字都是非负整数。
 *   表示利润和资本的数组的长度不超过 50000。
 *   答案保证在 32 位有符号整数范围内
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/ipo
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-23
 **/
public class H502IPO {
    public static void main(String[] args) {
        int maxK = 6;
        int maxW = 2000;
        int range = 50000;
        int maxVale = 100_000;
        int time = 100_000;
        for (int i = 0; i < time; i++) {
            int k = (int) (Math.random() * (maxK + 1));
            int w = (int) (Math.random() * (maxW + 1));
            int len = (int) (Math.random() * (range + 1));
            int[] profits = generateArray(len, maxVale);
            int[] capital = generateArray(len, maxVale);
            int res = findMaximizedCapital(k, w, profits, capital);
            int ans = logarithm(k, w, profits, capital);
            if (res != ans) {
                System.out.printf(Locale.ROOT, "Fucking! Actual: %s, Expect: %s", res, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    /**
     * @param k       只能串行的最多做k个项目
     * @param w       初始资金
     * @param profits 每个项目在扣除花费之后还能挣到的钱(利润)
     * @param capital 每个项目回报
     * @return <code>int</code> 最大利润
     */
    public static int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        if (k == 0) {
            return w;
        }

        // 小根堆，投入资金最少排前
        Queue<Project> projects = new PriorityQueue<>(Comparator.comparingInt(p -> p.capital));
        for (int i = 0; i < profits.length; i++) {
            projects.add(new Project(profits[i], capital[i]));
        }

        // 大根堆，收益最多的排前
        Queue<Project> priorityQueue = new PriorityQueue<>((p1, p2) -> p2.profits - p1.profits);
        for (int i = 0; i < k; i++) {
            // 把初始资金允许项目放到priorityQueue
            while (!projects.isEmpty() && projects.peek().capital <= w) {
                priorityQueue.add(projects.poll());
            }

            if (priorityQueue.isEmpty()) {
                return w;
            }

            w += priorityQueue.poll().profits;
        }

        return w;
    }

    private static int logarithm(int k, int w, int[] profits, int[] capital) {
        if (k == 0 || w < 0 || profits.length == 0) {
            return w;
        }

        HashSet<Integer> doneSet = new HashSet<>(k);
        for (int j = 0; j < k; j++) {
            int max = -1;
            int index = -1;
            for (int i = 0; i < capital.length; i++) {
                // 遍历获取利润最多的项目
                if (capital[i] <= w && profits[i] > max && !doneSet.contains(i)) {
                    index = i;
                    max = profits[i];
                }
            }

            if (index == -1) {
                return w;
            }

            w += max;
            // 记录已做过的项目
            doneSet.add(index);
        }
        return w;
    }

    private static int[] generateArray(int len, int maxValue) {
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = (int) (Math.random() * maxValue);
        }
        return arr;
    }

    static class Project {
        /**
         * 利润
         */
        int profits;
        /**
         * 资本
         */
        int capital;

        public Project(int profits, int capital) {
            this.profits = profits;
            this.capital = capital;
        }
    }
}
