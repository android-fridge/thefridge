package fdi.ucm.thefridge.model;


import java.util.Comparator;

/**
 * Created by Michael Tome Rodriguez on 02/05/2016.
 * Clase de los ingredientes
 */
public class Ingrediente implements Comparator<Ingrediente> {
    private String nombre;
    private int img; //Las referencias a las imagenes se devuelven como enteros

    public Ingrediente() {
    }
    public Ingrediente(String nombre, int img){
        this.nombre = nombre;
        this.img = img;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
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


}
