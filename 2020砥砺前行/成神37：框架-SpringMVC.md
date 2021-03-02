# 二.框架：SpringMVC框架

## 1.MVC设计概述

在早期java web的开发中，统一把显示层、控制层、数据层的操作全部交给jsp或者JavaBean来进行处理，我们称之为model1.

![1603097315995](C:\Users\14579\Pictures\笔记图\MVCModel.png)

**产生的弊端：**

- JSP和JavaBean耦合严重，java代码和html代码也耦合在一起
- 前端和后端互相依赖，开发者不仅要掌握java还要有超高的前端水平
- 代码难以复用

**早期MVC模型（Model2）：Servlet+JSP+Java Bean**

![1603097676141](C:\Users\14579\Pictures\笔记图\MVCModel2.png)

首先用户的请求会到达 Servlet，然后根据请求调用相应的 Java Bean，并把所有的显示结果交给 JSP 去完成，这样的模式我们就称为 MVC 模式。

- **M 代表 模型（Model）**
  模型是什么呢？ 模型就是数据，就是 dao,bean
- **V 代表 视图（View）**
  视图是什么呢？ 就是网页, JSP，用来展示模型中的数据
- **C 代表 控制器（controller)**
  控制器是什么？ 控制器的作用就是把不同的数据(Model)，显示在不同的视图(View)上，Servlet 扮演的就是这样的角色。

**为解决持久层中一直未处理好的数据库事务的编程，又为了迎合 NoSQL 的强势崛起，Spring MVC 给出了方案：**

![1603097890669](C:\Users\14579\Pictures\笔记图\MVC.png)

**传统的模型层被拆分为了业务层(Service)和数据访问层（DAO,Data Access Object）。** 在 Service 下可以通过 Spring 的声明式事务操作数据访问层，而在业务层上还允许我们访问 NoSQL ，这样就能够满足异军突起的 NoSQL 的使用了，它可以大大提高互联网系统的性能。

- **特点：**
  结构松散，几乎可以在 Spring MVC 中使用各类视图
  松耦合，各个模块分离
  与 Spring 无缝集成

## 2.跟踪Spring MVC请求

![1603098327485](C:\Users\14579\Pictures\笔记图\MVC请求流程.png)

1. DispatcherServlet

   从请求离开浏览器以后，第一站到达的就是DispatcherServlet,看名字这是一个Servlet,Servlet可以拦截并处理HTTP请求，DispatcherServlet会拦截所有的请求，并将这些请求发送给Spring MVC控制器。

   ```java
   <servlet>
   	<servlet-name>dispatcher</servlet-name>
   	<servlet-class>org.springframework.web.servlet.DispatcerServlet</servlet-class>
   	<load-on-startup>1<load-on-startup>
   </servlet>
   <servlet-mapping>
   	<servlet-name></servlet-name>
   	<!--拦截所有的请求-->
        <url-pattern>/</url-pattern>   
   </servlet-mapping>
   ```

   **DispatcherServlet的任务就是拦截请求发送给Spring MVC控制器。**

2. 处理器映射（HandlerMapping）

   问题：典型的应用程序中可能会有多个控制器，这些请求到底应该发给哪一个控制器呢？

   所以DispatcherServlet会查询一个或多个处理器映射来确定请求的下一站在哪里，处理器映射会**根据请求所携带的URL信息来进行决策**，例如，我们通过配置simpleUrlHandlerMapping来将/hello地址交给helloController处理：

   ```java
   <bean id="simplerUrlHandlerMapping" class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
   	<property name ="mapping">
   		<props>
   			<!--/hello 路径的请求交给 id 为 helloController 的控制器处理-->		<prop key ="/hello">helloController</prop>
   		</property>
   	</property>
   </bean>
   <bean id="helloController" class="controller.HelloController"></bean>
   ```

3. 控制器

   一旦选择了合适的控制器，DispatcherServlet会将请求发送给选中的控制器，到了控制器，请求会卸下其负载（用户提交的请求）等待控制器处理完这些信息：

   ```java
   public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
       // 处理逻辑
       ....
   }
   ```

4. 返回DispatcherServlet

   当控制器在完成逻辑处理后，通常会产生一些信息，这些信息就是需要返回给用户并在浏览器上显示的信息，它们被称为**模型（Model）**。仅仅返回原始的信息时不够的——这些信息需要以用户友好的方式进行格式化，一般会是 HTML，所以，信息需要发送给一个**视图（view）**，通常会是 JSP。

   控制器所做的最后一件事就是将模型数据打包，并且表示出用于渲染输出的视图名**（逻辑视图名）。它接下来会将请求连同模型和视图名发送回 DispatcherServlet。**

   ```java
   public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
       // 处理逻辑
       ....
       // 返回给 DispatcherServlet
       return mav;
   }
   ```

5. 视图解析器

   这样以来，控制器就不会和特定的视图相耦合，传递给 DispatcherServlet 的视图名并不直接表示某个特定的 JSP。（实际上，它甚至不能确定视图就是 JSP）相反，**它传递的仅仅是一个逻辑名称，这个名称将会用来查找产生结果的真正视图。**

   DispatcherServlet 将会使用视图解析器（view resolver）来将逻辑视图名匹配为一个特定的视图实现，它可能是也可能不是 JSP

6. 视图

   既然 DispatcherServlet 已经知道由哪个视图渲染结果了，那请求的任务基本上也就完成了。

   它的最后一站是视图的实现，在这里它交付模型数据，请求的任务也就完成了。视图使用模型数据渲染出结果，这个输出结果会通过响应对象传递给客户端。

   ```java
   <%@ page language="java" contentType="text/html; charset=UTF-8"
            pageEncoding="UTF-8" isELIgnored="false"%>

   <h1>${message}</h1>
   ```



## 3.使用注解配置Spring MVC

1. 为Hello Controller 添加注解

   ```java
   package controller;

   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.servlet.ModelAndView;

   @Controller
   public class HelloController{

       @RequestMapping("/hello")
       public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {
           ModelAndView mav = new ModelAndView("index.jsp");
           mav.addObject("message", "Hello Spring MVC");
           return mav;
       }
   }
   ```

   **简单解释一下：**

   `@Controller` 注解：
   很明显，这个注解是用来声明控制器的，但实际上这个注解对 Spring MVC 本身的影响并不大。（Spring 实战说它仅仅是辅助实现组件扫描，可以用 `@Component` 注解代替，但我自己尝试了一下并不行，因为上述例子没有配置 JSP 视图解析器我还自己配了一个仍没有成功...）

   `@RequestMapping` 注解：
   很显然，这就表示路径 `/hello` 会映射到该方法上

2. 取消之前的xml注释

   在 dispatcher-servlet.xml 文件中，注释掉之前的配置，然后增加一句组件扫描：

   ```java
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

       <!--<bean id="simpleUrlHandlerMapping"-->
                                           <!--class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">-->
       <!--<property name="mappings">-->
               <!--<props>-->
                   <!--&lt;!&ndash; /hello 路径的请求交给 id 为 helloController 的控制器处理&ndash;&gt;-->
                   <!--<prop key="/hello">helloController</prop>-->
               <!--</props>-->
           <!--</property>-->
       <!--</bean>-->
       <!--<bean id="helloController" class="controller.HelloController"></bean>-->

       <!-- 扫描controller下的组件 -->
       <context:component-scan base-package="controller"/>
   </beans>
   ```

3. 重启服务器

   当配置完成，重新启动服务器，输入 `localhost/hello` 地址仍然能看到效果

   注意：

   如果 `@RequestMapping` 作用在类上，那么就相当于是给该类所有配置的映射地址前加上了一个地址，例如：

   ```java
   @Controller
   @RequestMapping("/wmyskxz")
   public class HelloController {
       @RequestMapping("/hello")
       public ModelAndView handleRequest(....) throws Exception {
           ....
       }
   }
   ```

   - 则访问地址： `localhost/wmyskxz/hello`

## 4.配置视图解析器

1. 还记得我们 Spring MVC 的请求流程吗，视图解析器负责定位视图，它接受一个由 DispaterServlet 传递过来的逻辑视图名来匹配一个特定的视图。

   - **需求：** 有一些页面我们不希望用户用户直接访问到，例如有重要数据的页面，例如有模型数据支撑的页面。
   - **造成的问题：**
     我们可以在【web】根目录下放置一个【test.jsp】模拟一个重要数据的页面，我们什么都不用做，重新启动服务器，网页中输入 `localhost/test.jsp` 就能够直接访问到了，这会造成**数据泄露**...
     另外我们可以直接输入 `localhost/index.jsp` 试试，根据我们上面的程序，这会是一个空白的页面，因为并没有获取到 `${message}` 参数就直接访问了，这会**影响用户体验**

2. 解决方案

   我们将我们的 JSP 文件配置在【WEB-INF】文件夹中的【page】文件夹下，【WEB-INF】是 Java Web 中默认的安全目录，是不允许用户直接访问的*（也就是你说你通过 localhost/WEB-INF/ 这样的方式是永远访问不到的）*

   但是我们需要将这告诉给视图解析器，我们在 dispatcher-servlet.xml 文件中做如下配置：

   ```java
   <bean id="viewResolver"
         class="org.springframework.web.servlet.view.InternalResourceViewResolver">
       <property name="prefix" value="/WEB-INF/page/" />
       <property name="suffix" value=".jsp" />
   </bean>
   ```

   这里配置了一个 Spring MVC 内置的一个视图解析器，该解析器是遵循着一种约定：会**在视图名上添加前缀和后缀，进而确定一个 Web 应用中视图资源的物理路径的。**让我们实际来看看效果：

   - #### 修改 HelloController

     ![img](C:\Users\14579\Pictures\笔记图\MVC1)

   - #### 配置视图解析器：

     ```java
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:context="http://www.springframework.org/schema/context"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

         <!--<bean id="simpleUrlHandlerMapping"-->
                                             <!--class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">-->
         <!--<property name="mappings">-->
                 <!--<props>-->
                     <!--&lt;!&ndash; /hello 路径的请求交给 id 为 helloController 的控制器处理&ndash;&gt;-->
                     <!--<prop key="/hello">helloController</prop>-->
                 <!--</props>-->
             <!--</property>-->
         <!--</bean>-->
         <!--<bean id="helloController" class="controller.HelloController"></bean>-->

         <!-- 扫描controller下的组件 -->
         <context:component-scan base-package="controller"/>
         <bean id="viewResolver"
               class="org.springframework.web.servlet.view.InternalResourceViewResolver">
             <property name="prefix" value="/WEB-INF/page/" />
             <property name="suffix" value=".jsp" />
         </bean>
     </beans>
     ```

   - #### 剪贴 index.jsp 文件

     在【WEB-INF】文件夹下新建一个【page】文件夹，并将【index.jsp】文件剪贴到里面：

     ![img](C:\Users\14579\Pictures\笔记图\mvc2)

   https://www.jianshu.com/p/91a2d0a1e45a