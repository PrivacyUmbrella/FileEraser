package pu.file_eraser.hook;

import android.util.Log;

import java.io.File;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import pu.file_eraser.global.Constant;
import pu.file_eraser.util.FileUtils;

class IoHook implements IXposedHookZygoteInit {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "IoHook";

    private final XC_MethodHook mHookCallback = new XC_MethodHook() {

        @Override
        protected void beforeHookedMethod(MethodHookParam param) {
            String path = ((File) param.thisObject).getAbsolutePath();
            boolean val = FileUtils.erase(path);
            Log.i(TAG, "beforeHookedMethod: " +
                    param.method.getName() +
                    " path=" + path + " val=" + val);
        }
    };

    @Override
    public void initZygote(StartupParam startupParam) {
        XposedHelpers.findAndHookMethod(File.class, "delete", mHookCallback);

        // 不应该被调用
        XposedHelpers.findAndHookMethod(File.class, "deleteOnExit", mHookCallback);
    }
}
