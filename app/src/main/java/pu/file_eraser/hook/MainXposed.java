package pu.file_eraser.hook;

import android.os.Build;
import android.util.Log;

import androidx.annotation.Keep;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import pu.file_eraser.BuildConfig;
import pu.file_eraser.global.Constant;

@Keep
public class MainXposed implements IXposedHookZygoteInit, IXposedHookLoadPackage {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "MainXposed";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {
//        Log.d(TAG, "handleLoadPackage: app=" + lpparam.packageName);

        if (lpparam.packageName.equals(BuildConfig.APPLICATION_ID))
            new FeSelfHook().handleLoadPackage(lpparam);

        if (BuildConfig.DEBUG) new FeDebugHook().handleLoadPackage(lpparam);
    }

    @Override
    public void initZygote(StartupParam startupParam) {
        // 防止重复挂接
        if (startupParam.startsSystemServer) {
//            XposedBridge.log(TAG);
            Log.i(TAG, "initZygote in version " + XposedBridge.getXposedVersion());
            new IoHook().initZygote(startupParam);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                new OsHook().initZygote(startupParam);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    new NioHook().initZygote(startupParam);
                }
            }
        }
    }
}
