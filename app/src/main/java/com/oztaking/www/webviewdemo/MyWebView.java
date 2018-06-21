package com.oztaking.www.webviewdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * @function:
 */

public class MyWebView extends WebView{

    private onScrollChangedCallback mOnScrollChangedCallback;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mOnScrollChangedCallback != null){
            mOnScrollChangedCallback.onScroll(l,t,oldl,oldt);
        }
    }



    public static interface onScrollChangedCallback{
        public void onScroll(int left,int top, int oldLeft, int oldTop);
    }





}
