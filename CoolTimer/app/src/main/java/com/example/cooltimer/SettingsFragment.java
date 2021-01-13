package com.example.cooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat {//предназначен для отображения фрагмента с настройками из файла timer_preferences.xml

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.timer_preferences);//добавляем разметку для фрагмента

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        SharedPreferences sharedPreferences = preferenceScreen.getSharedPreferences();

        int count = preferenceScreen.getPreferenceCount();
        String value;
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof ListPreference) {
                value = sharedPreferences.getString(preference.getKey(), "");// в SharedPreference находятся все ключ-значения
                // , которые туда сохраняются и от туда же их можно извлечь, используя метод getString(preference.getKey(),"")
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(value);
            }
            int a = 0;

        }
    }

}
