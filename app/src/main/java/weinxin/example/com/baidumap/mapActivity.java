package weinxin.example.com.baidumap;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class mapActivity extends AppCompatActivity implements BaiduMap.OnMapStatusChangeListener{
    private MapView mMapView;
    private BaiduMap bdMap;
    boolean firstLoc = true; //是否首次定位
    private LocationService locationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        initView();
        initData();
    }

    void initView() {
        mMapView = findViewById(R.id.bmapView2);
        bdMap = mMapView.getMap();
    }

    void initData() {
        MapStatusUpdate update = MapStatusUpdateFactory.zoomTo(17);
        bdMap.setMapStatus(update);
        bdMap.setMyLocationEnabled(true);//开启定位图层
        locationService = ((LocationApplication) getApplication()).locationService;
        locationService.registerListener(mLocationClientListener);
        locationService.start();
        bdMap.setOnMapStatusChangeListener(this);
//        检索
    }
    private BDAbstractLocationListener mLocationClientListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation arg0) {
            // TODO Auto-generated method stub
            if (arg0 == null)
                return;
            float accuracy = arg0.getRadius();
            double lat = arg0.getLatitude();
            double lon = arg0.getLongitude();
            MyLocationData.Builder build = new MyLocationData.Builder();
            build.accuracy(accuracy);
            build.latitude(lat);
            build.longitude(lon);
            MyLocationData myLocationData = build.build();
            bdMap.setMyLocationData(myLocationData);//设置定位数据
            if (firstLoc) {
                firstLoc = false;
                MapStatus.Builder mapStatusBuilder = new MapStatus.Builder();
                LatLng ll = new LatLng(lat, lon);
                mapStatusBuilder.target(ll);
                //mapStatusBuilder.zoom(18.0f);
                MapStatus mapStatus = mapStatusBuilder.build();
                MapStatusUpdate mapUpdate = MapStatusUpdateFactory
                        .newMapStatus(mapStatus);
                bdMap.animateMapStatus(mapUpdate);

            }
        }
    };

    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        locationService.stop();
        bdMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }




    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
            Log.d("xiaowen",mapStatus.target.latitude+"纬度");
            Log.d("xiaowen",mapStatus.target.longitude+"经度");
    }


}
