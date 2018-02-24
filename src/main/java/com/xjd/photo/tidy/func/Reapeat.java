package com.xjd.photo.tidy.func;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.xjd.photo.tidy.Config;
import com.xjd.photo.tidy.DigestUtil;

/**
 * @author elvis.xu
 * @since 2017-06-30 14:55
 */
public class Reapeat {
	Config config;

	volatile long time = 0;
	volatile int count = 0;
	Map<String, List<String>> map = new HashMap<String, List<String>>();

	public Reapeat(Config config) {
		this.config = config;
	}

	public void execute() {
		File dir = new File(config.getRepeat().getDir());
		config.getRepeat().setDir(dir.getAbsolutePath());
		System.out.println("开始查找重复文件: " + config.getRepeat().getDir());
		time = System.currentTimeMillis();
		exeDir(dir);
		printProcess();

		File out = new File(config.getRepeat().getOut());
		config.getRepeat().setOut(out.getAbsolutePath());
		System.out.println("开始输出重复文件: " + config.getRepeat().getOut());
		try {
			exeOut(out);
		} catch (IOException e) {
			System.out.println("输出重复文件异常!");
			e.printStackTrace();
		}
	}

	protected void exeDir(File dir) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				exeDir(file);

			} else if (file.isFile()) {
				exeFile(file);

			} else {
				System.out.println("无法识别文件类型: " + file.getAbsolutePath());
			}
		}
	}

	protected void exeFile(File file) {
		long length = file.length();
		String md5 = DigestUtil.digest(file, DigestUtil.MD5);
		String key = md5 + "_" + length;
		List<String> vals = map.get(key);
		if (vals == null) {
			vals = new LinkedList<String>();
			map.put(key, vals);
		}
		vals.add(file.getAbsolutePath());
		int i = ++count;
		if (i % 100 == 0) {
			printProcess();
		}
	}

	protected void printProcess() {
		long now = System.currentTimeMillis();
		System.out.println("processed file [" + (now - time) + "]: " + count);
		time = now;
	}

	protected void exeOut(File out) throws IOException {
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(out), Charset.forName("utf8")));
		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			if (entry.getValue().size() > 1) {
				writer.write("#####[" + entry.getKey() + "]#####");
				writer.newLine();
				boolean first = true;
				for (String val : entry.getValue()) {
					if (first) {
						writer.write("# ");
						first = false;
					} else {
						writer.write("  ");
					}
					writer.write(val);
					writer.newLine();
				}
				writer.newLine();
				writer.flush();
			}
		}
		writer.close();
	}
}
