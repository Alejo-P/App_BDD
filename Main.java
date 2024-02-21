import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame Inicio = new JFrame("Manipulacion BDD");
        Inicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Inicio.setLocationRelativeTo(null);
        Inicio.setContentPane(new Ventana_Principal().Ventana_principal);
        Inicio.pack();
        Inicio.setVisible(true);
    }
}
