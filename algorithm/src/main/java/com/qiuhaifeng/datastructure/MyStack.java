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

import java.util.Objects;

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
public class MyStack {
    public static void main(String[] args) {
        int capacity = (int) (Math.random() * 20);
        verifyStack(new DoubleLinkListImplementStack<>(0), capacity);
        System.out.println("");
        verifyStack(new ArrayImplementStack<>(capacity), capacity);
    }

    /**
     * <p>验证自定义栈</p>
     *
     * @param stack    自定义栈
     * @param capacity 次数
     */
    private static void verifyStack(IStack<Integer> stack, int capacity) {
        for (int i = 0; i < capacity; i++) {
            System.out.print(stack.push(i) + " -> ");
        }
        System.out.println("null");
        for (int i = 0; i < capacity; i++) {
            System.out.print(stack.pop() + " <- ");
        }
    }

    interface IStack<E> {
        /**
         * <p>将一个value推到这个堆栈的顶部</p>
         *
         * @param value value
         * @return <code>T</code>
         */
        E push(E value);

        /**
         * <p>移除此堆栈顶部的对象并返回该对象作为此函数的值</p>
         *
         * @return <code>T</code>
         */
        E pop();
    }

    // for test

    /**
     * 双向链表实现栈
     *
     * @param <E>
     */
    static class DoubleLinkListImplementStack<E> implements IStack<E> {
        private DoubleNode<E> head;

        public DoubleLinkListImplementStack(E value) {
            this.head = new DoubleNode<>(value);
        }

        /**
         * <p>将一个value推到这个堆栈的顶部</p>
         *
         * @param value value
         * @return <code>T</code>
         */
        @Override
        public E push(E value) {
            DoubleNode<E> top = new DoubleNode<>(value);
            if (Objects.nonNull(this.head)) {
                this.head.setPrev(top);
                top.setNext(this.head);
            }
            this.head = top;
            return value;
        }

        /**
         * <p>移除此堆栈顶部的对象并返回该对象作为此函数的值</p>
         *
         * @return <code>T</code>
         */
        @Override
        public E pop() {
            if (Objects.isNull(this.head)) {
                throw new ArrayIndexOutOfBoundsException("Stack may be empty!");
            }

            E value = this.head.getValue();
            this.head = this.head.getNext();
            if (Objects.nonNull(this.head)) {
                this.head.setPrev(null);
            }
            return value;
        }
    }

    /**
     * 数组实现栈
     *
     * @param <E>
     */
    static class ArrayImplementStack<E> implements IStack<E> {
        private Object[] data;
        private int maxLen;
        private int index;

        public ArrayImplementStack(int capacity) {
            this.index = 0;
            this.maxLen = capacity;
            this.data = new Object[capacity];
        }

        /**
         * <p>将一个value推到这个堆栈的顶部</p>
         *
         * @param value value
         * @return <code>T</code>
         */
        @Override
        public E push(E value) {
            if (this.index >= this.maxLen) {
                throw new ArrayIndexOutOfBoundsException("Stack may be full");
            }
            this.data[this.index++] = value;
            return value;
        }

        /**
         * <p>移除此堆栈顶部的对象并返回该对象作为此函数的值</p>
         *
         * @return <code>T</code>
         */
        @Override
        public E pop() {
            if (this.index-- <= 0) {
                throw new ArrayIndexOutOfBoundsException("Stack may be empty!");
            }
            return (E) this.data[this.index];
        }
    }
}
