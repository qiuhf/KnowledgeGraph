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

import java.util.Objects;

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
    public static class Node<T> {
        private T value;
        private Node next;

        public Node(T value) {
            this.value = value;
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
        while (Objects.nonNull(fast.next) && Objects.nonNull(fast.next.next) ) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
