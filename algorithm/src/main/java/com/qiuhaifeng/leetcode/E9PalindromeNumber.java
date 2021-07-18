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

import com.qiuhaifeng.datastructure.linkedlist.Node;
import com.qiuhaifeng.datastructure.linkedlist.view.PalindromeList;
import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Locale;

/**
 * <pre>
 *   回文数
 *      给你一个整数 x ，如果 x 是一个回文整数，返回 true ；否则，返回 false 。
 *     回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。例如，121 是回文，而 123 不是。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/palindrome-number
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-01
 **/
public class E9PalindromeNumber {
    public static void main(String[] args) {
        int time = 100_000;
        for (int i = 0; i < time; i++) {
            int x = AuxiliaryUtil.randomNumber(Integer.MAX_VALUE);
            boolean res = isPalindrome(x);
            boolean ans = logarithm(x);
            if (res != ans) {
                System.out.printf(Locale.ROOT, "Fucking num: %d, Actual: %s, Expect: %s", x, res, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }

        int revertedNum = 0;
        while (x > revertedNum) {
            revertedNum = revertedNum * 10 + x % 10;
            x /= 10;
        }

        return x == revertedNum || x == revertedNum / 10;
    }

    public static boolean logarithm(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }

        Node<Integer> head = new Node<>(x % 10);
        Node node = head;
        while ((x /= 10) != 0) {
            node.next = new Node<>(x % 10);
            node = node.next;
        }

        return PalindromeList.isPalindromeByStack(head);
    }
}
