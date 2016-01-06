import javax.swing.*;
import java.awt.event.*;

public class Window extends JFrame {
    public Window() {
        this.add(new Panel());
        this.setSize(400, 500);
        this.setTitle("Drawing some Rectangles");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
