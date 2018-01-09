package com.github.mhlistener.greendaoupgradelib;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 *  数据库升级辅助类
 * please call {@link #migrate(SQLiteDatabase, Class[])} or {@link #migrate(Database, Class[])}
 */

public final class MigrationHelper {

	private static final String TAG = "MigrationHelper";
	private static final String SQLITE_MASTER = "sqlite_master";
	private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

	public static void migrate(SQLiteDatabase db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
		LogUtil.d(TAG,"【The Old Database Version】" + db.getVersion());
		Database database = new StandardDatabase(db);
		migrate(database, daoClasses);
	}


	public static void migrate(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
		for (int i = 0; i < daoClasses.length; i++) {
			DaoConfig daoConfig = new DaoConfig(database, daoClasses[i]);
			createTable(database, true, daoConfig);
		}
		LogUtil.d(TAG,"【check Tables】start");
		checkTablesIfNeedAlter(database, daoClasses);
		LogUtil.d(TAG,"【check Tables】complete");
	}

	private static boolean isTableExists(Database db, boolean isTemp, String tableName) {
		if (db == null || TextUtils.isEmpty(tableName)) {
			return false;
		}
		String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
		String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
		Cursor cursor = null;
		int count = 0;
		try {
			cursor = db.rawQuery(sql, new String[]{"table", tableName});
			if (cursor == null || !cursor.moveToFirst()) {
				return false;
			}
			count = cursor.getInt(0);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return count > 0;
	}

	private static void checkTablesIfNeedAlter(Database db, Class<? extends AbstractDao<?, ?>>... daoClasses) {
		for (int i = 0; i < daoClasses.length; i++) {
			DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);
			String tableName = daoConfig.tablename;
			if (!isTableExists(db, false, tableName)) {
				continue;
			}

			try {
				List<String> columns = getColumns(db, tableName);
				List<String> lowcaseColumns = parseToLowcase(columns);
				for (int j = 0; j < daoConfig.properties.length; j++) {
					String columnName = daoConfig.properties[j].columnName;
					if (!lowcaseColumns.contains(columnName.toLowerCase())) {
						StringBuilder alterTableStringBuilder = new StringBuilder();
						alterTableStringBuilder.append("ALTER TABLE ").append(tableName).append(" ADD ");
						alterTableStringBuilder.append(columnName).append(" ");
						alterTableStringBuilder.append(getPropertyType(daoConfig.properties[j].type));
						db.execSQL(alterTableStringBuilder.toString());
						LogUtil.d(TAG,"【ALTER TABLE】" + alterTableStringBuilder.toString());
					}
				}
			} catch (SQLException e) {
				Log.e(TAG, "【Failed to ALTER TABLE 】" + tableName, e);
			}
		}
	}

	private static List<String> parseToLowcase(List<String> columnList) {
		List<String> newList = new ArrayList<>();
		for (String columnName : columnList) {
			newList.add(columnName.toLowerCase());
		}
		return newList;
	}

	private static List<String> getColumns(Database db, String tableName) {
		List<String> columns = null;
		Cursor cursor = null;
		try {
			cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
			if (null != cursor && cursor.getColumnCount() > 0) {
				columns = Arrays.asList(cursor.getColumnNames());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null)
				cursor.close();
			if (null == columns)
				columns = new ArrayList<>();
		}
		return columns;
	}

	public static void createTable(Database db, boolean ifNotExists, DaoConfig daoConfig) {
		String tableName = daoConfig.tablename;
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ");
		builder.append(ifNotExists ? "IF NOT EXISTS ": "");
		builder.append(tableName);
		builder.append(getColumnsSql(daoConfig));
		LogUtil.d(TAG,"【createTable】 sql:" + builder.toString());
		db.execSQL(builder.toString()); // 6: Description
	}

	private static String getColumnsSql(DaoConfig daoConfig) {
		if (daoConfig == null) {
			return "";
		}
		StringBuilder builder = new StringBuilder(" (");
		for (int i = 0; i < daoConfig.properties.length; i++) {
			builder.append(String.format("\"%s\" %s,", daoConfig.properties[i].columnName,
					getPropertyType(daoConfig.properties[i].type)));
		}
		if (daoConfig.properties.length > 0 && builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("); ");
		return builder.toString();
	}

	/**
	 * 根据字段类型返回对应的数据库字段语句，这边返回的默认属性可以根据项目需求而定
	 * 如果有需要可以直接拷贝下来，修改某些类型的默认属性
	 * @param type
	 * @return
	 */
	private static String getPropertyType(Class<?> type) {
		if (type.equals(byte[].class)) {
			return "BLOB";
		} else if (type.equals(String.class)) {
			return "TEXT DEFAULT ''";
		} else if (type.equals(boolean.class) || type.equals(Boolean.class)
				|| type.equals(int.class) || type.equals(Integer.class)
				|| type.equals(long.class) || type.equals(Long.class)
				|| type.equals(Date.class) || type.equals(Byte.class)) {
			return "INTEGER DEFAULT (0)";
		} else if (type.equals(float.class) || type.equals(Float.class)
				|| type.equals(double.class) || type.equals(Double.class)){
			return "REAL DEFAULT (0)";
		}
		return "TEXT DEFAULT ''";
	}

}
