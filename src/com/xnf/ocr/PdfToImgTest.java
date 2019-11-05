package com.xnf.ocr;

public class PdfToImgTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		// TODO Auto-generated method stub
		String pdfurl="C:\\Users\\99094\\Desktop\\document\\重庆合同模板\\重庆合同pdf\\dy.pdf";
		String imgurlpath="C:\\Users\\99094\\Desktop\\document\\重庆合同模板\\重庆合同pdf\\dy";
		int i = PdfToImg.changePdfToImg(pdfurl,imgurlpath);
        System.out.println(i);
	}

}
