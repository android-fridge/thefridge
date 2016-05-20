package fdi.ucm.thefridge.model;

/**
 * Created by Carlos Casado González on 19/05/2016.
 */
public class Usuario {
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
