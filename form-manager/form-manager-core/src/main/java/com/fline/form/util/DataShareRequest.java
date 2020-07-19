package com.fline.form.util;

import com.feixian.tp.common.util.JsonUtil;
import com.fline.form.vo.CertificateResult;
import com.fline.form.vo.RequestParam;
import com.fline.form.vo.ResponseResult;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataShareRequest {

    private static final String USERNAME = "hz_acc_0007";
    private static final String SECRET = "e10adc3949ba59abbe56e057f20f883e";
    private static final String CERTIFICATE_URL = "http://127.0.0.1:8800/yztb/rest/certificate";
    private static final String CERTIFICATE_FILE_URL = "http://127.0.0.1:8800/yztb/rest/certificate/file";
    private static final String APPLICANT_UNIT = "省一证通办";

    public static ResponseResult<List<CertificateResult>> getCertificateData(RequestParam param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(CERTIFICATE_URL);

        String nonce = UUID.randomUUID().toString();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String created = sdf.format(new Date());
        String passwdDigest = MD5Util.MD5(nonce + "_" + created + "_" + SECRET);

        httppost.addHeader("Username", USERNAME);
        httppost.addHeader("PasswdDigest", passwdDigest);
        httppost.addHeader("Nonce", nonce);
        httppost.addHeader("Created", created);
        try {
            httppost.addHeader("ApplicantUnit", URLEncoder.encode(APPLICANT_UNIT, "UTF-8"));
            httppost.addHeader("ApplicantUser",URLEncoder.encode(param.getApplicantUser(), "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }

        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("cerNo", param.getCerNo()));
        formparams.add(new BasicNameValuePair("cerName", param.getCerName()));
        formparams.add(new BasicNameValuePair("itemCode", param.getItemCode()));
        formparams.add(new BasicNameValuePair("otherParam", "{\"idCard\":\"" + param.getCerNo() + "+\"}"));
        //formparams.add(new BasicNameValuePair("otherParam", "{\"fields\":\"\",\"condition\":\"ID.in.('3')\",\"pageSize\":\"10\",\"pageNum\":\"0\"}"));
        UrlEncodedFormEntity uefEntity;
        ResponseResult<List<CertificateResult>> result = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultString = EntityUtils.toString(entity, "UTF-8");
                    result = JsonUtil.unmarshal(resultString, new TypeReference<ResponseResult<List<CertificateResult>>>(){});
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static ResponseResult<CertificateResult> getCertificateFile(RequestParam param) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(CERTIFICATE_FILE_URL);

        String nonce = UUID.randomUUID().toString();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String created = sdf.format(new Date());
        String passwdDigest = MD5Util.MD5(nonce + "_" + created + "_" + SECRET);

        httppost.addHeader("Username", USERNAME);
        httppost.addHeader("PasswdDigest", passwdDigest);
        httppost.addHeader("Nonce", nonce);
        httppost.addHeader("Created", created);
        try {
            httppost.addHeader("ApplicantUnit", URLEncoder.encode(APPLICANT_UNIT, "UTF-8"));
            httppost.addHeader("ApplicantUser",URLEncoder.encode(param.getApplicantUser(), "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }

        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("cerNo", param.getCerNo()));
        formparams.add(new BasicNameValuePair("cerName", param.getCerName()));
        formparams.add(new BasicNameValuePair("itemCode", param.getItemCode()));
        formparams.add(new BasicNameValuePair("certCode", param.getCertCode()));
        formparams.add(new BasicNameValuePair("otherParam", "{\"idCard\":\"" + param.getCerNo() + "+\"}"));
        //formparams.add(new BasicNameValuePair("otherParam", "{\"fields\":\"\",\"condition\":\"ID.in.('3')\",\"pageSize\":\"10\",\"pageNum\":\"0\"}"));
        UrlEncodedFormEntity uefEntity;
        ResponseResult<CertificateResult> result = null;
        try {
            uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            httppost.setEntity(uefEntity);
            System.out.println("executing request " + httppost.getURI());
            CloseableHttpResponse response = httpClient.execute(httppost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String resultString = EntityUtils.toString(entity, "UTF-8");
                    result = JsonUtil.unmarshal(resultString, new TypeReference<ResponseResult<CertificateResult>>() {});
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
