package edu.annauniv.dist.webtech.quiz_backend.Models;

import java.util.List;

public class QuestionResponse {
    private List<QuestionModel> questions;
    private int questionCount;

    public QuestionResponse(List<QuestionModel> questions, int questionCount) {
        this.questions = questions;
        this.questionCount = questionCount;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }
}