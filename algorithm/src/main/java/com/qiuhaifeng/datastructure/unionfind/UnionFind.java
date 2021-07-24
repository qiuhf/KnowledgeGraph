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

package com.qiuhaifeng.datastructure.unionfind;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * <pre>
 *   并查集
 *     1. 有若干个样本a、b、c、d…类型假设是V
 *     2. 在并查集中一开始认为每个样本都在单独的集合里
 *     3. 用户可以在任何时候调用如下两个方法：
 *     4. isSameSet和union方法的代价越低越好
 *     5. 每个节点都有一条往上指的指针
 *     6. 节点a往上找到的头节点，叫做a所在集合的代表节点
 *     7. 查询x和y是否属于同一个集合，就是看看找到的代表节点是不是一个
 *     8. 把x和y各自所在集合的所有点合并成一个集合，只需要小集合的代表点挂在大集合的代表点的下方即可
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-24
 **/
public class UnionFind<V> {
    /**
     * 样本对应的节点
     */
    private Map<V, Node<V>> nodeMap;
    /**
     * 样本节点对应的代表节点
     */
    private Map<Node<V>, Node<V>> parentMap;
    /**
     * 代表节点的大小
     */
    private Map<Node<V>, Integer> sizeMap;

    /**
     * <p>初始化</p>
     *
     * @param values 样本集
     */
    public UnionFind(List<V> values) {
        this.nodeMap = new HashMap<>();
        this.parentMap = new HashMap<>();
        this.sizeMap = new HashMap<>();
        for (V value : values) {
            Node<V> cur = new Node<>(value);
            this.nodeMap.put(value, cur);
            this.parentMap.put(cur, cur);
            this.sizeMap.put(cur, 1);
        }
    }

    /**
     * <pre>
     *     查找样板代表节点
     *     Node: 节点往上找代表点的过程，把沿途的链变成扁平的（优化点）
     * </pre>
     *
     * @param value 样本数据
     * @return <code>Node<V></code> 代表节点
     */
    private Node<V> findFather(V value) {
        Node<V> cur = this.nodeMap.get(value);
        Stack<Node<V>> stack = new Stack<>();
        while (this.parentMap.get(cur) != cur) {
            stack.push(this.parentMap.get(cur));
        }

        while (!stack.isEmpty()) {
            this.parentMap.put(stack.pop(), cur);
        }
        return cur;
    }

    /**
     * <p>查询样本x和样本y是否属于一个集合</p>
     *
     * @param x 样板x
     * @param y 样板y
     * @return <code>boolean</code>
     */
    public boolean isSameSet(V x, V y) {
        return this.findFather(x) == this.findFather(y);
    }

    /**
     * <pre>
     *     把x和y各自所在集合的所有样本合并成一个集合
     *     Node:小集合挂在大集合的下面（优化点）
     * </pre>
     *
     * @param x 样板x
     * @param y 样板y
     */
    public void union(V x, V y) {
        Node<V> xNode = findFather(x);
        Node<V> yNode = findFather(y);
        if (xNode != yNode) {
            Integer xSize = this.sizeMap.get(xNode);
            Integer ySize = this.sizeMap.get(yNode);
            Node<V> big = xSize > ySize ? xNode : yNode;
            Node<V> small = big == xNode ? yNode : xNode;
            this.parentMap.put(small, big);
            this.sizeMap.put(big, xSize + ySize);
            this.sizeMap.remove(small);
        }
    }

    static class Node<V> {
        V value;

        Node(V value) {
            this.value = value;
        }
    }
}
