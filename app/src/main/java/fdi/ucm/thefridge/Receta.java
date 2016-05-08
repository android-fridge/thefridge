package fdi.ucm.thefridge;

/**
 * Created by mizlap on 08/05/2016.
 */
public class Receta {

    String nombre;
    String descripcion;
    int photoId;

    public Receta(String nombre, String descripcion, int photoId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.photoId = photoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

}
