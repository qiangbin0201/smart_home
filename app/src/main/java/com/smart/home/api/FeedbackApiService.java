package com.smart.home.api;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by qiangbin on 17/4/25.
 */
public interface FeedbackApiService {

    @FormUrlEncoded
    @POST("/feedback/data")
    Observable<BaseResponse> feedback( @Field("contact_info") String mobile, @Field("content") String content);
}
