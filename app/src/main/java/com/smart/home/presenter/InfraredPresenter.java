package com.smart.home.presenter;


import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.smart.home.ConsumerIrManagerCompat;

/**
 * Created by qiangbin on 2017/5/11.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class InfraredPresenter {

    private static ConsumerIrManager mConsumerIrManager;
    
    public static ConsumerIrManager getInstance(Context context){
       return mConsumerIrManager = (ConsumerIrManager) context.getSystemService(Context.CONSUMER_IR_SERVICE);
    }


    public void transmit(int carrierFrequency, int[] pattern) {
        mConsumerIrManager.transmit(carrierFrequency, pattern);
    }

    public boolean hasIrEmitter() {
        return mConsumerIrManager.hasIrEmitter();
    }

    public android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        return mConsumerIrManager.getCarrierFrequencies();
    }
}
