package pu.file_eraser.global;

import androidx.annotation.Keep;

public class Constant {

    public static final String LOG_TAG_PREFIX = "FE_";

    public static final String XPOSED_WARNING_TITLE = "FileEraser xposed module enabled!";
    public static final String XPOSED_WARNING_TEXT = "Please DISABLE it in time when it's NOT needed.";

    @SuppressWarnings("SameReturnValue")
    @Keep
    public static boolean isXposedEnabled() {
        return false;
    }
}
