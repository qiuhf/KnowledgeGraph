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
public class DoubleNode<T> {
    private T value;
    private DoubleNode<T> prev;
    private DoubleNode<T> next;

    public DoubleNode(T value) {
        this.value = value;
    }

    public static void main(String[] args) {
        int maxDepth = 5;
        int range = 200;
        int testTime = 100;
        for (int i = 0; i < testTime; i++) {
            Optional<DoubleNode<Integer>> doubleNodeOp = generateRandomDoubleNode(maxDepth, range);
            if (doubleNodeOp.isPresent()) {
                DoubleNode<Integer> doubleNode = doubleNodeOp.get();

                Optional<List<DoubleNode<Integer>>> nodesOp = toList(doubleNode);
                Optional<DoubleNode<Integer>> reverseDoubleNodeOp = doubleNode.reverse();

                if (!nodesOp.isPresent() && !reverseDoubleNodeOp.isPresent()) {
                    continue;
                }

                if (nodesOp.isPresent() && reverseDoubleNodeOp.isPresent()) {
                    checkReverse(nodesOp.get(), reverseDoubleNodeOp.get());
                }
            }
        }

    }

    // for test

    /**
     * <p>检验反转结果</p>
     *
     * @param doubleNodeList    doubleNodeList
     * @param reverseDoubleNode 反转之后的头节点
     */
    private static void checkReverse(List<DoubleNode<Integer>> doubleNodeList, DoubleNode<Integer> reverseDoubleNode) {
        int size = doubleNodeList.size();
        for (int i = size; i > 0; i--) {
            if (!Objects.equals(doubleNodeList.get(i - 1), reverseDoubleNode)) {
                System.err.println("Original: " + doubleNodeList);
                System.err.println("actual: " + toList(reverseDoubleNode).get());
                return;
            }
            reverseDoubleNode = reverseDoubleNode.next;
        }
    }

    /**
     * <p>转换list列表</p>
     *
     * @param head 头节点
     * @param <T>  元素类型
     * @return <code>List<Node<T>></code>
     */
    private static <T> Optional<List<DoubleNode<T>>> toList(DoubleNode<T> head) {
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

        DoubleNode<Integer> head = new DoubleNode<>(randomNumber(range));
        DoubleNode<Integer> prev = head;
        while (depth-- > 1) {
            DoubleNode<Integer> doubleNode = new DoubleNode<>(randomNumber(range));
            prev.next = doubleNode;
            doubleNode.prev = prev;
            prev = doubleNode;
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
}
