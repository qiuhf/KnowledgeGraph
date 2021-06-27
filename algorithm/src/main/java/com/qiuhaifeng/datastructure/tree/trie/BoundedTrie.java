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

package com.qiuhaifeng.datastructure.tree.trie;

import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 *     有界字符集的前缀树
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public class BoundedTrie implements ITrieOperation {
    public static void main(String[] args) {
        ITrieOperation.logarithm(BoundedTrie::new);
    }

    private final int capacity;
    private Node root;

    /**
     * <p>构造函数</p>
     *
     * @param branchSize 多少条分支
     */
    public BoundedTrie(int branchSize) {
        if (branchSize < 0) {
            throw new IllegalArgumentException("The number of branches cannot be less than 0");
        }

        this.capacity = branchSize;
        this.root = new Node(branchSize);
    }

    /**
     * <p>添加某个字符串，可以重复添加</p>
     *
     * @param word 字符串
     */
    @Override
    public void insert(String word) {
        if (Objects.isNull(word)) {
            return;
        }

        char[] chars = word.toCharArray();
        Node node = this.root;
        node.pass++;
        for (int path : chars) {
            this.verifyChar(path);
            // 由字符，对应成走向哪条路
            if (Objects.isNull(node.next[path])) {
                node.next[path] = new Node(this.capacity);
            }
            node = node.next[path];
            node.pass++;
        }
        node.end++;
    }

    /**
     * <p>删掉某个字符串，可以重复删除</p>
     *
     * @param word 字符串
     */
    @Override
    public void delete(String word) {
        if (this.search(word) == 0) {
            return;
        }

        char[] chars = word.toCharArray();
        Node node = this.root;
        node.pass--;
        for (char path : chars) {
            if (--node.next[path].pass == 0) {
                node.next[path] = null;
                return;
            }
            node = node.next[path];
        }
        node.end--;
    }

    /**
     * <p>查询某个字符串在结构中有几个</p>
     *
     * @param word 字符串
     * @return <code>int</code>
     */
    @Override
    public int search(String word) {
        Optional<Node> nodeOp = this.lookupNode(word);
        return nodeOp.map(node -> node.end).orElse(0);
    }

    /**
     * <p>查询有多少个字符串，是以word做前缀的</p>
     *
     * @param word 字符串
     * @return <code>int</code>
     */
    @Override
    public int prefixNumber(String word) {
        Optional<Node> nodeOp = this.lookupNode(word);
        return nodeOp.map(node -> node.pass).orElse(0);
    }

    private void verifyChar(int path) {
        if (path < 0 || path > this.capacity) {
            throw new IllegalArgumentException("Invalid char");
        }
    }

    private Optional<Node> lookupNode(String word) {
        if (Objects.isNull(word)) {
            return Optional.empty();
        }

        char[] chars = word.toCharArray();
        Node node = this.root;
        for (int path : chars) {
            this.verifyChar(path);
            // 由字符，对应成走向哪条路
            if (Objects.isNull(node.next[path])) {
                return Optional.empty();
            }
            node = node.next[path];
        }

        return Optional.ofNullable(node);
    }

    class Node {
        private int pass;
        private int end;
        private Node[] next;

        public Node(int capacity) {
            this.next = new Node[capacity];
        }
    }
}
