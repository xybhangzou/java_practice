package com.xnf.io;

/**
 * 字符转字节，字节转会字符等操作。
 */
public class Encode {


    public static void main(String[] args) throws Exception {
        String s = "慕课ABC";
        //gbk 编码，中文占用两个字节，英文一个
        byte[] byte1 = s.getBytes("gbk");
        //utf-8编码，中文占用三个字节，英文一个
        byte [] byte2 = s.getBytes("utf-8");
        //utf-16be编码，中、英文占用两个字节
        byte [] byte3 = s.getBytes("utf-16be");

//        for(byte b1 : byte1){
//            //把字节转成（int）16进制的方式显示
//            System.out.print(Integer.toHexString(b1&0xff) +"  ");
//        }

        for(byte b2 : byte2){
            //把字节转成（int）16进制的方式显示
            System.out.print(Integer.toHexString(b2&0xff) +"  ");
        }
        System.out.println();
        for(byte b3 : byte3){
            //把字节转成（int）16进制的方式显示
            System.out.print(Integer.toHexString(b3&0xff) +"  ");
        }

        /**
         * 当你的字节序列是某种编码时，这个时候想字节序列转为字符串，也需要这种编码，
         * 即转化为字节序列的编码和反转为字符串的编码应该一值。示例如下：
         */
        String str = new String(byte1,"gbk");
        System.out.println(str);

        String str1 = new String(byte2);
        System.out.println(str1);

        /**
         * 文本文件就是字节序列，可以是任意编码的字节序列
         * 如果我们在中文机器上直接创建文本文件，那么该文本文件只认识ansi编码。
         *
         */



    }
}
