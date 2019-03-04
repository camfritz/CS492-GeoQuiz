package edu.fritzcamoregonstate.fritzcamrongeoquiz;

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private int mAnswerIndex;

    private String[] mAnswerArray;

    public Question(int textResId, boolean answerTrue) {
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public Question(int textResId, int answerIndex, String[] answers) {
        mTextResId = textResId;
        mAnswerIndex = answerIndex;

        mAnswerArray = new String[] {};
        mAnswerArray = answers;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }

    public String[] getAnswerArray() {
        return mAnswerArray;
    }
}
