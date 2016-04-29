package araragi.doctorsnotebook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 21.01.16.
 */
public class AddNewPatientActivity extends ActionBarActivity{


    private EditText editDate;
    private EditText editFamilyName;
    private EditText editName;
    private EditText editHistory;
    private EditText editDiagnosis;
    private EditText editAnaesthesia;
    private EditText editSpesial;
    private CheckBox checkPayment;



    DBAdapter patientDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_patient_with_scroll);


        editDate = (EditText) findViewById(R.id.editDate);
        editFamilyName = (EditText) findViewById(R.id.editFamilyName);
        editName = (EditText) findViewById(R.id.editName);
        editHistory = (EditText) findViewById(R.id.editHistory);
        editDiagnosis = (EditText) findViewById(R.id.editDiagnosis);
        editAnaesthesia = (EditText) findViewById(R.id.editAnaesthesia);
        editSpesial = (EditText) findViewById(R.id.editSpecialMark);
        checkPayment = (CheckBox) findViewById(R.id.checkBoxAddPatient);


    }
    public void toMainActivity(View v) {

        patientDataBase = new DBAdapter(this);
        patientDataBase.open();
        try{
            String date = editDate.getText().toString();
            String familyName = editFamilyName.getText().toString();
            String name = editName.getText().toString();
            int history = Integer.parseInt(editHistory.getText().toString());
            String diagnoz = editDiagnosis.getText().toString();
            String anest = editAnaesthesia.getText().toString();
            String spec = editSpesial.getText().toString();
            String pay = checkPaymentAdd(checkPayment);

            patientDataBase.insertRow(date, familyName, name, history, diagnoz, anest, spec, pay);

            Toast.makeText(this, "ADDED", Toast.LENGTH_SHORT).show();
            patientDataBase.close();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);


        }

        catch (Exception e){
            Toast.makeText(this, "Try again)", Toast.LENGTH_LONG).show();
        }



    }
    public String checkPaymentAdd(View v){
        boolean checked = ((CheckBox) v).isChecked();
        return checked?"da":"net";
    }


}
