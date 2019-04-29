package com.faizinahsan.cataloguemovie.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.faizinahsan.cataloguemovie.MainActivity;
import com.faizinahsan.cataloguemovie.R;

import java.util.Calendar;

public class DailyReminder extends BroadcastReceiver {
    public static final int NOTIFICAITION_ID = 1;
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "cataloguemovie channel";
    public static final String EXTRA_MESSAGE_PREF = "message";
//    public static final String EXTRA_TYPE_RECIEVE = "typeRelease";
    public static final String EXTRA_TYPE_PREF = "type";
    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;

    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotificaition(context, context.getResources().getString(R.string.message_daily), intent.getStringExtra(EXTRA_MESSAGE_PREF),NOTIFICAITION_ID);
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mNotificationManager.notify(NOTIFICAITION_ID, mBuilder.build());
        }
    };

    private void sendNotificaition(Context context, String title, String desc, int id){
        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICAITION_ID,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID).setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notifications_black_24dp))
                .setContentTitle(title)
                .setContentText(desc)
                .setSubText(context.getResources().getString(R.string.subtext))
                .setAutoCancel(true);
    /*
    Untuk android Oreo ke atas perlu menambahkan notification channel
    */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(NOTIFICAITION_ID, notification);
        }
        new Handler().postDelayed(runnable,5000);
    }
    public void setAlarm(Context context, String type, String time, String message){
        cancelAlarm(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminder.class);
        intent.putExtra(EXTRA_MESSAGE_PREF,message);
        intent.putExtra(EXTRA_TYPE_PREF,type);
        String timeArray[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND,0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICAITION_ID,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
    public void cancelAlarm(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICAITION_ID,intent,0);
        alarmManager.cancel(pendingIntent);

    }
}
