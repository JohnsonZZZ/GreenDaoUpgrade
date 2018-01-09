package com.github.mhlistener.greendaoupgradeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DBOpenHelper helper = new DBOpenHelper(getApplicationContext(), "test.db");
		DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
		final KeywordHistoryEntityDao dao = daoMaster.newSession().getKeywordHistoryEntityDao();

		final Button button = findViewById(R.id.btn_click);
		final TextView textView = findViewById(R.id.tv_show);
		textView.setMovementMethod(ScrollingMovementMethod.getInstance());

		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				long index = new Random().nextLong();
				KeywordHistoryEntity historyEntity = new KeywordHistoryEntity(index, "关键字:" + index, index);
				dao.insert(historyEntity);

				List<KeywordHistoryEntity> list = dao.loadAll();
				StringBuilder stringBuilder = new StringBuilder();
				for (KeywordHistoryEntity entity : list) {
					stringBuilder.append(MainActivity.this.toString(entity));
				}
				textView.setText(stringBuilder.toString());
			}
		});
	}

	private String toString(KeywordHistoryEntity entity) {
		return String.format("查询结果:%d,%s,%d\n", entity.getId(), entity.getKeyword(), entity.getQueryTime());

	}
}
