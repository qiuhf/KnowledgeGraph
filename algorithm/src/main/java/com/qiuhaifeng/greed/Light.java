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

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *   给定一个字符串str，只由‘X’和‘.’两种字符构成。
 *      ‘X’表示墙，不能放灯，也不需要点亮
 *      ‘.’表示居民点，可以放灯，需要点亮
 * 如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮.返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-23
 **/
public class Light {
    public static void main(String[] args) {
        int size = 12;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            String str = generateRandomString(size);
            int result = mixLightSize(str);
            int ans = verify(str);
            if (!Objects.equals(ans, result)) {
                System.err.println("string: " + str);
                System.err.printf(Locale.ROOT, "Actual: %s, Expect: %s", result, ans);
                return;
            }
        }
        System.out.println("Nice!");
    }

    public static int mixLightSize(String str) {
        if (Objects.isNull(str) || str.isEmpty()) {
            return 0;
        }
        char[] road = str.toCharArray();
        int lightSize = 0;
        int index = 0;
        while (index < road.length) {
            if (road[index] == 'x') {
                index++;
                continue;
            }
            lightSize++;
            if (index + 1 >= road.length) {
                break;
            }
            // . . . x x .
            // 0 1 2 3 4
            if (road[index + 1] == '.') {
                index += 3;
            } else {
                index += 2;
            }
        }

        return lightSize;
    }

    private static int verify(String str) {
        if (Objects.isNull(str) || str.isEmpty()) {
            return 0;
        }

        return process(str.toCharArray(), 0, new HashSet<>());
    }

    /**
     * <p>选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯</p>
     *
     * @param road  数组
     * @param index 当前位置
     * @param light 记录放了灯的位置
     * @return <code>int</code>
     */
    public static int process(char[] road, int index, HashSet<Integer> light) {
        // 结束的时候
        if (index == road.length) {
            for (int i = 0; i < road.length; i++) {
                if (road[i] == 'x') {
                    continue;
                }
                // 当前位置是点的话
                if (!light.contains(i - 1) && !light.contains(i) && !light.contains(i + 1)) {
                    return Integer.MAX_VALUE;
                }
            }
            return light.size();
        }
        // index 不放灯
        int no = process(road, index + 1, light);
        int yes = Integer.MAX_VALUE;
        if (road[index] == '.') {
            light.add(index);
            // index 放灯
            yes = process(road, index + 1, light);
            light.remove(index);
        }
        return Math.min(no, yes);
    }

    private static String generateRandomString(int size) {
        int len = (int) (Math.random() * (size + 1));
        if (len == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append(Math.random() > 0.5 ? 'x' : '.');
        }
        return builder.toString();
    }
}
