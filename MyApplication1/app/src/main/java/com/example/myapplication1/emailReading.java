package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class emailReading extends AppCompatActivity {

    /*
    This method can send email data from one email to another emails.
     */
    private void send(String to, String cc, String bcc, String subject, String body) {
        final String userName = "609899054@qq.com";
        // Here is the authentication password to activate smtp/pop3/imap service, not the login password
        final String passWord = "ukzyiijalaxfbfbd";

        Properties pro = System.getProperties();
        pro.put("mail.smtp.host", "smtp.qq.com");
        pro.put("mail.smtp.port", "25");
        pro.put("mail.smtp.auth", "true");
        // Construct a mailing session based on the mail session attributes and password validator
        Session sendMailSession = Session.getDefaultInstance(pro,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, passWord);
                    }
                });
        // Create an email message based on the session
        Message mailMessage = new MimeMessage(sendMailSession);
        // Set mail message
        try {
            mailMessage.setFrom(new InternetAddress(userName));
            if (!to.equals("")){
                mailMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(to));
            }
            if (!cc.equals("")){
                mailMessage.setRecipient(Message.RecipientType.CC,
                        new InternetAddress(cc));
            }
            if (!bcc.equals("")){
                mailMessage.setRecipient(Message.RecipientType.BCC,
                        new InternetAddress(bcc));
            }
            mailMessage.setSubject(subject);
            mailMessage.setSentDate(new Date());
            mailMessage.setText(body);
            Transport.send(mailMessage);
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_reading);

        // find all TestView
        TextView to = findViewById(R.id.readTo);
        TextView cc = findViewById(R.id.readCc);
        TextView subject = findViewById(R.id.readSubject);
        TextView body = findViewById(R.id.readBody);

        // Read email data from sharedPreferences.
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        final String emailTo = sharedPreferences.getString("to","");
        final String emailCC = sharedPreferences.getString("cc","");
        final String emailBCC = sharedPreferences.getString("bcc","");
        final String emailSubject = sharedPreferences.getString("subject","");
        final String emailBody = sharedPreferences.getString("body","");
        // Set email data
        to.setText(emailTo);
        cc.setText(emailCC);
        subject.setText(emailSubject);
        body.setText(emailBody);

        // Send Email
        new Thread(){
            @Override
            public void run()
            {
                // Here is the code for network access.
                send(emailTo, emailCC, emailBCC, emailSubject, emailBody);
            }
        }.start();

        // Back to mainActivity
        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //jump to mainActivity
                Intent intent = new Intent(emailReading.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}