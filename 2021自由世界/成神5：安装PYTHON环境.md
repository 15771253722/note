# PYTHON环境安装

CentOS 7.4 默认⾃带了⼀个Python2.7 环境：

![image-20210222095123882](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222095123882.png)

然⽽现在主流都是Python3,所以接下来再装一个python3，打造一个共存的环境。

退出： ctrl+z

## 1.准备PYTHON3安装包并解压

我这⾥下载的是Python-3.8.3.tgz 安装包，并将其直接放在了/root目录下

执⾏如下命令解压之：

```
tar zxvf Python-3.8.3.tgz
```

则可以在当前⽬录得到⽂件夹：Python-3.8.3

## 2.安装相关预备环境

直接执⾏如下命令即可：

```
yum install zlib-devel bzip2-devel openssl-devel ncurses-devel sqlitedevel readline-devel tk-devel gcc make
```

## 3.编译并安装

这⾥指定了安装⽬录为/usr/local/python3 ，有需要可以⾃定义

```
cd Python-3.8.3/
./configure prefix=/usr/local/python3
make install
```

等安装过程完毕，/usr/local/python3 ⽬录就会⽣成了

## 4.添加软链接

我们还需要将刚刚安装⽣成的⽬录/usr/local/python3 ⾥的python3 可执⾏⽂件做⼀份软链接，链接到/usr/bin 下，⽅便后续使⽤python3

```
ln -s /usr/local/python3/bin/python3 /usr/bin/python3 
ln -s /usr/local/python3/bin/pip3 /usr/bin/pip3
```

## 5.验证安装

命令⾏输⼊python3 ，即可查看Python3 版本的安装结果

![image-20210222103727337](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222103727337.png)