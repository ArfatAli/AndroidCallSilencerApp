package com.example.arfaatali.AndroidCallSilencerApp;

/**
 * Created by Arfaat Ali on 2/16/2017.
 */
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;



import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;




public class BlockedListActivity extends ActionBarActivity {
    ArrayList<SelectUser> selectUsers;
    ListView lv;
    File file = null;
    DatabaseHelper myDb;
    SelectUserAdapter adapter;
    String name,dd;
    Cursor cur;
    String nameSelected;
    String receiveDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R
                .layout.activity_blocked_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.getBackground().setAlpha(0);
        RelativeLayout mealLayout = (RelativeLayout) findViewById(R.id.R_layout2);
        mealLayout.setBackgroundColor(Color.LTGRAY);
        lv = (ListView) findViewById(R.id.listView);

        myDb = new DatabaseHelper(this);
        openAndQueryDatabase();
        Calendar c=new GregorianCalendar();
        c.add(Calendar.DATE, 30);
        Date d=c.getTime();
        dd=d.toString();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_item2, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.importbtn:
                importcsv();
                break;
            case R.id.exportbtn:
                exportcsv();
                break;
            case R.id.unknown:
                openUnknownActivity();
                break;
            default:
                break;
        }

        return true;
    }


    private void openAndQueryDatabase() {
        try {
            selectUsers = new ArrayList<SelectUser>();
            // DBHelper dbHelper = new DBHelper(this.getApplicationContext());
            // SQLiteDatabase newDB = dbHelper.getWritableDatabase();
            myDb = new DatabaseHelper(this.getApplicationContext());
            SQLiteDatabase newDB = myDb.getWritableDatabase();
            Cursor c = newDB.rawQuery("SELECT name,data FROM myTable ORDER BY name,data ASC", null);

            while (c.moveToNext()) {

                name = c.getString(c.getColumnIndex("name"));
                String num = c.getString(c.getColumnIndex("data"));
                SelectUser selectUser = new SelectUser();
                selectUser.setName(name);
                selectUser.setPhone(num);
                selectUsers.add(selectUser);
                Toast.makeText(BlockedListActivity.this, "" + name, Toast.LENGTH_SHORT).show();
            }


            adapter = new SelectUserAdapter(selectUsers, BlockedListActivity.this);
            lv.setAdapter(adapter);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long rowId) {

                    TextView textView = (TextView) view.findViewById(R.id.name);
                    nameSelected=textView.getText().toString();

                    AlertDialog.Builder builder = new AlertDialog.Builder(BlockedListActivity.this);

                    builder.setTitle("Confirm");
                    builder.setMessage("Are you sure to Delete");

                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // Do nothing but close the dialog
                            dialog.dismiss();
                            Integer deleteRows=myDb.deletedata(nameSelected);
                            if (deleteRows>0){
                                Toast.makeText(BlockedListActivity.this,"Data Is Deleted",Toast.LENGTH_SHORT).show();
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                            else {

                                Toast.makeText(BlockedListActivity.this,"Data Is Not Deleted",Toast.LENGTH_SHORT).show();

                            }

                        }
                    });

                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // Do nothing
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                    myDb.close();

                }

            });
        } catch (SQLiteException se) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        } finally {

        }

    }


    public void exportcsv() {

        ExportDatabaseCSVTask task = new ExportDatabaseCSVTask();
        task.execute();

    }

    public void importcsv() {

        File exportDir = new File(Environment.getExternalStorageDirectory(), "");
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        file = new File(exportDir, "myfile.csv");
        try {
            CSVReader reader = new CSVReader(new FileReader(file));
            String[] nextLine;
            int lineread = 0;
            while ((nextLine = reader.readNext()) != null) {
                lineread++;
                if (lineread == 1) {

                    continue;

                } else {
                    String name = nextLine[1].toString();
                    String number = nextLine[2].toString();
                    SimpleDateFormat df = new SimpleDateFormat("EEE,d MMM yyyy,HH:mm:ss");
                    Date today = (Date) Calendar.getInstance().getTime();
                    receiveDate = df.format(today);
                    boolean value = myDb.insertData(name,receiveDate, number);
                    if (value == true) {
                        Toast.makeText(getApplicationContext(), "Data inerted into table", Toast.LENGTH_LONG).show();
                    } else {

                        //Toast.makeText(getApplicationContext(), "Data is inserted when you uninstall this app and reinstall it again", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "Data is not inerted into table", Toast.LENGTH_LONG).show();

                    }
                }
            }
        } catch (IOException ie) {
            ie.printStackTrace();
        }

    }

    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(BlockedListActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
        }

        protected Boolean doInBackground(final String... args) {
            File dbFile = getDatabasePath("first1.DB");
            System.out.println(dbFile);  // it displays me the data base path in your logcat
            File exportDir = new File(Environment.getExternalStorageDirectory(), "");

            if (!exportDir.exists()) {
                exportDir.mkdirs();
            }

            File file = new File(exportDir, "myfile.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                SQLiteDatabase newDB = myDb.getWritableDatabase();
                Cursor curCSV = newDB.rawQuery("select * from myTable", null);
                csvWrite.writeNext(curCSV.getColumnNames());
                while (curCSV.moveToNext()) {
                    String arrStr[] = {curCSV.getString(0), curCSV.getString(1), curCSV.getString(2)};
                    // curCSV.getString(3),curCSV.getString(4)};
                    csvWrite.writeNext(arrStr);
                }
                csvWrite.close();
                curCSV.close();
                return true;
            } catch (SQLException sqlEx) {
                Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
                return false;
            } catch (IOException e) {
                Log.e("MainActivity", e.getMessage(), e);
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                Toast.makeText(BlockedListActivity.this, "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BlockedListActivity.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void openUnknownActivity(){

        Intent intent=new Intent(BlockedListActivity.this,Unknown_NumberActivity.class);
        startActivity(intent);
    }
}