package araragi.doctorsnotebook.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.database.DBAdapter;


public class MainActivity extends ActionBarActivity {

    DBAdapter patientDataBase;

    public static final String TAG_ALEX = "ALEX";
    public static final long SHOW_ALL = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }



    public void openDB() {
        patientDataBase = new DBAdapter(this);
        patientDataBase.open();

    }

    public void closeDB() {
        patientDataBase.close();
    }



    public void goToAddPatientActivity(View v){
        Intent intent = new Intent(this, AddNewPatientActivity.class);
        startActivity(intent);

    }
    public void goSearch(View v){

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);

    }
    public void goToList(View v){

        Intent intent = new Intent(this, PatientsListActivity.class);
        startActivity(intent);

    }

    public void goToDatabase(View v){

        Intent intent = new Intent(this, DatabaseActivity.class);
        startActivity(intent);

    }




}


