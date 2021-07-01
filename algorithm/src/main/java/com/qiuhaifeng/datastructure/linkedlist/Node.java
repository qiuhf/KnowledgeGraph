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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>单链表</pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class Node<T> {
    public Node<T> next;
    public T value;

    public Node(T value) {
        this.value = value;
    }

    /**
     * <p>移除指定数据的节点</p>
     *
     * @param value value
     * @return <code>Node</code> 新头节点
     */
    public Optional<Node<T>> removeValue(T value) {
        Node<T> head = this;
        // 确定头节点
        do {
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
        Node<T> checkpoint = head.next;
        Node<T> cur = head;
        // 2 3 2 3 2 “2”
        // 3 2 3 2
        while (Objects.nonNull(checkpoint)) {
            if (checkpoint.value != value) {
                cur = checkpoint;
            } else {
                // 当前节点next指向下一个节点next
                cur.next = checkpoint.next;
            }
            // 指向下一个节点
            checkpoint = cur.next;
        }

        return Optional.of(head);
    }

    /**
     * <p>单链表反转</p>
     *
     * @return <code>Node</code> 新头节点
     */
    public Optional<Node<T>> reverse() {
        Node<T> head = this;
        Node<T> next;
        Node<T> cur = null;
        // a -> b -> c -> null
        // c -> b -> a -> null
        do {
            // b | c | null
            next = head.next;
            // a -> null | b -> a | c -> b
            head.next = cur;
            // a | b | c
            cur = head;
            // b | c | null
            head = next;
        } while (Objects.nonNull(head));

        return Optional.of(cur);
    }

    // for test

    public static void main(String[] args) {
        int maxDepth = 8;
        // [0, range)
        int range = 10;
        int testTime = 10_000;
        for (int i = 0; i < testTime; i++) {
            generateRandomNode(maxDepth, range).ifPresent(Node::verifyNodeReverse);
            generateRandomNode(maxDepth, range).ifPresent(Node::verifyNodeRemove);
        }
    }

    /**
     * <p>验证移除指定数据的节点结果是否正确</p>
     *
     * @param node Node
     */
    private static void verifyNodeRemove(Node<Integer> node) {
        Integer removeValue = getRandomKind(node);
        Optional<List<Integer>> listOp = deleteValue(node, removeValue);
        Optional<Node<Integer>> nodesOp = node.removeValue(removeValue);
        if (!listOp.isPresent() && !nodesOp.isPresent()) {
            return;
        }

        if (listOp.isPresent() && nodesOp.isPresent()) {
            List<Integer> list = listOp.get();
            Node<Integer> nodes = nodesOp.get();
            for (Integer value : list) {
                if (!Objects.equals(value, nodes.value)) {
                    System.err.format(Locale.ROOT, "Remove value: %d\nExpect: %s\nActual: %s\n", removeValue,
                            listOp, toList(nodes));
                    return;
                }
                nodes = nodes.next;
            }
        }
    }

    /**
     * <p>概率获取要删除的数据</p>
     *
     * @param node Node
     * @return <code>Integer</code>
     */
    private static Integer getRandomKind(Node<Integer> node) {
        if (Objects.isNull(node) || Math.random() > 0.5) {
            return -1;
        }

        List<Integer> list = toList(node).get();
        return list.get((int) (Math.random() * list.size()));
    }

    /**
     * <p>遍历或者节点中的数据</p>
     *
     * @param node Node
     * @return <code>List<T></code>
     */
    private static <T> Optional<List<T>> toList(Node<T> node) {
        if (Objects.isNull(node)) {
            return Optional.empty();
        }

        Node<T> next = node;
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
     * @param node        Node
     * @param removeValue 要删除的数据
     * @param <T>         类型
     * @return <code>List<T></code>
     */
    private static <T> Optional<List<T>> deleteValue(Node<T> node, T removeValue) {
        if (Objects.isNull(node)) {
            return Optional.empty();
        }

        List<T> list = new ArrayList<>();
        do {
            if (node.value != removeValue) {
                list.add(node.value);
            }
            node = node.next;
        } while (Objects.nonNull(node));

        return Optional.of(list);
    }

    /**
     * <p>验证反转结果是否正确</p>
     *
     * @param node Node
     */
    private static void verifyNodeReverse(Node<Integer> node) {
        Optional<List<Node<Integer>>> listOp = toNodeList(node);
        Optional<Node<Integer>> nodesOp = node.reverse();

        if (!listOp.isPresent() && !nodesOp.isPresent()) {
            return;
        }

        if (listOp.isPresent() && nodesOp.isPresent()) {
            List<Node<Integer>> nodeList = listOp.get();
            Node<Integer> reverseNode = nodesOp.get();
            int size = nodeList.size();
            for (int i = size; i > 0; i--) {
                if (!Objects.equals(reverseNode, nodeList.get(i - 1))) {
                    System.err.format(Locale.ROOT, "Expect: %s\nActual: %s\n", listOp, toNodeList(reverseNode));
                    return;
                }
                reverseNode = reverseNode.next;
            }
        }
    }

    /**
     * <p>转换list列表</p>
     *
     * @param head 头节点
     * @param <T>  元素类型
     * @return <code>List<Node<T>></code>
     */
    private static <T> Optional<List<Node<T>>> toNodeList(Node<T> head) {
        List<Node<T>> list = new ArrayList<>();
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
    private static Optional<Node<Integer>> generateRandomNode(int maxDepth, int range) {
        int depth = (int) (Math.random() * (maxDepth + 1));
        if (depth == 0) {
            return Optional.empty();
        }
        Node<Integer> head = new Node<>((int) (Math.random() * range));
        // next指向head地址
        Node<Integer> next = head;
        while (depth-- > 1) {
            Node<Integer> node = new Node<>((int) (Math.random() * range));
            next.next = node;
            // next指向当前节点地址
            next = node;
        }

        return Optional.of(head);
    }
}
