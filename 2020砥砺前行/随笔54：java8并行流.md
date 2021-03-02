# Java8并行流

## 什么是并行流：

并行流就是将一个流的内容分成多个数据块，并用不同的线程分别处理每个不同的数据块的流。

```java
List<Apple>appleList = new ArrayList<>();
for(Apple apple : appleList){
    apple.setPrice(5.0 * apple.getWeight() / 1000);
}
```

我们通过迭代器遍历list中的apple对象，完成了每个apple价格的计算。而这个算法的时间复杂度是O（list.size（））随着list大小的增加，耗时也会跟着线性增加。并行流可以大大缩短这个时间。并行流处理该集合的方法如下：

```java
appleList.parallelStream().forEach(apple -> apple.setPrice(5.0*apple.getWeight()/1000));
```

和普通流的区别是这里调用的parallelStram()方法。当然也可以通过stream.parallel()将普通流转换成并行流。并行流也能通过sequential（）方法转换为顺序流。

但要注意：**流的并行和顺序转换不会对流本身做任何实际的变化，仅仅是打了个标记而已。并且在一条流水线上对流进行多次并行/顺序的转换，生效的是最后一次的方法调用。**

并行流如此方便，它的线程从哪里来呢？有多少个？怎么配置？

并行流内部使用了默认的ForkJoinPool线程池。**默认的线程数量就是处理器的核心数**，而配置系统核心属性：java.util.concurrent.ForkJoinPool.common.parallelism 可以改变线程池大小。

不过该值是全局变量。改变他会影响所有并行流。目前还无法为每个流配置专属的线程数。一般来说采用处理器核心数是不错的选择

## 测试并行流的性能

为了更容易的测试性能，我们在每次计算完苹果价格后，让线程睡 1s，表示在这期间执行了其他 IO 相关的操作，并输出程序执行耗时,顺序执行的耗时：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: MCJ
 * @Description //TODO $end$
 * @Date: 2020/12/23 20:13
 * @Param $param$
 * @return $return$
 */
public class AppleDemo {

    private static Logger log = LoggerFactory.getLogger(AppleDemo.class);

//    public static void main(String[] args) throws InterruptedException {
//
//        List<Apple> appleList = initAppleList();
//
//        Date begin = new Date();
//        for (Apple apple : appleList) {
//            apple.setPrice(5.0 * apple.getWeight() / 1000);
//            Thread.sleep(1000);
//        }
//        Date end = new Date();
//        log.info("苹果数量：{}个, 耗时：{}s", appleList.size(), (end.getTime() - begin.getTime()) /1000);
//    }

    private static List<Apple> initAppleList() {
        List<Apple> list = new ArrayList<Apple>();
        Apple apple = new Apple();
        apple.setPrice(1);
        apple.setWeight(1);
        Apple apple1 = new Apple();
        apple1.setPrice(11);
        apple1.setWeight(11);
        list.add(apple);
        list.add(apple1);
        return list;
    }

    public static void main(String[] args) throws InterruptedException {

        List<Apple> appleList = initAppleList();

        Date begin = new Date();
        appleList.parallelStream().forEach(apple ->
        {
            apple.setPrice(5.0 * apple.getWeight() / 1000);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Date end = new Date();
        log.info("苹果数量：{}个, 耗时：{}s", appleList.size(), (end.getTime() - begin.getTime()) /1000);
    }

}
```

结论：几核处理器快几倍！

## 并行流可以随便使用吗?

### 可拆分性影响流的速度

通过上面的测试，有的人会轻易得到一个结论：并行流很快，我们可以完全放弃 foreach/fori/iter 外部迭代，使用 Stream 提供的内部迭代来实现了。

事实真的是这样吗？并行流真的如此完美吗？答案当然是否定的。大家可以复制下面的代码，在自己的电脑上测试。测试完后可以发现，并行流并不总是最快的处理方式。

**1.对于iterate方法来处理的前n个数字来说，不管并行与否，它总是manyu循环的，非并行版本可以理解为流化操作没有循环更偏底层导致的慢。**可并行版本是为什么慢呢？这里有俩个需要注意的点:

​	1.1 iterate生成的是装修的对象，必须拆箱成数字才能求和。

​	1.2 我们很难把iterate分成多个独立的块来并行执行

这个问题很有意思，我们必须意思到某些流操作比其他操作更容易并行化。对于iterate来说，每次应用这个函数都要依赖于前一次应用的结果。

因此在这种情况下，我们不仅不能有效的将流划分成小块处理。反而还因为并行化再次增加了开支。

**2.而对于LongStream.rangeClosed() 方法来说，就不存在iterate的第二个痛点了。**它生成的是基本类型的值，不用拆装箱操作，另外它可以直接将要生成的数字 1 - n 拆分成 1 - n/4， 1n/4 - 2n/4， ... 3n/4 - n 这样四部分。因此并行状态下的 rangeClosed() 是快于 for 循环外部迭代的。

```java
package lambdasinaction.chap7;

import java.util.stream.*;

public class ParallelStreams {

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(Long::sum).getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(Long::sum).getAsLong();
    }

}
```

```java
package lambdasinaction.chap7;

import java.util.concurrent.*;
import java.util.function.*;

public class ParallelStreamsHarness {

    public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public static void main(String[] args) {
        System.out.println("Iterative Sum done in: " + measurePerf(ParallelStreams::iterativeSum, 10_000_000L) + " msecs");
        System.out.println("Sequential Sum done in: " + measurePerf(ParallelStreams::sequentialSum, 10_000_000L) + " msecs");
        System.out.println("Parallel forkJoinSum done in: " + measurePerf(ParallelStreams::parallelSum, 10_000_000L) + " msecs" );
        System.out.println("Range forkJoinSum done in: " + measurePerf(ParallelStreams::rangedSum, 10_000_000L) + " msecs");
        System.out.println("Parallel range forkJoinSum done in: " + measurePerf(ParallelStreams::parallelRangedSum, 10_000_000L) + " msecs" );
    }

    public static <T, R> long measurePerf(Function<T, R> f, T input) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long start = System.nanoTime();
            R result = f.apply(input);
            long duration = (System.nanoTime() - start) / 1_000_000;
            System.out.println("Result: " + result);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }
}
```

## 共享变量的修改问题

并行流虽然轻易的实现了多线程，但是仍未解决多线程中共享变量的修改问题。下面代码中存在共享变量 total，分别使用顺序流和并行流计算前n个自然数的和。

```java
public static long sideEffectSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).forEach(accumulator::add);
    return accumulator.total;
}

public static long sideEffectParallelSum(long n) {
    Accumulator accumulator = new Accumulator();
    LongStream.rangeClosed(1, n).parallel().forEach(accumulator::add);
    return accumulator.total;
}

public static class Accumulator {
    private long total = 0;

    public void add(long value) {
        total += value;
    }
}
```

顺序执行每次输出的结果都是：50000005000000，而并行执行的结果却五花八门了。这是因为每次访问 totle 都会存在数据竞争，关于数据竞争的原因，大家可以看看关于 volatile 的博客。因此当代码中存在修改共享变量的操作时，是不建议使用并行流的。

## 并行流的使用注意

在并行流的使用上有下面几点需要注意：

**1.尽量使用 LongStream / IntStream / DoubleStream 等原始数据流代替 Stream 来处理数字，以避免频繁拆装箱带来的额外开销**

**2.要考虑流的操作流水线的总计算成本** ，假设 N 是要操作的任务总数，Q 是每次操作的时间。N * Q 就是操作的总时间，Q 值越大就意味着使用并行流带来收益的可能性越大

例如：前端传来几种类型的资源，需要存储到数据库。每种资源对应不同的表。我们可以视作类型数为 N，存储数据库的网络耗时 + 插入操作耗时为 Q。一般情况下网络耗时都是比较大的。因此该操作就比较适合并行处理。当然当类型数目大于核心数时，该操作的性能提升就会打一定的折扣了。更好的优化方法在日后的博客会为大家奉上

**3.对于较少的数据量，不建议使用并行流**

**4.容易拆分成块的流数据，建议使用并行流**

以下是一些常见的集合框架对应流的可拆分性能表

| 源              | 可拆分性 |
| --------------- | -------- |
| ArrayList       | 极佳     |
| LinkedList      | 差       |
| IntStream.range | 极佳     |
| Stream.iterate  | 差       |
| HashSet         | 好       |
| TreeSet         | 好       |