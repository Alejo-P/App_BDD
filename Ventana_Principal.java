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

    public Ventana_Principal() {
        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
