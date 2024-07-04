package cc.doctor.stars.biz.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpMessage;
import org.apache.http.ParseException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class HttpUtils {

    private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
    private static CookieStore cookieStore = new BasicCookieStore();

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.custom().setDefaultCookieStore(cookieStore).setConnectionManager(poolingHttpClientConnectionManager).build();
    }

    public static CloseableHttpResponse get(String url) {
        return get(url, null, null);
    }

    public static CloseableHttpResponse get(String url, Map<String, String> params) {
        return get(url, params, null);
    }

    public static CloseableHttpResponse get(String url, Map<String, String> params, Map<String, String> headers) {
        log.info("url:{}, params:{}, headers:{}", url, params, headers);
        List<BasicNameValuePair> formParams = new ArrayList<>();
        if (!CollectionUtils.isEmpty(params)) {
            for (String name : params.keySet()) {
                formParams.add(new BasicNameValuePair(name, params.get(name)));
            }
        }
        try {
            HttpGet httpGet;
            if (CollectionUtils.isEmpty(params)) {
                httpGet = new HttpGet(url);
            } else {
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, Charset.defaultCharset());
                String param = EntityUtils.toString(uefEntity);
                httpGet = new HttpGet(url + "?" + param);
            }
            setHeaders(httpGet, headers);
            return execute(httpGet);
        } catch (ParseException | IOException e) {
            log.error("", e);
        }
        return null;
    }

    private static void setHeaders(HttpMessage httpMessage, Map<String, String> headers) {
        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                httpMessage.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    public static CloseableHttpResponse post(String url, Map<String, Object> params) {
        return post(url, null, params, null);
    }

    public static CloseableHttpResponse postJson(String url, String json, Map<String, String> headers) {
        HttpPost httpPost = new HttpPost(url);
        setHeaders(httpPost, headers);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        //中文乱码问题解决
        StringEntity stringEntity = new StringEntity(json, "UTF-8");
        httpPost.setEntity(stringEntity);
        return execute(httpPost);
    }

    public static CloseableHttpResponse post(String url, Map<String, String> getParamMap,
                                             Map<String, Object> postParamMap, Map<String, String> headers) {
        url = urlWithGetParams(url, getParamMap);
        //post params
        List<BasicNameValuePair> formParams = new ArrayList<>();
        HttpPost httpPost = new HttpPost(url);
        for (String name : postParamMap.keySet()) {
            formParams.add(new BasicNameValuePair(name, postParamMap.get(name).toString()));
        }
        try {
            UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, "UTF-8");
            httpPost.setEntity(uefEntity);
            setHeaders(httpPost, headers);
            return execute(httpPost);
        } catch (IOException e) {
            log.error("", e);
        }
        return null;
    }

    private static String urlWithGetParams(String url, Map<String, String> getParamMap) {

        if (CollectionUtils.isEmpty(getParamMap)) {
            return url;
        }
        //get params
        List<BasicNameValuePair> formParams = new ArrayList<>();
        for (String name : getParamMap.keySet()) {
            formParams.add(new BasicNameValuePair(name, getParamMap.get(name)));
        }
        UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(formParams, Charset.defaultCharset());
        String param = null;
        try {
            param = EntityUtils.toString(uefEntity);
        } catch (IOException e) {
            log.error("", e);
        }
        if (param != null && !param.trim().isEmpty()) {
            url = url + "?" + param;
        }
        return url;
    }

    private static CloseableHttpResponse execute(HttpRequestBase httpRequestBase) {
        try {
            return getHttpClient().execute(httpRequestBase);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public static String parseHttpResponse(CloseableHttpResponse response) {
        if (response == null) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try {
                return EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                log.error("", e);
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }
        return null;
    }
}
