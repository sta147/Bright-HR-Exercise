package com.kayani.brighthr.login.network;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kayani.brighthr.login.BuildConfig;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkFactory {
    public static NetworkService getApiService() {
        final Retrofit retrofit = new Builder()
                .baseUrl(BuildConfig.BASEURL)
                .addFieldNamingStrategy(new CamelCaseFieldNamingStrategy())
                .build();

        return retrofit.create(NetworkService.class);
    }


    private static class Builder {
        private final ArrayList<Interceptor> mLowerInterceptors = new ArrayList<>();
        private String mBaseUrl;
        private FieldNamingStrategy mFieldNamingStrategy;

        public Builder baseUrl(@NonNull String baseUrl) {
            mBaseUrl = baseUrl;
            return this;
        }

        public Builder addLowerInterceptor(@NonNull Interceptor interceptor) {
            mLowerInterceptors.add(interceptor);
            return this;
        }

        public Builder addFieldNamingStrategy(@NonNull FieldNamingStrategy fieldNamingStrategy) {
            mFieldNamingStrategy = fieldNamingStrategy;
            return this;
        }

        public Retrofit build() {
            final GsonBuilder gsonBuilder = new GsonBuilder();
            if (mFieldNamingStrategy != null) {
                gsonBuilder.setFieldNamingStrategy(mFieldNamingStrategy);
            }

            final OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS);

            for (final Interceptor interceptor : mLowerInterceptors) {
                httpClientBuilder.addInterceptor(interceptor);
            }

            final Gson gson = gsonBuilder.create();
            final OkHttpClient httpClient = httpClientBuilder.build();

            return new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASEURL)
                    .client(httpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }
}
