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

import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *   岛屿数量
 *     给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。岛屿总是被水包围，并且每座岛屿只能由水平方向
 * 和/或竖直方向上相邻的陆地连接形成。此外，你可以假设该网格的四条边均被水包围。
 *
 * 示例 1：
 *   输入：grid = [
 *     ['1','1','1','1','0'],
 *     ['1','1','0','1','0'],
 *     ['1','1','0','0','0'],
 *     ['0','0','0','0','0']
 *   ]
 *   输出：1
 *
 * 示例 2：
 *   输入：grid = [
 *     ['1','1','0','0','0'],
 *     ['1','1','0','0','0'],
 *     ['0','0','1','0','0'],
 *     ['0','0','0','1','1']
 *   ]
 *   输出：3
 *
 * 提示：
 *   m == grid.length
 *   n == grid[i].length
 *   1 <= m, n <= 300
 *   grid[i][j] 的值为 '0' 或 '1'
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-of-islands
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-26
 **/
public class M200NumberOfIsLands {
    public static void main(String[] args) {
        char[][] grid = {
                {'1', '1', '1', '0', '0'},
                {'0', '0', '0', '1', '0'},
                {'1', '1', '0', '0', '1'},
                {'1', '1', '0', '0', '1'},
                {'0', '0', '1', '0', '1'}
        };

        M200NumberOfIsLands numberOfIsLands = new M200NumberOfIsLands();
        int result = numberOfIsLands.infect(grid);
        int ans = numberOfIsLands.verify(grid);
        if (ans != result) {
            System.err.printf(Locale.ROOT, "Actual: %s, Expect: %s", result, ans);
        }
    }

    /**
     * @param grid: 岛屿
     * @return <code>int</code> 岛屿数量
     */
    public int infect(char[][] grid) {
        if (Objects.isNull(grid) || grid[0].length == 0) {
            return 0;
        }

        LandUnionFind landUnionFind = new LandUnionFind(grid);
        // 左和上方向 依次union
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == '1') {
                    if (col != 0 && grid[row][col - 1] == '1') {
                        landUnionFind.union(row, col - 1, row, col);
                    }
                    if (row != 0 && col < grid[row - 1].length && grid[row - 1][col] == '1') {
                        landUnionFind.union(row - 1, col, row, col);
                    }
                }
            }
        }
        return landUnionFind.getSets();
    }

    static class LandUnionFind {
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
         * 每行长度
         */
        private int[] line;
        /**
         * 一共有多少个集合
         */
        private int sets = 0;

        LandUnionFind(char[][] grid) {
            int len = grid.length;
            this.line = new int[len];
            this.line[0] = 0;
            int length = grid[0].length;
            for (int row = 1; row < len; row++) {
                // 4 7 12
                length += grid[row].length;
                // 0 4 7 12
                this.line[row] = length - grid[row].length;
            }

            this.parent = new int[length];
            this.size = new int[length];
            this.help = new int[length];
            for (int row = 0; row < len; row++) {
                for (int col = 0; col < grid[row].length; col++) {
                    if (grid[row][col] == '1') {
                        int index = indexOf(row, col);
                        this.parent[index] = index;
                        this.size[index] = 1;
                        this.sets++;
                    }
                }
            }
        }

        private int indexOf(int row, int col) {
            return this.line[row] + col;
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

        public void union(int row1, int col1, int row2, int col2) {
            int land1 = findFather(indexOf(row1, col1));
            int land2 = findFather(indexOf(row2, col2));
            if (land1 != land2) {
                if (this.size[land1] > this.size[land2]) {
                    this.size[land1] += this.size[land2];
                    this.parent[land2] = land1;
                } else {
                    this.size[land2] += this.size[land1];
                    this.parent[land1] = land2;
                }
                this.sets--;
            }
        }

        private int getSets() {
            return this.sets;
        }
    }

    private int verify(char[][] grid) {
        if (Objects.isNull(grid) || grid[0].length == 0) {
            return 0;
        }

        int landNum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    landNum++;
                    process(grid, i, j);
                }
            }
        }
        return landNum;
    }

    private void process(char[][] grid, int i, int j) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        process(grid, i - 1, j);
        process(grid, i + 1, j);
        process(grid, i, j - 1);
        process(grid, i, j + 1);
    }
}
