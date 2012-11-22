package org.uDevelop.linguatrainer;

import java.util.Random;

import android.content.Context;

public class WordsStorage {
	private DatabaseAdapter mDbAdapter;
	private int[] mUsedWords;  //уже показанные слова
	private int mUsedCount; //количество показанных
	private int mMaxWordsCount;
	private int mWordsInDatabase;
	private Random mRand;
	
	public WordsStorage(Context context, int maxWordsQty) {
		mMaxWordsCount = maxWordsQty;
		mUsedWords = new int[mMaxWordsCount];	
		mUsedCount = 0;	
		mDbAdapter = new DatabaseAdapter(context);			
		mRand = new Random();		
		mWordsInDatabase = mDbAdapter.getRecordsCount();
	}	
	
	private boolean isUsedWord(int id) {
		for(int i = 0; i < mUsedCount; i++) {
			if (mUsedWords[i] == id) {
				return true;
			}
		}
		return false;
	}
	
	public WordsPair getNextWords() {
		WordsPair res = null;
		if (mUsedCount < mMaxWordsCount) {
			int id = mRand.nextInt(mWordsInDatabase)+1; 
			while (isUsedWord(id)) {  //количество слов в тесте должно быть не более, чем слов в базе, а то влетим в бесконечный цикл
				id = mRand.nextInt(mWordsInDatabase)+1;			
			}
			mUsedWords[mUsedCount] = id;
			mUsedCount++;
			res = mDbAdapter.getRecord(id);
			if (res == null) {
				res = null;
			}
		}		
		return res;
	}
	
	public void closeDatabase() { //вызываем при завершении работы с классом
		mDbAdapter.close();
	}
}