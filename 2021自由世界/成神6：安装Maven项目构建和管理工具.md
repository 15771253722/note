# MAVEN项⽬构建和管理⼯具安装 

## 1.准备MAVEN安装包并解压

这⾥下载的是apache-maven-3.6.3-bin.tar.gz 安装包，并将其放置于提前创建好的/opt/maven⽬录下。

执⾏命令解压之：

```
tar zxvf apache-maven-3.6.3-bin.tar.gz
```

即可在当前⽬录得到/opt/maven/apache-maven-3.6.3 ⽬录

## 2.配置MAVEN加速镜像源

这⾥配置的是阿⾥云的maven镜像源。

编辑修改 /opt/maven/apache-maven-3.6.3/conf/settings.xml ⽂件，在标签对⾥添加如下内容即可：

```
<mirror>
        <id>alimaven</id>
        <name>aliyun maven</name>
        <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
        <mirrorOf>central</mirrorOf>
</mirror>
```

![image-20210222130718969](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222130718969.png)

## 3.配置环境变量

因为下载的是⼆进制版安装包，所以解压完，配置好环境变量即可使⽤了。

编辑修改 /etc/profile ⽂件，在⽂件尾部添加如下内容，maven 的安装路径。

```
export MAVEN_HOME=/opt/maven/apache-maven-3.6.3 
export PATH=$MAVEN_HOME/bin:$PATH
```

接下来执⾏source /etc/profile 来刷新环境变量，让maven 环境的路径配置⽣效，

## 4.检验安装结果

执⾏mvn -version ，能打印出maven 版本信息说明安装、配置成功：

![image-20210222132854806](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222132854806.png)