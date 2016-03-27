package araragi.doctorsnotebook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by araragi on 21.01.16.
 */
public class DBAdapter {

    private static final String TAG = "DBAdapter";

    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;

    public static final String KEY_DATA = "date";
    public static final String KEY_FAMILY_NAME = "patients_family_name";
    public static final String KEY_NAME = "patients_name";
    public static final String KEY_HISTORY_NUMBER = "history_number";
    public static final String KEY_DIAGNOSIS = "diagnosis";
    public static final String KEY_ANAESTHESIA_TYPE = "anaesthesia";
    public static final String KEY_SPECIAL_MARK = "special_mark";
    public static final String KEY_PAYMENT = "payment";

    public static final int COL_DATA = 1;
    public static final int COL_FAMILY_NAME = 2;
    public static final int COL_NAME = 3;
    public static final int COL_HISTORY = 4;
    public static final int COL_DIAGNOSIS = 5;
    public static final int COL_ANAESTHESIA = 6;
    public static final int COL_SPESIAL_MARK = 7;
    public static final int COL_PAYMENT = 8;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID,KEY_DATA, KEY_FAMILY_NAME, KEY_NAME, KEY_HISTORY_NUMBER,
            KEY_DIAGNOSIS, KEY_ANAESTHESIA_TYPE, KEY_SPECIAL_MARK, KEY_PAYMENT};

    public static final String DATABASE_NAME = "patient_database.db";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_TABLE = "patients";

    private static final String DATABASE_CREATE_SCRIPT = "CREATE TABLE " + DATABASE_TABLE +
            " (" + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_DATA + " TEXT NOT NULL, " +
            KEY_FAMILY_NAME + " TEXT NOT NULL, " + KEY_NAME + " TEXT NOT NULL, " + KEY_HISTORY_NUMBER +
            " INTEGER, " + KEY_DIAGNOSIS + " TEXT NOT NULL, " + KEY_ANAESTHESIA_TYPE +
            " TEXT NOT NULL, " + KEY_SPECIAL_MARK + " TEXT NOT NULL, " + KEY_PAYMENT + " TEXT NOT NULL);";


    private final Context context;

    private DatabaseHelper dbhalper;
    private SQLiteDatabase database;
    private Cursor allRows;

    public DBAdapter(Context ctx){
        this.context = ctx;
        dbhalper = new DatabaseHelper(context);
    }

    public DBAdapter open(){
        database = dbhalper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbhalper.close();
    }



    public long insertRow(String date, String familyName, String name, int historyNumber, String diagnosis,
                          String anaesthesia, String spesialMark, String payment){
        ContentValues values = new ContentValues();
        values.put(KEY_DATA, date);
        values.put(KEY_FAMILY_NAME, familyName);
        values.put(KEY_NAME, name);
        values.put(KEY_HISTORY_NUMBER, historyNumber);
        values.put(KEY_DIAGNOSIS, diagnosis);
        values.put(KEY_ANAESTHESIA_TYPE, anaesthesia);
        values.put(KEY_SPECIAL_MARK, spesialMark);
        values.put(KEY_PAYMENT, payment);
        return database.insert(DATABASE_TABLE, null, values);
    }
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return database.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public Cursor getAllRows() {
        String where = null;
        Cursor c = database.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
        if(c != null){
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	database.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    public boolean updateRow(long rowId, String data, String familyName, String name, int historyNumber, String diagnosis,
                             String anaesthesia, String spesialMark, String payment) {
        String where = KEY_ROWID + "=" + rowId;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_DATA, data);
        newValues.put(KEY_FAMILY_NAME, familyName);
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_HISTORY_NUMBER, historyNumber);
        newValues.put(KEY_DIAGNOSIS, diagnosis);
        newValues.put(KEY_ANAESTHESIA_TYPE, anaesthesia);
        newValues.put(KEY_SPECIAL_MARK, spesialMark);
        newValues.put(KEY_PAYMENT, payment);

        return database.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    public Cursor getSearchResult(String key, String searchArgs) {
        Log.e(TAG, "---Get string to search---");
        String where = key + " = ?";
        String[] args = new String[]{searchArgs};
        Cursor c = 	database.query(true, DBAdapter.DATABASE_TABLE, ALL_KEYS,
                where, args, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            Log.e(TAG, "---Cursor not null---");
        }
        return c;
    }



    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SCRIPT);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion){
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which destroed all old data!");

            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            onCreate(_db);
        }


    }




}
