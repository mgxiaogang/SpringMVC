package com.liupeng.spring.httpclient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Service;

/**
 * 案例地址：https://www.jianshu.com/p/da4a806e599b
 *
 * @author fengdao.lp
 * @date 2018/6/24
 */
@Service
public class OKHttpClientTest {

    /**
     * 描述Http请求和响应体的内容类型，也就是Content-Type
     */
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * get请求 - 【同步调用】
     */
    public String testGetSync(String url) throws IOException {
        OkHttpClient clientWith30sTimeout = new OkHttpClient().newBuilder()
            .addInterceptor(new LoggingInterceptor())
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

        Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
        Response response = clientWith30sTimeout.newCall(request).execute();
        return response.body().string();
    }

    /**
     * get请求 - 【异步调用】
     */
    public void testGetAsync(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url(url)
            .get()
            .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("error");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) { throw new IOException("Unexpected code " + response); }
                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        });
    }

    /**
     * post请求
     */
    public String testPostSync(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
            .add("password", "111")
            .add("birthday", "2018-06-24")
            .build();

        // 请求头大全：http://tools.jb51.net/table/http_header
        Request request = new Request.Builder()
            .url(url)
            .header("User-Agent", "My super agent")
            .addHeader("Accept", "application/json")
            .post(formBody)
            .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        System.out.println(response.header("Server"));
        System.out.println(response.header("Set-Cookie"));
        System.out.println(response.headers());
        return response.body().string();
    }

    /**
     * 拦截器
     */
    public class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            long startTime = System.nanoTime();

            System.out.println(request.url());
            System.out.println(chain.connection());
            System.out.println(request.headers());

            Response response = chain.proceed(request);

            long endTime = System.nanoTime();
            System.out.println(endTime - startTime);
            return response;
        }
    }
}
