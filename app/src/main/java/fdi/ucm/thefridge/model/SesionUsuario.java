package fdi.ucm.thefridge.model;

/**
 * Created by Carlos Casado González on 20/05/2016.
 */
public class SesionUsuario {
    private static String id, password;
    private static boolean logueado;

    public static boolean isLogueado() {
        return logueado;
    }

    public static void setLogueado(boolean logueado) {
        SesionUsuario.logueado = logueado;
    }

    public static String getId() {
        return id;
    }

    public static String getPassword() {
        return password;
    }

    public static void setId(String id) {

        SesionUsuario.id = id;
    }

    public static void setPassword(String password) {
        SesionUsuario.password = password;
    }
}