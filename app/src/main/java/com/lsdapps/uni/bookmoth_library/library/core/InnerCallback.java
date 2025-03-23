package com.lsdapps.uni.bookmoth_library.library.core;

public interface InnerCallback<T> {
    void onSuccess(T body);
    void onError(String errorMessage);
}
