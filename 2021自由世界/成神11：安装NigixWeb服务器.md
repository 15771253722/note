# WEB服务器NGINX安装部署

## 1.⾸先安装包并解压

这⾥下载的是nginx-1.17.10.tar.gz 安装包，并将其直接放在了root ⽬录下

1、在/usr/local/ 下创建nginx ⽂件夹并进⼊

```
cd /usr/local/ 
mkdir nginx 
cd nginx
```

2、将Nginx 安装包解压到/usr/local/nginx 中即可

```
[root@localhost nginx]# tar zxvf /root/nginx-1.17.10.tar.gz -C ./
```

解压完之后，/usr/local/nginx ⽬录中会出现⼀个nginx-1.17.10 的⽬录

## 2.预先安装额外的依赖

```
yum -y install pcre-devel 
yum -y install openssl openssl-devel
```

## 3.编译安装NGINX

```
cd nginx-1.17.10
./configure 
make && make install
```

安装完成后，Nginx的可执⾏⽂件位置位于

```
/usr/local/nginx/sbin/nginx
```

## 4.启动NGINX

直接执⾏如下命令即可：

```
[root@localhost sbin]# /usr/local/nginx/sbin/nginx
```

如果想停⽌Nginx服务，可执⾏：

```
/usr/local/nginx/sbin/nginx -s stop
```

如果修改了配置⽂件后想重新加载Nginx，可执⾏：

```
/usr/local/nginx/sbin/nginx -s reload
```

注意其配置⽂件位于：

```
/usr/local/nginx/conf/nginx.conf
```

## 5.浏览器验证启动情况

![image-20210228224142954](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228224142954.png)