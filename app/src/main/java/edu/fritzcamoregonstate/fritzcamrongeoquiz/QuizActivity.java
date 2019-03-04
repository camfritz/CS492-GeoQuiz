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


    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;

    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_prevented, false),
            new Question(R.string.question_blackout, false),
            new Question(R.string.question_avoided, true),
            new Question(R.string.question_cte, true),
            new Question(R.string.question_impact, true),
            new Question(R.string.question_concussion_number, 3, new String[]{"0 - 250", "250 - 500", "500 - 1000", "1000+"}),
            new Question(R.string.question_recovery, 2, new String[]{"Avoiding physically demanding activities",
                    "Avoiding the consumption of alcohol", "Staying home and having a movie marathon", "Getting plenty of sleep at night"}),

            new Question(R.string.question_sign, 3, new String[] {"Loss of consciousness", "Amnesia", "Drowsiness", "None of the Above"}),
            new Question(R.string.question_order, 2, new String[] {"Recognize, Refer, Return, Remove", "Remove, Refer, Recognize, Return",
                    "Recognize, Remove, Refer, Return", "Refer, Remove, Recognize, Return"}),

            new Question(R.string.question_america, 3, new String[] {"1.7 Million", "2.8 Million", "3.2 Million", "3.8 Million"})

    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check to see if a current question index is saved on the bundle, if so then retrieve the index.

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }


        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true, -1);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false, -1);
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            } });

        mButton1 = (Button) findViewById(R.id.button1);
        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false, 0);
            }
        });

        mButton2 = (Button) findViewById(R.id.button2);
        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false, 1);
            }
        });

        mButton3 = (Button) findViewById(R.id.button3);
        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false, 2);
            }
        });

        mButton4 = (Button) findViewById(R.id.button4);
        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false, 3);
            }
        });


        updateQuestion();
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

        if(mCurrentIndex > 4) {
            mTrueButton.setVisibility(View.GONE);
            mFalseButton.setVisibility(View.GONE);

            mButton1.setVisibility(View.VISIBLE);
            mButton2.setVisibility(View.VISIBLE);
            mButton3.setVisibility(View.VISIBLE);
            mButton4.setVisibility(View.VISIBLE);

            mButton1.setText(mQuestionBank[mCurrentIndex].getAnswerArray()[0]);
            mButton2.setText(mQuestionBank[mCurrentIndex].getAnswerArray()[1]);
            mButton3.setText(mQuestionBank[mCurrentIndex].getAnswerArray()[2]);
            mButton4.setText(mQuestionBank[mCurrentIndex].getAnswerArray()[3]);
        }
        else {
            mTrueButton.setVisibility(View.VISIBLE);
            mFalseButton.setVisibility(View.VISIBLE);

            mButton1.setVisibility(View.GONE);
            mButton2.setVisibility(View.GONE);
            mButton3.setVisibility(View.GONE);
            mButton4.setVisibility(View.GONE);
        }
    }

    private void checkAnswer(boolean userPressedTrue, int pressedIndex) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;


        if(pressedIndex == -1) {
            if (userPressedTrue == answerIsTrue) {
                messageResId = R.string.correct_toast;
            } else {
                messageResId = R.string.incorrect_toast;
            }
        }
        else {
            if(pressedIndex == mQuestionBank[mCurrentIndex].getAnswerIndex()) {
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
