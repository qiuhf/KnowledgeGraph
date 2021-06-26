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

package com.qiuhaifeng.datastructure.stack;

import java.util.Locale;

/**
 * <pre>
 *    栈：数据先进后出，犹如弹匣
 *    1. 双向链表实现
 *    2. 数组实现
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class ArrayImplementStack<E> implements IStack<E> {
    private final int capacity;
    private Object[] data;
    private int index;

    public ArrayImplementStack(int capacity) {
        this.index = 0;
        this.capacity = capacity;
        this.data = new Object[this.capacity];
    }

    /**
     * <p>将一个value推到这个堆栈的顶部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    @Override
    public E push(E value) {
        if (this.index >= this.capacity) {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ROOT, "Stack may be full! " +
                    "capacity: %d , current index: %d", this.capacity, this.index + 1));
        }
        this.data[this.index++] = value;
        return value;
    }

    /**
     * <p>移除此堆栈顶部的对象并返回该对象作为此函数的值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E pop() {
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException(String.format(Locale.ROOT, "Stack may be empty! " +
                    "current index: %d", this.index - 1));
        }

        return (E) this.data[--this.index];
    }

    /**
     * <p>返回栈顶部对象的值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E peek() {
        return (E) this.data[this.index - 1];
    }

    /**
     * <p>堆栈是否为空</p>
     *
     * @return <code>boolean</code>
     */
    @Override
    public boolean isEmpty() {
        return this.index <= 0;
    }

    // for test

    public static void main(String[] args) {
        new ArrayImplementStack<>(6).checked(6);
        new ArrayImplementStack<>(100).verify(aVoid -> new ArrayImplementStack<>(100));
    }
}
