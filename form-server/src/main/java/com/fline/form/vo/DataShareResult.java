package com.fline.form.vo;

import java.util.List;
import java.util.Map;

public class DataShareResult {

    private String requestId;

    private List<Map<String, Object>> dataList;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }
}
