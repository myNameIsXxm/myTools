package com.action;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PicColor {

	private int R;
	private int G;
	private int B;

	public void changPathPicColor(String colorpath) {
		File root = new File(colorpath);
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
										String pp = colorpath + "\\" + pathName1
												+ "\\" + pathName2 + "\\"
												+ pathName3 + "\\"
												+ ffff.getName();
										changePicColor(pp);
									}
								}
							} else {// -----------三层
								String pp = colorpath + "\\" + pathName1 + "\\"
										+ pathName2 + "\\" + fff.getName();
								changePicColor(pp);
							}
						}
					} else {// -----------两层
						String pp = colorpath + "\\" + pathName1 + "\\"
								+ ff.getName();
						changePicColor(pp);
					}
				}
			} else {// -----------一层
				changePicColor(colorpath + "\\" + f.getName());
			}
		}
	}

	private void changePicColor(String filepath) {
		try {
			BufferedImage bf = ImageIO.read(new FileInputStream(filepath));
			File file = new File(filepath);
			int height = Util.getPicHeight(file);
			int width = Util.getPicWidth(file);
			for (int h = 0; h < height; h++) {
				for (int w = 0; w < width; w++) {
					Color c = new Color(bf.getRGB(w, h));
					if (c.getBlue() != 255 || c.getRed() != 255
							|| c.getGreen() != 255) {
						bf.setRGB(w, h, new Color(R, G, B).getRGB());
					}
				}
			}
			Util.delFile(filepath);
			ImageIO.write(bf, "PNG", new File(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setR(int r) {
		R = r;
	}

	public void setG(int g) {
		G = g;
	}

	public void setB(int b) {
		B = b;
	}

}
