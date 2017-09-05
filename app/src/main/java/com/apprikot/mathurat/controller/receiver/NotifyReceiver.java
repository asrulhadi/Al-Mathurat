package com.apprikot.mathurat.controller.receiver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.apprikot.mathurat.R;
import com.apprikot.mathurat.controller.activities.MainActivity;
import com.apprikot.mathurat.controller.utils.PrefHelp;
import com.apprikot.mathurat.model.Notify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class NotifyReceiver extends BroadcastReceiver {

	public static String TAG = NotifyReceiver.class.getSimpleName();

	public final long TIME_TOLERANCE = 2L * 1000L * 60L;
	public static final String ID = "thaker_id";
	public static final String TIME_HOUR = "timeHour";
	public static final String TIME_MINUTE = "timeMinute";
	public static final String DATA = "thaker_data";
	public static final String TONE = "alarmTone";
	public static final String TYPE = "alarmType";
	public static final String EXTRA_ALARM_TIME = "dateTime";
	public Context context;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO: show notification
		this.context=context;
		long time = intent.getExtras().getLong(EXTRA_ALARM_TIME);
		int id = intent.getExtras().getInt(ID);
		Notify data = intent.getExtras().getParcelable(DATA);
		if (Math.abs(System.currentTimeMillis() - time) % AlarmManager.INTERVAL_DAY > TIME_TOLERANCE) {
			return;
		}
		Intent notifyIntent = new Intent(context, MainActivity.class);
//		notifyIntent.putExtra(DATA, data);
		generate(notifyIntent, context.getString(R.string.app_name), context.getString(R.string.thaker_time_to_read), id);
		setAlarms(context);
	}


	public static void setAlarms(Context context) {
		cancelAlarms(context);
		Notify notify = PrefHelp.getAllNotify(context);

		if (notify.isNotify) {
			List<Boolean> notifyDays = prepareDays(notify.notify_days);
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR, notify.notify_hour);
			calendar.set(Calendar.MINUTE, notify.notify_min);
			calendar.set(Calendar.AM_PM, notify.notify_am_pm);
			calendar.set(Calendar.SECOND, 0);
			PendingIntent pIntent = createPendingIntent(context, notify, calendar.getTimeInMillis());

			final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
			final int nowHour = Calendar.getInstance().get(Calendar.HOUR);
			final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);
			boolean alarmSet = false;

			//First check if it's later in the week
			for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
				if (notifyDays.get(dayOfWeek - 1) && dayOfWeek >= nowDay &&
						!(dayOfWeek == nowDay && notify.notify_hour < nowHour) &&
						!(dayOfWeek == nowDay && notify.notify_hour == nowHour && notify.notify_min <= nowMinute)) {
					calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
					setAlarm(context, calendar, pIntent);
					alarmSet = true;
					break;
				}
			}
			//Else check if it's earlier in the week
			if (!alarmSet) {
				for (int dayOfWeek = Calendar.SUNDAY; dayOfWeek <= Calendar.SATURDAY; ++dayOfWeek) {
					if (notifyDays.get(dayOfWeek - 1) && dayOfWeek <= nowDay) {
						calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
						calendar.add(Calendar.WEEK_OF_YEAR, 1);
						setAlarm(context, calendar, pIntent);
						alarmSet = true;
						break;
					}
				}
			}
		}
	}

	@SuppressLint("NewApi")
	private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		if (android.os.Build.VERSION.SDK_INT >= 23){
			alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		} else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		} else {
			alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
		}
	}

	public static void cancelAlarms(Context context) {
		Notify notify =  PrefHelp.getAllNotify(context);
		if(notify == null){
			Log.d(TAG, " store file null ");
			return;
		}
		if (notify.isNotify) {
			PendingIntent pIntent = createPendingIntent(context, notify, 0);
			AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			alarmManager.cancel(pIntent);
		}
	}

	private static PendingIntent createPendingIntent(Context context, Notify data, long timeMs) {
		Intent intent = new Intent(context, NotifyReceiver.class);
		intent.putExtra(ID, data.id);
		intent.putExtra(DATA, data);
		intent.putExtra(TIME_HOUR, data.notify_hour);
		intent.putExtra(TIME_MINUTE, data.notify_min);
		intent.putExtra(EXTRA_ALARM_TIME, timeMs);
		return PendingIntent.getBroadcast(context, data.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);

	}


	private void generate(Intent notificationIntent, String title, String body, int id) {
//		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		Notification notification = NotificationUtil.createNotification(context, contentIntent, title, body, R.drawable.notify_on);
//		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		manager.cancel(id);
//		manager.notify(id, notification);

		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
		Uri alarmSound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.notification);
		builder.setSmallIcon(R.drawable.notify_on);
		builder.setSound(alarmSound);
		builder.setContentIntent(contentIntent);
		builder.setContentTitle(title);
		builder.setContentText(body);
		Notification notification = builder.build();
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancel(id);
		manager.notify(id, notification);

	}

	private static List<Boolean> prepareDays(String str) {
		List<Boolean> repeatingDays = new ArrayList<>();
		String[] daysArray = str.split(",");
		for (int i = 0; i < daysArray.length; ++i) {
			repeatingDays.add(i, Boolean.valueOf(daysArray[i]));
		}
		return repeatingDays;
	}
}
