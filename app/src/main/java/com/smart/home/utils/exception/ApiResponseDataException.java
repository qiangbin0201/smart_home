package com.smart.home.utils.exception;

import com.bigbang.news.model.ErrorContent;

/**
 * Created by hesc on 15/08/25.
 * <p>服务端API接口返回数据异常类</p>
 */
@ReportException(code=1)
public class ApiResponseDataException extends BaseException {
    public String url;
    public String response;

    public ApiResponseDataException(String url, String response, String message) {
        this(url, response, message, null);
    }

    public ApiResponseDataException(String url, String response, String message, Throwable cause) {
        super(0, message, cause);
        this.url = url;
        this.response=response;
    }

    @ReportContent
    @Override
    public ErrorContent getErrorContent() {
        ErrorContent content = new ErrorContent();
        content.url = url;
        content.message = message;
        content.response = response;
        return content;
    }
}
