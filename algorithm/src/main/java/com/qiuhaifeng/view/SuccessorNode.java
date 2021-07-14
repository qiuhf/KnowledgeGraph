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

import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 *   二叉树结构如下定义：
 *      Class Node {
 *      	V value;
 *      	Node left;
 *      	Node right;
 *      	Node parent;
 *      }
 *  给你二叉树中的某个节点，返回该节点的后继节点
 *  后继节点: 中序遍历，当前A节点的下一个B节点（即B节点是A节点后继节点）
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-13
 **/
public class SuccessorNode {
    public static void main(String[] args) {
        int size = 18;
        Node[] nodes = new Node[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = new Node<>(i);
        }

        while (--size > 0) {
            if ((size & 1) != 0) {
                nodes[(size - 1) / 2].left = nodes[size];
            } else {
                nodes[(size - 1) / 2].right = nodes[size];
            }
            nodes[size].parent = nodes[(size - 1) / 2];
        }

        HashMap<Node, Object> map = getSuccessorMap(nodes[0]);

        int time = 100_000;
        for (int i = 0; i < time; i++) {
            Node node = nodes[(int) (Math.random() * (nodes.length))];
            Node successorNode = getSuccessorNode(node);
            Object result = Objects.nonNull(successorNode) ? successorNode.value : null;
            Object ans = map.get(node);
            if (!Objects.equals(ans, result)) {
                System.err.printf(Locale.ROOT, "Cur Node: %s, Avtual: %s, Expect: %s", node.value, result, ans);
                return;
            }
        }
        System.out.println("Nice");
    }

    public static Node getSuccessorNode(Node node) {
        if (Objects.isNull(node)) {
            return null;
        }

        // 有右子节点
        if (Objects.nonNull(node.right)) {
            Node cur = node.right;
            // 找最左节点
            while (Objects.nonNull(cur.left)) {
                cur = cur.left;
            }
            return cur;
        }

        // 没有右子节点
        Node parent = node.parent;
        // 当前节点是其父亲节点右孩子
        while (Objects.nonNull(parent) && parent.right == node) {
            node = parent;
            parent = node.parent;
        }
        return parent;
    }

    private static HashMap<Node, Object> getSuccessorMap(Node head) {
        Stack<Node> stack = new Stack<>();
        HashMap<Node, Object> nodeMap = new HashMap<>();
        Node preNode = null;
        Node cur = head;
        while (!stack.isEmpty() || Objects.nonNull(cur)) {
            if (Objects.nonNull(cur)) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.pop();
                if (Objects.nonNull(preNode)) {
                    nodeMap.put(preNode, cur.value);
                }
                preNode = cur;
                cur = cur.right;
            }
        }
        return nodeMap;
    }

    static class Node<V> {
        V value;
        Node left;
        Node right;
        Node parent;

        public Node(V value) {
            this.value = value;
        }
    }
}
