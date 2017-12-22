package com.wt.fastgo_user.widgets;

import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final int DEFAULT_MAX_SIZE = 10 * 1024 * 2014;

	public static String md5(final String s) {
		final String MD5 = "MD5";
		try {
			// Create MD5 Hash
			MessageDigest digest = MessageDigest.getInstance(MD5);
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuilder hexString = new StringBuilder();
			for (byte aMessageDigest : messageDigest) {
				String h = Integer.toHexString(0xFF & aMessageDigest);
				while (h.length() < 2) {
					h = "0" + h;
				}
				hexString.append(h);
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String md5(InputStream in, int max_size) {
		try {
			MessageDigest md5er = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024];
			int read;
			int totalRead = 0;
			do {
				read = in.read(buffer);
				totalRead += read;
				if (totalRead > max_size) {
					break;
				}
				if (read > 0) {
					md5er.update(buffer, 0, read);
				}
			} while (read != -1);
			// in.close();
			byte[] digest = md5er.digest();
			if (digest == null)
				return null;
			String strDigest = "0x";
			for (int i = 0; i < digest.length; i++) {
				strDigest += Integer.toString((digest[i] & 0xff) + 0x100, 16)
						.substring(1).toUpperCase();
			}
			return strDigest;
		} catch (Exception e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String md5(InputStream in) {
		return md5(in, DEFAULT_MAX_SIZE);
	}

	public static String md5(File file) {
		String content = String.valueOf(System.currentTimeMillis());
		try {
			content = file.getAbsolutePath() + file.lastModified()
					+ file.length();
		} catch (Exception ex) {
		}

		return md5(content);
	}

	public static String inputStream2String(InputStream inputStream,
			String encoding) throws IOException {
		return new String(inputStream2bytes(inputStream), encoding);
	}

	public static String inputStream2String(InputStream inputStream)
			throws IOException {
		return new String(inputStream2bytes(inputStream),
				Charset.defaultCharset());
	}

	private static byte[] inputStream2bytes(InputStream inputStream)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		while ((length = inputStream.read(buffer)) != -1) {
			baos.write(buffer, 0, length);
		}
		return baos.toByteArray();
	}

	public static String null2String(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		return str;
	}

	/**
	 * 获取字符串个数（汉字和全角字符算1个，字母数字0.5个）
	 * 
	 * @param s
	 * @return
	 */
	public static int getWordCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			// 如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255
			if (ascii >= 0 && ascii <= 255) {
				length++;
			} else {
				length += 2;
			}
		}
		return length / 2;
	}

	public static String encodeEmojis(String s) {
		if (TextUtils.isEmpty(s)) {
			s = "";
		}
		String reg = "(\\ud83c[\\udf00-\\udfff])|(\\ud83d[\\udc00-\\ude4f])|(\\ud83d[\\ude80-\\udeff])";
		if (!s.matches(reg)) {
			return s;
		}
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, URLEncoder.encode(m.group(1)));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static String decodeEmojis(String s) {
		if (TextUtils.isEmpty(s)) {
			s = "";
		}
		String reg = "(\\ud83c[\\udf00-\\udfff])|(\\ud83d[\\udc00-\\ude4f])|(\\ud83d[\\ude80-\\udeff])";
		if (!s.matches(reg)) {
			return s;
		}
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(s);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, URLDecoder.decode(m.group(1)));
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
