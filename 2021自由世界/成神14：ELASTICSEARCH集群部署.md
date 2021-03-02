# ELASTICSEARCH集群部署

## 环境准备

**节点准备**
本⽂准备搭建 双节点 Elasticsearch集群（⽤双节点仅仅是做演示），因此这⾥准备了两台 Linux CentOS 7.4 64bit 机器：

节点1：
192.168.200.134 
节点2：
192.168.200.135 
Elasticsearch 安装包准备 
这⾥下载的是：
6.4.2 版

```
wget --no-check-certificate https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-6.4.2.tar.gz
```

链接用不了，官网禁止非页面方式下载。我直接去官网下的安装包。

**安装⽬录准备**

这⾥拟将 Elasticsearch安装在 /opt/elasticsearch ⽬录下：

```
mkdir /opt/elasticsearch 
将压缩包复制到该⽬录下并解压
tar -zxvf /root/elasticsearch-6.4.2.tar.gz -C ./
```

## ELASTICSEARCH 集群配置

需要修改两个节点上的配置⽂件 elasticsearch.yml

后面的空格要删掉

![image-20210301212859774](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301212859774.png)

```
节点1配置：
cluster.name: mcj         # 集群名称 
node.name: mcj1                 # 节点名 
network.host: 192.168.200.134     # 绑定的节点1地址 
network.bind_host: 0.0.0.0      # 此项不设置你试试本机可能访问不了啊 discovery.zen.ping.unicast.hosts: ["192.168.200.134","192.168.200.135"]  # hosts列表 
discovery.zen.minimum_master_nodes: 1 
## 如下配置是为了解决 Elasticsearch可视化⼯具 dejavu的跨域问题！若不⽤可视化⼯具 则可省略之 
http.port: 9200 
http.cors.allow-origin: "http://192.168.199.76:1358" 
http.cors.enabled: true 
http.cors.allow-headers : X-Requested-With,X-Auth-Token,Content-Type,cd Content-Length,Authorization 
http.cors.allow-credentials: true


节点2配置：
cluster.name: mcj         # 集群名称 
node.name: mcj1                 # 节点名 
network.host: 192.168.200.135     # 绑定的节点2地址 
network.bind_host: 0.0.0.0     
discovery.zen.ping.unicast.hosts: ["192.168.200.134","192.168.200.135"]  # hosts列表 
discovery.zen.minimum_master_nodes: 1 
## 如下配置是为了解决 Elasticsearch可视化⼯具 dejavu的跨域问题！若不⽤可视化⼯具 则可省略之 
http.port: 9200 
http.cors.allow-origin: "http://192.168.199.76:1358" 
http.cors.enabled: true 
http.cors.allow-headers : X-Requested-With,X-Auth-Token,Content-
Type,Content-Length,Authorization 
http.cors.allow-credentials: true
```

## 集群启动前准备

**创建⽤户及⽤户组**

由于 Elasticsearch不能以 root⽤户启动，因此需要添加⾮ root⽤户：

```
groupadd es 
useradd es -g es 
chown -R es:es ./elasticsearch-6.4.2
```

![image-20210301212958922](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301212958922.png)

**关闭防⽕墙**

```
systemctl stop firewalld 
systemctl disable firewalld
```

## 启动 ELASTICSEARCH集群

**切换⽤户**

```
su es
```

**分别在** **节点****1****和** **节点****2****上启动****ES****服务**

```
cd elasticsearch-6.4.2/bin
./elasticsearch  // 若要后台启动，则加-d参数
```

**浏览器访问：****http://ip:9200/** **查看启动效果**

![image-20210301215149222](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301215149222.png)

**浏览器访问：****http://ip:9200/** **查看启动效果**

![image-20210301215202330](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210301215202330.png)

**接下来插⼊两条数据**



**查看数据的⼊库效果**



## 安装IK分词器

**下载**

```
wget https://github.com/medcl/elasticsearch-analysis
ik/releases/download/v6.4.2/elasticsearch-analysis-ik-6.4.2.zip
```

**解压** **/** **安装**

```
/opt/elasticsearch/elasticsearch-6.4.2/plugins/elasticsearch-analysis-ik-
6.4.2
```

再将 zip包置于上述⽬录下并解压：

```
unzip elasticsearch-analysis-ik-6.4.2.zip
```

**重启** **Elasticsearch****集群**

重启 Elasticsearch集群，若发现如下内容，这说明插件安装成功：