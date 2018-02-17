package com.example.arfaatali.AndroidCallSilencerApp;
/**
 * Created by Arfaat Ali on 2/16/2017.
 */
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.graphics.Color;
        import android.support.v7.app.ActionBarActivity;
        import android.os.Bundle;
        import android.content.ContentResolver;
        import android.database.Cursor;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.RelativeLayout;
        import android.widget.SearchView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.graphics.Bitmap;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.provider.ContactsContract;
        import android.provider.MediaStore;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
public class MainActivity extends ActionBarActivity {
    ArrayList<SelectUser> selectUsers;
    ListView listView;
    Cursor phones;
    ContentResolver resolver;
    SearchView search;
    DatabaseHelper myDb;
    SelectUserAdapter adapter;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        RelativeLayout mealLayout = (RelativeLayout) findViewById(R.id.R_layout);
        mealLayout.setBackgroundColor(R.color.bisque3);
        selectUsers = new ArrayList<SelectUser>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list);
        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();
        myDb = new DatabaseHelper(this);
        search = (SearchView) findViewById(R.id.searchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });

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
                Intent intent = new Intent(MainActivity.this, BlockedListActivity.class);
                startActivity(intent);
                break;

            case  R.id.unknown:
                Intent intent2=new Intent(MainActivity.this,Unknown_NumberActivity.class);
                startActivity(intent2);
            default:
                break;
        }

        return true;
    }

    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
           

            if (phones != null) {
               
                if (phones.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {
                           
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    SelectUser selectUser = new SelectUser();
                    selectUser.setThumb(bit_thumb);
                    selectUser.setName(name);
                    selectUser.setPhone(phoneNumber);
                    selectUser.setEmail(id);

                    selectUsers.add(selectUser);
                   
                }
            } else {
                Log.e("Cursor closing ", "----------------");
            }
            phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new SelectUserAdapter(selectUsers, MainActivity.this);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.name);
                    TextView textView2 = (TextView) view.findViewById(R.id.no);
                    String name = textView.getText().toString();
                    String number = textView2.getText().toString();
                    Toast.makeText(getApplicationContext(), "Selected name: " + name, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Selected number: " + number, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, ContactInfoActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("number", number);
                    startActivity(intent);
                }
            });

            listView.setFastScrollEnabled(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        phones.close();
    }

}
