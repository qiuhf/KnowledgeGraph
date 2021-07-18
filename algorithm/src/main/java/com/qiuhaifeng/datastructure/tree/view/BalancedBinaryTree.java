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

import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *     判断二叉树是否是平衡二叉树
 *     平衡二叉树：每一个子树的左右高度绝对值不超过1
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-18
 **/
public class BalancedBinaryTree {
    public static void main(String[] args) {
        int maxLevel = 100;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node head = Node.generateRandomBST(maxLevel);
            boolean result = isBalanced(head);
            boolean ans = verify(head);
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
    public static boolean isBalanced(Node head) {
        return process(head).balanced;
    }

    private static Info process(Node head) {
        if (Objects.isNull(head)) {
            return new Info(true, 0);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // 左右都是平衡二叉树且高度差小于等于1
        boolean balanced =
                leftInfo.balanced && rightInfo.balanced && (Math.abs(leftInfo.height - rightInfo.height) < 2);

        return new Info(balanced, height);
    }

    @AllArgsConstructor
    static class Info {
        private boolean balanced;
        private int height;
    }

    /**
     * <p>后序遍历</p>
     *
     * @param head 任意头节点
     * @return <code>boolean</code>
     */
    private static boolean verify(Node head) {
        if (Objects.isNull(head)) {
            return true;
        }
        boolean[] balanced = new boolean[]{true};
        process(head, balanced);
        return balanced[0];
    }

    private static int process(Node head, boolean[] balanced) {
        if (!balanced[0] || Objects.isNull(head)) {
            return -1;
        }

        int leftH = process(head.left, balanced);
        int rightH = process(head.right, balanced);
        if (Math.abs(leftH - rightH) > 1) {
            balanced[0] = false;
        }

        return Math.max(leftH, rightH) + 1;
    }
}
