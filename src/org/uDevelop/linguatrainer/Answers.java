package org.uDevelop.linguatrainer;

import android.os.Parcel;
import android.os.Parcelable;

public class Answers implements Parcelable {
	public String ru;
	public String en;
	public String user;
	
	public Answers() {
		
	}	
	
	@Override
	public int describeContents() {
		return 0;
	}	
	
	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(ru);
		parcel.writeString(en);
		parcel.writeString(user);
	 }

	public static final Parcelable.Creator<Answers> CREATOR = new Parcelable.Creator<Answers>() {	    
		
		public Answers createFromParcel(Parcel in) {	     
			return new Answers(in);
		}

	    public Answers[] newArray(int size) {
	    	return new Answers[size];
	    }
	};
	  
	private Answers(Parcel parcel) {
		ru = parcel.readString();
		en = parcel.readString();		  
		user = parcel.readString();
	}
}
