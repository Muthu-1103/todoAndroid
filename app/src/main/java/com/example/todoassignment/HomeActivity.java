package com.example.todoassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoassignment.Adapter.todoAdapter;
import com.example.todoassignment.Model.todoModel;
import com.example.todoassignment.utils.DBHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements onDialogCloseListener{
    TextView username;
    DatabaseHelper databaseHelper;
    Button logout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DBHelper myDB;
    private List<todoModel>mlist;
    private todoAdapter adapter;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        recyclerView=findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);
        myDB=new DBHelper(HomeActivity.this);
        mlist=new ArrayList<>();
        adapter=new todoAdapter(myDB,HomeActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mlist=myDB.getAllTask();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);
        fab.setOnClickListener(v->{
            addNewTask.newInstance().show(getSupportFragmentManager(),addNewTask.TAG);
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(new recyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        username=findViewById(R.id.textLog);
        databaseHelper=new DatabaseHelper(this);
        logout=findViewById(R.id.logoutButton);
        String user=getIntent().getStringExtra("USERNAME");
        username.setText("Welcome "+user);
        logout.setOnClickListener(v->{
            Logout();
        });
    }

    private void Logout() {
        Intent i=new Intent(HomeActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mlist=myDB.getAllTask();
        Collections.reverse(mlist);
        adapter.setTasks(mlist);
        adapter.notifyDataSetChanged();
    }
}