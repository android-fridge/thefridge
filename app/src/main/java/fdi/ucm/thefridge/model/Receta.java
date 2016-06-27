package fdi.ucm.thefridge.model;

import java.io.Serializable;

/**
 * Created by mizlap on 08/05/2016.
 */
public class Receta implements Serializable{

    String nombre;
    String dificultad;
    String duracion;
    String personas;
    String imagen;
    int _id;

    public Receta(int _id, String nombre, String dificultad, String duracion, String personas,String imagen) {
        this.nombre = nombre;
        this.dificultad = dificultad;
        this.duracion = duracion;
        this.personas = personas;
        this._id = _id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPersonas() {return personas;}
    public void setPersonas(String personas) {this.personas = personas;}
    public String getDificultad() {return dificultad;}
    public void setDificultad(String dificultad) {this.dificultad = dificultad;}
    public String getDuracion() {return duracion;}
    public void setDuracion(String duracion) {this.duracion = duracion;}
    public int get_id() {return _id;}
    public void set_id(int _id) {this._id = _id;}
    public String getImagen() {
        return imagen;
    }
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


}
