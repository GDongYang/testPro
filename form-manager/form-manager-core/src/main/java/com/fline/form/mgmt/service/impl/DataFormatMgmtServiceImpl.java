package com.fline.form.mgmt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.feixian.tp.cache.Cache;
import com.feixian.tp.common.util.Detect;
import com.fline.form.constant.Contants;
import com.fline.form.mgmt.service.DataFormatMgmtService;
import com.fline.form.util.HttpRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("dataFormatMgmtService")
public class DataFormatMgmtServiceImpl  implements DataFormatMgmtService {

    public static final String context = "/FieldFormat";
    @Value("${dataFormat.url}")
    private String dataFormatUrl;
    @Resource
    private Cache cache;

	@Override
    public String findAddress(String parentId) {
        return findByParent("CZA0001", parentId);
    }

    @Override
    public Map<String, String> findIndustry() {
        return findValues("CZA0005");
    }

    @Override
    public String findBureau() {
        return findByParent("CZA0008", "330000");
    }

    @Override
    public String findPagenation(String pageNum, String pageSize) {
        String url = dataFormatUrl + context;
        String result = HttpRequest.sendPost(url, "pageNum=" + pageNum + "&pageSize=" + pageSize);
        return result;
    }


    @Override
    public String findById(String dataId) {
        String url = dataFormatUrl + context + "view/" + dataId;
        String result = HttpRequest.sendGet(url, null);
        return result;
    }

    @Override
    public String search(String param) {
        String url = dataFormatUrl + context + "/search";
        String result = HttpRequest.sendPost(url, param);
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, String> findValues(String dataId) {
        String key = Contants.DATA_FORMAT_CACHE + dataId;
        if (!cache.contains(key)) {
            String url = dataFormatUrl + context + "/value";
            String result = HttpRequest.sendGet(url, "fieldFormatID=" + dataId);
            cacheValues(dataId, result);
        }
        return (Map<String, String>)cache.get(key);
    }

    @Override
    public String findByParent(String formateId, String parentId) {
        String url = dataFormatUrl + context+"/value/findByParent";
        String result = HttpRequest.sendPost(url, "fieldFormatID=" + formateId + "&parentKey=" + parentId);
        return result;
    }

    @Override
    public String findAllByParent(String formateId, String parentId) {
        String url = dataFormatUrl + context+"/value/findAllByParent";
        String result = HttpRequest.sendPost(url, "fieldFormatID=" + formateId + "&parentKey=" + parentId);
        return result;
    }

    @Override
    public String findEntType(String parentId) {
        return findByParent("CZA0012", parentId);
    }

    private void cacheValues(String dataId, String valueStr) {
        JSONArray arr = JSONArray.fromObject(valueStr);
        int size = arr.size();
        Map<String, String> map = new HashMap<String, String>();
        for (int ii = 0; ii < size; ii ++) {
            JSONObject jb = (JSONObject)arr.get(ii);
            map.put(jb.getString("key"), jb.getString("value"));
        }
        cache.put(Contants.DATA_FORMAT_CACHE + dataId, 1000000 * 60, map);
    }

    @Override
    public String getFormateValue(String dataId, String key) {
        Map<String, String> values = findValues(dataId);
        return values.get(key);
    }

   /* @Override
    public void setDataFormatText(Object item) {
        if (item == null) {
            return;
        }
        Class<?> itemClass = item.getClass();
        Field[] fields = itemClass.getDeclaredFields();

        int length = fields.length;
        for (int i = 0; i < length; i ++) {
            Field field = fields[i];
            if(!field.isAccessible())
                field.setAccessible(true);
            if(field.isAnnotationPresent(DataFormatText.class)) {
                DataFormatText dataFormatText = (DataFormatText)field.getAnnotation(DataFormatText.class);
                String code = dataFormatText.code();
                String keyField = dataFormatText.keyField();
                try {
                    Field kField = itemClass.getDeclaredField(keyField);
                    if(!kField.isAccessible())
                        kField.setAccessible(true);
                    String key = (String)kField.get(item);
                    String dfText = getFormateValue(code,key);
                    field.set(item, dfText);
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
*/
    @Override
    public List<?> setDataFormatText(List<?> items) {
        if (Detect.notEmpty(items)) {
            for (Object item : items) {
                setDataFormatText(item);
            }
        }
        return null;
    }


	@Override
	public String findCategoryCode(String param) {
		return null;
	}

	@Override
	public void setDataFormatText(Object item) {	
	}
}
