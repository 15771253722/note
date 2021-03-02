# nginx

## 1.重启指令

先刷redis 

里面有值了

再重启nginx

./nginx -s reload

## 2.压力测试

```xml
nginx压力测试方法：
#ab命令
#安装ab
#Centos系统
yum install apr-util
#Ubuntu系统
sudo apt-get install apache2-utils
 
#ab命令的参数
-n //在测试会话中所执行的请求个数。默认为1
-c //一次产生的请求个数。默认为1
-t //测试所进行的最大秒数。默认值为50000
-p //包含了需要的POST的数据文件
-T //POST数据所使用的Content-type头信息
 
#实例
ab -c 1000 -n 5000 http://www.baidu.com/
每次发送1000并发的请求数，请求数总数为5000。
 
------------------------------------------------------------------------------
#nginx防止被压力测试的设置方法：
#限制同一IP并发数最大为10
vim /etc/nginx/nginx.conf
http{}字段第一行添加：
limit_conn_zone $binary_remote_addr zone=one:10m;
vim /etc/nginx/conf.d/default
server{}字段添加：
limit_conn one 10;
 
#重启nginx
service nginx restart
 
#如出现这种错误提示，表明nginx.conf中的limit_conn_zone $binary_remote_addr zone=one:10m;
#没有添加到正确的区域，最后添加在http字段的第一行。
the size 10485760 of shared memory zone "one" conflicts with already declared size 0 in /etc/nginx/nginx.conf:33
```

## 参数分析:

### 截图：

![](C:\Users\14579\Pictures\笔记图\压测图.png)

### 简单分析：

```java
#最重要的指标之一，相当于LR中的每秒事务数，后面括号中的mean表示这是一个平均值，越大抗压越强  
Time per request:       218.982 [ms] (mean)   
#最重要的指标之二，相当于LR中的平均事务响应时间，后面括号中的mean表示这是一个平均值  
Time per request:       0.219 [ms] (mean, across all concurrent requests)  
Transfer rate:          330822.86 [Kbytes/sec] received  
#平均每秒网络上的流量，吞吐量，越大抗压越强  
  
Connection Times (ms)  
              min  mean[+/-sd] median   max  
Connect:        0    0   4.5      0      29  
Processing:     1   17  14.5     17     653  
Waiting:        0   17  14.5     17     653  
Total:         15   18  15.2     17     674  
  
Percentage of the requests served within a certain time (ms)  
  50%     17  
  66%     18  
  75%     18  
  80%     18  
  90%     20  
  95%     22  
  98%     37  
  99%     40  
 100%    674 (longest request)  
```

### 专业分析：

```java
启动ab，并出入三个参数（PS D:\wamp\bin\apache\Apache2.2.21\bin> .\ab -n1000 -c10 http://localhost/index.php ）  
-n1000    表示请求总数为1000  
-c10      表示并发用户数为10  
http://localhost/index.php   表示这写请求的目标URL  
测试结果也一目了然:   
测试出的吞吐率为：   Requests per second: 2015.93 [#/sec] (mean)  ---除此之外还有其他一些信息----  
Server Software //表示被测试的Web服务器软件名称  
Server Hostname  //表示请求的URL主机名  
Server Port     //表示被测试的Web服务器软件的监听端口  
Document Path   //表示请求的URL中的根绝对路径，通过该文件的后缀名，我们一般可以了解该请求的类型  
Document Length   //表示HTTP响应数据的正文长度  
Concurrency Level  //表示并发用户数，这是我们设置的参数之一  
Time taken for tests //表示所有这些请求被处理完成所花费的总时间  
Complete requests  //表示总请求数量，这是我们设置的参数之一  
Failed requests    //表示失败的请求数量，这里的失败是指请求在连接服务器、发送数据等环节发生异常，以及无响应后超时的情况。如果接收到的HTTP响应数据的头信息中含有2XX以外的状态码，则会在测试结果中显示另一个名为       “Non-2xx responses”的统计项，用于统计这部分请求数，这些请求并不算在失败的请求中。  
Total transferred   //表示所有请求的响应数据长度总和，包括每个HTTP响应数据的头信息和正文数据的长度。注意这里不包括HTTP请求数据的长度，仅仅为web服务器流向用户PC的应用层数据总长度。  
HTML transferred    //表示所有请求的响应数据中正文数据的总和，也就是减去了Total transferred中HTTP响应数据中的头信息的长度。  
Requests per second   //吞吐率，计算公式：Complete requests / Time taken for tests  
Time per request   //用户平均请求等待时间，计算公式：Time token for tests/（Complete requests/Concurrency Level）  
Time per requet(across all concurrent request)   //服务器平均请求等待时间，计算公式：Time taken for tests/Complete requests，正好是吞吐率的倒数。也可以这么统计：Time per request/Concurrency Level  
Transfer rate    //表示这些请求在单位时间内从服务器获取的数据长度，计算公式：Total trnasferred/ Time taken for tests，这个统计很好的说明服务器的处理能力达到极限时，其出口宽带的需求量。  
Percentage of requests served within a certain time（ms）   //这部分数据用于描述每个请求处理时间的分布情况，比如以上测试，80%的请求处理时间都不超过6ms，这个处理时间是指前面的Time per request，即对于单个用户而言，平均每个请求的处理时间。  
```

## 3.刷新redis缓存---lua脚本

添加缓存

http://192.168.200.128/ad_update?position=web_index_lb

读取缓存---也可以直接看redis

http://192.168.200.128/ad_read?position=web_index_lb