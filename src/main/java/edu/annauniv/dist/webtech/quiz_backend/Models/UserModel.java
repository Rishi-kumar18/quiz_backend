package edu.annauniv.dist.webtech.quiz_backend.Models;

public class UserModel {
    private String name;
    private String rollno;
    private int age;

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getRollno() {
        return rollno;
    }
}
