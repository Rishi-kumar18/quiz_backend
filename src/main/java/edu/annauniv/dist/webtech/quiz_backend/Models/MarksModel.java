package edu.annauniv.dist.webtech.quiz_backend.Models;

public class MarksModel {
    private int questionId;
    private int testId;
    private int userId;
    private String userAnswer;
    private boolean isCorrect;

    // Getters and Setters
    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public boolean getIsCorrect() {
        return isCorrect;
    }

    public void setisCorrect(boolean correct) {
        isCorrect = correct;
    }
}