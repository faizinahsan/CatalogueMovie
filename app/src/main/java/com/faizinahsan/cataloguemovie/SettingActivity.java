package com.faizinahsan.cataloguemovie;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.faizinahsan.cataloguemovie.notifications.DailyReminder;
import com.faizinahsan.cataloguemovie.notifications.NotificationPreference;

import static com.faizinahsan.cataloguemovie.notifications.Utils.KEY_FIELD_DAILY_REMINDER;
import static com.faizinahsan.cataloguemovie.notifications.Utils.KEY_FIELD_UPCOMING_REMINDER;
import static com.faizinahsan.cataloguemovie.notifications.Utils.KEY_HEADER_DAILY_REMINDER;
import static com.faizinahsan.cataloguemovie.notifications.Utils.KEY_HEADER_UPCOMING_REMINDER;

public class SettingActivity extends AppCompatActivity {
    public static final String TYPE_REMINDER_RECIEVE = "reminderAlarmRelease";
    DailyReminder dailyReminder;
    NotificationPreference notificationPreference;
    public SharedPreferences sReleaseReminder, sDailyReminder;
    public SharedPreferences.Editor editorReleaseReminder, editorDailyReminder;
    Switch setCheckedDailyReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        dailyReminder = new DailyReminder();
        notificationPreference = new NotificationPreference(this);

        setCheckedDailyReminder = findViewById(R.id.daily_reminder);
        setPreference();
    }

    private void dailyReminderOn() {
        String time = "12:46";
        String message = getResources().getString(R.string.daily_reminder);
        notificationPreference.setReminderDailyTime(time);
        notificationPreference.setReminderDailyMessage(message);
        dailyReminder.setAlarm(SettingActivity.this, TYPE_REMINDER_RECIEVE, time, message);
    }

    private void dailyReminderOff() {
        dailyReminder.cancelAlarm(SettingActivity.this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


//Fungsi pengaturan daily reminder
    public void setDailyReminder(boolean isChecked) {
        editorDailyReminder = sDailyReminder.edit();
        if (isChecked) {
            editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, true);
            editorDailyReminder.commit();
            dailyReminderOn();
        } else {
            editorDailyReminder.putBoolean(KEY_FIELD_DAILY_REMINDER, false);
            editorDailyReminder.commit();
            dailyReminderOff();
        }
    }

//    @OnCheckedChanged(R.id.release_Reminder)
//    public void setReleaseReminder(boolean isChecked) {
//        editorReleaseReminder = sReleaseReminder.edit();
//        if (isChecked) {
//            editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, true);
//            editorReleaseReminder.commit();
//            releaseReminderOn();
//        } else {
//            editorReleaseReminder.putBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
//            editorReleaseReminder.commit();
//            releaseReminderOff();
//        }
//    }

    private void setPreference() {
        sReleaseReminder = getSharedPreferences(KEY_HEADER_UPCOMING_REMINDER, MODE_PRIVATE);
        sDailyReminder = getSharedPreferences(KEY_HEADER_DAILY_REMINDER, MODE_PRIVATE);
//        boolean checkUpcomingReminder = sReleaseReminder.getBoolean(KEY_FIELD_UPCOMING_REMINDER, false);
//        releaseReminder.setChecked(checkUpcomingReminder);
        boolean checkDailyReminder = sDailyReminder.getBoolean(KEY_FIELD_DAILY_REMINDER, false);
        setCheckedDailyReminder.setChecked(checkDailyReminder);
        boolean isCheckedDailyReminder = setCheckedDailyReminder.isChecked();
        setDailyReminder(isCheckedDailyReminder);
    }
}
