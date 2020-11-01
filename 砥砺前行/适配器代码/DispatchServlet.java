package com.mcj.zhongruan.modules.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: MCJ
 * @Date: 2020/8/27 15:54
 * 模拟一个dispatcherServlet
 */
public class DispatchServlet {

    public static List<HandlerAdapter>handlerAdapters = new ArrayList<HandlerAdapter>();

    public DispatchServlet(){
        handlerAdapters.add(new AnnotationHandlerAdapter());
        handlerAdapters.add(new HttpHandlerApapter());
        handlerAdapters.add(new SimpleHandlerAdapter());
    }

    public void doDispatch(){
        //此处模拟SpringMVC从request取handler的对象，仅仅new出，可以出。
        //不论实现何种Controller，适配器总能经过适配以后得到想要的结果。
  //      HttpController controller2 = new HttpController();
        AnnotationController controller2 = new AnnotationController();
  //      SimpleController controller2 = new SimpleController();
        //得到对应适配器
        HandlerAdapter adapter = getHandler(controller2);
        //通过适配器执行对应的controller对应方法
        adapter.handle(controller2);

    }

    private HandlerAdapter getHandler(Controller controller2) {
        for (HandlerAdapter adapter:this.handlerAdapters) {
            if (adapter.supports(controller2)){
                return adapter;
            }
        }
        return null;
    }

    public static void main(String[]args){
        new DispatchServlet().doDispatch();
    }

}
