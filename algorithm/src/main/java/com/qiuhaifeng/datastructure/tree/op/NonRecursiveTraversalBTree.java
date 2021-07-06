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
import java.util.Stack;

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
public class NonRecursiveTraversalBTree {
    public static void main(String[] args) {
        Node head = Node.generateBTree(8, true);

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

        Stack<Node> stack = new Stack<>();
        stack.push(head);
        do {
            // 移除父节点并打印
            Node node = stack.pop();
            System.out.printf(Locale.ROOT, "%s ", node.value);
            // 右子节点先进栈
            if (Objects.nonNull(node.right)) {
                stack.push(node.right);
            }
            // 左子节点后进栈
            if (Objects.nonNull(node.left)) {
                stack.push(node.left);
            }
        } while (!stack.isEmpty());
    }

    /**
     * <p>任何子树的处理顺序都是，先左子树、再头节点、然后右子树</p>
     *
     * @param head 头节点
     */
    private static void in(Node head) {
        Stack<Node> stack = new Stack<>();
        Node cur = head;
        while (!stack.isEmpty() || Objects.nonNull(cur)) {
            // 整条左边界先进栈
            if (Objects.nonNull(cur)) {
                stack.push(cur);
                cur = cur.left;
            } else {
                // 移除最后一个左节点并打印
                cur = stack.pop();
                System.out.printf(Locale.ROOT, "%s ", cur.value);
                // 指向父节点的右节点
                //
                cur = cur.right;
            }
        }
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

        Stack<Node> stack = new Stack<>();
        stack.push(head);
        Node cur;
        do {
            cur = stack.peek();
            if (Objects.nonNull(cur.left) && head != cur.left && head != cur.right) {
                stack.push(cur.left);
            } else if (Objects.nonNull(cur.right) && head != cur.right) {
                stack.push(cur.right);
            } else {
                cur = stack.pop();
                System.out.printf(Locale.ROOT, "%s ", cur.value);
                // 标记已打印的节点
                head = cur;
            }
        } while (!stack.isEmpty());
    }
}
