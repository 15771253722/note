# 规定了HashMap的初始容量，性能没提速。

直接上图：

没加初始容量：

![1605076671180](C:\Users\14579\AppData\Local\Temp\1605076671180.png)



**加了初始容量，反而变慢了。。。。**

![1605077266678](C:\Users\14579\AppData\Local\Temp\1605077266678.png)

寻找原因：

- 解释1：

  HashMap的机制是：当内部维护的哈希表的容量达到75%时，触发rehash。所以初始化容量要设置成num/3*4，而不是num。阿里的java开发手册里也写了这条，而且阿里为了抵消舍入误差，建议初始化容量设置为num/3*4+1。

- 解释2

  new HashMap(count)方法并不会直接以你传递的count值作为容器的初始值，而是从count中取出2的最大幂值，在乘2. 例如：已知有100个元素，则hashMap初始化时传递 100 * 4 / 3 + 1 = 134 作为初始值(2的最大幂值128);最后创建出来的hashMap容器的容量为256。



