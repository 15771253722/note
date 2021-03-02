# GIT工具安装

## 方式一：通过包管理器安装

在Linux上安装Git向来仅需一行命令即可搞定，因为有各式各样的包管理器。

```
yum install git
```

当然通过这种方式安装的Git可能不是较新版的Git，以我们的的环境CentOS 7.4来说，这种方式安装的Git版本位1.8.3.1，不过一般来说是够用的。

## 方式二：通过源码编译安装

删除老Git

```
yum -y remove git
```

如果想安装较新版本的Git，则需要自行下载Git源码来编译安装。

### 1.准备Git安装包

我这里选择安装的是2.26.2版，将下载好的安装包v2.26.2.tar.gz直接 放在root目录下，然后本地解压，得到git-2.26.2目录：

```
tar -zxvf Git-v2.26.2.tar.gz
```

### 2.提前安装可能需要的依赖

```
yum install curl-devel expat-devel gettext-devel openssl-devel zlibdevel gcc-c++ perl-ExtUtils-MakeMaker
```

途中选择俩个Y  再卸载git1.8

### 3.编译安装Git

进入到对应目录，执行配置、编译、安装命令即可，如下图：

```
[root@localhost ~]# cd git-2.26.2/

[root@localhost git-2.26.2]# make configure
[root@localhost git-2.26.2]# ./configure --prefix=/usr/local/git 
这俩个命令有可能不好使，直接第四个命令就行

[root@localhost git-2.26.2]# make prefix=/usr/local/git all（这句可能有用）
[root@localhost git-2.26.2]# make profix=/usr/local/git
[root@localhost git-2.26.2]# make install
```

### 4.将Git加入环境变量

```
vim /etc/profile
尾部加入：
export GIT_HOME=/usr/local/git 
export PATH=$PATH:$GIT_HOME/bin
最后执行：
source /etc/profile
刷新环境变量
```

### 5.查看安装结果

```
git --version
```

![image-20210220170505081](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210220170505081.png)