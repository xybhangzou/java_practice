package com.xnf.ocr;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.AccessController;
import java.security.PrivilegedAction;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

public class PdfToImg {
	public static int changePdfToImg(String instructiopath,String picturepath) {
		int countpage =0;
		try {

			File file = new File(instructiopath);
			//随机文件
			RandomAccessFile raf = new RandomAccessFile(file, "r");
			FileChannel channel = raf.getChannel();
			//缓冲区
			MappedByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0, channel.size());
			PDFFile pdffile = new PDFFile(buf);
			//创建图片文件夹
			File dirfile = new File(picturepath);
			if(!dirfile.exists()){
				dirfile.mkdirs();
			}
			//获得图片页数
			countpage = pdffile.getNumPages();
			System.out.println("总页数是---->:"+countpage);
			for (int i = 1; i <= countpage; i++) {
				PDFPage page = pdffile.getPage(i);
				int width=(int)page.getBBox().getWidth();
				int height=(int)page.getBBox().getHeight();
				Rectangle rect = new Rectangle(0, 0, width, height);
				int n = 2;
				/** 图片清晰度（n>0且n<7）【pdf放大参数】 */
				Image img = page.getImage(width * n, height * n,
						rect, /** 放大pdf到n倍，创建图片。 */
						null, /** null for the ImageObserver */
						true, /** fill background with white */
						true /** block until drawing is done */
				);
				//图像缓冲区
				BufferedImage tag = new BufferedImage(width * n,
						height * n, BufferedImage.TYPE_INT_RGB);
				tag.getGraphics().drawImage(img, 0, 0, width * n,
						height * n, null);
				/**
				 * hyiao 修改转成pdf之后，进行图片切割
				 */
				//首页单张不切割
//				if(i==1) {
					FileOutputStream out = new FileOutputStream(picturepath+"/"+i+".jpg");
					/** 输出到文件流 */
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
					JPEGEncodeParam param2 = encoder.getDefaultJPEGEncodeParam(tag);

					param2.setQuality(1f, true);
					/** 1f~0.01f是提高生成的图片质量 */
					encoder.setJPEGEncodeParam(param2);
					encoder.encode(tag);
					/** JPEG编码 */
					out.close();
//				}else {
//					//图片切割
//					splitImage(i,picturepath,tag);
//					//splitImage(i,picturepath,picturepath+"/"+ i+".jpg");
//				}
			}
			channel.close();
			raf.close();
			unmap(buf);
			/** 如果要在转图片之后删除pdf，就必须要这个关闭流和清空缓冲的方法 */
			//file.delete();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return countpage;

	}

	@SuppressWarnings("unchecked")
	public static void unmap(final Object buffer) {
		AccessController.doPrivileged(new PrivilegedAction() {
			public Object run() {
				try {
					Method getCleanerMethod = buffer.getClass().getMethod(
							"cleaner", new Class[0]);
					getCleanerMethod.setAccessible(true);
					sun.misc.Cleaner cleaner = (sun.misc.Cleaner) getCleanerMethod
							.invoke(buffer, new Object[0]);
					cleaner.clean();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	private static void splitImage(int filename,String file_path,String originalImg) throws IOException {

		//String originalImg = "C:\\img\\split\\a380_1280x1024.jpg";
		//String originalImg = "F:\\images\\school\\-11ff2b32525fd5f2.jpg";
		// 读入大图
		File file = new File(originalImg);
		FileInputStream fis = new FileInputStream(file);
		BufferedImage image = ImageIO.read(fis);

		// 分割成4*4(16)个小图
		int rows = 1;
		int cols = 2;
		int chunks = rows * cols;

		// 计算每个小图的宽度和高度
		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				//设置小图的大小和类型
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				//写入图像内容
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0,
						chunkWidth, chunkHeight,
						chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth,
						chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}

		// 输出小图
		for (int i = 0; i < imgs.length; i++) {
			//ImageIO.write(imgs[i], "jpg", new File("C:\\img\\split\\img" + i + ".jpg"));
			ImageIO.write(imgs[i], "jpg", new File(file_path +"/"+filename+""+i + ".jpg"));
		}

		System.out.println("完成分割！");
	}


	private static void splitImage(int filename,String file_path,BufferedImage image) throws IOException {

		// 分割成4*4(16)个小图
		int rows = 1;
		int cols = 2;
		int chunks = rows * cols;

		// 计算每个小图的宽度和高度
		int chunkWidth = image.getWidth() / cols;
		int chunkHeight = image.getHeight() / rows;

		int count = 0;
		BufferedImage imgs[] = new BufferedImage[chunks];
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				//设置小图的大小和类型
				imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());

				//写入图像内容
				Graphics2D gr = imgs[count++].createGraphics();
				gr.drawImage(image, 0, 0,
						chunkWidth, chunkHeight,
						chunkWidth * y, chunkHeight * x,
						chunkWidth * y + chunkWidth,
						chunkHeight * x + chunkHeight, null);
				gr.dispose();
			}
		}

		// 输出小图
		for (int i = 0; i < imgs.length; i++) {
			//   ImageIO.write(imgs[i], "jpg", new File(file_path+"/" +filename+"-"+i + ".jpg"));
			FileOutputStream out = new FileOutputStream(file_path +"/"+filename+""+i + ".jpg");
			/** 输出到文件流 */
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param2 = encoder.getDefaultJPEGEncodeParam(imgs[i]);

			param2.setQuality(1f, true);
			/** 1f~0.01f是提高生成的图片质量 */
			encoder.setJPEGEncodeParam(param2);
			encoder.encode(imgs[i]);
			/** JPEG编码 */
			out.close();
		}

		System.out.println("完成分割！");
	}

}
