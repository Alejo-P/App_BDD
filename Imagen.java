import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;


public class Imagen {
    JPanel Img;
    private JLabel imagen1;
    private JButton usarEstaImagenButton;
    private JButton cancelarButton;
    private Registro usuario;

    public Imagen(File ruta, Registro info) {
        usuario = info;
        ImageIcon imagen = new ImageIcon(String.valueOf(ruta));
        // Escalar la imagen al tamaño deseado
        Image imagenEscalada = imagen.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imagenEscaladaIcon = new ImageIcon(imagenEscalada);

        imagen1.setIcon(imagenEscaladaIcon);

        usarEstaImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Leer la imagen desde el archivo seleccionado
                FileInputStream Imagen = null;
                try {
                    Imagen = new FileInputStream(ruta);
                    byte[] imagenBytes= new byte[(int) ruta.length()];
                    Imagen.close();
                    usuario.setImagen(imagenBytes);
                    JOptionPane.showMessageDialog(null, "Imagen cargada y lista para ser insertada en la base de datos", "Acción Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(Img); // Cerrar la ventana actual
                    frame.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar la imagen", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame=(JFrame) SwingUtilities.getWindowAncestor(Img); //Cerrar la ventana actual
                frame.dispose();
            }
        });
    }
}
