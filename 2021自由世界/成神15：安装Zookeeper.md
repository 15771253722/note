# ZOOKEEPER安装部署 

## 1.准备安装包

这⾥使⽤的是3.6.1 版，下载的是apache-zookeeper-3.6.1-bin.tar.gz 压缩包，并将其放在了/root ⽬录下

## 2.解压并安装

在/usr/local/ 下创建zookeeper ⽂件夹并进⼊

```
cd /usr/local/ 
mkdir zookeeper 
cd zookeeper
```

将ZooKeeper 安装包解压到/usr/local/zookeeper 中即可

```
[root@localhost zookeeper]# tar -zxvf /root/apache-zookeeper-3.6.1-bin.tar.gz -C ./
```

解压完之后，/usr/local/zookeerper ⽬录中会出现⼀个apache-zookeeper-3.6.1-bin 的⽬录

## 3.创建DATA⽬录

这⾥直接在/usr/local/zookeeper/apache-zookeeper-3.6.1-bin ⽬录中创建⼀个data ⽬录。

等下该data ⽬录地址要配到ZooKeeper 的配置⽂件中：

## 4.创建配置⽂件并修改

进⼊到zookeeper 的conf ⽬录，复制zoo_sample.cfg 得到zoo.cf

```
[root@localhost apache-zookeeper-3.6.1-bin]# cd conf/ [root@localhost conf]# cp zoo_sample.cfg zoo.cfg
```

修改配置⽂件zoo.cfg ，将其中的dataDir 修改为上⾯刚创建的data ⽬录，其他选项可以按需配置



## 5.启动ZOOKEEPER

```
[root@localhost apache-zookeeper-3.6.1-bin]# ./bin/zkServer.sh start
```



启动后可以通过如下命令来检查启动后的状态：

```
[root@localhost apache-zookeeper-3.6.1-bin]# ./bin/zkServer.sh status
```

![image-20210301101617530](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301101617530.png)

从图中也可以看出zookeeper默认会绑定端⼝2181 。

## 6.配置环境变量

编辑配置⽂件：

```
vim /etc/profile
```

尾部加⼊ZooKeeper 的bin 路径配置即可

```
export ZOOKEEPER_HOME=/usr/local/zookeeper/apache-zookeeper-3.6.1-bin 
export PATH=$PATH:$ZOOKEEPER_HOME/bin
```

最后执⾏ source /etc/profile 使环境变量⽣效即可。

## 7.设置开机⾃启

⾸先进⼊/etc/rc.d/init.d ⽬录，创建⼀个名为zookeeper 的⽂件，并赋予执⾏权限

```
[root@localhost ~]# cd /etc/rc.d/init.d/
[root@localhost init.d]# touch zookeeper
[root@localhost init.d]# chmod +x zookeeper
```

接下来编辑zookeeper ⽂件，并在其中加⼊如下内容：

```
#!/bin/bash
#
# zookeeper  ---  this script is used to start and stop zookeeper
#
# chkconfig:   - 80 12
# description:  zookeeper is a centralized service for maintaining configuration information,naming,providing distributed synchronization,and providing group services. 
# processname: zookeeper
ZOOKEEPER_HOME=/usr/local/zookeeper/apache-zookeeper-3.6.1-bin 
export JAVA_HOME=/usr/local/java/jdk1.8.0_161 
# 此处根据你的实际情况更换对 应 
case $1 in
        start) su root $ZOOKEEPER_HOME/bin/zkServer.sh start;;
        stop) su root $ZOOKEEPER_HOME/bin/zkServer.sh stop;;
        status) su root $ZOOKEEPER_HOME/bin/zkServer.sh status;;
        restart) su root $ZOOKEEPER_HOME/bin/zkServer.sh restart;;
        *) echo "require start|stop|status|restart" ;;

esac
```

![image-20210301102922094](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301102922094.png)

最后加⼊开机启动即可：

```
chkconfig --add zookeeper 
chkconfig zookeeper on
```

