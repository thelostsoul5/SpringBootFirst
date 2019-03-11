package xyz.thelostsoul.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * http 客户端
 * @author thelostsoul
 */
public class HttpClient {

    private static final Log LOG = LogFactory.getLog(HttpClient.class);

    private HttpURLConnection connection;

    private URL url;

    public HttpClient(String url, Map<String, String> param) throws Exception {
        String paramString = null;
        if (param != null) {
            StringBuilder finalParamString = new StringBuilder();
            param.forEach((k, v) -> finalParamString.append(k).append("=").append(v).append("&"));
            finalParamString.deleteCharAt(finalParamString.length() - 1);
            paramString = finalParamString.toString();
        }

        String urlWithParam = (paramString == null) ? url : url + "?" + paramString;

        this.url = new URL(urlWithParam);
        this.connection = (HttpURLConnection) this.url.openConnection();
    }

    public void setHeader(Map<String, String> headers) {
        if (headers == null) {
            return;
        }

        headers.forEach((k, v) -> this.connection.addRequestProperty(k, v));
    }

    public void setConnectTimeout(int second) {
        this.connection.setConnectTimeout(second * 1000);
    }

    public void setReadTimeout(int second) {
        this.connection.setReadTimeout(second * 1000);
    }

    /**
     * 向指定URL发送GET方法的请求
     * @return 远程资源的响应结果
     */
    public byte[] get() throws IOException {
        byte[] bytes;

        LOG.error(url.toString());

        // 获取所有响应头字段
        // 遍历所有的响应头字段
        Map<String, List<String>> map = this.connection.getHeaderFields();
        for (String key : map.keySet()) {
            LOG.error(key + " -> " + map.get(key));
        }

        try (InputStream is = this.connection.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            LOG.error("返回码：" + this.connection.getResponseCode());

            // 定义 BufferedReader输入流来读取URL的响应
            int size;
            final int BUFFER_SIZE = 1024;
            byte[] buf = new byte[BUFFER_SIZE];
            while ((size = is.read(buf)) != -1) {
                os.write(buf, 0, size);
            }
            bytes = os.toByteArray();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }

        return bytes;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param body 请求体
     * @return 所代表远程资源的响应结果
     */
    public byte[] post(byte[] body) throws IOException {
        byte[] bytes;

        LOG.error(url.toString());

        // 发送POST请求必须设置如下两行
        this.connection.setDoInput(true);
        this.connection.setDoOutput(true);
        this.connection.setRequestMethod("POST");

        try (OutputStream out = this.connection.getOutputStream();
             InputStream is = this.connection.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            LOG.error("返回码：" + this.connection.getResponseCode());

            // 发送请求参数
            out.write(body);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            int size;
            final int BUFFER_SIZE = 1024;
            byte[] buf = new byte[BUFFER_SIZE];
            while ((size = is.read(buf)) != -1) {
                os.write(buf, 0, size);
            }
            bytes = os.toByteArray();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            throw e;
        }

        return bytes;
    }
}
