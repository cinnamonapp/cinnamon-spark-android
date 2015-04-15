package com.cinnamon.lib;

public class ApiResponse<T> {
    public static interface Listern<T>{
        void onSuccess(T t);

        void onErrorResponse(Exception error);
    }
}
