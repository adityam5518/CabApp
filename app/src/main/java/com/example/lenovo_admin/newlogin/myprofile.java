package com.example.lenovo_admin.newlogin;

import android.app.Dialog;
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

public class myprofile extends AppCompatActivity {
    EditText mob, old, new1;
    Button change;
    String mobholder, oldholder, newholder;
    HashMap<String, String> hashMap = new HashMap<>();
    Dialog progressDialog;
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/profile.php";
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        mob = findViewById(R.id.mob);
        old = findViewById(R.id.old);
        new1 = findViewById(R.id.new1);
        change = findViewById(R.id.cpass);
        Intent intent = getIntent();
        mobholder = intent.getStringExtra(DashboardActivity.mob);
        mob.setText(mobholder);
        mob.setFocusable(false);
    }

    public void pass(View view) {
        oldholder = old.getText().toString();
        newholder = new1.getText().toString();
        if (TextUtils.isEmpty(mobholder) || TextUtils.isEmpty(oldholder) || TextUtils.isEmpty(newholder)) {
            Toast.makeText(myprofile.this, "Fill all Fields", Toast.LENGTH_LONG).show();
           }
        else{
            if (newholder.length() > 6)
                   ChangepassFunction(mobholder, oldholder, newholder);
        else
            Toast.makeText(myprofile.this, "Password too Short(min 6) ", Toast.LENGTH_LONG).show();
    }

}

    private void ChangepassFunction(String mobHolder11, String oldHolder1, String newHolder1) {

        class ChangeClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(myprofile.this, "Changing Password", null, true, true);
            }

            @Override
            protected void onPostExecute(String htttResponse) {
                super.onPostExecute(htttResponse);
                progressDialog.dismiss();
                Toast.makeText(myprofile.this, htttResponse, Toast.LENGTH_LONG).show();
            }


            @Override
            protected String doInBackground(String... param) {
                hashMap.put("mobile", param[0]);
                hashMap.put("old", param[1]);
                hashMap.put("new", param[2]);
                String finalresult = httpParse.postRequest(hashMap, HttpURL);
                return finalresult;
            }
        }
        ChangeClass changeClass = new ChangeClass();
        changeClass.execute(mobHolder11, oldHolder1, newHolder1);
    }
}