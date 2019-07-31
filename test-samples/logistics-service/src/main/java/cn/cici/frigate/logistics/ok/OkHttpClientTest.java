package cn.cici.frigate.logistics.ok;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @description: 类介绍：
 * @createDate: 2019/7/31 10:56
 * @author: Heyfan Xie
 */
public class OkHttpClientTest {

    private OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) throws Exception{

        OkHttpClientTest test = new OkHttpClientTest();
        test.run();

    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://api.github.com/repos/square/okhttp/issues")
                .header("User-Agent", "OkHttp Headers.java")
                .addHeader("Accept", "application/json; q=0.5")
                .addHeader("Accept", "application/vnd.github.v3+json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body());
            System.out.println("Server: " + response.header("Server"));
            System.out.println("Date: " + response.header("Date"));
            System.out.println("Vary: " + response.headers("Vary"));
        }
    }
}
