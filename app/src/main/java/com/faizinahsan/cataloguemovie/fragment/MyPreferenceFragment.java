package com.faizinahsan.cataloguemovie.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.NotificationSettings;
import com.faizinahsan.cataloguemovie.R;
import com.faizinahsan.cataloguemovie.notifications.AlarmService;
import com.faizinahsan.cataloguemovie.notifications.DailyReciever;
import com.faizinahsan.cataloguemovie.notifications.TodayReminder;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private boolean DEFAULT_VALUE = false;
    private String dailyReminder;
    private String releaseNotif;
    private SwitchPreference dailyPreference;
    private SwitchPreference releasePreference;
    private DailyReciever dailyReciever;
    private TodayReminder todayReminder;
    private AlarmService alarmService;
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        init();
        setSummaries();
    }
    private void init(){
        dailyReminder = getResources().getString(R.string.daily_key);
        releaseNotif = getResources().getString(R.string.release_key);
        dailyPreference = (SwitchPreference) findPreference(dailyReminder);
        releasePreference = (SwitchPreference) findPreference(releaseNotif);
        dailyPreference.setOnPreferenceChangeListener(this);
        releasePreference.setOnPreferenceChangeListener(this);
        dailyReciever = new DailyReciever();
         todayReminder = new TodayReminder();
         alarmService = new AlarmService();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(dailyReminder)){
            dailyPreference.setChecked(sharedPreferences.getBoolean(dailyReminder,DEFAULT_VALUE));
        }
        if (key.equals(releaseNotif)){
            releasePreference.setChecked(sharedPreferences.getBoolean(releaseNotif,DEFAULT_VALUE));
        }
    }
    private void setSummaries(){
        SharedPreferences sh = getPreferenceManager().getSharedPreferences();
        dailyPreference.setChecked(sh.getBoolean(dailyReminder,DEFAULT_VALUE));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object o) {
        String key = preference.getKey();
        boolean isOn = (boolean) o;
        if (key.equals(dailyReminder)){
            if (isOn){
//                dailyReciever.setRepeatingAlarm(getActivity(),dailyReciever.TYPE_REPEATING,"07:00",getString(R.string.daily_label));
                alarmService.setRepeatingAlarm(getActivity(), AlarmService.TYPE_REPEATING,"07:00", getString(R.string.daily_label));
                Toast.makeText(getContext(), "Daily Reminder ON", Toast.LENGTH_SHORT).show();

            }else{
//                dailyReciever.cancelAlarm(getActivity(),dailyReciever.TYPE_REPEATING);
                alarmService.cancelAlarm(getActivity(),AlarmService.TYPE_REPEATING);
                Toast.makeText(getContext(), "Daily Reminder OFF", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (key.equals(releaseNotif)){
            if (isOn){
                alarmService.setRepeatingAlarm(getActivity(), AlarmService.TYPE_REPEATING,"15:51", getString(R.string.release_notif_label));

                Toast.makeText(getContext(), "Release Notif ON", Toast.LENGTH_SHORT).show();
            }else{
                alarmService.cancelAlarm(getActivity(),AlarmService.TYPE_REPEATING);
                Toast.makeText(getContext(), "Release Notif OFF", Toast.LENGTH_SHORT).show();

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        return false;
    }

}
