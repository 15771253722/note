1. **POJO**(plain ordinary java object) ：简单无规则java对象

   **POJO是一种概念或者接口，身份及作用随环境变化而变化**

   纯的传统意义的java对象.就是说在一些Object/Relation Mapping工具中,能够做到维护数据库表记录的persisent object完全是一个符合Java Bean规范的纯Java对象,没有增加别的属性和方法.我的理解就是最基本的Java Bean,只有属性字段及setter和getter方法!

   POJO有一些Private的参数作为对象的属性。然后针对每个参数定义了get和set方法作为访问的接口

   Plain Ordinary Java Object简单Java对象

   即POJO是一个简单的普通的Java对象，它不包含业务逻辑或持久逻辑等，但不是JavaBean、EntityBean等，不具有任何特殊角色和不继承或不实现任何其它Java框架的类或接口。

   POJO对象有时也被称为Data对象，大量应用于表现现实中的对象。 

   一个POJO持久化以后就是PO。

   直接用它传递、传递过程中就是DTO

   直接用来对应表示层就是VO 

2. **BO**(**service、manager、business等命名**) 业务对象：

   从业务模型的角度看,见UML元件领域模型中的领域对象.封装业务逻辑的java对象,通过调用DAO方法,结合PO,VO进行业务操作.

   Business Object业务对象

   主要作用是把业务逻辑封装为一个对象。这个对象可以包括一个或多个其它的对象。

   形象描述为一个对象的形为和动作，当然也有涉及到基它对象的一些形为和动作。比如处理

   一个人的业务逻辑，有睡觉，吃饭，工作，上班等等形为还有可能和别人发关系的形为。

   这样处理业务逻辑时，我们就可以针对BO去处理。

3. **DTO** (Data Transfer Object)数据传输对象：

   **经过处理后的PO，可能增加或者减少PO的属性**

   主要用于远程调用等需要大量传输对象的地方。

   主要用于远程调用等需要大量传输对象的地方。

   比如我们一张表有100个字段，那么对应的PO就有100个属性。

   但是我们界面上只要显示10个字段，

   客户端用WEB service来获取数据，没有必要把整个PO对象传递到客户端，

   这时我们就可以用只有这10个属性的DTO来传递结果到客户端，这样也不会暴露服务端表结构.到达客户端以后，如果用这个对象来对应界面显示，那此时它的身份就转为VO

4. **PO**(persistant object) 持久对象：

   在o/r 映射的时候出现的概念,如果没有o/r映射,就没有这个概念存在了.通常对应数据模型(数据库),本身还有部分业务逻辑的处理.可以看成是与数据库中的表相映射的java对象.最简单的PO就是对应数据库中某个表中的一条记录,多个记录可以用PO的集合.PO中应该不包含任何对数据库的操作.

   Persistant Object持久对象，数据库表中的记录在java对象中的显示状态

   最形象的理解就是一个PO就是数据库中的一条记录。

   好处是可以把一条记录作为一个对象处理，可以方便的转为其它对象。

5. **​JavaBean** 是一种Java语言写成的可重用组件。为写成JavaBean，类必须是具体的和公共的，并且具有无参数的构造器。JavaBean 通过提供符合一致性设计模式的公共方法将内部域暴露成员属性。众所周知，属性名称符合这种模式，其他Java 类可以通过自身机制发现和操作这些JavaBean 的属性。

6. **​VO**（value object）值对象：
   主要体现在视图的对象，对于一个WEB页面将整个页面的属性封装成一个对象。然后用一个VO对象在控制层与视图层进行传输交换。

7. **DAO**（**Data Access Object数据访问对象**）：

   主要用来封装对数据库的访问。通过它可以把POJO持久化为PO，用PO组装出来VO、DTO

8. **Controller控制层主要是Action/Servlet等构成（目前Spring MVC则是通过@Controller标签使用）**：

   此层业务层与视图层打交道的中间层，负责传输VO对象和调用BO层的业务方法，负责视图层请求的数据处理后响应给视图层。

9. **View（视图层）**

   主要是指由JSP、HTML等文件形成的显示层。

   总结一下要用具体的X0需要看具体环境及项目架构，在不同的层、不同的应用场合，对象的身份也不一样，而且对象身份的转化也是很自然的。就像你对老婆来说就是老公，对父母来说就是子女。设计这些概念的初衷不是为了唬人而是为了更好的理解和处理各种逻辑，让大家能更好的去用面向对象的方式处理问题。

   在平时开发项目中大家千万过度设计各层，因为这样会带来大量的工作和重复工作。如果不是大型系统可简化一些层，因为技术是为应用服务的。

   ![](C:\Users\14579\Pictures\笔记图\JavaBean举例.png)

10. ​

   ​

   ​







注：

1、entity里的每一个字段，与数据库相对应，

2、dto里的每一个字段，是和你前台页面相对应，

3、VO，这是用来转换从entity到dto，或者从dto到entity的中间的东西。

举个例子：

你的html页面上有三个字段，name，pass，age

你的数据库表里，有两个字段，name，pass(注意没有age哦)而你的dto里，就应该有下面三个(因为对应html页面上三个字段嘛)

private string name；

private string pass; 

private string age;

这个时候，你的entity里，就应该有两个(因为对应数据库表中的2个字段嘛)

private string name；

private string pass;

到了这里，好了，业务经理让你做这样一个业务“年龄大于20的才能存入数据库”

这个时候，你就要用到vo了

你要先从页面上拿到dto，然后判断dto中的age是不是大于20，如果大于20，就把dto中的

name和pass拿出来，放到vo中，然后在把vo中的name和pass原封不懂的给entity，然后根据

entity的值，在传入数据库，这就是他们三个的区别。