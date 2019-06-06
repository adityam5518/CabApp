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
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import static java.sql.Types.NULL;

public class OTP extends AppCompatActivity {
    TextView hideotp;
        Button send_otp,login;
        EditText mobile,eotp;
        String sendotp1;
        String mobHolder="";
        String finalResult;
        String HttpURL = "https://rentmycar.000webhostapp.com/connect/otp/test.php";
        Boolean CheckEditText;
        ProgressDialog progressDialog;
        HashMap<String, String> hashMap = new HashMap<>();
        HttpParse httpParse = new HttpParse();
        int number;
    public static final String UserMobile = "";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_otp);
            login=(Button)findViewById(R.id.Login) ;
            mobile = (EditText) findViewById(R.id.mobileno);
            hideotp=(TextView)findViewById(R.id.hideotp1);
            send_otp = (Button) findViewById(R.id.send_otp);
            send_otp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(number==NULL)
                    {
                        mobHolder = mobile.getText().toString();
                        number = (new Random().nextInt(10000));
                        CheckEditTextIsEmptyOrNot();
                        if(CheckEditText) {
                            mobile.setFocusable(false);
                            hideotp.setText(String.valueOf(number));
                            if (isValidMobile(mobHolder)) {
                                UserOTPFunction(mobHolder, sendotp1);
                            } else {
                                Toast.makeText(OTP.this, "Please Enter Your Valid Mobile No", Toast.LENGTH_LONG).show();
                            }
                        }
                        else
                            Toast.makeText(OTP.this,"Please Enter Mobile no",Toast.LENGTH_LONG).show();

                }
                else
                        Toast.makeText(OTP.this,"Please Click Reset",Toast.LENGTH_LONG).show();
                }
            });
        }

        public void CheckEditTextIsEmptyOrNot() {
            sendotp1=Integer.toString(number);
            if (TextUtils.isEmpty(mobHolder)) {
                CheckEditText = false;
            } else {
                CheckEditText = true;
            }
        }

    public void UserOTPFunction(final String mobile, final String password){

        class UserOTPClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(OTP.this,"Checking Credentials",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                    Toast.makeText(OTP.this,httpResponseMsg,Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("mobile",params[0]);

                hashMap.put("otp",params[1]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserOTPClass userOTPClass = new UserOTPClass();

        userOTPClass.execute(mobile,password);
    }

        public void verifyotp(View view) {
            eotp = findViewById(R.id.enterotp);
            String votp = eotp.getText().toString();
            if (!TextUtils.isEmpty(votp)) {
                if (votp.equals(Integer.toString(number))) {
                    Intent veri = new Intent(OTP.this, MainActivity.class);
                    veri.putExtra("vmobile", mobHolder);
                    startActivity(veri);
                }
            }
            else
                Toast.makeText(OTP.this,"Please Enter OTP",Toast.LENGTH_LONG).show();
        }
    private boolean isValidMobile(String phone) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if(phone.length()!=10)
            {
                check = false;

            }
            else
            {
                check = true;

            }
        }
        else
        {
            check=false;
        }
        return check;
    }

    public void reset1(View view) {
        number=NULL;
        mobile.setFocusableInTouchMode(true);
        mobile.setFocusable(true);
        hideotp.setText(String.valueOf(number));

    }

    public void loginpage(View view) {
            Intent intent=new Intent(OTP.this,UserLoginActivity.class);
            startActivity(intent);
    }
}

