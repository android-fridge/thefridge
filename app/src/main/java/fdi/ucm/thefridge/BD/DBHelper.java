package fdi.ucm.thefridge.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

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

    public static final class Usuario{
        private String id, password;

        public Usuario(String id, String password){
            this.id=id;
            this.password=password;
        }
        public Usuario(){

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

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
        values.put("id", usr.id);
        values.put("password", usr.password);
        /*Lanza excepcion si hay error en insercion*/
        db.insertOrThrow(DBHelper.DB_TABLE,null,values);
        //db.insert(DBHelper.DB_TABLE,null,values);

    }

    public void update (Usuario usr){
        ContentValues values=new ContentValues();
        values.put("id", usr.id);
        values.put("password", usr.password);
        db.update(DBHelper.DB_TABLE,values,"id="+usr.id,null);
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
                usr.id=c.getString(0);
                usr.password=c.getString(1);
            }
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
