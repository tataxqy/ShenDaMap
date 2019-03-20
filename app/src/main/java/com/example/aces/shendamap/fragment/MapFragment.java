package com.example.aces.shendamap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.GetChars;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;

import com.example.aces.shendamap.R;
import com.example.aces.shendamap.application.GetLocation;

/**
 * Created by Aces on 2018/6/21.
 */

public class MapFragment extends Fragment implements View.OnClickListener {
    private Button start;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_map,null);
        findView(view);
        return view;
    }
    private void findView(View view)
    {
        start=(Button)view.findViewById(R.id.button_start);
        start.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            //跳转到GetLocation
            case R.id.button_start:
                startActivity(new Intent(getActivity(), GetLocation.class));
                break;

        }
    }
}
