package weinxin.example.com.baidumap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button but;
    private TextView text;
    private LocationService locationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=findViewById(R.id.text);
        but=findViewById(R.id.but);
        but.setOnClickListener(this);
        locationService = ((LocationApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.but:
                locationService.start();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationService.stop();
        locationService.unregisterListener(mListener); //注销掉监听
    }

    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取地址相关的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            /**
             * 时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
             * location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
             */
            sb.append(bdLocation.getTime());
            sb.append("\nlocType : ");// 定位类型
            sb.append(bdLocation.getLocType());
            sb.append("\nlocType description : ");// *****对应的定位类型说明*****
            sb.append(bdLocation.getLocTypeDescription());
            sb.append("\nlatitude : ");// 纬度
            sb.append(bdLocation.getLatitude());
            sb.append("\nlontitude : ");// 经度
            sb.append(bdLocation.getLongitude());
            sb.append("\nradius : ");// 半径
            sb.append(bdLocation.getRadius());
            sb.append("\nCountryCode : ");// 国家码
            sb.append(bdLocation.getCountryCode());
            sb.append("\nCountry : ");// 国家名称
            sb.append(bdLocation.getCountry());
            sb.append("\ncitycode : ");// 城市编码
            sb.append(bdLocation.getCityCode());
            sb.append("\ncity : ");// 城市
            sb.append(bdLocation.getCity());
            sb.append("\nDistrict : ");// 区
            sb.append(bdLocation.getDistrict());
            sb.append("\nStreet : ");// 街道
            sb.append(bdLocation.getStreet());
            sb.append("\naddr : ");// 地址信息
            sb.append(bdLocation.getAddrStr());
            sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
            sb.append(bdLocation.getUserIndoorState());
            sb.append("\nDirection(not all devices have value): ");
            sb.append(bdLocation.getDirection());// 方向
            sb.append("\nlocationdescribe: ");
            sb.append(bdLocation.getLocationDescribe());// 位置语义化信息
            sb.append("\nPoi: ");// POI信息

            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(bdLocation.getSpeed());// 速度 单位：km/h
                sb.append("\nsatellite : ");
                sb.append(bdLocation.getSatelliteNumber());// 卫星数目
                sb.append("\nheight : ");
                sb.append(bdLocation.getAltitude());// 海拔高度 单位：米
                sb.append("\ngps status : ");
                sb.append(bdLocation.getGpsAccuracyStatus());// *****gps质量判断*****
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                // 运营商信息
                if (bdLocation.hasAltitude()) {// *****如果有海拔高度*****
                    sb.append("\nheight : ");
                    sb.append(bdLocation.getAltitude());// 单位：米
                }
                sb.append("\noperationers : ");// 运营商信息
                sb.append(bdLocation.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (bdLocation.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (bdLocation.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (bdLocation.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            locationService.stop();
            text.setText(sb.toString());
        }
    };

}