package com.kayani.brighthr.login.util;


public interface Logger {
    void log(String tag, String message);

    void log(String tag, String message, Throwable throwable);
}
