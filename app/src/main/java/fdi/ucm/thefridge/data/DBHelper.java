package fdi.ucm.thefridge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import fdi.ucm.thefridge.model.Usuario;

/**
 * Created by Carlos Casado González on 15/05/2016.
 */
public class DBHelper {

    public static final String DB_NAME="thefridgeBD";
    public static final String DB_TABLE="usuarios";
    public static final int DB_VERSION=3;
    private static final String[] COLS = new String[]{"id","password"};
    private SQLiteDatabase db;
    private final DBOpenHelper dbOpenHelper;


    public DBHelper (Context context){
        dbOpenHelper=new DBOpenHelper(context, "WR_DATA", 1);
        establishDb();
    }

    private void establishDb() {
        if (db==null){
            db=dbOpenHelper.getWritableDatabase();
        }
    }

    public void cleanup(){
        if (db != null){
            db.close();;
            db=null;
        }
    }

    public void insert (Usuario usr)throws SQLiteConstraintException {
        ContentValues values=new ContentValues();
        values.put("id", usr.getId());
        values.put("password", usr.getPassword());
        /*Lanza excepcion si hay error en insercion*/
        db.insertOrThrow(DBHelper.DB_TABLE,null,values);
        //db.insert(DBHelper.DB_TABLE,null,values);

    }

    public void update (Usuario usr){
        ContentValues values=new ContentValues();
        values.put("id", usr.getId());
        values.put("password", usr.getPassword());
        db.update(DBHelper.DB_TABLE,values,"id="+usr.getId(),null);
    }

    public void delete(String id){
        db.delete(DBHelper.DB_TABLE,"id="+id,null);
    }

    public Usuario get(String id){
        Cursor c=null;
        Usuario usr=null;
        try{
            c=db.query(true,DBHelper.DB_TABLE,DBHelper.COLS,"id= '"+id+"'",null,null,null,null,null);
            if(c.getCount()>0){
                c.moveToFirst();
                usr=new Usuario();
                usr.setId(c.getString(0));
                usr.setPassword(c.getString(1));
            }else
                return null;
        }catch(SQLException e){
            e.printStackTrace();
        }finally{
            if (c != null && !c.isClosed())
                c.close();
        }

        return usr;
    }

    public static class DBOpenHelper extends SQLiteOpenHelper{
        private static final String DB_CREATE = "CREATE TABLE " +
                DBHelper.DB_TABLE +
                " (id VARCHAR(30) PRIMARY KEY, password VARCHAR(20) NOT NULL);";

        public DBOpenHelper(Context context, String dbName, int version) {
            super(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION);
        }

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DBOpenHelper.DB_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBHelper.DB_TABLE);
            this.onCreate(db);
        }

    }



}
