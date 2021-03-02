**问题一：警告提示**

[2016-11-06T16:27:21,712][WARN ][o.e.b.JNANatives ] unable to install syscall filter: 

java.lang.UnsupportedOperationException: seccomp unavailable: requires kernel 3.5+ with CONFIG_SECCOMP and CONFIG_SECCOMP_FILTER compiled in
at org.elasticsearch.bootstrap.Seccomp.linuxImpl(Seccomp.java:349) ~[elasticsearch-5.0.0.jar:5.0.0]
at org.elasticsearch.bootstrap.Seccomp.init(Seccomp.java:630) ~[elasticsearch-5.0.0.jar:5.0.0]

报了一大串错误，其实只是一个警告。

**解决**：使用心得linux版本，就不会出现此类问题了。

 

**问题二：ERROR: bootstrap checks failed**

max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]
max number of threads [1024] for user [lishang] likely too low, increase to at least [2048]

**解决：**切换到root用户，编辑limits.conf 添加类似如下内容

vi /etc/security/limits.conf 

添加如下内容:

\* soft nofile 65536

\* hard nofile 131072

\* soft nproc 2048

\* hard nproc 4096

 ![image-20210301213222610](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301213222610.png)

**问题三**：max number of threads [1024] for user [lish] likely too low, increase to at least [2048]

**解决：**切换到root用户，进入limits.d目录下修改配置文件。

vi /etc/security/limits.d/90-nproc.conf 

修改如下内容：

\* soft nproc 1024

\#修改为

\* soft nproc 2048

 

**问题四**：max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144]

**解决**：切换到root用户修改配置sysctl.conf

vi /etc/sysctl.conf 

添加下面配置：

vm.max_map_count=655360

并执行命令：

sysctl -p

 ![image-20210301213101498](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301213101498.png)

![image-20210301213137947](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301213137947.png)

**问题五：**`max file descriptors [4096] for elasticsearch process likely too low, increase to at least [65536]`

**解决**：修改切换到root用户修改配置limits.conf 添加下面两行

***命令\***:vi /etc/security/limits.conf

\*    hard  nofile      65536
\*    soft  nofile      65536
切换到es的用户。

 

然后，重新启动elasticsearch，即可启动成功。

**六、后台运行**

最后还有一个小问题，如果你在服务器上安装Elasticsearch，而你想在本地机器上进行开发，这时候，你很可能需要在关闭终端的时候，让Elasticsearch继续保持运行。最简单的方法就是使用nohup。先按**Ctrl + C**，停止当前运行的Elasticsearch，改用下面的命令运行Elasticsearch

**nohup./bin/elasticsearch&**

这样，你就可以放心地关闭服务器终端，而不用担心Elasticsearch也跟着关闭了。