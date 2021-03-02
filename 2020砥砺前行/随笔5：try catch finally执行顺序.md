```java
try{
}chatch(){
}finally{
}
```

```java
class Test22
{
public static String test(){
try{
//throw new Exception();
return "return";
}catch(Exception e){
System.out.println("catch");
}finally{
return "finally";
}
}
public static void main(String[] args)
{
System.out.println(test());
}
}
```

是java中异常处理最常见的一种方法。
测试一:

输出结果为:finally
这说明finally先执行,其实我这也可以通过理解return的意义来理解这个顺序,return是指跳出这个语句块,跳出了自然不能回来再执行里面的语句了,而finally又是必须执行的,所以在跳出之前还是让finally执行完吧.

测试二:

class Test22
{
public static String test(){
try{
throw new Exception();

}catch(Exception e){
System.out.println("catch");
}finally{
return "finally";
}
}
public static void main(String[] args)
{
System.out.println(test());
}
}

输出结果:
catch
finally
这就是平常最常见的顺序了,try中势力出了异常,那么就是catch中执行了,然后就是finally里的语句了.
测试三:
class Test22
{
public static String test(){
try{
throw new Exception();
}catch(Exception e){
return "catch";
}finally{
return "finally";
}
}
public static void main(String[] args)
{
System.out.println(test());
}
}
输出结果:
finally
在catch中有return和try块中有return是一样的情况了.