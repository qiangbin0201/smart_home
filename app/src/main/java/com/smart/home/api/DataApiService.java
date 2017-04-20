package com.smart.home.api;

import com.smart.home.model.StateDetail;


import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by qiangbin on 2017/4/18.
 */

public interface DataApiService {

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getBulbData(@Field("position") String position, @Field("state") String state, @Field("brightness") int brightness);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getTvData(@Field("position") String poition, @Field("state") String state, @Field("channel") String channel, @Field("volume") int volume);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getAirConditionData(@Field("position") String poition, @Field("state") String state, @Field("schema") String schema, @Field("temperature") int temperature);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getFanData(@Field("position") String poition, @Field("state") String state, @Field("speed") int speed);

}

