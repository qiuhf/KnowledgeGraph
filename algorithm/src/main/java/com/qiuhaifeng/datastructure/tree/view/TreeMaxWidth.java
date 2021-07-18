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

import com.qiuhaifeng.datastructure.tree.op.Node;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Objects;
import java.util.Queue;

/**
 * <pre>
 *     求二叉树最宽的层有多少个节点
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-13
 **/
public class TreeMaxWidth {
    public static void main(String[] args) {
        int maxLevel = 10;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Node head = Node.generateRandomBST(maxLevel);
            int ans = maxWidthUseMap(head);
            int result = maxWidthNoMap(head);
            if (ans != result) {
                System.out.printf(Locale.ROOT, "Oops! Actual: %d, Expect: %d", result, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int maxWidthNoMap(Node head) {
        if (Objects.isNull(head)) {
            return 0;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 最大宽度
        int maxWidth = 1;
        // 当前层节点数
        int curLevelSize = 1;
        Node cur;
        do {
            cur = queue.poll();
            if (Objects.nonNull(cur.left)) {
                queue.add(cur.left);
            }

            if (Objects.nonNull(cur.right)) {
                queue.add(cur.right);
            }

            // 每当前层节点遍历完成
            if (--curLevelSize == 0) {
                // 与下一层节点数比较取最大值
                maxWidth = Math.max(maxWidth, queue.size());
                curLevelSize = queue.size();
            }
        } while (!queue.isEmpty());
        return maxWidth;
    }

    public static int maxWidthUseMap(Node head) {
        if (Objects.isNull(head)) {
            return 0;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(head);
        // 数据结构: <当前节点，当前层>
        HashMap<Node, Integer> levelMap = new HashMap<>(16);
        levelMap.put(head, 1);
        int maxWidth = 1;
        // 当前正在统计哪一层的宽度
        int curLevel = 1;
        // 当前curLevel层宽度目前是多少
        int curLevelNodes = 0;
        Node cur;
        do {
            cur = queue.poll();
            int level = levelMap.get(cur);
            if (Objects.nonNull(cur.left)) {
                queue.add(cur.left);
                levelMap.put(cur.left, level + 1);
            }

            if (Objects.nonNull(cur.right)) {
                queue.add(cur.right);
                levelMap.put(cur.right, level + 1);
            }

            if (level == curLevel) {
                curLevelNodes++;
            } else {
                maxWidth = Math.max(maxWidth, curLevelNodes);
                curLevel++;
                curLevelNodes = 1;
            }
        } while (!queue.isEmpty());
        return Math.max(maxWidth, curLevelNodes);
    }
}
