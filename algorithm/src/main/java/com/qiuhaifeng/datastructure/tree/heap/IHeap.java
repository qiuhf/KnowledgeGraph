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

package com.qiuhaifeng.datastructure.tree.heap;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Locale;
import java.util.function.Function;

/**
 * <pre>
 *  1. 堆结构就是用数组实现的完全二叉树结构
 *    > 从上至下，从左往右依次填满的二叉树，称为完全二叉树
 *  2. 完全二叉树中如果每棵子树的最大值都在顶部就是`大根堆`
 *  3. 完全二叉树中如果每棵子树的最小值都在顶部就是`小根堆`
 *  4. 堆结构的heapInsert与heapify操作
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public interface IHeap<E> {
    /**
     * <p>堆是否空</p>
     *
     * @return <code>boolean</code>
     */
    boolean isEmpty();

    /**
     * <p>堆大小</p>
     *
     * @return <code>int</code>
     */
    int size();

    /**
     * <p>新增</p>
     *
     * @param value value
     * @return <code>T</code> T
     */
    E push(E value);

    /**
     * <p>移除并返回</p>
     *
     * @return <code>T</code> T
     */
    E pop();

    /**
     * <p>返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    E peek();

    /**
     * <p>对数器</p>
     *
     * @param callback 验证对象
     */
    static void logarithm(Function<Integer, IHeap> callback) {
        int capacity = (int) (Math.random() * 20);
        IHeap<Integer> heap = callback.apply(capacity);
        for (int i = 0; i < capacity; i++) {
            heap.push(AuxiliaryUtil.randomNumber(10));
        }
        for (int i = 0; i < capacity; i++) {
            System.out.printf(Locale.ROOT, "%d -> ", heap.pop());
        }
        System.out.println("null\nheap.isEmpty() = " + heap.isEmpty());
    }
}