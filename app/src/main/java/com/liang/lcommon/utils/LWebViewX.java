package com.liang.lcommon.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Environment;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.io.File;

/**
 * @author : Amarao
 * CreateAt : 19:04 2019/1/12
 * Describe : WebView 辅助
 */
public class LWebViewX {

    public static void initWebViewCache(WebView mWebView) {
        String cachePath = new File(Environment.getExternalStorageDirectory(), "webCache").getAbsolutePath();

        WebSettings settings = mWebView.getSettings();
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(cachePath);
        settings.setDatabaseEnabled(true);
        // 过时
        settings.setDatabasePath(cachePath);
        settings.setDomStorageEnabled(true);// 开启dom缓存

        // LOAD_DEFAULT：默认的缓存使用模式。在进行页面前进或后退的操作时，如果缓存可用并未过期就优先加载缓存，否则从网络上加载数据。这样可以减少页面的网络请求次数。
        // LOAD_CACHE_ELSE_NETWORK：只要缓存可用就加载缓存，哪怕它们已经过期失效。如果缓存不可用就从网络上加载数据。
        // LOAD_NO_CACHE：不加载缓存，只从网络加载数据。
        // LOAD_CACHE_ONLY：不从网络加载数据，只从缓存加载数据。
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 缓存最大值，过时
        settings.setAppCacheMaxSize(1000 * 1024);
    }


    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebViewSettings(WebView mWebView) {
        //支持获取手势焦点
        mWebView.requestFocusFromTouch();
        // 触觉反馈，暂时没发现用处在哪里
        mWebView.setHapticFeedbackEnabled(false);

        WebSettings settings = mWebView.getSettings();
        // 支持插件
        settings.setPluginState(WebSettings.PluginState.ON);
        // 允许js交互
        settings.setJavaScriptEnabled(true);
        // 设置WebView是否可以由 JavaScript 自动打开窗口，默认为 false
        // 通常与 JavaScript 的 window.open() 配合使用。
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 允许中文编码
        settings.setDefaultTextEncodingName("UTF-8");
        // 使用大视图，设置适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        // 支持多窗口
        settings.setSupportMultipleWindows(false);
        // 隐藏自带缩放按钮
        settings.setBuiltInZoomControls(false);
        // 支持缩放
        settings.setSupportZoom(true);
        //设置可访问文件
        settings.setAllowFileAccess(true);
        //当WebView调用requestFocus时为WebView设置节点
        settings.setNeedInitialFocus(true);
        //支持自动加载图片
        settings.setLoadsImagesAutomatically(true);
        // 指定WebView的页面布局显示形式，调用该方法会引起页面重绘。
        // NORMAL,SINGLE_COLUMN 过时, NARROW_COLUMNS 过时 ,TEXT_AUTOSIZING
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 从Lollipop(5.0)开始WebView默认不允许混合模式，https当中不能加载http资源，需要设置开启
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }


    /**
     * 获取文件网页
     *
     * @param file 要加载的文件
     * @return 返回加载的路径
     */
    public static String fileUrl(File file) {
        return "file://" + file.getAbsolutePath();
    }


    /**
     * 加载 assets 里面的网页
     *
     * @param filePath 资源路径
     * @return 加载地址
     */
    public static String assetsUrl(String filePath) {
        return "file:///android_asset/" + filePath;
    }

    /**
     * 回退
     *
     * @param webView webview
     * @return 是否回退成功
     */
    public static boolean goBack(WebView webView) {
        if (webView.canGoBack()) {
            webView.goBack();
            return true;
        } else {
            return false;
        }
    }


    /**
     * 回收 webview 资源
     *
     * @param webView webview
     */
    public static void destroyWebView(WebView webView) {
        if (webView == null) {
            return;
        }
        try {
            try {
                ((ViewGroup) webView.getParent()).removeView(webView);
            } catch (Exception ignore) {

            }
            webView.stopLoading();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearAnimation();
            webView.loadUrl("about:blank");
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

