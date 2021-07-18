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

package com.qiuhaifeng.datastructure.tree;

/**
 * <pre>B树</pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-06
 **/
public class Node<V> {
    public V value;
    public Node<V> left;
    public Node<V> right;

    public Node(V value) {
        this.value = value;
    }

    /**
     * <p>生成完全二叉树</p>
     *
     * @param size  大小
     * @param isNum true:数字/false:小写字母
     * @return <code>Node</code>
     */
    public static Node generateBTree(int size, boolean isNum) {
        if (size <= 0) {
            return null;
        }

        Node[] nodes = new Node[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = isNum ? new Node<>(i) : new Node<>((char) (i + 97));
        }

        while (--size > 0) {
            if ((size & 1) != 0) {
                nodes[(size - 1) / 2].left = nodes[size];
            } else {
                nodes[(size - 1) / 2].right = nodes[size];
            }
        }

        return nodes[0];
    }

    /**
     * <pre>生成二叉树，随机子节点为空</pre>
     *
     * @param maxLevel 最大层数
     * @return <code>Node</code>
     */
    public static Node generateRandomBST(int maxLevel) {
        return generate(1, maxLevel);
    }

    private static Node generate(int level, int maxLevel) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node<>((int) (Math.random() * 20));
        head.left = generate(level + 1, maxLevel);
        head.right = generate(level + 1, maxLevel);
        return head;
    }
}
