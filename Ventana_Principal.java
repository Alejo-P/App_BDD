import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JButton insertarOActualizarRegistroButton;
    private JScrollPane Tabla_scroll;
    private boolean conn;
    private ConexionMySQL conexionMySQL;

    public Ventana_Principal() {
        conexionMySQL = new ConexionMySQL();
        String[] Columnas = {"Cedula", "Nombre", "Apellido", "Fecha Matricula", "Periodo", "Direccion", "Telefono", "Edad", "Curso", "Imagen"};
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                conn=conexionMySQL.Conexion("jdbc:mysql://uv8e5bhcj06tdvic:ME3Yq8U3Cax9OIjbHqdq@bfgwcnxsobb1g6asgq4d-mysql.services.clever-cloud.com:3306/bfgwcnxsobb1g6asgq4d", "uv8e5bhcj06tdvic", "ME3Yq8U3Cax9OIjbHqdq");
                if (conn){
                    String cedula=Consulta_cedula.getText();
                    String ID;
                    if (!cedula.isEmpty()){
                        ID=conexionMySQL.Consulta("SELECT * FROM usuarios WHERE cedula=%s".formatted(cedula));
                    }
                    else{
                        ID=conexionMySQL.Consulta("SELECT * FROM usuarios");
                    }
                    // Obtener los datos de la consulta
                    Object[][] Valores_consulta = conexionMySQL.Obtener_valores(ID);

                    // Crear un DefaultTableModel con los datos y los nombres de las columnas
                    DefaultTableModel model = new DefaultTableModel(Valores_consulta, Columnas);

                    // Crear un JTable con el modelo de datos configurado
                    Tabla_informacion.setModel(model);

                    // Agregar el JTable a un JScrollPane
                    //JScrollPane scrollPane = new JScrollPane(Tabla_informacion);
                    System.out.println("Conexion establecida");
                }
                else{
                    JOptionPane.showMessageDialog(Ventana_principal,"Conexión no establecida", "Error en la conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Tabla_informacion =  new JTable();
    }
}
