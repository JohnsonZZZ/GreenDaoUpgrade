# GreenDaoUpgrade
GreenDao自动化升级

[![](https://jitpack.io/v/mhlistener/GreenDaoUpgrade.svg)](https://jitpack.io/#mhlistener/GreenDaoUpgrade)

[博客链接](https://www.jianshu.com/p/67f02df63132)

Step 1. 在 root gradle目录下添加
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Step 2. 在 子module gradle目录下添加依赖
```
dependencies {
    implementation 'com.github.mhlistener:GreenDaoUpgrade:1.0.0'
}
```
