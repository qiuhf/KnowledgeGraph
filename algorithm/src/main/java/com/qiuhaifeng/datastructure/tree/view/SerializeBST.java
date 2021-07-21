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

package com.qiuhaifeng.datastructure.tree.view;

import com.qiuhaifeng.datastructure.tree.Node;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

/**
 * <pre>
 *   实现二叉树的序列化和反序列化
 *     1）先序方式
 *     2）后序方式
 *     3）按层（宽度优先）方式
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-09
 **/
public class SerializeBST {
    public static void main(String[] args) {
        int maxLevel = 100;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer> head = Node.generateRandomBST((int) (Math.random() * (maxLevel + 1)));
            Node<Integer> preBuild = preDeserialize(preSerialize(head));
            Node<Integer> posBuild = postDeserialize(postSerialize(head));
            Node<Integer> levelBuild = levelDeserialize(levelSerialize(head));
            if (!compare(preBuild, posBuild) || !compare(posBuild, levelBuild)) {
                System.err.printf(Locale.ROOT, "Oops! level; %s\n", levelSerialize(head));
                return;
            }
        }
        System.out.println("Nice");
    }

    /**
     * <p>按先序方式序列化</p>
     *
     * @param head 头节点
     * @return <code>Queue<String></code>
     */
    private static Queue<String> preSerialize(Node<Integer> head) {
        Queue<String> queue = new LinkedList<>();
        preSerial(head, queue);
        return queue;
    }

    private static void preSerial(Node<Integer> node, Queue<String> queue) {
        if (Objects.isNull(node)) {
            queue.add(null);
        } else {
            queue.add(convertStr(node));
            preSerial(node.left, queue);
            preSerial(node.right, queue);
        }
    }

    /**
     * <p>按先序方式反序列化</p>
     *
     * @param queue 节点值集
     * @return <code>Node<Integer></code>
     */
    private static Node<Integer> preDeserialize(Queue<String> queue) {
        if (Objects.isNull(queue) || queue.isEmpty()) {
            return null;
        }

        return preDeserial(queue);
    }

    private static Node<Integer> preDeserial(Queue<String> queue) {
        String value = queue.poll();
        if (Objects.isNull(value)) {
            return null;
        }

        Node<Integer> head = new Node<>(Integer.parseInt(value));
        head.left = preDeserial(queue);
        head.right = preDeserial(queue);
        return head;
    }

    /**
     * <p>按后序方式序列化</p>
     *
     * @param head 头节点
     * @return <code>Queue<String></code>
     */
    private static Queue<String> postSerialize(Node<Integer> head) {
        Queue<String> queue = new LinkedList<>();
        postSerial(head, queue);
        return queue;
    }

    private static void postSerial(Node<Integer> node, Queue<String> queue) {
        if (Objects.isNull(node)) {
            queue.add(null);
        } else {
            postSerial(node.left, queue);
            postSerial(node.right, queue);
            queue.add(convertStr(node));
        }
    }

    /**
     * <p>按后序方式反序列化</p>
     *
     * @param queue 节点值集
     * @return <code>Node<Integer></code>
     */
    private static Node<Integer> postDeserialize(Queue<String> queue) {
        if (Objects.isNull(queue) || queue.isEmpty()) {
            return null;
        }
        Stack<String> stack = new Stack<>();
        // 左右头 -> 头右左
        do {
            stack.push(queue.poll());
        } while (!queue.isEmpty());
        return postDeserial(stack);
    }

    private static Node<Integer> postDeserial(Stack<String> stack) {
        String value = stack.pop();
        if (Objects.isNull(value)) {
            return null;
        }

        Node<Integer> head = new Node<>(Integer.parseInt(value));
        head.right = postDeserial(stack);
        head.left = postDeserial(stack);
        return head;
    }

    /**
     * <p>按层方式序列化</p>
     *
     * @param head 头节点
     * @return <code>Queue<String></code>
     */
    private static Queue<String> levelSerialize(Node<Integer> head) {
        Queue<String> queue = new LinkedList<>();
        if (Objects.isNull(head)) {
            queue.add(null);
        } else {
            queue.add(convertStr(head));
            Queue<Node<Integer>> nodeQueue = new LinkedList<>();
            nodeQueue.add(head);
            do {
                head = nodeQueue.poll();
                if (Objects.nonNull(head.left)) {
                    nodeQueue.add(head.left);
                    queue.add(convertStr(head.left));
                } else {
                    queue.add(null);
                }

                if (Objects.nonNull(head.right)) {
                    nodeQueue.add(head.right);
                    queue.add(convertStr(head.right));
                } else {
                    queue.add(null);
                }
            } while (!nodeQueue.isEmpty());
        }

        return queue;
    }

    /**
     * <p>按层方式反序列化</p>
     *
     * @param queue 节点值集
     * @return <code>Node<Integer></code>
     */
    private static Node<Integer> levelDeserialize(Queue<String> queue) {
        if (Objects.isNull(queue) || queue.isEmpty() || Objects.isNull(queue.peek())) {
            return null;
        }

        Node<Integer> head = new Node<>(Integer.parseInt(queue.poll()));
        Queue<Node<Integer>> nodeQueue = new LinkedList<>();
        nodeQueue.add(head);
        Node<Integer> cur;
        while (!queue.isEmpty()) {
            cur = nodeQueue.poll();
            cur.left = generateNode(queue.poll());
            cur.right = generateNode(queue.poll());
            if (Objects.nonNull(cur.left)) {
                nodeQueue.add(cur.left);
            }

            if (Objects.nonNull(cur.right)) {
                nodeQueue.add(cur.right);
            }
        }

        return head;
    }

    private static String convertStr(Node<Integer> node) {
        return Integer.toString(Optional.ofNullable(node.value).orElse(0));
    }

    private static Node<Integer> generateNode(String value) {
        return Objects.isNull(value) ? null : new Node<>(Integer.parseInt(value));
    }

    private static boolean compare(Node head1, Node head2) {
        if (Objects.isNull(head1) && Objects.isNull(head2)) {
            return true;
        }

        if (Objects.isNull(head1) || Objects.isNull(head2)) {
            return false;
        }

        if (!Objects.equals(head1.value, head2.value)) {
            return false;
        }
        return compare(head1.left, head2.left) && compare(head1.right, head2.right);
    }
}
