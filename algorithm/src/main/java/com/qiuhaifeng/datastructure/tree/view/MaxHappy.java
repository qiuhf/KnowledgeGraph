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

package com.qiuhaifeng.datastructure.tree.view;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *   派对的最大快乐值
 *   Note：上司参加，直系下属不能参加；上司不参加，直系下属可参加也可不参加
 *
 *     员工信息的定义如下:
 *     class Employee {
 *         private int happy; // 这名员工可以带来的快乐值
 *         private List<Employee> subordinates; // 这名员工有哪些直接下级
 *     }
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-20
 **/
public class MaxHappy {
    static class Employee {
        /**
         * 这名员工可以带来的快乐值
         */
        private int happy;
        /**
         * 这名员工有哪些直接下级
         */
        private List<Employee> subordinates = new ArrayList<>();
    }

    public static void main(String[] args) {
        int maxSubordinates = 12;
        int maxLevel = 4;
        int maxHappy = 100;
        int testTimes = 100_000;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = party(maxLevel, maxSubordinates, maxHappy);
            int result = maxHappy(boss);
            int ans = verify(boss);
            if (!Objects.equals(ans, result)) {
                System.out.printf(Locale.ROOT, "Oops! Actual: %s, Expect: %s", result, ans);
                return;
            }
        }

        System.out.println("Nice!");
    }

    /**
     * <p>二叉树递归套路</p>
     *
     * @param boss 老板
     * @return <code>int</code>
     */
    public static int maxHappy(Employee boss) {
        Info info = process(boss);
        return Math.max(info.join, info.reject);
    }

    private static Info process(Employee boss) {
        if (Objects.isNull(boss)) {
            return new Info(0, 0);
        }

        int join = boss.happy;
        int reject = 0;
        for (Employee employee : boss.subordinates) {
            Info info = process(employee);
            // 上级已经确定来的情况下
            join += info.reject;
            // 上级已经确定不来的情况下
            reject += Math.max(info.join, info.reject);
        }

        return new Info(join, reject);
    }

    @AllArgsConstructor
    static class Info {
        /**
         * 参加时最大快乐值
         */
        private int join;
        /**
         * 不参加时最大快乐值
         */
        private int reject;
    }

    public static int verify(Employee boss) {
        if (Objects.isNull(boss)) {
            return 0;
        }
        return Math.max(joinOrReject(boss, true), joinOrReject(boss, false));
    }

    private static int joinOrReject(Employee boss, boolean join) {
        if (join) {
            int ans = 0;
            for (Employee employee : boss.subordinates) {
                // 如果boss来的话，下属没得选，只能不来
                ans += joinOrReject(employee, false);
            }
            return ans;
        }

        int p1 = boss.happy;
        int p2 = 0;
        for (Employee employee : boss.subordinates) {
            // 如果boss不来的话，下属可以来也可以不来
            p1 += joinOrReject(employee, true);
            p2 += joinOrReject(employee, false);
        }
        return Math.max(p1, p2);

    }

    private static Employee party(int maxLevel, int maxEmployee, int maxHappy) {
        if (Math.random() < 0.02) {
            return null;
        }

        Employee boss = new Employee();
        boss.happy = (int) (Math.random() * (maxHappy + 1));
        invite(boss, 1, maxLevel, maxEmployee, maxHappy);
        return boss;
    }

    private static void invite(Employee boss, int level, int maxLevel, int maxEmployee, int maxHappy) {
        if (level > maxLevel) {
            return;
        }

        int employeeSize = (int) (Math.random() * (maxEmployee + 1));
        for (int i = 0; i < employeeSize; i++) {
            Employee employee = new Employee();
            employee.happy = (int) (Math.random() * (maxHappy + 1));
            boss.subordinates.add(employee);
            invite(employee, level + 1, maxLevel, maxEmployee, maxHappy);
        }
    }
}
