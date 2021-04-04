package io.github.kimmking.gateway.outbound.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class OkhttpOutboundHandler {


    public static void main(String[] args) throws IOException, InterruptedException {

        OkhttpOutboundHandler ok = new OkhttpOutboundHandler();
        for (int i = 0; i < 20; i++) {
            System.out.println(ok.run("http://localhost:8888"));
            Thread.sleep(1000);
        }
    }

    public String run(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response code: " + response.code());
            return response.body().string();
        }
    }
}
