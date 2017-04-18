package com.smart.home.api;

import java.util.List;

import retrofit.http.Field;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by lenovo on 2017/4/18.
 */

public interface DataApiService {

    @POST("")
    Observable<BaseResponse<List<News>>> getNewsList(@Field("device_id") String device_id, @Field("type_name") String type_name, @Field("category_id") int category_id, @Field("category_name") String category_name, @Field("count") int count);

}
