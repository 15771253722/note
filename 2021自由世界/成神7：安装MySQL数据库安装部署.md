# MYSQL数据库部署和安装

## 1.⾸先准备安装包

这⾥下载的是mysql-5.7.30-linux-glibc2.12-x86_64.tar.gz 安装包，并将其直接放在了root⽬录下。

## 2.卸载系统⾃带的MARIADB（如果有） 

如果系统之前⾃带 Mariadb ，可以先卸载之。

⾸先查询已安装的 Mariadb 安装包：

```
rpm -qa|grep mariadb
```

![image-20210222133731603](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222133731603.png)

将其均卸载之：

![image-20210222133823067](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222133823067.png)

## 3.解压MYSQL安装包

将上⾯准备好的MySQL 安装包解压到/usr/local/ ⽬录，并重命名为mysql

```
tar -zxvf /root/mysql-5.7.30-linux-glibc2.12-x86_64.tar.gz -C /usr/local/ 
要先进/usr/local/目录里
mv mysql-5.7.30-linux-glibc2.12-x86_64 mysql
```

## 4.创建MYSQL⽤户和⽤户组

```
groupadd mysql 
useradd -g mysql mysql
```

同时新建/usr/local/mysql/data ⽬录，后续备⽤

```
mkdir /usr/local/mysql/data
```

## 5.修改MYSQL⽬录的归属⽤户

```
[root@localhost mysql]# chown -R mysql:mysql ./
```

## 6.准备MYSQL的配置⽂件

在/etc ⽬录下新建my.cnf ⽂件 

vim命令也有直接创建的功能：

写⼊如下简化配置：

```
[mysql]
# 设置mysql客户端默认字符集 default-character-set=utf8 socket=/var/lib/mysql/mysql.sock
[mysqld] 
skip-name-resolve
#设置3306端⼝ 
port = 3306 
socket=/var/lib/mysql/mysql.sock
# 设置mysql的安装⽬录 
basedir=/usr/local/mysql
# 设置mysql数据库的数据的存放⽬录 
datadir=/usr/local/mysql/data
# 允许最⼤连接数 
max_connections=200
# 服务端使⽤的字符集默认为8⽐特编码的latin1字符集 character-set-server=utf8
# 创建新表时将使⽤的默认存储引擎 
default-storage-engine=INNODB
lower_case_table_names=1
max_allowed_packet=16M
# client
port=3306
socket=/var/lib/mysql/mysql.sock
```

同时使⽤如下命令创建/var/lib/mysql ⽬录，并修改权限：

```
mkdir /var/lib/mysql 
chmod 777 /var/lib/mysql
```

## 7.正式开始安装MYSQL

执⾏如下命令正式开始安装：

```
cd /usr/local/mysql
./bin/mysqld --initialize --user=mysql --basedir=/usr/local/mysql --datadir=/usr/local/mysql/data
```

![image-20210223115624320](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210223115624320.png)

注意：记住上⾯打印出来的root 的密码，后⾯⾸次登陆需要使⽤

## 8.复制启动脚本到资源⽬录

执⾏如下命令复制：

```
[root@localhost mysql]# cp ./support-files/mysql.server /etc/init.d/mysqld
```

并修改/etc/init.d/mysqld ，修改其basedir 和datadir 为实际对应⽬录：

```
basedir=/usr/local/mysql 
datadir=/usr/local/mysql/data
```

## 9.设置MYSQL系统服务并开启⾃启 

首先增加mysqld 服务控制脚本执⾏权限：

```
chmod +x /etc/init.d/mysqld
```

同时将mysqld 服务加⼊到系统服务：

```
chkconfig --add mysqld
```

最后检查mysqld 服务是否已经⽣效即可：

```
chkconfig --list mysqld
```

![image-20210223120625963](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210223120625963.png)

这样就表明mysql服务已经生效了，在2，3，4，5运行级别随系统启动而自动启动，以后可以直接使用service命令控制mysql的启停了。

## 10.启动MYSQL

直接执行：

```
service mysqld start
```

![image-20210223140129002](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210223140129002.png)

## 11.将MYSQL的BIN目录加入PATH环境变量

这样⽅便以后在任意⽬录上都可以使⽤mysql 提供的命令。

编辑~/.bash_profile ⽂件，在⽂件末尾处追加如下信息:

```
export PATH=$PATH:/usr/local/mysql/bin
```

![image-20210223140415620](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210223140415620.png)

最后执⾏如下命令使环境变量⽣效

```
source ~/.bash_profile
```

## 12.首次登陆MYSQL

以root账号登陆mysql ，使⽤上⽂安装完成提示的密码进⾏登⼊

```
mysql -u root -p
```

![image-20210224163028724](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210224163028724.png)

## 13.修改ROOT账户密码

在mysql的命令⾏执⾏如下命令即可，密码可以换成你想⽤的密码即可：

```
mysql>alter user user() identified by "FreeWorld"; 
mysql>flush privileges;
```

![image-20210224163230956](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210224163230956.png)

## 14.设置远程主机登录

```
mysql> use mysql; 
mysql> update user set user.Host='%' where user.User='root'; 
mysql> flush privileges;
```

![image-20210224164015533](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210224164015533.png)

## 15.利⽤NAVICAT等⼯具进⾏测试

测试肯定试不通的  没有关闭防火墙

```
centos7以前：
service iptables stop
centos7之后：
systemctl stop firewalld
```

![image-20210224164914291](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210224164914291.png)

![image-20210224164929445](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210224164929445.png)