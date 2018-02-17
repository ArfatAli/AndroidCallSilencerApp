package com.example.arfaatali.AndroidCallSilencerApp;
/**
 * Created by Arfaat Ali on 2/16/2017.
 */

        import android.media.AudioManager;
        import android.telecom.Call;
        import android.telephony.PhoneStateListener;
        import android.telephony.TelephonyManager;
        import android.util.Log;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteException;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;



public class CallService  extends BroadcastReceiver {
    Context mContext;
    String sendr="";
    Cursor cur;

    List<String> array;

    Cursor c;

    String one="";
    String two="";
    String three="";
    String four="";
    String five="";
    String six="";
    String seven="";
    String eight="";
    String nine="";
    String ten="";
    String onetrim,twotrim,threetrim,fourtrim,fivetrim,sixtrim,seventrim,eighttrim,ninetrim,tentrim;

    DatabaseHelper myDb;



    @Override
    public void onReceive(Context mContext, Intent intent)
    {


        try
        {

            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
            {
                String sendr = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                String str = "";


                openAndQueryDatabase();
                if (array.size()==0){
                    Toast.makeText(mContext,"No number in list",Toast.LENGTH_SHORT).show();
                }
                else if (array.size()==1){
                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                }
                else if (array.size()==2){
                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                }else if (array.size()==3){
                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                }else if (array.size()==4){
                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+","");
                }else if (array.size()==5){
                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+","");
                    five = array.get(4);
                    fivetrim =five.replaceAll("\\s+","");
                }else if (array.size()==6) {

                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+","");
                    five = array.get(4);
                    fivetrim =five.replaceAll("\\s+","");
                    six=array.get(5);
                    sixtrim=six.replaceAll("\\s+","");

                }else  if (array.size()==7){
                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+","");
                    five = array.get(4);
                    fivetrim =five.replaceAll("\\s+","");
                    six=array.get(5);
                    sixtrim=six.replaceAll("\\s+","");
                    seven=array.get(7);
                    seventrim=seven.replaceAll("\\s+","");


                }else if (array.size()==8){

                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+","");
                    five = array.get(4);
                    fivetrim =five.replaceAll("\\s+","");
                    six=array.get(5);
                    sixtrim=six.replaceAll("\\s+","");
                    seven=array.get(7);
                    seventrim=seven.replaceAll("\\s+","");
                    eight=array.get(8);
                    eighttrim=eight.replaceAll("\\s+","");
                }else if (array.size()==9){

                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+","");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+","");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+","");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+","");
                    five = array.get(4);
                    fivetrim =five.replaceAll("\\s+","");
                    six=array.get(5);
                    sixtrim=six.replaceAll("\\s+","");
                    seven=array.get(6);
                    seventrim=seven.replaceAll("\\s+","");
                    eight=array.get(7);
                    eighttrim=eight.replaceAll("\\s+","");
                    nine=array.get(8);
                    ninetrim=nine.replaceAll("\\s+","");



                }else if (array.size()==10){

                    one = array.get(0);
                    onetrim =one.replaceAll("\\s+", "");
                    two = array.get(1);
                    twotrim =two.replaceAll("\\s+", "");
                    three = array.get(2);
                    threetrim =three.replaceAll("\\s+", "");
                    four = array.get(3);
                    fourtrim =four.replaceAll("\\s+", "");
                    five = array.get(4);
                    fivetrim =five.replaceAll("\\s+", "");
                    six=array.get(5);
                    sixtrim=six.replaceAll("\\s+", "");
                    seven=array.get(6);
                    seventrim=seven.replaceAll("\\s+", "");
                    eight=array.get(7);
                    eighttrim=eight.replaceAll("\\s+", "");
                    nine=array.get(8);
                    ninetrim=nine.replaceAll("\\s+", "");
                    ten=array.get(9);
                    tentrim=ten.replaceAll("\\s+", "");
                }

                else {
                    Toast.makeText(mContext,"No number in list",Toast.LENGTH_SHORT).show();

                }
                if ( sendr.equals(onetrim) || sendr.equals(fourtrim) || sendr.equals(twotrim) || sendr.equals(threetrim) || sendr.equals(fivetrim)|| sendr.equals(sixtrim)|| sendr.equals(seventrim)|| sendr.equals(eighttrim)|| sendr.equals(ninetrim)|| sendr.equals(tentrim)) {
                    Toast.makeText(mContext, "" + str, Toast.LENGTH_SHORT).show();
                    //abortBroadcast();
                    //state.equals(TelephonyManager.EXTRA_STATE_IDLE);
          final AudioManager mode = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
          mode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                  }
                else {
                    Toast.makeText(mContext,"this number is not equal to the Blocked List",Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(mContext, "Phone Is Ringing", Toast.LENGTH_LONG).show();


            }// end of phone ringing

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))
            {
                Toast.makeText(mContext, "Call Recieved", Toast.LENGTH_LONG).show();
                // Your Code
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE))
            {

                Toast.makeText(mContext, "Phone Is Idle", Toast.LENGTH_LONG).show();
                // Your Code

            }
        }
        catch(Exception e)
        {
            //your custom message
        }

    }





    private void openAndQueryDatabase() {
        try {
            array = new ArrayList<String>();

            SQLiteDatabase newDB=myDb.getWritableDatabase();
            cur = newDB.rawQuery("SELECT name,data FROM myTable ORDER BY name,data ASC", null);

            while(cur.moveToNext()){
                String num = cur.getString(cur.getColumnIndex("data"));
                array.add(num);

            }


        } catch (SQLiteException se ) {
            //Log.e(getClass().getSimpleName(), "Could not create or Open the database");
            Toast.makeText(mContext, "Could not create or Open the database", Toast.LENGTH_LONG).show();
        }
        finally {
            cur.close();
        }
        myDb.close();
    }




}





