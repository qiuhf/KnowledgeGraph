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

package com.qiuhaifeng.datastructure.tree.op;

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
     * <p>生成树</p>
     *
     * @param size  大小
     * @param isNum true:数字/false:小写字母
     * @return <code>Node</code>
     */
    public static Node generateBTree(int size, boolean isNum) {
        Node[] nodes = new Node[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = isNum ? new Node<>(i) : new Node<>((char) (i + 97));
        }
        int i = 0;
        int left = 1 + (i << 1);
        // 父节点处理标记
        int mark = 1;
        while (left < size) {
            nodes[i].left = nodes[left];
            if (left + 1 >= size - 1) {
                break;
            }
            nodes[i].right = nodes[left + 1];
            // 左子节点作为下一次父节点
            i = left;
            if ((i << 1) + 2 > size - 1) {
                while ((mark & (1 << ((i - 1) / 2))) != 0) {
                    // 已处理，移动到父节点
                    i = (i - 1) / 2;
                    if (i == 0) {
                        return nodes[0];
                    }
                }
                i = (i - 1) / 2 + 1;
                mark |= (1 << i);
            }
            left = 1 + (i << 1);
        }

        return nodes[0];
    }
}
