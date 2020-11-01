# 三.框架：SpringBoot框架

## 1.自动配置/开箱即用原理

​	Spring Boot 内部提供了很多自动化配置的类，例如，RedisAutoConfiguration 、MongoRepositoriesAutoConfiguration 、ElasticsearchAutoConfiguration ， 这些自动化配置的类会判断 classpath 中是否存在自己需要的那个类，如果存在则会自动配置相关的配置，否则就不会自动配置，因此，开发者在 Maven 的 pom 文件中添加相关依赖后，Spring Boot 就会针对这个应用自动创建和注入需要的 Spring Bean 到上下文中。这些依赖就会下载很多 jar 包到 classpath 中 ，有了这些 lib 就会触发自动化配置，所以，我们就能很便捷地使用对于的模块功能了。

## 2.整合Web

一.整合Servlet

1.通过注解扫描完成Servlet组件的注册

1.1编写servlet

```java
/**
 *
 * springboot整合servlet方式一
 * @author java
 *<servlet>
 *    <servlet-name>FirstServletController</servlet-name>
 *    <servlet-class>com.zzp.controller.FirstServletController</servlet-class>
 *</servlet>
 *<servlet-mapping>
 *    <servlet-name>FirstServletController</servlet-name>
 *    <url-pattern>/firstServlet</url-pattern>
 *</servlet-mapping>
 */
@WebServlet(name="FirstServletController",urlPatterns="/firstServlet")
public class FirstServletController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FirstServlet。。。。。。。。。。。。。");
    }

}
```

1.2编写启动类

```java
/**
 * 
 * springboot整合servlet方式一
 * @author java
 *
 */
@SpringBootApplication
@ServletComponentScan//在servlet启动时扫描@WebServlet这个注解
public class APP {
    
    public static void main(String[] args) {
    SpringApplication.run(APP.class, args);
    }

}
```

2.通过方法完成Servlet组件的注册

2.1编写servlet

```java
/**
 *
 * springboot整合servlet的方式二
 *
 * @author java
 *
 */
public class SecondServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("secondServlet.................");
    }

}
```

2.2编写启动类

```java
/**
 *
 * springboot整合servlet的第二种方式
 * 如果第二种方式启动项目时，端口号被占用了，
 * 则在在src->main->resources目录下新建一个文件，名称为：application.properties
 * 设置端口号为server.port = 9527
 * @author java
 *
 */
@SpringBootApplication
public class AppSecond {

    public static void main(String[] args) {
        SpringApplication.run(AppSecond.class, args);
    }

    @Bean
    public ServletRegistrationBean getServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
        bean.addUrlMappings("/secondServlet");
        return bean;
    }
}
```

二.整合Filter

1.通过注解扫描完成Filter组件的注册

1.1编写Filter

```java
/**
 *
 * 方式一：通过注解扫描完成filter组件的注入
 * <filter>
 *     <filter-name></filter-name>
 *     <filter-class></filter-class>
 * </filter>
 * <filter-mapping>
 *     <filter-name></filter-name>
 *     <url-pattern></url-pattern>
 * </filter-mapping>
 * @author java
 *
 */
//@WebFilter(filterName="FirstFilter",urlPatterns={"*.do","*.jsp"})
@WebFilter(filterName="FirstFilter",urlPatterns="/firstServlet")
public class FirstFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("开始进入filter");
        chain.doFilter(request, response);
        System.out.println("离开filter");
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
```

```java
/**
 *
 * springboot整合servlet方式一
 * @author java
 *<servlet>
 *    <servlet-name>FirstServletController</servlet-name>
 *    <servlet-class>com.zzp.controller.FirstServletController</servlet-class>
 *</servlet>
 *<servlet-mapping>
 *    <servlet-name>FirstServletController</servlet-name>
 *    <url-pattern>/firstServlet</url-pattern>
 *</servlet-mapping>
 */
@WebServlet(name="FirstServletController",urlPatterns="/firstServlet")
public class FirstServletController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FirstServlet。。。。。。。。。。。。。");
    }

}
```

1.2编写启动类

```java
@SpringBootApplication
@ServletComponentScan
public class APP01 {

    public static void main(String[] args) {
        SpringApplication.run(APP01.class, args);
    }
}
```

2.通过方法完成Filter组件的注册

2.1编写Filter

```java
public class SecondFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        System.out.println("开始进入filter");
        chain.doFilter(request, response);
        System.out.println("离开filter");

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
```

```java
/**
 *
 * springboot整合servlet的方式二
 *
 * @author java
 *
 */
public class SecondServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("secondServlet.................");
    }

}
```

2.2编写启动类

```java
/**
 *
 * springboot通过方法注册filter的第二种方式
 * @author java
 *
 */
@SpringBootApplication
public class App02 {

    public static void main(String[] args) {
        SpringApplication.run(App02.class, args);
    }

    @Bean
    public ServletRegistrationBean getServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new SecondServlet());
        bean.addUrlMappings("/secondServlet");
        return bean;
    }

    @Bean
    public FilterRegistrationBean getFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new SecondFilter());
        //bean.addUrlPatterns(new String[] {"*.do","*.jsp"});
        bean.addUrlPatterns("/secondServlet");
        return bean;
    }
}
```

三.整合Listener

1通过注解扫描完成Listener组件的注册

1.1编写Listener

```java
/** *  * 方法一：通过注解扫描完成 Listener 组件的注册 * @author java *<listener> * <listener-class>com.bjsxt.listener.FirstListener</listener-class> *</listener> */@WebListenerpublic class FirstListener implements ServletContextListener{
	@Override	public void contextInitialized(ServletContextEvent sce) {		System.out.println("listener....init....");	}
	@Override	public void contextDestroyed(ServletContextEvent sce) {			}	}
```

1.2编写启动类

```java
/**
 *
 * 方法一：通过注解扫描完成 Listener 组件的注册
 * @author java
 *
 */
@SpringBootApplication
@ServletComponentScan
public class App01 {

    public static void main(String[] args) {
        SpringApplication.run(App01.class, args);
    }

}
```

2.通过方法完成Listenner组件注册

2.1编写Listen

```java
/**
 *
 * springboot注册listener的方式二
 * @author java
 *
 */
public class SecondListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("SecondListener...init...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub

    }

}
```

2.2编写启动类

```java
/**
 *
 * springboot注册listener的方式二
 * @author java
 *
 */
@SpringBootApplication
public class App02 {
    public static void main(String[] args) {
        SpringApplication.run(App02.class, args);
    }

    @Bean
    public ServletListenerRegistrationBean<SecondListener> getServletListenerRegistrationBean(){
        ServletListenerRegistrationBean<SecondListener> bean = new ServletListenerRegistrationBean<SecondListener>(new SecondListener());
        return bean;
    }
}
```

四.访问静态资源

1.SpringBoot 从 classpath/static的目录

注意目录名称必须是static

![img](C:\Users\14579\Pictures\笔记图\静态资源1.jpg)

2.ServletContext根目录下

在src/main/webapp目录名称必须要webapp

![img](C:\Users\14579\Pictures\笔记图\静态资源2.jpg)

五，文件上传

1. 编写 Controller

```java
 1 /**
 2  * 
 3  * 文件上传
 4  * @author java
 5  *
 6  */
 7 @RestController
 8 public class FileUploadController {
 9     @RequestMapping("/fileUploadController")
10     public Map<String,Object> getUploadFile(MultipartFile filename) throws IllegalStateException, IOException{
11         System.out.println(filename.getOriginalFilename());
12         filename.transferTo(new File("e:/"+filename.getOriginalFilename()));
13         Map<String,Object> map = new HashMap<String, Object>();
14         map.put("msg", "ok");
15         return map;
16     }
17 }
```

2. 编写启动类

```java
1 @SpringBootApplication
2 public class App {
3 
4     public static void main(String[] args) {
5         SpringApplication.run(App.class, args);
6     }
7 
8 }
```

3. 设置上传文件大小的默认值

![img](https://img2018.cnblogs.com/blog/1411451/201810/1411451-20181021220459211-1676708662.jpg)

\#设置上传文件的大小
spring.http.multipart.maxFileSize=200MB
\#设置上传文件的总大小
spring.http.multipart.maxRequestSize=200MB

## 3.整合数据库（事务问题）

## 一、集成 MySQL 数据库

### 1.1 配置 MySQL

添加依赖

```
<dependencies>
    <!--Spring 数据库相关依赖-->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-jpa</artifactId>
	</dependency>
	<!--mysql 驱动依赖-->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<scope>runtime</scope>
	</dependency>
</dependencies>
```

设置配置文件

```
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8
    username: root
    password: sa000
```

### 1.2 测试数据库连接

添加测试依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```

在 `test/java` 目录下新建测试类

```
@SpringBootTest
public class DataBaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnection() {
        try {
            Connection connection = dataSource.getConnection();
            Assert.assertNotNull(connection);
        } catch (SQLException e) {
            fail("数据库连接获取失败,错误原因:" + e);
        }
    }
}
```

执行测试，发现绿条说明测试通过。

### 1.3 设置连接池

**SpringBoot** 在我们引入 `spring-boot-starter-jdbc`或`spring-boot-starter-data-jpa` 依赖后就会默认启用连接池功能，它的连接池默认创建选择规则如下：

- 优先寻找创建 HikariCP 连接池

- 如果没有 HikariCP 连接池，会查找创建 Tomcat

- 如果没有 Tomcat 连接池，会查找创建 dbcp

- 如果没有 dbcp 连接池，会查找创建 dbcp2

- 也可以使用 spring.datasource.type 属性指定连接池类型

  ```
  spring.datasource.type= com.zaxxer.hikari.HikariDataSource
  ```

我们来通过一个测试方法验证下：

```
@SpringBootTest
public class DataBaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testDataSourceType(){
        Assert.assertEquals(HikariDataSource.class,dataSource.getClass());
    }
}    
```

执行测试，发现绿条说明测试通过。

常用连接池配置如下：

```
#验证连接的有效性
spring.datasource.primary.test-while-idle=true
#获取连接时候验证，会影响性能
spring.datasource.primary.test-on-borrow=false
#在连接归还到连接池时是否测试该连接
spring.datasource.primary.test-on-return=false
spring.datasource.primary.validation-query=SELECT 1 FROM DUAL
#空闲连接回收的时间间隔，与test-while-idle一起使用，设置5分钟
spring.datasource.primary.time-between-eviction-runs-millis=300000
#连接池空闲连接的有效时间 ，设置30分钟
spring.datasource.primary.min-evictable-idle-time-millis=1800000
spring.datasource.primary.initial-size=5
#指定连接池中最大的活跃连接数.
spring.datasource.primary.max-active=50
#指定连接池等待连接返回的最大等待时间，毫秒单位.
spring.datasource.primary.max-wait=60000
#指定必须保持连接的最小值
spring.datasource.primary.min-idle=5
```

更多配置请参考 [SprignBoot文档](https://docs.spring.io/spring-boot/docs/2.2.0.RELEASE/reference/html/appendix-application-properties.html#data-properties)。

### 1.4 JDBC 使用

配置好数据库之后，我们就来使用一下最基本的数据库操作。

在数据库 `test` 新建一个用户表：

```
CREATE TABLE `sys_user` (
  `user_id` int(21) NOT NULL COMMENT '用户编码',
  `user_name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

在 `test/java` 目录下新建测试类

```
@SpringBootTest
public class JdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    public void test() {
        delete();
        Assert.assertEquals(0,count());
        add();
        Assert.assertEquals(1,count());
    }


    private int count() {
        String sql = "select count(user_id) from sys_user ";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        return result;
    }

    private void add() {
        String sql = "insert into sys_user " +
                "values('1','marklogzhu','26','marklogzhu@163.com')";
        jdbcTemplate.execute(sql);
    }

    private void delete() {
        String sql = "delete from sys_user ";
        jdbcTemplate.execute(sql);
    }

}
```

执行测试，发现绿条说明测试通过。

## 二、集成 Redis 数据库

### 2.1 配置 Redis

添加依赖

```
<dependencies>
	......
	<!--Spring redis 依赖-->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-data-redis</artifactId>
	</dependency>
	<!--redis 连接池依赖-->
	<dependency>
		<groupId>org.apache.commons</groupId>
		<artifactId>commons-pool2</artifactId>
		<version>2.7.0</version>
	</dependency>
</dependencies>
```

设置配置文件

```
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    # 连接超时时间
    timeout: 1000ms
    jedis:
      pool:
        # 最大连接数
        max-active: 8
        # 最大阻塞等待时间(负数表示没限制)
        max-wait: -1ms
        # 最大空闲
        max-idle: 8
        # 最小空闲
        min-idle: 0
```

测试

```
@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
   
    @Test
    public void testSimpleValue(){
        String key = "name";
        String name ="MarkLogZhu";
        stringRedisTemplate.opsForValue().set(key,name);
        String result = stringRedisTemplate.opsForValue().get(key);
        Assert.assertEquals(name,result);
    }
    
}
```

执行测试，发现绿条说明测试通过。

### 2.2 实现序列化功能

添加依赖

```
<dependencies>
	......
	<!--JSON工具类依赖-->
	<dependency>
		<groupId>com.google.code.gson</groupId>
		<artifactId>gson</artifactId>
		<version>2.8.6</version>
	</dependency>
</dependencies>
```

设置序列化功能

```
@Configuration
public class RedisConfig {


    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(
                Object.class);
        final ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().build();
        objectMapper.disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        return jackson2JsonRedisSerializer;
    }


}
```

测试

```
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testObjectValue(){
        String key = "obj";
        SysUser user = new SysUser(1,"MarkLogZhu");
        redisTemplate.opsForValue().set(key,user);
        SysUser result = (SysUser)redisTemplate.opsForValue().get(key);
        Assert.assertEquals(user,result);
    }
    
}
```

执行测试，发现绿条说明测试通过。

## 4.整合权限

### （1）shiro

## 1.shiro是什么?

###### Shiro是Apache下的一个开源项目。shiro属于轻量级框架，相对于SpringSecurity简单的多，也没有SpringSecurity那么复杂。以下是我自己学习之后的记录。

###### 官方架构图如下：

![img](//upload-images.jianshu.io/upload_images/15087669-df91fa9f6c3e40d7.png?imageMogr2/auto-orient/strip|imageView2/2/w/414/format/webp)

官方架构图

## 2.主要功能

### shiro主要有三大功能模块：

###### 1. Subject：主体，一般指用户。

###### 2. SecurityManager：安全管理器，管理所有Subject，可以配合内部安全组件。(类似于SpringMVC中的DispatcherServlet)

###### 3. Realms：用于进行权限信息的验证，一般需要自己实现。

## 3.细分功能

###### 1. Authentication：身份认证/登录(账号密码验证)。

###### 2. Authorization：授权，即角色或者权限验证。

###### 3. Session Manager：会话管理，用户登录后的session相关管理。

###### 4. Cryptography：加密，密码加密等。

###### 5. Web Support：Web支持，集成Web环境。

###### 6. Caching：缓存，用户信息、角色、权限等缓存到如redis等缓存中。

###### 7. Concurrency：多线程并发验证，在一个线程中开启另一个线程，可以把权限自动传播过去。

###### 8. Testing：测试支持；

###### 9. Run As：允许一个用户假装为另一个用户（如果他们允许）的身份进行访问。

###### 10. Remember Me：记住我，登录后，下次再来的话不用登录了。

#### 上代码：

##### 目录结构：

![img](//upload-images.jianshu.io/upload_images/15087669-b5c72e9970e06528.png?imageMogr2/auto-orient/strip|imageView2/2/w/473/format/webp)

目录结构

#### pom.xml:

```
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.4.RELEASE</version>
        <relativePath/>
    </parent>
    <groupId>com.wsl</groupId>
    <artifactId>spring-shiro-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>spring-shiro-demo</name>
    <description>Demo project for Spring Boot</description>

    <properties>
        <java.version>1.8</java.version>
        <spring.shiro.version>1.6.0</spring.shiro.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!-- shiro -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>${spring.shiro.version}</version>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--页面模板依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!--热部署依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
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
```

#### user.java(用户实体类)：

```
package com.wsl.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    private String id;
    private String userName;
    private String password;
    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
```

#### Role.java(角色对应实体类)：

```
package com.wsl.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Role {

    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;
}
```

#### Permissions.java(权限对应实体类):

```
package com.wsl.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Permissions {
    private String id;
    private String permissionsName;
}
```

#### LoginServiceImpl.java:

```
package com.wsl.service.impl;

import com.wsl.bean.Permissions;
import com.wsl.bean.Role;
import com.wsl.bean.User;
import com.wsl.service.LoginService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class LoginServiceImpl implements LoginService {

    @Override
    public User getUserByName(String getMapByName) {
        return getMapByName(getMapByName);
    }

    /**
     * 模拟数据库查询
     *
     * @param userName 用户名
     * @return User
     */
    private User getMapByName(String userName) {
        Permissions permissions1 = new Permissions("1", "query");
        Permissions permissions2 = new Permissions("2", "add");
        Set<Permissions> permissionsSet = new HashSet<>();
        permissionsSet.add(permissions1);
        permissionsSet.add(permissions2);
        Role role = new Role("1", "admin", permissionsSet);
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        User user = new User("1", "wsl", "123456", roleSet);
        Map<String, User> map = new HashMap<>();
        map.put(user.getUserName(), user);
        
        Set<Permissions> permissionsSet1 = new HashSet<>();
        permissionsSet1.add(permissions1);
        Role role1 = new Role("2", "user", permissionsSet1);
        Set<Role> roleSet1 = new HashSet<>();
        roleSet1.add(role1);
        User user1 = new User("2", "zhangsan", "123456", roleSet1);
        map.put(user1.getUserName(), user1);
        return map.get(userName);
    }
}
```

#### 自定义Realm用于查询用户的角色和权限信息并保存到权限管理器：

#### CustomRealm.java

```
package com.wsl.shiro;

import com.wsl.bean.Permissions;
import com.wsl.bean.Role;
import com.wsl.bean.User;
import com.wsl.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * @MethodName doGetAuthorizationInfo
     * @Description 权限配置类
     * @Param [principalCollection]
     * @Return AuthorizationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //查询用户名称
        User user = loginService.getUserByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

    /**
     * @MethodName doGetAuthenticationInfo
     * @Description 认证配置类
     * @Param [authenticationToken]
     * @Return AuthenticationInfo
     * @Author WangShiLin
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if (StringUtils.isEmpty(authenticationToken.getPrincipal())) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        User user = loginService.getUserByName(name);
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
```

#### ShiroConfig.java，把CustomRealm和SecurityManager等注入到spring容器中：

```
package com.wsl.config;

import com.wsl.shiro.CustomRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class shiroConfig {
    
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    //将自己的验证方式加入容器
    @Bean
    public CustomRealm myShiroRealm() {
        CustomRealm customRealm = new CustomRealm();
        return customRealm;
    }

    //权限管理，配置主要是Realm的管理认证
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        //登出
        map.put("/logout", "logout");
        //对所有用户认证
        map.put("/**", "authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

  
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
```

#### LoginController.java:我们编写一个简单的登录方法，一个index页的查询方法，一个add方法，一个admin方法，对应不同的角色或权限拦截

```
package com.wsl.controller;

import com.wsl.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String login(User user) {
        if (StringUtils.isEmpty(user.getUserName()) || StringUtils.isEmpty(user.getPassword())) {
            return "请输入用户名和密码！";
        }
        //用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
        } catch (UnknownAccountException e) {
            log.error("用户名不存在！", e);
            return "用户名不存在！";
        } catch (AuthenticationException e) {
            log.error("账号或密码错误！", e);
            return "账号或密码错误！";
        } catch (AuthorizationException e) {
            log.error("没有权限！", e);
            return "没有权限";
        }
        return "login success";
    }

    @RequiresRoles("admin")
    @GetMapping("/admin")
    public String admin() {
        return "admin success!";
    }

    @RequiresPermissions("query")
    @GetMapping("/index")
    public String index() {
        return "index success!";
    }

    @RequiresPermissions("add")
    @GetMapping("/add")
    public String add() {
        return "add success!";
    }
}
```

##### 注解验证角色和权限的话无法捕捉异常，从而无法正确的返回给前端错误信息，所以我加了一个类用于拦截异常，具体代码如下

##### MyExceptionHandler.java

```
package com.wsl.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e) {
        log.error("没有通过权限验证！", e);
        return "没有通过权限验证！";
    }
}
```

打开网页 [http://localhost:8080/login?userName=wsl&password=123456](https://links.jianshu.com/go?to=http%3A%2F%2Flocalhost%3A8080%2Flogin%3FuserName%3Dwsl%26password%3D123456)

![img](//upload-images.jianshu.io/upload_images/15087669-bc8cae7048b8cf1a.png?imageMogr2/auto-orient/strip|imageView2/2/w/346/format/webp)

登录成功

然后输入index地址：[http://localhost:8080/index](https://links.jianshu.com/go?to=http%3A%2F%2Flocalhost%3A8080%2Findex)

![img](//upload-images.jianshu.io/upload_images/15087669-00c9c687c0818516.png?imageMogr2/auto-orient/strip|imageView2/2/w/379/format/webp)

index访问成功

换zhangsan账号登录后再访问index
[http://localhost:8080/login?userName=zhangsan&password=123456](https://links.jianshu.com/go?to=http%3A%2F%2Flocalhost%3A8080%2Flogin%3FuserName%3Dzhangsan%26password%3D123456)
[http://localhost:8080/index](https://links.jianshu.com/go?to=http%3A%2F%2Flocalhost%3A8080%2Findex)

![img](//upload-images.jianshu.io/upload_images/15087669-56ec6c1d8e65cd45.png?imageMogr2/auto-orient/strip|imageView2/2/w/605/format/webp)

权限控制访问失败

最近看好多人都项目报错，我把源码放到码云上，大家可以自行下载：

[源码地址：https://gitee.com/wsl6/shiro-demo.git](https://links.jianshu.com/go?to=https%3A%2F%2Fgitee.com%2Fwsl6%2Fshiro-demo.git)

#### 最后加一些常见的shiro异常：

##### 1. AuthenticationException 认证异常

Shiro在登录认证过程中，认证失败需要抛出的异常。 AuthenticationException包含以下子类：

###### 1.1. CredentitalsException 凭证异常

IncorrectCredentialsException             不正确的凭证
ExpiredCredentialsException               凭证过期

###### 1.2. AccountException 账号异常

ConcurrentAccessException:      并发访问异常（多个用户同时登录时抛出）
UnknownAccountException:        未知的账号
ExcessiveAttemptsException:     认证次数超过限制
DisabledAccountException:       禁用的账号
LockedAccountException:     账号被锁定
UnsupportedTokenException:      使用了不支持的Token

##### 2. AuthorizationException: 授权异常

Shiro在登录认证过程中，授权失败需要抛出的异常。 AuthorizationException包含以下子类：

###### 2.1.  UnauthorizedException:

抛出以指示请求的操作或对请求的资源的访问是不允许的。

###### 2.2. UnanthenticatedException:

当尚未完成成功认证时，尝试执行授权操作时引发异常。

### （2）Spring Security



## 5.整合中间件