package com.action;

import java.awt.AWTException;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Util {
	/**
	 * 按一定的规则更改文件夹下的文件名字
	 *
	 * @param logoPath
	 *            文件夹路径
	 */

	public static void renamePic(String logoPath, Map<String, String> map) {
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
										String pp = logoPath + "\\" + pathName1 + "\\" + pathName2 + "\\" + pathName3
												+ "\\";
										new File(pp + ffff.getName())
												.renameTo(new File(pp + changString(ffff.getName(), map)));
									}
								}
							} else {// -----------三层
								String pp = logoPath + "\\" + pathName1 + "\\" + pathName2 + "\\";
								new File(pp + fff.getName()).renameTo(new File(pp + changString(fff.getName(), map)));
							}
						}
					} else {// -----------两层
						String pp = logoPath + "\\" + pathName1 + "\\";
						new File(pp + ff.getName()).renameTo(new File(pp + changString(ff.getName(), map)));
					}
				}
			} else {// -----------一层
				String pp = logoPath + "\\";
				new File(pp + f.getName()).renameTo(new File(pp + changString(f.getName(), map)));
			}
		}
	}

	private static String changString(String s, Map<String, String> map) {
		Set<Entry<String, String>> set = map.entrySet();
		for (Entry<String, String> e : set) {
			s = s.replace(e.getKey(), e.getValue());
		}
		return s;
	}

	/**
	 * 删除文件夹下一定规则的文件
	 *
	 * @param logoPath
	 *            文件夹路径
	 */
	public static void deleteSamePic(String logoPath) {
		File root = new File(logoPath);
		File[] files = root.listFiles();
		for (File f : files) {
			String pathName = f.getName();
			File[] _files = f.listFiles();
			for (File ff : _files) {
				String filename = ff.getName();
				if (filename.contains("黑色")) {
					String pp = logoPath + "\\" + pathName + "\\" + filename;
					delFile(pp);
				}
			}
		}
	}

	/**
	 * 删除某个文件夹
	 *
	 * @param path
	 *            文件夹路径
	 * @param delRoot
	 *            true：删除文件夹，false：只删除文件夹里面的文件
	 */
	public static void delFolder(String path, boolean delRoot) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempFileList = file.list();
		File tempFile = null;
		for (int i = 0; i < tempFileList.length; i++) {
			if (path.endsWith(File.separator)) {
				tempFile = new File(path + tempFileList[i]);
			} else {
				tempFile = new File(path + File.separator + tempFileList[i]);
			}
			if (tempFile.isFile()) {
				tempFile.delete();
				System.out.println("删除文件：" + tempFile.getPath());
			} else if (tempFile.isDirectory()) {
				delFolder(path + "/" + tempFileList[i], true);// 递归调用
			}
		}
		if (delRoot) {
			file.delete();
			System.out.println("删除文件夹：" + file.getPath());
		}
	}

	/**
	 * 删除某个文件
	 *
	 * @param path
	 *            要删除的文件
	 */
	public static boolean delFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("文件不存在");
			return false;
		}
		try {
			if (file.isFile()) {
				return file.delete();
			}
		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取图片的宽度
	 *
	 * @param file
	 *            图片地址
	 */
	public static int getPicWidth(File file) {
		try {
			return ImageIO.read(new FileInputStream(file)).getWidth();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取图片的高度
	 *
	 * @param file
	 *            图片地址
	 */
	public static int getPicHeight(File file) {
		try {
			return ImageIO.read(new FileInputStream(file)).getHeight();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 新建文件
	 *
	 * @param 文件路径
	 */
	public static void newFilePath(String path) {
		try {
			File newFile = new File(path.toString());
			if (!newFile.exists()) {
				newFile.mkdirs();
				System.out.println("创建目录" + path);
			}
		} catch (Exception e) {
			System.out.println("新建目录操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 *
	 * @param 文件路径
	 */
	public static void newFilePathByFile(String filename) {
		filename = filename.replace(filename.split("\\\\")[filename.split("\\\\").length - 1], "");
		newFilePath(filename);
	}

	/*
	 * 打开文件夹
	 *
	 * @param path 要打开的文件夹的路径
	 */
	public static void openFile(String path) {
		try {
			Runtime.getRuntime().exec("cmd /c start explorer " + path);
		} catch (IOException e) {
			System.err.println("打开" + path + "文件失败");
			e.printStackTrace();
		}
	}
	
	/*
	 * 打开文件
	 */
	public static void openFile2(String path) {
		try {
			Runtime.getRuntime().exec("cmd /c start " + path);
		} catch (IOException e) {
			System.err.println("打开" + path + "文件失败");
			e.printStackTrace();
		}
	}

	/*
	 * 读取配置文件的所有信息
	 *
	 * @param filePath 配置文件所在路径
	 *
	 * @return 配置文件的键值对信息
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> readProperties(String filePath) {
		Properties props = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try {
			props.load(new BufferedInputStream(new FileInputStream(filePath)));
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				map.put(key, Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	/*
	 * 往配置文件写东西
	 *
	 * @param filePath 配置文件所在路径
	 *
	 * @param parameterName 写入配置文件的key
	 *
	 * @param parameterValue 写入配置文件的value
	 */
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			prop.setProperty(parameterName, parameterValue);
			prop.store(new FileOutputStream(filePath), null);
		} catch (IOException e) {
			System.err.println("写入出错");
		}
	}

	/*
	 * 根据key删除一条记录
	 *
	 * @param PropPath 配置文件所在路径
	 *
	 * @param name property的key
	 */
	@SuppressWarnings("rawtypes")
	public static void deleteFile(String PropPath, String name) {
		Properties props = new Properties();
		Map<String, String> map = new HashMap<String, String>();
		try {
			props.load(new BufferedInputStream(new FileInputStream(PropPath)));
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				map.put(key, Property);
			}
			props.clear();
			map.remove(name);
			props.putAll(map);
			props.store(new FileOutputStream(PropPath), null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 根据key读取一条记录的value
	 *
	 * @param PropPath 配置文件所在路径
	 *
	 * @param name 记录的的key
	 *
	 * @return 根据key得到的value
	 */
	@SuppressWarnings("rawtypes")
	public static String getValueByKey(String PropPath, String name) {
		Properties props = new Properties();
		try {
			props.load(new BufferedInputStream(new FileInputStream(PropPath)));
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				if (key.equals(name))
					return props.getProperty(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 清空配置文件
	 *
	 * @param Path 配置文件路径
	 */
	public static void clearProperties(String Path) {
		try {
			FileOutputStream testfile = new FileOutputStream(Path);
			testfile.write(new String("").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 热键缩放到系统托盘(现只实现缩到任务栏,无法实现从托盘放大,因为后台无法监听键盘事件)
	 *
	 * @param m 要缩放的容器
	 *
	 * @param trayIcon 系统托盘
	 */
	public static void zoomMainFrame(JFrame m, TrayIcon trayIcon) {
		int state = m.getExtendedState();
		if (state == 0) {
			try {
				SystemTray.getSystemTray().add(trayIcon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
			m.setVisible(false);
		}
	}

	/*
	 * 格式化键盘
	 *
	 * @param s 系统的出的键盘key
	 *
	 * @return 格式化后的键盘Key(让Zz==Z,Back Quote`==Back Quote)
	 */
	public static String formatKeytext(String s) {
		int length = s.length();
		if (length == 2) {
			String ups = s.toUpperCase();
			if (ups.charAt(0) == ups.charAt(1)) {
				return String.valueOf(ups.charAt(0));
			}
		} else if (length == 11) {
			if (s.equals("Back Quote`")) {
				return "Back Quote";
			}
		}
		return s;
	}

	/*
	 * 运行exe程序
	 *
	 * @param path 要运行的exe程序路径
	 */
	public static void exeRun(String path) {
		try {
			Runtime.getRuntime().exec(path);
		} catch (IOException e) {
			System.out.println("要运行的程序找不到");
			e.printStackTrace();
		}

	}

	/*
	 * 得到字符串的width长度
	 *
	 * @param strValue 字符串数据
	 *
	 * @param font 字体
	 *
	 * @result 一个map数据,其中width key对应width,height key对应height
	 */
	public static Map<String, Integer> getStrigWidthAndHeight(String strValue, Font font) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		FontMetrics fm = new JLabel().getFontMetrics(font);
		String tmpChar = "";
		int chr_width = 0;
		int totalWidth = 0;
		int chr_height = 0;
		int totalHeight = 0;
		for (int i = 0, len = strValue.length(); i < len; i++) {
			tmpChar = strValue.substring(i, i + 1);
			chr_width = fm.stringWidth(tmpChar);
			chr_height = fm.getHeight();
			totalHeight = chr_height > totalHeight ? chr_height : totalHeight;
			totalWidth += chr_width;
		}
		map.put("width", totalWidth);
		map.put("height", totalHeight);
		return map;
	}

	/**
	 * 检查颜色输入框
	 * 
	 * @param r
	 *            输入值
	 */
	public static boolean checkRGBInetger(String r) {
		Integer i;
		try {
			i = Integer.parseInt(r);
		} catch (Exception e) {
			return false;
		}
		if (i >= 0 && i <= 255)
			return true;
		return false;
	}

	/**
	 * 检查地址输入框
	 * 
	 * @param String...
	 *            输入值
	 */
	public static boolean checkPath(String... strings) {
		for (String s : strings) {
			if (!checkPath(s))
				return false;
		}
		return true;
	}

	public static boolean checkPath(String text) {
		if (text == null || text.equals(""))
			return false;
		return true;
	}

	/**
	 * 检查是否是数字
	 * 
	 * @param r
	 *            输出值
	 */
	public static boolean checkInetger(String r) {
		try {
			Integer.parseInt(r);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 自动在字符串后面补充空格
	 * 
	 * @param 字符串
	 * @param 最大长度（若是超过一倍则补充到2倍）
	 */
	public static String autoCompleWithSpace(String string, int i) {
		Integer width = getStrigWidthAndHeight(string, new Font("宋体", Font.PLAIN, 10)).get("width");
		if (width <= i) {
			for (int n = width; n <= i; n += 5) {
				string += " ";
			}
		} else {
			for (int n = width; n <= 2 * i; n += 5) {
				string += " ";
			}
		}
		return string;
	}

	/**
	 * 书写日志信息
	 */
	public static void log(String file, String msg) {
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter fw = new BufferedWriter(new FileWriter(f, true));
			fw.write(msg);
			fw.newLine();
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void logNoLine(String file, String msg) {
		try {
			File f = new File(file);
			if (!f.exists()) {
				f.createNewFile();
			}
			BufferedWriter fw = new BufferedWriter(new FileWriter(f, true));
			fw.write(msg);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
