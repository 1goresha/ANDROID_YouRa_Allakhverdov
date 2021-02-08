package com.example.cooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener
        , Preference.OnPreferenceChangeListener {//предназначен для отображения фрагмента с настройками из файла timer_preferences.xml

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.timer_preferences);//добавляем разметку для фрагмента

        PreferenceScreen preferenceScreen = getPreferenceScreen();
        sharedPreferences = preferenceScreen.getSharedPreferences();

        int count = preferenceScreen.getPreferenceCount();
        for (int i = 0; i < count; i++) {
            Preference preference = preferenceScreen.getPreference(i);
            if (preference instanceof ListPreference) {
                String value = sharedPreferences.getString(preference.getKey(), "");// в SharedPreference находятся все ключ-значения
                // , которые туда сохраняются и от туда же их можно извлечь, используя метод getString(preference.getKey(),"")
                ListPreference listPreference = (ListPreference) preference;
                listPreference.setSummary(value);
            }
            if (preference instanceof EditTextPreference) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                EditTextPreference editTextPreference = (EditTextPreference) preference;
                editTextPreference.setSummary(value);
            }
        }

        Preference preference = findPreference("timer_duration");
        if (preference != null) {
            preference.setOnPreferenceChangeListener(this);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            String value = sharedPreferences.getString(key, "");
            ListPreference listPreference = (ListPreference) preference;
            listPreference.setSummary(value);
        }
        if (preference instanceof EditTextPreference) {
            String value = sharedPreferences.getString(key, "");
            EditTextPreference editTextPreference = (EditTextPreference) preference;
            editTextPreference.setSummary(value);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {//срабатывает до OnSharedPreferencesChanged
        String s = (String) newValue;
        if (preference.getKey().equals("timer_duration")) {
            try {
                int value = Integer.parseInt(s);
                preference.setSummary(value + "");
            } catch (NumberFormatException nfe) {
                Toast.makeText(getContext(), "wrong number format", Toast.LENGTH_SHORT).show();
                return false;
            } catch (Exception e) {
                Toast.makeText(getContext(), "something went wrong", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}
