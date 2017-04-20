package com.smart.home.api;

import java.io.Serializable;

public class BaseResponse<T> implements Serializable {
    public int code;
    public String message;
    public String errormsg;
    public T data;
}
