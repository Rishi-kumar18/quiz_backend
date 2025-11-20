package edu.annauniv.dist.webtech.quiz_backend.Views;

import edu.annauniv.dist.webtech.quiz_backend.Models.MarksModel;
import edu.annauniv.dist.webtech.quiz_backend.Models.QuestionModel;
import edu.annauniv.dist.webtech.quiz_backend.Models.TestModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarksView {
    static private Connection connection;

    public MarksView() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/quizapp", "postgres", "Rishi18");
            String sql = "CREATE TABLE IF NOT EXISTS marks (questionid INT, testid INT, userid INT, useranswer VARCHAR(255), iscorrect BOOLEAN, PRIMARY KEY (questionid, testid, userid))";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void insertMarks(MarksModel marks) {
        try {
            System.out.println("the boolean iscorrect value is : "+marks.getIsCorrect());
            String sql = "INSERT INTO marks (questionid, testid, userid, useranswer, iscorrect) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, marks.getQuestionId());
            pstmt.setInt(2, marks.getTestId());
            pstmt.setInt(3, marks.getUserId());
            pstmt.setString(4, marks.getUserAnswer());
            pstmt.setBoolean(5, marks.getIsCorrect());
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<QuestionModel> fetchMarksByUserIdAndTestId(int userId, int testId) {
        List<QuestionModel> questionList = new ArrayList<>();
        try {
            String sql = "SELECT q.questionid, q.questiontext, q.option1, q.option2, q.option3, q.option4, q.correctanswer, m.useranswer, m.iscorrect " +
                    "FROM marks m " +
                    "JOIN questions q ON m.questionid = q.questionid " +
                    "WHERE m.userid = ? AND m.testid = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, testId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                QuestionModel question = new QuestionModel();
                question.setQuestionId(rs.getInt("questionid"));
                question.setQuestionText(rs.getString("questiontext"));
                question.setOption1(rs.getString("option1"));
                question.setOption2(rs.getString("option2"));
                question.setOption3(rs.getString("option3"));
                question.setOption4(rs.getString("option4"));
                question.setCorrectAnswer(rs.getString("correctanswer"));
                question.setUserAnswer(rs.getString("useranswer"));
                question.setCorrect(rs.getBoolean("iscorrect"));
                questionList.add(question);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return questionList;
    }

    public int calculateCorrectAnswerPercentage(int userId, int testId) {
        int totalQuestions = 0;
        int correctAnswers = 0;
        try {
            // Fetch total number of questions
            String totalQuestionsSql = "SELECT COUNT(*) AS total_count FROM marks WHERE userid = ? AND testid = ?";
            PreparedStatement totalQuestionsStmt = connection.prepareStatement(totalQuestionsSql);
            totalQuestionsStmt.setInt(1, userId);
            totalQuestionsStmt.setInt(2, testId);
            ResultSet totalQuestionsRs = totalQuestionsStmt.executeQuery();
            if (totalQuestionsRs.next()) {
                totalQuestions = totalQuestionsRs.getInt("total_count");
            }

            // Fetch number of correctly answered questions
            String correctAnswersSql = "SELECT COUNT(*) AS correct_count FROM marks WHERE userid = ? AND testid = ? AND iscorrect = TRUE";
            PreparedStatement correctAnswersStmt = connection.prepareStatement(correctAnswersSql);
            correctAnswersStmt.setInt(1, userId);
            correctAnswersStmt.setInt(2, testId);
            ResultSet correctAnswersRs = correctAnswersStmt.executeQuery();
            if (correctAnswersRs.next()) {
                correctAnswers = correctAnswersRs.getInt("correct_count");
            }

            // Calculate percentage
            if (totalQuestions > 0) {
                return (correctAnswers * 100) / totalQuestions;
            } else {
                return 0; // Avoid division by zero
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Object> fetchMarksAndCorrectCount(int userId, int testId) {
        List<Object> result = new ArrayList<>();
        List<QuestionModel> questionList = fetchMarksByUserIdAndTestId(userId, testId);
        int correctCount = calculateCorrectAnswerPercentage(userId, testId);
        result.add(questionList);
        result.add(correctCount);
        return result;
    }

    public List<TestModel> fetchTestsByUserId(int userId) {
        List<TestModel> testList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT t.testid, t.testname, t.testmarks FROM test t JOIN marks m ON t.testid = m.testid WHERE m.userid = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TestModel test = new TestModel();
                test.setTestId(rs.getInt("testid"));
                test.setTestName(rs.getString("testname"));
                test.setTestMarks(rs.getInt("testmarks"));
                testList.add(test);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return testList;
    }
    public int[] fetchTestIdsByUserId(int userId) {
        List<Integer> testIdList = new ArrayList<>();
        try {
            String sql = "SELECT DISTINCT testid FROM marks WHERE userid = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                testIdList.add(rs.getInt("testid"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return testIdList.stream().mapToInt(i -> i).toArray();
    }

    public List<Map<String, Object>> fetchStudentMarksPercentageByTestId(int testId) {
        List<Map<String, Object>> results = new ArrayList<>();
        String sql =
                "SELECT m.userid, u.email AS student_name, " +
                        "       CAST(ROUND(100.0 * SUM(CASE WHEN m.iscorrect THEN 1 ELSE 0 END) / COUNT(*)) AS INT) AS marks_percentage " +
                        "FROM marks m " +
                        "JOIN users u ON u.id = m.userid " +
                        "WHERE m.testid = ? " +
                        "GROUP BY m.userid, u.email " +
                        "ORDER BY marks_percentage DESC, u.email ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, testId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    row.put("userId", rs.getInt("userid"));
                    row.put("studentEmail", rs.getString("student_name"));
                    row.put("marksPercentage", rs.getInt("marks_percentage"));
                    results.add(row);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }
}