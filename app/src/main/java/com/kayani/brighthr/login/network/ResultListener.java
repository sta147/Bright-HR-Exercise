package com.kayani.brighthr.login.network;

public abstract class ResultListener<T> {
    public abstract void onResult(T data, int resultCode);
}
