package com.xnf.io;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.*;

//使用 BufferedReader 在控制台读取字符
public class Test {

   public static void main(String [] args) throws IOException {
       char c;
       // 使用 System.in 创建 BufferedReader
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       System.out.println("输入字符, 按下 'q' 键退出。");
       // 读取字符
       do {
           c = (char) br.read();
           System.out.println(c);
       } while (c != 'q');

    }
}
//使用 BufferedReader 在控制台读取字符
class Test1{

    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;
        System.out.println("Enter 'end' to quit.");
        do{
            str = br.readLine();
            System.out.println(str);
        }while(!str.equals("end"));
    }
}

class Test2 {
//    public static void main(String[] args) {
//        int b;
//        b= 'A';
//        System.out.write(b);
//        System.out.write('\n');
//    }


    public static void main(String[] args) {
        try{
            String pdfFile = "C:/Users/sfzImage.pdf";
            PDDocument doc = PDDocument.load(new File(pdfFile));
            int pagenumber = doc.getNumberOfPages();
            pdfFile = pdfFile.substring(0, pdfFile.lastIndexOf("."));
            String fileName = pdfFile + ".doc";
            File file = new File(fileName);
            if (!file.exists())
            {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(fileName);
            Writer writer = new OutputStreamWriter(fos, "UTF-8");
            PDFTextStripper stripper = new PDFTextStripper();
            stripper.setSortByPosition(true);// 排序
            stripper.setStartPage(1);// 设置转换的开始页
            stripper.setEndPage(pagenumber);// 设置转换的结束页
            stripper.writeText(doc, writer);
            writer.close();
            doc.close();
            System.out.println("pdf转换word成功！");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}