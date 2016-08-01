package com.example.pengge.myapplication.OkHttpNet;

/**
 * Created by pengge on 16/7/29.
 */

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import com.example.pengge.myapplication.Gson.gsonAnalysis;


public class OkHttpBase {
    private static OkHttpBase myInstance;
    OkHttpClient client = new OkHttpClient();
    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
    /**
     * 单例模式创建对象
     * @return 对象
     */
    public static OkHttpBase getInstance() {

        if (myInstance == null) {
            synchronized (OkHttpBase.class) {
                if (myInstance == null) {
                    myInstance = new OkHttpBase();
                }
            }
        }
        return myInstance;

    }
    public String get_list(String url) throws IOException
    {
        System.out.println(this.run(url));
        return this.run(url);
    }
    public static void main(String[] args) throws IOException
    {
        OkHttpBase example = new OkHttpBase();
        String response = example.run("http://ppf.com/Index/index/index");
        Gson gson = new Gson();
        gsonAnalysis json_class = gson.fromJson(response,gsonAnalysis.class);
        String order_name = json_class.getLon();
        System.out.println(response);
        System.out.println(order_name);

    }


}