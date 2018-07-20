package xyz.thelostsoul.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * http 客户端
 */
public class HttpClient {

    private static final Log LOG = LogFactory.getLog(HttpClient.class);

    private URLConnection connection;

    private URL url;

    public HttpClient(String url, Map<String, String> param) throws Exception {
        String paramString = null;
        if (param != null) {
            StringBuilder finalParamString = new StringBuilder();
            param.forEach((k, v) -> finalParamString.append(k + "=" + v + "&"));
            finalParamString.deleteCharAt(paramString.length() - 1);
            paramString = finalParamString.toString();
        }

        this.url = new URL(url + "?" + paramString);
        this.connection = this.url.openConnection();
    }

    public void setHeader(Map<String, String> headers) throws Exception {
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

        Map<String, List<String>> map = connection.getHeaderFields();
        for (String key : map.keySet()) {
            LOG.error(key + " -> " + map.get(key));
        }

        try (InputStream is = this.connection.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // 获取所有响应头字段
            // 遍历所有的响应头字段
            // 定义 BufferedReader输入流来读取URL的响应
            int size;
            int BUFFER_SIZE = 1024;
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

        try (OutputStream out = this.connection.getOutputStream();
             InputStream is = this.connection.getInputStream();
             ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            // 发送POST请求必须设置如下两行
            this.connection.setDoInput(true);
            this.connection.setDoOutput(true);
            // 获取URLConnection对象对应的输出流
            // 发送请求参数
            out.write(body);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            int size;
            int BUFFER_SIZE = 1024;
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
