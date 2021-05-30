package com.mobdeve.ragasam.ragasa_ramos_mp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Safe.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "my_authority";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "authority_name";
    private static final String COLUMN_NUMBER = "authority_number";
    private static final String COLUMN_ISDEFAULT = "authority_isDefault";

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

/*db.insert(TABLE_NAME,null, );
        //add Police
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, "Police");
        cv.put(COLUMN_ISDEFAULT, false);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Log.d("SafetyApp", "FAILED ADDING POLICE");
        }else {
            Log.d("SafetyApp", "SUCCESS IN ADDING POLICE");
        }

        //add Police
        ContentValues cv2 = new ContentValues();
        cv2.put(COLUMN_NAME, "Fire");
        cv2.put(COLUMN_ISDEFAULT, false);
        long result2 = db.insert(TABLE_NAME,null, cv);
        if(result2 == -1){
            Log.d("SafetyApp", "FAILED ADDING FIRE");
        }else {
            Log.d("SafetyApp", "SUCCESS IN ADDING FIRE");
        }

        //add Hospital
        ContentValues cv3 = new ContentValues();
        cv3.put(COLUMN_NAME, "Fire");
        cv3.put(COLUMN_ISDEFAULT, false);
        long result3 = db.insert(TABLE_NAME,null, cv);
        if(result3 == -1){
            Log.d("SafetyApp", "FAILED ADDING HOSPITAL");
        }else {
            Log.d("SafetyApp", "SUCCESS IN ADDING HOSPITAL");
        }*/

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    long addAuthority(String name, String number, Boolean isDefault){
        Log.d("SafetyApp", "adding in DB..Number "+number);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_ISDEFAULT, isDefault);
        long result = db.insert(TABLE_NAME,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }

        return result;
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    void updateAuthorityNumber(String row_id, String number){
        Log.d("SafetyApp", "updating in DB..Number "+number);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NUMBER, number);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        Log.d("SafetyApp", "updating in DB DONE.. "+result);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void updateAuthorityDefault(String row_id, boolean isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ISDEFAULT, isDefault);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }


    boolean checkIfDataExists(String TableName, String column, String fieldValue) {
        Log.d("SafetyApp", "ENTERED CHECK DB");
        String checkQuery = "SELECT " + column + " FROM " + TableName + " WHERE " + column + "= '" +fieldValue+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery(checkQuery, null);
        Log.d("SafetyApp", "CHECK CURSOR IN DB "+cursor.getCount());
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}