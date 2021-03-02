# REDIS缓存安装部署 

## 1.⾸先准备REDIS安装包

这⾥下载的是redis-5.0.8.tar.gz 安装包，并将其直接放在了root ⽬录下

## 2.解压安装包

在/usr/local/ 下创建redis ⽂件夹并进⼊

```
cd /usr/local/ 
mkdir redis 
cd redis
```

将Redis 安装包解压到/usr/local/redis 中即可

```
[root@localhost redis]# tar zxvf /root/redis-5.0.8.tar.gz -C ./
```

解压完之后，/usr/local/redis ⽬录中会出现⼀个redis-5.0.8 的⽬录。

## 3.编译并安装

```
cd redis-5.0.8/ 
make && make install
```

## 4.将REDIS 安装为系统服务并后台启动 

进入utils ⽬录，并执⾏如下脚本即可：

```
[root@localhost redis-5.0.8]# cd utils/
[root@localhost utils]# ./install_server.sh
```

此处我全部选择的默认配置即可，有需要可以按需定制

这里是一路回车（选默认）

![image-20210224224119165](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210224224119165.png)

## 5.查看REDIS服务启动情况

直接执⾏如下命令来查看Redis的启动结果：

```
systemctl status redis_6379.service
```

![image-20210228193742831](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228193742831.png)

## 6.启动REDIS客户端并测试

启动自带的redis-cli 客户端，测试通过：：

```
systemctl status redis_6379.service
```

![image-20210228194254046](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228194254046.png)

但是此时只能在本地访问，⽆法远程连接，因此还需要做部分设置

## 7.设置允许远程连接

编辑redis 配置⽂件

```
vim /etc/redis/6379.conf 
```

将bind 127.0.0.1 修改为 0.0.0.0

![image-20210228194531848](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228194531848.png)

重启redis服务：

```
systemctl restart redis_6379.service
```

![image-20210228195418746](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228195418746.png)

## 8.设置访问密码

编辑redis 配置⽂件

```
vim /etc/redis/6379.conf 
```

找到如下内容：

```
#requirepass foobared
```

去掉注释，将foobared 修改为⾃⼰想要的密码，保存即可。

```
requirepass FreeWorld
```

保存，重启Redis 服务即可

```
systemctl restart redis_6379.service
```

这样后续的访问需要先输⼊密码认证通过⽅可：

![image-20210228195232294](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228195232294.png)