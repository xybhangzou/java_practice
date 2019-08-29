package com.xnf.practice;


    public class Test{

    public static void main(String [] args){
        Class a = All.class;
        System.out.println(a.getName());


        try{
            Class c = Class.forName("com.xnf.practice.World");
            System.out.println( c.getName());

            All b = (All)c.newInstance();
            b.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}