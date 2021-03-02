# 应⽤服务器TOMCAT安装部署 

## 1.准备安装包

这⾥使⽤的是8.5.55 版：apache-tomcat-8.5.55.tar.gz ，直接将其放在了/root ⽬录下

## 2.解压并安装

在/usr/local/ 下创建tomcat ⽂件夹并进⼊

```
cd /usr/local/ 
mkdir tomcat 
cd tomcat
```

将Tomcat 安装包解压到/usr/local/tomcat 中即可

```
[root@localhost tomcat]# tar -zxvf /root/apache-tomcat-8.5.55.tar.gz -C ./
```

解压完之后，/usr/local/tomcat ⽬录中会出现⼀个apache-tomcat-8.5.55 的⽬录

## 3.启动TOMCAT

直接进apache-tomcat-8.5.55 ⽬录，执⾏其中bin ⽬录下的启动脚本即可

```
[root@localhost apache-tomcat-8.5.55]# cd bin/ 
[root@localhost bin]# ./startup.sh
```

![image-20210228222142813](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228222142813.png)

![image-20210228222216361](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228222216361.png)

## 4.配置快捷操作和开机启动

⾸先进⼊/etc/rc.d/init.d ⽬录，创建⼀个名为tomcat 的⽂件，并赋予执⾏权限

```
[root@localhost ~]# cd /etc/rc.d/init.d/ 
[root@localhost init.d]# touch tomcat
[root@localhost init.d]# chmod +x tomcat
```

接下来编辑tomcat ⽂件，并在其中加⼊如下内容：

```
#!/bin/bash
#chkconfig:- 20 90
#description:tomcat
#processname:tomcat
TOMCAT_HOME=/usr/local/tomcat/apache-tomcat-8.5.55 case $1 in
        start) su root $TOMCAT_HOME/bin/startup.sh;;
        stop) su root $TOMCAT_HOME/bin/shutdown.sh;;
        *) echo "require start|stop" ;;

esac
```

这样后续对于Tomcat的开启和关闭只需要执⾏如下命令即可：

```
service tomcat start 
service tomcat stop
```

最后加⼊开机启动即可：

```
chkconfig --add tomcat 
chkconfig tomcat on
```

