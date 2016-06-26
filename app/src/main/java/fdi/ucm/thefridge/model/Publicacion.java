package fdi.ucm.thefridge.model;

import java.util.Date;

/**
 * Created by Carlos Casado González on 25/06/2016.
 */
public class Publicacion {
    private int _id;
    private String usuario;
    private String mensaje;
    private Date fecha;
    private String img;

    public Publicacion(int _id, String usuario, String mensaje, Date fecha, String img) {
        this._id = _id;
        this.usuario = usuario;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.img = img;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
