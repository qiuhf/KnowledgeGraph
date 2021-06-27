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

import com.qiuhaifeng.datastructure.linkedlist.DoubleNode;

import java.util.EmptyStackException;
import java.util.Objects;

/**
 * <pre>
 *   用双向链表构实现栈结构
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class TwoLinkListImplementStack<E> implements IStack<E> {
    private DoubleNode<E> head;

    /**
     * <p>将一个value推到这个堆栈的顶部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    @Override
    public E push(E value) {
        if (isEmpty()) {
            this.head = new DoubleNode<>();
            this.head.setValue(value);
        } else {
            DoubleNode<E> top = new DoubleNode<>(value);
            top.setNext(this.head);
            this.head.setPrev(top);
            this.head = top;
        }

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
            throw new EmptyStackException();
        }

        E value = this.head.getValue();
        this.head = this.head.getNext();
        if (Objects.nonNull(this.head)) {
            this.head.setPrev(null);
        }
        return value;
    }

    /**
     * <p>返回栈顶部对象的值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E peek() {
        return this.head.getValue();
    }

    /**
     * <p>堆栈是否为空</p>
     *
     * @return <code>boolean</code>
     */
    @Override
    public boolean isEmpty() {
        return Objects.isNull(this.head);
    }

    // for test

    public static void main(String[] args) {
        IStack.logarithm(size -> new TwoLinkListImplementStack<>());
    }
}
