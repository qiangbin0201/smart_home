package com.smart.home.presenter;

import com.smart.home.api.BaseResponse;
import com.smart.home.api.DataApiService;
import com.smart.home.confige.AppProxy;
import com.smart.home.model.StateDetail;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by qiangbin on 2017/4/18.
 */

public class ControlPresenter {

    private static final String BULB = "电灯";

    private static final String TV = "电视";

    private static final String AIR_CONDITION = "空调";

    private static final String FAN = "风扇";


    private static ControlPresenter mControlPresenter;

    public static ControlPresenter getInstance(){
        if(mControlPresenter == null){
            mControlPresenter = new ControlPresenter();
        }
        return mControlPresenter;
    }

    public Observable<BaseResponse<StateDetail>> getBulbData(String equipCode, String state, int brightness){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getBulbData(BULB, equipCode, state, brightness);
    }

    public Observable<BaseResponse<StateDetail>> getTvData(String equipCode, String state, int channel, int volume){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getTvData(TV, equipCode, state, channel, volume);
    }

    public Observable<BaseResponse<StateDetail>> getAirConditionData(String equipCode, String state, String mode, int temperature){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getAirConditionData(AIR_CONDITION, equipCode, state, mode, temperature);
    }

    public Observable<BaseResponse<StateDetail>> getFanData(String equipCode, String state, int speed){
        DataApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(DataApiService.class);
        return service.getFanData(FAN, equipCode, state, speed);
    }
}
