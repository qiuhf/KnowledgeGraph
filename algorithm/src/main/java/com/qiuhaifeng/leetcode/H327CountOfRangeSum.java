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

import java.util.Objects;

/**
 * <pre>
 * 给你一个整数数组nums以及两个整数lower和upper 。求数组中，值位于范围[lower, upper]之内的区间和的个数。
 * 区间和 S(i, j) 表示在 nums 中，位置从 i 到 j 的元素之和，包含 i 和 j (i ≤ j)。
 *
 * 示例 1：
 * 输入：nums = [-2,5,-1], lower = -2, upper = 2
 * 输出：3
 * 解释：存在三个区间：[0,0]、[2,2] 和 [0,2] ，对应的区间和分别是：-2 、-1 、2 。
 *
 * 示例 2：
 * 输入：nums = [0], lower = 0, upper = 0
 * 输出：1
 *  
 *
 * 提示：
 * 1 <= nums.length <= 100_000
 * -2,147,483,648 <= nums[i] <= 2,147,483,647
 * -100_000 <= lower <= upper <= 100_000
 * 题目数据保证答案是一个 32 位 的整数
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/count-of-range-sum
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-15
 **/
public class H327CountOfRangeSum {
    public static void main(String[] args) {
        H327CountOfRangeSum countOfRangeSum = new H327CountOfRangeSum();
        System.out.println(countOfRangeSum.countRangeSum(new int[]{-2, 5, -1}, -2, 2) == 3);
        System.out.println(countOfRangeSum.countRangeSum(new int[]{-2147483647, 0, -2147483647, 2147483647}, -564,
                3864) == 3);
    }

    /**
     * <p>统计位于范围 [lower, upper]之内的区间和的个数</p>
     *
     * @param nums  整数数组
     * @param lower 下限
     * @param upper 上限
     * @return <code>int</code>
     */
    public int countRangeSum(int[] nums, int lower, int upper) {
        if (Objects.isNull(nums) || nums.length == 0) {
            return -1;
        }

        if (lower > upper) {
            throw new IllegalArgumentException("Lower value less than upper value");
        }

        // 前缀和数组, 必须用long
        int length = nums.length;
        long[] preSum = new long[length];
        preSum[0] = nums[0];
        for (int i = 1; i < length; i++) {
            preSum[i] = preSum[i - 1] + nums[i];
        }
        return this.process(preSum, 0, length - 1, lower, upper);
    }

    private int process(long[] preSum, int left, int right, int lower, int upper) {
        // base case
        if (left == right) {
            return preSum[left] >= lower && preSum[left] <= upper ? 1 : 0;
        }

        int mid = left + ((right - left) >> 1);
        return this.process(preSum, left, mid, lower, upper) + this.process(preSum, mid + 1, right, lower, upper)
                + this.merge(preSum, left, mid, right, lower, upper);
    }

    private int merge(long[] preSum, int left, int mid, int right, int lower, int upper) {
        int ans = 0;
        int windowL = left;
        int windowR = left;
        // -2 3 2   [-2, 2]  mid=1, r=2, l=0;
        for (int i = mid + 1; i <= right; i++) {
            long mix = preSum[i] - upper;
            while (windowL <= mid && preSum[windowL] < mix) {
                windowL++;
            }
            long max = preSum[i] - lower;
            while (windowR <= mid && preSum[windowR] <= max) {
                windowR++;
            }
            ans += windowR - windowL;
        }

        long[] helper = new long[right - left + 1];
        int index = 0;
        int lp = left;
        int rp = mid + 1;
        while (lp <= mid && rp <= right) {
            helper[index++] = preSum[lp] < preSum[rp] ? preSum[lp++] : preSum[rp++];
        }

        while (lp <= mid) {
            helper[index++] = preSum[lp++];
        }

        while (rp <= right) {
            helper[index++] = preSum[rp++];
        }

        for (int i = 0; i < helper.length; i++) {
            preSum[i + left] = helper[i];
        }

        return ans;
    }
}
