# KUBERNETES集群部署

## 1.节点规划

本⽂准备部署⼀个 **⼀主两从** 的 **三节点** Kubernetes集群，整体节点规划如下表所示：

![image-20210228225637835](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210228225637835.png)

```
下⾯介绍⼀下各个节点的软件版本：

操作系统：
CentOS-7.4-64Bit Docker版本：
1.13.1 Kubernetes版本：
1.13.1 所有节点都需要安装以下组件：

Docker ：不⽤多说了吧 
kubelet ：运⾏于所有 Node上，负责启动容器和 Pod 
kubeadm ：负责初始化集群 
kubectl ： k8s命令⾏⼯具，通过其可以部署/管理应⽤ 以及CRUD各种资源
```

## 2.准备⼯作

所有节点关闭防⽕墙

```
systemctl disable firewalld.service 
systemctl stop firewalld.service
```



禁⽤SELINUX

```
setenforce 0
vi /etc/selinux/config SELINUX=disabled
```



所有节点关闭 swap

```
swapoff -a
```

设置所有节点主机名

```
hostnamectl --static set-hostname  k8s-master 
hostnamectl --static set-hostname  k8s-node-1 
hostnamectl --static set-hostname  k8s-node-2
```

所有节点 主机名/IP加⼊ hosts解析

编辑/etc/hosts ⽂件，加⼊以下内容：

```
192.168.39.79 k8s-master 
192.168.39.77 k8s-node-1 
192.168.39.78 k8s-node-2
```

## 3.组件安装

0x01. Docker安装（所有节点）

不赘述了，参考上⽂Docker环境安装

0x02. kubelet、kubeadm、kubectl安装（所有节点）

⾸先准备repo

```
cat>>/etc/yum.repos.d/kubrenetes.repo<<EOF
[kubernetes] 
name=Kubernetes Repo 
baseurl=https://mirrors.aliyun.com/kubernetes/yum/repos/kubernetes-el7-x86_64/ 
gpgcheck=0 
gpgkey=https://mirrors.aliyun.com/kubernetes/yum/doc/yum-key.gpg
EOF


然后执⾏如下指令来进⾏安装
setenforce 0 
sed -i 's/^SELINUX=enforcing$/SELINUX= disabled/' /etc/selinux/config

yum install -y kubelet kubeadm kubectl 
systemctl enable kubelet && systemctl start kubelet
```

## 4.MASTER节点配置

0x01. 初始化 k8s集群

为了应对⽹络不畅通的问题，我们国内⽹络环境只能提前⼿动下载相关镜像并重新打 tag :

```
docker pull mirrorgooglecontainers/kube-apiserver:v1.13.1 
docker pull mirrorgooglecontainers/kube-controller-manager:v1.13.1 docker pull mirrorgooglecontainers/kube-scheduler:v1.13.1 
docker pull mirrorgooglecontainers/kube-proxy:v1.13.1 
docker pull mirrorgooglecontainers/pause:3.1 
docker pull mirrorgooglecontainers/etcd:3.2.24 
docker pull coredns/coredns:1.2.6
docker pull registry.cn-shenzhen.aliyuncs.com/cp_m/flannel:v0.10.0-amd64
docker tag mirrorgooglecontainers/kube-apiserver:v1.13.1 
k8s.gcr.io/kube-apiserver:v1.13.1 
docker tag mirrorgooglecontainers/kube-controller-manager:v1.13.1 
k8s.gcr.io/kube-controller-manager:v1.13.1 
docker tag mirrorgooglecontainers/kube-scheduler:v1.13.1 
k8s.gcr.io/kube-scheduler:v1.13.1 
docker tag mirrorgooglecontainers/kube-proxy:v1.13.1 k8s.gcr.io/kubeproxy:v1.13.1 
docker tag mirrorgooglecontainers/pause:3.1 k8s.gcr.io/pause:3.1 
docker tag mirrorgooglecontainers/etcd:3.2.24 k8s.gcr.io/etcd:3.2.24 docker tag coredns/coredns:1.2.6 k8s.gcr.io/coredns:1.2.6 
docker tag registry.cn-shenzhen.aliyuncs.com/cp_m/flannel:v0.10.0-amd64 quay.io/coreos/flannel:v0.10.0-amd64

docker rmi mirrorgooglecontainers/kube-apiserver:v1.13.1           docker rmi mirrorgooglecontainers/kube-controller-manager:v1.13.1  docker rmi mirrorgooglecontainers/kube-scheduler:v1.13.1           docker rmi mirrorgooglecontainers/kube-proxy:v1.13.1               docker rmi mirrorgooglecontainers/pause:3.1                        docker rmi mirrorgooglecontainers/etcd:3.2.24                      docker rmi coredns/coredns:1.2.6 
docker rmi registry.cn-shenzhen.aliyuncs.com/cp_m/flannel:v0.10.0-amd64
```

