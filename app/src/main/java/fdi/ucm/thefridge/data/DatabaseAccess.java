package fdi.ucm.thefridge.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fdi.ucm.thefridge.model.Publicacion;
import fdi.ucm.thefridge.model.Receta;
import fdi.ucm.thefridge.model.Ingrediente;
import fdi.ucm.thefridge.model.SesionUsuario;
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
     * Devuelve todas las recetas de la base de datos.
     *
     * @return a List of quotes
     */
    public List<Receta> getRecetas() {
        List<Receta> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM recetas ORDER BY nombre", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String imagen = cursor.getString(5);
            Receta r = new Receta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)
                    ,cursor.getString(3),cursor.getString(4), imagen);
            list.add(r);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Devuelve las recetas que contienen los ingredientes de la nevera de un usuario.
     *
     * @return a List of quotes
     */
    public List<Receta> getRecetasNevera() {
        int id_user = SesionUsuario.getIdNum();
        List<Receta> list = new ArrayList<>();//
        Cursor cursor = database.rawQuery("SELECT DISTINCT r.* FROM recetas AS r" +
                ",receta_ingredientes AS ri WHERE r._id=ri.id_receta AND ri.id_ingrediente IN" +
                "(SELECT id_ingrediente FROM nevera WHERE id_usuario="+id_user+") ORDER BY r.nombre", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String imagen = cursor.getString(5);
            Receta r = new Receta(cursor.getInt(0),cursor.getString(1),cursor.getString(2)
                    ,cursor.getString(3),cursor.getString(4),imagen);
            list.add(r);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Devuelve todas los ingredientes de la base de datos.
     *
     * @return a List of ingredientes
     */
    public ArrayList<Ingrediente> getIngredientes() {
        ArrayList<Ingrediente> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM ingredientes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String categoria = nombre.substring(0,1).toUpperCase();
            String rareza = cursor.getString(3);
            Ingrediente ing = new Ingrediente(id, nombre, rareza, categoria);
            list.add(ing);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    /**
     * Devuelve todas los ingredientes de la base de datos.
     *
     * @return a List of ingredientes
     */
    public ArrayList<Ingrediente> getIngredientesNevera() {
        int id_user = SesionUsuario.getIdNum();
        ArrayList<Ingrediente> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT DISTINCT I.* FROM ingredientes AS i, nevera AS n " +
                "WHERE i._id=n.id_ingrediente AND n.id_usuario="+id_user, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String nombre = cursor.getString(1);
            String categoria = nombre.substring(0,1).toUpperCase();
            String rareza = cursor.getString(3);
            Ingrediente ing = new Ingrediente(id, nombre, rareza, categoria);
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
               // usr.setIdNum(c.getInt(1));
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
    public void insertPublicacion (Publicacion publicacion)throws SQLiteConstraintException {
        ContentValues values=new ContentValues();
        values.put("usuario", publicacion.getUsuario());
        values.put("mensaje", publicacion.getMensaje());
        values.put("img", publicacion.getImg());
        values.put("fecha", getDateTime());
        /*Lanza excepcion si hay error en insercion*/
        database.insertOrThrow("publicaciones",null,values);

    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void update (Usuario usr){
        ContentValues values=new ContentValues();
        values.put("nombre", usr.getId());
        values.put("password", usr.getPassword());
        database.update("usuarios",values,"nombre="+usr.getId(),null);
    }
    public ArrayList<Publicacion> getPublicaciones(int numFilas){
        ArrayList<Publicacion> list=new ArrayList<>();
        Cursor cursor=database.rawQuery("select usuario, mensaje, fecha, img from publicaciones order by fecha desc limit "+numFilas, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String usr = cursor.getString(0);
            String msj=cursor.getString(1);
            String fecha=cursor.getString(2);
            DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date= null;
            try {
                date = formatter.parse(fecha);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String img=cursor.getString(3);
            list.add(new Publicacion(0,usr,msj,date,img));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
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

    /**
     * insert en la nevera para conseguir los ingredientes en la nevera del usuario
     * @param user id del usuario
     * @param ingrediente id del ingrediente
     * @throws SQLiteConstraintException
     */
    public void insertNevera (int user, int ingrediente)throws SQLiteConstraintException {
        ContentValues values=new ContentValues();
        values.put("id_usuario", user);
        values.put("id_ingrediente", ingrediente);
        /*Lanza excepcion si hay error en insercion*/
        database.insertOrThrow("nevera",null,values);

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
