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

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * <pre>
 *     用队列结构实现栈结构
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-13
 **/
public class QueueImplementStack<E> implements IStack<E> {
    private final Queue<E> queue;
    private final Queue<E> help;

    public QueueImplementStack() {
        this.queue = new LinkedList<>();
        this.help = new LinkedList<>();
    }

    /**
     * <p>将一个value推到这个堆栈的顶部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    @Override
    public E push(E value) {
        if (this.help.isEmpty()) {
            this.exchange(this.help, this.queue, value);
        } else {
            this.exchange(this.queue, this.help, value);
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

        return this.queue.isEmpty() ? this.help.poll() : this.queue.poll();
    }

    /**
     * <p>返回此堆栈顶部对象的值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return this.queue.isEmpty() ? this.help.peek() : this.queue.peek();
    }

    /**
     * <p>堆栈是否为空</p>
     *
     * @return <code>boolean</code>
     */
    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty() && this.help.isEmpty();
    }

    /**
     * <p>将原队列所有元素追加到新队列中</p>
     *
     * @param queue  新队列
     * @param origin 原队列
     * @param value  值
     */
    private void exchange(Queue<E> queue, Queue<E> origin, E value) {
        queue.offer(value);
        while (!origin.isEmpty()) {
            queue.offer(origin.poll());
        }
    }

    public static void main(String[] args) {
        IStack.logarithm(size -> new QueueImplementStack<>());
    }
}
