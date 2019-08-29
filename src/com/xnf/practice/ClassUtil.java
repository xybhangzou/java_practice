package com.xnf.practice;

import java.lang.reflect.Method;

public class ClassUtil {

    /**
     * 打印类的信息：包括成员函数、成员变量
     * @param object 该对象所属类信息
     */
   public static void printClassMessage(Object object){
         Class c = object.getClass();

         //类的名称
       System.out.println("该类的全称："+c.getName());
       System.out.println("该类的简称："+c.getSimpleName());

       //成员函数（方法）
//       Method [] methods = c.getDeclaredMethods();//该类自己所有方法。

       printMethod(c);

   }

    public static void printMethod(Class c) {
        Method[] methods = c.getMethods();//该类所有public 方法，包含父类继承而来的。

        for(int i=0;i<methods.length;i++){
             //方法的返回值类型
            Class returnType = methods[i].getReturnType();
            System.out.println(returnType.getName());

            //方法名称
            System.out.print(methods[i].getName()+"()");

            //方法参数类类型
            Class [] paramTypes = methods[i].getParameterTypes();
            for(Class p:paramTypes){
                //参数类型名称
                System.out.print(p.getName()+",");
            }


        }
    }


}


class TestReflect{
    public static void main(String[] args) {
        String s ="aaa";
        ClassUtil.printClassMessage(s);
    }
}