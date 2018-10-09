package edu.fritzcamoregonstate.fritzcamrongeoquiz;

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

    //key index for saving question index, question answered status, and the answer onto the bundle
    private static final String KEY_INDEX = "index";
    private static final String IS_ANSWERED_INDEX = "is_answered";
    private static final String USER_ANSWER_INDEX = "user_answer";
    private static final String SCORE_INDEX = "score_index";
    private static final String NUM_ANSWERED_INDEX = "num_answered_index";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

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
    private int mScore = 0;
    private int mNumAnswered = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check to see if a current question index is saved on the bundle, if so then retrieve the index.

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mScore = savedInstanceState.getInt(SCORE_INDEX);
            mNumAnswered = savedInstanceState.getInt(NUM_ANSWERED_INDEX);

            if(savedInstanceState.getBoolean(IS_ANSWERED_INDEX) == true) {
                mQuestionBank[mCurrentIndex].setAnswered();
            }

            mQuestionBank[mCurrentIndex].setUserAnswer(savedInstanceState.getBoolean(USER_ANSWER_INDEX));
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
                updateQuestion();
            } });

        updateQuestion();
    }

    //override onSaveInstanceState to save the current question index, question answered status, and the answer onto the bundle

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(IS_ANSWERED_INDEX, mQuestionBank[mCurrentIndex].getIsAnswered());
        savedInstanceState.putBoolean(USER_ANSWER_INDEX, mQuestionBank[mCurrentIndex].getUserAnswer());
        savedInstanceState.putInt(SCORE_INDEX, mScore);
        savedInstanceState.putInt(NUM_ANSWERED_INDEX, mNumAnswered);


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

        //update the button views appropriately if the question has been answered previously

        if(mQuestionBank[mCurrentIndex].getIsAnswered() == true) {
            if(mQuestionBank[mCurrentIndex].getUserAnswer() == true) {
                mFalseButton.setEnabled(false);
            }
            else {
                mTrueButton.setEnabled(false);
            }
        }
        else {
            mFalseButton.setEnabled(true);
            mTrueButton.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {

        if(mNumAnswered >= mQuestionBank.length - 1) {
            Toast newToast = Toast.makeText(this, "Your Score: " + mScore, Toast.LENGTH_LONG);
            newToast.show();
        }

        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;

            if(mNumAnswered != mQuestionBank.length) {
                mScore += 1;
                mNumAnswered += 1;
            }

        }
        else {
            messageResId = R.string.incorrect_toast;
            if(mNumAnswered != mQuestionBank.length) {
                mNumAnswered += 1;
            }
        }

        Toast newToast = Toast.makeText(QuizActivity.this, messageResId, Toast.LENGTH_SHORT);
        newToast.setGravity(Gravity.TOP, 0, 200);
        newToast.show();

        //update the question answered status as answered, set user answer to the question, then update the button views appropriately

        mQuestionBank[mCurrentIndex].setAnswered();
        mQuestionBank[mCurrentIndex].setUserAnswer(userPressedTrue);
        if(mQuestionBank[mCurrentIndex].getIsAnswered() == true) {
            if(userPressedTrue == true) {
                mFalseButton.setEnabled(false);
            }
            else {
                mTrueButton.setEnabled(false);
            }
        }
    }
}
