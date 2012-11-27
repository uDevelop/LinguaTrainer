package org.uDevelop.linguatrainer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProcessActivity extends Activity {
	private final static int sMaxWordsCount = 10; //слов в тесте
	private WrongAnswers mWrongAnswers;
	private EditText mTranslationEdit;
	private TextView mWordText;	
	private boolean mIsRusToEng;
	private WordsStorage mWordsStorage; //обертка для базы для получения слов
	private WordsPair mCurWords;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process);
        mWordText = (TextView)findViewById(R.id.word);
        mTranslationEdit = (EditText)findViewById(R.id.translationEdit);
        mIsRusToEng = getIntent().getBooleanExtra(Constants.IS_RUS_TO_ENG, true);
        ProcessActivityState saveState = (ProcessActivityState)getLastNonConfigurationInstance(); 
        if (saveState != null) { //восстанавливаем все после поворота экрана
        	mWordsStorage = saveState.words;
        	mWrongAnswers = saveState.wrongAnswers;
        	mCurWords = saveState.curWords;
        	showWord(mCurWords);
        }
        else { 
        	mWrongAnswers = new WrongAnswers(sMaxWordsCount);
        	mWordsStorage = new WordsStorage(this, sMaxWordsCount);
        	showNextWord();
        }       
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_process, menu);
        return true;
    }
    
    @Override
    public Object onRetainNonConfigurationInstance() { //сейвим состояние
    	final ProcessActivityState state = new ProcessActivityState();
    	state.curWords = mCurWords;
    	state.words = mWordsStorage;
    	state.wrongAnswers = mWrongAnswers;
        return state;
    }
    
    public void onClick(View view) {
    	switch (view.getId()){
    	case R.id.nextBtn:
    			if (mCurWords != null) {
    				checkWords();
    				showNextWord();
    			}    			
    			break;
    	}
    }
    
    private void showNextWord() { //показываем следующее слово, чистим эдит или переходим на экран результатов
    	mCurWords = mWordsStorage.getNextWords();
    	if (mCurWords != null) {
    		showWord(mCurWords);
    		mTranslationEdit.setText("");
    	}
    	else { 
    		Intent intent = new Intent(this, ResultScreen.class);
    		intent.putExtra(Constants.IS_RUS_TO_ENG, mIsRusToEng);
    		intent.putExtra(Constants.WRONG_ANSWERS, mWrongAnswers);
    		startActivity(intent);
    		mWordsStorage.closeDatabase(); //завершаем работу с бд
    		this.finish();
    	}
    }
    
    private void showWord(WordsPair words) { //вывод текущего слова в ТекстВью
    	if (mIsRusToEng) {
			mWordText.setText(words.ru);
		}
		else {
			mWordText.setText(words.en);
		}  
    }
        
    private void checkWords() { //проверка на правильность введенного юзером слова
    	String userWord = mTranslationEdit.getText().toString();
    	boolean isEqual;
    	if (mIsRusToEng) {
    		isEqual = mCurWords.en.equalsIgnoreCase(userWord);
    	}
    	else {
    		isEqual = mCurWords.ru.equalsIgnoreCase(userWord);
    	}
    	if (!isEqual) {
    		mWrongAnswers.addWrongAnswer(mCurWords.ru, mCurWords.en, userWord); //заносим неправельный ответ хранилище
    	}	    	
    }   
    
}
