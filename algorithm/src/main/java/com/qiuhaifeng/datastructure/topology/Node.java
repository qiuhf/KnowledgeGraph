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

package com.qiuhaifeng.datastructure.topology;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>点</p>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-01
 **/
public class Node<V> {
    /**
     * 值
     */
    public V value;
    /**
     * 直接入度数量
     */
    public int in;
    /**
     * 直接出度数量
     */
    public int out;
    /**
     * 出度对应直接点集合
     */
    public List<Node<V>> next;
    /**
     * 出度对应的边集合
     */
    public List<Edge<V>> edges;

    public Node(V value) {
        this.value = value;
        this.in = 0;
        this.out = 0;
        this.next = new ArrayList<>();
        this.edges = new ArrayList<>();
    }
}
