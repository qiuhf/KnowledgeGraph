# JVM知识体系

## java从编码到执行

![FlowChart](F:\workspace\KnowledgeGraph\jvm\src\main\resources\image\FlowChart.png)

1. .java 文件通过javac命令解析编译.class文件
2. 将.class文件和java自带的 类库加载到classLoader，classLoader只 负责class文件的加载， 至于它是否可以运行，则由执行引擎决定
3. 字节码解析器或JIT即时编译器解析，交给执行引擎执行

> 任何语言只有能编译成class文件都可以在JVM执行

## JVM是一种规范

-  The Java Virtual Machine Specification
- https://docs.oracle.com/javase/specs/index.html

## JDK/JRE/JVM

![JVM-JRE-JDK](F:\workspace\KnowledgeGraph\jvm\src\main\resources\image\JVM-JRE-JDK.png)

## 类加载-初始化

![ClassLoader](F:\workspace\KnowledgeGraph\jvm\src\main\resources\image\ClassLoader.png)

### loading

 - 双亲委派, JVM按需动态加载，主要出于安全来考虑（如：自定义java.lang.String编译打包给客户，存在窃取客户敏感信息隐患）

![ParentalDelegation](F:\workspace\KnowledgeGraph\jvm\src\main\resources\image\ParentalDelegation.png)

![ClassLoaderFlowChart](F:\workspace\KnowledgeGraph\jvm\src\main\resources\image\ClassLoaderFlowChart.png)

>class加载到内存后，同时生成class对象指向对应内存地址

- JVM规范并没有规定何时加载，但是严格规定了什么时候必须初始化。LazyLoading 五种情况

  1）new/getstatic/putstatic/invokestatic指令，访问final变量除外

  2）java.lang.reflect对类进行反射调用时

  3）初始化子类的时候，父类首先初始化

  4）虚拟机启动时，被执行的主类必须初始化

  5）动态语言支持java.lang.invoke.MethodHandle解析的结果为REF_getstatic REF_putstatic REF_invokestatic的方法句柄时，该类必须初始化

- ClassLoader源码

  findInCache ->  parent.loadClass -> findClass

  > 涉及到设计模式：模板方法（钩子函数）

- 如何自定义类加载器

  1）继承ClassLoader;

  2）重写findClass() -> defineClass(byte[] -> Class clazz) ;

  3）加密

- 混合执行 编译执行 解释执行

  1）解析器 byteCode intepreter

  2) JIT，Just In-Time compiler

  3) 混合模式

    a) 混合使用使用解释器 + 热点代码编译

    b) 起始阶段采用解释执行

    c) 热点代码检查

   - 多次被调用的方法（方法计数器：监测方法执行频率）
   - 多次被调用的循环（循环计数器：监测循环执行频率）
   - 进行编译

>-Xmixed 默认混合模式，开始解释执行，启动速度快，对热点代码实行监测和编译
>
>-Xint 使用解释模式，启动很快，执行稍慢
>
>-Xcomp 使用纯编译模式，执行很快，启动很慢

### Linking

1. Verification

   验证文件是否符合JVM规定

2. Preparation

   静态成员变量赋**默认值**，例如 int类型，默认值0

3. Resolution

   将类、方法、属性等符号引用解析为直接引用 常量池中的各种符号引用解析为指针、偏移量等内存地址的直接引用

### Initializing

​	调用类初始化代码 ，给静态成员变量赋**初始值**

> load - 默认值 - 初始值
>
> new - 申请内存 - 默认值 - 初始值

## 硬件层数据一致性

​    协议很多，intel 用MESI https://www.cnblogs.com/z00377750/p/9180644.html 

​    现代CPU的数据一致性实现 = 缓存锁(MESI ...) + 总线锁，读取缓存以**cache line**为基本单位，目前多数的实现是**64bytes**。

> 位于同一缓存行的两个不同数据，被两个不同CPU锁定，产生互相影响的**伪共享**问题。

> 使用缓存行的对齐能够提高效率，案例：com.lmax.disruptor.RingBuffer.RingBuffer.java

## 乱序问题

CPU为了提高指令执行效率，会在一条指令执行过程中（比如cpu的速度往往都比主存的高至少两个数量级），去同时执行另一条指令，前提是，两条指令没有依赖关系（写操作也可以进行合并）

https://www.cnblogs.com/liushaodong/p/4777308.html

> wcBuffer（4位）提高写性能：com.qiuhaifeng.juc.interview.wcbuffer.WriteCombining.java

> 乱序执行证明：com.qiuhaifeng.disorder.Disorder.java
>
> 参考:https://preshing.com/20120515/memory-reordering-caught-in-the-act/

## 如何保证特定情况下不乱序

硬件内存屏障， 以 **X86**为例

> sfence:  store| 在sfence指令前的写操作当必须在sfence指令后的写操作前完成。
> lfence：load | 在lfence指令前的读操作当必须在lfence指令后的读操作前完成。
> mfence：modify/mix | 在mfence指令前的读写操作当必须在mfence指令后的读写操作前完成。

> 原子指令，如x86上的”lock …” 指令是一个Full Barrier，执行时会锁住内存子系统来确保执行顺序，甚至跨多个CPU。Software Locks通常使用了内存屏障或原子指令来实现变量可见性和保持程序顺序

JVM级别如何规范（JSR133）

> LoadLoad屏障：
> 	对于这样的语句Load1; LoadLoad; Load2， 
>
> ```
> 在Load2及后续读取操作要读取的数据被访问前，保证Load1要读取的数据被读取完毕。
> ```
>
> StoreStore屏障：
>
> ```
> 对于这样的语句Store1; StoreStore; Store2，
> 在Store2及后续写入操作执行前，保证Store1的写入操作对其它处理器可见。
> ```
>
> LoadStore屏障：
>
> ```
> 对于这样的语句Load1; LoadStore; Store2，
> 在Store2及后续写入操作被刷出前，保证Load1要读取的数据被读取完毕。
> ```
>
> StoreLoad屏障：
>
> ```
> 对于这样的语句Store1; StoreLoad; Load2，
> 在Load2及后续所有读取操作执行前，保证Store1的写入对所有处理器可见。
> ```

**volatile**的实现细节

1. 字节码层面：ACC_VOLATILE

2. JVM层面：volatile内存区的读写 都加屏障

   > StoreStoreBarrier
   >
   > volatile 写操作
   >
   > StoreLoadBarrier

   > LoadLoadBarrier
   >
   > volatile 读操作
   >
   > LoadStoreBarrier

3. OS和硬件层面
   https://blog.csdn.net/qq_26222859/article/details/52235930
   hsdis - HotSpot Dis Assembler
   windows lock 指令实现 | MESI实现

**synchronized**实现细节

1. 字节码层面：ACC_SYNCHRONIZED / monitorenter monitorexit monitorexit
2. JVM层面：C C++ 调用了操作系统提供的同步机制
3. OS和硬件层面
   X86 : lock cmpxchg / xxx
   [https](https://blog.csdn.net/21aspnet/article/details/88571740)[://blog.csdn.net/21aspnet/article/details/](https://blog.csdn.net/21aspnet/article/details/88571740)[88571740](https://blog.csdn.net/21aspnet/article/details/88571740)