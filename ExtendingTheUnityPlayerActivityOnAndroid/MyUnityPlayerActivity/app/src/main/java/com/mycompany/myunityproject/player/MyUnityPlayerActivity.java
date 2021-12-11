package com.mycompany.myunityproject.player;

import android.os.Bundle;
import android.util.Log;

import com.unity3d.player.UnityPlayerActivity;

public class MyUnityPlayerActivity extends UnityPlayerActivity {
    private static final String TAG = "Unity";

    // This override is not required for extending the UnityPlayerActivity.
    // It is included to test configuration on a device.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "Running MyUnityPlayerActivity.");
    }
}
