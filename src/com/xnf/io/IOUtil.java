package com.xnf.io;

import java.io.*;

public class IOUtil {
    /**
     * 读取指定文件内容，按照16进制输出到控制台，并且每输出10个字节换行。
     * @param fileName
     * @throws IOException
     */
    public static void printHex(String fileName) throws IOException {
        FileInputStream in = new FileInputStream(fileName);
        int b;
        int i=1;
        while((b=in.read())!=-1){
            if(b<=0xf){
                //单位数面前补0
                System.out.print("0");
            }
            System.out.print(Integer.toHexString(b)+" ");
            if(i++%10==0){
                System.out.println();
            }
        }
        in.close();

    }

    /**
     * 文件拷贝，字节批量读取
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile)throws IOException{
        if(!srcFile.exists()){
            throw new IllegalArgumentException("文件:"+srcFile+"不存在");
        }
        if(!srcFile.isFile()){
            throw new IllegalArgumentException(srcFile+"不是文件");
        }
        FileInputStream in = new FileInputStream(srcFile);
        FileOutputStream out = new FileOutputStream(destFile);
        byte[] buf = new byte[8*1024];
        int b ;
        while((b = in.read(buf,0,buf.length))!=-1){
            out.write(buf,0,b);
            out.flush();//最好加上
        }
        in.close();
        out.close();

    }

    /**
     * 字符流处理
     * @throws IOException
     */
   public void zflMethod() throws IOException{
       FileInputStream in = new FileInputStream("e:\\javaio\\imoocutf8.txt");
       InputStreamReader isr = new InputStreamReader(in,"utf-8");//默认项目的编码,操作的时候，要写文件本身的编码格式

       FileOutputStream out = new FileOutputStream("e:\\javaio\\imoocutf81.txt");
       OutputStreamWriter osw = new OutputStreamWriter(out,"utf-8");
        /*int c ;
        while((c = isr.read())!=-1){
            System.out.print((char)c);
        }*/
       char[] buffer = new char[8*1024];
       int c;
		/*批量读取，放入buffer这个字符数组，从第0个位置开始放置，最多放buffer.length个
		  返回的是读到的字符的个数
		*/
       while(( c = isr.read(buffer,0,buffer.length))!=-1){
           String s = new String(buffer,0,c);
           System.out.print(s);
           osw.write(buffer,0,c);
           osw.flush();
       }
       isr.close();
       osw.close();
   }

    public static void main(String[] args) {

        try {
            printHex("D:\\document\\个人资料整理\\个人电脑信息记录.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        File src = new File("D:\\document\\技术文档整理\\技术类pdf\\Linux就该这么学.pdf");
        File desc = new File("D:\\document\\技术文档整理\\技术类pdf\\Linux学.pdf");
        try {
            //文件的复制
            copyFile(src,desc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
