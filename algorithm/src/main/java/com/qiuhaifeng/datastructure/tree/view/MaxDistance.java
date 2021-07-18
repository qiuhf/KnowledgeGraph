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
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * <pre>
 *     给定一棵二叉树的头节点head，任何两个节点之间都存在距离，返回整棵二叉树的最大距离
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-18
 **/
public class MaxDistance {
    public static void main(String[] args) {
        int maxLevel = 100;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node head = Node.generateRandomBST(maxLevel);
            int result = maxDistance(head);
            int ans = verify(head);
            if (!Objects.equals(ans, result)) {
                System.out.printf(Locale.ROOT, "Oops! Actual: %s, Expect: %s", result, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    /**
     * <p>二叉树递归套路</p>
     *
     * @param head 任意头节点
     * @return <code>boolean</code>
     */

    public static int maxDistance(Node head) {
        return process(head).maxDistance;
    }

    private static Info process(Node head) {
        if (Objects.isNull(head)) {
            return new Info(0, 0);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;
        // 情况1：head左子树最大距离
        // 情况2：head右子树最大距离
        // 情况3：head左子树高度 + 右子树高度 + 1
        int maxDistance = Math.max(leftInfo.maxDistance, rightInfo.maxDistance);

        return new Info(Math.max(maxDistance, leftInfo.height + rightInfo.height + 1), height);
    }

    @AllArgsConstructor
    static class Info {
        private int maxDistance;
        private int height;
    }

    private static int verify(Node head) {
        if (Objects.isNull(head)) {
            return 0;
        }

        ArrayList<Node> nodeList = new ArrayList<>();
        pre(head, nodeList);

        HashMap<Node, Node> parentMap = new HashMap<>(nodeList.size());
        parentMapping(head, parentMap);
        int max = 0;
        for (int i = 0; i < nodeList.size(); i++) {
            for (int j = i; j < nodeList.size(); j++) {
                max = Math.max(max, distance(parentMap, nodeList.get(i), nodeList.get(j)));
            }
        }
        return max;
    }

    private static int distance(HashMap<Node, Node> parentMap, Node left, Node right) {
        Set<Node> lSet = new HashSet<>();
        Node cur = left;
        lSet.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            lSet.add(cur);
        }

        cur = right;
        while (!lSet.contains(cur)) {
            cur = parentMap.get(cur);
        }

        Node lowestAncestor = cur;
        cur = left;
        int distance1 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance1++;
        }

        cur = right;
        int distance2 = 1;
        while (cur != lowestAncestor) {
            cur = parentMap.get(cur);
            distance2++;
        }
        return distance1 + distance2 - 1;
    }

    private static void parentMapping(Node head, HashMap<Node, Node> parentMap) {
        if (Objects.nonNull(head.left)) {
            parentMap.put(head.left, head);
            parentMapping(head.left, parentMap);
        }

        if (Objects.nonNull(head.right)) {
            parentMap.put(head.right, head);
            parentMapping(head.right, parentMap);
        }
    }

    private static void pre(Node head, ArrayList<Node> nodeList) {
        if (Objects.isNull(head)) {
            return;
        }
        nodeList.add(head);
        pre(head.left, nodeList);
        pre(head.right, nodeList);
    }
}
