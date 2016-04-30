package araragi.doctorsnotebook.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import araragi.doctorsnotebook.R;
import araragi.doctorsnotebook.alltherest.PatientAccount;
import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 27.04.16.
 */
public class DatabaseActivity extends ActionBarActivity {

    DBAdapter patientDataBase;
    TextView dataBaseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);
        openDB();

    }

    @Override
    protected void onDestroy() {

        closeDB();
        super.onDestroy();

    }


    private void openDB() {
        patientDataBase = new DBAdapter(this);
        patientDataBase.open();

    }

    private void closeDB() {
        patientDataBase.close();
    }


    public void deleteAllAlertDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DatabaseActivity.this);
        builder.setTitle("WARNING!!!")
                .setMessage("Are you sure you want to destroy all data!?")
                .setCancelable(true)
                .setNeutralButton("Cancel", null)
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        patientDataBase.deleteAll();
                        Toast.makeText(DatabaseActivity.this, "DELETED!!!", Toast.LENGTH_LONG).show();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();


    }

    public void writeDbToTextFile(View view) {

        Cursor cursor = patientDataBase.getAllRows();

        File dataBaseSd = null;
        String sdState = android.os.Environment.getExternalStorageState();




        if (sdState.equals(Environment.MEDIA_MOUNTED)) {
            File sdDirectory = android.os.Environment.getExternalStorageDirectory();
            dataBaseSd = new File(sdDirectory, "baza.txt");

        } else {
            dataBaseSd = getApplicationContext().getCacheDir();

        }

        if (!dataBaseSd.exists()) {
            dataBaseSd.isDirectory();

        }

        try {

            FileWriter fw = new FileWriter(dataBaseSd);

            do {PatientAccount patient = new PatientAccount(cursor);

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append(patient.getDate() + " ");
                stringBuilder.append(patient.getFamilyName() + " ");
                stringBuilder.append(patient.getName() + " ");
                stringBuilder.append(patient.getDiagnosis() + " ");
                stringBuilder.append(patient.getAnaesthesiaType() + " ");
                stringBuilder.append(patient.getSpecialMark() + " ");
                stringBuilder.append(patient.getPayment() + "\n" + "---" + "\n");
                String s = stringBuilder.toString();
                fw.write(s);

            }while (cursor.moveToNext());

            fw.flush();
            fw.close();

        } catch (Exception e) {
            Toast.makeText(DatabaseActivity.this, "Writing to file failed", Toast.LENGTH_LONG).show();
        }


    }

    public void readFromTextFile(View v){

        StringBuilder builder = new StringBuilder();

        File sdDirectory = android.os.Environment.getExternalStorageDirectory();
        String path = sdDirectory.getAbsolutePath();

        try{
            FileInputStream fis = new FileInputStream(new File(path + "/baza.txt"));
            if(fis!=null){
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String line;

                while ((line = br.readLine())!=null){
                        builder.append(line + "\n");
                }
                fis.close();
            }
        }catch (Exception e){
            String s = e.toString();
            Toast.makeText(this, s, Toast.LENGTH_LONG).show();
            Log.e("alex", s);

        }
        dataBaseText=(TextView) findViewById(R.id.text_database);
        dataBaseText.setText(builder.toString());


    }

}