package com.example.aces.shendamap.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.aces.shendamap.R;

/**
 * Created by Aces on 2018/6/21.
 */

public class ViewFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_view,null);
         findView(view);
        return view;
    }
    private void findView(View view)
    {
            WebView webView=(WebView)view.findViewById(R.id.web_view);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://5167889e8419.vt.iamh5.cn/v3/idea/2jFGM23m?unid=ohAJ7wRDOww9Ny0Wkebv5dH2GrO8&wxid=oi1821BT3-68MxQGrQx-bfSseZ3w&latestUser=1");
    }
}

