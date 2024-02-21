import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

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
        conn=conexionMySQL.Conexion("jdbc:mysql://uv8e5bhcj06tdvic:ME3Yq8U3Cax9OIjbHqdq@bfgwcnxsobb1g6asgq4d-mysql.services.clever-cloud.com:3306/bfgwcnxsobb1g6asgq4d", "uv8e5bhcj06tdvic", "ME3Yq8U3Cax9OIjbHqdq");
        String[] Columnas = {"Cedula", "Nombre", "Apellido", "Fecha Matricula", "Periodo", "Direccion", "Telefono", "Edad", "Curso", "Imagen"};
        final byte[][] imagenBytes = {null};
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                }
                else{
                    JOptionPane.showMessageDialog(Ventana_principal,"Conexión no establecida", "Error en la conexión", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        insertarOActualizarRegistroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cedula=Insercion_cedula.getText();
                String nombre=Insercion_nombre.getText();
                String apellido=Insercion_apellido.getText();
                String direccion=Insercion_direccion.getText();
                String telefono=Insercion_telefono.getText();
                String edad=Insercion_edad.getText();
                String curso=Insercion_combobox.getSelectedItem().toString();
                boolean imagen = imagenBytes[0].length==0;
                if (!cedula.isEmpty() && !nombre.isEmpty() && !apellido.isEmpty() && !direccion.isEmpty() && !telefono.isEmpty() && !edad.isEmpty() && !curso.isEmpty() && !imagen){
                    int num=conexionMySQL.Modificar("INSERT INTO usuarios (cedula, nombre, apellido, direccion, telefono, edad, curso, imagen) VALUES (%d, %s, %s, %s, %s, %d, %s, %s)".formatted(Integer.parseInt(cedula), nombre, apellido, direccion, telefono, Integer.parseInt(edad), curso, imagenBytes[0]));
                    System.out.println(num);
                    if (num>0){
                        JOptionPane.showMessageDialog(Ventana_principal, "Registro insertado correctamente", "Acción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(Ventana_principal, "No se inserto el registro", "Error en la inserción", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(Ventana_principal, "Por favor, llene todos los campos y cargue una imagen", "Error en la entrada de datos", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        subirImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser archivo = new JFileChooser();
                int ventana = archivo.showOpenDialog(null);
                if (ventana == JFileChooser.APPROVE_OPTION){
                    File archivoImagen = archivo.getSelectedFile();
                    try {
                        // Leer la imagen desde el archivo seleccionado
                        FileInputStream Imagen = new FileInputStream(archivo.getSelectedFile());
                        imagenBytes[0] = new byte[(int) archivoImagen.length()];
                        Imagen.read(imagenBytes[0]);
                        Imagen.close();

                        //File file = archivo.getSelectedFile();
                        //imagenBytes[0] = Files.readAllBytes(file.toPath());
                        JOptionPane.showMessageDialog(Ventana_principal, "Imagen cargada y lista para ser insertada en la base de datos", "Acción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Tabla_informacion =  new JTable();
    }
}