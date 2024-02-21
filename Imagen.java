import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Imagen {
    JPanel Img;
    private JLabel imagen1;
    private JButton usarEstaImagenButton;
    private JButton cancelarButton;

    public Imagen(File ruta) {
        ImageIcon imagen = new ImageIcon(String.valueOf(ruta));
        // Escalar la imagen al tamaño deseado
        Image imagenEscalada = imagen.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon imagenEscaladaIcon = new ImageIcon(imagenEscalada);

        imagen1.setIcon(imagenEscaladaIcon);

        usarEstaImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
