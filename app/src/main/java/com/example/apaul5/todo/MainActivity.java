package com.example.apaul5.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class MainActivity extends AppCompatActivity {

    //member variabe to hold Arraylist
    ArrayList<String> items;
    //member for holding ArrayAdapter
    ArrayAdapter<String> itemsAdapter;
    //member for holding the list views
    ListView lvItems;
    //Member variable to hold text input
    EditText taskText;

    private int ACTIVITY_ID=10000;
    //member to Hold the Array position
    private int editPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.IvItems);
        taskText = (EditText) findViewById(R.id.etEditText);
        poulateArrayElements();
        lvItems.setAdapter(itemsAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                editPosition=position;
                launchEditActivityView(items.get(position));

            }
        });

    }

    public void launchEditActivityView(String editTextValue) {
        // first parameter is the context, second is the class of the activity to launch
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("taskName", editTextValue);
        startActivityForResult(i, ACTIVITY_ID);
    }

    public void poulateArrayElements(){
        items=new ArrayList<String>();
        readItems();
        itemsAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
    }

    public void onAddItem(View view) {
        itemsAdapter.add(taskText.getText().toString());
        taskText.setText("");
        writeItems();

    }

    private void readItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            items=new ArrayList<String>(FileUtils.readLines(file));
        }catch(IOException e){

        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File file = new File(filesDir,"todo.txt");
        try{
            FileUtils.writeLines(file,items);
        }catch(IOException e){
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_ID ) {
            items.set(editPosition,data.getExtras().getString("taskName"));
            itemsAdapter.notifyDataSetChanged();
            writeItems();

        }
    }
}
