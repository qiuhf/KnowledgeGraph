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

package com.qiuhaifeng.datastructure.queue;

import java.util.Locale;

/**
 * <pre>
 *   用数组结构实现队列结构
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class ArrayImplementQueue<E> implements IQueue<E> {
    private Object[] data;
    private final int capacity;
    private int size;
    private int pushIdx;
    private int popIdx;

    public ArrayImplementQueue(int capacity) {
        this.capacity = capacity;
        this.data = new Object[capacity];
    }

    /**
     * <p>将一个value添加到队列的头部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    @Override
    public E offer(E value) {
        if (++this.size > this.capacity) {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ROOT, "Queue may be full! " +
                    "capacity: %d , current index: %d", this.capacity, this.size));
        }
        this.data[this.popIdx] = value;
        this.popIdx = this.nextIndex(this.popIdx);
        return value;
    }

    /**
     * <p>从队列的头部移除并返回一个value</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E poll() {
        if (this.size-- <= 0) {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ROOT, "Queue may be empty! " +
                    "current index: %d", this.size));
        }

        E value = (E) this.data[this.pushIdx];
        this.pushIdx = this.nextIndex(this.pushIdx);
        return value;
    }

    /**
     * <p>队列是否为空</p>
     *
     * @return <code>boolean</code>
     */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * <p>计算下一个指针值</p>
     *
     * @param index 当前指针
     * @return <code>int</code>
     */
    private int nextIndex(int index) {
        return ++index >= this.capacity ? 0 : index;
    }

    public static void main(String[] args) {
        IQueue.logarithm(ArrayImplementQueue::new);
    }
}
