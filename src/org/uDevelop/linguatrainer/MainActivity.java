package org.uDevelop.linguatrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView mTranslationDirText;
	private boolean mIsRusToEng; //направление первода
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTranslationDirText = (TextView)findViewById(R.id.translationDirectionText);
        MainActivityState saveState = (MainActivityState)getLastNonConfigurationInstance(); 
        if (saveState != null) { //восстанавливаем инфу в активити, если экран вертелся
        	mIsRusToEng = saveState.isRusToEng;
        }
        else {
        	mIsRusToEng = true;        	
        }
        showDirection();
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public MainActivityState onRetainNonConfigurationInstance() {
    	MainActivityState state = new MainActivityState();
    	state.isRusToEng = mIsRusToEng;
        return state;
    }
    
    public void onClick(View view) {
    	switch (view.getId()) {
    	case R.id.changeDirectionBtn:
    		mIsRusToEng = !mIsRusToEng;
    		showDirection();
    		break;
    	case R.id.StartBtn:
    		Intent intent = new Intent(this, ProcessActivity.class);
    		intent.putExtra(Constants.IS_RUS_TO_ENG, mIsRusToEng);
    		startActivity(intent);    		
    		break;
    	}    	
    }
    private void showDirection() {  //выводим в текствью направление перевода
    	if (!mIsRusToEng) {
			mTranslationDirText.setText(R.string.EngToRus);
		}
		else {
			mTranslationDirText.setText(R.string.RusToEng);
		}    	
    }
}
