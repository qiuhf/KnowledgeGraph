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

package com.qiuhaifeng.hackerrank;

import java.text.DecimalFormat;

/**
 * <pre>
 *   Given a time in -hour AM/PM format, convert it to military (24-hour) time.
 *   Note: - 12:00:00AM on a 12-hour clock is 00:00:00 on a 24-hour clock.
 *         - 12:00:00PM on a 12-hour clock is 12:00:00 on a 24-hour clock.
 *
 * Example
 *  str = '12:01:00PM'
 *  Return '12:01:00'.
 *
 *  str = '12:01:00AM'
 *  Return '00:01:00'.
 *
 * 来源：本题为hackerrank原题
 * 链接：https://www.hackerrank.com/challenges/one-week-preparation-kit-time-conversion/problem
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-14
 **/
public class TimeConversion {
    public static void main(String[] args) {
        String[] strArr = new String[4];
        DecimalFormat decimalFormat = new DecimalFormat("#00");
        strArr[0] = decimalFormat.format(Math.random() * 12) + ":";
        strArr[1] = decimalFormat.format(Math.random() * 59) + ":";
        strArr[2] = decimalFormat.format(Math.random() * 59);
        strArr[3] = Math.random() > 0.5 ? "PM" : "AM";
        String s = "";
        for (int i = 0; i < strArr.length; i++) {
            s += strArr[i];
        }
        System.out.println(s);
        System.out.println(timeConversion(s));
    }

    /*
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */
    public static String timeConversion(String s) {
        // Write your code here
        String end = s.substring(2, s.length() - 2);
        String ans = s.substring(0, s.length() - 2);
        if (s.charAt(8) == 'A') {
            return s.startsWith("12") ? "00" + end : ans;
        }

        return s.startsWith("12") ? ans : (Integer.parseInt(s.substring(0, 2)) + 12) + end;
    }
}
