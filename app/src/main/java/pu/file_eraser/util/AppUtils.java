package pu.file_eraser.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

public class AppUtils {

    private static final String TAG = "AppUtils";

    private AppUtils() {
    }

    public static String getLabel(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return context.getString(info.applicationInfo.labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "getLabel: ", e);
        }
        return null;
    }
}
