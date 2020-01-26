# FileEraser

文件擦除Xposed模块

## 简介

本模块的作用是在文件在删除之前重写一次，防止数据恢复出来。

本模块作用场景：使用删除敏感文件或者卸载敏感应用(擦除应用数据)。

## 注意事项

* 本模块实际上没有任何直接的用户界面，在安装之后之所以会出现启动图标是因为方便安装调试。点击一次图标后在下一次启动器刷新时图标会消失。

* 启用本模块后，文件删除速度会变慢，并且对闪存芯片的磨损会加剧。因此请在删除了敏感数据之后及时禁用本模块。

* 因为一些技术限制，本模块可能在部分场景下无法生效。请查看[`logcat`](https://github.com/PrivacyUmbrella/FileEraser/blob/39733a363041a7fd438fe2091fe04135e2680834/app/src/main/java/pu/file_eraser/global/Constant.java#L7)以确认被擦除的文件。

## 附加事项

> 推荐使用[AmazeFileManager](https://github.com/TeamAmaze/AmazeFileManager)删除文件，本模块经测试对它有效。

> 本模块在Android 9.0和[EdXposed](https://github.com/ElderDrivers/EdXposed)下调试开发。