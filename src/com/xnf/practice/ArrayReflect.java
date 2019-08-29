package com.xnf.practice;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * 集合的泛型（演示：通过反射（编译之后的操作）实例）
 */
public class ArrayReflect {

    public static void main(String[] args) {
        ArrayList list = new ArrayList();

        ArrayList<String> list1 = new ArrayList<String>();
        list1.add("2");
//        list1.add(30)  -- 错误，添加类型不正确。
        Class c1 = list.getClass();
        Class c2 = list1.getClass();

        System.out.println(c1 == c2);//True
        //反射的操作都是编译之后的操作

        /**
         * c1 ==c2 返回True说明编译之后的集合是去泛型化的，
         * Java 中集合的泛型，是防止错误输入的，只在编译阶段有效，绕过编译就无效了。---下面实例通过反射来验证
         */

        try {
            Method m = c2.getMethod("add",Object.class);
            m.invoke(list1,20);//绕过编译之后的操作
            System.out.println(list1.size());
            System.out.println(list1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
