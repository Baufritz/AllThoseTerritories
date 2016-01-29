/**
 * Created by Baufritz on 06.01.2016.
 *
 * MapWindow Class
 * Draws main Map and handles interactions with Game
 *
 * <- gets MapParser from AllThoseTerritories
 * -> calls Game with parsedMap and itself as parameter
 */
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.Map;

public class MapWindow extends JPanel implements MouseListener, MouseMotionListener {

    Map<String, Territory> TerrMap;
    LinkedList<Field> Places;
    Game currentGame;
    String statusmessage;

    public MapWindow(MapParser parsedMap) {
        this.TerrMap = parsedMap.getTerritories();
        this.Places = parsedMap.getFields();
        this.setBackground(Color.blue);
        addMouseListener(this);
        addMouseMotionListener(this);
        currentGame = new Game(parsedMap, this);
        statusmessage = "Map loaded: " + TerrMap.size() + " territories on " + parsedMap.getContinents().size() + " continents.";
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        paintLines(g2);

        for(Map.Entry<String,Territory> terr : TerrMap.entrySet()){
            Territory current = terr.getValue();
            LinkedList<Field> temp = terr.getValue().getFields();
            for( Field tempField : temp ){
                g.setColor(Color.black);
                g.drawPolygon(tempField.getBorder());                                           //Draws outer edge
                g.setColor(current.color());
                g.fillPolygon(tempField.getBorder());                                           //Fills the polygon
            }
            g.setColor(Color.black);
            g.drawString(Integer.toString(terr.getValue().Army()),terr.getValue().getCapitalX(), terr.getValue().getCapitalY()); //Display troops in capital

        }

        g.setColor(Color.white);
        g.drawString(statusmessage, 10, 610);

    }

    public void paintLines(Graphics2D g){
        for(Map.Entry<String,Territory> terr : TerrMap.entrySet()) {
            Territory current = terr.getValue();
            g.setColor(Color.white);
            g.setStroke(new BasicStroke(3));
            for(Map.Entry<String,Territory> neigh : current.getNeighbors().entrySet()) {
                int aX = current.getCapitalX();
                int aY = current.getCapitalY();
                int bX = neigh.getValue().getCapitalX();
                int bY = neigh.getValue().getCapitalY();

                //compute distance normal
                int AtoBX = Math.abs(aX-bX);
                int AtoBY = Math.abs(aY-bY);

                double ABnormal = Math.sqrt(AtoBX * AtoBX + AtoBY * AtoBY);

                //compute distance if drawn over horizontal border
                if(aX < bX)
                    AtoBX = Math.abs(aX + 1250 - bX);
                else
                    AtoBX = Math.abs(bX + 1250 - aX);

                double ABhorizontal = Math.sqrt(AtoBX * AtoBX + AtoBY * AtoBY);

                //compute distance if drawn over vertical border
                AtoBX = Math.abs(bX - aX);
                AtoBY = Math.abs(bY - aY + 650);
                double ABvertical = Math.sqrt(AtoBX * AtoBX + AtoBY * AtoBY);

                //drawLine() works even if you set the points out of bounds
                //normal distance is shortest
                if(ABnormal <= ABhorizontal && ABnormal <= ABvertical) {
                    g.setColor(Color.white);
                    g.drawLine(aX, aY, bX, bY);
                }
                //horizontal distance is shortest
                else if (ABhorizontal <= ABnormal && ABhorizontal <= ABvertical) {
                    if(aX < bX)g.drawLine(aX + 1250,aY,bX, bY);
                    else g.drawLine(bX + 1250,bY,aX, aY);

                    if(aX < bX)g.drawLine(bX - 1250,bY,aX, aY);
                    else g.drawLine(aX - 1250,aY,bX, bY);

                }
                //vertical distance is shortest
                else if(ABvertical <= ABhorizontal && ABvertical <= ABnormal) {
                    if(aX < bX)g.drawLine(aX,aY + 650,bX, bY);
                    else g.drawLine(bX,bY + 650,aX, aY);

                    if(aX < bX)g.drawLine(bX,bY - 650,aX, aY);
                    else g.drawLine(aX,aY - 650,bX, bY);

                }

            }
        }

    }

    //prints status message to screen and console, redraws Map
    public void updateMap(){this.updateMap("");}
    public void updateMap(String update){
        if(!update.equals("")) {
            System.out.println(update);
            statusmessage = update;
        }
        this.repaint();
    }

    //currently does nothing
    public void mousePressed(MouseEvent me) {
    }

    //currently does nothing
    public void mouseExited(MouseEvent me) {
    }

    //currently does nothing
    public void mouseEntered(MouseEvent me) {
    }

    //currently does nothing
    public void mouseReleased(MouseEvent me) {
    }

    //checks if the mouse click happened on a Field
    //if yes, calls currentGame.clickEvent() with the territory clicked
    public void mouseClicked(MouseEvent me) {
        for(Field place: Places){
            if(place.getBorder().contains(me.getPoint())){
                currentGame.clickEvent(place.belongsTo(), me);
            }
        }
        this.repaint();

    }

    //currently does nothing
    public void mouseMoved(MouseEvent me) {
    }

    //currently does nothing
    public void mouseDragged(MouseEvent me) {
    }
}