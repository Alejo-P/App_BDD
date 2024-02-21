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

    /**
     *Consulta a la base de datos
     *
     * @param SQL_query Consulta SQL para la base de datos,
     * tiene que ser (SELECT, SHOW, DESCRIBE)
     * @return ID de la consulta realizada
     * @throws IllegalArgumentException Si la sentencia no es una sentencia de consulta válida
     * */
    public String Consulta(String SQL_query) {
        boolean find = false;
        try {
            if (SQL_query.startsWith("SELECT") || SQL_query.startsWith("SHOW") || SQL_query.startsWith("DESCRIBE")) {
                ResultSet resultado = statement.executeQuery(SQL_query);
                List<Object[]> info = new ArrayList<>();
                while (resultado.next()) {
                    ResultSetMetaData metaData = resultado.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = resultado.getObject(i);
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

    /**
     * Modificar la estructura de la base de datos
     * @param SQL_query Sentencia SQL para interactuar con la base de datos,
     * tiene que ser DML(INSERT, UPDATE, DELETE) o DDL(ALTER, DROP, CREATE)
     * @return Número de filas afectadas por la interacción
     * @throws IllegalArgumentException Si la sentencia no pertenece a sentencia de modificación de información*/
    public int Modificar(String SQL_query) {
        boolean find = false;
        String[] sentenciasSQL = {"INSERT", "UPDATE", "DELETE", "ALTER", "DROP", "CREATE"};
        try {
            for (String sentencia : sentenciasSQL) {
                if (SQL_query.startsWith(sentencia)) {
                    int ejecucion = statement.executeUpdate(SQL_query);
                    conexion.commit();
                    return ejecucion;
                }
            }
            if (!find) {
                throw new IllegalArgumentException("La sentencia " + SQL_query + " no es una sentencia de modificación válida");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Obtener los datos de una consulta previa
     * @param ID_Consulta ID de la consulta (valor obtenido al usar el método Consulta())
     * @return Información de la consulta buscada
     **/
    public Object[][] Obtener_valores(String ID_Consulta) {
        boolean find = false;
        for (Map<String, Object> consulta : consultas) {
            if (consulta.get("ID").equals(ID_Consulta)) {
                find = true;
                List<Object[]> valor = (List<Object[]>) consulta.get("Consulta");
                Object[][] result = new Object[valor.size()][];
                for (int i = 0; i < valor.size(); i++) {
                    result[i] = valor.get(i);
                }
                return result;
            }
        }
        if (!find) {
            System.out.println("No existe una consulta con el ID " + ID_Consulta);
        }
        return null;
    }
    /**
     * Cerrar la conexión a la base de datos,
     * cierra la conexión actual del servidor de la base de datos*/
    public void Cerrar_conexion() {
        try {
            consultas.clear();
            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public int insertarRegustros(Registro registro) {
        try {
            PreparedStatement ps = conexion.prepareStatement("INSERT INTO usuarios (cedula, nombre, apellido, direccion, telefono, edad, curso, imagen) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, registro.getCedula());
            ps.setString(2, registro.getNombre());
            ps.setString(3, registro.getApellido());
            ps.setString(4, registro.getDireccion());
            ps.setString(5, registro.getTelefono());
            ps.setInt(6, registro.getEdad());
            ps.setString(7, registro.getCurso());
            ps.setBytes(8, registro.getImagen());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
