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

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.function.Function;

/**
 * <pre>
 *    队列：数据先进先出，好似排队
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public interface IQueue<E> {
    /**
     * <p>将一个value添加到队列的头部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    E offer(E value);

    /**
     * <p>从队列的头部移除并返回一个value</p>
     *
     * @return <code>E</code>
     */
    E poll();

    /**
     * <p>队列是否为空</p>
     *
     * @return <code>boolean</code>
     */
    boolean isEmpty();

    /**
     * <p>对数器</p>
     */
    default void verify(Function<Void, IQueue<Integer>> callback) {
        int range = 20_000;
        int oneTestDataNum = 100;
        int testTime = 100_000;

        for (int i = 0; i < testTime; i++) {
            Queue<Integer> queue = new LinkedList<>();
            IQueue myQueue = callback.apply(null);
            for (int j = 0; j < oneTestDataNum; j++) {
                boolean empty = queue.isEmpty() && myQueue.isEmpty();
                if (empty || Math.random() > 0.5) {
                    Integer value = (int) (Math.random() * range);
                    queue.offer(value);
                    myQueue.offer((E) value);
                    continue;
                }

                Integer pop = queue.remove();
                Integer pop1 = (Integer) myQueue.poll();
                if (!Objects.equals(pop1, pop)) {
                    System.err.format("oops! Actual: %s, Expect: %s\n", pop1, pop);
                    return;
                }
            }
        }
        System.out.println("Nice!");
    }

    /**
     * <p>验证自定义队列</p>
     *
     * @param capacity 次数
     */
    default void checked(int capacity) {
        for (Integer i = 0; i < capacity; i++) {
            System.out.print(this.offer((E) i) + " -> ");
        }
        System.out.println("null");
        for (int i = 0; i < capacity; i++) {
            System.out.print(this.poll() + " <- ");
        }
        System.out.print("null\n");
    }
}