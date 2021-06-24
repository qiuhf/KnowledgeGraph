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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * <pre>
 *     加强堆
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-23
 **/
public class StrengthenTheHeap<E> {
    /**
     * 堆
     */
    private List<E> heap;
    /**
     * 堆大小
     */
    private int heapSize;
    /**
     * 反向索引
     */
    private HashMap<E, Integer> indexMap;
    /**
     * 比较器，大根堆/小根堆由此比较器决定
     */
    private Comparator<? super E> comparator;

    /**
     * <p>构成函数</p>
     *
     * @param comparator 比较器
     */
    public StrengthenTheHeap(Comparator<? super E> comparator) {
        this.heap = new LinkedList<E>();
        this.indexMap = new HashMap<>();
        this.comparator = comparator;
    }

    /**
     * <p>堆是否空</p>
     *
     * @return <code>boolean</code>
     */
    public boolean isEmpty() {
        return this.heapSize == 0;
    }

    /**
     * <p>堆大小</p>
     *
     * @return <code>int</code>
     */
    public int size() {
        return this.heapSize;
    }

    /**
     * <p>是否包含指定元素</p>
     *
     * @param node node
     * @return <code>boolean</code>
     */
    public boolean contains(E node) {
        return this.indexMap.containsKey(node);
    }

    /**
     * <p>新增</p>
     *
     * @param node node
     * @return <code>E</code>
     */
    public E push(E node) {
        this.heap.add(node);
        this.indexMap.put(node, this.heapSize);
        this.heapInsert(this.heapSize++);
        return node;
    }

    /**
     * <p>返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    public E peek() {
        return this.heap.get(0);
    }

    /**
     * <p>移除并返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    public E pop() {
        E node = this.heap.get(0);
        this.swap(0, this.heapSize - 1);
        this.indexMap.remove(node);
        this.heap.remove(--this.heapSize);
        this.heapify(0);
        return node;
    }

    /**
     * <p>移除指定元素</p>
     *
     * @param node node
     */
    public void remove(E node) {
        Integer index = this.indexMap.get(node);
        // 如果移除是最后一个元素直接返回
        if (index == this.heapSize - 1) {
            this.indexMap.remove(node);
            this.heap.remove(--this.heapSize);
            return;
        }

        E replace = this.heap.get(this.heapSize - 1);
        this.heap.set(index, replace);
        this.indexMap.put(replace, index);

        heapInsert(index);
        heapify(index);
    }

    /**
     * <p>获取堆中所有元素</p>
     *
     * @return <code>List<E></code>
     */
    public List<E> getAllElements() {
        return new ArrayList<>(this.heap);
    }

    /**
     * <pre>
     *      从index位置，往下看，不断的下沉
     *      时间复杂度：O(logN)
     * </pre>
     * smaller
     *
     * @param index 当前位置
     */
    private void heapify(Integer index) {
        int left = 1 + (index << 1);
        // 溢出跳出循环
        while (left > 0 && index < this.heapSize) {
            int right = left + 1;
            int childIndex = right < this.heapSize && this.comparator.compare(this.heap.get(right),
                    this.heap.get(left)) < 0 ? right : left;
            if (this.comparator.compare(this.heap.get(index), this.heap.get(childIndex)) >= 0) {
                return;
            }
            swap(index, childIndex);
            index = childIndex;
            left = 1 + (index << 1);
        }
    }

    /**
     * <pre>
     *     检查当前节点是否位于最底层
     * </pre>
     *
     * @param index 当前节点下标
     * @return <code>int</code>
     */
    private int checkLowestLevel(int index) {
        int high = this.heapSize;
        while ((index & (index - 1)) != 0) {
            index &= (index - 1);
            high++;
        }
        return Math.min((high - 1) & (~1), index);
    }

    /**
     * <pre>
     *     新加进来的数，现在停在了index位置，请依次往上移动
     *     时间复杂度：O(logN)
     * </pre>
     *
     * @param index 当前位置
     */
    private void heapInsert(int index) {
        while (this.comparator.compare(this.heap.get(index), this.heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void swap(int cur, int other) {
        // 堆值交换
        E curNode = this.heap.get(cur);
        E otherNode = this.heap.get(other);
        this.heap.set(cur, otherNode);
        this.heap.set(other, curNode);
        // 更新反向索引
        this.indexMap.put(curNode, other);
        this.indexMap.put(curNode, other);
    }
}
