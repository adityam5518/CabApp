package com.example.lenovo_admin.newlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;


public class UserLoginActivity extends AppCompatActivity {
    EditText Mobile, Password;
    Button LogIn ;
    String PasswordHolder, MobileHolder;
    String finalResult ;
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/UserLogin.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    public static final String UserMobile = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Mobile = (EditText)findViewById(R.id.mobile);
        Password = (EditText)findViewById(R.id.password);
        LogIn = (Button)findViewById(R.id.Login);

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    if(MobileHolder.toString().equals("admin")  &&  PasswordHolder.toString().equals("admin") ){
                        Intent intent = new Intent(UserLoginActivity.this, adminpanel.class);
                        startActivity(intent);
                    }
                    else
                    UserLoginFunction(MobileHolder, PasswordHolder);
                }
                else {

                    Toast.makeText(UserLoginActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){

        MobileHolder = Mobile.getText().toString();
        PasswordHolder = Password.getText().toString();

        if(TextUtils.isEmpty(MobileHolder) || TextUtils.isEmpty(PasswordHolder))
        {
            CheckEditText = false;
        }
        else {

            CheckEditText = true ;
        }
    }

    public void UserLoginFunction(final String mobile, final String password){

        class UserLoginClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(UserLoginActivity.this,"Checking Credentials",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                if(httpResponseMsg.equalsIgnoreCase("Data Matched")){

                    finish();

                    Intent intent = new Intent(UserLoginActivity.this, DashboardActivity.class);

                    intent.putExtra(UserMobile,mobile);

                    startActivity(intent);

                }
                else{

                    Toast.makeText(UserLoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("mobile",params[0]);

                hashMap.put("password",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserLoginClass userLoginClass = new UserLoginClass();

        userLoginClass.execute(mobile,password);
    }

    public void regg(View view) {
        Toast.makeText(UserLoginActivity.this,"Fill All details for registration",Toast.LENGTH_LONG).show();
        Intent intent=new Intent(UserLoginActivity.this,OTP.class);
        startActivity(intent);
    }
}