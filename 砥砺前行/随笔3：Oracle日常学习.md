

## 1.nvl（字段，字段）函数：1为null取2

```java
select * from 管理员.表 a where nvl(a.字段,a.字段)=‘数’
```

## 2.decode（字段，‘’，字段，‘1’，字段，字段）

第一组是，进第二组，第二组是，取第三组，如果有第四组取第四组。

```java
select 字段 from 表名 where decode （字段，‘’，另一个字段，‘’，字段，字段）=‘字段’
```

```java
 nvl(b.cerno, decode(t.invtype, 2, t.cerno, t.asrecerno)) sfzjhm
```

解释：

字段1为空，字段2为1，字段3无限制，字段4和字段5比较，如果相同执行sql。

# 3.连环sql

```java
select * 
    from hx.al z
    where y.b1 in (select t.d1
    		from hx.c1 y
             where y.d2 = ?
             and y.d3 = ?
             and y.d4 = 'Y' )
    and z.e1=
    	(
			select Max(e.字段)
			from hx.表1 e
    		where e.字段 in(select m.f
    					from hx.o e
    					where e. =?
    					and e. =?
    					and e. ='Y'))
```

# 4.传参数

1. ```java
   String a = "";
   for(int i = 0; i<= list.size(); i++){
       Map map = list.get(i);
       if(i==list.size()-1){
           a = a+map.get("a");
       }else{
           a=a+map.get("a")+",";
       }
   }
   a="'"+a.replace(",",",")+"'";
   SQLParam param = new SQLParam();
   param.addMacroParam(a);
   List<Map<String,Object>>aList=ps.queryMapListByKey("SQLKey",param);

   SQL:
   select 查询字段, 
   	(select 字段 
   		from 表
   		where 俩表相等字段)
   	from 表  where  and 字段 in （#macroparam#）
   ```

   # 5.字符拼接（只能用在Oracle）

   **||**：实战举例1：

   ```java
   select max（nvl(字段，0)）+1 || ‘’ from 表
   ```

   ​ 实战举例2：

   ```java
   select nvl(max(to_number(字段))，0)+1 || '' from 表
   ```

   # 6.去重

   ```
   select distinct * from table(表名) where (条件)
   ```

   # ​7.模糊查询


```java
select a.*,rowid from SJGS_FPXCJGJ  a where a.fpxcuuid='111111111' and a.fp_dm like '2%';  --以2开头
select a.*,rowid from SJGS_FPXCJGJ  a where a.fpxcuuid='111111111' and a.fp_dm like '%2';  --以2结尾
select a.*,rowid from SJGS_FPXCJGJ  a where a.fpxcuuid='111111111' and a.fp_dm like '%2%'; --还有2的
```

# 8.TRUNC函数

```
TRUNC（number,num_digits）
Number 需要截尾取整的数字。
Num_digits 用于指定取整精度的数字。Num_digits 的默认值为 0。如果Num_digits为正数，则截取小数点后Num_digits位；如果为负数，则先保留整数部分，然后从个位开始向前数，并将遇到的数字都变为0。
TRUNC()函数在截取时不进行四舍五入，直接截取。

针对数字的案例，如：

select trunc(123.458) from dual --123

select trunc(123.458,0) from dual --123

select trunc(123.458,1) from dual --123.4

select trunc(123.458,-1) from dual --120

select trunc(123.458,-4) from dual --0

select trunc(123.458,4) from dual --123.458

select trunc(123) from dual --123

select trunc(123,1) from dual --123

select trunc(123,-1) from dual --120

 

针对日期的案例，如：

select trunc(sysdate) from dual --2017/6/13  返回当天的日期

select trunc(sysdate,'yyyy') from dual   --2017/1/1  返回当年第一天.

select trunc(sysdate,'mm') from dual  --2017/6/1  返回当月第一天.

select trunc(sysdate,'d') from dual  --2017/6/11 返回当前星期的第一天(以周日为第一天).

select trunc(sysdate,'dd') from dual  --2017/6/13  返回当前年月日

select trunc(sysdate,'hh') from dual  --2017/6/13 13:00:00  返回当前小时

select trunc(sysdate,'mi') from dual  --2017/6/13 13:06:00  返回当前分钟
```

# 9.select 1 和 exists 子句

select 1常用在exists子句中，检测符合条件记录是否存在。

如select * from T1 where exists(select 1 from T2 where T1.a=T2.a) ;
T1数据量小而T2数据量非常大时，T1<<T2 时，1) 的查询效率高。

```
select *
  		from WBJH_JYZX_GGXX a
 		where a.lrrq &gt; trunc(sysdate)
   		and a.lrrq &lt;= (TRUNC(SYSDATE) - 1)
   		AND A.CLBZ = '0'
   		and not exists (select 1 from dj_gsjyzxhdxx t where a.jyzxgguuid=t.lyuuid_1 and t.gsjyzxhd_dm='10')
```

```
select a.regno zzhm,nvl(b.cerno,decode(t.invtype, 2, t.cerno, t.asrecerno)) sfzjhm from hz_e_baseinfo a 
		left join hz_e_pri_person b on a.pripid=b.pripid
		left join hz_e_inv t on t.pripid=a.pripid and t.exeaffsign = '1'
		where a.pripid =?
```

# 10.start with

```
 假如一个表里存在2个字段：STUDENT_ID（学生ID）、GRADE_ID（班级ID），那么我们可以通过表示每一个学生属于哪一个班级，来形成一个树状
结构，通过START WITH... CONNECT BY PRIOR...语法，就可以取得这棵树的所有记录。

--自顶向下 以BRH_ID = '0003'这个节点为根节点，向下查询
SELECT * FROM IM_BRANCH 
START WITH BRH_ID = '0003'
CONNECT BY PRIOR BRH_ID = BRH_PARENTID

--自底向上 以BRH_ID = '0003'这个节点为叶节点，向上查询
SELECT * FROM IM_BRANCH 
START WITH BRH_ID = '0003'
CONNECT BY BRH_ID = PRIOR BRH_PARENTID
```

# 11.listagg行转列

```
//listagg() WITHIN GROUP ()  将多行合并成一行
SELECT
	T .DEPTNO,
	listagg (T .ENAME, ',') WITHIN GROUP (ORDER BY T .ENAME) names
FROM
	SCOTT.EMP T
WHERE
	T .DEPTNO = '20'
GROUP BY
	T .DEPTNO
```

# 12.脚本和sql

## [sql存储过程和sql脚本的区别](https://www.cnblogs.com/hushzhang/p/12753033.html)

```
数据库脚本: 一般来说就是sql语句、命令的组合，属于未命名的，每次执行前需要编译。

存储过程： 是命名的sql脚本，经过预编译；执行时不需要再次编译。


好用的sql：
select to_char(trunc(t.xgrq),'YYYYMMDD') , count(1) from dl_dqrb T where trunc(t.xgrq) >=date '2010-07-20' and trunc(t.xgrq) < date'2020-08-23' group by to_char(trunc(t.xgrq),'YYYYMMDD')

select to_char(t.xgrq,'YYYYMMDD') , (row_number() over(partition by to_char(t.xgrq,'YYYYMMDD')order by t.xgrq)) xh from dl_dqrb T where trunc(t.xgrq) >=date '2010-07-20' and trunc(t.xgrq) < date'2020-08-23' 

```

# 13.excel批量写语句

新建格子拼接语句，转到txt批量替换。plsql里面用command执行，in最多999条语。

# 14.exists 存在

条件里添加 exists（select 1 from 表 where 条件）

# 15.并行

```
select /*+parallel（想开的并行数量）*/ * from 表
```

# 16.替代函数：translate和replace 

1.translate

  语法：TRANSLATE(char, from, to)

  

  用法：返回将出现在from中的每个字符替换为to中的相应字符以后的字符串。

​            若from比to字符串长，那么在from中比to中多出的字符将会被删除。

​            三个参数中有一个是空，返回值也将是空值。

  举例：SQL> select translate('abcdefga','abc','wo') 返回值 from dual;

​            返回值

​            -------

​            wodefgw

  分析：该语句要将'abcdefga'中的'abc'转换为'wo',

​            由于'abc'中'a'对应'wo'中的'w',

​            故将'abcdefga'中的'a'全部转换成'w';

​            而'abc'中'b'对应'wo'中的'o',

​            故将'abcdefga'中的'b'全部转换成'o';

​            'abc'中的'c'在'wo'中没有与之对应的字符,

​            故将'abcdefga'中的'c'全部删除;

​            简单说来，就是将from中的字符转换为to中与之位置对应的字符，

​            若to中找不到与之对应的字符，返回值中的该字符将会被删除。

​            在实际的业务中，可以用来删除一些异常数据，

​            比如表a中的一个字段t_no表示电话号码，

​            而电话号码本身应该是一个由数字组成的字符串，

​            为了删除那些含有非数字的异常数据，

​            就用到了translate函数：

​            SQL> delete from a，

​                      where length(translate(trim(a.t_no), 

​                                            '0123456789' || a.t_no,

​                                            '0123456789')) <> length(trim(a.t_no));

 

2.replace 

   

  语法：REPLACE(char, search_string,replacement_string)

  用法：将char中的字符串search_string全部转换为字符串replacement_string。

​        

  举例：SQL> select REPLACE('fgsgswsgs', 'fk' ,'j') 返回值 from dual;

​            返回值

​            ---------

​            fgsgswsgs

 

​            SQL> select REPLACE('fgsgswsgs', 'sg' ,'eeerrrttt') 返回值 from dual;

​            返回值

​            -----------------------

​            fgeeerrrtttsweeerrrttts

  分析：第一个例子中由于'fgsgswsgs'中没有与'fk'匹配的字符串，

​            故返回值仍然是'fgsgswsgs'；

​            第二个例子中将'fgsgswsgs'中的字符串'sg'全部转换为'eeerrrttt'。

 

总结：综上所述，replace与translate都是替代函数，

只不过replace针对的是字符串，而translate针对的是单个字符。

# 17.between and

select  *   from student  where  score  between   60(包括) and 100(不包括)；                       在什么之间

select *  from  student  where   score  Not  between  0（包括） and  60 (不包括）；           在什么间之外

# 18.&

​    如果SQL中包含“&”的特殊字符，在执行的时候会认为&后面的是变量，会提示用户输入该变量的值，例如：我们在配置某个菜单的时候的location中会出现某个Action的配置，如果包含两个以上的参数'/XXXAction.do?method=xxx&xxid=xxx' 这样的字符串时，执行SQL的时候会过不去，会认为xxid为变量。

​    遇到这样的问题，我们可以将&符号与后面的字符串分离开的方式，可以将上面的字符串改成多个字符串连接：'/XXXAction.do?method=xxx' || '&' || 'xxid=xxx' ，这样的多字符串连接，执行SQL的时候就会正常运行。

# 19.union 和 union all的区别

相同点：
union和union all 都是对于多个查询结果的并集进行操作
不同点：
1.union 不会输出两个结果并集的重复行
2.union all 会输出两个结果并集的重复行

# 20.with语句

WITH AS短语，也叫做子查询部分（subquery factoring），是用来定义一个SQL片断，该SQL片断会被整个SQL语句所用到。这个语句算是公用表表达式（CTE）。

比如

with A as (select * from class)

```
select *from A  
```

这个语句的意思就是，先执行select * from class   得到一个结果，将这个结果记录为A  ，在执行select *from A  语句。A 表只是一个别名。

也就是将重复用到的大批量 的SQL语句，放到with  as 中，加一个别名，在后面用到的时候就可以直接用。

对于大批量的SQL数据，起到优化的作用。

PostgreSQL提供的一种方法，用于写一个大的查询中使用的辅助报表与查询，能使复杂和大型查询简单易读。

1：with子句的使用
使用现有数据库查询，查询在2017.05.01号0点到2017.05.08号0点之间的充值玩家的id,sid,期间充值总数num。
命令如下：
with u as (select id, sum(amount) as num from pay where pay_time >= 1493568000 and pay_time < 1494172800 group by id) select u.id, pinfo.sid, u.num from u join pinfo on u.id=pinfo.id;

解析：
使用with查询出相关玩家的id和期间充值总数num，
再查询出对应id玩家的sid。

2：多个with子句的使用
使用现有数据查询，在1的基础上，增加查询这些玩家到
2017.05.08号0点的历史充值总数total。
with u1 as (select id, sum(amount) as num from pay where pay_time >= 1493568000 and pay_time < 1494172800 group by id), u2 as(select id, sum(amount) as total from pay where pay_time < 1494172800 group by id) select u1.id, pinfo.sid, u1.num, u2.total from u1, u2, pinfo where u2.id = u1.id and pinfo.id = u1.id;

解析：
使用with查询出固定时间内充值的玩家id和期间充值总数，
使用with查询出指定时间之前充值的玩家的id和历史充值总数，
再查询出u1中所有id对应的u2中的数据，以及pinfo中的sid。

# 21.instr*函数

*instr*函数为字符查找函数，其功能是查找一个字符串在另一个字符串中首次出现的位置。*instr*函数在Oracle/PLSQL中是返回要截取的字符串在源字符串中的位置。

# 22.sign函数

比较大小的函数。根据某个值是0，正数韩式负数，分别返回0，1，-1。

# 23.流程控制函数decode

例：

工资在8000元以下的将加20％；工资在8000元以上的加15％，8000 元的不加。实现：
select decode(sign(salary - 8000),1,salary*1.15,-1,salary*1.2,salary from employee

含义解释:

引用

**decode(条件,值1,翻译值1,值2,翻译值2,...值n,翻译值n,缺省值)**

![1601536661853](C:\Users\14579\AppData\Local\Temp\1601536661853.png)

# 24.coalesce函数

1.coalesce函数是用来获取第一个不为空的列的值
2.coalesce函数里面的数据类型，必须全部都跟第一列的数据类型一致

例如：coalesce（aa.字段,bb.字段,cc.字段）

# 25.修改密码

开始-->运行-->cmd
输入 ：sqlplus /nolog 回车
输入 ：connect / as sysdba 回车
用户解锁 : alter user scott account unlock 回车
修改密码：alter user scott identified by **tiger**