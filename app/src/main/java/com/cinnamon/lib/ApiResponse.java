package com.cinnamon.lib;

public class ApiResponse<T> {
    public static interface Listener<T>{
        void onSuccess(T t);

        void onErrorResponse(Exception error);
    }
}
