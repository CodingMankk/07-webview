package com.oztaking.www.webviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private LinearLayout mll;
    private WebView mWebView;
    private Button mLoadButton;
    private Button mBtn;

    private String s1 = "http://i.youku" +
            ".com/i/UMjczOTc0NDkzNg==?spm=a2h0j.11185381.module_basic_sub.A";
    private String s2 = "https://www.jianshu.com/p/3c94ae673e2a";
    private String s3 = "https://finance.qq.com/a/20180620/023025.htm";
    private String s4 = "http://www.w3school.com.cn/";
    private String s5 = "https://mail.qq.com/cgi-bin/loginpage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mll = (LinearLayout) findViewById(R.id.ll);
        mLoadButton = (Button) findViewById(R.id.mBtn_load);
        mBtn = (Button) findViewById(R.id.mBtn_load);
        mWebView = (WebView) findViewById(R.id.webview);


        //【1】使用外部浏览器加载页面；
        //        webViewLoadByBrower();

        //【2】使用本webview加载页面；
        //        webViewLoadByMyWebview();

        //【3】加载本地url
        //        webViewLoadNativeHtml();


        //【4】webview的各种设置的使用
//        webViewMethodsTest();

        //【5】js调用java代码
//        jsInvokeJava();

        javaInvokeJs();

        //【6】java调用js
//        javaInvokeJs();
//        Android2JS(mWebView);
    }

    //【1】使用外部浏览器加载页面；
    private void webViewLoadByBrower() {
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("https://blog.csdn.net/harvic880925/article/list/1");

            }
        });
    }

    //【2】使用本webview加载页面；
    private void webViewLoadByMyWebview() {
        //需要使用client;
        mWebView.setWebViewClient(new WebViewClient());

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("https://blog.csdn.net/harvic880925/article/list/1");
            }
        });
    }

    //【3】加载本地url
    private void webViewLoadNativeHtml() {
        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("file:///android_asset/1.html");
            }
        });
    }

    //【4】webview的各种设置的使用
    private void webViewMethodsTest() {

        //使用webview自己打开网页
        mWebView.setWebViewClient(new WebViewClient());


        WebSettings settings = mWebView.getSettings();

        //支持缩放
        settings.setSupportZoom(true);
        //是否显示窗口悬浮的缩放控制，默认true
        settings.setDisplayZoomControls(true);
        //是否使用WebView内置的缩放组件，由浮动在窗口上的缩放控制和手势缩放控制组成，默认false
        settings.setBuiltInZoomControls(true);
        //启用js
        settings.setJavaScriptEnabled(true);

        //打开页面自适应屏幕
        settings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
//
        //是否启动概述模式浏览界面，当页面宽度超过WebView显示宽度时，缩小页面适应WebView。默认false
        settings.setLoadWithOverviewMode(true);

        settings.setLoadsImagesAutomatically(true);

        settings.setSupportMultipleWindows(true);//支持多窗口；

        //如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点
        mWebView.requestFocusFromTouch();

        //设置使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            //需要设置setting
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(s5);
            }
        });
    }

    //【5】js调用java代码
    private void jsInvokeJava(){
        /**
         * public void addJavascriptInterface(Object obj, String interfaceName)
         * Object obj：interfaceName所绑定的对象
         * String interfaceName：所绑定的对象所对应的名称
         *
         * 向WebView注入一个interfaceName的对象，这个对象绑定的是obj对象，通过interfaceName就可以调用obj对象中的方法
         *
         */

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.addJavascriptInterface(new JSBridge(),"kk");
        mWebView.loadUrl("file:///android_asset/1.html");

    }

    public class JSBridge {
//        @JavascriptInterface
//        public void toastMsg(String msg) {
//            Toast.makeText(getApplicationContext(), "通过native传递的Toast：" + msg, Toast.LENGTH_SHORT).show();
//
//        }
//
        @JavascriptInterface
        public void onSumResult(int sum) {
            Toast.makeText(getApplicationContext(), "通过native传递的Toast：" + sum, Toast.LENGTH_SHORT).show();
        }


    }


    //【5】java调用js的代码

    private void javaInvokeJs(){

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/1.html");

        mWebView.addJavascriptInterface(new JSBridge(),"kk");

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:sum(1,99)");

            }
        });
    }





}
