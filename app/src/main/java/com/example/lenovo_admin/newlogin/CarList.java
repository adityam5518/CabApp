package com.example.lenovo_admin.newlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;

public class CarList extends AppCompatActivity {
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/stock.php";
    String finalResult;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    static String BrandHolder = "";
    static String ModelHolder = "";
    static String RateHolder="";
    String MobileNo="";
    public final static String mob= "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);
        Intent intent = getIntent();
        MobileNo= intent.getStringExtra(DashboardActivity.mob);

    }

    public void booki10(View view) {
        BrandHolder = "Hyundai";
        ModelHolder = "i10";
        RateHolder="12";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void CheckStockFunction(final String Brand, final String Model) {
        class CheckStockClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(CarList.this, "Checking Car Availability", null, true, true);
            }

            @Override
            protected void onPostExecute(String HttpResponseMsg) {
                super.onPostExecute(HttpResponseMsg);
                progressDialog.dismiss();

                if (HttpResponseMsg.equalsIgnoreCase("Car available")) {
                    finish();
                    Intent intent = new Intent(CarList.this,Order.class);
                    intent.putExtra("CarBrand",BrandHolder);
                    intent.putExtra("CarModel",ModelHolder);
                    intent.putExtra("MobileNo",MobileNo);
                    intent.putExtra("rate",RateHolder);
                    Toast.makeText(CarList.this, HttpResponseMsg, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(CarList.this, HttpResponseMsg, Toast.LENGTH_LONG).show();
                }

            }
            @Override
            protected String doInBackground(String... params) {
                hashMap.put("carbrand",params[0]);
                hashMap.put("carmodel",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        CheckStockClass checkStockClass=new CheckStockClass();
        checkStockClass.execute(Brand,Model);
    }


    public void bookfigo(View view) {
        BrandHolder= "Ford";
        ModelHolder = "Figo";
        RateHolder="13";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void booki20(View view) {
        BrandHolder = "Hyundai";
        ModelHolder = "i20";
        RateHolder="13";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void bookbrezza(View view) {
        BrandHolder = "Maruti";
        ModelHolder = "Brezza";
        RateHolder="15";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void bookswift(View view) {
        BrandHolder = "Maruti";
        ModelHolder = "Swift";
        RateHolder="14";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void bookscorpio(View view) {
        BrandHolder = "Mahindra";
        ModelHolder = "Scorpio";
        RateHolder="15";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void bookbaleno(View view) {
        BrandHolder = "Maruti";
        ModelHolder = "Baleno";
        RateHolder="15";
        CheckStockFunction(BrandHolder,ModelHolder);
    }

    public void bookxuv(View view) {
        BrandHolder = "Mahindra";
        ModelHolder = "XUV";
        RateHolder="16";
        CheckStockFunction(BrandHolder,ModelHolder);
    }
}
