package com.liang.lcommon.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.Toast;
import com.liang.lcommon.R;
import com.liang.lcommon.base.LBaseActivity;
import com.liang.liangutils.libs.view.LTitleView;
import com.liang.liangutils.utils.LEmptyX;
import com.liang.liangutils.utils.LFixInputMethodManagerLeak;
import com.liang.liangutils.view.LViewX;
import com.liang.liangutils.web.LHExts;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * @author : amarao
 * @date: 2019-06-30
 * describer: LWebActivity
 */
public class LWebActivity extends LBaseActivity {

    public static final String KEY_URL = "WEB_KEY_URL";

    public static final String KEY_TITLE = "WEB_KEY_TITLE";

    private String mTitle;

    public static void startActivity(Context context, String url, String title) {
        if (LEmptyX.isEmpty(url)) {
            Toast.makeText(context, "无法打开的链接", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, LWebActivity.class);
        intent.putExtra(KEY_URL, LHExts.fixUrl(url));
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    public static void startActivityStartHttp(Context context, String url, String title) {
        if (LEmptyX.isEmpty(url)) {
            Toast.makeText(context, "无法打开的链接", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, LWebActivity.class);
        intent.putExtra(KEY_URL, url);
        intent.putExtra(KEY_TITLE, title);
        context.startActivity(intent);
    }

    public static void startSystemBrowser(Context context, String url) {
        // 打开网址 这个是通过打开android自带的浏览器进行的打开网址
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            // 网址正确 跳转成功
            context.startActivity(intent);
        } else {
            //网址不正确 跳转失败 提示错误
            Toast.makeText(context, "网址输入错误，请重新输入！", Toast.LENGTH_SHORT).show();
        }
    }

    LTitleView mTitleView;

    SmartRefreshLayout mRefreshSrl;

    WebView mWebContent;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.lweb_activity);
        mTitleView = findViewById(R.id.title_view);
        mRefreshSrl = findViewById(R.id.refresh_srl);
        mWebContent = findViewById(R.id.web_content);

        mTitle = getIntent().getStringExtra(KEY_TITLE);
        mTitleView.setTitle(mTitle);
        mTitleView.setClickLeftFinish(this);
        // 如果想要打开刷新，在此开启
        LViewX.initSmartRefresh(mRefreshSrl, null, null);
        mWebContent.getSettings().setJavaScriptEnabled(true);
        mWebContent.loadUrl(getIntent().getStringExtra(KEY_URL));

        //mWebContent.setWebViewClient(new WebViewClient());
//        mWebContent.setWebChromeClient(new WebChromeClient() {
//
//            @Override
//            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
//                AlertDialog.Builder b = new AlertDialog.Builder(LWebActivity.this);
//                b.setTitle("Alert");
//                b.setMessage(message);
//                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                b.setCancelable(false);
//                b.create().show();
//                return true;
//            }
//
//            //设置响应js 的Confirm()函数
//            @Override
//            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
//                AlertDialog.Builder b = new AlertDialog.Builder(LWebActivity.this);
//                b.setTitle("Confirm");
//                b.setMessage(message);
//                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.confirm();
//                    }
//                });
//                b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                });
//                b.create().show();
//                return true;
//            }
//
//            //设置响应js 的Prompt()函数
//            @Override
//            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result) {
//                final View v = View.inflate(LWebActivity.this, R.layout.prompt_dialog, null);
//                ((TextView) v.findViewById(R.id.prompt_message_text)).setText(message);
//                ((EditText) v.findViewById(R.id.prompt_input_field)).setText(defaultValue);
//                AlertDialog.Builder b = new AlertDialog.Builder(LWebActivity.this);
//                b.setTitle("Prompt");
//                b.setView(v);
//                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String value = ((EditText) v.findViewById(R.id.prompt_input_field)).getText().toString();
//                        result.confirm(value);
//                    }
//                });
//                b.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        result.cancel();
//                    }
//                });
//                b.create().show();
//                return true;
//            }
//
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LFixInputMethodManagerLeak.fixLeak(this);
    }
}
