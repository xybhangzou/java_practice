package com.xnf.practice;

import java.lang.reflect.Method;

/**
 * 方法的反射
 */
public class MethodReflect {
    public static void main(String[] args) {
        A a = new A();
        Class c = a.getClass();

        Method m;

        {
            try {
                //getMethod()方法获取的是 public 方法。
                //getDeclaredMethod()方法获取自己声明的方法

                //Methond 对象的Invoke 方法---public Object invoke(Object obj, Object... args)

//                m = c.getMethod("print");
                m = c.getMethod("print",int.class,int.class);
                m.invoke(a,1,3);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}


class A{

    public void  print(){
        System.out.println("调用无参方法！");
    }

    public void print(int a,int b){
        System.out.println(a+b);
    }

    public void print(String a ,String b){
        System.out.println(a.toUpperCase()+","+b.toUpperCase());
    }

}