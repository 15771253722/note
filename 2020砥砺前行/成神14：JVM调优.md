# JVM虚拟机：JVM调优

## 1.基本概念：

JVM把内存区分为堆区（heap）、栈区（stack）和方法区（method）。由于本文主要讲解JVM调优，因此我们可以简单的理解为：JVM中的堆区中存放的是实际的对象，是需要被GC的。其他的都无须GC。

下图文JVM的内存模型：

![](C:\Users\14579\Pictures\笔记图\JVM内存模型.jpg)

1. JVM实质上分为三大块，年轻代（YoungGen），年老代（OldMemory），及持久代（Perm,在Java8中被取消，我们不做研究。）。

2. 垃圾回收GC，分为俩种，一是Minor GC，可以称为YGC，即年轻代GC，当Eden区，还有一种被称为Major GC，又称为FullGC。

3. GC原理：

   我们可以看到年轻代包括Eden区（对象刚被new出来的时候，放到该区），S0和S1，是幸存者1区和幸存者2区，从名字可以看出，是当发生YGC，没有被任何其他对象所引用的对象将会从内存中被清除，还被其他对象引用的则放到幸存区。当发生多次YGC，在s0、s1区多次没有被清除的对象，则会被移动到老年区域。当老年代区域被占满的时候，则会发送FullGC。

   无论是YGC或是FullGC，都会导致stop-the-world，即整个程序停止一些事务的处理，只有GC进程允许以进行垃圾回收，因此如果垃圾回收时间较长，部分web或socket程序，当终端连接的时候会报connetTimeOut或readTimeOut异常。

4. 从JVM调优的角度来看，我们应该尽量避免发生YGC或FullGC，或者使得YGC和FullGC的时间足够短。

## 2.Jvm调优的准备工作

1. 配置jstatd的远程RMI服务（当我们要看远程服务器上JAVA程序的GC情况的时间，需要执行此步骤），允许JVM工具查看JVM的使用情况。

   将如下 的代码存为文件jstatd.all.policy，放到JAVA_HOME/bin中，其内容如下：不知道JAVA_HOME目录的，可以进行which java查看。

   ```java
   grant codebase "file:${java.home}/../lib/tools.jar"{
   permission java.security.AllPermission;};
   ```

   执行命令jstatd-Djava.security.policy=jstatd.all.policy -J-Djava.rmi.server.hostname=10.27.20.38&

   (10.27..20.38为你服务器的ip地址，&表示用守护线程的方式运行)

2. 执行C:\glassfish4\jdk7\bin\jvisualvm.exe打开JVM控制台。工具-插件-Visual GC插件进行安装。

3. 对要执行java程序进行调优，以c1000k.jar为例，在该jar包所在目录下建立一个start.sh文件，文件内容如下。

   ```Java
   java -server -Xms4G -Xmx4G -Xmn2G -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1100 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar c1000k.jar&
   ```

   通过这样的配置，使用JVM控制台即可查看JVM（CPU/内存）及垃圾回收的情况。

4. 控制台配置

   打开\jvisualvm.exe，远程-添加远程主机-输入远程IP-添加JMX连接。

   ![](C:\Users\14579\Pictures\笔记图\控制台.png)

## 3.正式调优

### 第一步：

JVM调优核心为调整年轻代、年老代的内存空间大小及使用GC发生器的类型等。以上面start.sh文件内容为例：

```java
java -server -Xms4G -Xmx4G -Xmn2G -XX:SurvivorRatio=1 -XX:+UseConcMarkSweepGC -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1100 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -jar c1000k.jar&
```

**分析：**

这台机器是一个4G内存的机器，因此：

-Xms4G是指：JVM启动时整个堆（包括年轻代、年老代）的初始化大小。

-Xmx4G是指：JVM启动时整个堆的最大值。

-Xmn2G是指：年轻代的空间大小、神下的是年老代的空间。

-XX：SurvivorRatio=1是指：年轻代空间中2个Survivor空间与Eden的空间大小比例。此处为1：1：1，算法如下：比如整个年轻代空间为2G，如果比例为1，那么2/3，则S0/S1/Eden的空间大小同样的，为666M。

该值不设置，则JDK默认为比例为8，那么是1：1：8，通过上面的算法可以得出S0/S1的大小。我们可以看到官方通过增大Eden区的大小，来减少YGC发生的次数，但有时我们发现，虽然次数少了，但Eden区满的时候，由于占用的空间较大，导致释放缓慢，由此stop-the-world的时间较长，因此需要按照程序情况去调优。

-XX:+UseConcMarkSweepGC是指GC的回收类型。这里是CMS类型，JDK1.7以后推荐使用+UseG1GC,被称为G1类型（或Garbage First）的回收器。

### 第二步：

当我们设定好start.sh的参数值后，执行./start.sh此时就启动了。

我们可以通过jvisualvm.exe中的Visual GC插件查看GC的图形，我们也可以再服务器上执行：jstat -gc 15016 1000,看到每秒钟java进程号为15016的GC回收情况。

[root@yxdenvapp04 c1000k]#jstat -gc 15016 1000

S0C S1C S0U S1U EC EU OC OU PC PU YGC YGCT FGC FGCT GCT

699008.0 699008.0 29980.4 0.0 699136.0 116881.6 2097152.0 660769.4 21248.0 20071.0 354 54.272 0 0.000 54.272

699008.0 699008.0 29980.4 0.0 699136.0 118344.8 2097152.0 660769.4 21248.0 20071.0 354 54.272 0 0.000 54.272

699008.0 699008.0 29980.4 0.0 699136.0 119895.5 2097152.0 660769.4 21248.0 20071.0 354 54.272 0 0.000 54.272

699008.0 699008.0 29980.4 0.0 699136.0 121383.1 2097152.0 660769.4 21248.0 20071.0 354 54.272 0 0.000 54.272
解释如下：

S0C 是指: Survivor0区的分配空间

S0U是指：Survivor1区的已经使用的空间

EC是指：Eden区所使用的空间

EU是指：Eden区当前使用的空间

OC是指：老年代分配的空间

OU是指：老年代当前使用的空间

PC是指：持久代分配的空间

PU是指：持久代当前使用的空间

YGC是指：年轻代发生的次数，这里是354次

YGCT是指：年轻代发送的总时长，这里是54.272秒，因此每次年轻代发生GC，即平均每次stop-the-world的时长为54.272/354=0.153秒。

FGC是指：年老代回收的次数，或者称为FullGC的次数。

FGCT是指：年老代发生回收的总时长。

GCT是指：包括年轻代YGC及年老代FGC的总时长。

通常结合图像或数据，我们可以看到当EU即将等于EC的时候，此时发生YGC，因此YGC次数+1，YGCT时间增加。

经过实际的调优测试我们发现，当发生YGC时候，如果S0U或S1U区如果有任意一个区域为0的时候，此时YGC的速度很快，相反如果S0U或者S1U中都有数据，或相对满的时候，此时YGC的时间变长，这就是因为S0/S1及Eden区的比例问题导致的。

### 第三步：

经过一定时间的调优，我们基本可以使得YGC的次数非常少，时间非常快，很长时间，数半天都不会发生FGC，此时JVM的调优算是一个好的结果。

### 第四步：

在MAC电脑上可以通过jconsole调出图形化分析工具。





