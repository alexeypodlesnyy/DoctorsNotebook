package araragi.doctorsnotebook.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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


    public void deleteAllAlertDialog(View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("WARNING!!!")
                .setMessage("Are you shure you whant to destroy all data!?")
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "Good boy", Toast.LENGTH_LONG).show();
                    }
                }).
                setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        patientDataBase.deleteAll();
                        Toast.makeText(MainActivity.this, "DELETED!!!", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();


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

        Intent intent = new Intent(this, ListActivity.class);
        intent.putExtra("show", SHOW_ALL);
        startActivity(intent);

    }




}


