# GreenDaoUpgrade
GreenDao自动化升级

##How To
Step 1. Add the JitPack repository to your build file
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Step 2. Add the dependency
```
dependencies {
		implementation 'com.github.mhlistener:GreenDaoUpgrade:1.0.0'
	}
```
