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
import java.util.Stack;

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
    /**
     * <p>双向链表实现栈</p>
     *
     * @param <E>
     */
    static class DoubleLinkListImplementStack<E> implements IStack<E> {
        private DoubleNode<E> head;

        public DoubleLinkListImplementStack() {
        }

        /**
         * <p>将一个value推到这个堆栈的顶部</p>
         *
         * @param value value
         * @return <code>T</code>
         */
        @Override
        public E push(E value) {
            if (isEmpty()) {
                this.head = new DoubleNode<>();
                this.head.setValue(value);
                return value;
            }

            DoubleNode<E> top = new DoubleNode<>(value);
            top.setNext(this.head);
            this.head.setPrev(top);
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
            if (isEmpty()) {
                throw new ArrayIndexOutOfBoundsException("Stack may be empty!");
            }

            E value = this.head.getValue();
            this.head = this.head.getNext();
            if (Objects.nonNull(this.head)) {
                this.head.setPrev(null);
            }
            return value;
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
    }

    /**
     * <p>固定数组大小实现栈</p>
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
            if (!isEmpty()) {
                return (E) this.data[--this.index];
            }

            throw new ArrayIndexOutOfBoundsException("Stack may be empty!");
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
    }

    /**
     * IStack
     *
     * @param <E>
     */
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

        /**
         * <p>堆栈是否为空</p>
         *
         * @return <code>boolean</code>
         */
        boolean isEmpty();
    }

    // for test

    public static void main(String[] args) {
        int capacity = (int) (Math.random() * 20);

        verifyStack(new DoubleLinkListImplementStack<>(), capacity);
        System.out.println("");

        verifyStack(new ArrayImplementStack<>(capacity), capacity);
        System.out.println("");

        int range = 20_000;
        int oneTestDataNum = 100;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            Stack<Integer> stack = new Stack<>();
            ArrayImplementStack<Integer> myStack1 = new ArrayImplementStack<>(oneTestDataNum);
            DoubleLinkListImplementStack<Integer> myStack2 = new DoubleLinkListImplementStack<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                int value = (int) (Math.random() * range);
                if (stack.isEmpty() || Math.random() > 0.5) {
                    stack.push(value);
                    myStack1.push(value);
                    myStack2.push(value);
                    continue;
                }

                Integer pop = stack.pop();
                Integer pop1 = myStack1.pop();
                if (!Objects.equals(pop1, pop)) {
                    System.err.format("ArrayImplementStack oops! Actual: %s, Expect: %s\n", pop1, pop);
                    return;
                }

                Integer pop2 = myStack2.pop();
                if (!Objects.equals(pop2, pop)) {
                    System.err.format("DoubleLinkListImplementStack oops! Actual: %s, Expect: %s\n", pop1, pop);
                    return;
                }
            }
        }
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
        System.out.print("null");
    }
}