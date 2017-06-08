package com.np.base.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class UFile {
	public static void ccheckPath(File path) {
		if (path.exists() == false)
			path.mkdirs();
	}

	public static void ccheckFilepath(File file) {
		File path = file.getParentFile();
		if (path == null)
			return;
		if (path.exists() == false)
			path.mkdirs();
	}

	public static boolean delete(File file) {
		if (file.canWrite() == false)
			return false;
		boolean ok = file.delete();
		return ok;
	}

	public static void validate(File path) {
		if (path.exists() == false)
			path.mkdirs();
	}

	@SuppressWarnings("resource")
	public static ByteBuffer read(File file) throws IOException {
		if (file.exists() == false)
			return ByteBuffer.allocate(0);
		ByteBuffer buf = ByteBuffer.allocate((int) file.length());
		FileChannel channel = new FileInputStream(file).getChannel();
		try {
			while (buf.hasRemaining()) {
				int len = channel.read(buf);
				if (len == -1)
					break;
			}
		} finally {
			channel.close();
		}
		buf.rewind();
		return buf;
	}

	@SuppressWarnings("resource")
	public static void write(File file, byte[] data) throws IOException {
		ByteBuffer buf = ByteBuffer.wrap(data);
		FileChannel channel = new FileOutputStream(file).getChannel();
		try {
			while (buf.hasRemaining()) {
				int len = channel.write(buf);
				if (len == -1)
					break;
			}
		} finally {
			channel.close();
		}
	}

}
