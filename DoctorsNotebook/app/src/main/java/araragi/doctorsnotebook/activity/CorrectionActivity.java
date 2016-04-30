package araragi.doctorsnotebook.activity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.alltherest.PatientAccount;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 29.01.16.
 */
public class CorrectionActivity extends ActionBarActivity{

    DBAdapter patientDataBase;

    EditText editFamilyName;
    EditText editName;
    EditText editDate;
    EditText editHistoryNum;
    EditText editDiagnosis;
    EditText editAnest;
    EditText editSpecial;
    CheckBox checkPayCorrection;

    PatientAccount currentPatient;

    long currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correction_activity);

        patientDataBase = new DBAdapter(this);
        patientDataBase.open();

        Long idExtra = getIntent().getLongExtra("id", 0);
        currentId = idExtra;

        Cursor c = patientDataBase.getRow(idExtra);

        currentPatient = rowToPatientAccount(c);

        c.close();



        editDate = (EditText)findViewById(R.id.editDateCorrection);
        editDate.setText(currentPatient.getDate());

        editFamilyName = (EditText)findViewById(R.id.editFamilyNameCorrection);
        editFamilyName.setText(currentPatient.getFamilyName());

        editName = (EditText)findViewById(R.id.editNameCorrection);
        editName.setText(currentPatient.getName());

        editHistoryNum = (EditText)findViewById(R.id.editHistoryCorrection);
        editHistoryNum.setText(Integer.toString(currentPatient.getHistoryNumber()));

        editDiagnosis = (EditText)findViewById(R.id.editDiagnosisCorrection);
        editDiagnosis.setText(currentPatient.getDiagnosis());

        editAnest = (EditText)findViewById(R.id.editAnaesthesiaCorrection);
        editAnest.setText(currentPatient.getAnaesthesiaType());

        editSpecial = (EditText)findViewById(R.id.editSpecialMarkCorrection);
        editSpecial.setText(currentPatient.getSpecialMark());

        checkPayCorrection = (CheckBox)findViewById(R.id.checkBoxCorrection);
        if(currentPatient.getPayment().equals("da")) {

            checkPayCorrection.setChecked(true);
        }
        else{
            checkPayCorrection.setChecked(false);
        }




    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        patientDataBase.close();
    }

    public PatientAccount rowToPatientAccount(Cursor c){

        return new PatientAccount(c);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.correction_activity_menu, menu);

        return true;
    }

    public void correctRow(View v){
        try{
            long id = currentPatient.getId();
            String date = editDate.getText().toString();
            String family = editFamilyName.getText().toString();
            String name = editName.getText().toString();
            int history = Integer.parseInt(editHistoryNum.getText().toString());
            String diagnoz = editDiagnosis.getText().toString();
            String anest = editAnest.getText().toString();
            String spec = editSpecial.getText().toString();
            String pay = checkPaymentCorr(checkPayCorrection);


            patientDataBase.updateRow(id, date, family, name, history, diagnoz, anest, spec, pay);
            Toast.makeText(this, "UPDATED", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ListActivity.class);
            startActivity(intent);


        }

        catch (Exception e){
            Toast.makeText(this, "Try again)", Toast.LENGTH_LONG).show();
        }

    }
    public String checkPaymentCorr(View v){
        boolean checked = ((CheckBox) v).isChecked();
        return checked?"da":"net";
    }

    public void deleteRowDialog(MenuItem item) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CorrectionActivity.this);

        builder.setTitle("Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        patientDataBase.deleteRow(currentId);
                        Toast.makeText(CorrectionActivity.this, "Account " +
                            currentPatient.getFamilyName() + " deleted", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CorrectionActivity.this, PatientsListActivity.class);
                        startActivity(intent);
                    }
                })
                .setNeutralButton("Cancel", null)
                .create()
                .show();

    }

    public void goToMainMenu(MenuItem item){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
