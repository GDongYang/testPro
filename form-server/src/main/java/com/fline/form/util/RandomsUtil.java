package com.fline.form.util;

import org.python.antlr.ast.For;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 随机生成七位数流水号
 * Created by Administrator on 2020/4/14.
 */
public class RandomsUtil {
    public final static String getRandom() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            str.append(random.nextInt(10));
        }
        int num = Integer.parseInt(str.toString());
//        System.out.println(num);
        String stirng = Integer.toString(num);
        return stirng;
    }

    public final static String getProjid() {

        SimpleDateFormat format = new SimpleDateFormat("yyMMdd");
        String batchno = format.format(new Date());
//        System.out.println(batchno);

        String projid = "64010001" + batchno + "01" + getRandom();
        return projid;
    }

    public static void main(String[] args) {
        System.out.println(getRandom());
        System.out.println(getProjid());
    }
}
