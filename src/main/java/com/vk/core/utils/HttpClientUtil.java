package com.vk.core.utils;

import okhttp3.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * 作者:   王恒
 * Author:VINCE
 * 日期:   2020-07-07
 * Des:   VINCE 个人独创
 * <p>
 * * #                                                   #
 * #                       _oo0oo_                     #
 * #                      o8888888o                    #
 * #                      88" . "88                    #
 * #                      (| -_- |)                    #
 * #                      0\  =  /0                    #
 * #                    ___/`---'\___                  #
 * #                  .' \\|     |# '.                 #
 * #                 / \\|||  :  |||# \                #
 * #                / _||||| -:- |||||- \              #
 * #               |   | \\\  -  #/ |   |              #
 * #               | \_|  ''\---/''  |_/ |             #
 * #               \  .-\__  '-'  ___/-. /             #
 * #             ___'. .'  /--.--\  `. .'___           #
 * #          ."" '<  `.___\_<|>_/___.' >' "".         #
 * #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 * #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 * #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 * #                       `=---='                     #
 * #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 * #                                                   #
 * #               佛祖保佑         永无bug             #
 * #                                                   #
 */
public class HttpClientUtil {
    public static final Duration DEFAULT_CALL_TIMEOUT = Duration.ofMinutes(2L);
    public static final Duration DEFAULT_CONNECT_TIMEOUT = Duration.ofMinutes(2L);
    public static final Duration DEFAULT_READ_TIMEOUT = Duration.ofMinutes(2L);
    public static final Duration DEFAULT_WRITE_TIMEOUT = Duration.ofMinutes(2L);
    private static OkHttpClient okHttpClient = null;

    public HttpClientUtil() {
    }

    public static void reinitialize(Duration connectTimeout, Duration readTimeout, Duration writeTimeout, Duration callTimeout) {
        okHttpClient = (new OkHttpClient.Builder()).connectTimeout(connectTimeout).readTimeout(readTimeout).writeTimeout(writeTimeout).callTimeout(callTimeout).build();
    }

    public static String executeGet(HttpUrl url, Headers headers, Charset charset) throws IOException {
        return execute(url, headers, charset, okhttp3.Request.Builder::get);
    }

    public static String executePost(HttpUrl url, Headers headers, Charset charset, RequestBody body) throws IOException {
        return execute(url, headers, charset, (builder) -> {
            builder.post(body);
        });
    }

    public static String execute(HttpUrl url, Headers headers, Charset charset, Consumer<Request.Builder> builderConsumer) throws IOException {
        okhttp3.Request.Builder requestBuilder = (new okhttp3.Request.Builder()).url(url).headers(headers);
        builderConsumer.accept(requestBuilder);
        Request request = requestBuilder.build();
        Response response = okHttpClient.newCall(request).execute();
        Throwable var7 = null;

        String var8;
        try {
            if (response.body() == null) {
                var8 = null;
                return var8;
            }

            var8 = new String(response.body().bytes(), charset);
        } catch (Throwable var18) {
            var7 = var18;
            throw var18;
        } finally {
            if (response != null) {
                if (var7 != null) {
                    try {
                        response.close();
                    } catch (Throwable var17) {
                        var7.addSuppressed(var17);
                    }
                } else {
                    response.close();
                }
            }

        }

        return var8;
    }

    /** @deprecated */
    @Deprecated
    public static String getHttpDataAsUTF_8(String url, String refererUrl) {
        try {
            Map<String, String> headerMap = new HashMap();
            headerMap.put("Referer", refererUrl);
            Request request = (new okhttp3.Request.Builder()).url(url).headers(Headers.of(headerMap)).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    /** @deprecated */
    @Deprecated
    public static String getHttpDataByPost(String url, String refererUrl, Map<String, String> map) {
        try {
            okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
            map.forEach((key, value) -> {
                if (value != null) {
                    builder.add(key, value);
                }

            });
            RequestBody body = builder.build();
            Map<String, String> headerMap = new HashMap();
            headerMap.put("Referer", refererUrl);
            Request request = (new okhttp3.Request.Builder()).url(url).post(body).headers(Headers.of(headerMap)).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    /** @deprecated */
    @Deprecated
    public static String getHttpDataByPost(String url, String refererUrl, String string) {
        return getHttpDataByPost(url, refererUrl, string, "text/x-markdown; charset=utf-8");
    }

    /** @deprecated */
    @Deprecated
    public static String getHttpDataByPost(String url, String refererUrl, String string, String header) {
        try {
            RequestBody body = RequestBody.create(MediaType.parse(header), string);
            Map<String, String> headerMap = new HashMap();
            headerMap.put("Referer", refererUrl);
            Request request = (new okhttp3.Request.Builder()).url(url).post(body).headers(Headers.of(headerMap)).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public static void openBrowseURL(String url) {
        Desktop desktop = Desktop.getDesktop();

        try {
            desktop.browse(new URI(url));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public static void openBrowseURLThrowsException(String url) throws IOException, URISyntaxException {
        Desktop desktop = Desktop.getDesktop();
        desktop.browse(new URI(url));
    }

    static {
        reinitialize(DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT, DEFAULT_WRITE_TIMEOUT, DEFAULT_CALL_TIMEOUT);
    }
}
