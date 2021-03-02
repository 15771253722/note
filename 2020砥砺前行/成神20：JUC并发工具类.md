# 多线程/并发：JUC并发工具类

java.util.concurrent及其子包，集中了Java并发的各种基础工具类，主要有如下几个方面：

- 提供了比synchronized更加高级的各种同步结构，包括CountDownLatch、CyclicBarrier、Semaphore等，可以实现更加丰富的多线程操作，比如利用Semaphore作为资源控制器，限制同时进行工作的线程数量。

- 各种线程安全的容器，比如最常见的ConcurrentHashMap、有序的ConcunrrentSkipListMap,或者通过类似快照机制，实现线程安全的动态数组CopyOnWriteArrayList等。

- 各种并发队列实现，如各种BlockedQueue实现，比较典型的ArrayBlockingQueue、SynchorousQueue或针对特定场景的PriorityBlockingQueue等。

- 强大的Executor框架，可以创建各种不同类型的线程池，调度任务运行等，绝大部分下，不在需要自己重头实现线程池和任务调度器。

  并发包里提供的线程安全Map、List和Set。

  ![](C:\Users\14579\Pictures\笔记图\并发包里提供的线程安全.png)

如果应用侧重于Map放入或者获取的速度，而不在乎顺序，大多数推荐使用ConcurrentHashMap.反之则侧重于使用ConcurrentSkipListMap;如果需要对大量数据进行非常频繁地修改，ConcurrentSkipListMap也可能表现出优势。

关于俩个CopyOnWrite容器，其实CopyOnWriteArraySet是通过包装了CopyOnWriteArayList来实现的。

![](C:\Users\14579\Pictures\笔记图\并发工具类1.png)

CopyOnWrite它的原理是，任何修改操作，如add、set、remove，都会拷贝原数组，修改后替换原来的数组，通过这种防御性的方式，实现另类的线程安全。

所以这种数据结构，相对比较适合读多写少的操作，不然修改的开销还是非常明显的。

![](C:\Users\14579\Pictures\笔记图\并发工具类2.png)