package com.faizinahsan.cataloguemovie.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.faizinahsan.cataloguemovie.NotificationSettings;
import com.faizinahsan.cataloguemovie.R;

public class MyPreferenceFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    private boolean DEFAULT_VALUE = false;
    private String dailyReminder;
    private String releaseNotif;
    private SwitchPreference dailyPreference;
    private SwitchPreference releasePreference;
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
                Toast.makeText(getContext(), "Daily Reminder ON", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Daily Reminder OFF", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        if (key.equals(releaseNotif)){
            if (isOn){
                Toast.makeText(getContext(), "Release Notif ON", Toast.LENGTH_SHORT).show();
            }else{
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
