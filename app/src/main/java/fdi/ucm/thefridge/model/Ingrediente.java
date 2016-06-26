package fdi.ucm.thefridge.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by Michael Tome Rodriguez on 02/05/2016.
 * Clase de los ingredientes
 */
public class Ingrediente implements Comparator<Ingrediente>, Parcelable {
    private int _id;
    private String nombre;
    private String rareza;
    private String categoria;

    public Ingrediente() {
    }
    public Ingrediente(int _id, String nombre, String rareza, String categoria){
        this._id = _id;
        this.nombre = nombre;
        this.rareza = rareza;
        this.categoria = categoria;
    }

    public Ingrediente(Parcel in) {
        this._id = in.readInt();
        this.nombre = in.readString();
        this.rareza = in.readString();
        this.categoria = in.readString();
    }

    public int getId(){
        return _id;
    }

    public void setId(int _id){
        this._id = _id;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getRareza(){
        return rareza;
    }

    public void setRareza(String rareza){
        this.rareza = rareza;
    }

    public String getcategoria(){
        return categoria;
    }

    public void setcategoria(String categoria){
        this.categoria = categoria;
    }

    @Override
    public int compare(Ingrediente lhs, Ingrediente rhs) {
        return lhs.getNombre().compareTo(rhs.getNombre());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(_id);
        dest.writeString(nombre);
        dest.writeString(rareza);
        dest.writeString(categoria);
    }

    public static final Parcelable.Creator<Ingrediente> CREATOR = new Parcelable.Creator<Ingrediente>() {

        @Override
        public Ingrediente[] newArray(int size) {
            return new Ingrediente[size];
        }

        @Override
        public Ingrediente createFromParcel(Parcel source) {
            return new Ingrediente(source);
        }
    };
}
