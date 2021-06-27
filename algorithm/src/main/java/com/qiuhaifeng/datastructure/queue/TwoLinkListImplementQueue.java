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

import com.qiuhaifeng.datastructure.linkedlist.DoubleNode;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <pre>
 *    用双向链表结构实现队列结构
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-05
 **/
public class TwoLinkListImplementQueue<E> implements IQueue<E> {
    private DoubleNode<E> head;
    private DoubleNode<E> tail;

    /**
     * <p>将一个value添加到队列的头部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    @Override
    public E offer(E value) {
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
    public E poll() {
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

    public static void main(String[] args) {
        IQueue.logarithm(size -> new TwoLinkListImplementQueue<>());
    }
}
