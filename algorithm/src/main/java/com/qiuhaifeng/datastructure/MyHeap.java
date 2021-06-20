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

package com.qiuhaifeng.datastructure;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Arrays;
import java.util.Locale;
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
public class MyHeap {
    public static void main(String[] args) {
        int capacity = 12;
        check(capacity, new RightMaxHeap<>(capacity));
        System.out.println("=====================================");
        check(capacity, new MaxHeap<>(capacity));
    }

    private static void check(int capacity, IHeap<Integer> heap) {
        for (int i = 0; i < capacity; i++) {
            heap.push(AuxiliaryUtil.randomNumber(10));
        }
        System.out.println("heap.isFull() = " + heap.isFull());
        System.out.println(heap);
        for (int i = 0; i < capacity; i++) {
            System.out.printf(Locale.ROOT, "%d > ", heap.pop());
        }
        System.out.println("null\nheap.isEmpty() = " + heap.isEmpty());
    }

    /**
     * <p>大根堆</p>
     *
     * @param <T>
     */
    static class MaxHeap<T extends Comparable<T>> implements IHeap<T> {
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
         * <p>堆是否满</p>
         *
         * @return <code>boolean</code>
         */
        @Override
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
        public T push(T value) {
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
        public T pop() {
            if (this.isEmpty()) {
                throw new NoSuchElementException("Heap is empty!");
            }
            T value = (T) this.heap[0];
            swap(this.heap, 0, --this.heapSize);
            // 从上到下的方法，时间复杂度为O(N*logN)
            this.heapify(this.heap, 0, this.heapSize);
            return value;
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
            for (int parent = (index - 1) >> 1; index > 0 && ((T) this.heap[index]).compareTo((T) this.heap[parent]) > 0; parent =
                    (index - 1) >> 1) {
                this.swap(this.heap, index, parent);
                index = parent;
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
            for (int left = 1 + (index << 1); left < heapSize; left = 1 + (index << 1)) {
                // 右子节点，可能有可能没有！
                int right = left + 1;
                // 相等情况，取左节点
                int largest = right < heapSize && ((T) heap[right]).compareTo((T) heap[left]) > 0 ? right : left;
                if (((T) heap[index]).compareTo((T) heap[largest]) >= 0) {
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

        @Override
        public String toString() {
            return Arrays.toString(this.heap);
        }
    }

    /**
     * <p>获取右边最大值</p>
     *
     * @param <T>
     */
    static class RightMaxHeap<T extends Comparable<T>> implements IHeap<T> {
        private Object[] data;
        private final int capacity;
        private int length;

        RightMaxHeap(int capacity) {
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
         * <p>堆是否满</p>
         *
         * @return <code>boolean</code>
         */
        @Override
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
        public T push(T value) {
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
        public T pop() {
            if (this.isEmpty()) {
                throw new NoSuchElementException("Heap is empty!");
            }
            int maxIndex = 0;
            // [1,N) => [1,N-1) ... => [1,1)
            // 时间复杂度为 O((N-1)^2)
            for (int i = 1; i < length; i++) {
                if (((T) this.data[i]).compareTo((T) this.data[maxIndex]) > 0) {
                    maxIndex = i;
                }
            }
            T value = (T) this.data[maxIndex];
            this.data[maxIndex] = this.data[--this.length];
            return value;
        }

        @Override
        public String toString() {
            return Arrays.toString(this.data);
        }
    }

    interface IHeap<T extends Comparable<T>> {
        /**
         * <p>堆是否空</p>
         *
         * @return <code>boolean</code>
         */
        boolean isEmpty();

        /**
         * <p>堆是否满</p>
         *
         * @return <code>boolean</code>
         */
        boolean isFull();

        /**
         * <p>新增</p>
         *
         * @param value value
         * @return <code>T</code> T
         */
        T push(T value);

        /**
         * <p>移除并返回</p>
         *
         * @return <code>T</code> T
         */
        T pop();
    }
}
