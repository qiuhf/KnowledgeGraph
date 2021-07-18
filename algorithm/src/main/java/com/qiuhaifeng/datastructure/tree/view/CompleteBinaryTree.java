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

import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;

/**
 * <pre>
 *     判断二叉树是否是完全二叉树
 *     完全二叉树：从上至下，从左往右依次填满的二叉树
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-18
 **/
public class CompleteBinaryTree {
    public static void main(String[] args) {
        int maxLevel = 100;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node head = Node.generateRandomBST(maxLevel);
            boolean result = isCompleteBT(head);
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
    public static boolean isCompleteBT(Node head) {
        return process(head).isCBT;
    }

    private static Info process(Node head) {
        if (Objects.isNull(head)) {
            return new Info(true, true, 0);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);
        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        // 情况1：head节点左右节点都是满二叉树，左节点高度 == 右节点高度
        boolean full = leftInfo.isFull && rightInfo.isFull && (leftInfo.height == rightInfo.height);

        boolean completed = full;
        if (leftInfo.isFull && rightInfo.isFull && (leftInfo.height == rightInfo.height + 1)) {
            // 情况2：head节点左右节点都是满二叉树，左节点高度 == 右节点高度 + 1
            completed = true;
        } else if (leftInfo.isCBT && rightInfo.isFull && (leftInfo.height == rightInfo.height + 1)) {
            // 情况3：head节点左节点是完全二叉树，右节点是满二叉树，左节点高度 == 右节点高度 + 1
            completed = true;
        } else if (leftInfo.isFull && rightInfo.isCBT && (leftInfo.height == rightInfo.height)) {
            // 情况4：head节点左节点是满二叉树，右节点是完全二叉树，左节点高度 == 右节点高度
            completed = true;
        }

        return new Info(completed, full, height);
    }

    @AllArgsConstructor
    static class Info {
        private boolean isCBT;
        private boolean isFull;
        private int height;
    }

    /**
     * <p>宽度优先遍历</p>
     *
     * @param head 任意头节点
     * @return <code>boolean</code>
     */
    private static boolean verify(Node head) {
        if (Objects.isNull(head)) {
            return true;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        Node cur;
        // 子节点存在空时，开启检查
        boolean checkpoint = false;
        do {
            cur = queue.poll();
            // 左节点存在子节点为空时，右节点任意一个子节点不为空，返回false
            if (checkpoint && (Objects.nonNull(cur.left) || Objects.nonNull(cur.right))) {
                return false;
            }

            if (Objects.nonNull(cur.left)) {
                queue.add(cur.left);
            }

            if (Objects.nonNull(cur.right)) {
                queue.add(cur.right);
            }

            // 存在子节点为空时
            if (Objects.isNull(cur.left) || Objects.isNull(cur.right)) {
                // 左子节点空，右子节点不空，返回false
                if (Objects.isNull(cur.left) && Objects.nonNull(cur.right)) {
                    return false;
                }
                checkpoint = true;
            }
        } while (!queue.isEmpty());

        return true;
    }
}
