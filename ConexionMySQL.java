import java.sql.*;

public class ConexionMySQL {
    //Atributos
    private String url;
    private String usuario;
    private String password;
    private Connection conexion;
    private Statement statement;
    //Constructor
    public ConexionMySQL(String url, String usuario, String password) {
        this.url = url;
        this.usuario = usuario;
        this.password = password;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion= DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //Metodos

}
