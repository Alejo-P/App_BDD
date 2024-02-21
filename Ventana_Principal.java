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
    private Registro usuario;

    public Ventana_Principal() {
        conexionMySQL = new ConexionMySQL();
        usuario = new Registro();
        conn=conexionMySQL.Conexion("jdbc:mysql://uv8e5bhcj06tdvic:ME3Yq8U3Cax9OIjbHqdq@bfgwcnxsobb1g6asgq4d-mysql.services.clever-cloud.com:3306/bfgwcnxsobb1g6asgq4d", "uv8e5bhcj06tdvic", "ME3Yq8U3Cax9OIjbHqdq");
        String[] Columnas = {"Cedula", "Nombre", "Apellido", "Fecha Matricula", "Periodo", "Direccion", "Telefono", "Edad", "Curso", "Imagen"};
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
                try {
                    usuario.setCedula(Integer.parseInt(Insercion_cedula.getText()));
                    usuario.setNombre(Insercion_nombre.getText());
                    usuario.setApellido(Insercion_apellido.getText());
                    usuario.setDireccion(Insercion_direccion.getText());
                    usuario.setTelefono(Insercion_telefono.getText());
                    usuario.setEdad(Integer.parseInt(Insercion_edad.getText()));
                    usuario.setCurso(Insercion_combobox.getSelectedItem().toString());
                    if (usuario.regitroCompleto() && usuario.getImagen().length!=0){
                        try{
                            int num=conexionMySQL.insertarRegustros(usuario);
                            if (num>0){
                                JOptionPane.showMessageDialog(Ventana_principal, "Registro insertado correctamente", "Acción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(Ventana_principal, "No se inserto el registro", "Error en la inserción", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception e1){
                            int opcion = JOptionPane.showConfirmDialog(Ventana_principal, "Desea actualizar el registro", "Registro repetido", JOptionPane.YES_NO_OPTION);
                            String mensajeError = "Error en la accion: %s".formatted(String.valueOf(e1));
                            JOptionPane.showMessageDialog(Ventana_principal, mensajeError, "Error en la accioon realizada", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(Ventana_principal, "Por favor, llene adecuadamente todos los campos y cargue una imagen\nCampos Cedula y Telefono de 10 digitos,\ny llene todos los campos solicitados) ", "Error en la entrada de datos", JOptionPane.ERROR_MESSAGE);
                    }
                }catch (NumberFormatException nfe){
                    JOptionPane.showMessageDialog(Ventana_principal, "Inserte un valor numerico en los campos Cedula y Edad", "Datos Invalidos", JOptionPane.ERROR_MESSAGE);
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
                        byte[] imagenBytes= new byte[(int) archivoImagen.length()];
                        Imagen.close();
                        JFrame imgs = new JFrame("Visor de imagenes");
                        imgs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        imgs.setContentPane(new Imagen(archivoImagen).Img);
                        imgs.setLocationRelativeTo(null);
                        imgs.setResizable(false);
                        imgs.pack();
                        imgs.setSize(400, 400);
                        imgs.setVisible(true);
                        usuario.setImagen(imagenBytes);

                        JOptionPane.showMessageDialog(Ventana_principal, "Imagen cargada y lista para ser insertada en la base de datos", "Acción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cons=Eliminacion_cedula.getText();
                if (!cons.isEmpty())
                {
                    int columnas = conexionMySQL.Modificar("DELETE FROM usuarios WHERE cedula = %s".formatted(cons));
                    if (columnas>0){
                        JOptionPane.showMessageDialog(Ventana_principal, "Registro eliminado exitosamente", "Eliminacion exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        String mensajeError = "Ningun registro coincide con : %s".formatted(cons);
                        JOptionPane.showMessageDialog(Ventana_principal, mensajeError, "Error en la eliminacion", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(Ventana_principal, "Llene el campo antes ed presionar el boton", "Accion invalida", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        Tabla_informacion =  new JTable();
    }
}