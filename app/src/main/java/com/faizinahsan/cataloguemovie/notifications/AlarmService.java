package com.faizinahsan.cataloguemovie.notifications;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.faizinahsan.cataloguemovie.MainActivity;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.api.APIService;
import com.faizinahsan.cataloguemovie.model.MovieResponse;
import com.faizinahsan.cataloguemovie.model.Movies;
import com.faizinahsan.cataloguemovie.model.TVResponse;
import com.faizinahsan.cataloguemovie.model.TVShows;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlarmService extends BroadcastReceiver {
    public static final String TYPE_RELEASE = "ReleaseTodayAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    public static String CHANNEL_ID = "channel_01";
    public static CharSequence CHANNEL_NAME = "calagouemovie channel";

    private final int NOTIF_ID_RELEASE = 100;
    private final int NOTIF_ID_REPEATING = 101;

    public ArrayList<TVShows> tvShows = new ArrayList<>();
    private static final String BASE_URL= "https://api.themoviedb.org/3/";
    private Retrofit retrofit;
    private void initializeRetrofit(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);

        String title = context.getResources().getString(R.string.app_name);
        int notifId = type.equalsIgnoreCase(TYPE_RELEASE) ? NOTIF_ID_RELEASE : NOTIF_ID_REPEATING;

        Log.v("ON RECIEVE",title+" "+notifId);

//        ShowAlarmNotification(context, title, message, notifId);
        if (message.equals(context.getResources().getString(R.string.release_notif_label))) {
            getMovieRelease(context, notifId);
        } else {
            ShowAlarmNotification(context, title, message, notifId);
        }
    }
    private void ShowAlarmNotification(Context context, String title, String message, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarm);

        /*
Untuk android Oreo ke atas perlu menambahkan notification channel
*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        notificationManager.notify(notifId, builder.build());
    }
    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);

//        String timeArray[] = time.split(":");
        String[] timeArray = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1);

        int requestCode = type.equalsIgnoreCase(TYPE_RELEASE) ? NOTIF_ID_RELEASE : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmService.class);

        int requestCode = type.equalsIgnoreCase(TYPE_RELEASE) ? NOTIF_ID_RELEASE : NOTIF_ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);
    }
    public void getMovieRelease(final Context context, final int notifId) {
        initializeRetrofit();
        APIService service = retrofit.create(APIService.class);
        Call<MovieResponse> call = service.getUpcommingMovie("0421fe7c9fd6d2048fb64bfca56e3819");
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//                tvShows = response.body().getResults();
                ArrayList<Movies> items = response.body().getResults();
                int index = new Random().nextInt(items.size());
//                TVShows item = items.get(index);
//                String title = items.get(index).getTitle();
//                String message = items.get(index).getOverview();
//                ShowAlarmNotification(context,title,message,notifId);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                Log.d("TAG","Date: "+currentDate);
                for (int i = 0; i<items.size(); i++){
                    String title = items.get(i).getTitle();
                    String release_date =items.get(i).getReleaseDate();
                    if (release_date.equals(currentDate)){
                        String message = "Today Movie, "+title+" has released";
                        ShowAlarmNotification(context,title,message,notifId);
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }
}
