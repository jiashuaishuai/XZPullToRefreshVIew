package com.example.shuaijia.xzpulltorefreshview.pulltorefreshview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shuaijia.xzpulltorefreshview.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by liaopenghui on 2016/12/7.
 */

public class AssetsRefreshHeader extends RefreshHeaderBase {
    private static final String TAG = "AssdetsRefreshHeader";
    private ImageView iv_head;
    private TextView tv_state;
    private ProgressBar component_progressbar;
    private ObjectAnimator oaY;
    private boolean isshow = true;

    public AssetsRefreshHeader(Context context) {
        super(context);
        initViews();

    }


    public AssetsRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();

    }


    private void initViews() {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.assets_refresh_head, this);
        iv_head = (ImageView) header.findViewById(R.id.iv_head);
        tv_state = (TextView) header.findViewById(R.id.tv_state);
        component_progressbar = (ProgressBar) header.findViewById(R.id.component_progressbar);
    }


    @Override
    public void onUIReset(PtrFrameLayout frame) {

    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
        //主要进行重置操作
        iv_head.setVisibility(INVISIBLE);
        component_progressbar.setVisibility(VISIBLE);
        component_progressbar.setProgress(0);
        tv_state.setText("下拉刷新");
        isshow = true;
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        iv_head.setVisibility(VISIBLE);
        tv_state.setText("正在加载");
        oaY = ObjectAnimator.ofFloat(iv_head, "rotationY", 0, 360);
        oaY.setDuration(500);
        oaY.setRepeatMode(ValueAnimator.REVERSE);
        oaY.setRepeatCount(ValueAnimator.INFINITE);
        oaY.start();
//        invalidate();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        isshow = false;
        tv_state.setText("加载完成");
        if (oaY != null && oaY.isRunning()) {
            oaY.cancel();
        }

    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        final int mOffsetToRefresh = frame.getOffsetToRefresh();
        final int currentPos = ptrIndicator.getCurrentPosY();
        final int lastPos = ptrIndicator.getLastPosY();


        int i = (int) (ptrIndicator.getCurrentPercent() * 100);
        if (isshow)
            if (i < 100) {
                component_progressbar.setVisibility(VISIBLE);
            } else {
                component_progressbar.setVisibility(INVISIBLE);
            }
        component_progressbar.setProgress(i);
        if (currentPos < mOffsetToRefresh && lastPos >= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromBottomUnderTouch(frame);
            }
        } else if (currentPos > mOffsetToRefresh && lastPos <= mOffsetToRefresh) {
            if (isUnderTouch && status == PtrFrameLayout.PTR_STATUS_PREPARE) {
                crossRotateLineFromTopUnderTouch(frame);
            }
        }
    }

    private void crossRotateLineFromTopUnderTouch(PtrFrameLayout frame) {
        if (!frame.isPullToRefresh()) {
            tv_state.setVisibility(VISIBLE);
            iv_head.setVisibility(VISIBLE);
            component_progressbar.setProgress(100);
            component_progressbar.setVisibility(INVISIBLE);
            tv_state.setText("释放刷新");
        }
    }

    private void crossRotateLineFromBottomUnderTouch(PtrFrameLayout frame) {
        tv_state.setVisibility(VISIBLE);
        if (frame.isPullToRefresh()) {
            tv_state.setText("下拉");
        } else {
            tv_state.setText("下拉刷新");
            iv_head.setVisibility(INVISIBLE);
        }
    }
}
