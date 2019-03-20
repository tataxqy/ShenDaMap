package com.example.aces.shendamap.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.aces.shendamap.R;
import com.example.aces.shendamap.adapter.ExpressAdapter;
import com.example.aces.shendamap.adapter.Expressdata;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by Aces on 2018/6/26.
 */

public class ExpressActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_name;
    private EditText et_number;
    private Button btn_get_courier;
    private ListView mListView;

    private List<Expressdata> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express);

        initView();
    }

    //初始化View
    private void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_number = (EditText) findViewById(R.id.et_number);
        btn_get_courier = (Button) findViewById(R.id.btn_done);
        btn_get_courier.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_done:

                //获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接我们的url
                String url = "http://v.juhe.cn/exp/index?key=" + "001a6525380e5d6a4b4ca36ec6e75792"
                        + "&com=" + name + "&no=" + number;

                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(number)) {
                    //请求数据（Json）
                    RxVolley.get(url, new HttpCallback() {

                        public void onSuccess(String t) {

                            //解析Json
                            parseJson(t);
                        }
                    });
                } else {
                    Toast.makeText(this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //解析数据
    private void parseJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            //获得result内的内容
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonArray = jsonResult.getJSONArray("list");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject json = (JSONObject) jsonArray.get(i);

                Expressdata data = new Expressdata();
                data.setRemark(json.getString("remark"));//获得状态
                data.setZone(json.getString("zone"));//获得地点
                data.setDatetime(json.getString("datetime"));//获得时间
                mList.add(data);
            }
            //倒序排列
            Collections.reverse(mList);
            ExpressAdapter adapter = new ExpressAdapter(this,mList);//this是当前context
            mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
