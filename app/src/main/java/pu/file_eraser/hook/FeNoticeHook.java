package pu.file_eraser.hook;

import android.app.AndroidAppHelper;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import pu.file_eraser.BuildConfig;
import pu.file_eraser.R;
import pu.file_eraser.global.Constant;
import pu.file_eraser.util.AppUtils;

class FeNoticeHook implements IXposedHookLoadPackage {

    private static final String TAG = Constant.LOG_TAG_PREFIX + "FeNoticeHook";

    private Context mContext;
    private XC_MethodHook.Unhook mUnhook;
    private NotificationManager mNotificationManager;
    private int mHashCode = hashCode();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) {
        if ("android".equals(lpparam.packageName) && lpparam.isFirstApplication) {
            Log.d(TAG, "handleLoadPackage: called");
            mContext = AndroidAppHelper.currentApplication();
            Class<?> amsClazz = XposedHelpers.findClass("com.android.server.am.ActivityManagerService",
                    Thread.currentThread().getContextClassLoader());
            mUnhook = XposedHelpers.findAndHookMethod(amsClazz,
                    "finishBooting",
                    new XC_MethodHook() {

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) {
                            notifyXposedWarning();
                            if (mUnhook != null) {
                                Log.i(TAG, "afterHookedMethod: finishBooting [boot completed]");
                                mUnhook.unhook();
                                XposedHelpers.findAndHookMethod(amsClazz,
                                        "shutdown", int.class,
                                        new XC_MethodHook() {

                                            @Override
                                            protected void beforeHookedMethod(MethodHookParam param) {
                                                Log.i(TAG, "beforeHookedMethod: shutdown [normal shutdown/reboot]");
                                                clearNotification();
                                                mUnhook = null;
                                            }
                                        });
                            }
                        }
                    });
        }
    }

    private void notifyXposedWarning() {
        Notification.Builder builder;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (mNotificationManager == null) {
            Log.e(TAG, "notifyXposedWarning: NotificationManager is null! Cannon send notification.");
            return;
        }
        String title = null, text = null, channelName = null;
        try {
            Context myCtx = mContext.createPackageContext(BuildConfig.APPLICATION_ID, 0);
            Resources resources = myCtx.getResources();
            title = resources.getString(R.string.xposed_warning_title);
            text = resources.getString(R.string.xposed_warning_text);
            channelName = AppUtils.getLabel(myCtx);
        } catch (Throwable throwable) { //SecurityException|PackageManager.NameNotFoundException|Resources.NotFoundException
            Log.e(TAG, "notifyXposedWarning: ", throwable);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(TAG, channelName, NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(mContext, TAG);
        } else {
            builder = new Notification.Builder(mContext);
        }
        builder.setSmallIcon(android.R.drawable.ic_menu_manage)
                .setContentTitle(title)
                .setContentText(text)
                .setStyle(new Notification.BigTextStyle().bigText(text))
                .setAutoCancel(false)
                .setOngoing(true)
                .setTicker(title)
                .setPriority(Notification.PRIORITY_MAX)
                .setShowWhen(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC)
                    .setColor(Color.RED);
        }
        Log.d(TAG, "notifyXposedWarning: hashCode: " + mHashCode);
        mNotificationManager.notify(TAG, mHashCode, builder.build());
    }

    private void clearNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(TAG, mHashCode);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mNotificationManager.deleteNotificationChannel(TAG);
            }
        }
    }
}
