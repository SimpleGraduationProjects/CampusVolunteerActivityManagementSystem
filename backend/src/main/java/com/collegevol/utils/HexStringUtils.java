package com.collegevol.utils;

import java.io.ByteArrayOutputStream;

/**
 * 16进制转换
 *
 */
public class HexStringUtils {

	/** 16进制数字字符集 */
	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将普通字符串转换为16进制字符串,不支持中文
	 * 
	 * @param s
	 * @return
	 */
	public static String string2HexSimple(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	/**
	 * 将16进制字符串转换为普通字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String hex2String(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 * 
	 * @param str
	 * @return
	 */
	public static String string2strHex(String str) {
		return bytes2strHex(str.getBytes());
	}

	/**
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytes2strHex(byte[] bytes) {
		// 根据默认编码获取字节数组
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将16进制数字解码成字符串，适用于所有字符（包括中文）
	 * 
	 * @param bytes
	 * @return
	 */
	public static String strHex2String(String strHex) {
		return new String(strHex2Bytes(strHex));
	}

	/**
	 * 将16进制数字解码成byte数组，适用于所有字符（包括中文）
	 * 
	 * @param bytes
	 * @return
	 */
	public static byte[] strHex2Bytes(String strHex) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(strHex.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < strHex.length(); i += 2) {
			baos.write((hexString.indexOf(strHex.charAt(i)) << 4 | hexString.indexOf(strHex.charAt(i + 1))));
		}
		return baos.toByteArray();
	}

	public static byte[] hexStr2Bytes(String src) {  
	    int m = 0, n = 0;  
	    int l = src.length() / 2;  
	   
	    byte[] ret = new byte[l];  
	    for (int i = 0; i < l; i++) {  
	        m = i * 2 + 1;  
	        n = m + 1;  
	        ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));  
	    }  
	    return ret;  
	}

	private static byte uniteBytes(String src0, String src1) {  
	    byte b0 = Byte.decode("0x" + src0).byteValue();  
	    b0 = (byte) (b0 << 4);  
	    byte b1 = Byte.decode("0x" + src1).byteValue();  
	    byte ret = (byte) (b0 | b1);  
	    return ret;  
	}
	
}
