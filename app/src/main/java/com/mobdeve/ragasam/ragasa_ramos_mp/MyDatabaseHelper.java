package com.mobdeve.ragasam.ragasa_ramos_mp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static MyDatabaseHelper mDatabaseInstance = null;
    private Context context;

    private static final String DATABASE_NAME = "Safe.db";
    private static final int DATABASE_VERSION = 1;

    private static final String COLUMN_ID = "_id";
    // Authority Table
    private static final String TABLE_NAME = "my_authority";
    private static final String COLUMN_NAME = "authority_name";
    private static final String COLUMN_NUMBER = "authority_number";
    private static final String COLUMN_ISDEFAULT = "authority_isDefault";

    // Contact Table
    private static final String TABLE_NAME2 = "my_contacts";
    private static final String COLUMN_PERSON = "contact_name";
    private static final String COLUMN_NUMBER2 = "contact_number";
    private static final String COLUMN_MESSAGE = "contact_message";
    private static final String COLUMN_LOCATION = "contact_location";

    public static MyDatabaseHelper newInstance(Context context){
        if (mDatabaseInstance == null){
            mDatabaseInstance = new MyDatabaseHelper(context.getApplicationContext());
        }
        return mDatabaseInstance;
    }

     MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_ISDEFAULT + " BOOLEAN);";
        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_NAME2 +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PERSON + " TEXT, " +
                COLUMN_NUMBER2 + " TEXT, " +
                COLUMN_MESSAGE + " TEXT, " +
                COLUMN_LOCATION + " BOOLEAN);";
        db.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    // For Authority Table
    public long addAuthority(String name, String number, Boolean isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_ISDEFAULT, isDefault);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result != -1) {
            Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateAuthorityNumber(String row_id, String number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NUMBER, number);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result != -1) {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }

    }

    public void updateAuthorityDefault(String row_id, boolean isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ISDEFAULT, isDefault);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result != -1) {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean checkIfDataExists(String TableName, String column, String fieldValue) {
        String checkQuery = "SELECT " + column + " FROM " + TableName + " WHERE " + column + "= '" +fieldValue+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(checkQuery, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    public void updateRecords(String row_id, boolean isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ISDEFAULT, isDefault);
        long result = db.update(TABLE_NAME, cv, "_id!=?", new String[]{row_id});
    }

    public void updateAllRecords(boolean isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ISDEFAULT, isDefault);
        long result = db.update(TABLE_NAME, cv, null, null);
    }

    public void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    // For Contact Table
    public long addContact(String name, String number, String message, boolean shareLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PERSON, name);
        cv.put(COLUMN_NUMBER2, number);
        cv.put(COLUMN_MESSAGE, message);
        cv.put(COLUMN_LOCATION, shareLocation);
        long result = db.insert(TABLE_NAME2,null, cv);
        if(result != -1) {
            Toast.makeText(context, "Successfully Added!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    public Cursor readAllContacts(){
        String query = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void updateContact(String row_id, String name, String number, String message, boolean shareLocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PERSON, name);
        cv.put(COLUMN_NUMBER2, number);
        cv.put(COLUMN_MESSAGE, message);
        cv.put(COLUMN_LOCATION, shareLocation);
        long result = db.update(TABLE_NAME2, cv, "_id=?", new String[]{row_id});
        if(result != -1) {
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteOneContact(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }



}