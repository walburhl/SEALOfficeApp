package com.olsencheung4930.studentorgtimetablev1;

public class Student  {
    private String studentName, organizationName;
    private int loginHours;
    //private long startTime,endTime;
    //private Boolean isLogin;

    public Student(){
        this.studentName = null;
        this.organizationName =null;
        this.loginHours = 0;
        //this.isLogin = false;
    }

    public String getstudentName() {
        return studentName;
    }

    public void setstudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getLoginHours() {
        return loginHours;
    }

    public void setLoginHours(int loginHours) {
        this.loginHours = loginHours;
    }

//    public Boolean getLogin() {
//        return isLogin;
//    }
//
//    public void setLogin(Boolean login) {
//        isLogin = login;
//    }
}
