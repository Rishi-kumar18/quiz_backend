package edu.annauniv.dist.webtech.quiz_backend.Views;

import edu.annauniv.dist.webtech.quiz_backend.Models.UserModel;

import java.sql.*;
import java.util.*;

public class UserView {
    static private Connection connection;
    public UserView(){
        try{
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/quizapp", "postgres", "Rishi18");
            String sql = "create table IF NOT EXISTS userss (id SERIAL PRIMARY KEY, usernmame varchar(50) NOT NULL, age int NOT NULL, rollno varchar(50) NOT NULL)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<UserModel> listUser(){
        List<UserModel> userList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM userss";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                UserModel tmpUser = new UserModel();
                tmpUser.setAge(rs.getInt("age"));
                tmpUser.setName(rs.getString("usernmame"));
                tmpUser.setRollno(rs.getString("rollno"));
                userList.add(tmpUser);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userList;
    }
    public Map<String , Object> login(String username, String rollno) {
        Map<String , Object> result = new HashMap<>();
        List<UserModel> userList = new ArrayList<>();
        try{
            String sql = "SELECT * FROM userss where usernmame = ? and rollno = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1,username);
            pstmt.setString(2,rollno);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                UserModel tmpUser = new UserModel();
                tmpUser.setAge(rs.getInt("age"));
                tmpUser.setName(rs.getString("usernmame"));
                tmpUser.setRollno(rs.getString("rollno"));
                userList.add(tmpUser);
            }
            for (UserModel um : userList){
                if(um.getName().equals(username) && um.getRollno().equals(rollno)){
                    result.put("status", "ok");
                    result.put("result", "available");
                }
                else {
                    result.put("status", "ok");
                    result.put("result", "Not available");
                }
            }
        } catch (Exception e) {
            result.put("status" ,"not Ok");
            result.put("err" ,e.getMessage());
        }
        return result;
    }
}
