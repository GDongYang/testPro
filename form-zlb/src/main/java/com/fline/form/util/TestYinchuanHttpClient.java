package com.fline.form.util;

import sun.security.provider.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TestYinchuanHttpClient {


    private static String bcardNO = "92640103MA764Q45J";
    private static String bType = "2";
    private static String apId = "yztb";
    private static String secretKey = "fgdWrkYZJeFFbmMn";

    private static  String explain = bcardNO+bType+apId+secretKey;

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String encryption = encryption(explain);
        System.out.println(encryption);

    }


    public static String encryption(String plainText) throws NoSuchAlgorithmException {

        String re_md5 = "";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes());
        byte b[] = md.digest();

        int i;
        StringBuffer buf = new StringBuffer("");
        for(int offset = 0;offset<b.length;offset++){
            i = b[offset];
            if(i<0)
                i+=256;
            if(i<16)
                buf.append(Integer.toHexString(i));
            buf.append(Integer.toHexString(i));
        }

        re_md5 = buf.toString();

        return re_md5;
    }
}
