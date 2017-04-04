package com.smart.home.utils.exception;

import com.google.gson.JsonObject;

/**
 * Created by hesc on 15/08/25.
 * <p>服务端向客户端公布的异常类</p>
 */
public class ApiIssueException extends HttpIssueException {
    //http请求的url地址
    public final String request;
    //http请求返回的json格式的异常数据
    public final JsonObject object;

    public ApiIssueException(String request, int code, String message) {
        super(code, message);
        this.request = request;
        this.object = new JsonObject();
    }

    public ApiIssueException(JsonObject object, int code, String message){
        super(code, message);
        request = "";
        this.object = object;
    }
}
