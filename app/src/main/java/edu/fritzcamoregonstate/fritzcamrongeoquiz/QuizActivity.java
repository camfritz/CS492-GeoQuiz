package edu.fritzcamoregonstate.fritzcamrongeoquiz;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    //key index for saving onto the bundle
    private static final String KEY_INDEX = "index";

    private static final int REQUEST_CODE_CHEAT = 0;

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mCheatAttemptsTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_oregon, false),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_north_america, true),
    };

    private int mCurrentIndex = 0;
    private boolean mIsCheater;
    private int mCheatAttemptsLeft = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check to see if a current question index is saved on the bundle, if so then retrieve the index.

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mCheatAttemptsTextView = (TextView) findViewById(R.id.cheat_attempts_remaining);
        mCheatAttemptsTextView.setText(R.string.cheat_attempts_3);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;
                updateQuestion();
            } });

        //Button listener for cheat button

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Start the CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);

                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return; }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return; }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if(mIsCheater) {
                mCheatAttemptsLeft -= 1;
            }
        }
    }

    //override onSaveInstanceState to save the current question index onto the bundle using the static KEY_INDEX for retrieval at a later time

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onStop() {
        super.onStart();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onStart();
        Log.d(TAG, "onDestroy() called");
    }

    @Override
    public void onResume() {
        super.onStart();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onStart();
        Log.d(TAG, "onPause() called");
    }

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (mIsCheater) {
            messageResId = R.string.judgment_toast;
        }
        else {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            }
            else {
                messageResId = R.string.incorrect_toast;
            }
        }

        Toast newToast = Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT);
        newToast.setGravity(Gravity.TOP, 0, 200);
        newToast.show();
    }
}
