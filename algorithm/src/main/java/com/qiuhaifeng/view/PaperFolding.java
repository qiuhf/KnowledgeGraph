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

package com.qiuhaifeng.view;

/**
 * <pre>
 *     请把一段纸条竖着放在桌子上，然后从纸条的下边向上方对折1次，压出折痕后展开。此时折痕是凹下去的，即折痕突起的方向指向纸条的背面。
 * 如果从纸条的下边向上方连续对折2次，压出折痕后展开，此时有三条折痕，从上到下依次是下折痕、下折痕和上折痕。
 *    给定一个输入参数nTime，代表纸条都从下边向上方连续对折N次。 请从上到下打印所有折痕的方向。
 *    例如:nTime=1时，打印: down nTime=2时，打印: down down up
 *   Tip: 中序遍历
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-07-13
 **/
public class PaperFolding {
    public static void main(String[] args) {
        printAllFolds(1);
    }

    public static void printAllFolds(int nTime) {
        process(1, nTime, "down");
    }

    private static void process(int time, int nTime, String direction) {
        if (time > nTime) {
            return;
        }
        process(time + 1, nTime, "down");
        System.out.print(direction + " ");
        process(time + 1, nTime, "up");
    }
}
