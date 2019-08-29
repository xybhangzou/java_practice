package com.xnf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RafDemo {

    public static void main(String[] args)throws IOException {
        File demo = new File("demo");
        if(!demo.exists()){
            demo.mkdir();
        }
        File file = new File(demo,"raf.dat");
        if(!file.exists()){
            file.createNewFile();
        }

        RandomAccessFile raf = new RandomAccessFile(file,"rw");
        System.out.println(raf.getFilePointer());
        String s = "文件IO流的学习！";
        byte [] bytes = s.getBytes("utf-8");
        //写文件
        raf.write(bytes);

        //读文件，必须把指针移到头部
        raf.seek(0);
        //一次性读取，把文件中的内容读取到数组中。
        byte[] buf = new byte[(int)raf.length()];
        raf.read(buf);
    }
}
