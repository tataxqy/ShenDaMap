package com.example.aces.shendamap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.example.aces.shendamap.R;

import com.example.aces.shendamap.application.ExpressActivity;

/**
 * Created by Aces on 2018/6/21.
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private Button ok;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, null);
        findView(view);
        return view;
    }

    private void findView(View view) {
        ok=(Button)view.findViewById(R.id.btn_ok);
        ok.setOnClickListener(this);

    }
    public void onClick(View v) {
        switch (v.getId())
        {
            //跳转到
            case R.id.btn_ok:
                startActivity(new Intent(getActivity(), ExpressActivity.class));
                break;

        }
    }
}






