/**
 * Created by Baufritz on 06.01.2016.
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

    Map<String, Territory> TerrMap;

    Point ref;

    public Panel( Map<String, Territory> init_TerrMap) {

        TerrMap = init_TerrMap;

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.gray);

        for(Map.Entry<String,Territory> terr : TerrMap.entrySet()){
            LinkedList<Polygon> temp = terr.getValue().getFields();

            for( Polygon tempField : temp ){
                g.drawPolygon(tempField);
                //g.fillPolygon(tempField);                                         //für geschlossene Flächen, dann wird allerdings keine Randlinie gezogen
            }

        }

    }

    public void mousePressed(MouseEvent me) {

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

    }
}