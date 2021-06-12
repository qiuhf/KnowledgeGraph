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

import java.util.LinkedList;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;

/**
 * <pre>
 *    队列：数据先进先出，好似排队
 *    1. 双向链表实现
 *    2. 数组实现
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class MyQueue {
    /**
     * <p>双向链表实现队列</p>
     *
     * @param <E>
     */
    static class DoubleLinkListImplementQueue<E> implements IQueue<E> {
        private DoubleNode<E> head;
        private DoubleNode<E> tail;

        /**
         * <p>将一个value添加到队列的头部</p>
         *
         * @param value value
         * @return <code>E</code>
         */
        @Override
        public E push(E value) {
            DoubleNode<E> node = new DoubleNode<>(value);
            if (isEmpty()) {
                this.tail = node;
                this.head = node;
                return value;
            } else {
                node.setNext(this.head);
                this.head.setPrev(node);
                this.head = node;
            }

            return value;
        }

        /**
         * <p>从队列的头部移除并返回一个value</p>
         *
         * @return <code>E</code>
         */
        @Override
        public E pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue may be empty!");
            }

            E value = this.tail.getValue();
            if (this.head.equals(this.tail)) {
                this.head = null;
                this.tail = null;
            } else {
                this.tail = tail.getPrev();
                this.tail.setNext(null);
            }

            return value;
        }

        /**
         * <p>队列是否为空</p>
         *
         * @return <code>boolean</code>
         */
        @Override
        public boolean isEmpty() {
            return Objects.isNull(this.head);
        }
    }

    /**
     * <p>环形数组实现队列</p>
     *
     * @param <E>
     */
    static class ArrayImplementQueue<E> implements IQueue<E> {
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
        public E push(E value) {
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
        public E pop() {
            if (this.size-- <= 0) {
                throw new ArrayIndexOutOfBoundsException(String.format(Locale.ROOT, "Queue may be empty! " +
                        "current index: %d", this.size ));
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
    }

    /**
     * IQueue
     *
     * @param <E>
     */
    interface IQueue<E> {
        /**
         * <p>将一个value添加到队列的头部</p>
         *
         * @param value value
         * @return <code>E</code>
         */
        E push(E value);

        /**
         * <p>从队列的头部移除并返回一个value</p>
         *
         * @return <code>E</code>
         */
        E pop();

        /**
         * <p>队列是否为空</p>
         *
         * @return <code>boolean</code>
         */
        boolean isEmpty();
    }

    // for test

    public static void main(String[] args) {
        int capacity = (int) (Math.random() * 20);
        verifyStack(new DoubleLinkListImplementQueue<>(), capacity);
        System.out.println("");

        verifyStack(new ArrayImplementQueue<>(capacity), capacity);
        System.out.println("");

        int range = 20_000;
        int oneTestDataNum = 100;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            Queue<Integer> queue = new LinkedList<>();
            IQueue<Integer> myQueue1 = new ArrayImplementQueue<>(oneTestDataNum);
            IQueue<Integer> myQueue2 = new DoubleLinkListImplementQueue<>();
            for (int j = 0; j < oneTestDataNum; j++) {
                boolean empty = queue.isEmpty() && myQueue1.isEmpty() && myQueue2.isEmpty();
                if (empty || Math.random() > 0.5) {
                    int value = (int) (Math.random() * range);
                    queue.offer(value);
                    myQueue1.push(value);
                    myQueue2.push(value);
                    continue;
                }

                Integer pop = queue.remove();
                Integer pop1 = myQueue1.pop();
                if (!Objects.equals(pop1, pop)) {
                    System.err.format("ArrayImplementQueue oops! Actual: %s, Expect: %s\n", pop1, pop);
                    return;
                }

                Integer pop2 = myQueue2.pop();
                if (!Objects.equals(pop2, pop)) {
                    System.err.format("DoubleLinkListImplementQueue oops! Actual: %s, Expect: %s\n", pop1, pop);
                    return;
                }
            }
        }

    }

    /**
     * <p>验证自定义队列</p>
     *
     * @param queue    自定义队列
     * @param capacity 次数
     */
    private static void verifyStack(IQueue<Integer> queue, int capacity) {
        for (int i = 0; i < capacity; i++) {
            System.out.print(queue.push(i) + " -> ");
        }
        System.out.println("null");
        for (int i = 0; i < capacity; i++) {
            System.out.print(queue.pop() + " <- ");
        }
        System.out.print("null");
    }
}
