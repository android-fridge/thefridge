package fdi.ucm.thefridge.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by Michael Tome Rodriguez on 02/05/2016.
 * Clase de los ingredientes
 */
public class Ingrediente implements Comparator<Ingrediente>, Parcelable {
    private String nombre;
    private String rareza;
    private int img; //Las referencias a las imagenes se devuelven como enteros

    public Ingrediente() {
    }
    public Ingrediente(String nombre, String rareza, int img){
        this.nombre = nombre;
        this.rareza = rareza;
        this.img = img;
    }

    public Ingrediente(Parcel in) {
        this.nombre = in.readString();
        this.rareza = in.readString();
        this.img = in.readInt();
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

    public int getImg(){
        return img;
    }

    public void setImg(int img){
        this.img = img;
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
        dest.writeString(nombre);
        dest.writeString(rareza);
        dest.writeInt(img);
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
