package araragi.doctorsnotebook.alltherest;

import android.database.Cursor;

import araragi.doctorsnotebook.database.DBAdapter;

/**
 * Created by araragi on 21.01.16.
 */
public class PatientAccount {

    public int id;//This is the id of SQL row
    public String date;
    public String familyName;
    public String name;
    public int historyNumber;
    public String diagnosis;
    public String anaesthesiaType;
    public String specialMark;
    public String payment;



    public PatientAccount(int id, String date, String familyName, String name, int historyNumber, String diagnosis,
                          String anaesthesiaType, String specialMark, String payment) {
        this.id = id;
        this.date = date;
        this.familyName = familyName;
        this.name = name;
        this.historyNumber = historyNumber;
        this.diagnosis = diagnosis;
        this.anaesthesiaType = anaesthesiaType;
        this.specialMark = specialMark;
        this.payment = payment;
    }
    public PatientAccount(Cursor c){
        this.id = c.getInt(DBAdapter.COL_ROWID);
        this.date = c.getString(DBAdapter.COL_DATA);
        this.familyName = c.getString(DBAdapter.COL_FAMILY_NAME);
        this.name = c.getString(DBAdapter.COL_NAME);
        this.historyNumber = c.getInt(DBAdapter.COL_HISTORY);
        this.diagnosis = c.getString(DBAdapter.COL_DIAGNOSIS);
        this.anaesthesiaType = c.getString(DBAdapter.COL_ANAESTHESIA);
        this.specialMark = c.getString(DBAdapter.COL_SPESIAL_MARK);
        this.payment = c.getString(DBAdapter.COL_PAYMENT);



    }


    public void setID(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHistoryNumber(int historyNumber) {
        this.historyNumber = historyNumber;
    }

    public int getHistoryNumber() {
        return historyNumber;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    public void setAnaesthesiaType(String anaesthesiaType){this.anaesthesiaType = anaesthesiaType;}
    public String getAnaesthesiaType(){return anaesthesiaType;}

    public void setSpecialMark(String specialMark){this.specialMark = specialMark;}
    public String getSpecialMark(){return specialMark;}

    public void setPayment(String payment){this.payment = payment;}
    public String getPayment(){return payment;}



}
