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

import java.util.Objects;
import java.util.Stack;
import java.util.function.Function;

/**
 * <pre>
 *    栈：数据先进后出，犹如弹匣
 *    1. 双向链表实现
 *    2. 数组实现
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public interface IStack<E> {
    /**
     * <p>将一个value推到栈的顶部</p>
     *
     * @param value value
     * @return <code>E</code>
     */
    E push(E value);

    /**
     * <p>移除栈顶部的对象并返回该对象作为此函数的值</p>
     *
     * @return <code>E</code>
     */
    E pop();

    /**
     * <p>返回栈顶部对象的值</p>
     *
     * @return <code>E</code>
     */
    E peek();

    /**
     * <p>栈是否为空</p>
     *
     * @return <code>boolean</code>
     */
    boolean isEmpty();

    /**
     * <p>对数器</p>
     *
     * @param callback callback
     */
    default void verify(Function<Void, IStack<Integer>> callback) {
        int range = 20_000;
        int oneTestDataNum = 100;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            Stack<Integer> stack = new Stack<>();
            IStack<Integer> myStack = callback.apply(null);
            for (int j = 0; j < oneTestDataNum; j++) {
                boolean empty = stack.isEmpty() && myStack.isEmpty();
                if (empty || Math.random() > 0.5) {
                    int value = (int) (Math.random() * range);
                    stack.push(value);
                    myStack.push(value);
                    continue;
                }

                if (!Objects.equals(stack.peek(), myStack.peek())) {
                    System.err.format("Oops peek! Actual: %s, Expect: %s\n", stack.peek(), myStack.peek());
                    return;
                }
                Integer pop = stack.pop();
                Integer pop1 = myStack.pop();
                if (!Objects.equals(pop1, pop)) {
                    System.err.format("Oops pop! Actual: %s, Expect: %s\n", pop1, pop);
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
            System.out.print(this.push((E) i) + " -> ");
        }
        System.out.println("null");
        for (int i = 0; i < capacity; i++) {
            System.out.print(this.pop() + " <- ");
        }
        System.out.print("null\n");
    }
}