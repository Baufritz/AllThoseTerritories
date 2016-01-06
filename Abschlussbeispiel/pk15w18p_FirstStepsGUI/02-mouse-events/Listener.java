import java.awt.*;
import java.awt.event.*;

public class Listener implements MouseListener {

    public void mousePressed(MouseEvent me) {
        System.out.println("down at " + me.getPoint());
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
        System.out.println("up at " + me.getPoint());
    }

    public void mouseClicked(MouseEvent me) {
        System.out.println("click at " + me.getPoint());
    }
}
