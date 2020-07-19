package com.fline.form.mgmt.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fline.form.mgmt.service.LegalSsoMgmtService;
import com.fline.form.util.DigestHelper;
import com.fline.form.util.SSLUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.LinkedHashMap;

@Service
public class LegalSsoMgmtServiceImpl implements LegalSsoMgmtService {

    @Value("${legal.query-url}")
    private String queryUrl;
    @Value("${legal.project-id}")
    private String projectId;
    @Value("${legal.project-secret}")
    private String projectSecret;

    @Override
    public JSONObject doQuery(String ssotoken) {
        JSONObject param = new JSONObject();
        param.put("token", ssotoken);

        // 获取请求签名值
        String signature = DigestHelper.getSignature(param.toString(), projectSecret);

        // 设置请求的Headers头信息
        LinkedHashMap<String, String> headers = new LinkedHashMap<String, String>();
        headers.put("x-esso-project-id", projectId);
        headers.put("x-esso-signature", signature);
        headers.put("Content-Type", "application/json");
        headers.put("Charset", "UTF-8");

        JSONObject jsonObj = sendPOST(queryUrl, param.toString(), headers);
        if (null != jsonObj) {
            int errCode = jsonObj.getIntValue("errCode");
            String msg = jsonObj.getString("msg");
            if (0 == errCode) {
                /* 用户信息 */
                String info = jsonObj.getString("info");
                System.out.println("用户信息  = " + info);
            } else {
                System.out.println("验证令牌并获取用户的登录信息失败:" + msg);
            }
        }
        return jsonObj;
    }

    /***
     * 向指定URL发送POST方法的请求
     *
     * @param apiUrl
     * @param data
     * @param headers
     * @return
     */
    private JSONObject sendPOST(String apiUrl, String data, LinkedHashMap<String, String> headers) {
        StringBuffer strBuffer = null;
        String result = null;
        JSONObject jsonObj = null;
        try {
            // 建立连接
            URL url = new URL(apiUrl);
            /* 获取客户端向服务器端传送数据所依据的协议名称 */
            String protocol = url.getProtocol();
            if ("https".equalsIgnoreCase(protocol)) {
                /* 获取HTTPS请求的SSL证书 */
                try {
                    SSLUtils.ignoreSsl();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 需要输出
            httpURLConnection.setDoOutput(true);
            // 需要输入
            httpURLConnection.setDoInput(true);
            // 不允许缓存
            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestMethod("POST");
            // 设置Headers
            if (null != headers) {
                for (String key : headers.keySet()) {
                    httpURLConnection.setRequestProperty(key, headers.get(key));
                }
            }
            // 连接会话
            httpURLConnection.connect();
            // 建立输入流，向指向的URL传入参数
            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
            // 设置请求参数
            dos.write(data.getBytes("UTF-8"));
            dos.flush();
            dos.close();
            // 获得响应状态
            int http_StatusCode = httpURLConnection.getResponseCode();
            String http_ResponseMessage = httpURLConnection.getResponseMessage();
            if (HttpURLConnection.HTTP_OK == http_StatusCode) {
                strBuffer = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
                while ((readLine = responseReader.readLine()) != null) {
                    strBuffer.append(readLine);
                }
                responseReader.close();
                result = strBuffer.toString();
                if (null == result || result.length() == 0) {
                    throw new Exception("获取企业（法人）信息失败");
                } else {
                    jsonObj = JSONObject.parseObject(result);
                }
            } else {
                throw new Exception(
                        MessageFormat.format("请求失败,失败原因: Http状态码 = {0} , {1}", http_StatusCode, http_ResponseMessage));
            }
            // 断开连接
            httpURLConnection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
