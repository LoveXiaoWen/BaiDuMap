package weinxin.example.com.baidumap;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.List;

public class Test extends AppCompatActivity implements View.OnClickListener {
    private Button but_gps, but_orientation, but_mulch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        but_mulch = findViewById(R.id.but_mulch);
        but_mulch.setOnClickListener(this);
        but_gps = findViewById(R.id.but_gps);
        but_gps.setOnClickListener(this);
        but_orientation = findViewById(R.id.but_orientation);
        but_orientation.setOnClickListener(this);
        // 如果你不想申请权限组，仅仅想申请某几个权限：
        AndPermission.with(this)
                .requestCode(300)
                .permission(
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .callback(listener)
                .start();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        switch (v.getId()) {
            case R.id.but_gps: //定位
                intent.setClass(this, MainActivity.class);
                break;
            case R.id.but_orientation://图层定位
                intent.setClass(this, Orientation.class);
                break;
            case R.id.but_mulch://覆盖物
                intent.setClass(this, mapActivity.class);
                break;

        }
        startActivity(intent);

    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 300) {
                // TODO ...
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 200) {
                // TODO ...
            }
        }
    };
}
