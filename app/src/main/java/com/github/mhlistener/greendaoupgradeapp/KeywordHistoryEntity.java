package com.github.mhlistener.greendaoupgradeapp;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by JohnsonFan on 2017/10/16.
 */

@Entity(nameInDb = "KeywordHistory")
public class KeywordHistoryEntity {

	@Id(autoincrement = true)
	@Property(nameInDb = "Id")
	public Long Id;

	@Property(nameInDb = "Keyword")
	public String Keyword;

	@Property(nameInDb = "QueryTime")
	public long QueryTime;

	@Generated(hash = 4193202)
	public KeywordHistoryEntity(Long Id, String Keyword, long QueryTime) {
		this.Id = Id;
		this.Keyword = Keyword;
		this.QueryTime = QueryTime;
	}

	@Generated(hash = 462930205)
	public KeywordHistoryEntity() {
	}

	public Long getId() {
		return this.Id;
	}

	public void setId(Long Id) {
		this.Id = Id;
	}

	public String getKeyword() {
		return this.Keyword;
	}

	public void setKeyword(String Keyword) {
		this.Keyword = Keyword;
	}

	public long getQueryTime() {
		return this.QueryTime;
	}

	public void setQueryTime(long QueryTime) {
		this.QueryTime = QueryTime;
	}


}
