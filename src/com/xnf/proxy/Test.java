package com.xnf.proxy;

/**
 * 动态代理类的实现
 */
public class Test {
    public static void main(String[] args) {
        //代理类
        LiuDeHuaProxy proxy = new LiuDeHuaProxy();

        //获得代理对象
        People p = proxy.getProxy();

//        People p2 = new LiuDeHua();
//        p2.dance("aaaa");

        //调用代理对象的sing方法
        String retValue = p.sing("冰雨");
        System.out.println(retValue);

        //调用代理对象的dance方法
        String value = p.dance("江南style");
        System.out.println(value);
    }


}
