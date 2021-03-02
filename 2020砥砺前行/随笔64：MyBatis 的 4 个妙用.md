# MyBatis小技巧

**1、在Mybatis mapping.xml映射配置文件中使用大于>号小于号<**

由于**Mybatis**的映射文件遵循xml文件的格式，所以不能使用像大于号或者小于号这样的xml文件特殊字符，需要使用转义字符代替。

| <    | <    | 小于号 |
| :--- | :--- | :----- |
| >    | >    | 大于号 |
| &    | &    | 和     |
| '    | ’    | 单引号 |
| "    | “    | 双引号 |

可以使用：

```
SELECT * FROM test 
WHERE 1 = 1 AND start_date  &lt;= CURRENT_DATE AND end_date &gt;= CURRENT_DATE
<![CDATA[ when min(starttime)<='12:00' and max(endtime)<='12:00' ]]>
```

**2、Mybatis中使用OGNL表达式test比较字符串**

在**Mybatis**映射配置文件中，使用OGNL表达式test的时候，比较字符串时，需要调用 toString()方法保证 == 两边的值都是 String 类型。

```
<!-- 以下为错误写法,会抛NumberFormatException异常 -->
<if test="username == 'U'">

<!-- 正确写法如下两种 -->
<if test="username == 'U'.toString()">
<if test='username == "U"'>
```

**3、Mybatis实现WHERE IN查询**

WHERE IN查询中，IN的参数是一个列表，需要传送一个列表参数，使用 foreach 实现。

```
<select id="selectPostIn" resultType="domain.blog.Post">
 SELECT * FROM POST P
 WHERE ID in
 <foreach item="item" index="index" collection="list"  open="(" separator="," close=")">
    #{item}
 </foreach>
</select>
```

当使用可迭代对象或者数组时，index是当前迭代的次数，item的值是本次迭代获取的元素。当使用字典（或者Map.Entry对象的集合）时，index是键，item是值。

[你可以将任何可迭代对象（如列表、集合等）和任何的字典或者数组对象传递给foreach作为集合参数。](http://mp.weixin.qq.com/s?__biz=MzI3ODcxMzQzMw==&mid=2247507913&idx=2&sn=cc9bce2bdf5a3d106c6f7a3040f0ae14&chksm=eb505affdc27d3e9da88636d4cbe4906992f141218dd3d5f894d3a600fc65cd02dda66c3e33a&scene=21#wechat_redirect)

**4、Mybatis插入数据的时候返回插入记录的主键id**

在进行输入库插入的时候，如果我们需要使用已经插入的记录的主键，则需要返回刚才插入的数据的主键id。

通过设置 insert 标签的 useGeneratedKeys 属性为 true 可以返回插入的记录的主键的id。

```
<insert id="User" useGeneratedKeys="true" keyProperty="id"> </insert>
```