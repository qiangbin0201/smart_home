package com.smart.home.presenter;

import com.smart.home.api.BaseResponse;
import com.smart.home.api.DataApiService;
import com.smart.home.confige.AppProxy;
import com.smart.home.model.StateDetail;

import rx.Observable;

/**
 * Created by qiangbin on 2017/4/18.
 */

public class ControlPresenter {

    public Observable<BaseResponse<StateDetail>> getBulbData(String position, String state, int brightness){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getBulbData(position, state, brightness);
    }

    public Observable<BaseResponse<StateDetail>> getTvData(String position, String state, String channel, int volume){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getTvData(position, state, channel, volume);
    }

    public Observable<BaseResponse<StateDetail>> getAirConditionData(String position, String state, String schema, int temperature){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getAirConditionData(position, state, schema, temperature);
    }

    public Observable<BaseResponse<StateDetail>> getFanData(String position, String state, int speed){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getFanData(position, state, speed);
    }
}
