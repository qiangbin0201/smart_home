package com.smart.home.presenter;


import com.smart.home.api.BaseResponse;
import com.smart.home.api.FeedbackApiService;
import com.smart.home.confige.AppProxy;

import rx.Observable;

/**
 * Created by lish on 16/7/22.
 */
public class FeedbackPresenter {

    public Observable<BaseResponse> feedback(String content, String mobile) {
        FeedbackApiService service = AppProxy.getInstance().getDataApiRestAdapter().create(FeedbackApiService.class);
        return service.feedback( mobile, content);
    }
}
