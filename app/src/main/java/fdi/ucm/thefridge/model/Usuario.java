package fdi.ucm.thefridge.model;

/**
 * Created by Carlos Casado González on 19/05/2016.
 */
public class Usuario {
    private String id, password;
    private int _id;


    public Usuario(String id, String password, int _id){
        this.id=id;
        this.password=password;
        this._id=_id;
    }
    public Usuario(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIdNum() {
        return _id;
    }

    public void setIdNum(int _id) {
        this._id = _id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
