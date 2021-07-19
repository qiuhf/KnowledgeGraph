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
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *   给定一棵二叉树的头节点head，返回这颗二叉树中最大的二叉搜索子树的大小
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-19
 **/
public class MaxSubBinarySearchTreeSize {
    public static void main(String[] args) {
        int maxLevel = 20;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node<Integer> head = Node.generateRandomBST(maxLevel);
            int ans = verify(head);
            int result = maxSubBSTSize(head);
            if (!Objects.equals(ans, result)) {
                System.out.printf(Locale.ROOT, "Oops! Actual: %s, Expect: %s\n", result, ans);
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
    public static int maxSubBSTSize(Node<Integer> head) {
        if (Objects.isNull(head)) {
            return 0;
        }
        return process(head).maxBSTSubtreeSize;
    }

    private static Info process(Node<Integer> head) {
        if (Objects.isNull(head)) {
            // allSize和subMaxSize无法确定故给null
            return null;
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        int maxValue = head.value;
        int minValue = head.value;
        int allSize = 1;
        int p1 = -1;
        if (Objects.nonNull(leftInfo)) {
            p1 = leftInfo.maxBSTSubtreeSize;
            maxValue = Math.max(maxValue, leftInfo.maxValue);
            minValue = Math.min(minValue, leftInfo.minValue);
            allSize += leftInfo.allSize;
        }

        int p2 = -1;
        if (Objects.nonNull(rightInfo)) {
            p2 = rightInfo.maxBSTSubtreeSize;
            maxValue = Math.max(maxValue, rightInfo.maxValue);
            minValue = Math.min(minValue, rightInfo.minValue);
            allSize += rightInfo.allSize;
        }

        int p3 = -1;
        boolean leftBST = Objects.isNull(leftInfo) || (leftInfo.maxBSTSubtreeSize == leftInfo.allSize);
        boolean rightBST = Objects.isNull(rightInfo) || (rightInfo.maxBSTSubtreeSize == rightInfo.allSize);
        if (leftBST && rightBST) {
            boolean leftMaxLessX = Objects.isNull(leftInfo) || (leftInfo.maxValue < head.value);
            boolean rightMinMoreX = Objects.isNull(rightInfo) || (head.value < rightInfo.minValue);
            if (leftMaxLessX && rightMinMoreX) {
                int leftSize = Objects.isNull(leftInfo) ? 0 : leftInfo.allSize;
                int rightSize = Objects.isNull(rightInfo) ? 0 : rightInfo.allSize;
                p3 = leftSize + rightSize + 1;
            }
        }

        // 与头节点无关时，取左右子树中最大二叉搜索子树的大小
        // 与头节点有关时，左子树大小 + 右子树大小 + 1
        return new Info(maxValue, minValue, allSize, Math.max(p3, Math.max(p1, p2)));
    }

    @AllArgsConstructor
    static class Info {
        private int maxValue;
        private int minValue;
        /**
         * 整棵数大小
         */
        private int allSize;
        /**
         * 最大二叉搜索子树的大小
         */
        private int maxBSTSubtreeSize;
    }

    private static int verify(Node<Integer> head) {
        if (Objects.isNull(head)) {
            return 0;
        }

        int size = getBSTSize(head);
        // 与头节点有关时，左子树大小 + 右子树大小 + 1
        if (size != 0) {
            return size;
        }
        // 与头节点无关时，取左右子树中最大二叉搜索子树的大小
        return Math.max(verify(head.left), verify(head.right));
    }

    /**
     * <p>中序遍历</p>
     *
     * @param head 任意头节点
     * @return <code>boolean</code>
     */
    private static int getBSTSize(Node<Integer> head) {
        if (Objects.isNull(head)) {
            return 0;
        }

        List<Integer> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i - 1) >= arr.get(i)) {
                return 0;
            }
        }
        return arr.size();
    }

    private static void in(Node<Integer> head, List<Integer> values) {
        if (Objects.isNull(head)) {
            return;
        }
        in(head.left, values);
        values.add(head.value);
        in(head.right, values);
    }
}
