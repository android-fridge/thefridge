package fdi.ucm.thefridge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;
import fdi.ucm.thefridge.model.Receta;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.Usuario;

/**
 * Created by mizadri on 23/06/2016.
 */
public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all recetas from the database.
     *
     * @return a List of quotes
     */
    public List<Receta> getRecetas() {
        List<Receta> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM recetas", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Receta r = new Receta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)
                    ,cursor.getString(3),cursor.getString(4));
            list.add(r);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Read all recetas from the database.
     *
     * @return a List of ingredientes
     */
    public ArrayList<Ingrediente> getIngredientes() {
        ArrayList<Ingrediente> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM ingredientes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String nombre = cursor.getString(1);
            String categoria = cursor.getString(2);
            String rareza = cursor.getString(3).substring(0,1).toUpperCase();
            Ingrediente ing = new Ingrediente(nombre, rareza, categoria);
            list.add(ing);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public Usuario get(String id){
        Cursor c=null;
        Usuario usr=null;
        try{
            c=database.query(true,"usuarios",new String[]{"nombre","password"},"nombre= '"+id+"'",null,null,null,null,null);
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

    public void insert (Usuario usr)throws SQLiteConstraintException {
        ContentValues values=new ContentValues();
        values.put("nombre", usr.getId());
        values.put("password", usr.getPassword());
        /*Lanza excepcion si hay error en insercion*/
        database.insertOrThrow("usuarios",null,values);

    }

    public void update (Usuario usr){
        ContentValues values=new ContentValues();
        values.put("nombre", usr.getId());
        values.put("password", usr.getPassword());
        database.update("usuarios",values,"nombre="+usr.getId(),null);
    }

    public ArrayList<String> GetlistIngredientesReceta(int id) {
        ArrayList<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT descripcion FROM receta_ingredientes WHERE id_receta="+
                id+" AND id_ingrediente IS NULL", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String desc = cursor.getString(0);
            list.add(desc);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public String[] getPasosReceta(int id) {
        Cursor cursor = database.rawQuery("SELECT descripcion FROM receta_pasos WHERE id_receta="+id, null);
        int i = 0;
        String[] list = new String[cursor.getCount()];
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String desc = cursor.getString(0);
            list[i] = desc;
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
