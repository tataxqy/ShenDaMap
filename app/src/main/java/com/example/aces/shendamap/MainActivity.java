package com.example.aces.shendamap;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.example.aces.shendamap.fragment.BusFragment;
import com.example.aces.shendamap.fragment.MapFragment;
import com.example.aces.shendamap.fragment.UserFragment;
import com.example.aces.shendamap.fragment.ViewFragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //TabLayout
    private TabLayout mTabLayout;
    //ViewPager
    private ViewPager mViewPager;//容器
    //Title
    private List<String>mTitle;
    //Fragment
    private List<Fragment>mFragment;
    public LocationClient mLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //去掉阴影，阴影是ActionBar的控件，设置为0
        getSupportActionBar().setElevation(0);
        initData();
        initView();

    }
   //初始化
    private void initData()
    {
        mTitle = new ArrayList<>();
        mTitle.add("地图");
        mTitle.add("四处逛逛");
        mTitle.add("校园小巴");
        mTitle.add("快递服务");
        mFragment = new ArrayList<>();
        mFragment.add(new MapFragment());
        mFragment.add(new ViewFragment());
        mFragment.add(new BusFragment());
        mFragment.add(new UserFragment());
    }

    private void initView()
    {
        mTabLayout=(TabLayout)findViewById(R.id.mTabLayout);
        mViewPager=(ViewPager)findViewById(R.id.mViewPager);

        //有多少个Fragment就加载多少个
        mViewPager.setOffscreenPageLimit(mFragment.size());
        //设置适配器，管理
       mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
           //把选中的位置传进去，选中的子页
            public Fragment getItem(int position) {
                return mFragment.get(position);
            }

            //返回个数
            public int getCount() {
                return mFragment.size();
            }
            //把选中的位置传进去
            public CharSequence getPageTitle(int position) {
                return mTitle.get(position);
            }
        });
        //将TabLayout和ViewPager结合起来
        mTabLayout.setupWithViewPager(mViewPager);
    }

}
