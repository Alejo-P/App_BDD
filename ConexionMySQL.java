import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class ConexionMySQL {
    //Atributos
    private String url;
    private String usuario;
    private String password;
    private Connection conexion;
    private Statement statement;
    private String ID_query;
    private List<Map<String, Object>> consultas;
    //Constructor
    public ConexionMySQL() {
        consultas = new ArrayList<>();
        ID_query = "";
        conexion = null;
        statement = null;
    }
    //Metodos

    /**
     * Conexion a la base de datos MySQL.
     *
     * @param url Enlace de conexion al servidor de la base de datos MySQL
     * @param usuario Usuario para la conexion al servidor
     * @param password Contraseña de acceso al servidor
     * @return True si la conexión es exitosa, False en caso contrario
     * @throws SQLException Si ocurre un error al ejecutar la consulta.
     */
    public boolean Conexion(String url, String usuario, String password){

        this.url = url;
        this.usuario = usuario;
        this.password = password;
        boolean estado;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion= DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();
            estado = true;
        } catch (Exception e) {
            e.printStackTrace();
            estado = false;
        }
        return estado;
    }

    public String Consulta(String SQL_query) {
        boolean find = false;
        try {
            if (SQL_query.startsWith("SELECT") || SQL_query.startsWith("SHOW") || SQL_query.startsWith("DESCRIBE")) {
                ResultSet resultSet = statement.executeQuery(SQL_query);
                List<Object[]> info = new ArrayList<>();
                while (resultSet.next()) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultSet.getObject(i);
                    }
                    info.add(row);
                }

                for (Map<String, Object> consulta : consultas) {
                    if (info.equals(consulta.get("Consulta"))) {
                        ID_query = (String) consulta.get("ID");
                        find = true;
                        break;
                    }
                }

                if (!find) {
                    ID_query = RandomString();
                    Map<String, Object> nuevaConsulta = new HashMap<>();
                    nuevaConsulta.put("ID", ID_query);
                    nuevaConsulta.put("Consulta", info);
                    consultas.add(nuevaConsulta);
                }
                return ID_query;
            } else {
                throw new IllegalArgumentException("La sentencia " + SQL_query + " no es una sentencia de consulta válida");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    private String RandomString() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(5);
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        return sb.toString();
    }
}
