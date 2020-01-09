-optimizationpasses 99

# 关闭日志
-assumenosideeffects class android.util.Log {
    public static int *(...);
}

-verbose