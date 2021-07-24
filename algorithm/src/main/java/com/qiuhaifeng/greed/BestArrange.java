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

package com.qiuhaifeng.greed;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *     一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。给你每一个项目开始的时间和结束的时间
 * 你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。返回最多的宣讲场次
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-23
 **/
public class BestArrange {
    public static void main(String[] args) {
        int size = 8;
        int time = 24;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Program[] programs = generateRandomProgram(size, time);
            int result = bestArrange(programs);
            int ans = verify(programs);
            if (!Objects.equals(ans, result)) {
                System.err.printf(Locale.ROOT, "Actual: %s, Expect: %s", result, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int bestArrange(Program[] programs) {
        if (Objects.isNull(programs) || programs.length == 0) {
            return 0;
        }

        // 以会议结束时间排序，最早排前
        Arrays.sort(programs, Comparator.comparingInt(p -> p.end));
        int result = 0;
        int timeLine = 0;
        for (Program program : programs) {
            if (program.start >= timeLine) {
                result++;
                timeLine = program.end;
            }
        }
        return result;
    }

    private static int verify(Program[] programs) {
        if (Objects.isNull(programs) || programs.length == 0) {
            return 0;
        }

        return process(programs, 0, 0);
    }

    /**
     * <p>获取最多场</p>
     *
     * @param programs 会议列表
     * @param done     已完成会议数
     * @param timeLine 当前开始时间
     * @return <code>int</code>
     */
    private static int process(Program[] programs, int done, int timeLine) {
        if (programs.length == 0) {
            return done;
        }

        int ans = done;
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].start >= timeLine) {
                Program[] next = copyButExcept(programs, i);
                ans = Math.max(ans, process(next, done + 1, programs[i].end));
            }
        }
        return ans;
    }

    private static Program[] copyButExcept(Program[] programs, int cur) {
        Program[] arr = new Program[programs.length - 1];
        int index = 0;
        for (int i = 0; i < programs.length; i++) {
            if (i != cur) {
                arr[index++] = programs[i];
            }
        }
        return arr;
    }

    private static Program[] generateRandomProgram(int maxSize, int maxTime) {
        int size = (int) (Math.random() * (maxSize + 1));
        if (size == 0) {
            return null;
        }
        Program[] programs = new Program[size];
        for (int i = 0; i < programs.length; i++) {
            int star = (int) (Math.random() * (maxTime + 1));
            int end = (int) (Math.random() * (maxTime + 1));
            if (star == end) {
                programs[i] = new Program(star, end + 1);
            } else {
                programs[i] = new Program(Math.min(star, end), Math.max(star, end));
            }
        }

        return programs;
    }

    static class Program {
        int start;
        int end;

        public Program(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }
}
