package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



public class MainActivity extends AppCompatActivity {

    private EditText to;
    private EditText cc;
    private EditText bcc;
    private EditText subject;
    private EditText body;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find all EditView
        to = (EditText) findViewById(R.id.to);
        cc = (EditText) findViewById(R.id.cc);
        bcc = (EditText) findViewById(R.id.bcc);
        subject = (EditText) findViewById(R.id.subject);
        body = (EditText) findViewById(R.id.body);

        // Read historical data from sharedPreferences.
        sharedPreferences = this.getSharedPreferences("data", MODE_PRIVATE);
        String toStr = sharedPreferences.getString("to","");
        String ccStr = sharedPreferences.getString("cc","");
        String bccStr = sharedPreferences.getString("bcc","");
        String subjectStr = sharedPreferences.getString("subject","");
        String bodyStr = sharedPreferences.getString("body","");
        // Set historical data in EditView.
        if ( toStr != null ){
            to.setText(toStr);
        }
        if (ccStr != null ){
            cc.setText(ccStr);
        }
        if (bccStr != null ){
            bcc.setText(bccStr);
        }
        if (subjectStr != null ){
            subject.setText(subjectStr);
        }
        if (bodyStr != null ){
            body.setText(bodyStr);
        }

        // Clear present data in EditView.
        Button clear = findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                to.setText("");
                cc.setText("");
                bcc.setText("");
                subject.setText("");
                body.setText("");
            }
        });

        // Send email and jump to emailReading Activity
        Button send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Store all relevant email data in sharedPreferences。
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("to",to.getText().toString());
                editor.putString("cc",cc.getText().toString());
                editor.putString("bcc",bcc.getText().toString());
                editor.putString("subject",subject.getText().toString());
                editor.putString("body",body.getText().toString());
                editor.apply();
                //jump to emailReading Activity
                Intent intent = new Intent(MainActivity.this, emailReading.class);
                startActivity(intent);
            }
        });

    }


    @Override
    protected void onStop() {
        super.onStop();

        // preserve all the input data
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("to", to.getText().toString());
        editor.putString("cc", cc.getText().toString());
        editor.putString("bcc", bcc.getText().toString());
        editor.putString("subject", subject.getText().toString());
        editor.putString("body", body.getText().toString());
        editor.apply();

    }
}