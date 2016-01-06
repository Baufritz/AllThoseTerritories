import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Panel extends JPanel
                   implements MouseListener, MouseMotionListener {
    public Panel() {
        addMouseListener(this);  // !
        addMouseMotionListener(this); // !
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.blue);
        g.fillRect(20, 20, 50, 50);
    }

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

    public void mouseMoved(MouseEvent me) {
        System.out.println("moved to " + me.getPoint());
    }

    public void mouseDragged(MouseEvent me) {
        System.out.println("dragged to " + me.getPoint());
    }
}
