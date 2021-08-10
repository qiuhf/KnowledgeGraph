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

package com.qiuhaifeng.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * <pre>
 *  给定一个有向图，图节点的拓扑排序定义如下:
 *     对于图中的每一条有向边 A -> B , 在拓扑排序中A一定在B之前.
 *     拓扑排序中的第一个节点可以是图中的任何一个没有其他节点指向它的节点.
 *     针对给定的有向图找到任意一种拓扑排序的顺序.
 *  Note:
 *     你可以假设图中至少存在一种拓扑排序
 *     图结点的个数 <= 5000
 *
 *  样例 1：
 *   输入：graph = {0,1,2,3#1,4#2,4,5#3,4,5#4#5}
 *   输出：[0, 1, 2, 3, 4, 5]
 *   拓扑排序可以为:
 *      [0, 1, 2, 3, 4, 5]
 *      [0, 2, 3, 1, 5, 4]
 *      ...
 *      您只需要返回给定图的任何一种拓扑顺序。
 *
 *  挑战能否分别用BFS和DFS完成？
 *
 * 来源：本题为lintcode原题
 * 链接：https://www.lintcode.com/problem/127/
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-10
 **/
public class M127TopologySort {
    public static void main(String[] args) {
        DirectedGraphNode node0 = new DirectedGraphNode(0);
        DirectedGraphNode node1 = new DirectedGraphNode(1);
        DirectedGraphNode node2 = new DirectedGraphNode(2);
        DirectedGraphNode node3 = new DirectedGraphNode(3);
        DirectedGraphNode node4 = new DirectedGraphNode(4);
        DirectedGraphNode node5 = new DirectedGraphNode(5);
        DirectedGraphNode node6 = new DirectedGraphNode(6);
        node0.neighbors.add(node1);
        node0.neighbors.add(node2);
        node0.neighbors.add(node3);
        node1.neighbors.add(node4);
        node2.neighbors.add(node4);
        node2.neighbors.add(node5);
        node3.neighbors.add(node4);
        node3.neighbors.add(node5);
        node4.neighbors.add(node6);
        ArrayList<DirectedGraphNode> graph = new ArrayList<>();
        graph.add(node0);
        graph.add(node1);
        graph.add(node2);
        graph.add(node3);
        graph.add(node4);
        graph.add(node5);
        graph.add(node6);
        ArrayList<DirectedGraphNode> sort = new M127TopologySort().topSort(graph);
        System.out.println(sort);
    }

    /**
     * topSort
     *
     * @param graph: A list of Directed graph node
     * @return Any topological order for the given graph.
     */
    public ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        // write your code here
        if (Objects.isNull(graph) || graph.isEmpty()) {
            return new ArrayList<>();
        }

        // return dfs(graph);
        return bfs(graph);
    }

    class Record {
        DirectedGraphNode node;
        int depth;

        public Record(DirectedGraphNode node, int depth) {
            this.node = node;
            this.depth = depth;
        }
    }

    private ArrayList<DirectedGraphNode> dfs(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> cacheRecord = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            process(node, cacheRecord);
        }

        ArrayList<Record> records = new ArrayList<>(cacheRecord.values());
        records.sort((r1, r2) -> r2.depth - r1.depth);

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (Record record : records) {
            ans.add(record.node);
        }
        return ans;
    }

    private Record process(DirectedGraphNode node, HashMap<DirectedGraphNode, Record> cacheRecord) {
        Record record = cacheRecord.get(node);
        if (Objects.nonNull(record)) {
            return record;
        }

        int depth = 0;
        for (DirectedGraphNode neighbor : node.neighbors) {
            depth = Math.max(depth, process(neighbor, cacheRecord).depth);
        }
        record = new Record(node, depth + 1);
        cacheRecord.put(node, record);
        return record;
    }

    private ArrayList<DirectedGraphNode> bfs(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Integer> graphMap = new HashMap<>();
        for (DirectedGraphNode node : graph) {
            graphMap.put(node, 0);
        }

        for (DirectedGraphNode node : graph) {
            for (DirectedGraphNode next : node.neighbors) {
                graphMap.put(next, graphMap.get(next) + 1);
            }
        }

        Queue<DirectedGraphNode> queue = new LinkedList<>();
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        graphMap.forEach((node, val) -> {
            if (val == 0) {
                queue.add(node);
                ans.add(node);
            }
        });

        while (!queue.isEmpty()) {
            DirectedGraphNode cur = queue.poll();
            for (DirectedGraphNode next : cur.neighbors) {
                int in = graphMap.get(next) - 1;
                if (in == 0) {
                    ans.add(next);
                    queue.add(next);
                } else {
                    graphMap.put(next, in);
                }
            }
        }
        return ans;
    }

    static class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;

        DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }
}
