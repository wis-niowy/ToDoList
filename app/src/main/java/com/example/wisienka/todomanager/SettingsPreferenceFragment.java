package com.example.wisienka.todomanager;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.wisienka.todomanager.R;

/**
 * Created by Wisienka on 2018-03-28.
 */

public class SettingsPreferenceFragment extends PreferenceFragment
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_preference);
    }
}
