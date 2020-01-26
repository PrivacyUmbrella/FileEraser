package pu.file_eraser.util;

import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

public class EnvUtils {

    private static final String TAG = "EnvUtils";

    private EnvUtils() {
    }

    /**
     * https://developer.android.com/training/tv/start/hardware#runtime-check
     *
     * @param context {@link Context}
     * @return bool
     */
    public static boolean isTvDevice(Context context) {
        UiModeManager uiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
        if (uiModeManager != null) {
            return uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_TELEVISION;
        } else {
            Log.w(TAG, "isTvDevice: UiModeManager is null.");
            return false;
        }
    }
}
