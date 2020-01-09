package pu.file_eraser.util;

import android.app.Activity;
import android.content.pm.PackageManager;

public class UiUtils {

    private UiUtils() {
    }

    public static void hideLauncherIcon(Activity launchActivity) {
        launchActivity.getPackageManager()
                .setComponentEnabledSetting(launchActivity.getComponentName(),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER,
                        PackageManager.DONT_KILL_APP);
    }
}
