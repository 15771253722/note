# docker和虚拟机

## 突发问题：

在虚拟机中用docker指令，突然用了下linux指令，发现找不见docker创造的那些软件（这里叫容器更恰当一下）。查了2-3小时文档，也算是把这俩个概念搞明白了。

## 自己的理解（人话）：

比如这台电脑：一个win10系统，自己开发，必须装个虚拟机，然后虚拟机里装各个开发用的东西。

有了docker：直接把东西打包成Docker镜像，东西不用装了，直接开发。

## 官方话：

- 虚拟机：在一台物理机器上，利用虚拟化技术，虚拟出来多个操作系统，每个操作系统之间是隔离的。举例说明：电脑上安装操作系统，比如我们安装了win10的操作系统；再往上安装虚拟机软件，比如我们常用的VirtualBox、VMWare，它们的作用是模拟计算机硬件；这样虚拟机模拟出来的操作系统了；在虚拟的操作系统中，安装所需的软件、组件等。比如我们在虚拟操作系统中安装JDK、Tomcat等；最后就是具体的应用了，也就是把应用部署到Tomcat中。


- Docker：官方解释是开源的应用容器引擎。用人话解释一下：依然需要现在电脑上安装操作系统， 然后安装Docker容器的管理器，到了这一步，不需要自己安装JDK和Tomcat，而是由开发人员将素有的依赖和应用都被打包成了Docker镜像。例如，JDK、Tomcat、应用都被打包在了一起，运行在Docker容器里，容器和容器间是隔离的。

![docker和虚拟机有什么区别](https://img-blog.csdnimg.cn/20190421162219895.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3l3ODg4NjQ4NA==,size_16,color_FFFFFF,t_70)

## 区别：

- 从两者的架构图上看，虚拟机是在硬件级别进行虚拟化，模拟硬件搭建操作系统；而Docker是在操作系统的层面虚拟化，复用操作系统，运行Docker容器。
- Docker的速度很快，秒级，而虚拟机的速度通常要按分钟计算。
- Docker所用的资源更少，性能更高。同样一个物理机器，Docker运行的镜像数量远多于虚拟机的数量。
- 虚拟机实现了操作系统之间的隔离，Docker算是进程之间的隔离，虚拟机隔离级别更高、安全性方面也更强。

![docker和虚拟机有什么区别](https://img-blog.csdnimg.cn/20190421162358700.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3l3ODg4NjQ4NA==,size_16,color_FFFFFF,t_70)

## docker能不能取代虚拟机：

- Docker是可以用于生产环境的；但是Docker是有学习成本的，前期要踩不少的坑；
- 如果没有容器集群管理工具的话，只用Docker的话，没有太大必要（人肉维护？开发和运维都会死的）；
- 虚拟机和Docker各有优势，很多企业都采用物理机上做虚拟机，虚拟机中跑Docker的方式。
- 总之，要说Docker代替虚拟机还为时过早，至少短期内不会；