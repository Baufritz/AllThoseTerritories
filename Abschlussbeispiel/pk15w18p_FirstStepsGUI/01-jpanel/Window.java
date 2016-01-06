import javax.swing.*;

public class Window extends JFrame {

    public Window() {
        // in das Fenster JFrame wird eine Fläche JPanel
        // eingefügt. Das JPanel bleibt vorerst leer.
        this.add(new JPanel());

        this.setSize(400, 500);
        this.setTitle("Some Rectangles");

        // Wenn [x] am Fenster angeklickt wird, soll sich
        // das Programm auch beenden (EXIT) und nicht nur
        // das Fenster geschlossen werden:
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
