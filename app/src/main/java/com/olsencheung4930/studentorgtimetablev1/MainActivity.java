package com.olsencheung4930.studentorgtimetablev1;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView studentOrgName, studentName,selectHourText;
    AutoCompleteTextView orgInput;
    Button loginButton, logoutButton;
    int numberOfHours =0;
    int timeLogCount = 0;
    Organization Organizations = new Organization();
    ArrayList<TimeLog> timeLogArrayList = new ArrayList<TimeLog>(500);

    ConnectionClass connectionClass;
    SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy H:mm");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentOrgName = (TextView) findViewById(R.id.orgName);
        studentName = (TextView) findViewById(R.id.studentNameInput);
        loginButton = (Button) findViewById(R.id.loginButton);
        logoutButton = (Button) findViewById(R.id.logoutButton);


        connectionClass = new ConnectionClass();


        //Drop Down function
        ArrayAdapter<String> orgInputAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Organizations.getStudentOrganizationList());

        orgInput = (AutoCompleteTextView) findViewById(R.id.orgNameInput);
        orgInput.setAdapter(orgInputAdapter);

//            Time Testing
//            TimeLog l1 = new TimeLog("olsen", "abc clube");
//            l1.setLogInTimeString("5/01/2018 18:27");
//
//            TimeLog l2 = new TimeLog("olsen", "abc clube");
//            l2.setLogInTimeString("5/01/2018 13:00");
//            timeLogArrayList.add(l2);
//
//            Log.w("Cal result: ", Integer.toString(calculateTime(l1)));


        //logOutButton OnClick Listener
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!checkCredentials()) {
                    return;
                }
                else{
                    TimeLog logoutTimeLog = new TimeLog(studentName.getText().toString(),orgInput.getText().toString());
                    if(checkExistTimeLog(logoutTimeLog)){
                        Toast tempResult = Toast.makeText(getApplicationContext(), "You are already checked in!\nCheck out Before you do check in", Toast.LENGTH_LONG);
                        tempResult.show();
                       return;
                    }
                    timeLogArrayList.add(new TimeLog(studentName.getText().toString(), orgInput.getText().toString()));
                    timeLogCount++;
                    Log.w("List to String", timeLogArrayList.get(timeLogCount - 1).toString());
                    Toast tempResult = Toast.makeText(getApplicationContext(), "You have successfully checked in!", Toast.LENGTH_LONG);
                    tempResult.show();
                    studentName.setText("");
                    orgInput.setText("");
                }
            }
        });

        //Input constring and display
       logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkCredentials())
                return;

                TimeLog logoutTimeLog = new TimeLog(studentName.getText().toString(),orgInput.getText().toString());

                if(checkExistTimeLog(logoutTimeLog)){

                    numberOfHours = calculateTime(logoutTimeLog);
                    if(numberOfHours >= 13){
                        Toast tempResult = Toast.makeText(getApplicationContext(), "You have check in more then 12 hours\n" +
                                "Your record will not be added into the system and please recheck in", Toast.LENGTH_LONG);
                        tempResult.show();
                        numberOfHours = 1;
                    }

                    DoLogin  doLogin = new DoLogin();
                    doLogin.execute("");
                    Log.d("Success?","Reach end");

                    removeTimeLogs(logoutTimeLog);
                }
                else{
                    Toast tempResult = Toast.makeText(getApplicationContext(), "Information is not in the check in system!\nCheck in before you check out", Toast.LENGTH_LONG);
                    tempResult.show();
                    studentName.setText("");
                    orgInput.setText("");
                }


//                    String result;
//                    result = "Student Org name: " + orgInput.getText() + " Student name: " + studentName.getText() + " Hours: " +
//                            Integer.toString(numberOfHours);
//
//                    Toast tempResult = Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG);
//                    tempResult.show();

                    //Have to reset and give out a successful toast or renew the page

            }

        });




    }

    public class DoLogin extends AsyncTask<String,String,String> {
        String z = "";
        Boolean isSuccess = false;

//        String userid = edtuserid.getText().toString();
//        String password = edtpass.getText().toString();

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            }
            studentName.setText("");
            orgInput.setText("");
            numberOfHours = 0;

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                Connection con = connectionClass.CONN();
                if (con == null) {
                    z = "Error in connection with SQL server";
                } else {
                    String query = "Execute spNewAddTimeLog '" +studentName.getText().toString() +"','"+orgInput.getText().toString()+"',"+
                            numberOfHours;

                    Statement stmt = con.createStatement();
                    int success = stmt.executeUpdate(query);


                    z = "Check out successfull";
                    isSuccess = true;


                        /*
                        if (rs.next()) {

                            z = "Login successfull";
                            isSuccess = true;
                        } else {
                            z = "Invalid Credentials";
                            isSuccess = false;
                        }
                        */

                }
            } catch (Exception ex) {
                isSuccess = false;
                z = "Exception";
            }

            return z;
        }
    }

    public boolean checkCredentials(){
        if(!Organizations.search(orgInput.getText().toString())){
            Toast tempResult = Toast.makeText(getApplicationContext(), "Invalid Student Organization name\nPlease Refer to the list", Toast.LENGTH_LONG);
            tempResult.show();
            return false;
        }
        else if(studentName.getText().length()<=2){
            Toast tempResult = Toast.makeText(getApplicationContext(), "Invalid Student name\nPlease enter your name", Toast.LENGTH_LONG);
            tempResult.show();
            return false;
        }

        return true;
    }
    public boolean checkExistTimeLog(TimeLog t){
        for(TimeLog element:timeLogArrayList){
            if((t.getStudentName().equalsIgnoreCase(element.getStudentName())&&
                t.getOrgName().equalsIgnoreCase(element.getOrgName())))
                return true;
            Log.w("Array Lop Information",element.toString());
        }

        return false;
    }

    public void removeTimeLogs(TimeLog t){
        int target = 0;
        for(TimeLog element:timeLogArrayList){
            if((t.getStudentName().equalsIgnoreCase(element.getStudentName())&&
                    t.getOrgName().equalsIgnoreCase(element.getOrgName()))){
                break;
            }
            Log.w("Array Lop Information",element.toString());
            target++;
        }
            timeLogArrayList.remove(target);
        timeLogCount --;
    }

    public int calculateTime(TimeLog t){
        int target = 0;
        int returnRoundUpHours = 0;
        for(TimeLog element:timeLogArrayList){
            if((t.getStudentName().equalsIgnoreCase(element.getStudentName())&&
                    t.getOrgName().equalsIgnoreCase(element.getOrgName()))){
                break;
            }
            Log.w("Array Lop Information",element.toString());
            target++;
        }

        try {
            Date loginTime = sdf.parse(timeLogArrayList.get(target).getLogInTimeString());
            Date logoutTime = sdf.parse(t.getLogInTimeString());
            Log.w("loginTime:", loginTime.toString());
            Log.w("logoutTime:", logoutTime.toString());
            long hourDifference = logoutTime.getTime() - loginTime.getTime();
            Log.w("Time difference:", Long.toString(hourDifference));

            double d1 = (double)hourDifference;
//            Log.w("d1:", Double.toString(d1));
            double timeInDouble = (d1/(60*60*1000));
//            Log.w("timeInDouble:", Double.toString(timeInDouble));
            double timeInRoundUpDouble = Math.ceil(timeInDouble);
//            Log.w("timeInRoundUpDouble:", Double.toString(timeInRoundUpDouble));
            returnRoundUpHours = (int)timeInRoundUpDouble;
//            Log.w("returnRoundUpHours:", Integer.toString(returnRoundUpHours));

        } catch (ParseException e) {
            e.printStackTrace();
        }
       ;
        return returnRoundUpHours;
    }


}
