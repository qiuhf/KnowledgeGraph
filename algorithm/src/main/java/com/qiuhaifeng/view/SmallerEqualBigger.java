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

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 *     将单向链表按某值划分成左边小、中间相等、右边大的形式
 *      1）把链表放入数组里，在数组上做partition（笔试用）
 *      2）分成小、中、大三部分，再把各个部分之间串起来（面试用）
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-01
 **/
public class SmallerEqualBigger {
    public static void main(String[] args) {
        int maxDepth = 100;
        int range = 10_000;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            Node<Integer> head = AuxiliaryUtil.generateRandomNode(maxDepth, range);
            List<Integer> list = AuxiliaryUtil.nodeToList(head);
            Integer pivot = Math.random() > 0.5 || list.isEmpty() ? AuxiliaryUtil.randomNumber(range) :
                    list.get((int) (Math.random() * list.size()));
            Node<Integer> copyNode = AuxiliaryUtil.copyNode(head);

            List<Integer> except = AuxiliaryUtil.nodeToList(groupingByPartition(head, pivot));
            List<Integer> actual = AuxiliaryUtil.nodeToList(grouping(copyNode, pivot));
            for (int j = 0; j < except.size(); j++) {
                if (except.get(j).compareTo(pivot) != actual.get(j).compareTo(pivot)) {
                    System.err.printf(Locale.ROOT, "Original: %s, pivot: %d\n", list, pivot);
                    System.err.println("Actual: " + actual);
                    System.err.println("Except: " + except);
                    break;
                }
            }
        }
    }

    public static <T extends Comparable<T>> Node<T> grouping(Node<T> head, T pivot) {
        if (Objects.isNull(head)) {
            return null;
        }
        // 记录每个区间头尾[smallerHead, smallerTail, equalHead, equalTail, biggerHead, biggerTail]
        Node<T>[] nodes = new Node[6];
        Node<T> node;
        do {
            // 记录头节点下一个位置
            node = head.next;
            // 下一个节点置null, 得到单一节点
            head.next = null;
            if (pivot.compareTo(head.value) > 0) {
                append(nodes, head, 0, 1);
            } else if (pivot.compareTo(head.value) == 0) {
                append(nodes, head, 2, 3);
            } else {
                append(nodes, head, 4, 5);
            }
            head = node;
        } while (Objects.nonNull(head));
        // 确认头节点
        head = Objects.nonNull(nodes[0]) ? nodes[0] : Optional.ofNullable(nodes[2]).orElse(nodes[4]);
        // 如果小于区尾部不等于null
        // 等于区头部不等于null，将小于区尾部接到等于区头部
        // 等于区头部等于null，将小于区尾部接到大于区头部
        if (Objects.nonNull(nodes[1])) {
            nodes[1].next = Optional.ofNullable(nodes[2]).orElse(nodes[4]);
        }
        // 如果等于区尾部不等于null, 将等于区尾部接到大于区头部
        if (Objects.nonNull(nodes[3])) {
            nodes[3].next = nodes[4];
        }

        return head;
    }

    private static <T extends Comparable<T>> void append(Node<T>[] nodes, Node<T> cur, int head, int tail) {
        if (Objects.isNull(nodes[head])) {
            nodes[head] = cur;
            nodes[tail] = cur;
        } else {
            nodes[tail].next = cur;
            nodes[tail] = cur;
        }
    }

    /**
     * <p>把链表放入数组里，在数组上做partition</p>
     *
     * @param head  头节点
     * @param pivot 比较值
     * @param <T>   值类型
     * @return <code>Node</code> 分区后的头节点
     */
    public static <T extends Comparable<T>> Node<T> groupingByPartition(Node<T> head, T pivot) {
        if (Objects.isNull(head)) {
            return null;
        }
        // 获取helper数组长度
        int len = 0;
        Node cur = head;
        do {
            len++;
            cur = cur.next;
        } while (Objects.nonNull(cur));

        // 节点转换数组
        Node<T>[] helper = new Node[len];
        helper[0] = head;
        for (int i = 1; i < len; i++) {
            helper[i] = helper[i - 1].next;
        }
        // 分区
        partition(helper, 0, len, pivot);

        // 重新组装生成头节点
        for (int i = 1; i < len; i++) {
            // 只保留每个元素的头节点
            helper[i].next = null;
            helper[i - 1].next = helper[i];
        }
        return helper[0];
    }

    private static <T extends Comparable<T>> void partition(Node<T>[] nodes, int left, int right, T pivot) {
        int lIdx = left - 1;
        int rIdx = right;
        int cursor = left;
        while (cursor < rIdx) {
            if (pivot.compareTo(nodes[cursor].value) < 0) {
                swap(nodes, cursor, --rIdx);
            } else if (pivot.compareTo(nodes[cursor].value) == 0) {
                cursor++;
            } else {
                swap(nodes, cursor++, ++lIdx);
            }
        }
    }

    private static void swap(Object[] array, int cur, int other) {
        Object tmp = array[cur];
        array[cur] = array[other];
        array[other] = tmp;
    }
}
