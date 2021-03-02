# NODE环境安装 

## 1.准备NODE安装包

我这里下载的是node-v12.16.3-linux-x64.tar.xz 安装包，并将其直接放在了root目录下

## 2.创建⽬录并解压

### 2.1 在/usr/local/ 下创建node ⽂件夹并进⼊

```
cd /usr/local/ 
mkdir node 
cd node
```

### 2.2 将Node 的安装包解压到/usr/local/node 中即可

```
tar -xJvf /root/node-v12.16.3-linux-x64.tar.xz -C ./
```

解压完之后，/usr/local/node ⽬录中会出现⼀个node-v12.16.3-linux-x64 的⽬录

## 3.配置NODE系统环境变量

编辑~/.bash_profile ⽂件，在⽂件末尾追加如下信息:

![image-20210222093708330](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222093708330.png)

```
# Nodejs 
export PATH=/usr/local/node/node-v12.16.3-linux-x64/bin:$PATH
```

![image-20210222093753619](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222093753619.png)

刷新环境变量，使之⽣效即可：

```
source ~/.bash_profile
```

## 4.检查安装结果

```
node -v 
npm version 
npx -v
```

均有版本信息输出即可：

![image-20210222094001569](C:\Users\14579\AppData\Roaming\Typora\typora-user-images\image-20210222094001569.png)