package com.example.aces.shendamap.application;

import android.Manifest;
import android.content.pm.PackageManager;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import com.baidu.mapapi.utils.poi.BaiduMapPoiSearch;
import com.baidu.mapapi.utils.poi.PoiParaOption;
import com.example.aces.shendamap.R;

import java.util.ArrayList;
import java.util.List;




public class GetLocation extends AppCompatActivity implements View.OnClickListener{
    //地图的总控制器
    private BaiduMap baiduMap;
    //防止多次调用animateMapStatus（）方法
    private boolean isFirstLocate = true;
    public static String Area;
    public LocationClient mLocationClient = null;

    private MapView mapview;
    private EditText editText;
    public static double lati;
    public static double longa;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());//设置监听
        //intiation方法接收一个全局的context参数，
        SDKInitializer.initialize(getApplicationContext());
        //显示
        setContentView(R.layout.activity_location);
        mapview = (MapView) findViewById(R.id.mapview);
        //获取baiduMap的实例
        baiduMap = mapview.getMap();
        Button button=(Button)findViewById(R.id.btn_poi);
        editText=(EditText)findViewById(R.id.edit_text) ;
        button.setOnClickListener(this);
        baiduMap.setMyLocationEnabled(true);//让设备和地图的定位点一起移动，允许百度地图进行定位


        //获取访问权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(GetLocation.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(GetLocation.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(GetLocation.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(GetLocation.this, permissions, 1);

        } else {
            requestLocation();//开始

        }
    }

    //按钮点击事件
    public void onClick(View v)
    {
        switch(v.getId()){
            case R.id.btn_poi:
                Area=editText.getText().toString();
                poiSearch(Area);

                break;

        }
    }


    //这个方法用来把地图中心点位置移动到我的位置
    private void navigateTO(BDLocation location) {
        if (isFirstLocate) {
            //loacation 的内容被封装到LatLng对象中，
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //移动到我的位置
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);

            //设置缩放，确保屏幕内有我，精度为1-20
            update = MapStatusUpdateFactory.zoomTo(19);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }

        MyLocationData.Builder locationBuileder = new MyLocationData.Builder();
        locationBuileder.latitude(location.getLatitude());
        locationBuileder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuileder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void requestLocation() {
        //初始化
        initLocation();
        //开始定位
        mLocationClient.start();

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        ////可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        int span = 0;
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(span);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "要同意所有权限才能开启程序哟:)", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestLocation();
                } else {
                    Toast.makeText(this, "error!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }

    }

    //重写onResume(),onPause(),onDestroy()方法，保证资源及时释放
    protected void onDestroy() {
        super.onDestroy();
        mapview.onDestroy();
        //程序退出要关掉定位
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapview.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapview.onPause();
    }

    //BDLocationListener：定位的回调接口
    //定位成功之后一定会调用onReceiveLocation方法，
    //BDLocation 存放定位成功后的回调数据
    public class MyLocationListener implements BDLocationListener {
        public void onReceiveLocation(BDLocation location) {
            if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
                lati = location.getLatitude();
                longa = location.getLongitude();
                navigateTO(location);//移动到我的位置


            }
        }
    }


    public void poiSearch(String area)
    {
        BaiduMapPoiSearch poi =new BaiduMapPoiSearch();
        //poi检索的参数
        PoiParaOption opt =new PoiParaOption();
        //搜素关键字
        opt.key(area);
        //搜素半径:200m
        opt.radius(200);
        //搜锁位置的中心
        LatLng point1=new LatLng(lati,longa);
        opt.center(point1);
        poi.openBaiduMapPoiNearbySearch(opt,this);
    }
}

