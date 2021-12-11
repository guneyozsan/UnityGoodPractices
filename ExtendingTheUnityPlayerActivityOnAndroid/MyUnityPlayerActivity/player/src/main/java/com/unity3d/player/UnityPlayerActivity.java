// This is a stripped of version of the Unity file `UnityPlayerActivity.java`.
// Original file is not included to prevent licensing issues.
//
// If you ever need a specific usage, you can copy the original file from
// `C:\Program Files\Unity\Hub\Editor\YOUR_UNITY_VERSION\Editor\Data\PlaybackEngines\AndroidPlayer\src\com\unity3d\player`.

package com.unity3d.player;

import android.app.Activity;

public class UnityPlayerActivity extends Activity implements IUnityPlayerLifecycleEvents
{
    // When Unity player unloaded move task to background
    @Override public void onUnityPlayerUnloaded() {
    }

    // When Unity player quited kill process
    @Override public void onUnityPlayerQuitted() {
    }
}
