package org.uDevelop.linguatrainer;

import android.os.Parcel;
import android.os.Parcelable;


public class WrongAnswers implements Parcelable  {
	private int mCount; //количество неверных слов
	private int mTotalAnswersCount;
	private Answers mAnswers[];
	private int mCurIndex; //индекс текущего слова для функции getNextAnswer;
	
	public WrongAnswers(int maxWordsQty) {
		mTotalAnswersCount = maxWordsQty;
		mCount = 0;
		mAnswers = new Answers[mTotalAnswersCount];
		mCurIndex = 0;
	}
	
	public int getWrongAnswersCount() {
		return mCount;
	}
	
	public int getTotalAnswersCount() {
		return mTotalAnswersCount;
	}
	
	public void addWrongAnswer(String ru, String en, String user) {
		Answers answ = new Answers();
		answ.ru = ru;
		answ.en = en;
		answ.user = user;
		mAnswers[mCount] = answ;
		mCount++;
	}
	public String ru;
	public String en;
	public String user;
	public void moveToFirst() {
		mCurIndex = 0;
	}
	
	public Answers getNextAnswer() {
		Answers ans = mAnswers[mCurIndex];
		mCurIndex++;
		return ans;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(mCount);
		parcel.writeInt(mTotalAnswersCount);
		parcel.writeInt(mCurIndex);
		for(int i = 0; i < mCount; i++) { 
			parcel.writeParcelable(mAnswers[i], flags);
		}	  
	 }

	public static final Parcelable.Creator<WrongAnswers> CREATOR = new Parcelable.Creator<WrongAnswers>() {	    
		
		public WrongAnswers createFromParcel(Parcel in) {	     
			return new WrongAnswers(in);
		}

	    public WrongAnswers[] newArray(int size) {
	    	return new WrongAnswers[size];
	    }
	};
	  
	private WrongAnswers(Parcel parcel) {
		mCount = parcel.readInt();
		mTotalAnswersCount = parcel.readInt();		  
		mCurIndex = parcel.readInt();
		mAnswers = new Answers[mCount];
		for(int i = 0; i < mCount; i++) {
			mAnswers[i] = parcel.readParcelable(Answers.class.getClassLoader());
		}		  
	}	
}
