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

package com.qiuhaifeng.datastructure.tree.trie;

import com.qiuhaifeng.util.AuxiliaryUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

/**
 * <pre>
 *  前缀树(PrefixTree /Trie)
 *     1. 单个字符串中，字符从前到后的加到一棵多叉树上
 *     2. 字符放在路上，节点上有专属的数据项（常见的是pass和end值）
 *     3. 所有样本都这样添加，如果没有路就新建，如有路就复用
 *     4. 沿途节点的pass值增加1，每个字符串结束时来到的节点end值增加1
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-27
 **/
public interface ITrieOperation {
    /**
     * <p>添加某个字符串，可以重复添加</p>
     *
     * @param word 字符串
     */
    void insert(String word);

    /**
     * <p>删掉某个字符串，可以重复删除</p>
     *
     * @param word 字符串
     */
    void delete(String word);

    /**
     * <p>查询某个字符串在结构中有几个</p>
     *
     * @param word 字符串
     * @return <code>int</code>
     */
    int search(String word);

    /**
     * <p>查询有多少个字符串，是以word做前缀的</p>
     *
     * @param word 字符串
     * @return <code>int</code>
     */
    int prefixNumber(String word);

    /**
     * <p>对数器</p>
     */
    static void verify(Function<Integer, ITrieOperation> callback) {
        int range = 100;
        int length = 20;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = AuxiliaryUtil.generateRandomStringArray(range, length);
            CheckOperation right = new CheckOperation();
            ITrieOperation trie = callback.apply(range + 1);
            for (int j = 0; j < arr.length; j++) {
                switch ((int) (Math.random() * 4) + 1) {
                    case 1:
                        trie.insert(arr[j]);
                        right.insert(arr[j]);
                        break;
                    case 2:
                        trie.delete(arr[j]);
                        right.delete(arr[j]);
                        break;
                    case 3:
                        if (trie.search(arr[j]) != right.search(arr[j])) {
                            System.out.printf(Locale.ROOT, "Oops search!\nString[]: %s\nWord: %s\nActual: %d\n" +
                                    "Except: %d\n", Arrays.toString(arr), arr[j], trie.search(arr[j]), right.search(arr[j]));
                            return;
                        }
                        break;
                    default:
                        if (trie.prefixNumber(arr[j]) != right.prefixNumber(arr[j])) {
                            System.out.printf(Locale.ROOT, "Oops prefixNumber!\nString[]: %s\nWord: %s\nActual: %d\n" +
                                    "Except: %d\n", Arrays.toString(arr), arr[j], trie.prefixNumber(arr[j]), right.prefixNumber(arr[j]));
                            return;
                        }
                }
            }
        }
        System.out.println("Nice!");
    }

    // for test

    class CheckOperation implements ITrieOperation {
        private Map<String, Integer> box;

        public CheckOperation() {
            this.box = new HashMap<>();
        }

        /**
         * <p>添加某个字符串，可以重复添加</p>
         *
         * @param word 字符串
         */
        @Override
        public void insert(String word) {
            if (Objects.isNull(word)) {
                return;
            }

            Integer count = this.box.get(word);
            if (Objects.isNull(count)) {
                this.box.put(word, 1);
            } else {
                this.box.put(word, count + 1);
            }
        }

        /**
         * <p>删掉某个字符串，可以重复删除</p>
         *
         * @param word 字符串
         */
        @Override
        public void delete(String word) {
            if (Objects.isNull(word) || !this.box.containsKey(word)) {
                return;
            }

            Integer count = this.box.get(word);
            if (count == 1) {
                this.box.remove(word);
            } else {
                this.box.put(word, count - 1);
            }
        }

        /**
         * <p>查询某个字符串在结构中有几个</p>
         *
         * @param word 字符串
         * @return <code>int</code>
         */
        @Override
        public int search(String word) {
            return Optional.ofNullable(this.box.get(word)).orElse(0);
        }

        /**
         * <p>查询有多少个字符串，是以word做前缀的</p>
         *
         * @param word 字符串
         * @return <code>int</code>
         */
        @Override
        public int prefixNumber(String word) {
            int count = 0;
            for (Map.Entry<String, Integer> entry : this.box.entrySet()) {
                String key = entry.getKey();
                if (key.startsWith(word)) {
                    count += entry.getValue();
                }
            }
            return count;
        }
    }
}
