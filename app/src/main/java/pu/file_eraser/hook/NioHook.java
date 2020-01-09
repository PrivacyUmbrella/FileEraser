package pu.file_eraser.hook;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import pu.file_eraser.global.Constant;
import pu.file_eraser.util.FileUtils;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NioHook implements IXposedHookZygoteInit {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "NioHook";

    private final XC_MethodHook mHookCallback = new XC_MethodHook() {

        @Override
        protected void beforeHookedMethod(MethodHookParam param) {
            String
                    path = ((Path) param.args[0]).normalize().toString(),
                    methodName = param.method.getName();
            Log.i(TAG, "beforeHookedMethod: " +
                    methodName +
                    " path=" + path);
            switch (methodName) {
                case "delete":
                    FileUtils.erase(path);
                    break;
                case "deleteIfExists":
                    if (new File(path).exists()) FileUtils.erase(path);
                    else param.setResult(false);
                    break;
            }
        }
    };

    @Override
    public void initZygote(StartupParam startupParam) {
        XposedHelpers.findAndHookMethod(Files.class, "delete", Path.class, mHookCallback);
        XposedHelpers.findAndHookMethod(Files.class, "deleteIfExists", Path.class, mHookCallback);
    }
}
