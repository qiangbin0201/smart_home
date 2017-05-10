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
    Observable<BaseResponse<StateDetail>> getBulbData(@Field("equipName") String equipName, @Field("equipCode") String equipCode, @Field("state") String state, @Field("brightness") int brightness);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getTvData(@Field("equipName") String equipName, @Field("equipCode") String equipCode, @Field("state") String state, @Field("channel") int channel, @Field("volume") int volume);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getAirConditionData(@Field("equipName") String equipName, @Field("equipCode") String equipCode, @Field("state") String state, @Field("mode") String mode, @Field("temperature") int temperature);

    @FormUrlEncoded
    @POST("")
    Observable<BaseResponse<StateDetail>> getFanData(@Field("equipName") String equipName, @Field("equipCode") String equipCode, @Field("state") String state, @Field("speed") int speed);

}

