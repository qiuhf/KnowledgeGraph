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

import java.util.NoSuchElementException;

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
 * @since 2021-06-19
 **/
public class RightMaxHeap<E extends Comparable<E>> implements IHeap<E> {
    private Object[] data;
    private final int capacity;
    private int length;

    public RightMaxHeap(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
    }

    /**
     * <p>堆是否空</p>
     *
     * @return <code>boolean</code>
     */
    @Override
    public boolean isEmpty() {
        return this.length == 0;
    }

    /**
     * <p>堆大小</p>
     *
     * @return <code>int</code>
     */
    @Override
    public int size() {
        return this.length;
    }

    /**
     * <p>堆是否满</p>
     *
     * @return <code>boolean</code>
     */
    public boolean isFull() {
        return this.length == this.capacity;
    }

    /**
     * <p>新增</p>
     *
     * @param value value
     * @return <code>T</code> T
     */
    @Override
    public E push(E value) {
        if (this.isFull()) {
            throw new NoSuchElementException("Heap is full!");
        }
        this.data[length++] = value;
        return value;
    }

    /**
     * <p>移除并返回</p>
     *
     * @return <code>T</code> T
     */
    @Override
    public E pop() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Heap is empty!");
        }
        int maxIndex = 0;
        // [1,N) => [1,N-1) ... => [1,1)
        // 时间复杂度为 O((N-1)^2)
        for (int i = 1; i < length; i++) {
            if (((E) this.data[i]).compareTo((E) this.data[maxIndex]) > 0) {
                maxIndex = i;
            }
        }
        E value = (E) this.data[maxIndex];
        this.data[maxIndex] = this.data[--this.length];
        return value;
    }

    /**
     * <p>返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E peek() {
        int maxIndex = 0;
        for (int i = 1; i < length; i++) {
            if (((E) this.data[i]).compareTo((E) this.data[maxIndex]) > 0) {
                maxIndex = i;
            }
        }

        return (E) this.data[maxIndex];
    }

    public static void main(String[] args) {
        IHeap.logarithm(RightMaxHeap::new);
    }
}
