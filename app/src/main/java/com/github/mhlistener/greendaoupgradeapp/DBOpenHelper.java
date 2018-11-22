package com.github.mhlistener.greendaoupgradeapp;

import android.content.Context;

import com.github.mhlistener.greendaoupgradelib.MigrationHelper;

import org.greenrobot.greendao.database.Database;


/**
 * 数据库升级 注意以下几点
 * 1.schemaVersion版本号升级
 * 2.将新增或者修改后的EntityDao 放到startMigrate方法下migrate，完成数据库的升级
 */

public class DBOpenHelper extends DaoMaster.OpenHelper {


	public DBOpenHelper(Context context, String name) {
		super(context, name);
	}

	@Override
	public void onCreate(Database db) {
		super.onCreate(db);
		startMigrate(db);
	}

	@Override
	public void onUpgrade(Database db, int oldVersion, int newVersion) {
		super.onUpgrade(db, oldVersion, newVersion);
		startMigrate(db);
	}

	private void startMigrate(Database db) {
		MigrationHelper.migrate(db, KeywordHistoryEntityDao.class);
	}

}
