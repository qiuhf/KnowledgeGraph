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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * <pre>
 * 岛屿数量II
 *    给定 n, m, 分别代表一个二维矩阵的行数和列数, 并给定一个大小为 k 的二元数组A. 初始二维矩阵全0. 二元数组A内的k个元素代表k次操作,
 * 设第i个元素为 (A[i].x, A[i].y), 表示把二维矩阵中下标为A[i].x行A[i].y列的元素由海洋变为岛屿. 问在每次操作之后, 二维矩阵中岛屿的
 * 数量. 你需要返回一个大小为k的数组.
 *
 * 设定0表示海洋, 1代表岛屿, 并且上下左右相邻的1为同一个岛屿.
 *
 * 样例
 *   样例 1:
 *    输入: n = 4, m = 5, A = [[1,1],[0,1],[3,3],[3,4]]
 *    输出: [1,1,2,2]
 *    解释:
 *        0        1        2        3        4
 *       00000    00000    01000    01000    01000
 *       00000    01000    01000    01000    01000
 *       00000    00000    00000    00000    00000
 *       00000    00000    00000    00010    00011
 *
 *  样例 2:
 *    输入: n = 3, m = 3, A = [[0,0],[0,1],[2,2],[2,1]]
 *    输出: [1,1,2,2]
 *
 * 来源：本题为lintcode原题
 * 链接：https://www.lintcode.com/problem/434/
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-26
 **/
public class M434NumberOfIsLandsII {
    public static void main(String[] args) {
        Point[] points = new Point[4];
        points[0] = new Point(1, 1);
        points[1] = new Point(0, 1);
        points[2] = new Point(3, 3);
        points[3] = new Point(3, 4);

        M434NumberOfIsLandsII numberOfIsLands = new M434NumberOfIsLandsII();
        System.out.println(numberOfIsLands.numIslands2(4, 5, points));
    }

    /**
     * @param n         An integer
     * @param m         An integer
     * @param operators an array of point
     * @return an integer array
     */
    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        List<Integer> ans = new ArrayList<>();
        if (Objects.isNull(operators)) {
            return ans;
        }

        LandUnionFind landUnionFind = new LandUnionFind(n, m);
//        LandUnionFind2 landUnionFind = new LandUnionFind2();
        for (Point point : operators) {
            ans.add(landUnionFind.connect(point));
        }

        return ans;
    }

    /**
     * 行和列不是特别大时适用
     */
    static class LandUnionFind {
        private int[] parent;
        /**
         * 用size[index]是否等于1，判断该坐标是否岛屿
         */
        private int[] size;
        private int[] help;
        private int row;
        private int col;
        private int sets = 0;

        LandUnionFind(int r, int c) {
            this.row = r;
            this.col = c;
            int len = this.row * this.col;
            this.parent = new int[len];
            this.size = new int[len];
            this.help = new int[len];
        }

        private int indexOf(int row, int col) {
            return row * this.col + col;
        }

        private int findFather(int index) {
            int i = 0;
            while (index != this.parent[index]) {
                this.help[i++] = index;
                index = this.parent[index];
            }

            for (--i; i >= 0; i--) {
                this.parent[this.help[i]] = index;
            }

            return index;
        }

        private void union(int x1, int y1, int x2, int y2) {
            if (x1 < 0 || x1 == this.row || y1 < 0 || y1 == this.col || x2 < 0 || x2 == this.row || y2 < 0 || y2 == this.col) {
                return;
            }
            int index1 = indexOf(x1, y1);
            int index2 = indexOf(x2, y2);
            if (this.size[index1] == 0 || this.size[index2] == 0) {
                return;
            }

            int p1 = findFather(index1);
            int p2 = findFather(index2);
            if (p1 != p2) {
                if (this.size[p1] > this.size[p2]) {
                    this.size[p1] += this.size[p2];
                    this.parent[p2] = p1;
                } else {
                    this.size[p2] += this.size[p1];
                    this.parent[p1] = p2;
                }
                this.sets--;
            }
        }

        public int connect(Point point) {
            int index = indexOf(point.x, point.y);
            if (this.size[index] != 1) {
                this.parent[index] = index;
                this.size[index] = 1;
                this.sets++;
                union(point.x - 1, point.y, point.x, point.y);
                union(point.x + 1, point.y, point.x, point.y);
                union(point.x, point.y - 1, point.x, point.y);
                union(point.x, point.y + 1, point.x, point.y);
            }
            return this.sets;
        }
    }

    /**
     * 行和列特别大时适用
     */
    static class LandUnionFind2 {
        private Map<String, String> parentMap;
        private Map<String, Integer> sizeMap;
        private int sets;
        private Stack<String> stack;

        public LandUnionFind2() {
            this.parentMap = new HashMap<>();
            this.sizeMap = new HashMap<>();
            this.stack = new Stack<>();
            this.sets = 0;
        }

        private String key(int x, int y) {
            return x + "_" + y;
        }

        public int connect(Point point) {
            String key = this.key(point.x, point.y);
            if (!this.sizeMap.containsKey(key)) {
                this.parentMap.put(key, key);
                this.sizeMap.put(key, 1);
                this.sets++;

                union(this.key(point.x - 1, point.y), key);
                union(this.key(point.x + 1, point.y), key);
                union(this.key(point.x, point.y - 1), key);
                union(this.key(point.x, point.y + 1), key);
            }
            return this.sets;
        }

        private void union(String key1, String key2) {
            if (!this.sizeMap.containsKey(key1)) {
                return;
            }

            String p1 = this.findFather(key1);
            String p2 = this.findFather(key2);
            if (!p1.equals(p2)) {
                Integer xSize = this.sizeMap.get(p1);
                Integer ySize = this.sizeMap.get(p2);
                String big = xSize > ySize ? p1 : p2;
                String small = big.equals(p1) ? p2 : p1;
                this.parentMap.put(small, big);
                this.sizeMap.put(big, xSize + ySize);
                this.sets--;
            }
        }

        private String findFather(String key) {
            String cur = this.parentMap.get(key);
            while (!key.equals(cur)) {
                this.stack.push(cur);
                cur = this.parentMap.get(cur);
            }
            while (!this.stack.isEmpty()) {
                this.parentMap.put(stack.pop(), key);
            }

            return key;
        }
    }

    static class Point {
        int x;
        int y;

        Point(int a, int b) {
            x = a;
            y = b;
        }
    }
}
