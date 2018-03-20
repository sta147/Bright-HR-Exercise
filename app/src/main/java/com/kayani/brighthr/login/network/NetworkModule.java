package com.kayani.brighthr.login.network;

import android.support.annotation.NonNull;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kayani.brighthr.login.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kayani on 19/03/2018.
 */

@Module
public class NetworkModule {
    File cacheFile;

    public NetworkModule(File cacheFile) {
        this.cacheFile = cacheFile;
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
//            e.printStackTrace();
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .cache(cache)

                .build();


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
//                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public NetworkService provideApiService() {
        final Retrofit retrofit = new Builder()
                .baseUrl(BuildConfig.BASEURL)
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

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public NetworkService providesNetworkService(
            Retrofit retrofit) {
        return retrofit.create(NetworkService.class);
    }
    @Provides
    @Singleton
    @SuppressWarnings("unused")
    public Service providesService(
            NetworkService networkService) {
        return new Service(networkService);
    }

}