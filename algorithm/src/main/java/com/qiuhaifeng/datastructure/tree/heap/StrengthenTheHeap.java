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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <pre>
 *     加强堆
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-23
 **/
public class StrengthenTheHeap<E> implements IHeap<E> {
    public static void main(String[] args) {
        new StrengthenTheHeap<Integer>().checked(12);
    }

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
    private NodeIndex<E> nodeIndex;
    /**
     * 比较器
     */
    private Comparator<? super E> comparator;

    /**
     * <p>构成函数</p>
     */
    public StrengthenTheHeap() {
        this((o1, o2) -> ((Comparable) o1).compareTo(o2));
    }

    /**
     * <p>构成函数</p>
     *
     * @param comparator 比较器,决定堆是大根堆/小根堆
     */
    public StrengthenTheHeap(Comparator<E> comparator) {
        this.heap = new LinkedList<>();
        this.nodeIndex = new NodeIndex<>();
        this.comparator = comparator;
    }

    /**
     * <p>新增</p>
     *
     * @param node node
     * @return <code>E</code>
     */
    @Override
    public E push(E node) {
        if (Objects.isNull(node)) {
            throw new NullPointerException("Node is null");
        }

        this.heap.add(node);
        this.nodeIndex.add(node, this.heapSize);
        this.heapInsert(this.heapSize++);
        return node;
    }

    /**
     * <p>移除并返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E pop() {
        E node = this.heap.get(0);
        this.swap(0, this.heapSize - 1);
        this.nodeIndex.remove(node, this.heapSize - 1);
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
        if (Objects.isNull(node)) {
            throw new NullPointerException("Node is null");
        }

        E replace = this.heap.get(this.heapSize - 1);
        int index = this.nodeIndex.get(node);
        if (index == -1) {
            return;
        }

        this.nodeIndex.remove(node, index);
        this.heap.remove(--this.heapSize);

        this.heap.set(index, replace);
        this.nodeIndex.upset(replace, index, this.heapSize);
        if (Objects.equals(node, replace)) {
            return;
        }

        this.heapInsert(index);
        this.heapify(index);
    }

    /**
     * <p>返回堆顶值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E peek() {
        return this.heap.get(0);
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
     * <p>是否包含指定元素</p>
     *
     * @param node node
     * @return <code>boolean</code>
     */
    public boolean contains(E node) {
        return this.nodeIndex.contains(node);
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
     *     根据比较器重建堆
     *     时间复杂度为O(N)
     * </pre>
     *
     * @param comparator 比较器
     */
    public void rebuild(Comparator<E> comparator) {
        this.comparator = comparator;
        for (int i = this.fromLow2Floor(); i >= 0; i--) {
            this.heapify(i);
        }
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
        while (left > 0 && left < this.heapSize) {
            int right = left + 1;
            int childIndex = right < this.heapSize && this.comparator.compare(this.heap.get(right),
                    this.heap.get(left)) < 0 ? right : left;
            if (this.comparator.compare(this.heap.get(index), this.heap.get(childIndex)) <= 0) {
                return;
            }
            this.swap(index, childIndex);
            index = childIndex;
            left = 1 + (index << 1);
        }
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

    /**
     * <pre>
     *     获取倒数第二层最后一个数下标
     * </pre>
     *
     * @return <code>int</code>
     */
    private int fromLow2Floor() {
        int high = this.heapSize;
        while ((high & (high - 1)) != 0) {
            high &= (high - 1);
        }
        return (high - 1) & (~1);
    }

    private void swap(int cur, int other) {
        // 堆值交换
        E curNode = this.heap.get(cur);
        E otherNode = this.heap.get(other);
        this.heap.set(cur, otherNode);
        this.heap.set(other, curNode);
        // 更新反向索引
        this.nodeIndex.upset(curNode, other, cur);
        this.nodeIndex.upset(otherNode, cur, other);
    }

    static class NodeIndex<E> {
        private HashMap<E, Integer> index;

        NodeIndex() {
            this.index = new HashMap<>();
        }

        private void add(E node, int cur) {
            Integer mark = this.index.get(node);
            // 记录新节点坐标
            int index = Objects.isNull(mark) ? 1 << cur : (mark | (1 << cur));
            this.index.put(node, index);
        }

        private void upset(E node, int cur, int oldIndex) {
            Integer mark = this.index.get(node);
            if (Objects.isNull(mark)) {
                return;
            }
            // 复位节点旧坐标，设置新坐标
            int index = mark & (~(1 << oldIndex)) | (1 << cur);
            this.index.put(node, index);
        }

        private int get(E node) {
            Integer mark = this.index.get(node);
            if (Objects.isNull(mark)) {
                return -1;
            }

            int index = 0;
            while ((mark & (1 << index)) == 0) {
                index++;
            }
            // 相同情况，取出最早加入的节点
            return index;
        }

        private boolean contains(E node) {
            return this.index.containsKey(node);
        }

        private void remove(E node, int cur) {
            Integer mark = this.index.get(node);
            if (Objects.isNull(mark)) {
                return;
            }
            // 复位节点坐标
            int index = mark & (~(1 << cur));
            if (index == 0) {
                this.index.remove(node);
            } else {
                this.index.put(node, index);
            }
        }
    }
}
