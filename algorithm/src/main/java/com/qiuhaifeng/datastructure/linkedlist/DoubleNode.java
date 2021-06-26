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

package com.qiuhaifeng.datastructure.linkedlist;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>双链表</pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
@Getter
@Setter
public class DoubleNode<T> {
    private T value;
    private DoubleNode<T> prev;
    private DoubleNode<T> next;

    public DoubleNode() {
    }

    public DoubleNode(T value) {
        this.value = value;
    }

    /**
     * <p>移除指定数据的节点</p>
     *
     * @param value value
     * @return <code>Node</code> 新头节点
     */
    public Optional<DoubleNode<T>> removeValue(T value) {
        DoubleNode<T> head = this;
        // 确定头节点
        do {
            head.prev = null;
            if (head.value != value) {
                break;
            }
            head = head.next;
        } while (Objects.nonNull(head));

        // 如果都等于指定值，返回空
        if (Objects.isNull(head)) {
            return Optional.empty();
        }

        // 下一个待比较的节点
        DoubleNode<T> checkpoint = head.next;
        DoubleNode<T> cur = head;
        // 2 3 2 3 2 “2”
        // 3 2 3 2
        while (Objects.nonNull(checkpoint)) {
            if (checkpoint.value != value) {
                // 指向下一个节点
                cur = checkpoint;
            } else {
                // 当前节点next指向下一个节点next
                cur.next = checkpoint.next;
                // 如果下一个节点为null, 说明到末尾，直接跳出循环
                if (Objects.isNull(checkpoint.next)) {
                    break;
                }
                checkpoint.next.prev = cur;
            }
            checkpoint = cur.next;
        }

        return Optional.of(head);
    }

    /**
     * <p>双链表反转</p>
     *
     * @return <code>Node</code>
     */
    public Optional<DoubleNode<T>> reverse() {
        DoubleNode<T> head = this;
        DoubleNode<T> next;
        DoubleNode<T> cur = null;
        // a <-> b <-> c <-> null
        // c <-> b <-> a <-> null
        while (Objects.nonNull(head)) {
            // b | c | null
            next = head.next;
            // b <- a -> null | c <- b -> a | null <- c -> b
            head.next = cur;
            head.prev = next;
            // a | b | c
            cur = head;
            // b | c | null
            head = next;
        }

        return Optional.of(cur);
    }

    // for test

    public static void main(String[] args) {
        int maxDepth = 8;
        int range = 10;
        int testTime = 10_000;
        for (int i = 0; i < testTime; i++) {
            generateRandomDoubleNode(maxDepth, range).ifPresent(DoubleNode::verifyDoubleNodeReverse);
            generateRandomDoubleNode(maxDepth, range).ifPresent(DoubleNode::verifyNodeRemove);
        }
    }

    /**
     * <p>验证反转结果是否正确</p>
     *
     * @param doubleNode DoubleNode
     */
    private static void verifyDoubleNodeReverse(DoubleNode<Integer> doubleNode) {
        Optional<List<DoubleNode<Integer>>> listOp = toNodeList(doubleNode);
        Optional<DoubleNode<Integer>> nodesOp = doubleNode.reverse();

        if (!listOp.isPresent() && !nodesOp.isPresent()) {
            return;
        }

        if (listOp.isPresent() && nodesOp.isPresent()) {
            List<DoubleNode<Integer>> doubleNodeList = listOp.get();
            DoubleNode<Integer> reverseDoubleNode = nodesOp.get();
            int size = doubleNodeList.size();
            for (int i = size; i > 0; i--) {
                if (!Objects.equals(doubleNodeList.get(i - 1), reverseDoubleNode)) {
                    System.err.format(Locale.ROOT, "Expect: %s\nActual: %s\n", listOp, toNodeList(reverseDoubleNode));
                    return;
                }
                reverseDoubleNode = reverseDoubleNode.next;
            }
        }
    }

    /**
     * <p>验证移除指定数据的节点结果是否正确</p>
     *
     * @param doubleNodes Node
     */
    private static void verifyNodeRemove(DoubleNode<Integer> doubleNodes) {
        Integer removeValue = getRandomKind(doubleNodes);
        Optional<List<Integer>> listOp = deleteValue(doubleNodes, removeValue);
        Optional<DoubleNode<Integer>> nodesOp = doubleNodes.removeValue(removeValue);
        if (!listOp.isPresent() && !nodesOp.isPresent()) {
            return;
        }

        if (listOp.isPresent() && nodesOp.isPresent()) {
            List<Integer> list = listOp.get();
            DoubleNode<Integer> doubleNode = nodesOp.get();
            for (Integer value : list) {
                if (!Objects.equals(value, doubleNode.value)) {
                    System.err.format(Locale.ROOT, "Remove value: %d\nExpect: %s\nActual: %s\n", removeValue,
                            listOp, toList(doubleNode));
                    return;
                }
                doubleNode = doubleNode.next;
            }
        }
    }

    /**
     * <p>概率获取要删除的数据</p>
     *
     * @param doubleNode DoubleNode
     * @return <code>Integer</code>
     */
    private static Integer getRandomKind(DoubleNode<Integer> doubleNode) {
        if (Objects.isNull(doubleNode) || Math.random() < 0.5) {
            return -1;
        }

        List<Integer> list = toList(doubleNode).get();
        return list.get((int) (Math.random() * list.size()));
    }

    /**
     * <p>遍历或者节点中的数据</p>
     *
     * @param doubleNode DoubleNode
     * @return <code>List<T></code>
     */
    private static <T> Optional<List<T>> toList(DoubleNode<T> doubleNode) {
        if (Objects.isNull(doubleNode)) {
            return Optional.empty();
        }

        DoubleNode<T> next = doubleNode;
        List<T> list = new ArrayList<>();
        do {
            list.add(next.value);
            next = next.next;
        } while (Objects.nonNull(next));

        return Optional.of(list);
    }

    /**
     * <p>删除指定数据，并将非指定数据存放到list</p>
     *
     * @param doubleNode  Node
     * @param removeValue 要删除的数据
     * @param <T>         类型
     * @return <code>List<T></code>
     */
    private static <T> Optional<List<T>> deleteValue(DoubleNode<T> doubleNode, T removeValue) {
        if (Objects.isNull(doubleNode)) {
            return Optional.empty();
        }

        DoubleNode<T> next = doubleNode;
        List<T> list = new ArrayList<>();
        do {
            if (next.value != removeValue) {
                list.add(next.value);
            }
            next = next.next;
        } while (Objects.nonNull(next));

        return Optional.of(list);
    }

    /**
     * <p>转换list列表</p>
     *
     * @param head 头节点
     * @param <T>  元素类型
     * @return <code>List<Node<T>></code>
     */
    private static <T> Optional<List<DoubleNode<T>>> toNodeList(DoubleNode<T> head) {
        if (Objects.isNull(head)) {
            return Optional.empty();
        }

        List<DoubleNode<T>> list = new ArrayList<>();
        do {
            list.add(head);
            head = head.next;
        } while (Objects.nonNull(head));

        return Optional.of(list);
    }

    /**
     * <p>随机生成头节点</p>
     *
     * @param maxDepth 最大深度
     * @param range    取值范围
     * @return <code>Node</code> 头节点
     */
    private static Optional<DoubleNode<Integer>> generateRandomDoubleNode(int maxDepth, int range) {
        int depth = (int) (Math.random() * (maxDepth + 1));
        if (depth == 0) {
            return Optional.empty();
        }

        DoubleNode<Integer> head = new DoubleNode<>((int) (Math.random() * range));
        DoubleNode<Integer> prev = head;
        while (depth-- > 1) {
            DoubleNode<Integer> doubleNode = new DoubleNode<>((int) (Math.random() * range));
            prev.next = doubleNode;
            doubleNode.prev = prev;
            prev = doubleNode;
        }

        return Optional.of(head);
    }
}
