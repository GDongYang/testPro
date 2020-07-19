package com.fline.form.constant;

import org.springframework.util.ClassUtils;

public class Constant {

    public final static String INTERFACE_VERIFY = "interface_verify";
    public final static String INTERFACE_LOADDATA = "interface_loaddata";
    public final static String INTERFACE_SUBMIT = "interface_submit";
    public final static String PHOTO_CODE = "POLICEU0022S00036";

    public static final String FDP_FILE_KEY = "tz_attr";


    public static final String SIGN_FLAG = "加盖电子公章";

    public final static String PERSON = "tz_gaj_cer_052";
    public final static String ENTERPRISE = "tz_gsj_cer_572";

    public final static String REAL_PATH = ClassUtils.getDefaultClassLoader().getResource("").getPath();
    public final static String FONT_PATH = "fonts/STSONG.TTF";
    public final static String TEMPLATES_PATH = "templates/";

    public final static String TERMINAL_KEY = "KamfuIDDataSign";
    public final static String OFFLINE_KEY = "ac5fa6776ee84728a7456311d19a4880";

    public final static String PERSON_TYPE = "31";
    public final static String COMPANY_TYPE = "51";

    public final static String COMMON_FORM_CODE = "tz_form_849";
    public final static String COMMON_PERSON_FORM_CODE = "zjs_form_844";
    public final static String COMMON_COMPANY_FORM_CODE = "zjs_form_814";
    public final static String COMMON_APPLICATION_FORM = "tz_cer_452";

    public final static int THREAD_POOL_TIME_OUT = 15;

    public static final String FORM_BUSI_CODE = "formBusiCode";
    public static final String FORM_BIZ_INFO = "formBizInfo";

}
