import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana_Principal {
    private JTabbedPane tabbedPane1;
    private JTextField Consulta_cedula;
    private JButton consultarButton;
    private JTable Tabla_informacion;
    private JTextField Insercion_cedula;
    private JTextField Insercion_nombre;
    private JTextField Insercion_apellido;
    private JTextField Insercion_direccion;
    private JTextField Insercion_telefono;
    private JTextField Insercion_edad;
    private JComboBox Insercion_combobox;
    private JButton subirImagenButton;
    private JTextField Eliminacion_cedula;
    private JButton eliminarButton;
    JPanel Ventana_principal;
    private boolean conn;
    private ConexionMySQL conexionMySQL;

    public Ventana_Principal() {
        conexionMySQL = new ConexionMySQL();
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                conn=conexionMySQL.Conexion("jdbc:mysql://uv8e5bhcj06tdvic:ME3Yq8U3Cax9OIjbHqdq@bfgwcnxsobb1g6asgq4d-mysql.services.clever-cloud.com:3306/bfgwcnxsobb1g6asgq4d", "uv8e5bhcj06tdvic", "ME3Yq8U3Cax9OIjbHqdq");
                if (conn){
                    System.out.println("Conexion establecida");
                }
                else{
                    System.out.println("Conexion no establecida");
                }
            }
        });
    }
}
