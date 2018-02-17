package com.example.arfaatali.AndroidCallSilencerApp;

/**
 * Created by Arfaat Ali on 2/16/2017.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ContactInfoActivity extends ActionBarActivity {

    EditText textname,textnumber;
    String nameContact,numberContact;
    Button btnYes,btnNo;

    DatabaseHelper myDb;
    String finalnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        RelativeLayout mealLayout = (RelativeLayout) findViewById(R.id.RelativeLayout4);
        mealLayout.setBackgroundColor(Color.LTGRAY);

        textname=(EditText)findViewById(R.id.EditTextNmae);
        textnumber=(EditText)findViewById(R.id.EditTextNo);
        btnYes=(Button)findViewById(R.id.Yes);
        btnNo=(Button)findViewById(R.id.No);

        myDb=new DatabaseHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nameContact = extras.getString("name");
            numberContact=extras.getString("number");
            if (numberContact.contains("+92")){

                textname.setText(nameContact);
                textnumber.setText(numberContact);

            }
            else if(!numberContact.contains("+92")){
                StringBuilder sb=new StringBuilder(numberContact);
                sb.delete(0,1);
                finalnumber = sb.insert(0, "+92").toString();
                textname.setText(nameContact);
                textnumber.setText(finalnumber);
            }


            // and get whatever type user account id is
        }




        noButtonPressed();
        yesButtonPressed();


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
            // action with ID action_refresh was selected
            case R.id.ListButton:
                Intent intent = new Intent(ContactInfoActivity.this, BlockedListActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        return true;
    }
    public void noButtonPressed(){
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
    public void yesButtonPressed(){
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddData();

            }
        });

    }
    public void AddData(){

        // Toast.makeText(MainActivity.this,""+path,Toast.LENGTH_SHORT).show();
        //Toast.makeText(MainActivity.this,""+pic,Toast.LENGTH_LONG).show();
        String num;
        String name;
        num=textnumber.getText().toString();
        name=textname.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("EEE,d MMM yyyy,HH:mm:ss");
        Date today = (Date) Calendar.getInstance().getTime();
        String receiveDate = df.format(today);
        boolean inserted = myDb.insertData(name,receiveDate, num);

        if (inserted == true) {
            Toast.makeText(ContactInfoActivity.this, "Data Is Saved", Toast.LENGTH_LONG).show();
        } else {

            Toast.makeText(ContactInfoActivity.this, "Data is not inserted", Toast.LENGTH_LONG).show();
        }
        myDb.close();
    }

}
