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

package com.qiuhaifeng.leetcode;

/**
 * <pre>
 *    班上有 N 名学生。其中有些人是朋友，有些则不是。他们的友谊具有是传递性。如果已知 A 是 B 的朋友，B 是 C 的朋友，那么我们可以认为
 *  A 也是 C 的朋友。所谓的朋友圈，是指所有朋友的集合。给定一个 N * N 的矩阵 M，表示班级中学生之间的朋友关系。如果M[i][j] = 1，表示
 *  已知第 i 个和 j 个学生互为朋友关系，否则为不知道。你必须输出所有学生中的已知的朋友圈总数。
 *
 *  N 在[1,200]的范围内。对于所有学生，有M[i][i] = 1。
 * 如果有M[i][j] = 1，则有M[j][i] = 1。
 *
 * 输入:
 *  [[1,1,0],
 *   [1,1,0],
 *   [0,0,1]]
 * 输出:
 *      2
 * 说明：
 *     已知学生0和学生1互为朋友，他们在一个朋友圈。
 *     第2个学生自己在一个朋友圈。所以返回2。
 *
 * 本题为lintcode原题
 * 测试链接：https://www.lintcode.com/problem/1179/
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-26
 **/
public class M547FriendCircles {
    public static void main(String[] args) {
        int[][] M = {
                {1, 1, 0, 1, 0},
                {1, 1, 0, 0, 1},
                {0, 0, 1, 1, 0},
                {1, 0, 1, 1, 0},
                {0, 1, 1, 0, 1}
        };
        int circleNum = new M547FriendCircles().findCircleNum(M);
        System.out.println(circleNum);
    }

    /**
     * @param M: a matrix
     * @return <code>int</code> 所有学生的朋友圈总数
     */
    public int findCircleNum(int[][] M) {
        int len = M.length;
        FriendCircles circles = new FriendCircles(len);
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (M[i][j] == 1) { // i和j互相认识
                    circles.union(i, j);
                }
            }
        }
        return circles.getSets();
    }

    static class FriendCircles {
        /**
         * parent[i] = k ： i的父亲是k
         */
        private int[] parent;
        /**
         * i所在的集合大小是多少
         * Note: size[i] = k ： 如果i是代表节点，size[i]才有意义，否则无意义
         */
        private int[] size;
        /**
         * 辅助结构
         */
        private int[] help;
        /**
         * 一共有多少个集合
         */
        private int sets;

        FriendCircles(int len) {
            this.parent = new int[len];
            this.size = new int[len];
            this.help = new int[len];
            this.sets = len;
            for (int i = 0; i < len; i++) {
                this.parent[i] = i;
                this.size[i] = 1;
            }
        }

        private int findFather(int i) {
            int index = 0;
            while (i != this.parent[i]) {
                this.help[index++] = i;
                i = this.parent[i];
            }

            for (index--; index >= 0; index--) {
                this.parent[this.help[index]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int fi = findFather(i);
            int fj = findFather(j);
            if (fi != fj) {
                if (this.size[fi] > this.size[fj]) {
                    this.size[fi] += this.size[fj];
                    this.parent[fj] = fi;
                } else {
                    this.size[fj] += this.size[fi];
                    this.parent[fi] = fj;
                }
                this.sets--;
            }
        }

        private int getSets() {
            return this.sets;
        }
    }
}
