package edu.annauniv.dist.webtech.quiz_backend.Views;

import edu.annauniv.dist.webtech.quiz_backend.Models.UserModel;

import java.sql.*;
import java.util.*;

public class UserView {
    static private Connection connection;

    public UserView() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/quizapp", "postgres", "Rishi18");
            String sql = "create table IF NOT EXISTS users (id SERIAL PRIMARY KEY, email varchar(50) NOT NULL, password varchar(60) NOT NULL, role varchar(50) NOT NULL)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<UserModel> listUser() {
        List<UserModel> userList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                UserModel tmpUser = new UserModel();
                tmpUser.setEmail(rs.getString("email"));
                tmpUser.setRole(rs.getString("role"));
                userList.add(tmpUser);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public Map<String, Object> login(String email, String password) {
        Map<String, Object> result = new HashMap<>();
        try {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result.put("status", "200");
                result.put("role", rs.getString("role"));
            } else {
                result.put("status", "not ok");
                result.put("message", "Invalid email or password");
            }
        } catch (Exception e) {
            result.put("status", "not ok");
            result.put("error", e.getMessage());
        }
        return result;
    }
}