package pu.file_eraser.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import pu.file_eraser.BuildConfig;

public class FeSelfHook implements IXposedHookLoadPackage {

    private XC_LoadPackage.LoadPackageParam mLpparam;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        mLpparam = lpparam;
        signalXposedModuleActive();
    }

    private void signalXposedModuleActive() {
        Class<?> constantClass = XposedHelpers.findClass(BuildConfig.APPLICATION_ID + ".global.Constant", mLpparam.classLoader);
        XposedHelpers.findAndHookMethod(constantClass, "isXposedEnabled", XC_MethodReplacement.returnConstant(true));
    }
}
