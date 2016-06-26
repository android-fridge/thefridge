package fdi.ucm.thefridge.model;

/**
 * Created by Carlos Casado González on 20/05/2016.
 */
public class SesionUsuario {
    private static String id;
    private static int idNum;
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

    public static int getIdNum() {
        return idNum;
    }

    public static void setId(String id) {

        SesionUsuario.id = id;
    }

    public static void setIdNum(int idNum) {
        SesionUsuario.idNum = idNum;
    }
}
