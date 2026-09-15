package Proyecto.modelo.bbdd_mysql;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static final String USUARIO = "admin";
    private static final String CLAVE = "1234";

    public static Connection getConexion() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(URL, USUARIO, CLAVE);
        } catch (Exception e) {
        }
        return cn;
    }
}