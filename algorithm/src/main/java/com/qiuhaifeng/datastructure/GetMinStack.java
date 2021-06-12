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

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 *  实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 *      1）pop、push、getMin操作的时间复杂度都是 O(1)。
 *      2）设计的栈类型可以使用现成的栈结构。
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-12
 **/
public class GetMinStack<E extends Comparable<E>> extends Stack<E> {
    private final Stack<E> mimValueStack;

    public GetMinStack() {
        this.mimValueStack = new Stack<>();
    }

    /**
     * <p>将一个value推到这个堆栈的顶部</p>
     *
     * @param item value
     * @return <code>E</code>
     */
    @Override
    public E push(E item) {
        super.push(item);
        if (this.mimValueStack.isEmpty()) {
            this.mimValueStack.push(item);
        } else {
            E value = this.mimValueStack.peek();
            // value <= item, value放栈顶
            // value > item, item放栈顶
            this.mimValueStack.push(value.compareTo(item) > 0 ? item : value);
        }
        return item;
    }

    /**
     * <p>移除此堆栈顶部的对象并返回该对象作为此函数的值</p>
     *
     * @return <code>E</code>
     */
    @Override
    public synchronized E pop() {
        E item = super.pop();
        this.mimValueStack.pop();
        return item;
    }

    /**
     * <p>获取栈中的最小值</p>
     *
     * @return <code>E</code>
     */
    public E getMin() {
        return this.mimValueStack.peek();
    }

    // for test

    public static void main(String[] args) {
        int range = 1_000;
        int oneTestNumData = 100;
        int testTime = 100_000;
        for (int i = 0; i < testTime; i++) {
            GetMinStack<Integer> minStack = new GetMinStack<>();
            List<Integer> dataList = new ArrayList<>();
            for (int j = 0; j < oneTestNumData; j++) {
                boolean empty = minStack.isEmpty() && dataList.isEmpty();
                if (empty || Math.random() > 0.5) {
                    int value = AuxiliaryUtil.randomNumber(range);
                    minStack.push(value);
                    dataList.add(value);
                    continue;
                }

                Integer min = dataList.stream().min(Integer::compareTo).get();
                if (!Objects.equals(minStack.getMin(), min)) {
                    System.err.format("GetMinStack oops! Actual: %s, Expect: %s\n", minStack.getMin(), min);
                    return;
                }

                Integer actual = minStack.pop();
                int index = dataList.size() - 1;
                Integer expect = dataList.remove(index);
                if (!Objects.equals(actual, expect)) {
                    System.err.format("GetDataStack oops!\nActual: %s\nExpect: %s\n", actual, expect);
                    return;
                }
            }
        }
    }

}
