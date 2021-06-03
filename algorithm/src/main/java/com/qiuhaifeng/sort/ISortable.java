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

package com.qiuhaifeng.sort;

/**
 * ISortable
 *
 * @author sz_qiuhf@163.com
 * @since 2021-05-18
 **/
public interface ISortable {
    /**
     * <p>指定数组，按照从小到大排序</p>
     *
     * @param arr 数组
     */
    void sort(int[] arr);
}
