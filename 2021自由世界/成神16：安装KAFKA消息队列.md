# 消息队列KAFKA安装部署

## ⾸先准备ZOOKEEPER服务

因为Kafka 的运⾏环境依赖于ZooKeeper ，所以⾸先得安装并运行ZooKeeper 。

## 准备KAFKA安装包

这⾥下载的是2.5.0 版：kafka_2.12-2.5.0.tgz ，将下载后的安装包放在了/root ⽬录下

## 解压并安装

在/usr/local/ 下创建kafka ⽂件夹并进⼊

```
cd /usr/local/ 
mkdir kafka 
cd kafka
```

将Kafka安装包解压到/usr/local/kafka 中即可

```
[root@localhost kafka]# tar -zxvf /root/kafka_2.12-2.5.0.tgz -C ./
```

解压完之后，/usr/local/kafka ⽬录中会出现⼀个kafka_2.12-2.5.0 的⽬录

## 创建LOGS⽬录

这⾥直接在/usr/local/kafka/kafka_2.12-2.5.0 ⽬录中创建⼀个logs ⽬录

等下该logs ⽬录地址要配到Kafka的配置⽂件中。

## 修改配置⽂件

进⼊到Kafka 的config ⽬录，编辑配置⽂件server.properties

```
[root@localhost kafka_2.12-2.5.0]# cd config/ [root@localhost config]# vim server.properties
```

修改配置⽂件，⼀是将其中的log.dirs 修改为上⾯刚创建的logs ⽬录，其他选项可以按需配置

![image-20210301104355730](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301104355730.png)

另外关注⼀下连接ZooKeeper 的相关配置，根据实际情况进⾏配置：

![image-20210301104455792](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301104455792.png)

## 启动KAFKA

执⾏如下命令即可：

```
./bin/kafka-server-start.sh ./config/server.properties
```

![image-20210301104538275](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301104538275.png)

如果需要后台启动，则加上-daemon 参数即可

![image-20210301104603744](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301104603744.png)

## 实验验证

⾸先创建⼀个名为mcj的topic ：

```
./bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic mcj
```

![image-20210301105359707](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301105359707.png)

服务必须启动  所以开了俩个端口

创建完成以后，可以使⽤命令来列出⽬前已有的topic 列表

```
./bin/kafka-topics.sh --list --bootstrap-server localhost:9092
```

![image-20210301105442540](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301105442540.png)

接下来创建⼀个**⽣产者**，⽤于在codesheep 这个topic 上⽣产消息：

```
./bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic mcj
```

![image-20210301110041002](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301110041002.png)

⽽后接着创建⼀个**消费者**，⽤于在codesheep 这个topic 上获取消息：

```
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mcj
```

此时⽣产者发出的消息，在消费者端可以获取到：

![image-20210301110050816](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301110050816.png)

## 注意事项

实验过程中如果出现⼀些诸如客户端不能连通或访问等问题，可尝试考虑关闭防⽕墙：

```
systemctl stop firewalld.service 
systemctl disable firewalld.service
```

