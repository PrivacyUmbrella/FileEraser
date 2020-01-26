package pu.file_eraser.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import pu.file_eraser.global.Constant;
import pu.file_eraser.util.EnvUtils;

public class MainActivity extends Activity {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate: The activity is only a stub for \"Run 'app'\". " +
                "Hook enabled:" + Constant.isXposedEnabled());
        if (EnvUtils.isTvDevice(this)) {
            throw new UnsupportedOperationException("I'm not a usable Android TV application.");
        } else {
            Log.d(TAG, "onCreate: debug");
            finish();
        }
    }
}
