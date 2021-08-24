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

package com.qiuhaifeng.falsesharing;

import com.lmax.disruptor.Sequence;
import org.springframework.util.StopWatch;

/**
 * <p>
 * 伪共享问题
 * 通过缓存行对齐，提高效率，此demo借鉴了{@link Sequence}
 * </p>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-24
 **/
public class CacheLinePadding {
    private static Data[] data = new Data[2];
    private static Value[] values = new Value[2];

    static {
        data[0] = new Data();
        data[1] = new Data();
        values[0] = new Value();
        values[1] = new Value();
    }

    public static void main(String[] args) throws InterruptedException {
        long time = 100_000_000;

        StopWatch watch = new StopWatch("伪共享测试..");
        watch.start("对齐");
        align(time);
        watch.stop();

        watch.start("不对齐");
        notAligned(time);
        watch.stop();

        System.out.println(watch.prettyPrint());
    }

    private static void align(long time) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < time; i++) {
                values[0].value = i;

            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < time; i++) {
                values[1].value = i;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    private static void notAligned(long time) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            for (long i = 0; i < time; i++) {
                data[0].p = i;
            }
        });

        Thread t2 = new Thread(() -> {
            for (long i = 0; i < time; i++) {
                data[1].p = i;
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }


    static class Data {
        private volatile long p = 0L;
    }

    static class LPadding {
        private long p1, p2, p3, p4, p5, p6, p7;
    }

    static class Value extends LPadding {
        private volatile long value = 0L;
    }

    static class RPadding extends Value {
        private long p9, p10, p11, p12, p13, p14, p15;
    }
}

