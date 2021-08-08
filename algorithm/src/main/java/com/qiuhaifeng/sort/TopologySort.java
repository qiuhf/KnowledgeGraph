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

package com.qiuhaifeng.sort;

import com.qiuhaifeng.datastructure.topology.Graph;
import com.qiuhaifeng.datastructure.topology.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * <pre>
 *     拓扑排序, 一定是有向图且其中没有环
 *     Note: 排序结果不唯一, 如
 *     节点 A 分别指向节点 B 和 C , 节点 B 和 C 同时指向节点 D
 *     拓扑排序可以是： A -> B -> C -> D 或者 A -> C -> B -> D
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-08
 **/
public class TopologySort {
    public <V> List<Node<V>> sort(Graph<V> graph) {
        List<Node<V>> ans = new ArrayList<>();
        if (Objects.isNull(graph) || graph.nodeMap.isEmpty()) {
            return new ArrayList<>();
        }
        HashMap<Node<V>, Integer> interMap = new HashMap<>();
        // 只有剩余入度为0的点，才进入这个队列
        Queue<Node<V>> zeroInQueue = new LinkedList<>();
        graph.nodeMap.values().forEach(node -> {
            if (node.in == 0) {
                zeroInQueue.add(node);
            } else {
                interMap.put(node, node.in);
            }
        });

        while (!zeroInQueue.isEmpty()) {
            Node<V> cur = zeroInQueue.poll();
            ans.add(cur);
            for (Node<V> next : cur.next) {
                int in = interMap.get(next) - 1;
                if (in == 0) {
                    zeroInQueue.add(next);
                } else {
                    interMap.put(next, in);
                }
            }
        }
        return ans;
    }
}
