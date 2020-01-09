package pu.file_eraser.global;

import androidx.annotation.Keep;

public class Constant {

    public static final String LOG_TAG_PREFIX = "FE_";

    @SuppressWarnings("SameReturnValue")
    @Keep
    public static boolean isXposedEnabled() {
        return false;
    }
}
