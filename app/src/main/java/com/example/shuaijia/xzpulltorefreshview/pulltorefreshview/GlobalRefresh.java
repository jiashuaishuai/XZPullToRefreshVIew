package com.example.shuaijia.xzpulltorefreshview.pulltorefreshview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.example.shuaijia.xzpulltorefreshview.R;

import in.srain.cube.views.ptr.PtrFrameLayout;

public class GlobalRefresh extends PtrFrameLayout {

    private RefreshHeaderBase headerView;
    private static final int DEFAULT_HEADER = 0;
    private static final int WHITE_HEADER = 1;
    private static final int HOME_HEADER = 2;
    private static final int CURRENT_PRODUCT = 3;

    public GlobalRefresh(Context context) {
        super(context);
        initViews(context, null);
    }

    public GlobalRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs);
    }

    public GlobalRefresh(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews(context, attrs);
    }

    private void initViews(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GlobalRefresh);
            int headerStyle = typedArray.getInt(R.styleable.GlobalRefresh_header_style, DEFAULT_HEADER);
            typedArray.recycle();
            switch (headerStyle) {
                case DEFAULT_HEADER:
                    headerView = new DefaultRefreshHeader(context);
                    break;
                case WHITE_HEADER:
                    headerView = new AssetsRefreshHeader(context);
                    break;
                case HOME_HEADER:
                    headerView = new HomePtrHeaderClass(context);
                    break;
                case CURRENT_PRODUCT:
                    headerView = new CurrentProduct(context);
                    break;
                default:
                    headerView = new DefaultRefreshHeader(context);
                    break;

            }
            setHeaderView(headerView);
            addPtrUIHandler(headerView);
        }


    }

    public RefreshHeaderBase getHeader() {
        return headerView;
    }

    /**
     * Specify the last update time by this key string
     *
     * @param key
    //     */
//    public void setLastUpdateTimeKey(String key) {
//        if (mPtrClassicHeader != null) {
//            mPtrClassicHeader.setLastUpdateTimeKey(key);
//        }
//    }

    /**
     * Using an object to specify the last update time.
     *
     * @param object
     */
//    public void setLastUpdateTimeRelateObject(Object object) {
//        if (mPtrClassicHeader != null) {
//            mPtrClassicHeader.setLastUpdateTimeRelateObject(object);
//        }
//    }


}
