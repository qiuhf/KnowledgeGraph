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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author sz_qiuhf@163.com
 * @since 2021-08-25
 **/
public class CustomClassLoader extends ClassLoader {
    /**
     * Loads the class with the specified <a href="#name">binary name</a>.
     * This method searches for classes in the same manner as the {@link
     * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
     * machine to resolve class references.  Invoking this method is equivalent
     * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
     * false)</tt>}.
     *
     * @param name The <a href="#name">binary name</a> of the class
     * @return The resulting <tt>Class</tt> object
     * @throws ClassNotFoundException If the class was not found
     */
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        String property = System.getProperty("user.dir") + "\\jvm\\target\\classes";
        try (FileInputStream fis = new FileInputStream(new File(property, name.replace(".", "/").concat(".class")));
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            int b;
            while ((b = fis.read()) != 0) {
                outputStream.write(b);
            }

            byte[] bytes = outputStream.toByteArray();
            return defineClass(name, bytes, 0, bytes.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return super.findClass(name);
    }

    public static void main(String[] args) throws Exception {
        ClassLoader l = new CustomClassLoader();
        Class clazz = l.loadClass("com.qiuhaifeng.classloader.ClassLoaderLevel");
        ClassLoaderLevel h = (ClassLoaderLevel)clazz.newInstance();

        System.out.println(h.getClass().getClassLoader());
        System.out.println(l.getParent());

        System.out.println(getSystemClassLoader());
    }
}
