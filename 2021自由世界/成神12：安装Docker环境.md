# DOCKER环境安装

## 1.安装DOCKER

```
yum install -y docker
```

静候安装完毕即可

![image-20210228224756031](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228224756031.png)

## 2.开启DOCKER服务

```
systemctl start docker.service
```

## 3.查看安装结果

```
docker version
```

![image-20210228224918122](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228224918122.png)

## 4.设置开机启动

```
systemctl enable docker.service
```

## 5.配置DOCKER镜像下载加速

默认安装后的Docker 环境在拉取Docker 镜像时速度很慢：

![image-20210228225016635](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228225016635.png)

因此我们需要⼿动配置镜像加速源，提升获取Docker 镜像的速度。

配置⽅法⾮常简单。 

直接编辑配置⽂件：

```
vim /etc/docker/daemon.json
```

在其中加⼊加速镜像源地址即可：

```
{
  "registry-mirrors": ["http://hub-mirror.c.163.com"] 
}
```

⽐如这⾥使⽤的是⽹易 的加速源，其他像阿⾥云 、DaoCloud 这些也都提供加速源，按需选择即可。

加完加速地址后，重新加载配置⽂件，重启 docker 服务即可：

```
systemctl daemon-reload 
systemctl restart docker.service
```

这样镜像加速器就配置完成了，后续下载docker 镜像速度将⼤增。