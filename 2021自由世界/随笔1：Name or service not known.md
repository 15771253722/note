# centos7 ping: www.baidu.com: Name or service not known

[root@www ~]# ping www.baidu.com
ping: www.baidu.com: Name or service not known

[root@www ~]#

1、**网络配置查看**

![image-20210220150249385](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210220150249385.png)

![image-20210220150305296](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210220150305296.png)

![image-20210220150317386](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210220150317386.png)

记住NAT设置中的子网IP、子网掩码、网关IP三项，接下来配置文件主要是这三项。

**2、编辑Linux中的网络配置文件**

vi /etc/sysconfig/network-scripts/ifcfg-ens33  #注 网络配置文件名可能会有不同，在输入到ifcfg时，可以连续按两下tab键，获取提示，比如我的机器 为 ifcfg-ens33

内容替换如下：

> TYPE=”Ethernet” 
> BOOTPROTO=”static” #静态连接 
> NAME=”ens33” 
> UUID=”1f093d71-07de-4ca5-a424-98e13b4e9532” 
> DEVICE=”ens33” 
> ONBOOT=”yes” #网络设备开机启动 
> IPADDR=”192.168.0.101” #192.168.59.x, x为3~255. 
> NETMASK=”255.255.255.0” #子网掩码 
> GATEWAY=”192.168.66.2” #网关IP
>
> DNS1= 8.8.8.8
>
> DNS2=8.8.8.4

 

**3、查看网络配置**

![image-20210220150335324](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210220150335324.png)

**4、\**重启网络服务\****

　　**service network restart**

**5、测试效果**

> ping 192.168.66.2 
> ping 115.239.210.27 （百度的某个IP，直接ping www.baidu.com 会出现域名解析问题，在保证网络连通后可进行配置）

**6、DNS文件配置**

> vi /etc/resolv.conf 
> nameserver 8.8.8.8
> nameserver 8.8.4.4

**7、测试**

![image-20210220150400206](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210220150400206.png)

OK 搞定正常上网！