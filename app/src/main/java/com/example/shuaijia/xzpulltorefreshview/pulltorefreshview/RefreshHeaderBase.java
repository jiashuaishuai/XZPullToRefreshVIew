package com.example.shuaijia.xzpulltorefreshview.pulltorefreshview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import in.srain.cube.views.ptr.PtrUIHandler;

/**
 * Created by Administrator on 2016/12/23.
 */

public abstract class RefreshHeaderBase extends FrameLayout implements PtrUIHandler {

    public RefreshHeaderBase(Context context) {
        super(context);
    }

    public RefreshHeaderBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshHeaderBase(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
