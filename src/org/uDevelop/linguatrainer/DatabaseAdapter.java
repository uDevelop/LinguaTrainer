package org.uDevelop.linguatrainer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAdapter {

	private final static String  sDbName = "word_db";
	private final static int  sDbVersion = 3;
	private final static String sDbSelectAll = "select * from words";
	private final static String sDbSelectWord = "select ru_word, en_word from words where _id = ";
	private DbOpenHelper mDbOpenHelper;
	private SQLiteDatabase mDatabase;

	public DatabaseAdapter(Context context) {
			mDbOpenHelper = new DbOpenHelper(context, sDbName, null, sDbVersion);
			mDatabase = mDbOpenHelper.getReadableDatabase();			
	}

	public int getRecordsCount() { 
		Cursor cursor = mDatabase.rawQuery(sDbSelectAll, null);
		int result = cursor.getCount();
		cursor.close();
		return result;
	}

	public WordsPair getRecord(int _id) { //извлекаем пару ru-en слов из базы
		Cursor cursor = mDatabase.rawQuery(sDbSelectWord + Integer.toString(_id), null);
		WordsPair res = null;
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			res = new WordsPair();
			res.ru = cursor.getString(0);
			res.en = cursor.getString(1);
		}			
		cursor.close();
		return res;
	}
	
	public void close() {
		mDatabase.close();
	}
}