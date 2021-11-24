// Replace this file

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
