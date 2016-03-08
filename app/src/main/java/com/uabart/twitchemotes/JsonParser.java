package com.uabart.twitchemotes;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JsonParser {

    private final OkHttpClient mClient = new OkHttpClient();
    private final Gson mGson = new Gson();
    private String mResponseString = "";
    private String mUrl = "";

    public JsonParser(String jsonUrl, Class tClass, MyCallbackInterface callback) {
        mUrl = jsonUrl;

        try {
            run(tClass, callback);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void run(final Class tClass, final MyCallbackInterface callback) throws Exception {
        Request request = new Request.Builder()
                .url(mUrl)
                .build();

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                mResponseString = response.body().string();

                if (mResponseString.equals(""))
                    Log.e("JsonParser", "Empty string");
                else {
                    Object jsonObject = mGson.fromJson(mResponseString, tClass);
                    callback.onDownloadFinished(jsonObject);
                }
            }


        });
    }

    interface MyCallbackInterface {
        void onDownloadFinished(Object result);
    }

}
