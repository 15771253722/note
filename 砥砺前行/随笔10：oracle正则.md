

/*
ORACLE中的支持正则表达式的函数主要有下面四个：
1，REGEXP_LIKE ：与LIKE的功能相似
2，REGEXP_INSTR ：与INSTR的功能相似
3，REGEXP_SUBSTR ：与SUBSTR的功能相似
4，REGEXP_REPLACE ：与REPLACE的功能相似
它们在用法上与Oracle SQL 函数LIKE、INSTR、SUBSTR 和REPLACE 用法相同，
但是它们使用POSIX 正则表达式代替了老的百分号（%）和通配符（_）字符。
POSIX 正则表达式由标准的元字符（metacharacters）所构成：
'^' 匹配输入字符串的开始位置，在方括号表达式中使用，此时它表示不接受该字符集合。
'$' 匹配输入字符串的结尾位置。如果设置了 RegExp 对象的 Multiline 属性，则 $ 也匹
配 '\n' 或 '\r'。
'.' 匹配除换行符之外的任何单字符。
'?' 匹配前面的子表达式零次或一次。
'+' 匹配前面的子表达式一次或多次。
'*' 匹配前面的子表达式零次或多次。
'|' 指明两项之间的一个选择。例子'^([a-z]+|[0-9]+)$'表示所有小写字母或数字组合成的
字符串。
'( )' 标记一个子表达式的开始和结束位置。
'[]' 标记一个中括号表达式。
'{m,n}' 一个精确地出现次数范围，m=<出现次数<=n，'{m}'表示出现m次，'{m,}'表示至少
出现m次。
\num 匹配 num，其中 num 是一个正整数。对所获取的匹配的引用。
字符簇： 
[[:alpha:]] 任何字母。
[[:digit:]] 任何数字。
[[:alnum:]] 任何字母和数字。
[[:space:]] 任何白字符。
[[:upper:]] 任何大写字母。
[[:lower:]] 任何小写字母。
[[:punct:]] 任何标点符号。
[[:xdigit:]] 任何16进制的数字，相当于[0-9a-fA-F]。
各种操作符的运算优先级
\转义符
(), (?:), (?=), [] 圆括号和方括号
*, +, ?, {n}, {n,}, {n,m} 限定符
^, $, anymetacharacter 位置和顺序
| 
*/
--创建表
create table fzq
(
id varchar(4),
value varchar(10)
);
--数据插入
insert into fzq values
('1','1234560');
insert into fzq values
('2','1234560');
insert into fzq values
('3','1b3b560');
insert into fzq values
('4','abc');
insert into fzq values
('5','abcde');
insert into fzq values
('6','ADREasx');
insert into fzq values
('7','123 45');
insert into fzq values
('8','adc de');
insert into fzq values
('9','adc,.de');
insert into fzq values
('10','1B');
insert into fzq values
('10','abcbvbnb');
insert into fzq values
('11','11114560');
insert into fzq values
('11','11124560');
--regexp_like
--查询value中以1开头60结束的记录并且长度是7位
select * from fzq where value like '1____60';
select * from fzq where regexp_like(value,'1....60');
--查询value中以1开头60结束的记录并且长度是7位并且全部是数字的记录。
--使用like就不是很好实现了。
select * from fzq where regexp_like(value,'1[0-9]{4}60');
-- 也可以这样实现，使用字符集。
select * from fzq where regexp_like(value,'1[[:digit:]]{4}60');
-- 查询value中不是纯数字的记录
select * from fzq where not regexp_like(value,'^[[:digit:]]+$');
-- 查询value中不包含任何数字的记录。
select * from fzq where regexp_like(value,'^[^[:digit:]]+$');
--查询以12或者1b开头的记录.不区分大小写。
select * from fzq where regexp_like(value,'^1[2b]','i');
--查询以12或者1b开头的记录.区分大小写。
select * from fzq where regexp_like(value,'^1[2B]');
-- 查询数据中包含空白的记录。
select * from fzq where regexp_like(value,'[[:space:]]');
--查询所有包含小写字母或者数字的记录。
select * from fzq where regexp_like(value,'^([a-z]+|[0-9]+)$');
--查询任何包含标点符号的记录。
select * from fzq where regexp_like(value,'[[:punct:]]');

