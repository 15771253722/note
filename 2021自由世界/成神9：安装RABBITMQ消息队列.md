# 消息队列RABBITMQ安装部署

## 1.⾸先安装ERLANG环境

因为RabbitMQ 需要erlang 环境的⽀持，所以必须先安装erlang。

我们这⾥要安装的是 erlang-22.3.3-1.el7.x86_64.rpm ，所以⾸先执⾏如下命令来安装其对应的 yum repo ：

```
curl -s https://packagecloud.io/install/repositories/rabbitmq/erlang/script.rpm.sh | sudo bash
```

![image-20210228200349091](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228200349091.png)

![image-20210228201256110](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228201256110.png)

接下来执⾏如下命令正式安装erlang 环境：

```
yum install erlang-22.3.3-1.el7.x86_64
```

![image-20210228201624805](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228201624805.png)

那个选择的  直接回车就行

接着上⾯提示再次执⾏如下命令即可：

记得看上面的命令：

![image-20210228201840918](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228201840918.png)

```
yum load-transaction /tmp/yum_save_tx.2021-02-28.04-13.C72FNf.yumtx
```

![image-20210228202403914](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228202403914.png)

接下来可以直接执⾏如下命令，测试erlang 是否安装成功：

```
erl
```

![image-20210228202432998](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228202432998.png)

## 2.安装RABBITMQ

### 下载安装包：

https://dl.bintray.com/rabbitmq/all/rabbitmq-server/ ：

文件名:rabbitmq-server-generic-unix-3.8.3.tar.xz

注意下载下来的文件是xz格式的，xz命令 XZ Utils 是为 POSIX 平台开发具有高压缩率的工具。它使用 LZMA2 压缩算法，生成的压缩文件比 POSIX 平台传统使用的 gzip、bzip2 生成的压缩文件更小，而且解压缩速度也很快。最初 XZ Utils 的是基于 LZMA-SDK 开发，但是 LZMA-SDK 包含了一些 WINDOWS 平台的特性，所以 XZ Utils 为以适应 POSIX 平台作了大幅的修改。XZ Utils 的出现也是为了取代 POSIX 系统中旧的 LZMA Utils。

### 解压：

```
xz -d rabbitmq-server-generic-unix-3.8.3.tar.xz

tar -xvf rabbitmq-server-generic-unix-3.8.3.tar

mv rabbitmq_server-3.8.3 /usr/local/
```

### 配置环境变量：

```
vim /etc/profile
# 在最后一行添加如下命令
export PATH=$PATH:/usr/local/lib/erlang/bin:/usr/local/rabbitmq_server-3.8.3/sbin
source /etc/profile
```

### 启动rabbitMQ：

```
rabbitmq-plugins enable rabbitmq_management
rabbitmq-server 或 rabbitmq-server -detached(后台运行)
后台运行有个bug：
Warning: PID file not written; -detached was passed.（不要管）
rabbitmqctl status 看状态，服务已经启动
```

![image-20210228210336135](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228210336135.png)

添加新用户：

默认账号密码是（guest/guest）
这里新添加账号：admin/admin

```
rabbitmqctl add_user admin admin
rabbitmqctl set_user_tags admin administrator
```

### 访问后台

http://localhost:15672/

### 停止mq

```
rabbitmqctl shutdown
```

### 解除guest账户的本地登录限制

修改核心配置参数

rabbitmq的核心配置参数在/usr/local/rabbitmq_server-3.8.3/ebin/rabbit.app里

```html
vim /usr/local/rabbitmq_server-3.8.3/ebin/rabbit.app
# 找到loopback_users 修改为如下内容（去除 <<"guest">>中<<"">>） {loopback_users, [guest]},
```

![image-20210228221145010](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228221145010.png)

## 2.设置RABBITMQ开机启动

```
chkconfig rabbitmq_server-3.8.3 on
```

## 5.访问可视化管理界⾯：

浏览器输⼊：你的服务器IP:15672

![image-20210228212422138](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228212422138.png)