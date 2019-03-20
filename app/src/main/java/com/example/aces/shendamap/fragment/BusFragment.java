package com.example.aces.shendamap.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.aces.shendamap.R;

/**
 * Created by Aces on 2018/6/26.
 */

public class BusFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        //把一个View的对象与XML布局文件关联并实例化
        View view=inflater.inflate(R.layout.fragment_bus,null);
        findView(view);
        return view;
    }
    //View的对象实例化之后，可以通过findViewById()查找布局文件中的指定Id的组件
    private void findView(View view)
    {
        WebView webView=(WebView)view.findViewById(R.id.bus_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.lyztt.com.cn/html/bus20180510/bus/main/index.html");
    }
}