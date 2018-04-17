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
	 * ��һ���Ĺ�������ļ����µ��ļ�����
	 *
	 * @param logoPath
	 *            �ļ���·��
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
									if (!ffff.isDirectory()) {// -----------�Ĳ�
										String pp = logoPath + "\\" + pathName1 + "\\" + pathName2 + "\\" + pathName3
												+ "\\";
										new File(pp + ffff.getName())
												.renameTo(new File(pp + changString(ffff.getName(), map)));
									}
								}
							} else {// -----------����
								String pp = logoPath + "\\" + pathName1 + "\\" + pathName2 + "\\";
								new File(pp + fff.getName()).renameTo(new File(pp + changString(fff.getName(), map)));
							}
						}
					} else {// -----------����
						String pp = logoPath + "\\" + pathName1 + "\\";
						new File(pp + ff.getName()).renameTo(new File(pp + changString(ff.getName(), map)));
					}
				}
			} else {// -----------һ��
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
	 * ɾ���ļ�����һ��������ļ�
	 *
	 * @param logoPath
	 *            �ļ���·��
	 */
	public static void deleteSamePic(String logoPath) {
		File root = new File(logoPath);
		File[] files = root.listFiles();
		for (File f : files) {
			String pathName = f.getName();
			File[] _files = f.listFiles();
			for (File ff : _files) {
				String filename = ff.getName();
				if (filename.contains("��ɫ")) {
					String pp = logoPath + "\\" + pathName + "\\" + filename;
					delFile(pp);
				}
			}
		}
	}

	/**
	 * ɾ��ĳ���ļ���
	 *
	 * @param path
	 *            �ļ���·��
	 * @param delRoot
	 *            true��ɾ���ļ��У�false��ֻɾ���ļ���������ļ�
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
				System.out.println("ɾ���ļ���" + tempFile.getPath());
			} else if (tempFile.isDirectory()) {
				delFolder(path + "/" + tempFileList[i], true);// �ݹ����
			}
		}
		if (delRoot) {
			file.delete();
			System.out.println("ɾ���ļ��У�" + file.getPath());
		}
	}

	/**
	 * ɾ��ĳ���ļ�
	 *
	 * @param path
	 *            Ҫɾ�����ļ�
	 */
	public static boolean delFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("�ļ�������");
			return false;
		}
		try {
			if (file.isFile()) {
				return file.delete();
			}
		} catch (Exception e) {
			System.out.println("ɾ���ļ���������");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡͼƬ�Ŀ��
	 *
	 * @param file
	 *            ͼƬ��ַ
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
	 * ��ȡͼƬ�ĸ߶�
	 *
	 * @param file
	 *            ͼƬ��ַ
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
	 * �½��ļ�
	 *
	 * @param �ļ�·��
	 */
	public static void newFilePath(String path) {
		try {
			File newFile = new File(path.toString());
			if (!newFile.exists()) {
				newFile.mkdirs();
				System.out.println("����Ŀ¼" + path);
			}
		} catch (Exception e) {
			System.out.println("�½�Ŀ¼��������");
			e.printStackTrace();
		}
	}

	/**
	 * �½��ļ�
	 *
	 * @param �ļ�·��
	 */
	public static void newFilePathByFile(String filename) {
		filename = filename.replace(filename.split("\\\\")[filename.split("\\\\").length - 1], "");
		newFilePath(filename);
	}

	/*
	 * ���ļ���
	 *
	 * @param path Ҫ�򿪵��ļ��е�·��
	 */
	public static void openFile(String path) {
		try {
			Runtime.getRuntime().exec("cmd /c start explorer " + path);
		} catch (IOException e) {
			System.err.println("��" + path + "�ļ�ʧ��");
			e.printStackTrace();
		}
	}
	
	/*
	 * ���ļ�
	 */
	public static void openFile2(String path) {
		try {
			Runtime.getRuntime().exec("cmd /c start " + path);
		} catch (IOException e) {
			System.err.println("��" + path + "�ļ�ʧ��");
			e.printStackTrace();
		}
	}

	/*
	 * ��ȡ�����ļ���������Ϣ
	 *
	 * @param filePath �����ļ�����·��
	 *
	 * @return �����ļ��ļ�ֵ����Ϣ
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
	 * �������ļ�д����
	 *
	 * @param filePath �����ļ�����·��
	 *
	 * @param parameterName д�������ļ���key
	 *
	 * @param parameterValue д�������ļ���value
	 */
	public static void writeProperties(String filePath, String parameterName, String parameterValue) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			prop.setProperty(parameterName, parameterValue);
			prop.store(new FileOutputStream(filePath), null);
		} catch (IOException e) {
			System.err.println("д�����");
		}
	}

	/*
	 * ����keyɾ��һ����¼
	 *
	 * @param PropPath �����ļ�����·��
	 *
	 * @param name property��key
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
	 * ����key��ȡһ����¼��value
	 *
	 * @param PropPath �����ļ�����·��
	 *
	 * @param name ��¼�ĵ�key
	 *
	 * @return ����key�õ���value
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
	 * ��������ļ�
	 *
	 * @param Path �����ļ�·��
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
	 * �ȼ����ŵ�ϵͳ����(��ֻʵ������������,�޷�ʵ�ִ����̷Ŵ�,��Ϊ��̨�޷����������¼�)
	 *
	 * @param m Ҫ���ŵ�����
	 *
	 * @param trayIcon ϵͳ����
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
	 * ��ʽ������
	 *
	 * @param s ϵͳ�ĳ��ļ���key
	 *
	 * @return ��ʽ����ļ���Key(��Zz==Z,Back Quote`==Back Quote)
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
	 * ����exe����
	 *
	 * @param path Ҫ���е�exe����·��
	 */
	public static void exeRun(String path) {
		try {
			Runtime.getRuntime().exec(path);
		} catch (IOException e) {
			System.out.println("Ҫ���еĳ����Ҳ���");
			e.printStackTrace();
		}

	}

	/*
	 * �õ��ַ�����width����
	 *
	 * @param strValue �ַ�������
	 *
	 * @param font ����
	 *
	 * @result һ��map����,����width key��Ӧwidth,height key��Ӧheight
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
	 * �����ɫ�����
	 * 
	 * @param r
	 *            ����ֵ
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
	 * ����ַ�����
	 * 
	 * @param String...
	 *            ����ֵ
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
	 * ����Ƿ�������
	 * 
	 * @param r
	 *            ���ֵ
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
	 * �Զ����ַ������油��ո�
	 * 
	 * @param �ַ���
	 * @param ��󳤶ȣ����ǳ���һ���򲹳䵽2����
	 */
	public static String autoCompleWithSpace(String string, int i) {
		Integer width = getStrigWidthAndHeight(string, new Font("����", Font.PLAIN, 10)).get("width");
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
	 * ��д��־��Ϣ
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
