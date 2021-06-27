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
 *   大根堆
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-19
 **/
public class MaxHeap<E extends Comparable<E>> implements IHeap<E> {
    public static void main(String[] args) {
        new MaxHeap<>(12).checked(12);
    }

    private Object[] heap;
    private int capacity;
    private int heapSize;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        this.heap = new Object[capacity];
    }

    /**
     * <p>堆是否空</p>
     *
     * @return <code>boolean</code>
     */
    @Override
    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    /**
     * <p>堆大小</p>
     *
     * @return <code>int</code>
     */
    @Override
    public int size() {
        return this.heapSize;
    }

    /**
     * <p>堆是否满</p>
     *
     * @return <code>boolean</code>
     */
    public boolean isFull() {
        return this.heapSize == this.capacity;
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

        this.heap[this.heapSize] = value;
        this.heapInsert(this.heapSize++);
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
        E value = (E) this.heap[0];
        this.swap(this.heap, 0, --this.heapSize);
        // 从上到下的方法，时间复杂度为O(N*logN)
        this.heapify(this.heap, 0, this.heapSize);
        return value;
    }

    /**
     * <p>返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E peek() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Heap is empty!");
        }

        return (E) this.heap[0];
    }

    /**
     * <pre>
     *     新加进来的数，现在停在了index位置，请依次往上移动
     *     Note:移动到0位置，或者干不掉自己的父节点了，停！
     * </pre>
     *
     * @param index 当前位置
     */
    private void heapInsert(int index) {
        while (((E) this.heap[index]).compareTo((E) this.heap[(index - 1) / 2]) > 0) {
            swap(this.heap, index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    /**
     * <pre>
     *      从index位置，往下看，不断的下沉
     *      Note:较大的子节点都不再比index位置的数大；已经没子节点了，停！
     * </pre>
     *
     * @param heap     堆
     * @param index    需要比较的下标
     * @param heapSize 当前堆大小
     */
    private void heapify(Object[] heap, int index, int heapSize) {
        for (int left = 1 + (index << 1); left != -1 && left < heapSize; left = 1 + (index << 1)) {
            // 右子节点，可能有可能没有！
            int right = left + 1;
            // 相等情况，取左节点
            int largest = right < heapSize && ((E) heap[right]).compareTo((E) heap[left]) > 0 ? right : left;
            if (((E) heap[index]).compareTo((E) heap[largest]) >= 0) {
                return;
            }
            this.swap(heap, index, largest);
            index = largest;
        }
    }


    private void swap(Object[] heap, int i, int j) {
        Object tmp = heap[i];
        heap[i] = heap[j];
        heap[j] = tmp;
    }
}
