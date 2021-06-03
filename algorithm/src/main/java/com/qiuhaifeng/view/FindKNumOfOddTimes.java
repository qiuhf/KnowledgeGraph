/*
 * Copyright 2020-2021. the original qiuhaifeng .
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qiuhaifeng.view;

import org.springframework.util.StopWatch;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;

/**
 * <pre>
 *   一个数组中，只有一种数出现了K次，其他数都出现了M次, M > 1，K > 0, K < M,找出K次数的数
 *   要求: 额外空间复杂度O(1)，时间复杂度O(N)
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-19
 **/
public class FindKNumOfOddTimes {
    public static void main(String[] args) {
        int[] a = new int[]{-2, 3, 4, 3, -2, -2, 3};
        System.out.println(lookup(a, 1, 3));
        System.out.println(verify(a, 1, 3));
        System.out.println(lookup1(a, 1, 3));

        // 取值范围【-range，range】
        int range = 2000;
        // 最多出现maxTime次
        int maxTime = 12;
        // 最多生成maxGroup组的数字
        int maxGroup = 20;
        // 验证testTime次数
        int testTime = 50;
//        StopWatch watch = new StopWatch("第1轮测试..");
        for (int i = 0; i < testTime; i++) {
            // [1, maxTime]
            int num1 = (int) (Math.random() * maxTime) + 1;
            int num2 = (int) (Math.random() * maxTime) + 1;
            int kTime = Math.min(num1, num2);
            int mTime = Math.max(num1, num2);
            if (kTime == mTime) {
                mTime++;
            }
            int[] arr = randomArray(maxGroup, range, kTime, mTime);

            StopWatch watch = new StopWatch("第" + i + "轮测试..");
            watch.start("lookup");
            Optional<Integer> lookupAns = lookup(arr, kTime, mTime);
            watch.stop();
            watch.start("verify");
            Optional<Integer> verifyAns = verify(arr, kTime, mTime);
            watch.stop();
            System.out.println(watch.prettyPrint());

            if (!verifyAns.equals(lookupAns)) {
                System.out.println("出错了，arr: " + Arrays.toString(arr));
                System.out.println("出错了，lookup: " + lookupAns);
                System.out.println("出错了，verify: " + verifyAns);
                return;
            }
        }
    }

    public static Optional<Integer> lookup(int[] arr, int kTime, int mTime) {
        if (Objects.isNull(arr) || arr.length < 3) {
            throw new IllegalArgumentException("数组不能为空且长度必须大于2");
        }

        if (mTime <= 1 || kTime <= 0 || kTime > mTime) {
            throw new IllegalArgumentException("k和m必须符合：M > 1，K > 0， K < M");
        }

        // 记录每一位出现的次数
        int[] countKm = new int[32];
        int countZero = 0;
        for (int num : arr) {
            if (num == 0) {
                countZero++;
                continue;
            }
            for (int i = 0; i < 32; i++) {
                countKm[i] += (num >> i) & 1;
            }
        }

        int ans = 0;
        if (countZero == kTime) {
            return Optional.of(ans);
        }

        for (int i = 0; i < 32; i++) {
            // 不能被m整除，说明该位置有k次的数出现
            if (countKm[i] != 0 && countKm[i] % mTime == kTime) {
                ans |= (1 << i);
            }
        }
        if (ans != 0) {
            return Optional.of(ans);
        }

        System.out.println("数组中不存在K次数的数， arr = " + Arrays.toString(arr));
        return Optional.empty();
    }

    public static Optional<Integer> verify(int[] arr, int kTime, int mTime) {
        if (Objects.isNull(arr) || arr.length < 3) {
            throw new IllegalArgumentException("数组不能为空且长度必须大于2");
        }

        if (mTime <= 1 || kTime <= 0 || kTime > mTime) {
            throw new IllegalArgumentException("k和m必须符合：M > 1，K > 0， K < M");
        }

        HashMap<Integer, Integer> dataMap = new HashMap<>(arr.length);
        for (int num : arr) {
            if (dataMap.containsKey(num)) {
                dataMap.put(num, dataMap.get(num) + 1);
            } else {
                dataMap.put(num, 1);
            }
        }

        for (Integer num : dataMap.keySet()) {
            if (dataMap.get(num) == kTime) {
                return Optional.of(num);
            }
        }

        System.out.println("数组中不存在K次数的数， arr = " + Arrays.toString(arr));
        return Optional.empty();
    }

    public static Optional<Integer> lookup1(int[] arr, int kTime, int mTime) {
        if (Objects.isNull(arr) || arr.length < 3) {
            throw new IllegalArgumentException("数组不能为空且长度必须大于2");
        }

        if (mTime <= 1 || kTime <= 0 || kTime > mTime) {
            throw new IllegalArgumentException("k和m必须符合：M > 1，K > 0， K < M");
        }

        // 二进制值与下标映射
        HashMap<Integer, Integer> binaryMapper = new HashMap<>(1 << 5);
        for (int i = 0; i < 32; i++) {
            binaryMapper.put(1 << i, i);
        }

        // 记录每一位出现的次数
        int[] countKm = new int[32];
        int countZero = 0;
        for (int num : arr) {
            if (num == 0) {
                countZero++;
                continue;
            }

            do {
                // 提取最右侧的1
                int rightOne = num & (-num);
                countKm[binaryMapper.get(rightOne)]++;
                // 去除最右侧已统计的1
                num ^= rightOne;
            } while (num != 0);
        }

        int ans = 0;
        if (countZero == kTime) {
            return Optional.of(ans);
        }

        for (int i = 0; i < 32; i++) {
            // 不能被m整除，说明该位置有k次的数出现
            if (countKm[i] != 0 && countKm[i] % mTime == kTime) {
                ans |= (1 << i);
            }
        }

        if (ans != 0) {
            return Optional.of(ans);
        }

        System.out.println("数组中不存在K次数的数， arr = " + Arrays.toString(arr));
        return Optional.empty();
    }

    public static int[] randomArray(int maxGroup, int range, int kTime, int mTime) {
        int kNum = randomNumber(range);
        // 真命天子出现次数,50%几率出现kTime,
        //  kTime = Math.random() < 0.5 ? kTime : (int) (Math.random() * (mTime - 1)) + 1;
        // 出现多少组: [2, mGroup]
        int mGroup = (int) (Math.random() * maxGroup) + 2;
        mGroup = Math.min(mGroup, maxGroup);

        //  (mGroup-1) * mTime, m次数出现的个数
        int[] arr = new int[kTime + (mGroup - 1) * mTime];
        int index = 0;
        // 填充k次数的数字
        for (; index < kTime; index++) {
            arr[index] = kNum;
        }
        mGroup--;
        HashSet<Integer> numSet = new HashSet<>();
        numSet.add(kNum);
        do {
            int mNum;
            do {
                // 数字已存在，重新生成
                mNum = randomNumber(range);
            } while (numSet.contains(mNum));
            numSet.add(mNum);

            // 填充m次数的数字
            for (int i = 0; i < mTime; i++) {
                try {
                    arr[index++] = mNum;
                } catch (Exception e) {
                    System.out.println("arr = " + Arrays.toString(arr));
                    System.out.println("arr.length = " + arr.length);
                    System.out.println("index = " + index);
                    System.out.println("numSet = " + numSet);
                    System.out.println("kTime = " + kTime + ", mTime = " + mTime);
                    System.out.println("kNum = " + kNum + ", mNum = " + mNum);
                    System.out.println("maxGroup = " + maxGroup + ", mGroup = " + mGroup);
                }
            }

            mGroup--;
        } while (mGroup != 0);

        // 打乱顺序
        int len = arr.length;
        for (int i = 0; i < len; i++) {
            int randomIndex = (int) (Math.random() * len);
            if (randomIndex != i) {
                arr[randomIndex] = arr[randomIndex] ^ arr[i];
                arr[i] = arr[randomIndex] ^ arr[i];
                arr[randomIndex] = arr[randomIndex] ^ arr[i];
            }
        }

        return arr;
    }

    /**
     * <p>随机生成范围中的一个数：{range, -range}</p>
     *
     * @param range 范围
     * @return <code>int</code>
     */
    private static int randomNumber(int range) {
        return ((int) (Math.random() * range) + 1) - ((int) (Math.random() * range) + 1);
    }
}
