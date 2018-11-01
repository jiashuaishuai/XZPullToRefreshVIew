package com.example.shuaijia.xzpulltorefreshview.pulltorefreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.example.shuaijia.xzpulltorefreshview.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by JiaShuai on 2017/9/26.
 */

public class CurrentProduct extends RefreshHeaderBase {
    public CurrentProduct(Context context) {
        this(context, null);
    }

    public CurrentProduct(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CurrentProduct(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View view = inflate(getContext(), R.layout.current_refresh_head, this);
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {

    }
}
