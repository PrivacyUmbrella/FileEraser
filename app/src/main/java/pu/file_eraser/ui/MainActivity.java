package pu.file_eraser.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import pu.file_eraser.global.Constant;
import pu.file_eraser.util.UiUtils;

public class MainActivity extends Activity {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String msg = "The activity is only a stub for \"Run 'app'\". " +
                "Hook enabled:" + Constant.isXposedEnabled();
        Log.i(TAG, "onCreate: " + msg);
        UiUtils.hideLauncherIcon(this);
        finish();
    }
}
