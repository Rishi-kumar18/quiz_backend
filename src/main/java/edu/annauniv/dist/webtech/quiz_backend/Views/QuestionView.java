package edu.annauniv.dist.webtech.quiz_backend.Views;

import edu.annauniv.dist.webtech.quiz_backend.Models.QuestionModel;
import edu.annauniv.dist.webtech.quiz_backend.Models.QuestionResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionView {
    static private Connection connection;

    public QuestionView() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/quizapp", "postgres", "Rishi18");
            String sql = "CREATE TABLE IF NOT EXISTS questions (" +
                    "questionid SERIAL PRIMARY KEY, " +
                    "testid INT NOT NULL, " +
                    "questiontext VARCHAR(255) NOT NULL, " +
                    "option1 VARCHAR(255) NOT NULL, " +
                    "option2 VARCHAR(255) NOT NULL, " +
                    "option3 VARCHAR(255) NOT NULL, " +
                    "option4 VARCHAR(255) NOT NULL, " +
                    "correctanswer VARCHAR(255) NOT NULL, " +
                    "FOREIGN KEY (testid) REFERENCES test(testid))";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createQuestions(String testName, List<QuestionModel> questions) {
        try {
            TestView testView = new TestView();
            int testId = testView.getTestIdByName(testName);

            String sql = "INSERT INTO questions (testid, questiontext, option1, option2, option3, option4, correctanswer) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            for (QuestionModel question : questions) {
                pstmt.setInt(1, testId);
                pstmt.setString(2, question.getQuestionText());
                pstmt.setString(3, question.getOption1());
                pstmt.setString(4, question.getOption2());
                pstmt.setString(5, question.getOption3());
                pstmt.setString(6, question.getOption4());
                pstmt.setString(7, question.getCorrectAnswer());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public QuestionResponse fetchQuestions(String testName) {
        List<QuestionModel> questionList = new ArrayList<>();
        int questionCount;
        try {
            TestView testView = new TestView();
            int testId = testView.getTestIdByName(testName);

            questionCount = countQuestionsByTestId(testId);

            String sql = "SELECT questiontext, option1, option2, option3, option4, correctanswer FROM questions WHERE testid = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, testId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuestionModel question = new QuestionModel();
                question.setQuestionText(rs.getString("questiontext"));
                question.setOption1(rs.getString("option1"));
                question.setOption2(rs.getString("option2"));
                question.setOption3(rs.getString("option3"));
                question.setOption4(rs.getString("option4"));
                question.setCorrectAnswer(rs.getString("correctanswer"));
                questionList.add(question);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new QuestionResponse(questionList, questionCount);
    }

    public int countQuestionsByTestId(int testId) {
        try {
            String sql = "SELECT COUNT(*) AS question_count FROM questions WHERE testid = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, testId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("question_count");
            } else {
                throw new RuntimeException("No questions found for the given testid");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}