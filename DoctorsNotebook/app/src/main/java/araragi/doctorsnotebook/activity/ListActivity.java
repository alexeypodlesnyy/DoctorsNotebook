package araragi.doctorsnotebook.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import araragi.doctorsnotebook.alltherest.ListAdapter;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 29.01.16.
 */
public class ListActivity extends android.app.ListActivity{

    DBAdapter patientDataBase;

    private static final String TAG_ALEX = "Alex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDB();
        long extra = getIntent().getLongExtra("show", 0);
        if(extra == 0) {
            Cursor c = patientDataBase.getAllRows();
            ListAdapter listAdapter = new ListAdapter(this, c, 0);
            setListAdapter(listAdapter);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id){

        super.onListItemClick(l, v, position, id);
        Long x = l.getItemIdAtPosition(position);
        Intent intent = new Intent(this, CorrectionActivity.class);
        intent.putExtra("id", x);

        startActivity(intent);


    }

    public void openDB() {
        patientDataBase = new DBAdapter(this);
        patientDataBase.open();

    }

    public void closeDB() {
        patientDataBase.close();
    }





}




