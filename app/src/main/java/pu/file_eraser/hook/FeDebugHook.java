package pu.file_eraser.hook;

import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import pu.file_eraser.global.Constant;

public class FeDebugHook implements IXposedHookLoadPackage {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "FeDebugHook";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        /* 两个开源文件管理器
         * me.zhanghai.android.files无法hook
         * （syscall remove） */
        String[] debugPkgs = new String[]{
                "me.zhanghai.android.files",
                "com.amaze.filemanager"
        };
        for (String pkg : debugPkgs) printAllClass(pkg, lpparam);
    }

    private void printAllClass(String appId, XC_LoadPackage.LoadPackageParam lpp) {
        if (lpp.packageName.equals(appId)) {
            XposedHelpers.findAndHookMethod(ClassLoader.class, "loadClass", String.class, new XC_MethodHook() {

                @Override
                protected void afterHookedMethod(MethodHookParam param) {
                    if (param.hasThrowable()) return;
                    Class<?> clazz = (Class<?>) param.getResult();
                    final String clazzName = clazz.getName();
                    Log.d(TAG, "printAllClass: loadClass " + clazzName);
                }
            });
        }
    }
}
