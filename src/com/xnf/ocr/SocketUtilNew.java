package com.xnf.ocr;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.util.StringUtils;

public class SocketUtilNew {
	private Logger logger = Logger.getLogger(this.getClass());
	private Socket socket;
	/**
	* 判断是否断开连接，断开返回true,没有返回false
	* @param
	* @return
	*/

	public static String scan (String ip, int port, String filePath){
		SocketUtilNew socketUtil = new SocketUtilNew();
		Boolean serverClose = socketUtil.isServerClose(ip, port);
		if(serverClose){
			return "true";
		}
		return socketUtil.send(ip, port, filePath);
	}

	public Boolean isServerClose(String ip, int port) {
		try {
			socket = new Socket(ip, port);
			socket.setSoTimeout(120000);
			//下面这段话注释，否则windows引擎无法使用
			//socket.sendUrgentData(0xFF);// 发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信
			return false;
		} catch (Exception se) {
			return true;
		}
	}

	/**
	 * 调用引擎
	 * @param ip 引擎IP地址
	 * @param port 引擎端口
	 * @param filePath 要识别图片地址
	 * @return
	 * @author wujp
	 * 2019年9月17日上午9:47:36
	 */
	public String send(String ip, int port, String filePath) {
		String result = null;
		try {
			// 2.获取输出流，用来向服务器发送信息
			OutputStream os = socket.getOutputStream();// 字节输出流
			// 转换为打印流
			PrintWriter pw = new PrintWriter(os);
			pw.write(filePath);
			pw.flush();// 刷新缓存，向服务器端输出信息
			// 关闭输出流
//			socket.shutdownOutput();
			// 3.获取输入流，用来读取服务器端的响应信息
			InputStream is = socket.getInputStream();
			Charset charset = Charset.forName("gbk");
			result = IOUtils.toString(is, charset);
			result = StringUtils.trimAllWhitespace(result);
			is.close();
			pw.close();
			os.close();
			socket.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("socket传输异常：" + ex.getMessage());
			ex.printStackTrace();
		}
		return result;
	}

	/**
	 * 全文本截取
	 * @param text 全文本
	 * @param startWord 开始字符
	 * @param endWord  结束字符
	 * @return
	 */
	public static String textSub(String text,String startWord,String endWord){
		String result = "" ;
		if(text.indexOf(startWord) < 0 ){
			System.out.println("----全文本检索不存在该字符:"+startWord+"！----");
		}
		if(text.indexOf(endWord) < 0 ){
			System.out.println("全文本检索不存在该字符:"+endWord+"！----");
		}
        if(text.indexOf(startWord) >= 0 && text.indexOf(endWord) >=0 ){
        	int startIndex = text.indexOf(startWord);
        	int endIndex = text.indexOf(endWord);
			result = text.substring(startIndex,endIndex).substring(startWord.length());
		}
		return result;
	}

	/**
	 *  单行文本截取
	 * @param rowText 单行文本信息
	 * @param keyWord  截取关键字
	 * @return
	 */
	public static String rowSub(String rowText,String keyWord){
        String signZn = "：";
        String signEn = ":";
        String result = "";
        int index = rowText.indexOf(keyWord);
        if(index >= 0){
        	String sub = rowText.substring(index).trim();
             if(sub.indexOf(signZn) > 0){
                   result = sub.substring(sub.indexOf(signZn)+1);
			 }
             if(sub.indexOf(signEn) > 0){
				   result = sub.substring(sub.indexOf(signEn)+1);
			 }
		}
        return result;
	}

	/**
	 * 重庆--高压合同关键信息提取
	 */
	public static void getGYText(){
		for (int j=1;j<=36;j++) {
			String imagePath = "C:\\Users\\99094\\Desktop\\document\\重庆合同模板\\重庆合同pdf\\gy\\"+j+".jpg";
			String resltstr = scan("127.0.0.1", 9160, imagePath);
			JSONObject res = new JSONObject(resltstr);
			String content = res.get("content").toString();
			String reslt[] = content.split("\\n\\r\\n");
			System.out.println(content);
		}
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
        //高压
		getGYText();
	}
}

