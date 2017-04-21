package bicycle.xiaohuanxiongscan;

import android.app.Activity;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zbar.lib.CaptureActivity;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends Activity implements EasyPermissions.PermissionCallbacks{
	private Button scan;
	private static final int RC_CAMERA_PERM = 123;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		scan=(Button) findViewById(R.id.scan);
		scan.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				cameraTask();


			}
		});
	}

	@AfterPermissionGranted(RC_CAMERA_PERM)
	public void cameraTask() {
		if (EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)) {
			// Have permission, do the thing!
			Log.i("444", "有相机");
			startActivity(new Intent(MainActivity.this, CaptureActivity.class));
		} else {
			Log.i("444", "无相机");
			// 禁止过（没设以后都禁止）
			EasyPermissions.requestPermissions(this, "小浣熊需要相机权限才能扫码哦~",
					RC_CAMERA_PERM, android.Manifest.permission.CAMERA);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
	}

	@Override
	public void onPermissionsGranted(int requestCode, List<String> perms) {
		Log.i("444", "Granted");
	}

	@Override
	public void onPermissionsDenied(int requestCode, List<String> perms) {
		Log.i("444", "Denied:" + "拒绝");
		if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
			Log.i("444", "Denied:" + "选了一直禁止该权限");
			new AppSettingsDialog.Builder(this, "您禁用了小浣熊的相机权限，需要打开才能使用相关功能，需要去手机设置中打开吗？")
					.setTitle("提示")
					.setPositiveButton("打开")
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					} /* click listener */)
					.setRequestCode(RC_CAMERA_PERM)
					.build()
					.show();
		}
	}

}
