package edu.annauniv.dist.webtech.quiz_backend.Views;

import edu.annauniv.dist.webtech.quiz_backend.Models.TestModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestView {
    static private Connection connection;

    public TestView() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/quizapp", "postgres", "Rishi18");
            String sql = "create table IF NOT EXISTS test (testid SERIAL PRIMARY KEY, testname varchar(50) NOT NULL, testmarks int NOT NULL, teststarttiming varchar(50) NOT NULL, testendtiming varchar(50) NOT NULL, testlocation varchar(50) NOT NULL, testdate DATE NOT NULL, testduration int NOT NULL)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createTest(String testName, int testMarks, String testStartTiming, String testEndTiming, String testLocation, String testDate, int testDuration) {
        try {
            String sql = "INSERT INTO test (testname, testmarks, teststarttiming, testendtiming, testlocation, testdate, testduration) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, testName);
            pstmt.setInt(2, testMarks);
            pstmt.setString(3, testStartTiming);
            pstmt.setString(4, testEndTiming);
            pstmt.setString(5, testLocation);
            pstmt.setDate(6, java.sql.Date.valueOf(testDate));
            pstmt.setInt(7, testDuration);
            pstmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<TestModel> fetchTests() {
        List<TestModel> testList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM test";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                TestModel test = new TestModel();
                test.setTestId(rs.getInt("testid"));
                test.setTestName(rs.getString("testname"));
                test.setTestMarks(rs.getInt("testmarks"));
                test.setTestStartTiming(rs.getString("teststarttiming"));
                test.setTestEndTiming(rs.getString("testendtiming"));
                test.setTestLocation(rs.getString("testlocation"));
                test.setTestDate(rs.getString("testdate"));
                test.setTestDuration(rs.getInt("testduration"));
                testList.add(test);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return testList;
    }

    public int getTestIdByName(String testName) {
        try {
            String sql = "SELECT testid FROM test WHERE testname = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, testName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("testid");
            } else {
                throw new RuntimeException("Test not found");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}