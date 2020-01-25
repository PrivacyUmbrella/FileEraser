package pu.file_eraser.hook;

import android.os.Build;
import android.system.Os;
import android.util.Log;

import androidx.annotation.RequiresApi;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import pu.file_eraser.global.Constant;
import pu.file_eraser.util.FileUtils;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
class OsHook implements IXposedHookZygoteInit {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "OsHook";

    @Override
    public void initZygote(StartupParam startupParam) {
        XposedHelpers.findAndHookMethod(Os.class, "remove", String.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                String path = (String) param.args[0];
                Log.i(TAG, "beforeHookedMethod: " + param.method.getName() + " path=" + path);
                FileUtils.erase(path);
            }
        });
    }
}
