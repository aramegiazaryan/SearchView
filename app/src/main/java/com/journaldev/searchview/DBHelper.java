package com.journaldev.searchview;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "MY.DB";
    private static final String TABLE_NAME = "animation";
    private static final int DB_VERSION = 1;
    public static final String ROWID = "_id";

    public DBHelper(Context context) {
        super(context.getApplicationContext(), DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MainActivity.ID + " TEXT, " +
                MainActivity.TITLE + " TEXT, " +
                MainActivity.TYPE + " TEXT, " +
                MainActivity.TAG + " TEXT, " +
                MainActivity.LINK_ICON + " TEXT, " +
                MainActivity.LINK_SOURCE + " TEXT, " +
                MainActivity.LINK_SOUND + " TEXT, " +
                MainActivity.PRICE + " TEXT, " +
                MainActivity.DATE + " TEXT, " +
                MainActivity.STATE_DOWNLOAD + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long insert(StoreModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MainActivity.ID, model.getId());
        cv.put(MainActivity.TITLE, model.getTitle());
        cv.put(MainActivity.TYPE, model.getType());
        cv.put(MainActivity.TAG, model.getTag());
        cv.put(MainActivity.LINK_ICON, model.getLinkIcon());
        cv.put(MainActivity.LINK_SOURCE, model.getLinkSource());
        cv.put(MainActivity.LINK_SOUND, model.getLinkSound());
        cv.put(MainActivity.PRICE, model.getPrice());
        cv.put(MainActivity.DATE, model.getDate());
        cv.put(MainActivity.STATE_DOWNLOAD, model.getStateDownload());
        long rowId = db.insert(TABLE_NAME, null, cv);
        db.close();
        return rowId;

    }

 /*   public int delete(String word) {
        SQLiteDatabase db = getWritableDatabase();
        int affectedRows = db.delete("animation", "name=?", new String[]{word});
       // String k="delete from dictionary where word=='"+word+"'";
       // db.execSQL("delete from dictionary where word=='"+word+"'");
        db.close();

        return affectedRows;
    }*/

    public int updateStateDownload(int id, int state) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MainActivity.STATE_DOWNLOAD, state);
        int affectedRows = db.update(TABLE_NAME, cv, MainActivity.ID + "=?", new String[]{Integer.toString(id)});
        db.close();
        return affectedRows;
    }

    //search dashtic vercnuma string u hamematuma tag dastum exac tvyalneri het u veradarznum e ham@nknox ardyunq@
    public Cursor  getAnimationListByKeyword(String search) {
        //Open connection to read only
        SQLiteDatabase db = getReadableDatabase();
        String selectQuery =  "SELECT  rowid as " +
                ROWID + "," +
                MainActivity.TITLE + "," +
                MainActivity.TYPE + "," +
                MainActivity.LINK_ICON + "," +
                MainActivity.PRICE + "," +
                MainActivity.STATE_DOWNLOAD +
                " FROM " + TABLE_NAME +
                " WHERE " +  MainActivity.TAG + "  LIKE  '%" + search + "%' "
                ;


        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor == null) {
            return null;
        } else if (!cursor.moveToFirst()) {
            cursor.close();
            return null;
        }
        return cursor;


    }


    //db-ic @st id-i talis e datan
    public Cursor query (String search) {
        SQLiteDatabase db = getWritableDatabase();
        /*Cursor c=db.query(
                TABLE_NAME *//* table *//*,
                new String[]{MainActivity.TITLE,MainActivity.TYPE,MainActivity.LINK_ICON,MainActivity.PRICE,MainActivity.STATE_DOWNLOAD} *//* columns *//*,
                search + " = ?" *//* where or selection *//*,
                //new String[]{search} *//* selectionArgs i.e. value to replace ? *//*,
                null,
                null *//* groupBy *//*,
                null *//* having *//*,
                null *//* orderBy *//*
        );*/
        Cursor c = db.query(TABLE_NAME,
                new String[]{MainActivity.TITLE,MainActivity.TYPE,MainActivity.LINK_ICON,MainActivity.PRICE,MainActivity.STATE_DOWNLOAD},
                search,// + " = ?",
                null,
               // new String[]{"Lion"},
                null,
                null,
                null,
                null);
        //    db.close();
        return  c;
    }
}
