# HashMap

## 一.数据结构解析

| JDK版本 |       实现方式       |   节点数>=8   |   节点数<=6   |
| :-----: | :------------------: | :-----------: | :-----------: |
| 1.8以前 |    数组+单向链表     | 数组+单向链表 | 数组+单向链表 |
| 1.8以后 | 数组+单向链表+红黑树 |  数组+红黑树  | 数组+单向链表 |

提高查询效率、减少系统开销。

## 二.扩容原理分析

### 2.1 无参构造

初始容量为16，负载因子0.75.

### 2.2 int initialCapacity

初始容量为int initialCapacity，负载因子0.75

### 2.3 int initialCapacity， float loadFactor

当指定的初始容量<0时，抛出IllegalArgumentException异常，当指定的初始容量>MAXIMUM_CAPACITY时，就让初始容量=MAXIMUM_CAPACITY。当负载因子小于0或者不是数字时，抛出IllegalArgumentException异常。

设定threshold=capacity * load factor，当HashMap的size到了threshold时，就要进行resize，也就是扩容。

tableSizeFor()的主要功能就是返回一个比给定整数大且最接近的2的幂次方的整数，如给定10，返回2的4次方16。

note：HashMap要求容量必须是2的幂。

HashMap使用链表法避免哈希冲突（相同hash值）， 当链表长度大于TREEIFY_THRESHOLD（默认为8）时，将链表转换为红黑树，当然小于UNTREEIFY_THRESHOLD（默认为6）时，又会转回链表以达到性能均衡。

![](C:\Users\14579\Pictures\笔记图\HashMap.png)

### 2.4 什么时候扩容？

通过HashMap源码可以看到在put操作时，即向容器中添加元素时，判断当前容器中元素的个数是否达到阈值（当前数组长度乘以加载因子）的时候，就要自动扩容了。

### 2.5 扩容（resize）

其实就是重新计算容量；而这个扩容时计算出所需容器的大小之后重新定义一个新的容器，将原来容器中的元素放入其中。

## 三.1.7和1.8的HashMap的不同点

### 1.JDK1.7用的是头插法，而JDK1.8及之后采用的是尾插法。

JDK1.7是用单链表进行纵向延伸，当采用头插法就是能够提高插入的效率，但是也会容易出现逆序且环形链表死循环问题。但是在JDK1.8之后是因为加入了红黑树，使用了尾插法，能够避免出现逆序且链表死循环的问题。

### 2.扩容后数据存储位置的计算方式不一样

1.在JDK1.7的时候是直接用hash值和需要扩容的二进制数进行&（这里就是为什么扩容的时候为啥一定必须是2的多少次幂的原因所在，因为如果只有2的n次幂的情况时最后一位二进制才一定是1，这样能最大程度减少hash碰撞）（hash值&length-1）。

2.而在JDK1.8的时候直接用了JDK1.7的时候计算的规律，也就是扩容前的原始位置+扩容的大小值=JDK1.8的计算方式，而不再是JDK1.7的那种异或的方式。但是这种方式就相当于只需要判断Hash值得新增参与运算得位是0还是1就直接迅速计算出了扩容后得存储方式。

### 3.JDK1.7的时候使用的是数组+单链表的数据结构。但是在JDK1.8及之后时，使用的是数组+链表+红黑树的数据结构（当链表的深度达到8的时候，也就是默认阈值，就会自动扩容把链表转成红黑树的数据结构来把时间复杂度从o（N）变成o（logN）提高了效率）。

## 四.HashMap为什么是线程不安全的？

### 1.put的时候导致的多线程数据不一致

比如有俩个线程A和B，首先A希望插入一个Key-Value对到HashMap中，首先计算记录所要落到的hash桶的索引坐标，然后获取到该桶里面的链表头结点，此时线程A的时间片用完了，而此时线程B被调度得以执行，和线程A一样执行，只不过线程B成功将记录插到了桶里面，假设线程A插入的记录计算出来的hash桶索引和线程B要插入的记录计算出来的hash桶索引是一样的，那么当线程B成功插入之后，线程A再次被调用运行时，它依然持有过期的链表头但是它对此一无所知，以至于它认为它应该这样做，如此一来就覆盖了线程B插入的记录，这样线程B插入的记录就凭空消失了，造成了数据不一致的行为。

### 2.resize而引起死循环

这种情况发生在HashMap自动扩容时，当2个线程同时检测到元素个数超过数组大小x负载因子。此时2个线程会在put（）方法中调用了resize（），俩个线程同时修改一个链表结构会产生一个循环链表（JDK1.7中，会出现resize前后元素顺序倒置的情况）。接下来再想通过get（）获取某一个元素，就会出现死循环。

## 五.HashMap和HashTable的区别

HashMap和Hashtable都实现了Map接口，但决定用哪一个之前先要弄清楚它们之间的区别。主要的区别有：线程安全性，同步（synchronization），以及速度。

1. HashMap几乎可以等价于Hashtable，除了HashMap是非synchronized的，并 可以接受null（HashMap可以接受为null的键值（key）和值（value），而Hashtable则不行）。
2. HashMap是非synchronized，而Hashtable是synchronized，这意味着Hashtable是线程安全的，多个线程可以共享一个Hashtable；而如果没有正确的同步的话，多个线程是不能共享HashMap的。Java 5 提供了ConcurrentHashMap，它是HashTable的代替，比HashTable的扩展性更好。
3. 另一个区别是HashMap的迭代器（iterator）是fail-fast迭代器，而Hashtable的enumerator迭代器不是fail-fast的。所以当有其它线程改变了HashMap的结构（增加或者移除元素），将会抛出ConcurrentHashModificationException异常。但这并不是一个一定发生的行为，要看JVM。这条同样也是Enumeration和Iterator的区别。
4. 由于Hashtable是线程安全的也是synchronized,所以在单线程环境下它比HashMap要慢。如果你不需要同步，只需要单一线程，那么使用HashMap性能要好过Hashtable。
5. HashMap不能保证随着时间的推移Map中的袁术次序是不变的。

#### 需要注意的重要术语：

1. sychronied意味着在一次仅有一个线程能够改变Hashtable。就是说任何线程要更新Hashtable时要首先获得同步锁，其他线程要等到同步锁被释放之后才能获得同步锁更新Hashtable。
2. Fail-safe和iterator迭代器相关。如果某个集合对象创建了iterator或者Listlterator，然后其它的线程试图“结构上”更改集合对象，将会抛出ConcurrentModificationException异常。但其他线程可以通过set（）方法更改集合对象是允许的，因为这并没有从“结构上”更改集合。但是假如已经从结构上进行了更改，再调用set（）方法，将会抛出IllegalArgumentException异常。
3. 结构上的更改指的是删除或者插入一个元素，这样会影响到map的结构。