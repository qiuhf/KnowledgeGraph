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

package com.qiuhaifeng.datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class Node<T> {
    private Node<T> next;
    private T value;

    public Node(T value) {
        this.value = value;
    }

    public static void main(String[] args) {
        int maxDepth = 100;
        int range = 200;
        int testTime = 1;
        for (int i = 0; i < testTime; i++) {
            Optional<Node<Integer>> nodeOp = generateRandomNode(maxDepth, range);
            if (nodeOp.isPresent()) {
                Node<Integer> node = nodeOp.get();

                Optional<List<Node<Integer>>> nodesOp = toList(node);
                Optional<Node<Integer>> reverseNodeOp = node.reverse();

                if (!nodesOp.isPresent() && !reverseNodeOp.isPresent()) {
                    continue;
                }

                if (nodesOp.isPresent() && reverseNodeOp.isPresent()) {
                    checkReverse(nodesOp.get(), reverseNodeOp.get());
                }
            }
        }
    }

    // for test

    /**
     * <p>检验反转结果</p>
     *
     * @param nodeList    nodeList
     * @param reverseNode 反转之后的头节点
     */
    private static void checkReverse(List<Node<Integer>> nodeList, Node<Integer> reverseNode) {
        int size = nodeList.size();
        for (int i = size; i > 0; i--) {
            if (!Objects.equals(reverseNode, nodeList.get(i - 1))) {
                System.err.println("Original: " + nodeList);
                System.err.println("actual: " + toList(reverseNode).get());
                return;
            }
            reverseNode = reverseNode.next;
        }
    }

    /**
     * <p>转换list列表</p>
     *
     * @param head 头节点
     * @param <T>  元素类型
     * @return <code>List<Node<T>></code>
     */
    private static <T> Optional<List<Node<T>>> toList(Node<T> head) {
        if (Objects.isNull(head)) {
            return Optional.empty();
        }

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
        Node<Integer> head = new Node<>(randomNumber(range));
        // next指向head地址
        Node<Integer> next = head;
        while (depth-- > 1) {
            Node<Integer> node = new Node<>(randomNumber(range));
            next.next = node;
            // next指向当前节点地址
            next = node;
        }

        return Optional.of(head);
    }

    /**
     * <p>随机生成范围中的一个数：{range, -range}</p>
     *
     * @param range 范围
     * @return <code>int</code>
     */
    private static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    /**
     * <p>单链表反转</p>
     *
     * @return <code>Node</code>
     */
    public Optional<Node<T>> reverse() {
        Node<T> head = this;
        Node<T> next;
        Node<T> cur = null;
        // a -> b -> c -> null
        // c -> b -> a -> null
        while (Objects.nonNull(head)) {
            // b | c | null
            next = head.next;
            // a -> null | b -> a | c -> b
            head.next = cur;
            // a | b | c
            cur = head;
            // b | c | null
            head = next;
        }

        return Optional.of(cur);
    }
}
