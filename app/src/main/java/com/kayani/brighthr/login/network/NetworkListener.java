package com.kayani.brighthr.login.network;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;

public class NetworkListener<T> implements Callback<T> {
    private final ResultListener<T> mListener;

    public NetworkListener(ResultListener<T> listener) {
        mListener = listener;
    }

    @Override
    public void onResponse(Call<T> call, retrofit2.Response<T> response) {
        mListener.onResult(response.body(), response.code());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        mListener.onResult(null, HttpURLConnection.HTTP_BAD_REQUEST);
    }
}
