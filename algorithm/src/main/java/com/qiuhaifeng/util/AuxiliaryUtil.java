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

package com.qiuhaifeng.util;

import com.qiuhaifeng.datastructure.linkedlist.Node;

/**
 * 辅助工具
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-13
 **/
public class AuxiliaryUtil {
    private AuxiliaryUtil() {
    }

    /**
     * <p>随机生成范围中的一个数：[range, -range]</p>
     *
     * @param range 范围
     * @return <code>int</code>
     */
    public static Integer randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }

    /**
     * <p>随机生成范围中的一个正整数：[1, range]</p>
     *
     * @param range 范围
     * @return <code>int</code>
     */
    public static int randomPositiveNum(int range) {
        return (int) (Math.random() * range) + 1;
    }

    /**
     * <p>随机生成范围中的一个负整数：[-range, 0)</p>
     *
     * @param range 范围
     * @return <code>int</code>
     */
    public static int randomNegativeNum(int range) {
        return -(int) (Math.random() * (range + 1)) - 1;
    }

    /**
     * <p>随机生成范字符串数组</p>
     *
     * @param range  字符取值范围: [0, range]
     * @param length 字符串数组最大长度: [1, length]
     * @return <code>String[]</code>
     */
    public static String[] generateRandomStringArray(int range, int length) {
        String[] ans = new String[(int) (Math.random() * length) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(range, length);
        }
        return ans;
    }

    /**
     * <p>随机生成范围中的字符串</p>
     *
     * @param range  字符取值范围: [0, range]
     * @param length 字符串最大长度: [1, length]
     * @return <code>String</code>
     */
    public static String generateRandomString(int range, int length) {
        char[] chars = new char[(int) (Math.random() * length) + 1];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (Math.random() * (range + 1));
        }
        return String.valueOf(chars);
    }

    /**
     * <p>根据指定条件，生成随机数组</p>
     *
     * @param maxLen 数组最大长度
     * @param range  范围
     * @return <code>int[]</code> 数组
     */
    public static int[] generateRandomArray(int maxLen, int range) {
        return generateRandomArray(maxLen, range, 0);
    }

    /**
     * <p>根据指定条件，生成随机数组</p>
     *
     * @param maxLen 数组最大长度
     * @param range  范围
     * @param code   -1:负  0正/0/负 1正
     * @return <code>int[]</code> 数组
     */
    public static int[] generateRandomArray(int maxLen, int range, int code) {
        // Math.random()   [0,1)
        // Math.random() * N  [0,N)
        // (int)(Math.random() * N)  [0, N-1]
        int len = (int) ((maxLen + 1) * Math.random());
        int[] ints = new int[len];
        for (int i = 0; i < len; i++) {
            switch (code) {
                case -1:
                    ints[i] = randomNegativeNum(range);
                    break;
                case 1:
                    ints[i] = randomPositiveNum(range);
                    break;
                default:
                    ints[i] = randomNumber(range);
            }
        }
        return ints;
    }

    /**
     * <p>根据指定条件，生成头节点</p>
     *
     * @param depth 最大深度
     * @param range 最大值
     * @return <code>Node</code> 头节点
     */
    public static Node<Integer> generateRandomNode(int depth, int range) {
        if (depth == 0) {
            return null;
        }

        Node<Integer> head = new Node<>(randomNumber(range));
        Node<Integer> node = head;
        while (--depth > 0) {
            node.next = new Node<>(randomNumber(range));
            node = node.next;
        }

        return head;
    }

    /**
     * <p>拷贝数组</p>
     *
     * @param arr1 arr1
     * @return <code>int[]</code> 数组
     */
    public static int[] copyArray(int[] arr1) {
        int[] arr2 = new int[arr1.length];
        System.arraycopy(arr1, 0, arr2, 0, arr1.length);
        return arr2;
    }
}
