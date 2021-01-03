package com.uriallab.haat.haat.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uriallab.haat.haat.SharedPreferences.ConfigurationFile;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

public class NetworkUtil2 {
    public final static String BASE_URL =  "http://85.194.65.236:700/";
    public final static String BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/";

    public static NetworkInterface getRetrofitNoHeader() {


        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(NetworkInterface.class);

    }
    public static NetworkInterface getRetrofitNoHeader2() {


        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL_GOOGLE)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(NetworkInterface.class);

    }


    public static NetworkInterface getRetrofitByToken(String token,String lang) {


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        final String newToken= token;

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {

                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .addHeader("Authorization", newToken)
                        .addHeader("lang", lang)
                        .method(original.method(), original.body());
                return chain.proceed(builder.build());

            }
        });

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClient.build())
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(NetworkInterface.class);
    }

    public static  String BASEExternalURL = "http://api.aladhan.com/";
    public static NetworkInterface getRetrofittNoHeader() {

        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASEExternalURL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(NetworkInterface.class);

    }
}
