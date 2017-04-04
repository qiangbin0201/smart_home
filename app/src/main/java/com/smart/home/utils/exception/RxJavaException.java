package com.smart.home.utils.exception;

/**
 * Created by hesc on 15/08/25.
 * <p>RxJava数据处理异常类</p>
 */
@ReportException(code=4)
public class RxJavaException extends BaseException {

    public RxJavaException(String message){
        super(0, message);
    }

    public RxJavaException(String message, Throwable cause){
        super(0, message, cause);
    }
}
