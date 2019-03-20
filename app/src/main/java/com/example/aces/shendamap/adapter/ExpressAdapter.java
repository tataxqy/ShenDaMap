package com.example.aces.shendamap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aces.shendamap.R;
import com.example.aces.shendamap.fragment.UserFragment;

import java.util.List;

import baidu.mapapi.overlayutil.BusLineOverlay;

/**
 * Created by Aces on 2018/6/27.
 */

public class ExpressAdapter extends BaseAdapter{

    private Context mContext;
    private List<Expressdata> mList;
    //布局加载器
    private LayoutInflater inflater;
    private Expressdata data;

    //构造方法，传入上下文，数据，一个item对应一类<Expressdata>
    public ExpressAdapter(Context mContext, List<Expressdata> mList) {
        this.mContext = mContext;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    //重写BaseAdapter的四个方法

    //返回长度
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    //返回ID
    public long getItemId(int position) {
        return position;
    }

    //初始化数据
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
             ViewHolder viewHolder = null;
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layoutexpress,null);//每个item
            viewHolder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = (TextView) convertView.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = (TextView) convertView.findViewById(R.id.tv_datetime);
            //设置缓存
            convertView.setTag(viewHolder);

            viewHolder = (ViewHolder) convertView.getTag();


        //设置数据
        data = mList.get(position);
        //获取
        viewHolder.tv_remark.setText(data.getRemark());//将数据放入item
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());
        return convertView;
    }

    class ViewHolder{
        private TextView tv_remark;
        private TextView tv_zone;
        private TextView tv_datetime;
    }

    }

