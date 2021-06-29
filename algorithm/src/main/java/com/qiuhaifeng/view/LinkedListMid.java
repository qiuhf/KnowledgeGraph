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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * <pre>
 *  单链表快慢指针练习：
 *      1）输入链表头节点，奇数长度返回中点，偶数长度返回上中点
 *      2）输入链表头节点，奇数长度返回中点，偶数长度返回下中点
 *      3）输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个
 *      4）输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-28
 **/
public class LinkedListMid {
    public static void main(String[] args) {
        for (int i = 0; i < 10_000; i++) {
            Node<Integer> head = generateRandomNode();
            Node node = head;
            List<Node> list = new ArrayList<>();
            while (Objects.nonNull(node)) {
                list.add(node);
                node = node.next;
            }
            int size = list.size();

            checkResult(head, list, size);
        }
        System.out.println("Nice");
    }

    private static void checkResult(Node<Integer> head, List<Node> list, int size) {
        Node ans1 = Objects.isNull(head) ? null : (size & 1) == 1 ? list.get(size / 2) : list.get(size / 2 - 1);
        Node res1 = findMidOrUpMidNode(head);
        if (!Objects.equals(ans1, res1)) {
            System.err.printf(Locale.ROOT, "输入链表头节点，奇数长度返回中点，偶数长度返回上中点. Actual: %s, Expect: %s\n",
                    Objects.isNull(res1) ? null : res1.value, Objects.isNull(ans1) ? null : ans1.value);
        }

        Node ans2 = Objects.isNull(head) ? null : (size & 1) == 1 ? list.get(size / 2) : list.get(size / 2);
        Node res2 = findMidOrDownMidNode(head);
        if (!Objects.equals(ans2, res2)) {
            System.err.printf(Locale.ROOT, "输入链表头节点，奇数长度返回中点，偶数长度返回下中点. Actual: %s, Expect: %s\n",
                    Objects.isNull(res2) ? null : res2.value, Objects.isNull(ans2) ? null : ans2.value);
        }

        Node ans3 = Objects.isNull(head) || size < 3 ? null : (size & 1) == 1 ? list.get(size / 2 - 1) :
                list.get(size / 2 - 2);
        Node res3 = findUpMidPreNode(head);
        if (!Objects.equals(ans3, res3)) {
            System.err.printf(Locale.ROOT, "输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个. Actual: %s, Expect: %s\n",
                    Objects.isNull(res3) ? null : res3.value, Objects.isNull(ans3) ? null : ans3.value);
        }

        Node ans4 = Objects.isNull(head) || size < 2 ? null : (size & 1) == 1 ? list.get(size / 2 - 1) :
                list.get(size / 2 - 1);
        Node res4 = findDownMidPreNode(head);
        if (!Objects.equals(ans4, res4)) {
            System.err.printf(Locale.ROOT, "输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个. Actual: %s, Expect: %s\n",
                    Objects.isNull(res4) ? null : res4.value, Objects.isNull(ans4) ? null : ans4.value);
        }
    }

    private static Node<Integer> generateRandomNode() {
        Node<Integer> head = null;
        int num = (int) (Math.random() * 10);
        if (num != 0) {
            head = new Node<>(0);
        }
        Node node = head;
        for (int i = 1; i < num; i++) {
            node.next = new Node<>(i);
            node = node.next;
        }
        return head;
    }

    public static class Node<T> {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Node.class.getSimpleName() + "[", "]")
                    .add("value=" + value)
                    .toString();
        }
    }

    /**
     * <p>输入链表头节点，奇数长度返回中点，偶数长度返回上中点</p>
     *
     * @param <T>  值类型
     * @param head 头节点
     * @return <code>Node</code> 上中节点
     */
    public static <T> Node findMidOrUpMidNode(Node<T> head) {
        if (Objects.isNull(head) || Objects.isNull(head.next) || Objects.isNull(head.next.next)) {
            return head;
        }

        // 链表至少有3个节点
        Node slow = head.next;
        Node fast = head.next.next;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * <p>输入链表头节点，奇数长度返回中点，偶数长度返回下中点</p>
     *
     * @param <T>  值类型
     * @param head 头节点
     * @return <code>Node</code> 上中节点
     */
    public static <T> Node findMidOrDownMidNode(Node<T> head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return head;
        }

        Node slow = head.next;
        Node fast = head.next;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * <p>输入链表头节点，奇数长度返回中点前一个，偶数长度返回上中点前一个</p>
     *
     * @param <T>  值类型
     * @param head 头节点
     * @return <code>Node</code> 上中节点
     */
    public static <T> Node findUpMidPreNode(Node<T> head) {
        if (Objects.isNull(head) || Objects.isNull(head.next) || Objects.isNull(head.next.next)) {
            return null;
        }
        // 链表至少有4个节点
        Node slow = head;
        Node fast = head.next.next;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * <p>输入链表头节点，奇数长度返回中点前一个，偶数长度返回下中点前一个</p>
     *
     * @param <T>  值类型
     * @param head 头节点
     * @return <code>Node</code> 上中节点
     */
    public static <T> Node findDownMidPreNode(Node<T> head) {
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return null;
        }

        if (Objects.isNull(head.next.next)) {
            return head;
        }

        // 链表至少有4个节点
        Node slow = head;
        Node fast = head.next;
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next)) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
