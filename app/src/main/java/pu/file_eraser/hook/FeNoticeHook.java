package pu.file_eraser.hook;

import android.app.AndroidAppHelper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import pu.file_eraser.global.Constant;

class FeNoticeHook implements IXposedHookLoadPackage {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "FeNoticeHook";

    private Context mContext;
    private XC_MethodHook.Unhook mUnhook;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if ("android".equals(lpparam.packageName) && lpparam.isFirstApplication) {
            mContext = AndroidAppHelper.currentApplication();
            mUnhook = XposedHelpers.findAndHookMethod("com.android.server.am.ActivityManagerService",
                    Thread.currentThread().getContextClassLoader(),
                    "finishBooting",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            notifyXposedWarning();
                            if (mUnhook != null) {
                                Log.i(TAG, "afterHookedMethod: unhook");
                                mUnhook.unhook();
                            }
                        }
                    });
        }
    }

    private void notifyXposedWarning() {
        Notification.Builder builder;
        NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager == null) {
            Log.e(TAG, "notifyXposedWarning: NotificationManager is null! Cannon send notification.");
            return;
        }
        String channelId = TAG + hashCode(), channelName = "FileEraser";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
            builder = new Notification.Builder(mContext, channelId);
        } else {
            builder = new Notification.Builder(mContext);
        }
        builder.setSmallIcon(android.R.drawable.ic_menu_manage);
        builder.setContentTitle(Constant.XPOSED_WARNING_TITLE);
        builder.setContentText(Constant.XPOSED_WARNING_TEXT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setColor(Color.RED);
        }
        builder.setAutoCancel(false);
        builder.setOngoing(true);
        manager.notify(hashCode(), builder.build());
    }
}
