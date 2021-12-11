# Extending the UnityPlayerActivity on Android

***Note:*** *This process is specific to the **Android** platform and not applicable to other platforms.*

On Android, it is possible to extend `UnityPlayerActivity` to override existing interactions between Unity and Android OS or introduce new behaviors.

From [the Unity documentation](https://docs.unity3d.com/Manual/AndroidUnityPlayerActivity.html):

> When you develop a Unity Android application, you can use plug-ins to extend the standard UnityPlayerActivity class (the primary Java class for the Unity Player on Android, similar to AppController.mm on Unity iOS). An application can override all basic interactions between the Android OS and the Unity Android application.

This project allows you to extend `UnityPlayerActivity` in an isolated source-control friendly Android Studio project, without a need to work on the exported Android Studio project of your game. It also removes the need to copy, maintain (and even worse, include) the file `classes.jar` from the Unity installation.

## Table of contents

- [Creating the project from scratch](#creating-the-project-from-scratch)
- [Usage](#usage)
   * [Android Studio](#android-studio)
      + [Update references to the project name](#update-references-to-the-project-name)
      + [Update local dependencies](#update-local-dependencies)
      + [Update the mock UnityPlayerActivity file with the original (Optional)](#update-the-mock-unityplayeractivity-file-with-the-original--optional-)
      + [Export plugin from Android Studio](#export-plugin-from-android-studio)
   * [Unity](#unity)
      + [Import plugin to Unity](#import-plugin-to-unity)
      + [Run on device](#run-on-device)
   * [Further improvements](#further-improvements)

## Creating the project from scratch

You can create this project from scratch following [the guide in my devblog](https://guneyozsan.github.io/extending-the-unity-player-activity-on-android/).

It is suggested create the project from scratch if you are using a significantly newer version of Android Studio, or you would like to keep the references of the tests removed.

This project was created using *Android Studio Arctic Fox 2020.3.1*.

## Usage

### Android Studio

Open the folder `MyUnityPlayerActivity` in Android Studio.

#### Update references to the project name

1. Rename the package name `com.mycompany.myunityproject.player` (e.g. `com.awesomegamestudio.veryfungame`) in the following files.
   1. Rename in `app/manifests/AndroidManifest.xml`.
   2. Rename in `app/java/com.mycompany.myunityproject.player/MyUnityPlayerActivity.java`.
      1. In `MyUnityPlayerActivity.java` package name will be marked red. Hover on it (or `Right click/Show context actions`), and select `Move to package 'NEW_PACKAGE_NAME'`.
2. Rebuild the project from `Build/Recompile 'MyUnityPlayerActivity.java'`.
3. Delete folder `app/java/com/mycompany`.

#### Update local dependencies

There are a couple of references to the local Unity installation. You need to update those Unity versions to match your Unity version installed locally.

1. Find the following lines in the `dependencies` sections of `build.gradle` files for both `app` and `player` modules.
   ```js
   compileOnly files('C:/Program Files/Unity/Hub/Editor/UNITY_VERSION/Editor/Data/PlaybackEngines/AndroidPlayer/Variations/mono/Release/Classes/classes.jar)
   ```
2. Update the Unity version to match the version of your local installation.

#### Update the mock UnityPlayerActivity file with the original (Optional)

This project includes a stripped-off version of the Unity file `UnityPlayerActivity.java`. This allows extending `MyUnityPlayerActivity` from `UnityPlayerActivity` class while keeping the reference `import com.unity3d.player.UnityPlayerActivity;` of `MyUnityPlayerActivity` intact. The original file is not included to prevent licensing issues.

If you ever need a specific usage or use the most recent version of `UnityPlayerActivity.java`, you can replace the mock file with the original one by copying from `C:\Program Files\Unity\Hub\Editor\YOUR_UNITY_VERSION\Editor\Data\PlaybackEngines\AndroidPlayer\src\com\unity3d\player` to `/player/java/com/unity3d/player/`.

#### Export plugin from Android Studio

1. Select build variant `debug` or `release` from `Build/Select Build Variant...`. *(Use build variant `debug` to see the logs in logcat.)*
2. In Android Studio `Build/Make` will build `.aar` to `/app/build/outputs/aar/app-release.aar`.

### Unity

Open the folder `MyUnityProject` (or your own Unity project) in Unity.

#### Import plugin to Unity

1. Copy the `.aar` file built in previous step to Unity project into the folder `Assets/Plugins/Android/libs/MyUnityPlayerActivity/`.
2. Customize Unity AndroidManifest *(need to be done only once)*:
   1. Enable `Custom Main Manifest` at `Project Settings/Player/Publishing Settings/Build`.
   2. Open `Assets/Plugins/Android/AndroidManifest.xml` for editing. Edit activity attribute from `<activity android:name="com.unity3d.player.UnityPlayerActivity" ...` to `<activity android:name="com.mycompany.myunityproject.player.MyUnityPlayerActivity" ... >`.
3. If you introduced any *non-compile-only* dependencies in `build.gradle (app)` of MyUnityPlayerActivity:
   1. Create a file `/Assets/MyUnityPlayerActivity/Editor/MyUnityPlayerActivityDependencies.xml`.
   2. Declare those dependencies in this file using the format below.

      For example, if your `build.gradle (app)` looks like this:

      ```js
      dependencies {
          implementation 'androidx.appcompat:appcompat:1.3.1'
          implementation 'com.google.android.gms:play-services-auth:19.2.0'

          //noinspection GradlePath
          compileOnly files('C:/Program Files/Unity/Hub/Editor/2019.4.33f1/Editor/Data/PlaybackEngines/AndroidPlayer/Variations/mono/Release/Classes/classes.jar')
          compileOnly project(':player')
      }
      ```

      Then your `MyUnityPlayerActivityDependencies.xml` should look like this:
      ```xml
      <dependencies>
          <androidPackages>
              <androidPackage spec="androidx.appcompat:appcompat:1.3.1"/>
              <androidPackage spec="com.google.android.gms:play-services-auth:19.2.0"/>
          </androidPackages>
      </dependencies>
      ```
   3. Use [External Dependency Manager for Unity](https://github.com/googlesamples/unity-jar-resolver) to resolve dependencies.

#### Run on device

1. In Unity, make sure the *Android* platform is selected in `Build Settings`.
2. Use `Build Settings/Build And Run` to run the application on a connected Android device.
3. If you used build variant `debug` while building the plugin in Android Studio, you should see the demo log `Running MyUnityPlayerActivity.` from overridden `onCreate()` in `adb logcat` (e.g. by running `adb logcat -s Unity` in command prompt, or by using the *Android Logcat* package in Unity).

### Further improvements

- Exporting Android Studio build and copying the built plugin into the Unity project can be automated for even a smoother workflow.
