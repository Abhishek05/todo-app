package com.example.apaul5.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {


    EditText editTask;
    Intent v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTask = (EditText) findViewById(R.id.edTask);
        v = getIntent();
        String username = v.getStringExtra("taskName");
        editTask.setText(username);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onSubmit(View viw) {
        // Prepare data intent
        // Pass relevant data back as a result
        //Intent intent = new Intent(this, MainActivity.class);
        String editSaveTask=editTask.getText().toString();
        Intent intent = new Intent();
        if(!(editSaveTask.equalsIgnoreCase(null)) && !(editSaveTask.equals(""))){
            intent.putExtra("taskName", editSaveTask);
            if (getParent() == null) {
                setResult(Activity.RESULT_OK, intent);
            } else {
                getParent().setResult(Activity.RESULT_OK, intent);
            }
            this.finish();

        }else{
            Toast.makeText(this,"Your Task Cannot be Empty...", Toast.LENGTH_SHORT).show();
        }


    }

}
