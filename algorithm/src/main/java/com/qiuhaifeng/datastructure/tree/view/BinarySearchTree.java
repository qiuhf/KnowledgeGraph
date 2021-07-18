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
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *     判断二叉树是否是二叉搜索树
 *     二叉搜索树：左树都比头节点小，右树都比头节点大
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-18
 **/
public class BinarySearchTree {
    public static void main(String[] args) {
        int maxLevel = 100;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer> head = Node.generateRandomBST(maxLevel);
            boolean ans = verify(head);
            boolean result = isBST(head);
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
    public static boolean isBST(Node<Integer> head) {
        return process(head).isBST;
    }

    private static Info process(Node<Integer> head) {
        if (Objects.isNull(head)) {
            return new Info(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        // 左右子树都是二叉搜索数，且左子树最大值小于父节点，右子树最小值大于头节点
        boolean isBST = leftInfo.isBST && rightInfo.isBST && head.value < rightInfo.minValue && head.value > leftInfo.maxValue;
        int maxValue = Math.max(leftInfo.maxValue, Math.max(rightInfo.maxValue, head.value));
        int minValue = Math.min(leftInfo.minValue, Math.min(rightInfo.minValue, head.value));

        return new Info(isBST, maxValue, minValue);
    }

    @AllArgsConstructor
    static class Info {
        private boolean isBST;
        private int maxValue;
        private int minValue;
    }

    /**
     * <p>中序遍历</p>
     *
     * @param head 任意头节点
     * @return <code>boolean</code>
     */
    private static boolean verify(Node<Integer> head) {
        if (Objects.isNull(head)) {
            return true;
        }
        ArrayList<Integer> nodeList = new ArrayList<>();
        in(head, nodeList);
        for (int i = 1; i < nodeList.size(); i++) {
            if (nodeList.get(i - 1) >= nodeList.get(i)) {
                return false;
            }
        }
        return true;
    }

    private static void in(Node<Integer> head, ArrayList<Integer> nodeList) {
        if (Objects.isNull(head)) {
            return;
        }
        in(head.left, nodeList);
        nodeList.add(head.value);
        in(head.right, nodeList);
    }
}
