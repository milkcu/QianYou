package com.xint78.qianyou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Queue;

public class GameDetailActivity extends AppCompatActivity {

    private DownloadManager downloadManager;
    private CompleteReceiver completeReceiver;

    class CompleteReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // get complete download id
            long completeDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Uri uri = downloadManager.getUriForDownloadedFile(completeDownloadId);

            Intent installIntent = new Intent();
            installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            installIntent.setAction(android.content.Intent.ACTION_VIEW);
            installIntent.setDataAndType(uri,"application/vnd.android.package-archive");
            startActivity(installIntent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        // init dl
        completeReceiver = new CompleteReceiver();
        /** register download success broadcast **/
        registerReceiver(completeReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        // init webview
        WebView detailView = (WebView) findViewById(R.id.detailView);
        //mWebView.getSettings().setJavaScriptEnabled(true);

        detailView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                downloadManager = ((DownloadManager) getSystemService(Activity.DOWNLOAD_SERVICE));
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

                if (!android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(GameDetailActivity.this, "外部存储卡不可用", Toast.LENGTH_SHORT).show();
                    return;
                }

                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "游戏名称" + ".apk");
                request.setTitle("你的某某游戏正在下载");
                request.setDescription("这里是通知栏游戏介绍");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setMimeType("application/vnd.android.package-archive");

                downloadManager.enqueue(request);

                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Toast.makeText(GameDetailActivity.this, "开始下载...", Toast.LENGTH_SHORT).show();

            }
        });
        detailView.loadUrl("http://milkcu.com/webview-test.html");
    }
}
