package org.uDevelop.linguatrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class ResultScreen extends Activity {
	private TextView mTotalAnswCountText;
	private TextView mRightAnswCountText; 
	private TextView mWrongAnswCountText;
	private GridView mErrorsGrid; //таблица результатов
	private GridView mHeadGrid; //шапка таблицы
	private WrongAnswers mWrongAnswers; //неправельные ответы
	private boolean mIsRusToEng;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);
        Intent intent = getIntent();
        mIsRusToEng = intent.getBooleanExtra(Constants.IS_RUS_TO_ENG, true);
        mWrongAnswers = intent.getParcelableExtra(Constants.WRONG_ANSWERS);
        mTotalAnswCountText = (TextView)findViewById(R.id.totalWords);
        mRightAnswCountText = (TextView)findViewById(R.id.rightAnswers);
        mWrongAnswCountText = (TextView)findViewById(R.id.wrongAnswers);
        mErrorsGrid = (GridView)findViewById(R.id.Errors); 
        mHeadGrid = (GridView)findViewById(R.id.head); 
        ShowInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_result_screen, menu);
        return true;
    }
    
    private void ShowInfo() {
    	String totalCount = Integer.toString(mWrongAnswers.getTotalAnswersCount());
    	String wrongCount = Integer.toString(mWrongAnswers.getWrongAnswersCount());
    	int rightQty = mWrongAnswers.getTotalAnswersCount()-mWrongAnswers.getWrongAnswersCount(); 
    	String rightCount = Integer.toString(rightQty);
    	mTotalAnswCountText.setText(getString(R.string.totalWords)+totalCount);
    	mWrongAnswCountText.setText(getString(R.string.wrongAnswers)+wrongCount);
    	mRightAnswCountText.setText(getString(R.string.rightAnswers)+rightCount);
    	int count = mWrongAnswers.getWrongAnswersCount();
    	if (count > 0) {
    		showHead();
    	  	mErrorsGrid.setNumColumns(3);
    	  	String[] gridList = new String[3*count];
    	  	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.grid_cell_text, gridList);
    	  	Answers answer;
    	  	mWrongAnswers.moveToFirst();
    	  	for (int i = 0; i < count; i++) {
    	  		answer = mWrongAnswers.getNextAnswer();
    	  		if (mIsRusToEng) {
    	  			gridList[i*3] = answer.ru;
    	  			gridList[i*3+1] = answer.en;    			
    	  		}
    	  		else {
    	  			gridList[i*3+1] = answer.ru;
    	  			gridList[i*3] = answer.en;    			
    	  		}    		
    	  		gridList[i*3+2] = answer.user;    											
    	  	}
    	  	mErrorsGrid.setAdapter(adapter);
    	}
    	else {
    		TextView yourErrorsText = (TextView)findViewById(R.id.yourErrs);
    		yourErrorsText.setText(R.string.congratulation);
    	}
    }
        
    private void showHead() {
    	String[] headList = new String[3];
    	mHeadGrid.setNumColumns(3);
    	headList[0] = "Слово";
    	headList[1] = "Перевод";
    	headList[2] = "Ваш вариант";
    	ArrayAdapter<String> hadapter = new ArrayAdapter<String>(this, R.layout.grid_cell_text, headList);
    	mHeadGrid.setAdapter(hadapter);
    }
}
