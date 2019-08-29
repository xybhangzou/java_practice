package com.xnf.io;

import java.io.*;
import java.rmi.StubNotFoundException;

public class Student implements Serializable {

    private String name;
    private transient String no; //该属性不会进行jvm 序列化
    private int age;

    public Student() {
    }

    public Student(String name, String no, int age) {
        this.name = name;
        this.no = no;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", no='" + no + '\'' +
                ", age=" + age +
                '}';
    }
}

class MainTest{
    public static void main(String[] args) throws  Exception{
        String file1 ="C:\\Demo_project\\java_demo\\demo\\obj.dat";
        //对象的序列化处理
//        ObjectOutputStream oos = new ObjectOutputStream(
//                new FileOutputStream(file1)
//        );
//        Student s = new Student("张三","10001",12);
//        oos.writeObject(s);
//        oos.flush();
//        oos.close();
        //对象的反序列化
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file1));
        Student s2 = (Student) ois.readObject();
        System.out.println(s2);
        ois.close();

    }
}
