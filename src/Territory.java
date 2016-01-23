import java.awt.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Baufritz on 06.01.2016.
 */
public class Territory {

    static final Color PLAYER = Color.green;
    static final Color AI = Color.red;
    static final Color ACTIVE = Color.yellow;

    private String name;                            //Name of Territoy
    private LinkedList<Field> Fields;               //Land for drawing
    private Map<String, Territory> Neighbors;       //Neighbors of Territory
    private int capX;                               //Capital of Territory, where to display Troops
    private int capY;                               //Capital of Territory, where to display Troops
    private int troops;                             //Amount of troops currently in this Territory
    private boolean active;                         //is this Territory currently active?
    private Color currentColor;                     //current color for drawing in Panel
    private int belongsTo;                          //0: neutral; 1: Player; 2: AI

    //Constructor requires name of Territory
    //everything else is set with functions
    public Territory(String init_name){
        name = init_name;
        Fields = new LinkedList<Field>();
        Neighbors = new TreeMap<String, Territory>();
        troops = 0;
        active = false;
        currentColor = Color.gray;
        belongsTo = 0;
    }

    public void toggleActive(){
        active = !active;
        if(active) this.currentColor = ACTIVE;
        else {
            if(belongsTo == 0) currentColor = Color.gray;
            if(belongsTo == 1) currentColor = PLAYER;
            if(belongsTo == 2) currentColor = AI;
        }
    }

    public boolean isActive(){
        return active;
    }

    //welcomes reinforcements
    public void addTroops(int reinforces){
        troops += reinforces;
    }

    //returns number of Soldiers currently in this Territory
    public int Army(){
        return troops;
    }

    //sets capital of Territory
    public void setCapital(int x, int y){
        this.capX = x;
        this.capY = y;
    }

    //returns capital of territory
    public int getCapitalX(){
        return capX;
    }
    public int getCapitalY(){
        return capY;
    }

    //adds new neighbor to attack/move troops to
    public void addNeighbor(Territory neighbor){
        Neighbors.put(neighbor.getName(), neighbor);
    }

    public Map<String, Territory> getNeighbors(){
        return Neighbors;
    }

    //adds new land to the territory
    public void addField(Field field){
        Fields.add(field);
    }

    //returns name of Territory
    public String getName(){
        return name;
    }

    //returns all polygons of territory for drawing
    public LinkedList<Field> getFields(){
        return Fields;
    }

    @Override
    public String toString(){
        return this.getName() + ": Currently " + troops + " troops.";
    }

    public Color color(){
        return currentColor;
    }

}

