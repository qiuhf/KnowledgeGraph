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

package com.qiuhaifeng.datastructure.topology.view;

import com.qiuhaifeng.datastructure.topology.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * 图遍历
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-08
 **/
public class GraphTraversal {
    public static void main(String[] args) {
        Node<Integer> head = new Node<>(0);
        ArrayList<Node<Integer>> next = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            next.add(new Node<>(i));
        }
        head.next = next;
        Node<Integer> subNext = head.next.get(2);
        ArrayList<Node<Integer>> next2 = new ArrayList<>();
        for (int i = 5; i < 8; i++) {
            next2.add(new Node<>(i));
        }
        subNext.next = next2;

        bfs(head);
        System.out.println();
        dfs(head);
    }

    /**
     * <p>从head出发，进行宽度优先遍历</p>
     *
     * @param head head
     */
    public static <V> void bfs(Node<V> head) {
        if (Objects.isNull(head)) {
            return;
        }
        Queue<Node<V>> queue = new LinkedList<>();
        HashSet<Node<V>> sets = new HashSet<>();
        queue.add(head);
        sets.add(head);
        do {
            Node<V> cur = queue.poll();
            System.out.printf(Locale.ROOT, "%s ", cur.value);
            for (Node<V> next : cur.next) {
                if (!sets.contains(next)) {
                    sets.add(next);
                    queue.add(next);
                }
            }
        } while (!queue.isEmpty());
    }

    /**
     * <p>从head出发，进行深度优先遍历</p>
     *
     * @param head head
     */
    public static <V> void dfs(Node<V> head) {
        if (Objects.isNull(head)) {
            return;
        }
        Stack<Node<V>> stack = new Stack<>();
        HashSet<Node<V>> sets = new HashSet<>();
        stack.push(head);
        sets.add(head);
        System.out.printf(Locale.ROOT, "%s ", head.value);
        do {
            Node<V> cur = stack.pop();
            for (Node<V> next : cur.next) {
                if (!sets.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    sets.add(next);
                    System.out.printf(Locale.ROOT, "%s ", next.value);
                    break;
                }
            }
        } while (!stack.isEmpty());
    }
}
