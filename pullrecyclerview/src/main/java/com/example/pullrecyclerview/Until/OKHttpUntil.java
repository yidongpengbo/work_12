package com.example.pullrecyclerview.Until;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class OKHttpUntil {
    /**
     * 1.单例
     */
    private static OKHttpUntil instance;
    private final OkHttpClient mClient;

    public static OKHttpUntil getInstance(){
        if (instance==null){
            synchronized (OKHttpUntil.class){
                if (null==instance){
                    instance=new OKHttpUntil();
                }
            }
        }
        return instance;
    }

    private OKHttpUntil(){
        /**
         * 2.构造方法
         */
        mClient = new OkHttpClient.Builder()
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .connectTimeout(10,TimeUnit.SECONDS)
                .build();
        //日志
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    }

    /**
     * 3.get同步
     */
    public String getExcute(String path) throws IOException {
        Request request = new Request.Builder().url(path).get().build();
        Call call = mClient.newCall(request);
        Response response = call.execute();
        String string = response.body().string();
        return string;
    }

    /**
     * get异步
     */
    private Handler mHandler=new Handler(Looper.getMainLooper());
    public void getEnquene(String path, final Class clazz, final MyCallBack myCallBack){
        Request request = new Request.Builder().get().url(path).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            myCallBack.fail(e);
                        }
                    });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.success(o);
                    }
                });
            }
        });
    }

    /**
     * post异步
     */

    public void postEnquene(String path, Map<String,String> map, final Class clazz, final MyCallBack myCallBack){
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String,String> entry:map.entrySet()) {
                builder.add(entry.getKey(),entry.getValue());
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().post(body).url(path).build();
        Call call = mClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.fail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final Object o = gson.fromJson(string, clazz);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        myCallBack.success(o);
                    }
                });

            }
        });
    }
}
