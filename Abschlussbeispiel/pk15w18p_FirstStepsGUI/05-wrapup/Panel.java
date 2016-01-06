import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    int x;
    int y;

    Point ref;

    public Panel() {
        x = 20;
        y = 20;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.blue);
        g.fillRect(x, y, 50, 50);
    }

    public void mousePressed(MouseEvent me) {
        ref = me.getPoint();
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mouseMoved(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
        Point newRef = me.getPoint();

        int relx = (newRef.x - ref.x);
        int rely = (newRef.y - ref.y);

        x += relx;
        y += rely;

        ref = newRef;

        this.repaint();
    }
}
