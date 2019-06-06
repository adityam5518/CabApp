package com.example.lenovo_admin.newlogin;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import static android.view.View.INVISIBLE;
import static java.sql.Types.NULL;


public class Order extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    String[] SPINNERLIST = {"S1:Near RTO,Sangamvadi,Pune", "S2:944,JM Road,Pune", "21/53,Pune Station", "42,C lane,Hadapsar"};
    int day,day2, month,month2,year2, year, hours, minutes, hoursdo, minutesdo;
    int dayFinal, monthFinal, yearFinal,dayFinal1, monthFinal1, yearFinal1, hoursFinal, minutesFinal, hoursFinaldo, minutesFinaldo;
    String CurrentDate;
    Calendar c;
    int ch,dch;
    Button orderCars, codebtn;
    TextView dropdateTV,dateTV, pickupTV, dropoffTV,tclose;
    public final static String MobHolder = "";
    String CarBrand = "";
    String Mob;
    String CarModel = "";
    String MobileNo = "";
    String CarRate = "";
    String Date,Date2;
    String Ptime = "";
    String Dtime = "";
    String Bill;
    String HttpURL = "https://rentmycar.000webhostapp.com/Loginapp/Order.php";
    String finalResult;
    final int[] month1 = new int[1];
    final int[] year1 = new int[1];
    final int[] hour1 = new int[1];
    final int[] minute1 = new int[1];
    final int[] day1 = new int[1];
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText dist,tcode;
    TextView carModel, carBrand, mobileNo, txtclose, rate, billshow;
    int billamount, finalbill;
    Dialog dialog;
    String spinner_value="";
    TextView coupon,applied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        final MaterialBetterSpinner betterSpinner = findViewById(R.id.selectCity);
        betterSpinner.setAdapter(arrayAdapter);
        dialog = new Dialog(this);
        dateTV = findViewById(R.id.dateTV);
        dropdateTV = findViewById(R.id.dropdate);
        pickupTV = findViewById(R.id.pickupTV);
        dropoffTV = findViewById(R.id.dropoffTV);
        billshow = findViewById(R.id.billshow);
        orderCars = findViewById(R.id.orderCars);
        Intent intent = getIntent();
        CarBrand = intent.getStringExtra("CarBrand");
        CarModel = intent.getStringExtra("CarModel");
        MobileNo = intent.getStringExtra("MobileNo");
        CarRate = intent.getStringExtra("rate");
        carBrand = findViewById(R.id.carbrand);
        carModel = findViewById(R.id.carmodel);
        mobileNo = findViewById(R.id.mobileno);
        coupon=findViewById(R.id.coupon);
        applied=findViewById(R.id.applied);
        dist = (EditText) findViewById(R.id.distance);
        rate = findViewById(R.id.ratedisplay);
        rate.setText(CarRate);
        carModel.setText(CarModel);
        carBrand.setText(CarBrand);
        mobileNo.setText(MobileNo);
        final int item2=NULL;
        Mob = mobileNo.getText().toString();
        dateTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dch = 1;
                    Calendar c = Calendar.getInstance();
                    year1[0] = c.get(Calendar.YEAR);
                    year = year1[0];
                    month1[0] = c.get(Calendar.MONTH);
                    month = month1[0];
                    day1[0] = c.get(Calendar.DAY_OF_MONTH);
                    day = day1[0];
                    hour1[0] = c.get(Calendar.HOUR_OF_DAY);
                    minute1[0] = c.get(Calendar.MINUTE);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Order.this, Order.this, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.show();
                }
            });
        dropdateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dch=2;
                if (dateTV.getText().toString().equals("") || pickupTV.getText().toString().equals(""))
                    Toast.makeText(Order.this, "Choose Pickup Date and time First", Toast.LENGTH_LONG).show();
                else {
                    year2 = yearFinal;
                    month2 = monthFinal-1;
                    day2 = dayFinal;
                    DatePickerDialog datePickerDialog1 = new DatePickerDialog(Order.this, Order.this, year2, month2, day2);
                    datePickerDialog1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                    datePickerDialog1.show();
                }
            }
        });


        pickupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch = 1;
                if (!dateTV.getText().toString().equalsIgnoreCase("")) {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(Order.this, Order.this,
                            hours, minutes, DateFormat.is24HourFormat(Order.this));
                    timePickerDialog.show();

                } else
                    Toast.makeText(Order.this, "Please Select Date", Toast.LENGTH_LONG).show();
            }
            });

        dropoffTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ch = 2;
                if (!pickupTV.getText().toString().equalsIgnoreCase("") && !dropdateTV.getText().toString().equals(""))
                        {
                            TimePickerDialog timePickerDialog = new TimePickerDialog(Order.this, Order.this,
                                    hoursdo, minutesdo, DateFormat.is24HourFormat(Order.this));
                            timePickerDialog.show();
                        }
                else
                    Toast.makeText(Order.this, "Please Select Pickup time and DropOff date", Toast.LENGTH_LONG).show();
            }
        });
        orderCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bill=billshow.getText().toString();
                if(TextUtils.isEmpty(CarBrand )|| TextUtils.isEmpty(CarModel) || TextUtils.isEmpty(spinner_value)||TextUtils.isEmpty(Date )|| TextUtils.isEmpty(Ptime)|| TextUtils.isEmpty(Dtime)||TextUtils.isEmpty(Mob)||TextUtils.isEmpty(bill) || TextUtils.isEmpty(Date2) )
                {
                    Toast.makeText(Order.this, "Please Fill all the details", Toast.LENGTH_LONG).show();
                }
                else{
                    BookCarFunction(CarBrand, CarModel,spinner_value, Date, Ptime, Dtime, Mob,bill,Date2);
                }
            }
        });
        betterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?>adapterView, View view, int position, long l) {
                spinner_value = adapterView.getItemAtPosition(position).toString();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDateSet(DatePicker view, int Year, int Month, int dayOfMonth) {
        switch (dch) {
            case 1:
                      yearFinal = Year;
                      monthFinal = Month + 1;
                      dayFinal = dayOfMonth;
                      dateTV.setText(yearFinal + "-" + monthFinal + "-" + dayFinal);
                      Calendar c = Calendar.getInstance();
                      Date = dateTV.getText().toString();
                      break;
            case  2:
                    yearFinal1 = Year;
                    monthFinal1 = Month + 1;
                    dayFinal1 = dayOfMonth;
                    dropdateTV.setText(yearFinal1 + "-" + monthFinal1 + "-" + dayFinal1);
                    Date2 = dropdateTV.getText().toString();
                    break;
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        switch (ch) {
            case 1:
                hoursFinal = hourOfDay;
                minutesFinal = minute;
                    if (dayFinal == day && (monthFinal - 1) == month && yearFinal == year)
                    {
                        if ((hourOfDay <= hour1[0]) && (minute <= minute1[0]))
                        {
                        Toast.makeText(Order.this, "Dont Select Past time", Toast.LENGTH_SHORT).show();
                        }
                    else
                        {
                        pickupTV.setText(hoursFinal + ":" + minutesFinal);
                        Ptime = pickupTV.getText().toString();
                        }
                    }
                else {
                    pickupTV.setText(hoursFinal + ":" + minutesFinal);
                    Ptime = pickupTV.getText().toString();
                }
                break;
            case 2:
                hoursFinaldo = hourOfDay;
                minutesFinaldo = minute;
                if(dateTV.getText().toString().equals(dropdateTV.getText().toString()))
                {
                    if ((hoursFinaldo <= hoursFinal) && (minutesFinaldo <= minutesFinal)) {
                        Toast.makeText(Order.this, "Please select proper time ", Toast.LENGTH_SHORT).show();
                        break;

                    }
                }
                        dropoffTV.setText(hoursFinaldo + ":" + minutesFinaldo);
                        Dtime = dropoffTV.getText().toString();
                break;
        }
    }
    public void BookCarFunction(final String Brand, final String Model,final String station, final String bdate, final String ptime, final String dtime, final String mobileno,final String billamt,final String dropdate) {
        class BookCarClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(Order.this, "Checking Car Availability", null, true, true);
            }
            @Override
            protected void onPostExecute(String HttpResponseMsg) {
                super.onPostExecute(HttpResponseMsg);
                progressDialog.dismiss();
                if (HttpResponseMsg.equalsIgnoreCase("Order Succesful")) {
                    finish();
                    Intent intent = new Intent(Order.this, DashboardActivity.class);
                    intent.putExtra(MobHolder, MobileNo);
                    Toast.makeText(Order.this, HttpResponseMsg, Toast.LENGTH_LONG).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(Order.this, HttpResponseMsg, Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("carbrand", params[0]);
                hashMap.put("carmodel", params[1]);
                hashMap.put("station", params[2]);
                hashMap.put("date", params[3]);
                hashMap.put("ptime", params[4]);
                hashMap.put("dtime", params[5]);
                hashMap.put("mobileno", params[6]);
                hashMap.put("bill",params[7]);
                hashMap.put("dropdate",params[8]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        BookCarClass bookCarClass = new BookCarClass();
        bookCarClass.execute(Brand, Model,station, bdate, ptime, dtime, mobileno,billamt,dropdate);
    }

    public void billamt(View view) {
        if(coupon.getVisibility()==View.INVISIBLE) {
            applied.setVisibility(View.INVISIBLE);
            coupon.setVisibility(View.VISIBLE);
        }

        if(isValidbill(dist.getText().toString())) {
            int a = Integer.parseInt(dist.getText().toString());
            int b = Integer.parseInt(rate.getText().toString());
            finalbill = a * b;
            billshow.setText(String.valueOf(finalbill));
        }
        else
            Toast.makeText(Order.this,"Invalid distance",Toast.LENGTH_LONG).show();
    }

    public void offer(View view) {
        if(TextUtils.isEmpty(billshow.getText().toString()))
        {
            Toast.makeText(Order.this,"Get bill First then apply coupon",Toast.LENGTH_LONG);
        }
        else
        {
            dialog.setContentView(R.layout.applycode);
            tclose =(TextView)dialog.findViewById(R.id.tclose);
            tcode=dialog.findViewById(R.id.txtcode);
            codebtn =(Button)dialog.findViewById(R.id.codebtn);
            tclose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            codebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String codes=tcode.getText().toString();
                    if(codes.equals("FLAT"))
                    {
                        Double bill=finalbill*0.8;
                        int newbills=bill.intValue();

                        billshow.setText(String.valueOf(newbills));
                        coupon.setVisibility(INVISIBLE);
                        applied.setVisibility(View.VISIBLE);
                    }
                    else{
                        Toast.makeText(Order.this,"Invalid code",Toast.LENGTH_LONG).show();
                    }
                    dialog.dismiss();
                }
            });

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
    private boolean isValidbill(String amtt) {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", amtt))
            check =true;
        else
            check=false;
        return check;
    }
}