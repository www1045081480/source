package com.np.base.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	/*
	 * 文字列からダイジェスト(MD5)を生成する
	 */
	public static String digest(String data) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bs = (data == null) ? new byte[0] : data.getBytes();
			md.update(bs);
			return hashByte2MD5(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * MD5 ハッシュ関数
	 */
	private static String hashByte2MD5(byte[] hash) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

}
