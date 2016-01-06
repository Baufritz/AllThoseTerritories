import java.awt.*;
import java.awt.event.*;

public class MotionListener implements MouseMotionListener {

    public void mouseMoved(MouseEvent me) {
        System.out.println("moved to " + me.getPoint());
    }

    public void mouseDragged(MouseEvent me) {
        System.out.println("dragged to " + me.getPoint());
    }
}
