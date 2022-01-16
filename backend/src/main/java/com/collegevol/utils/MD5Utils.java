package com.collegevol.utils;

import java.security.MessageDigest;

/**
 * 2012
 *
 * @author xgf
 */
public final class MD5Utils {

    private MD5Utils() {

    }

    /**
     * MD5后转16进制大写输出
     *
     * @param s
     * @return
     */
    public static String convert(String s) {
        return convert(s, true);
    }

    /**
     * MD5后转16进制大写输出
     *
     * @param s
     * @return
     */
    public static String convert(String s, boolean upperCase) {
        try {
            byte[] bytes = s.getBytes();
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            bytes = md.digest();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                int val = ((int) bytes[i]) & 0xff;
                if (val < 16)
                    sb.append("0");
                if (upperCase) {
                    sb.append(Integer.toHexString(val).toUpperCase());
                } else {
                    sb.append(Integer.toHexString(val));
                }

            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {

        System.out.println(MD5Utils.convert("123456"));
    }

}
