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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * <pre>
 *     给定一棵二叉树的头节点head，和另外两个节点a和b。 返回a和b的最低公共祖先
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-20
 **/
public class LowestAncestor {
    public static void main(String[] args) {
        int maxLevel = 20;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node head = Node.generateRandomBST(maxLevel);
            List<Node> nodeList = Node.nodeToList(head);
            Node a = nodeList.isEmpty() ? null : nodeList.get((int) (Math.random() * nodeList.size()));
            Node b = nodeList.isEmpty() ? null : nodeList.get((int) (Math.random() * nodeList.size()));
            a = Math.random() > 0.5 ? a : new Node<>(Integer.MAX_VALUE);
            b = Math.random() > 0.5 ? b : new Node<>(Integer.MIN_VALUE);
            Node result = lowestAncestor(head, a, b);
            Node ans = verify(head, a, b);
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
    public static Node lowestAncestor(Node head, Node a, Node b) {
        if (Objects.isNull(head)) {
            return null;
        }
        return process(head, a, b).lowestCommonAncestor;
    }

    private static Info process(Node head, Node a, Node b) {
        if (Objects.isNull(head)) {
            return new Info(false, false, null);
        }
        Info leftInfo = process(head.left, a, b);
        Info rightInfo = process(head.right, a, b);

        boolean findA = head == a || leftInfo.findA || rightInfo.findA;
        boolean findB = head == b || leftInfo.findB || rightInfo.findB;
        Node lowestCommonAncestor = null;
        if (Objects.nonNull(leftInfo.lowestCommonAncestor)) {
            // 左子树已找到最低公共祖先
            lowestCommonAncestor = leftInfo.lowestCommonAncestor;
        } else if (Objects.nonNull(rightInfo.lowestCommonAncestor)) {
            // 右子树已找到最低公共祖先
            lowestCommonAncestor = rightInfo.lowestCommonAncestor;
        } else {
            // 左右子树已找到a,b节点，head就是最低公共祖先
            if (findA && findB) {
                lowestCommonAncestor = head;
            }
        }

        return new Info(findA, findB, lowestCommonAncestor);
    }

    @AllArgsConstructor
    static class Info {
        private boolean findA;
        private boolean findB;
        private Node lowestCommonAncestor;
    }

    private static Node verify(Node head, Node a, Node b) {
        if (Objects.isNull(head)) {
            return null;
        }
        // 数据结构<子节点, 父节点>
        Map<Node, Node> parentMap = new HashMap<>(16);
        parentMap.put(head, null);
        fillParentMap(head, parentMap);

        if (!parentMap.containsKey(a) || !parentMap.containsKey(b)) {
            return null;
        }

        HashSet<Node> o1Set = new HashSet<>();
        Node cur = a;
        do {
            o1Set.add(cur);
            cur = parentMap.get(cur);
        } while (Objects.nonNull(cur));

        cur = b;
        while (!o1Set.contains(cur)) {
            cur = parentMap.get(cur);
        }
        return cur;
    }

    private static void fillParentMap(Node head, Map<Node, Node> parentMap) {
        if (head.left != null) {
            parentMap.put(head.left, head);
            fillParentMap(head.left, parentMap);
        }
        if (head.right != null) {
            parentMap.put(head.right, head);
            fillParentMap(head.right, parentMap);
        }
    }
}
