package com.fline.request;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;


@Controller
public class HttpUtil {//

    private static final String TAG = "HttpUtil";

    private static String sendHttpClientPOSTRequest(String path, String param, String encoding) throws IOException {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(param, encoding);
            HttpPost httpPost = new HttpPost(path);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
            httpPost.setHeader("Connection", "Keep-Alive");
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(entity);

            client = HttpClients.createDefault();
            response = client.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            /* 若状态码为200 ok */
            if (statusCode == 200) {
                InputStream inputStream = response.getEntity().getContent();
                byte[] dateStream = readStream(inputStream);
                return new String(dateStream);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != client) {
                client.close();
            }
        }
        return null;
    }

    /**
     * @param path   请求路径
     * @param params 请求参数
     * @return InputStream
     * @Description: 发送Post请求-编码格式默认 UTF-8
     * @Author:杨攀
     * @Since: 2014年8月4日下午12:24:34
     */
    public static String sendPOSTRequest(String path, Map<String, String> params) {
        return sendPOSTRequest(path, params, "UTF-8");
    }

    /**
     * @param path     请求路径
     * @param params   请求参数
     * @param encoding 编码
     * @return String  null 请求失败
     * @Description: 发送Post请求
     * @Author:杨攀
     * @Since: 2014年8月4日下午12:25:22
     */
    public static String sendPOSTRequest(String path, Map<String, String> params, String encoding) {
        InputStream inputStream = null;
        HttpURLConnection conn = null;
        try {
            // 组装 name=yangpan&age=28
            StringBuilder data = new StringBuilder();
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    data.append(entry.getKey()).append("=");
                    data.append(URLEncoder.encode(entry.getValue(), encoding));
                    data.append("&");
                }
                data.deleteCharAt(data.length() - 1);
            }
            byte[] entity = data.toString().getBytes();// 生成实体数据
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);// 设置超时
            conn.setRequestMethod("POST");
            // 允许对外输出数据
            conn.setDoOutput(true);
            // 设定传送的内容类型是可序列化的java对象
            // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", String.valueOf(entity.length));
            conn.setRequestProperty ("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");
            conn.setRequestProperty("AppKey", "3");
            conn.setRequestProperty("Connection", "Keep-Alive");
            OutputStream outStream = conn.getOutputStream();
            outStream.write(entity);
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                byte[] dateStream = readStream(inputStream);
                return new String(dateStream);
            } else {
            	System.out.println("第130行：ResponseCode : " + conn.getResponseCode() + "ResponseMessgae : " + conn.getResponseMessage());
            }
        } catch (Exception e) {
        	System.out.println("发送请求发生异常");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * 发送GET请求
     *
     * @param path     请求路径
     * @param params   请求参数
     * @param encoding 编码
     * @return String  null 请求失败
     */
    public static String sendGETRequest(String path, Map<String, String> params, String ecoding) {
        InputStream inputStream = null;
        // http://192.168.1.100:8080/web/ManageServlet?name=yangpan&age=28
        HttpURLConnection conn = null;
        try {
            StringBuilder url = new StringBuilder(path);
            url.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url.append(entry.getKey()).append("=");
                url.append(URLEncoder.encode(entry.getValue(), ecoding));
                url.append("&");
            }
            url.deleteCharAt(url.length() - 1);
            conn = (HttpURLConnection) new URL(url.toString()).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            // conn.setRequestProperty ("User-Agent", AppContext.getHandSetInfo ());
            conn.setRequestProperty("AppKey", "3");
            conn.setRequestProperty("Connection", "Keep-Alive");
            if (conn.getResponseCode() == 200) {
                inputStream = conn.getInputStream();
                byte[] dateStream = readStream(inputStream);
                return new String(dateStream);
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    /**
     * 读取流
     *
     * @param inStream
     * @return 字节数组
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outSteam = null;
        try {
            outSteam = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int len;
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != outSteam) {
                outSteam.close();
            }
            if (null != inStream) {
                inStream.close();
            }
        }
        return outSteam.toByteArray();
    }

    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    private static String sendHttpClientPOSTRequest2(String path, String param, String encoding) throws IOException {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(param, encoding);
            HttpPost httpPost = new HttpPost(path);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(60000).setConnectionRequestTimeout(60000).setSocketTimeout(60000).build();
            httpPost.setHeader("Connection", "Keep-Alive");
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(entity);

            client = HttpClients.createDefault();
            response = client.execute(httpPost);

            int statusCode = response.getStatusLine().getStatusCode();
            /* 若状态码为200 ok */
            if (statusCode == 200) {
                InputStream inputStream = response.getEntity().getContent();
                byte[] dateStream = readStream(inputStream);
                return new String(dateStream);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != response) {
                response.close();
            }
            if (null != client) {
                client.close();
            }
        }
        return null;
    }

    public static String postStream(String strUrl, String content, String encode) {
        HttpURLConnection urlCon = null;

        try {
            URL url = new URL(strUrl);
            urlCon = (HttpURLConnection) url.openConnection();
            urlCon.setRequestMethod("POST");
            urlCon.setRequestProperty("content-type", "application/json");
            urlCon.setConnectTimeout(5 * 1000);
            urlCon.setDoOutput(true);
            urlCon.getOutputStream().write(content.getBytes(encode));
            urlCon.getOutputStream().flush();
            urlCon.getOutputStream().close();
            if (urlCon.getResponseCode() == 200) {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlCon.getInputStream(), encode));
                String line;
                String respXML = "";
                while ((line = in.readLine()) != null) {
                    respXML += line;
                }
                in.close();
                return respXML;
            }
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } finally {
            if (urlCon != null) {
                urlCon.disconnect();
            }
        }
        return null;
    }

    private static String httpUrlConnection(String pathUrl, String requestString, String encode) {
        try {
            // 建立连接
            URL url = new URL(pathUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            // //设置连接属性
            httpConn.setDoOutput(true);// 使用 URL 连接进行输出
            httpConn.setDoInput(true);// 使用 URL 连接进行输入
            httpConn.setUseCaches(false);// 忽略缓存
            httpConn.setRequestMethod("POST");// 设置URL请求方法

            // 设置请求属性
            // 获得数据字节数据，请求数据流的编码，必须和下面服务器端处理请求流的编码一致
            byte[] requestStringBytes = requestString.getBytes(encode);
            httpConn.setRequestProperty("Content-length", "" + requestStringBytes.length);
            httpConn.setRequestProperty("Content-Type", "application/octet-stream");
            httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpConn.setRequestProperty("Charset", encode);

            // 建立输出流，并写入数据
            OutputStream outputStream = httpConn.getOutputStream();
            outputStream.write(requestStringBytes);
            outputStream.close();
            // 获得响应状态
            int responseCode = httpConn.getResponseCode();
            // 当正确响应时处理数据
            StringBuffer sb = new StringBuffer();
            if (HttpURLConnection.HTTP_OK == responseCode) {// 连接成功
                String readLine;
                BufferedReader responseReader;
                // 处理响应流，必须与服务器响应流输出的编码一致
                responseReader = new BufferedReader(new InputStreamReader(httpConn.getInputStream(), encode));
                while ((readLine = responseReader.readLine()) != null) {
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                return sb.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }



}
