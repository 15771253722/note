# JVM虚拟机：JVM性能监控与故障定位

java的bin目录下

## 1.jps

跟linux命令ps功能类似

## 2.jstat

查看收集hotspot虚拟机各方面的运行数据

比如参数-gcutil监视java堆状况，包括Eden区，survior区，老年代，永久代的容量，已用空间，gc的时间等信息。显示百分比

## 3.jinfo

查看和调整虚拟机的各项参数

jps -v参数可以查看初始化启动时的虚拟机参数。但是有些默认项可能没显示出来，需要使用info区查看。

## 4.jmap

java的内存映像工具

用于生成堆转储快找（dump文件）

生成dump文件一种方法是，在启动的jvm参数里，使用-XX:+HeapDumpOnOutOfMemoryError参数，程序在OOM后会自动生成dump文件。

还有一种方法是，是用jmap指令，参数-heap，-histo是打印详细的信息。

分析dump文件一般比较消耗资源，费时间。

市面上有很多成熟的分析工具，要是要分析，内存泄漏和内存溢出的原因的。

## 5.jhat

jhat就是分析dump文件的一种工具，但是相对比较简陋些，比较好的方法还是copy到别的地方，使用第三方工具分析，更常见些。

VisualVm,Eclipse Memory Analyzer ,IBM heapAnalyzer(mark需要时用)

##  6.jstack

java的栈跟踪工具，用于生成虚拟机当前时刻的线程快照（threaddump文件）。线程快照里包含了每个线程的堆栈信息。

**生成她的原因是为了定位，导致线程长时间停顿的原因，如线程间死锁、死循环、请求外部资源导致长时间的等待。**

Thread类里面有个getAllStackTrances()方法。可以做个工具，调用这个方法来实现分析线程栈信息。

可视化工具JConsole,VisualVM(mark)

## Full gc问题排查：

留下线程快照jstack

留下dump内存分配信息jmap

hashmap是线程不安全的。并发情况下，在扩容的情况下会形成环状链表，导致死循环。





