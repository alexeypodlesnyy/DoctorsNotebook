package araragi.doctorsnotebook.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.alltherest.ListAdapter;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 12.04.16.
 */
public class PatientsListActivity extends ActionBarActivity {

    DBAdapter patientDataBase;

    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patients_list_activity);

        patientDataBase = new DBAdapter(this);
        patientDataBase.open();


        listView = (ListView)findViewById(R.id.patientsList);

        Cursor c = patientDataBase.getAllRows();
        ListAdapter adapter = new ListAdapter(this, c, 0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Long x = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), CorrectionActivity.class);
                intent.putExtra("id", x);

                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Long x = id;
                boolean d = patientDataBase.deleteRow(x);
                Toast.makeText(PatientsListActivity.this, "Row was deleted.id = "+x.toString(),
                        Toast.LENGTH_LONG).show();

                return true;
            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        patientDataBase.close();
    }


}
