import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Baufritz on 06.01.2016.
 */
public class Territory {


    private String name;
    private LinkedList<Polygon> Fields;
    private Point Capital;
    private int troops;
    //TODO: flag isPlayer oder Computer

    public Territory(String init_name){
        name = init_name;
        Fields = new LinkedList<Polygon>();
        troops = 0;
    }

    public void addTroops(int reinforces){
        troops += reinforces;
    }

    public void setCapital(Point p){
        Capital = p;
    }

    public void addField(Polygon poly){
        //adds new land to the territory
        Fields.add(poly);
    }

    public String getName(){
        return name;
    }

    public LinkedList<Polygon> getFields(){
        //returns all polygons of a territory for drawing
        return Fields;
    }

}
