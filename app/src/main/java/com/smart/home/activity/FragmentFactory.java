package com.smart.home.activity;

import android.support.v4.app.Fragment;

/**
 *   创建Fragment的工厂接口
 */
public interface FragmentFactory {
    /**
     * 创建fragment
     * @return 返回fragment的实例
     */
    Fragment createFragment();
}
