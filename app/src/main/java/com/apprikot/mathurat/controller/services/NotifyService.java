package com.apprikot.mathurat.controller.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.apprikot.mathurat.controller.activities.NotifyScreen;
import com.apprikot.mathurat.controller.receiver.NotifyReceiver;

public class NotifyService extends Service {

	public static String TAG = NotifyService.class.getSimpleName();
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		Intent alarmIntent = new Intent(getBaseContext(), NotifyScreen.class);
		alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		alarmIntent.putExtras(intent);
		getApplication().startActivity(alarmIntent);
		NotifyReceiver.setAlarms(this);
		return super.onStartCommand(intent, flags, startId);
	}
	
}