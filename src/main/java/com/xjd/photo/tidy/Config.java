package com.xjd.photo.tidy;

/**
 * @author elvis.xu
 * @since 2017-06-30 14:35
 */
public class Config {
	private Repeat repeat = new Repeat();
	private Sync sync = new Sync();
	private Del del = new Del();

	public Repeat getRepeat() {
		return repeat;
	}

	public void setRepeat(Repeat repeat) {
		this.repeat = repeat;
	}

	public Sync getSync() {
		return sync;
	}

	public void setSync(Sync sync) {
		this.sync = sync;
	}

	public Del getDel() {
		return del;
	}

	public void setDel(Del del) {
		this.del = del;
	}

	public static class Repeat {
		private String dir;
		private String out;

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}

		public String getOut() {
			return out;
		}

		public void setOut(String out) {
			this.out = out;
		}
	}

	public static class Sync {
		private String dirA;
		private String dirB;
		private String out;

		public String getDirA() {
			return dirA;
		}

		public void setDirA(String dirA) {
			this.dirA = dirA;
		}

		public String getDirB() {
			return dirB;
		}

		public void setDirB(String dirB) {
			this.dirB = dirB;
		}

		public String getOut() {
			return out;
		}

		public void setOut(String out) {
			this.out = out;
		}
	}

	public static class Del {
		private String file;

		public String getFile() {
			return file;
		}

		public void setFile(String file) {
			this.file = file;
		}
	}
}
