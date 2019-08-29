package com.xnf.io;

import java.io.File;
import java.io.IOException;

/**
 * 列出File 一系列操作，遍历、过滤等操作。
 */
public class FileUtils {

    /**
     * 列举出指定目录（包含子目录）的所有文件。
     * @param dir
     * @throws IOException
     */
    public static void listDirectory(File dir) throws IOException {
        if(!dir.exists()){
            throw new IllegalArgumentException("目录"+dir+"不存在！");
        }
        if(!dir.isDirectory()){
            throw new IllegalArgumentException(dir+"不是目录");
        }
        //返回当前目录下所有文件（目录、文件）path,该path 不包含父级，不包含子目录的内容。
        /* String [] dirs = dir.list();
        for(String s:dirs){
            System.out.println(dir+"\\"+s);
        }*/

        //对目录下的子目录内容的获取，就必须获取当前目录下的所有文件的对象，
        // 对这些为目录的对象进行遍历处理。
        File [] files = dir.listFiles();
        if(files != null && files.length>0){
            for(File f:files){
                if (f.isDirectory()) {
                    listDirectory(f);
                }else{
                    System.out.println(f);
                }
            }
        }


    }


    public static void main(String[] args)throws IOException {
        listDirectory(new File("D:\\document\\个人资料整理"));
    }
}

