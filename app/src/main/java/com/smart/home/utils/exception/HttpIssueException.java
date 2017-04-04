package com.smart.home.utils.exception;

/**
 * Created by hesc on 15/08/25.
 * <p>http公布的异常类</p>
 */
public class HttpIssueException extends BaseException {
    //是否已经处理
    private boolean isHandled;

    public HttpIssueException(int code, String message){
        super(code, message);
    }

    public HttpIssueException(int code, String message, Throwable cause){
        super(code, message, cause);
    }

    /**
     * 异常是否已经处理
     * @return
     */
    public boolean isHandled(){
        return isHandled;
    }

    /**
     * 设置异常是否已经处理
     * @param isHandled
     */
    public void setHandled(boolean isHandled){
        this.isHandled = isHandled;
    }
}
