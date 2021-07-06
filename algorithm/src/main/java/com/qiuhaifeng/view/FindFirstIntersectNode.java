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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *      给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的 第一个节点。
 *  如果不相交，返回null
 * 【要求】
 *      如果两个链表长度之和为N，时间复杂度O(N)，额外空间复杂度O(1)。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-04
 **/
public class FindFirstIntersectNode {
    public static void main(String[] args) {
        int maxDepth = 200;
        int testTime = 10_000;
        for (int i = 0; i < testTime; i++) {
            Node[] nodes = generateRandomNodes(maxDepth);
            Node head1 = generateNode(nodes, maxDepth);
            Node head2 = generateNode(nodes, maxDepth);
            Node actual = getIntersectNode(head1, head2);
            Node except = useSet(head1, head2);
            if (except != actual) {
                System.out.printf("Head1: %s\nHead2: %s\n", toList(head1), toList(head2));
                System.err.printf("Actual: %s, Except: %s\n", (Objects.nonNull(actual) ? actual.value : null),
                        (Objects.nonNull(except) ? except.value : null));
                return;
            }
        }

        System.out.println("Nice!");
    }

    public static Node getIntersectNode(Node head1, Node head2) {
        // 获取入环节点
        Node entry1 = getEntryNode(head1);
        Node entry2 = getEntryNode(head2);
        // 都是无环的单链表
        if (Objects.isNull(entry1) && Objects.isNull(entry2)) {
            return bothNoRingList(head1, entry1, head2, entry2);
        }

        // 都是有环的单链表
        if (Objects.nonNull(entry1) && Objects.nonNull(entry2)) {
            return bothRingList(head1, entry1, head2, entry2);
        }

        // 一个单链表有环，另一个单链表无环，一定不存在相交点
        return null;
    }

    /**
     * <p>都有环情况下，获取第一个相交节点</p>
     *
     * @param head1  单链表1
     * @param entry1 单链表1入环节点
     * @param head2  单链表2
     * @param entry2 单链表2入环节点
     * @return <code>Node</code> null代表无相交节点
     */
    private static Node bothRingList(Node head1, Node entry1, Node head2, Node entry2) {
        // 入环节点相同，一定存在相交节点
        // 1. 第一个相交节点就是入环节点
        // 2. 第一个相交节点是入环节点之前的某个节点
        if (entry1 == entry2) {
            return bothNoRingList(head1, entry1, head2, entry2);
        }

        // 入环节点不相同
        // 1. 无相交节点
        // 2. 入环节点分别环上的不同位置，返回其中一个入环节点
        Node cur = entry1.next;
        do {
            if (cur == entry2) {
                // 与对数器保持一致，故此处返回entry2，实际返回entry1或entry2都可以
                return cur;
            }
            cur = cur.next;
            // entry1遍历一遍回到entry1节点，说明无相交点
        } while (cur != entry1);

        return null;
    }

    /**
     * <p>都无环情况下，获取第一个相交节点</p>
     *
     * @param head1  单链表1
     * @param entry1 单链表1入环节点
     * @param head2  单链表2
     * @param entry2 单链表2入环节点
     * @return <code>Node</code> 相交节点
     */
    private static Node bothNoRingList(Node head1, Node entry1, Node head2, Node entry2) {
        int len = 0;
        Node cur1 = head1;
        while (cur1 != entry1) {
            len++;
            cur1 = cur1.next;
        }

        Node cur2 = head2;
        while (cur2 != entry2) {
            len--;
            cur2 = cur2.next;
        }

        // len < 0，head2链表长
        // len == 0，head1和head2链表等长
        // len > 0，head1链表长
        cur1 = len < 0 ? head2 : head1;
        cur2 = cur1 == head2 ? head1 : head2;

        // 长链表先移动len步长
        len = Math.abs(len);
        while (len-- != 0) {
            cur1 = cur1.next;
        }

        // cur1和cur2等步长移动
        while (Objects.nonNull(cur1) && Objects.nonNull(cur2)) {
            // 如果节点相同，直接返回当前节点
            if (cur1 == cur2) {
                return cur1;
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }

        // 其中一个单链表遍历到尾部，说明无相交节点
        return null;
    }

    /**
     * <p>获取单链表第一个入环节点</p>
     *
     * @param head 头节点
     * @return <code>Node</code> null代表无入环节点
     */
    private static Node getEntryNode(Node head) {
        // 至少3个节点才可以形成环
        if (Objects.isNull(head) || Objects.isNull(head.next) || Objects.isNull(head.next.next)) {
            return null;
        }

        Node fast = head.next.next;
        Node slow = head.next;
        while (fast != slow) {
            // 快指针指向null, 说明单链表无环
            if (Objects.isNull(fast.next) || Objects.isNull(fast.next.next)) {
                return null;
            }
            fast = fast.next.next;
            slow = slow.next;
        }

        // 当快慢指针相交时，快指针回到头节点，与慢指针等步长移动
        fast = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }

        return fast;
    }

    public static Node useSet(Node head1, Node head2) {
        // 记录head1所有节点
        HashSet<Node> set1 = new HashSet<>();
        Node cur = head1;
        while (Objects.nonNull(cur) && !set1.contains(cur)) {
            set1.add(cur);
            cur = cur.next;
        }

        // 遍历head2，看set是否存在相交节点
        HashSet<Node> set2 = new HashSet<>();
        cur = head2;
        while (Objects.nonNull(cur) && !set2.contains(cur)) {
            if (set1.contains(cur)) {
                return cur;
            }
            set2.add(cur);
            cur = cur.next;
        }

        return null;
    }

    private static Node[] generateRandomNodes(int maxLen) {
        Node[] nodes = new Node[maxLen];
        for (int i = 0; i < maxLen; i++) {
            nodes[i] = new Node<>(i);
        }

        return nodes;
    }

    private static Node generateNode(Node[] nodes, int maxDepth) {
        int left = (int) (Math.random() * maxDepth);
        int right = Math.min((int) (Math.random() * maxDepth) + left, maxDepth);
        if (left == right) {
            return null;
        }

        for (int i = left + 1; i < right; i++) {
            if (Objects.nonNull(nodes[i].next)) {
                return nodes[left];
            }
            nodes[i - 1].next = nodes[i];
        }

        // 至少3个节点才形成环
        if (Math.random() > 0.2 && (right - left) > 2) {
            nodes[right - 1].next = nodes[(int) (Math.random() * (right - left - 2)) + left];
        }

        return nodes[left];
    }

    private static List toList(Node head) {
        if (Objects.isNull(head)) {
            return null;
        }

        List<Object> list = new ArrayList<>();
        Node node = head;
        while (Objects.nonNull(node) && !list.contains(node.value)) {
            list.add(node.value);
            node = node.next;
        }
        // 有环，记录入环节点, 无环添加null
        list.add(Objects.nonNull(node) ? node.value : null);
        return list;
    }
}
