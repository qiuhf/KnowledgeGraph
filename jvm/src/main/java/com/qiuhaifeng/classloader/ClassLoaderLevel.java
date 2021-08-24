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

package com.qiuhaifeng.classloader;

import sun.net.spi.nameservice.dns.DNSNameService;

/**
 * <pre>
 *     ClassLoaderLevel
 * </pre>
 *
 * @author sz_qiuhf@163.com
 * @since 2021-08-24
 **/
public class ClassLoaderLevel {
    public static void main(String[] args) {
        System.out.println("String classLoader           = " + String.class.getClassLoader());
        System.out.println("DNSNameService classLoader   = " + DNSNameService.class.getClassLoader());
        System.out.println("ClassLoaderLevel classLoader = " + ClassLoaderLevel.class.getClassLoader());

        System.out.println("DNSNameService super classLoader   = " + DNSNameService.class.getClassLoader().getParent());
        System.out.println("ClassLoaderLevel super classLoader = " + ClassLoaderLevel.class.getClassLoader().getParent());

        System.out.println("ClassLoader.getSystemClassLoader() = " + ClassLoader.getSystemClassLoader());
    }
}
