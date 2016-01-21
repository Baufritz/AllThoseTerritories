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
    LinkedList<Field> Places;

    Point ref;

    public Panel( Map<String, Territory> init_TerrMap, LinkedList<Field> init_Places) {
        this.TerrMap = init_TerrMap;
        this.Places = init_Places;
        this.setBackground(Color.blue);
        //TODO: FIND RIGHT COLOR DAMNIT
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        paintLine(g2);

        for(Map.Entry<String,Territory> terr : TerrMap.entrySet()){
            Territory current = terr.getValue();
            LinkedList<Field> temp = terr.getValue().getFields();
            for( Field tempField : temp ){
                g.setColor(Color.black);
                g.drawPolygon(tempField.getBorder());                                           //Draws outer edge
                g.setColor(current.color());
                g.fillPolygon(tempField.getBorder());                                           //Fills the polygon
            }
            g.setColor(Color.green);
            g.drawString("X",terr.getValue().getCapitalX(), terr.getValue().getCapitalY()); //TODO: display troops

        }

    }
    public void paintLine(Graphics2D g){
        for(Map.Entry<String,Territory> terr : TerrMap.entrySet()) {
            Territory current = terr.getValue();
            g.setColor(Color.ORANGE);
            g.setStroke(new BasicStroke(3));
            for(Map.Entry<String,Territory> neigh : current.getNeighbors().entrySet()) {
                g.drawLine(current.getCapitalX(), current.getCapitalY(), neigh.getValue().getCapitalX(), neigh.getValue().getCapitalY());
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
        for(Field place: Places){
            if(place.getBorder().contains(me.getPoint())){
                TerrMap.get(place.belongsTo()).toggleActive();
            }
        }

        this.repaint();
    }

    public void mouseMoved(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
    }
}