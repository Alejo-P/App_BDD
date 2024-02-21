import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Imagen {
    private JPanel Img;
    private JLabel imagen1;
    private JButton usarEstaImagenButton;
    private JButton cancelarButton;

    public Imagen(File ruta) {
        ImageIcon imagen = new ImageIcon(String.valueOf(ruta));
        imagen1.setIcon(imagen);
        usarEstaImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
