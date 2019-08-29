package com.xnf.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 目标类（LiuDeHua）代理类
 */
public class LiuDeHuaProxy {
    //类变量，代理类要代理的目标对象
    private People ldh = new LiuDeHua();

    public People getProxy() {
        return (People) Proxy.newProxyInstance(LiuDeHuaProxy.class
                .getClassLoader(), ldh.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method,
                                 Object[] args) throws Throwable {
                //此处可添加目标对象以外的方法。。。

                //如果调用的是代理对象的sing方法
                if (method.getName().equals("sing")) {
                    System.out.println("我是他的经纪人，要找他唱歌得先给十万块钱！！");
                    return method.invoke(ldh, args); //代理对象调用真实目标对象的sing方法去处理用户请求
                }
                //如果调用的是代理对象的dance方法
                if (method.getName().equals("dance")) {
                    System.out.println("我是他的经纪人，要找他跳舞得先给二十万块钱！！");
                    //已经给钱了，经纪人自己不会唱歌，就只能找刘德华去跳舞！
                    return method.invoke(ldh, args);//代理对象调用真实目标对象的dance方法去处理用户请求
                }

                return null;
            }
        });
    }

}
