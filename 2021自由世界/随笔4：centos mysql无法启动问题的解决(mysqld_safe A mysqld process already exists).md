# centos mysql无法启动问题的解决(mysqld_safe A mysqld process already exists)

ps aux |grep mysqld(啥意思我也不清楚，没时间了解了，效率优先），然后观察这几个进程的id，分别kill掉。

![image-20210223145715924](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210223145715924.png)

 软连接  ln -s /var/lib/mysql/mysql.sock /tmp/mysql.sock