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
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Queue;
import java.util.Stack;

/**
 * <pre>
 *     用栈结构实现队列结构
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-13
 **/
public class StackImplementQueue<E> {
    private final Stack<E> stackPush;
    private final Stack<E> stackPop;

    public StackImplementQueue() {
        this.stackPush = new Stack<>();
        this.stackPop = new Stack<>();
    }

    /**
     * <p>将一个value添加到队列的头部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    public E offer(E value) {
        this.stackPush.push(value);
        this.pushToPop();
        return value;
    }

    /**
     * <p>从队列的头部移除并返回一个value</p>
     *
     * @return <code>E</code>
     */
    public E poll() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        this.pushToPop();
        return this.stackPop.pop();
    }

    /**
     * <p>从队列的头部返回一个value</p>
     *
     * @return <code>E</code>
     */
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        this.pushToPop();
        return this.stackPop.peek();
    }

    /**
     * <p>队列是否为空</p>
     *
     * @return <code>boolean</code>
     */
    public boolean isEmpty() {
        return this.stackPush.isEmpty() && this.stackPop.isEmpty();
    }

    /**
     * <p>将原栈所有元素追加到新栈中</p>
     */
    private void pushToPop() {
        if (!this.stackPop.isEmpty()) {
            return;
        }

        do {
            this.stackPop.push(this.stackPush.pop());
        } while (!this.stackPush.isEmpty());
    }

    // for test

    public static void main(String[] args) {
        int range = 2000;
        int testTime = 100_000;
        Queue<Integer> queue = new LinkedList<>();
        StackImplementQueue<Integer> myQueue = new StackImplementQueue<>();
        for (int i = 0; i < testTime; i++) {
            boolean empty = queue.isEmpty() && myQueue.isEmpty();
            if (empty || Math.random() > 0.5) {
                int value = (int) (Math.random() * range);
                queue.offer(value);
                myQueue.offer(value);
                continue;
            }

            if (Math.random() > 0.35 && !Objects.equals(queue.peek(), myQueue.peek())) {
                System.err.format("StackImplementQueue oops! Actual: %s, Expect: %s\n", myQueue.peek(), queue.peek());
                return;
            }

            Integer pop = queue.poll();
            Integer pop1 = myQueue.poll();
            if (!Objects.equals(pop, pop1)) {
                System.err.format("StackImplementQueue oops! Actual: %s, Expect: %s\n", pop1, pop);
                return;
            }
        }
    }
}
