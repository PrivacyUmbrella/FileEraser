# FileEraser

文件擦除Xposed模块

## 简介

本模块的作用是在文件在删除之前重写一次，防止数据恢复出来。

本模块作用场景：使用删除敏感文件或者卸载敏感应用(擦除应用数据)。

## 注意事项

* 启用本模块后，文件删除速度会变慢，并且会对闪存芯片的磨损会加剧。因此请在删除了敏感数据之后及时禁用本模块。

* 本模块完全开源，并且正在研发阶段。开发者不对使用效果及潜在风险做出任何保证。

## 附加事项

> 推荐使用[AmazeFileManager](https://github.com/TeamAmaze/AmazeFileManager)删除文件，本模块经测试对它有效。
本模块在Android 9.0和[EdXposed](https://github.com/ElderDrivers/EdXposed)下调试开发。