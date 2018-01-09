# GreenDaoUpgrade
GreenDao自动化升级


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
