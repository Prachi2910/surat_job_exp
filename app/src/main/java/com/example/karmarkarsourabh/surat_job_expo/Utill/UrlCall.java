package com.example.karmarkarsourabh.surat_job_expo.Utill;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Class UrlCall it is use call the URL using OkHTTP3
 */

public class UrlCall {

    /**
     *
     * @param URL is url builder which is pass by building
     *            the URL with Param.
     * @return response.body() Json
     */


    public static String callUrl(HttpUrl.Builder URL) {

        try {
            Request request = new Request.Builder()
                    .url(URL.build())
                    .build();
            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            Log.e("IOException", "callUrl: " + e.getMessage());
            return null;
        }
    }
}
