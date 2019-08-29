package com.xnf.io;

import java.io.File;
import java.io.IOException;

/**
 * File 对象（目录、文件）的路径访问等操作。
 */
public class FileDemo {

    public static void main(String[] args) {
        //构造函数（用于创建文件或目录的file 对象）
        File file = new File("D:\\document\\个人资料整理\\ticket-model");

        //File.separator 分隔符，等同于"\\"转义符的作用。
        File file1 = new File("D:"+File.separator+"document");
        System.out.println("document 目录是否存在："+ file1.exists());
        if(!file.exists()){
            try {
                //创建一个文件（例如：".txt"; ".docx"结尾的文件）
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //创建单个目录，file 对象的路径中，父级目录必须存在，方可创建子目录
            file.mkdir();
            //创建多级目录，file 对象的路径中，不管父级目录是否存在，如果存在，则在该存在的目录下创建；不存在，则创建父目录和子目录
            file.mkdirs();

        }else {
            //文件、目录的删除
//            file.delete();
        }
        //------常用方法----------
        //判断文件、目录是否存在
        System.out.println(file.exists());

        //判断文件是否是目录
        System.out.println(file.isDirectory());

        //判断文件是否是文件（例如：".txt",".docx"）
        System.out.println(file.isFile());

        //打印file 对象的path，等同于该对象的toString()方法。
        System.out.println(file.toString());//file.toString()的内容

        //文件（目录）对象的绝对路径
        System.out.println(file.getAbsolutePath());

        //文件（目录）的名称,返回文件（目录）的名称
        System.out.println(file.getName());

        //返回父级目录
        System.out.println(file.getParent());







    }
}
