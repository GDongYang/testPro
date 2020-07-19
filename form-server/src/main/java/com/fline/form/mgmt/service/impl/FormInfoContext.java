package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.ttl.TransmittableThreadLocal;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FormInfoContext {

    private static Log logger = LogFactory.getLog(FormInfoContext.class);

    private static final ThreadLocal<JSONObject> formInfoThreadLocal = new TransmittableThreadLocal<>();

    public static JSONObject get() {
        return formInfoThreadLocal.get();
    }

    public static void set(JSONObject formInfo) {
        logger.info("setFormInfoContext");
        formInfoThreadLocal.set(formInfo);
    }

    public static void remove() {
        logger.info("removeFormInfoContext");
        formInfoThreadLocal.remove();
    }
}
