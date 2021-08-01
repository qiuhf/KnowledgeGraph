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

/**
 * <p>边</p>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-01
 **/
public class Edge<V> {
    /**
     * 边权重
     */
    public int weight;
    /**
     * 从
     */
    public Node<V> from;
    /**
     * 到
     */
    public Node<V> to;

    public Edge(int weight, Node<V> from, Node<V> to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
