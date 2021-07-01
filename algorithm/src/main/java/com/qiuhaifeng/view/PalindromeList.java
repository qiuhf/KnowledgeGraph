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

package com.qiuhaifeng.view;

import com.qiuhaifeng.datastructure.linkedlist.Node;
import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 *   给定一个单链表的头节点head，请判断该链表是否为回文结构。
 *      1）栈方法特别简单（笔试用）
 *      2）改原链表的方法就需要注意边界了（面试用）
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-29
 **/
public class PalindromeList {
    /**
     * <pre>
     *     通过栈方式判断该链表是否为回文结构，额外空间复杂度：O(N)
     * </pre>
     *
     * @param head 头节点
     * @return <code>boolean</code>
     */
    public static <T> boolean isPalindromeByStack(Node<T> head) {
        Stack<Node<T>> stack = new Stack<>();
        Node<T> node = head;
        // 链表数据进栈
        while (Objects.nonNull(node)) {
            stack.push(node);
            node = node.next;
        }

        while (Objects.nonNull(head)) {
            if (!Objects.equals(stack.pop().value, head.value)) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * <pre>
     *     通过栈方式判断该链表是否为回文结构，额外空间复杂度：O(N/2)
     * </pre>
     *
     * @param head 头节点
     * @return <code>boolean</code>
     */
    public static <T> boolean isPalindromeByHalfStack(Node<T> head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return true;
        }

        Node<T> mid = head.next;
        Node<T> fast = head;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            mid = mid.next;
            fast = fast.next.next;
        }
        Stack<Node<T>> stack = new Stack<>();
        // 将链表中或下中点的数据进栈
        while (Objects.nonNull(mid)) {
            stack.push(mid);
            mid = mid.next;
        }

        while (!stack.isEmpty()) {
            if (!Objects.equals(stack.pop().value, head.value)) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * <pre>
     *     通过改原链表的方法判断该链表是否为回文结构，额外空间复杂度：O(1)
     * </pre>
     *
     * @param head 头节点
     * @return <code>boolean</code>
     */
    public static <T> boolean isPalindrome(Node<T> head) {
        if (Objects.isNull(head)) {
            return true;
        }
        Node<T> slow = head;
        Node<T> fast = head;
        // 获取链表中点或下中点
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }
        // 记录中点位置的下一个节点
        fast = slow.next;
        // 中点下一个节点指向null
        slow.next = null;
        Node<T> tmp = null;
        // 第一次循环： 1 -> 2 -> 3 -> null | 3 <- 2 -> 1
        // 第一次循环： 1 -> 2 -> 3 -> null | 3 <- 2 <- 1
        // 循环结束，slow就是后半段的新头节点，如 3 <- 2 <- 1
        while (Objects.nonNull(fast)) {
            // 1 -> 2 -> 3 -> 2 -> 1 -> null
            //           s -> f -> t -> null
            //           s <- s    f    t
            tmp = fast.next;
            //           s <- f    t -> null
            //           s <- s <- f    t
            fast.next = slow;
            // 移动到下一个节点
            slow = fast;
            fast = tmp;
        }
        // 是否为回文标记，不能直接返回，需将后半截节点复原后再返回检查结果
        boolean ans = true;
        tmp = slow;
        fast = head;
        while (Objects.nonNull(fast) && Objects.nonNull(tmp)) {
            if (!Objects.equals(fast.value, tmp.value)) {
                ans = false;
                break;
            }
            tmp = tmp.next;
            fast = fast.next;
        }

        fast = slow.next;
        slow.next = null;
        // 复原原始节点
        while (Objects.nonNull(fast)) {
            tmp = fast.next;
            fast.next = slow;
            slow = fast;
            fast = tmp;
        }
        return ans;
    }

    public static void main(String[] args) {
        Node<Integer> head = new Node<>(0);
        Node<Integer> node = head;
        int size = 8;
        for (int i = 1, v = size / 2; i < size; i++) {
            node.next = new Node<>(i >= (size / 2) ? --v : i);
            node = node.next;
        }
        System.out.println(nodeToList(head));
        System.out.println(isPalindromeByStack(head));
        System.out.println(isPalindromeByHalfStack(head));
        System.out.println(isPalindrome(head));

        int testTime = 10_000;
        for (int i = 0; i < testTime; i++) {
            Node<Integer> randomHeadNode = generateRandomNode();
            if (isPalindromeByStack(randomHeadNode) != isPalindrome(randomHeadNode)) {
                System.out.printf(Locale.ROOT, "Oops isPalindrome！Node: %s", nodeToList(head));
                return;
            }
            if (isPalindromeByStack(randomHeadNode) != isPalindromeByHalfStack(randomHeadNode)) {
                System.out.printf(Locale.ROOT, "Oops isPalindromeByHalfStack！Node: %s", nodeToList(head));
                return;
            }
        }
    }

    private static Node<Integer> generateRandomNode() {
        int num = AuxiliaryUtil.randomPositiveNum(100);
        Node<Integer> head = null;
        if (num != 0) {
            head = new Node<>(0);
        }
        Node node = head;
        while ((num >> 1) > 0) {
            node.next = new Node<>(num & 1);
            node = node.next;
            num >>= 1;
        }

        return head;
    }

    private static <T> List<T> nodeToList(Node<T> head) {
        Node<T> node = head;
        List<T> list = new ArrayList<>();
        while (Objects.nonNull(node)) {
            list.add(node.value);
            node = node.next;
        }
        return list;
    }
}
