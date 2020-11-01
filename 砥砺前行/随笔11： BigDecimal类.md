# Java BigDecimal类常用方法

## 一.简介

Java在java.math包中提供的API类BigDecimal，用来对超过16位有效位的数进行精确的运算。双精度浮点型变量double可以处理16位有效数。在实际应用中，需要对更大或者更小的数进行运算和处理。float和double只能用来做科学计算或者是工程计算，在商业计算中要用java.math.BigDecimal。BigDecimal所创建的是对象，我们不能使用传统的+、-、*、/等算术运算符直接对其对象进行数学运算，而必须调用其相对应的方法。方法中的参数也必须是BigDecimal的对象。构造器是类的特殊方法，专门用来创建对象，特别是带有参数的对象。

## 二.构造器

```java
BigDecimal(int)       创建一个具有参数所指定整数值的对象。
BigDecimal(double) 创建一个具有参数所指定双精度值的对象。
BigDecimal(long)    创建一个具有参数所指定长整数值的对象。
BigDecimal(String) 创建一个具有参数所指定以字符串表示的数值的对象。

toString()                将BigDecimal对象的数值转换成字符串。
doubleValue()          将BigDecimal对象中的值以双精度数返回。
floatValue()             将BigDecimal对象中的值以单精度数返回。
longValue()             将BigDecimal对象中的值以长整数返回。
intValue()               将BigDecimal对象中的值以整数返回
```

## 三.常用方法

```
BigDecimal b1 = new BigDecimal("20");
BigDecimal b2 = new BigDecimal("30");


b1.add(b2) ：加法，求两个BigDecimal类型数据的和。
b1.subtract(b2)：减法，求两个BigDecimal类型数据的差。
b1.multiply(b2)：乘法，求两个BigDecimal类型数据的积。
b1.remainder(b2)：求余数，求b1除以b2的余数。
b1.max(b2) : 最大数，求两个BigDecimal类型数据的最大值
b1.min(b2) : 最小数，求两个BigDecimal类型数据的最小值。
bi.abs()：绝对值，求BigDecimal类型数据的绝对值。
b1.negate()：相反数，求BigDecimal类型数据的相反数。
```

除法：

```
BigDecimal divide(BigDecimal divisor, int scale, int roundingMode)
```

除法 divide有三个参数的方法，第一参数表示除数，第二个参数表示小数点后保留位数，第三个参数表示取舍规则。只有在作除法运算或四舍五入时才用到取舍规则。 因为BigDecimal除法可能出现不能整除的情况，比如 4.5/1.3，这时会报错java.lang.ArithmeticException: Non-terminating decimal expansion; no exact representable decimal result。所以当我们用三参数的除法方法时，规定了保留几位小数以及你的保留方式，就可以避免异常。

几个取舍规则：

```
ROUND_CEILING //向正无穷方向舍入
ROUND_DOWN //向零方向舍入
ROUND_FLOOR //向负无穷方向舍入
ROUND_HALF_DOWN  //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向下舍入, 例如1.55 保留一位小数结果为1.5
ROUND_HALF_EVEN  //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，如果保留位数是奇数，使用ROUND_HALF_UP，如果是偶数，使用ROUND_HALF_DOWN
ROUND_HALF_UP  //向（距离）最近的一边舍入，除非两边（的距离）是相等,如果是这样，向上舍入, 1.55保留一位小数结果为1.6
ROUND_UNNECESSARY //计算结果是精确的，不需要舍入模式
ROUND_UP //向远离0的方向舍入
```

BigDecimal中有一个setScale()方法（处理小数位数保留）。

