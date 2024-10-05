package com.example.todoassignment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText uname,psswd;
    TextView signup;
    Button loginBtn;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        uname=findViewById(R.id.usernameText);
        psswd=findViewById(R.id.logPassword);
        loginBtn=findViewById(R.id.loginButton);
        signup=findViewById(R.id.textSignUp);
        databaseHelper=new DatabaseHelper(this);
        signup.setOnClickListener(v->{
            Intent i=new Intent(MainActivity.this,RegisterActivity.class);
            startActivity(i);
        });
        loginBtn.setOnClickListener(v->{
            String user=uname.getText().toString().trim();
            String pass=psswd.getText().toString().trim();
            if(user.isEmpty() || pass.isEmpty()){
                Toast.makeText(MainActivity.this,"Please Enter Username and Password",Toast.LENGTH_SHORT).show();
            }
            else{
                String loggedInUname=databaseHelper.checkLogin(user,pass);
                if(loggedInUname!=null){
                    Intent i=new Intent(MainActivity.this,HomeActivity.class);
                    i.putExtra("USERNAME",loggedInUname);
                    startActivity(i);
                    finish();
                }
                else{
                    Toast.makeText(MainActivity.this,"Invalid Username or Password",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}