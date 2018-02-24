package com.xjd.photo.tidy;

import java.io.*;
import java.nio.charset.Charset;

import com.xjd.photo.tidy.func.Del;
import com.xjd.photo.tidy.func.Reapeat;

/**
 * @author elvis.xu
 * @since 2017-06-30 14:16
 */
public class Main {
	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.out.println("please use 'repeat' or 'sync' or 'del' as command arg");
			return;
		}

		Config config = null;
		try {
			config = loadConfig();
		} catch (IOException e) {
			System.out.println("load config failed!");
			e.printStackTrace();
			return;
		}

		if ("repeat".equalsIgnoreCase(args[0])) {
			new Reapeat(config).execute();

		} else if ("sync".equalsIgnoreCase(args[0])) {

		} else if ("del".equalsIgnoreCase(args[0])) {
			new Del(config).execute();

		} else {
			System.out.println("please use 'repeat' or 'sync' or 'del' as command arg");
			return;
		}
	}

	public static Config loadConfig() throws IOException {
		Config config = new Config();
		BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream("photo-tidy.cfg"), Charset.forName("utf8")));
		String line = null;
		while ((line = read.readLine()) != null) {
			line = line.trim();
			if (line.length() == 0 || '#' == line.charAt(0)) {
				continue;
			}

			String value = null;
			if ((value = readValue(line, "repeat.dir")) != null) {
				config.getRepeat().setDir(value);

			} else if ((value = readValue(line, "repeat.out")) != null) {
				config.getRepeat().setOut(value);

			} else if ((value = readValue(line, "sync.dir.A")) != null) {
				config.getSync().setDirA(value);

			} else if ((value = readValue(line, "sync.dir.B")) != null) {
				config.getSync().setDirB(value);

			} else if ((value = readValue(line, "sync.out")) != null) {
				config.getSync().setOut(value);

			} else if ((value = readValue(line, "del.file")) != null) {
				config.getDel().setFile(value);

			}
		}

		read.close();
		return config;
	}

	public static String readValue(String text, String param) {
		String[] split = text.split("=", 2);
		if (param.equalsIgnoreCase(split[0].trim())) {
			return split[1].trim();
		}
		return null;
	}
}
