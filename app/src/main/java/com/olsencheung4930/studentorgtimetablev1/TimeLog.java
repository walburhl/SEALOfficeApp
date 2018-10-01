package com.olsencheung4930.studentorgtimetablev1;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeLog {
    private String studentName,orgName,logInTimeString;
    private Date logInTime;




    public TimeLog(String studentName, String orgName){
        //This would be the First error to look at
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy H:mm");
        this.logInTimeString= sdf.format(currentDate.getTime());


        Log.w("current time return",logInTimeString);
        this.studentName = studentName;
        this.orgName = orgName;


    }

    @Override
    public String toString() {
        return studentName + ", " + orgName + ", " + logInTimeString;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Date getLogInTime() {
        return logInTime;
    }

    public void setLogInTime(Date logInTime) {
        this.logInTime = logInTime;
    }

    public String getLogInTimeString() {
        return logInTimeString;
    }

    public void setLogInTimeString(String logInTimeString) {
        this.logInTimeString = logInTimeString;
    }

}
