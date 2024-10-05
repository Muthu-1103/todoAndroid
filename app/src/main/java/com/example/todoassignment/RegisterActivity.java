package com.example.todoassignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RegisterActivity extends AppCompatActivity {
    EditText uname,psswd;
    TextView login;
    Button signup;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        uname=findViewById(R.id.textLogin);
        psswd=findViewById(R.id.passwordText);
        signup=findViewById(R.id.signupButton);
        login=findViewById(R.id.logText);
        databaseHelper=new DatabaseHelper(this);

        login.setOnClickListener(v->{
            Intent i=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(i);
        });

        signup.setOnClickListener(v->{
            String user=uname.getText().toString().trim();
            String pass=uname.getText().toString().trim();
            if(user.isEmpty()||pass.isEmpty()){
                Toast.makeText(RegisterActivity.this,"Please Enter Username and Password",Toast.LENGTH_SHORT).show();
            }
            else{
                if(databaseHelper.insertData(user,pass)){
                    Toast.makeText(RegisterActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                    i.putExtra("USERNAME",user);
                    startActivity(i);
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Username Already Exists...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}