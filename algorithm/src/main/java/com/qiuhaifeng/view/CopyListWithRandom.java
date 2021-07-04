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

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *   一种特殊的单链表节点类描述如下
 *      class Node {
 *          int value;
 *          Node next;
 *          Node rand;
 *          Node(int val) { value = val; }
 *      }
 *   rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null。
 *   给定一个由Node节点类型组成的无环单链表的头节点 head，请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
 *  【要求】
 *      时间复杂度O(N)，额外空间复杂度O(1)
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-03
 **/
public class CopyListWithRandom {
    public static void main(String[] args) {
        int maxDepth = 100;
        int range = 1_000;
        int testTime = 10_000;
        for (int i = 0; i < testTime; i++) {
            Node<Integer> head = generateRandomNode(maxDepth, range);
            String headStr = toString(head);
            String head1 = toString(useMapCopy(head));
            if (!headStr.equals(head1)) {
                System.err.printf(Locale.ROOT, "CopyByMap failed! \nActual: %s\nExcept: %s\n", head1, headStr);
                return;
            }

            String head2 = toString(copy(head));
            if (!headStr.equals(head2)) {
                System.err.printf(Locale.ROOT, "Copy failed! \nActual: %s\nExcept: %s\n", head2, headStr);
                return;
            }
        }
        System.out.println("Nice!");
    }

    /**
     * <p>时间复杂度O(N)，额外空间复杂度O(1)</p>
     *
     * @param head 头节点
     * @param <T>  类型
     * @return <code>Node<T></code>
     */
    public static <T> Node<T> copy(Node<T> head) {
        Node<T> cur = head;
        Node<T> next;
        // 在每个旧节点后面新增一个新节点
        while (Objects.nonNull(cur)) {
            next = cur.next;
            cur.next = new Node<>(cur.value);
            cur.next.next = next;
            cur = next;
        }

        cur = head;
        // 复制每个旧节点的rand指针给新节点
        while (Objects.nonNull(cur)) {
            // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
            // c          n
            next = cur.next.next;
            cur.next.rand = Objects.nonNull(cur.rand) ? cur.rand.next : null;
            cur = next;
        }

        Node<T> copyNode = Objects.nonNull(head) ? head.next : null;
        Node<T> node = copyNode;
        cur = head;
        // 复制每个旧节点的next指针给新节点, 并分离新旧节点
        while (Objects.nonNull(node) && Objects.nonNull(node.next)) {
            // 1 -> 1' -> 2 -> 2' -> 3 -> 3'
            // c          n
            next = cur.next.next;
            node.next = node.next.next;
            cur.next = cur.next.next;
            cur = next;
            node = next.next;
        }

        return copyNode;
    }

    /**
     * <p>时间复杂度O(N)，额外空间复杂度O(N)</p>
     *
     * @param head 头节点
     * @param <T>  类型
     * @return <code>Node<T></code>
     */
    public static <T> Node<T> useMapCopy(Node<T> head) {
        // 数据结构<旧节点，新节点>
        Map<Node<T>, Node<T>> nodeMap = new HashMap<>();
        Node<T> cur = head;
        while (Objects.nonNull(cur)) {
            nodeMap.put(cur, new Node<>(cur.value));
            cur = cur.next;
        }

        cur = head;
        while (Objects.nonNull(cur)) {
            Node<T> newHead = nodeMap.get(cur);
            newHead.next = nodeMap.get(cur.next);
            newHead.rand = nodeMap.get(cur.rand);
            cur = cur.next;
        }

        return nodeMap.get(head);
    }


    private static <T> String toString(Node<T> head) {
        StringBuilder next = new StringBuilder("\nnext: ");
        Node<T> cur = head;
        while (Objects.nonNull(cur)) {
            next.append(cur.value).append(" ");
            cur = cur.next;
        }

        StringBuilder rand = new StringBuilder("rand: ");
        cur = head;
        while (Objects.nonNull(cur)) {
            rand.append(Objects.nonNull(cur.rand) ? cur.rand.value : null).append(" ");
            cur = cur.next;
        }

        return next.append(System.lineSeparator()).append(rand).toString();
    }

    private static Node<Integer> generateRandomNode(int maxDepth, int range) {
        int depth = (int) (Math.random() * (maxDepth + 1));
        if (depth == 0) {
            return null;
        }
        List<Node<Integer>> list = new ArrayList<>(depth);
        List<Integer> index = new ArrayList<>(depth);
        for (int i = 0; i < depth; i++) {
            index.add(i);
            list.add(new Node<>(AuxiliaryUtil.randomNumber(range)));
        }

        Node<Integer> head = list.get(0);
        Node<Integer> cur = head;
        for (int i = 1; i < depth; i++) {
            cur.next = list.get(i);
            int randomIdx = (int) (Math.random() * depth);
            cur.rand = randomIdx == i ? null : list.get(randomIdx);
            cur = cur.next;
        }

        return head;
    }
}

class Node<T> {
    T value;
    Node<T> next;
    /**
     * rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，也可能指向null
     */
    Node<T> rand;

    Node(T value) {
        this.value = value;
    }
}
