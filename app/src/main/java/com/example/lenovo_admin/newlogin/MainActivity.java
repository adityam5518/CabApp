package com.example.lenovo_admin.newlogin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button register, log_in;
    EditText First_Name, etEmail, MobileNO, Password ;
    String F_Name_Holder, Email_Holder, PasswordHolder;
    String finalResult ;
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/UserRegistration.php";
    Boolean CheckEditText ;
    Context context;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String MobHolder="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        First_Name = (EditText)findViewById(R.id.editTextF_Name);
        etEmail = (EditText)findViewById(R.id.editTextEmail);
        MobileNO = findViewById(R.id.editTextMobile);
        Password = (EditText)findViewById(R.id.editTextPassword);
        register = (Button)findViewById(R.id.Submit);
        log_in = (Button)findViewById(R.id.Login);
        Intent veri = getIntent();
        MobHolder=veri.getStringExtra("vmobile");
        MobileNO.setText(MobHolder);
        MobileNO.setFocusable(false);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches())
                    {
                        etEmail.requestFocus();
                        Toast.makeText(MainActivity.this,"Please enter a valid email.",Toast.LENGTH_LONG).show();
                    }
                    else {
                            if ((PasswordHolder.length())>5) {
                            UserRegisterFunction(F_Name_Holder, Email_Holder, MobHolder, PasswordHolder);
                            }
                            else
                            {      Password.requestFocus();
                            Toast.makeText(MainActivity.this,"Password too short(atleast 6)",Toast.LENGTH_LONG).show();
                            }
                    }
                }
                else {

                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

        log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,UserLoginActivity.class);
                startActivity(intent);

            }
        });

    }
    public void CheckEditTextIsEmptyOrNot(){

        F_Name_Holder = First_Name.getText().toString();
        Email_Holder = String.valueOf(etEmail.toString());
        PasswordHolder = Password.getText().toString();
        MobHolder=MobileNO.getText().toString();

        if(TextUtils.isEmpty(F_Name_Holder) || TextUtils.isEmpty(Email_Holder) || TextUtils.isEmpty(MobHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            CheckEditText = false;

        }
        else {
            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String F_Name, final String email, final String mobileno, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(MainActivity.this, "Loading Data", null, true, true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(MainActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();


            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("f_name",params[0]);

                hashMap.put("email",params[1]);

                hashMap.put("mobileno",params[2]);

                hashMap.put("password",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(F_Name,email,mobileno,password);
    }
}
