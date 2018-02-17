package com.example.arfaatali.AndroidCallSilencerApp;

/**
 * Created by Arfaat Ali on 2/16/2017.
*/

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Unknown_NumberActivity extends ActionBarActivity {
    Button btnSubmit;
    EditText edt1,edt2;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unknown__number);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);

        edt1=(EditText)findViewById(R.id.txtNamUn);
        edt2=(EditText)findViewById(R.id.txtNoUn);
        btnSubmit=(Button)findViewById(R.id.btnUnknown);
        myDb=new DatabaseHelper(this);
        savedataList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_item, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.ListButton:
                Intent intent = new Intent(Unknown_NumberActivity.this, BlockedListActivity.class);
                startActivity(intent);
                break;
            case R.id.unknown:
                Toast.makeText(Unknown_NumberActivity.this, "You are On this ", Toast.LENGTH_SHORT).show();
            default:
                break;
        }

        return true;
    }
    public void savedataList(){

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddData();
            }
        });
    }
    public void AddData(){
        String num;
        String name;
        name=edt1.getText().toString();
        num=edt2.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("EEE,d MMM yyyy,HH:mm:ss");
        Date today = (Date) Calendar.getInstance().getTime();
        String receiveDate = df.format(today);
        boolean inserted = myDb.insertData(name,receiveDate, num);

        if (inserted == true) {
            Toast.makeText(Unknown_NumberActivity.this, "Data Is Saved", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(Unknown_NumberActivity.this, "Data is not inserted", Toast.LENGTH_LONG).show();
        }
        myDb.close();
    }
}
