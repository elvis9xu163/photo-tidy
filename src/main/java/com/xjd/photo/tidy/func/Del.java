package com.xjd.photo.tidy.func;

import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import com.xjd.photo.tidy.Config;

/**
 * @author elvis.xu
 * @since 2017-06-30 14:55
 */
public class Del {
	Config config;

	List<String> list = new LinkedList<String>();

	public Del(Config config) {
		this.config = config;
	}

	public void execute() {
		File file = new File(config.getDel().getFile());

		try {
			readDel(file);
		} catch (IOException e) {
			System.out.println("读取删除文件列表异常!");
			e.printStackTrace();
			return;
		}

		System.out.print("删除文件开始: " + list.size());
		exeDel();
	}

	protected void readDel(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("utf8")));
		String line = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.length() == 0 || '#' == line.charAt(0)) {
				continue;
			}
			list.add(line);
		}
		reader.close();
	}

	protected void exeDel() {
		for (String s : list) {
			File f = new File(s);
			if (!f.isFile()) {
				System.out.println("文件不存在或不是文件: " + s);
				continue;
			}
			if (!f.delete()) {
				System.out.println("文件删除失败: " + s);
			}
		}
	}

}
