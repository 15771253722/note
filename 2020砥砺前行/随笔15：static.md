# static

## 前言

说到static，静态变量和静态方法大家随口就来，因为他们在实际开发中应用很广泛，但他们真正在使用的时候会存在很多问题，而且它的使用不只那两种：

- 静态变量
- 静态方法
- 静态代码块
- 静态内部类
- 静态导入

接下来我们看一下这些用法。

## 静态变量

静态变量属于类，内存中只有一个实例，当类被加载，就会为该静态变量分配内存空间。

跟 class 本身在一起存放在方法区中永远不会被回收，除非 JVM 退出。（方法区还存哪些东西可以看看：Java虚拟机运行时数据区域）

`静态变量的使用方式`：【类名.变量名】和【对象.变量名】。

**【实例】** 实际开发中的日期格式化类SimpleDateFormat会经常用到，需要的时候会new一个对象出来直接使用。

但我们知道频繁的创建对象不好，所以在DateUtil中直接创建一个静态的SimpleDateFormat全局变量，直接使用这个实例进行操作，因为内存共享，所以节省了性能。

但是它在高并发情况下是存在线程安全问题的。SimpleDateFormat线程安全问题代码复现：

```java
public class OuterStatic {
 
  public static class InnerStaticSimpleDateFormat  implements Runnable {
        @Override
        public void run() {
          while(true) {
            try {
              Thread.sleep(3000);
                System.out.println(Thread.currentThread().getName() +":"+DateUtil.parse("2017-07-27 08:02:20"));
            } catch (Exception e) {
                e.printStackTrace();
            }
          }
        }    
    }
    public static void main(String[] args) {
      for(int i = 0; i < 3; i++){
         new Thread(new InnerStaticSimpleDateFormat(), "测试线程").start();

      }
    }
}
class DateUtil {
    
    private static  volatile SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public static  String formatFromDate(Date date)throws ParseException{
        return sdf.format(date);
    }
    public static Date parseToDate(String strDate) throws ParseException{
        return sdf.parse(strDate);
    }
}
```

虽然有volatile使对象可见，但运行后有一定几率会报`java.lang.NumberFormatException: multiple points`或`For input string: ""`等错误，原因是`多线程`都去操作一个对象

![img](https://mmbiz.qpic.cn/mmbiz_png/DB0IfcV7dwicjkZ20c2vTicUdmY6DhTzy6GcBiaYzl1ibiatFia1y9AcFuR5ibRJAaTNqHhAL0Ip6V5VB6E15kGibjo0ibw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

`解决办法有如下几种`：

- 使用私有的对象
- 加锁
- ThreadLocal。
- 使用第三方的日期处理函数。
- Java8推出了线程安全、简易、高可靠的时间包，里面有LocalDateTime年月日十分秒；LocalDate日期；LocalTime时间三个类可供使用。

下图是使用私有对象和ThreadLocal解决高并发状态的图解。

![img](https://mmbiz.qpic.cn/mmbiz_jpg/DB0IfcV7dwicjkZ20c2vTicUdmY6DhTzy6qgP87SsykicYvzLBOMtr6xqvXnqaVVnv4vCoibAemibMdaJ84PgyCIk7A/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

本文给出使用私有的对象和加锁两种实现代码，ThreadLocal方式读者可以尝试自己实现

```java
public class DateUtil {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
    public static String formatFromDate(Date date)throws ParseException{
      //方式一:让内存不共享，到用的时候再创建私有对象,使用时注释掉全局变量sdf
      //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      //return sdf.format(date);
      //方式二:加锁,使用时打开全局变量sdf的注释
        synchronized(sdf){
            return sdf.format(date);
        }  
    }
   public static Date parseToDate(String strDate) throws ParseException{
       //方式一:使用时注释掉全局变量sdf
       //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       //return sdf.parse(strDate);
       //方式二:加锁,使用时打开全局变量sdf的注释
        synchronized(sdf){
            return sdf.parse(strDate);
        }
    } 
}
```

## 静态方法

静态方法和非静态方法一样，都跟class 本身在一起存放在内存中，永远不会被回收，除非 JVM 退出，他们使用的区别的一个方面是非static方法需要实例调用，static方法直接用类名调用。

**【实例一】** 单例模式，它提供了一种创建对象的最佳方式，保证一个类仅有一个实例,并提供一个访问它的全局访问点。

```java
public class Singleton {
   private static volatile Singleton instance = null;  
    static {  //静态代码块，后面讲
       instance = new Singleton();  
    }  
    private Singleton (){}  
    public static Singleton getInstance() {  
        return instance;  
    }      
}
```

静态的方法不必实例化就能直接使用，用法方便，不用频繁的为对象开辟空间和对象被回收，节省系统资源。是不是相较之下觉得static用的比较爽呢？但是他也会带来一些问题：

**【实例二】** 一般工具类中的方法都写成static的，比如我们要实现一个订单导出功能，代码如下：

```java
public class ExportExcelUtil{
    @Autowired
    private static OrderService orderService ;

    public static void exportExcel(String id){
       //查询要导出的订单的数据
       Order order =orderService.getById(id);//这里的orderService对象会是null
      //...省略导出代码...
    }
} 
```

为什么orderService会是null？原因不是Spring没注入，而是static方法给它"清空"了。

`解决方案一`：@PostConstruct，它修饰的方法会在服务器加载Servlet时执行一次，代码如下：

```java
@Component //这个注解必须加
public class ExportExcelUtil{
    @Autowired
    OrderService orderService ;

    private static ExportExcelUtil  exportExcelUtil; 

    //注解@PostConstruct 这个其实就是类似声明了，当你加载一个类的构造函数之后执行的代码块，
    //也就是在加载了构造函数之后，就将service复制给一个静态的service。
     @PostConstruct  
     public void init() {       
        exportExcelUtil= this; 
        exportExcelUtil.orderService = this.orderService ; 
     }  

    public static void exportExcel(String id){
       //是不是很像经典main方法的调用模式呢？
       Order order =exportExcelUtil.orderService .getById(id);
       //...省略导出代码...
    }
}
```

每个工具类都要去加上@PostConstruct注解，代码重复性高。那我们可不可以直接从Spring容器中获取Bean实例？

`解决方案二`：ApplicationContextAware。通过它Spring容器会自动把上下文环境对象注入到ApplicationContextAware接口的实现类中setApplicationContext方法里。

换句话说，我们在ApplicationContextAware的实现类中，就可以通过这个上下文环境对象得到Spring容器中的Bean。

首先，在web项目中的web.xml中配置加载Spring容器的Listener：

```java
<!-- 初始化Spring容器，让Spring容器随Web应用的启动而自动启动 -->
<listener>  
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>  
</listener>
```

然后，实现ApplicationContextAware接口：

```java
public class SpringContextBean implements ApplicationContextAware{
 private static ApplicationContext context = null;

 public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
 {
  context = applicationContext;
 }

 public static <T> T getBean(String name)
 {
  return (T)context.getBean(name);
 }

 public static <T> T getBean(Class<T> beanClass){
  return context.getBean(beanClass);
 }
}
```

最后，在Spring配置文件中注册该工具类：

```java
<bean id="springContextBean" class="com.test.SpringContextBean"></bean>
```

原来的导出工具类代码可以简化到如下：

```java
public class ExportExcelUtil{
    public static void exportExcel(String id){
      OrderService orderService = SpringContextBean.getBean(OrderService.class);
      Order order =orderService .getById(id);
       //...省略导出代码...
    }
}
```

## 静态代码块

我们其实在工作中一直用到的代码块，所谓代码块是指使用“{}”括起来的一段代码。

其中静态代码块只执行一次，构造代码块在每次创建对象是都会执行。

根据位置不同，代码块可以分为四种：`普通代码块`、`构造块`、`静态代码块`、`同步代码块`。

**【实例】** 因为JVM只为静态分配一次内存，在加载类的过程中完成静态变量的内存分配。

所以实际工作中我们可以使用静态代码块初始化一些不变的属性：

```java
//final表示此map集合是不可变得
public  static  final  Map<String,String> spuKeysMap = new HashMap<String,String>();
static{
   spuKeysMap.put("spuName","男装");
   spuKeysMap.put("spuCode","男装编码");
   spuKeysMap.put("spuBrand","品牌");
   spuKeysMap.put("owner","所有者");
}
```

但是静态代码块和静态变量初始化有什么关系？在上文的单例模式中，我们使用了静态代码块来创建对象，为何那那样写？我在网上看到了这样一段代码：

```java
static {  
    _i = 10;  
}  
public static int _i = 20;  

public static void main(String[] args) {  
    System.out.println(_i);  
} 
```

上面的结果是10还是20？如果存在多个代码块呢？

```java
static {  
     _i = 10;  
}  
public static int _i =30;
static {  
    _i = 20;  
}   
public static void main(String[] args) {  
    ystem.out.println(_i);
}
```

测试过后你会发现两个答案结果都是20。

因为其实`public static int _i = 10;` 和如下代码是没有区别的，他们在编译后的字节码完全一致（读者可以使用javap -c命令查看字节码文件），所以两个例子的结果就是最后一次赋值的数值。

```java
public static int _i;  
static {  
    _i = 10;  
}
```

## 静态内部类

在定义内部类的时候，可以在其前面加上一个权限修饰符static，此时这个内部类就变为了静态内部类。

**【实例一】** 前文中写静态方法时的实例一，我们用了static块初始化单例对象，这样做有一个弊端，在调用单例其他方法时也会初始化对象，现在我们只希望在调用getInstance方法时初始化单例对象，要怎么改进呢？因为饿汗式写法性能不太好，所以最终单例模式优化到如下：

```java
public class Singleton {
    //使用静态内部类初始化对象
    private static class  SingletonHolder{
      private static volatile Singleton instance = new Singleton();   
    }
    private Singleton (){}  
    public static Singleton getInstance() {  
        return SingletonHolder.instance;  
    }    
    public static  void otherMothed(){
      System.out.println("调用单例的其他方法时不会创建对象.")
    }  
    public static  void  main(String [] args){
        //Singleton.otherMothed();
         Singleton.getInstance();
    }
}
```

**【实例二】** 博主在内部类的实际开发中应用不多，但有时候还真不能没有它，比如LinkedList使用了如下静态内部类：

![img](https://mmbiz.qpic.cn/mmbiz_png/DB0IfcV7dwicjkZ20c2vTicUdmY6DhTzy6jLxVUwgoWicBgqcibuUBy3ibfKMcCvibkia1NXFWm4RZPcWfTpBjZVS2JpA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

其实在数据结构中我们把next和prev称为前后节点的指针，HashMap内部也使用了静态内部类Entry的数组存放数据。

为了加深理解，读者可以亲自运行以下的代码来体会一下静态内部类。

```java
private static String name = "北京";  //静态变量
  public static void main(String[] args) { 
    new StaticInternal().outMethod();
  }   
  public static void outStaticMethod(String tempName) {       
      System.out.println("外部类的静态方法 name:"+tempName);  
  } 
  public void outMethod() {             // 外部类访问静态内部类的静态成员：内部类.静态成员  
    System.out.println("外部类的非静态方法调用");
      StaticInternal.InnerStaticClass inner = new   StaticInternal.InnerStaticClass();// 实例化静态内部类对象  
    inner.setInnerName("呼呼");// 访问静态内部类的非静态方法  
    InnerStaticClass.innerStaticMethod(); // 访问静态内部类的静态方法  
    System.out.println("外部类访问静态内部类的非静态方法 name:"+inner.getInnerName());
}

static class InnerStaticClass {            
  String  InnerName="西安";  
    static void innerStaticMethod() {    // 静态内部类的静态方法  
        System.out.println("静态内部类访问外部类的静态变量: name = " + name);  
        outStaticMethod(new InnerStaticClass().InnerName);     // 访问外部类的静态方法  
    }  
    // 静态内部类的非静态方法  
    public void setInnerName(String name) {  
        System.out.println("静态内部类的非静态方法");  
        this.InnerName = name;  
    }  
    public String getInnerName() {  
      System.out.println("静态内部类的非静态get方法 name="+name); 
        return this.InnerName;  
    } 
}
```

在这里我们来总结一下静态内部类：

- 加强代码可读性。如：

  ```java
  StaticInternal.InnerStaticClass inner 
  = new StaticInternal.InnerStaticClass();
  ```

- 多个外部类的对象可以共享同一个静态内部类的对象。

- 静态内部类无需依赖于外部类，它可以独立于外部对象而存在。因为静态类和方法只属于类本身，并不属于该类的对象，更不属于其他外部类的对象。

## 静态导入

静态导入是JKD1.5后新加的功能，一般不怎么常用，了解即可。有时候面试答出来这个会让别的觉得你热爱技术。

**【实例】** 回想一下，我们以前是不是这样写获取随机数：

```java
public static void main(String[] args) {
  double random = Math.random();
  System.out.println(Math.PI);
  System.out.println(Math.round(random));
}
```

Math出现的次数太多了，可以简化吗?现在我们可以直接使用静态导入来写，如下

```java
import static java.lang.Math.*;

public class StaticInternal {
 
  public static void main(String[] args) {
      double random = random();
      System.out.println(PI);
      System.out.println(round(random));
  } 
}
```

是不是方便了许多？但别着急偷懒，因为使用它过多会导致代码可读性差：

```java
import static java.lang.Math.*;
import static java.lang.Integer.*;
public class StaticInternal {
 
  public static void main(String[] args) {
 double random = random();
 System.out.println(PI);
 System.out.println(round(random));
 System.out.println(bitCount(11));
  } 
}
```

或许你知道PI是Math类的方法，那bitCount是哪个类的方法呢？所以尽量避免使用static导入。

实在要导入的话，去掉*号通配符，直接写成：java.lang.Integer.bitCount。