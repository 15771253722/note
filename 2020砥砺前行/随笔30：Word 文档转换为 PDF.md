# Word 文档转换为 PDF

## 一.自己装jar包

1. #### 将Word文档直接转换成PDF

   ```java
   import com.spire.doc.*;

   publicclass ToPDF {
       public static void main(String[] args) {

           //加载Word示例文档
           Document document = new Document();
           document.loadFromFile("C:\\Users\\Test1\\Desktop\\Sample.docx");

           //保存结果文档
           document.saveToFile("output/toPDF", FileFormat.PDF);
       }
   }
   ```

2. #### 将Word文档直接转换成PDF

```java
import com.spire.doc.*;

publicclass ToPDF {
    public static void main(String[] args) {

        //加载Word示例文档
        Document document = new Document();
        document.loadFromFile("C:\\Users\\Test1\\Desktop\\Sample.docx");

        //保存结果文档
        document.saveToFile("output/toPDF", FileFormat.PDF);
    }
}
```

## 二.Maven工程

1. 在pom.xml文件中配置Maven仓库路径。

   ```xml
   <repositories>
           <repository>
               <id>com.e-iceblue</id>
               <url>http://repo.e-iceblue.cn/repository/maven-public/</url>
           </repository>
       </repositories>
   ```

   ​

2. 在pom.xml文件中指定Spire.PDF for Java的Maven依赖。

   ```xml
   <dependencies>
       <dependency>
           <groupId> e-iceblue </groupId>
           <artifactId>spire.pdf</artifactId>
           <version>3.4.2</version>
       </dependency>
   </dependencies>
   ```

   ​

3. 配置完成后，在IDEA中，您只需点击”Import Changes”即可导入JAR包；在Eclipse中，您需要点击”Save”按钮， JAR包才会自动下载。至此，您已经成功在Maven项目中添加了Spire.PDF JAR包依赖。

**\*注**： Free Spire.PDF for Java的artifactId为spire.pdf.free*

```xml
<dependencies>
    <dependency>
        <groupId>e-iceblue</groupId>
        <artifactId>spire.pdf.free</artifactId>
        <version>2.6.3</version>
    </dependency>
</dependencies>
```



**IDEA中的详细安装流程**

**第一步**：创建Maven项目。

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-1.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-2.png)

**第二步**：设置任意GroupId和ArtifactId。

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-3.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-4.png)

**第三步**：配置pom.xml文件，然后点击”Import Changes”。

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-5.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-6.png)

**Eclipse中的详细安装流程**

**第一步**：创建Maven项目。

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-7.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-8.png)

**第二步**：设置任意GroupId和ArtifactId。

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-9.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-10.png)

**第三步**：配置pom.xml文件，然后点击”Save”按钮。

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-11.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-12.png)

![通过 Maven 仓库安装 Spire.PDF for Java](https://www.e-iceblue.cn/images/tutorials-images/Install-SpirePDF-for-Java-from-Maven-Repository-13.png)