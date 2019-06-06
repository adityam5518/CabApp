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
import static com.example.lenovo_admin.newlogin.sendfeedback.EMAIL_KEY;
import static com.example.lenovo_admin.newlogin.sendfeedback.FORM_DATA_TYPE;
import static com.example.lenovo_admin.newlogin.sendfeedback.MESSAGE_KEY;
import static com.example.lenovo_admin.newlogin.sendfeedback.SUBJECT_KEY;

public class sendfeedback extends AppCompatActivity {
        public static final MediaType FORM_DATA_TYPE
                = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        //URL derived from form URL
        public static final String URL="https://docs.google.com/forms/d/e/1FAIpQLSegaA5weHwxJnxL-OBPv0i7FiBff-o5o2UpSntFEU7gf90UQQ/formResponse";
        //input element ids found from the live form page
        public static final String EMAIL_KEY="entry.879531967";
        public static final String SUBJECT_KEY="entry.485428648";
        public static final String MESSAGE_KEY="entry.1696159737";
    public static final String mob = "";
        private Context context;
        private EditText emailEditText;
        private EditText mobEditText;
        private EditText messageEditText;
    String MobHolder;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sendfeedback);
            context =this;
            Button sendButton = (Button)findViewById(R.id.sendButton);
            emailEditText = (EditText)findViewById(R.id.emailEditText);
            mobEditText = (EditText)findViewById(R.id.MobeditText);
            messageEditText = (EditText)findViewById(R.id.messageEditText);
            Intent intent = getIntent();
            MobHolder = intent.getStringExtra(DashboardActivity.mob);
            mobEditText.setText(MobHolder);
            mobEditText.setFocusable(false);
            sendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(TextUtils.isEmpty(emailEditText.getText().toString()) ||
                            TextUtils.isEmpty(mobEditText.getText().toString()) ||
                            TextUtils.isEmpty(messageEditText.getText().toString()))
                    {
                        Toast.makeText(context,"All fields are mandatory.",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(!android.util.Patterns.EMAIL_ADDRESS.matcher(emailEditText.getText().toString()).matches())
                    {
                        Toast.makeText(context,"Please enter a valid email.",Toast.LENGTH_LONG).show();
                        return;
                    }

                    PostDataTask postDataTask = new PostDataTask();

                    postDataTask.execute(URL,emailEditText.getText().toString(),
                            mobEditText.getText().toString(),
                            messageEditText.getText().toString());
                }
            });
        }

    private class PostDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... contactData) {
            Boolean result = true;
            String url = contactData[0];
            String email = contactData[1];
            String subject = contactData[2];
            String message = contactData[3];
            String postBody="";
            try {
                postBody = EMAIL_KEY+"=" + URLEncoder.encode(email,"UTF-8") +
                        "&" + SUBJECT_KEY + "=" + URLEncoder.encode(subject,"UTF-8") +
                        "&" + MESSAGE_KEY + "=" + URLEncoder.encode(message,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
                result=false;
            }
            try{
                OkHttpClient client = new OkHttpClient();
                RequestBody body = RequestBody.create(FORM_DATA_TYPE, postBody);
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                Response response = client.newCall(request).execute();
            }catch (IOException exception){
                result=false;
            }
            return result;
        }
        @Override
        protected void onPostExecute(Boolean result){
            Toast.makeText(context,result?"Message successfully sent!":"There was some error in sending message. Please try again after some time.",Toast.LENGTH_LONG).show();
            finish();
        }

    }
}