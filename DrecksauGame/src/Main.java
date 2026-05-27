import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // Bisher nur ein SEHR ROUGH DRAFT von JFrame funktion für GUI
        // Wenn GUI nicht funktioniert und wir in der Konsole spielen müssen ist das auch kein Problem

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Drecksau");
        window.setSize(600, 900);

        JPanel gamepanel = new JPanel();
        window.add(gamepanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}