package com.example.lenovo_admin.newlogin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

import okhttp3.HttpUrl;

public class adminpanel extends AppCompatActivity {
    EditText model,brand,qty;
    String brandHolder,modelHolder,qtyHolder;
    Button addcar;
    HashMap<String,String>hashMap=new HashMap<>();
    Dialog progressDialog;
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/updatecar.php";
    HttpParse httpParse=new HttpParse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpanel);
        model=findViewById(R.id.model);
        brand=findViewById(R.id.brand);
        qty=findViewById(R.id.qty);
        addcar=findViewById(R.id.addcar);
    }

    public void addcar(View view) {
        brandHolder=brand.getText().toString();
        modelHolder=model.getText().toString();
        qtyHolder=qty.getText().toString();

        addcarFunction(brandHolder,modelHolder,qtyHolder);
    }

    private void addcarFunction(String brandHolder11, String modelHolder1, String qtyHolder1) {

        class AddcarClass extends AsyncTask<String,Void,String>
        {
            @Override
            protected void onPreExecute(){
                super.onPreExecute();
                progressDialog = ProgressDialog.show(adminpanel.this,"Adding Cars",null,true,true);
            }
            @Override
            protected  void onPostExecute(String htttResponse){
                super.onPostExecute(htttResponse);
                progressDialog.dismiss();
                    Toast.makeText(adminpanel.this,htttResponse,Toast.LENGTH_LONG).show();
                }


            @Override
            protected String doInBackground(String... param) {
                hashMap.put("brand",param[0]);
                hashMap.put("model",param[1]);
                hashMap.put("qty",param[2]);
                String finalresult=httpParse.postRequest(hashMap, HttpURL);
                return finalresult;
            }
        }
        AddcarClass addcarClass=new AddcarClass();
        addcarClass.execute(brandHolder11,modelHolder1,qtyHolder1);
    }
    public void orderhis(View view) {
        Intent intent=new Intent(adminpanel.this,userhistory.class);
        String user="admin";
        intent.putExtra("user",user);
        startActivity(intent);
    }

}