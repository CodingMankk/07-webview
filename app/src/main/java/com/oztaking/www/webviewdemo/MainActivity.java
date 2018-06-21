package com.oztaking.www.webviewdemo;

import android.app.ProgressDialog;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;

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
    private String s6 = "https://www.taobao.com/";


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


        //【6】java调用js
//        javaInvokeJs();

        //[7]增加进度条的监听
//        showProgressDialog();

        //【8】拦截
//        webViewUrlReplace();

        //【9】错误页面的加载
//        webViewErrorMsgInvoke();

        //[10]https ssl证书有问题
//        webViewSSLError();

        //[11]根据资源替换资源
        webViewRes();




    }


    //【12】增加返回键功能
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (mWebView.canGoBack()){
                mWebView.goBack();
                return true;
            }else {
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode, event);
    }



    //滚动事件监听



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
    private void jsInvokeJava() {
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

        mWebView.addJavascriptInterface(new JSBridge(), "kk");
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


    //【6】java调用js的代码

    private void javaInvokeJs() {

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/1.html");

        mWebView.addJavascriptInterface(new JSBridge(), "kk");

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:sum(1,99)");

            }
        });
    }


    //[7]增加进度条的监听
    private void showProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        mLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWebView.loadUrl(s6);
            }
        });

    }


    //【8】url拦截替换
    private void webViewUrlReplace() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://mail.qq.com/cgi-bin/loginpage");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Toast.makeText(getApplicationContext(),"拦截",Toast.LENGTH_SHORT).show();
//                return super.shouldOverrideUrlLoading(view, url);
                if (url.contains("mail.qq.com")){
                    view.loadUrl("https://www.baidu.com");
                }
                return false;
            }
        });
    }

    //【9】加载错误的信息的回调
    private void webViewErrorMsgInvoke(){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);



        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("file:///android_asset/error.html");
                Log.e("webView","onReceivedError:"+errorCode+"  "+description);

            }
        });

        mWebView.loadUrl("123");

    }


    //[10]ssl证书错误
    private void webViewSSLError(){
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                //发生错误之后继续加载
                handler.proceed();
                Log.e("webView","sslError:"+error.toString());
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("file:///android_asset/error.html");
                Log.e("webView","onReceivedError:"+errorCode+"  "+description);
            }
        });
        mWebView.loadUrl("https://www.12306.cn/");
    }


    //[11】修改图片
    private void webViewRes() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {

                if (url.equals("http://localhost/aaa.png")) {
                    try {
                        AssetFileDescriptor fd = getAssets().openFd("ic_launcher.png");
//                        AssetFileDescriptor fd = getAssets().openFd("s07.jpg");
                        FileInputStream is = fd.createInputStream();
                        WebResourceResponse response = new WebResourceResponse("image/png", "UTF-8", is);
                        return response;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }

        });

//        mWebView.loadUrl("file:///android_asset/1.html");
        mWebView.loadUrl(s2);
    }



}
