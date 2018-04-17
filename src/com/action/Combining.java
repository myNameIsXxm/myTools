package com.action;

import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Combining {

	// 需要设置的参数
	private String screenPic;// 底图路径
	private String logoPath;// 车标所在文件夹
	private String resultpath;


	private int marginTop;// 定制区域的上边缘距顶高度
	private int marginBottom;// 定制区域的下边缘距顶高度
	private int marginLeft;// LOGO的左偏移
	private int marginRight;// 定制区域的下边缘距顶高度

	// 不需要设置的参数
	private int customWidth;
	private int customHeight;

	private int logoMaxWidth;
	private int logoMaxHeight;

	private int width;
	private int height;
	private int top;
	private int left;

	private int resultWidth;
	private int resultHeight;

	public void combiningPic() {
		setScreenWidthAndHeight(screenPic);
		File root = new File(logoPath);
		File[] files = root.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				String pathName1 = f.getName();
				File[] files1 = f.listFiles();
				for (File ff : files1) {
					if (ff.isDirectory()) {
						String pathName2 = ff.getName();
						File[] files2 = ff.listFiles();
						for (File fff : files2) {
							if (fff.isDirectory()) {
								String pathName3 = fff.getName();
								File[] files3 = fff.listFiles();
								for (File ffff : files3) {
									if (!ffff.isDirectory()) {// -----------四层
										String pp = logoPath + "\\" + pathName1
												+ "\\" + pathName2 + "\\"
												+ pathName3 + "\\"
												+ ffff.getName();
										setLogoWidthAndHeight(ffff);
										setLogoTopAndLeft();
										PicInfor p = new PicInfor(pp, width, height, top, left);
										drawPic(screenPic, p);
									}
								}
							} else {// -----------三层
								String pp = logoPath + "\\" + pathName1 + "\\" + pathName2 + "\\" + fff.getName();
								setLogoWidthAndHeight(fff);
								setLogoTopAndLeft();
								PicInfor p = new PicInfor(pp, width, height, top, left);
								drawPic(screenPic, p);
							}
						}
					} else {// -----------两层
						String pp = logoPath + "\\" + pathName1 + "\\"+ ff.getName();
						setLogoWidthAndHeight(ff);
						setLogoTopAndLeft();
						PicInfor p = new PicInfor(pp, width, height, top, left);
						drawPic(screenPic, p);
					}
				}
			} else {// -----------一层
				String pp=logoPath + "\\" + f.getName();
				setLogoWidthAndHeight(f);
				setLogoTopAndLeft();
				PicInfor p = new PicInfor(pp, width, height, top, left);
				drawPic(screenPic, p);
			}
		}
	}

	private void setLogoTopAndLeft() {
		top = marginTop + (customHeight - height) / 2;
		left = marginLeft + (customWidth - width) / 2;
	}

	private void setLogoWidthAndHeight(File ff) {
		width = Util.getPicWidth(ff);
		height = Util.getPicHeight(ff);
		boolean b = false;
		int temp;
		if (height > logoMaxHeight) {
			temp = height;
			height = logoMaxHeight;
			width = width * logoMaxHeight / temp;
			b = true;
		}
		if (width > logoMaxWidth) {
			temp = width;
			width = logoMaxWidth;
			height = height * logoMaxWidth / temp;
			b = true;
		}
		if (!b) {
			if (width * logoMaxHeight > height * logoMaxWidth) {
				temp = width;
				width = logoMaxWidth;
				height = height * logoMaxWidth / temp;
			} else {
				temp = height;
				height = logoMaxHeight;
				width = width * logoMaxHeight / temp;
			}
		}
	}

	private void setScreenWidthAndHeight(String s) {
		File file = new File(s);
		resultWidth = Util.getPicWidth(file);
		resultHeight = Util.getPicHeight(file);
	}

	private void drawPic(String backgroundPicture, PicInfor pif) {
		String filename = pif.getPicUrl().replace(logoPath, resultpath);
		Util.newFilePathByFile(filename);
		try {
			BufferedImage bi = new BufferedImage(resultWidth, resultHeight,
					BufferedImage.TYPE_INT_ARGB);
			Graphics2D ig2 = bi.createGraphics();
			bi = ig2.getDeviceConfiguration().createCompatibleImage(
					resultWidth, resultHeight, Transparency.TRANSLUCENT);
			ig2.dispose();
			ig2 = bi.createGraphics();
			BufferedImage bibg = ImageIO.read(new File(backgroundPicture));
			ig2.drawImage(bibg, 0, 0, resultWidth, resultHeight, null);
			BufferedImage pic = ImageIO.read(new File(pif.getPicUrl()));
			ig2.drawImage(pic, pif.getLeft(), pif.getTop(), pif.getWidth(),
					pif.getHeight(), null);
			ImageIO.write(bi, "PNG", new File(filename));
			System.out.println("合成文件" + filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void refreshData(){
		customWidth = marginRight - marginLeft;
		customHeight = marginBottom - marginTop;
		logoMaxWidth = customWidth;
		logoMaxHeight = customHeight;
	}

	public void setScreenPic(String screenPic) {
		this.screenPic = screenPic;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

	public void setResultpath(String resultpath) {
		this.resultpath = resultpath;
	}

}