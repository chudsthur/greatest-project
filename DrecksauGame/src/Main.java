import javax.swing.*;

public class Main {
    public static void main(String[] args) {

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