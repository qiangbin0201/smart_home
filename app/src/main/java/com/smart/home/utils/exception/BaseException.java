package com.smart.home.utils.exception;

import com.bigbang.news.model.ErrorContent;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

/**
 * Created by hesc on 15/08/25.
 * <p>异常基类</p>
 */
public class BaseException extends Exception {
    //错误编码
    public final int code;
    //错误消息
    public final String message;
    //错误发生的时间
    public final Date errorTime;

    public BaseException(int code, String message){
        this(code, message, new Date(), null);
    }

    public BaseException(int code, String message, Throwable cause){
        this(code, message, new Date(), cause);
    }

    public BaseException(int code, String message, Date errorTime) {
        this(code, message, errorTime, null);
    }

    public BaseException(int code, String message, Date errorTime, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.errorTime = errorTime;
    }

    @ReportContent
    public ErrorContent getErrorContent(){
        ErrorContent content = new ErrorContent();
        content.code = code;
        content.message = message;
        content.stack = StackTraceAsString();
        return content;
    }

    /**
     * 获取堆栈信息
     * @return
     */
    public String StackTraceAsString(){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(outputStream);
        try {
            printStackTrace(stream);
            byte[] bytes = outputStream.toByteArray();
            return new String(bytes, "UTF-8");
        }catch (Exception e){
            return "";
        } finally {
            stream.close();
        }
    }
}
