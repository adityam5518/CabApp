package com.example.lenovo_admin.newlogin;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.textclassifier.TextLinks;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.TextUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lenovo_admin.newlogin.contactus.EMAIL_KEY;
import static com.example.lenovo_admin.newlogin.contactus.FORM_DATA_TYPE;
import static com.example.lenovo_admin.newlogin.contactus.MESSAGE_KEY;
import static com.example.lenovo_admin.newlogin.contactus.SUBJECT_KEY;

public class contactus extends AppCompatActivity {
    public static final MediaType FORM_DATA_TYPE
            = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
    //URL derived from form URL
    public static final String URL = "https://docs.google.com/forms/d/e/1FAIpQLSfBrr_uT6CFK9VhQzjkIQALVtlJvNFTcHKB2GYrpvBcKvfiDA/formResponse";
    //input element ids found from the live form page
    public static final String EMAIL_KEY = "entry.1045781291";
    public static final String SUBJECT_KEY = "entry.2005620554";
    public static final String MESSAGE_KEY = "entry.839337160";
    public static final String mob = "";
    private Context context;
    private EditText emailEditText;
    private EditText mobEditText;
    private EditText messageEditText;
    String MobHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactus);
        //save the activity in a context variable to be used afterwards
        context = this;
        //Get references to UI elements in the layout
        Button sendButton = (Button) findViewById(R.id.sendButton);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        mobEditText = (EditText) findViewById(R.id.MobeditText);
        messageEditText = (EditText) findViewById(R.id.messageEditText);
        Intent intent = getIntent();
        MobHolder = intent.getStringExtra(DashboardActivity.mob);
        mobEditText.setText(MobHolder);
        mobEditText.setFocusable(false);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Make sure all the fields are filled with values
                if (TextUtils.isEmpty(emailEditText.getText().toString()) ||
                        TextUtils.isEmpty(mobEditText.getText().toString()) ||
                        TextUtils.isEmpty(messageEditText.getText().toString())) {
                    Toast.makeText(context, "All fields are mandatory.", Toast.LENGTH_LONG).show();
                    return;
                }
                //Check if a valid email is entered
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches()) {
                    Toast.makeText(context, "Please enter a valid email.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Create an object for PostDataTask AsyncTask
                contactus.PostDataTask postDataTask = new contactus.PostDataTask();

                //execute asynctask
                postDataTask.execute(URL, emailEditText.getText().toString(),
                        mobEditText.getText().toString(),
                        messageEditText.getText().toString());
            }
        });
    }
        //AsyncTask to send data as a http POST request
        private class PostDataTask extends AsyncTask<String, Void, Boolean> {

            @Override
            protected Boolean doInBackground(String... contactData) {
                Boolean result = true;
                String url = contactData[0];
                String email = contactData[1];
                String subject = contactData[2];
                String message = contactData[3];
                String postBody = "";
                try {
                    //all values must be URL encoded to make sure that special characters like & | ",etc.
                    //do not cause problems
                    postBody = EMAIL_KEY + "=" + URLEncoder.encode(email, "UTF-8") +
                            "&" + SUBJECT_KEY + "=" + URLEncoder.encode(subject, "UTF-8") +
                            "&" + MESSAGE_KEY + "=" + URLEncoder.encode(message, "UTF-8");
                } catch (UnsupportedEncodingException ex) {
                    result = false;
                }
                try {
                    //Create OkHttpClient for sending request
                    OkHttpClient client = new OkHttpClient();
                    //Create the request body with the help of Media Type
                    RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                    Request request = new Request.Builder()
                            .url(url)
                            .post(body)
                            .build();
                    //Send the request
                    Response response = client.newCall(request).execute();
                } catch (IOException exception) {
                    result = false;
                }
                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                //Print Success or failure message accordingly
                Toast.makeText(context, result ? "Message successfully sent!" : "There was some error in sending message. Please try again after some time.", Toast.LENGTH_LONG).show();
                finish();
            }

        }

}



