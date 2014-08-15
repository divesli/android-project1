package com.example.tool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class BaseTool {
	
	public static boolean isNetWordViald(Context context) {
		try {
			ConnectivityManager cManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (cManager != null) {
				NetworkInfo nInfo = cManager.getActiveNetworkInfo();
				if (nInfo != null && nInfo.isConnected()) {
					if (nInfo.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void openWifi(Context context) {
		final Context _context = context;
		new AlertDialog.Builder(_context).setTitle("网络错误").setPositiveButton("请检查网络设置", new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				// TODO Auto-generated method stub
				if(android.os.Build.VERSION.SDK_INT > 10 ){
				     //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
					_context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
				} else {
					_context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
				}
			}
		}).show();
	}
}
