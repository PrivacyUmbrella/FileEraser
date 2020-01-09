package pu.file_eraser;

import android.app.Application;
import android.util.Log;

import pu.file_eraser.global.Constant;

public class _Application extends Application {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "_Application";

    @Override
    public void onCreate() {
        Log.v(TAG, "-------onCreate-------");
        super.onCreate();
    }
}
