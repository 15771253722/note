### springboot打成war包并部署tomcat服务器



### 操作步骤

1. 在pom文件中添加<packaging>war</packaging>
2. 在pom文件中添加maven插件依赖
3. 在pom文件中剔除springboot内嵌的tomcat依赖
4. 在pom文件中添加外部（自己的）tomcat依赖
5. 修改启动类
6. idea自带maven插件打包成war并发布到自己本机的tomcat中

### pom文件内容：

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.8.RELEASE</version>
    </parent>
    <groupId>com.itcast</groupId>
    <artifactId>luban1</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>luban</name>
    <packaging>war</packaging>

    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </dependency>
        	<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
~~~

### 启动类的修改：

~~~java
package com.itcast.luban1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Luban1Application extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(Luban1Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Luban1Application.class);
	}
}

~~~

代码修改完成后，在idea中用maven插件打成war包并发布到自己本机的tomcat中weapps目录下，并将项目名称修改为luban，然后在bin目录中双击starter.bat。

页面中输入http://localhost:8080/luban/anqila即可测试。