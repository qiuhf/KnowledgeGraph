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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 *     无界的前缀树
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public class UnboundedTrie implements ITrieOperation {
    public static void main(String[] args) {
        ITrieOperation.logarithm(size -> new UnboundedTrie());
    }

    private Node root;

    public UnboundedTrie() {
        this.root = new Node();
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

        Node node = this.root;
        char[] chars = word.toCharArray();
        node.pass++;
        for (int branch : chars) {
            if (!node.nodeMap.containsKey(branch)) {
                node.nodeMap.put(branch, new Node());
            }
            node = node.nodeMap.get(branch);
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

        Node node = this.root;
        char[] chars = word.toCharArray();
        node.pass--;
        for (int branch : chars) {
            if (--node.nodeMap.get(branch).pass == 0) {
                node.nodeMap.remove(branch);
                return;
            }
            node = node.nodeMap.get(branch);
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

    private Optional<Node> lookupNode(String word) {
        if (Objects.isNull(word)) {
            return Optional.empty();
        }

        char[] chars = word.toCharArray();
        Node node = this.root;
        for (int branch : chars) {
            if (!node.nodeMap.containsKey(branch)) {
                return Optional.empty();
            }
            node = node.nodeMap.get(branch);
        }

        return Optional.ofNullable(node);
    }

    class Node {
        private int pass;
        private int end;
        private Map<Integer, Node> nodeMap;

        public Node() {
            this.nodeMap = new HashMap<>();
        }
    }
}
