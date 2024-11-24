package com.learn.auth.http.response;



public class ApiResponse<T> {

    private String message;
    private T responseData;

    public ApiResponse(String message){
        this.message = message;
    }


    public ApiResponse(String message, T responseData){
        this.message = message;
        this.responseData = responseData;

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }



}