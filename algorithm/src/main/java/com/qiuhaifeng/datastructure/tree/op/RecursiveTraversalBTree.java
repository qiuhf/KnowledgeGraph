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

import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *  递归方式：
 *    先序：任何子树的处理顺序都是，先头节点、再左子树、然后右子树
 *    中序：任何子树的处理顺序都是，先左子树、再头节点、然后右子树
 *    后序：任何子树的处理顺序都是，先左子树、再右子树、然后头节点
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-06
 **/
public class RecursiveTraversalBTree {
    public static void main(String[] args) {
        Node head = Node.generateBTree(26, false);

        System.out.print("Pre order: ");
        pre(head);
        System.out.print("\nIn order: ");
        in(head);
        System.out.print("\nPost order: ");
        post(head);
    }

    /**
     * <p>任何子树的处理顺序都是，先头节点、再左子树、然后右子树</p>
     *
     * @param head 头节点
     */
    private static void pre(Node head) {
        if (Objects.isNull(head)) {
            return;
        }
        // 先头节点
        System.out.printf(Locale.ROOT, "%s ", head.value);
        // 再左子树
        pre(head.left);
        // 然后右子树
        pre(head.right);
    }

    /**
     * <p>任何子树的处理顺序都是，先左子树、再头节点、然后右子树</p>
     *
     * @param head 头节点
     */
    private static void in(Node head) {
        if (Objects.isNull(head)) {
            return;
        }

        in(head.left);
        System.out.printf(Locale.ROOT, "%s ", head.value);
        in(head.right);
    }

    /**
     * <p>任何子树的处理顺序都是，先左子树、再右子树、然后头节点</p>
     *
     * @param head 头节点
     */
    private static void post(Node head) {
        if (Objects.isNull(head)) {
            return;
        }

        post(head.left);
        post(head.right);
        System.out.printf(Locale.ROOT, "%s ", head.value);
    }
}
