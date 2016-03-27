package araragi.doctorsnotebook.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.alltherest.ListAdapter;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 29.01.16.
 */
public class SearchActivity extends ActionBarActivity{

    DBAdapter patientDataBase;


    EditText editSearch;
    ListView listView;
    Spinner spinnerSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        patientDataBase = new DBAdapter(this);
        patientDataBase.open();

        spinnerSearch = (Spinner)findViewById(R.id.spinnerSearch);
        ArrayAdapter<?> adapterSpinner = ArrayAdapter.createFromResource(this, R.array.dropdown_list,
                android.R.layout.simple_spinner_item);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSearch.setAdapter(adapterSpinner);


        listView = (ListView)findViewById(R.id.listViewSearch);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Long x = parent.getItemIdAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), CorrectionActivity.class);
                intent.putExtra("id", x);

                startActivity(intent);

            }
        });


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        patientDataBase.close();
    }

    public void searchPatient(View v){

        editSearch = (EditText) findViewById(R.id.editSearch);
        String s = editSearch.getText().toString();
        String selected = spinnerSearch.getSelectedItem().toString();

        String key = "KEY_FAMILY_NAME";
        switch (selected){
            case "Family name":
                key = DBAdapter.KEY_FAMILY_NAME;
                break;
            case "Diagnosis":
                key = DBAdapter.KEY_DIAGNOSIS;
                break;
            case "Anaesthesia":
                key = DBAdapter.KEY_ANAESTHESIA_TYPE;
                break;
            case "Date":
                key = DBAdapter.KEY_DATA;
                break;
        }


        Cursor c = patientDataBase.getSearchResult(key, s);

        ListAdapter adapter = new ListAdapter(this, c, 0);
        listView.setAdapter(adapter);



    }


}
