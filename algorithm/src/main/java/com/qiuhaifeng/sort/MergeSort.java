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

package com.qiuhaifeng.sort;

import java.util.Objects;

/**
 * <pre>
 *   归并排序
 *       1）整体是递归，左边排好序+右边排好序+merge让整体有序
 *       2）让其整体有序的过程里用了排外序方法
 *       3）利用master公式来求解时间复杂度
 *       4）当然可以用非递归实现
 *
 *   复杂度？
 *     T(N) = 2*T(N/2) + O(N^1)
 *   时间复杂度: 根据master可知为O(N*logN)
 *   额外空间复杂度：merge过程需要辅助数组，所以是O(N)
 *   归并排序的实质是把比较行为变成了有序信息并传递，比O(N^2)的排序快
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-06-13
 **/
public class MergeSort implements ISortable {
    public static void main(String[] args) {
        new MergeSort().check();
    }

    /**
     * <p>指定数组，按照从小到大排序</p>
     *
     * @param arr 数组
     */
    @Override
    public void sort(int[] arr) {
        if (Objects.isNull(arr) || arr.length < 2) {
            return;
        }

        this.process(arr, 0, arr.length - 1);
    }

    /**
     * <p>[L..R]范围上的有序</p>
     *
     * @param arr   数组
     * @param left  左边界
     * @param right 右边界
     */
    private void process(int[] arr, int left, int right) {
        if (left == right) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        this.process(arr, left, mid);
        this.process(arr, mid + 1, right);
        this.merge(arr, left, mid, right);
    }

    /**
     * <p>[L..R]范围上的归并排序</p>
     *
     * @param arr   数组
     * @param left  左边界
     * @param mid   中点
     * @param right 右边界
     */
    private void merge(int[] arr, int left, int mid, int right) {
        int[] helper = new int[right - left + 1];
        int index = 0;
        int lPos = left;
        int rPos = mid + 1;
        while (lPos <= mid && rPos <= right) {
            // 相等情况优先使用左值
            helper[index++] = arr[lPos] <= arr[rPos] ? arr[lPos++] : arr[rPos++];
        }

        // 情况一，rPos先越界，将左剩余的值赋值到helper
        while (mid >= lPos) {
            helper[index++] = arr[lPos++];
        }

        // 情况二，lPos先越界，将右剩余的值赋值到helper
        while (right >= rPos) {
            helper[index++] = arr[rPos++];
        }

        // left之前的数已排好序，故从left位置开始
        System.arraycopy(helper, 0, arr, left, helper.length);
    }
}
