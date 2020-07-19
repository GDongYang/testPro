package com.fline.request;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TestMd5 {
    public static void main(String[] args) {
        String secret = "e10adc3949ba59abbe56e057f20f883e";
        String nonce = UUID.randomUUID().toString();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String created = sdf.format(new Date());
        String passwdDigest = TestRequest.MD5(nonce + "_" + created + "_" + secret);
        System.out.println("nonce:"+nonce);
        System.out.println("created:"+created);
        System.out.println("passwdDigest:"+passwdDigest);

    }
}
