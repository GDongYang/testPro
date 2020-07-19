package com.fline.form.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @BelongsProject: SGAT
 * @BelongsPackage: com.fline.yztb.util
 * @Author: jibing
 * @CreateTime: 2019-06-05 19:41
 * @Description: 对时间的处理
 */
public class DateUtil {

    /**
     * @Author jibing
     * @Date 19:42 2019/6/5
     * @Description  比较时间(true:一小时之内，false:一小时之外)
     **/
    public static boolean compare(String time1,String time2) throws ParseException {


        //比较日期可以写成"yyyy-MM-dd"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //将字符串形式的时间转化为Date类型的时间
        Date a = sdf.parse(time1);
        Date b = sdf.parse(time2);

        //根据将Date转换成毫秒，1小时=3600000毫秒
        if(a.getTime() - b.getTime() < 3600000) {
            return true;
        } else {
            return false;
        }
    }

}
