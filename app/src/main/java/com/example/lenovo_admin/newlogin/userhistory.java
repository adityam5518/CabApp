package com.example.lenovo_admin.newlogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class userhistory extends AppCompatActivity {
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/getdata.php";
    String mobHolder="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhistory);
        Intent intent=getIntent();
        mobHolder=intent.getStringExtra("user");
        OrderFunction(mobHolder);
    }
    public void OrderFunction(final String mobileno){

        class OrderFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(userhistory.this, "Loading Data", null, true, true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                final ListView lView = (ListView) findViewById(R.id.lvMain1);

                String[] from = {"name_item","name_item2","name_item3","name_item4","name_item5","name_item6","name_item7","name_item8","name_item9","name_item10"};

                int[] to = {R.id.name_item,R.id.name_item2,R.id.name_item3,R.id.name_item4,R.id.name_item5,R.id.name_item6,R.id.name_item7,R.id.name_item8,R.id.name_item9,R.id.name_item10};
                ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
                HashMap<String, String> hashmap;

                try {
                    JSONObject json = new JSONObject(httpResponseMsg);
                    JSONArray jArray = json.getJSONArray("platform");

                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject friend = jArray.getJSONObject(i);
                        JSONObject friend2 = jArray.getJSONObject(i);
                        JSONObject friend3 = jArray.getJSONObject(i);
                        JSONObject friend4 = jArray.getJSONObject(i);
                        JSONObject friend5 = jArray.getJSONObject(i);
                        JSONObject friend6 = jArray.getJSONObject(i);
                        JSONObject friend7 = jArray.getJSONObject(i);
                        JSONObject friend8 = jArray.getJSONObject(i);
                        JSONObject friend9 = jArray.getJSONObject(i);
                        JSONObject friend10 = jArray.getJSONObject(i);

                        String nameOS = friend.getString("carbrand");
                        String nameOS2 = friend2.getString("carmodel");
                        String nameOS3 = friend3.getString("ptime");
                        String nameOS4 = friend4.getString("dtime");
                        String nameOS5 = friend5.getString("OrderNo");
                        String nameOS6 = friend6.getString("customer");
                        String nameOS7 = friend7.getString("PickupDate");
                        String nameOS8 = friend8.getString("billamt");
                        String nameOS9 = friend9.getString("dropdate");
                        String nameOS10 = friend9.getString("Station");


                        hashmap = new HashMap<String, String>();
                        hashmap.put("name_item", "" + nameOS);
                        hashmap.put("name_item2", "" + nameOS2);
                        hashmap.put("name_item3", "" + nameOS3);
                        hashmap.put("name_item4", "" + nameOS4);
                        hashmap.put("name_item5", "" + nameOS5);
                        hashmap.put("name_item6", "" + nameOS6);
                        hashmap.put("name_item7", "" + nameOS7);
                        hashmap.put("name_item8", "" + nameOS8);
                        hashmap.put("name_item9", "" + nameOS9);
                        hashmap.put("name_item10", "" + nameOS10);
                        arrayList.add(hashmap);
                    }
                    final SimpleAdapter adapter = new SimpleAdapter(userhistory.this, arrayList, R.layout.item, from, to);
                    lView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("mobile",params[0]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        OrderFunctionClass orderFunctionClass = new OrderFunctionClass();

        orderFunctionClass.execute(mobileno);
    }
}
