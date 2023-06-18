package com.example.saeadaniproject;


public class HelperClass {

    String name, job, num, email, password, role;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public HelperClass() {
    }

    public HelperClass(String name, String job, String num, String email, String password, String role) {
        this.name = name;
        this.job = job;
        this.num = num;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

