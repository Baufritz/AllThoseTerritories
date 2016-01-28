import java.awt.*;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;



/**
 * Created by Baufritz on 06.01.2016.
 *
 * Territory Class
 *
 * Represents a territory on the map.
 *
 */
public class Territory {

    //Color constants for player, AI, and selected countries
    private static final Color[] PLAYERCOLORS = {Color.gray, Color.green, Color.red};
    private static final Color ACTIVE = Color.yellow;

    //TODO: if any variables are added, add them to equals() as well
    private String name;                            //Name of Territory
    private LinkedList<Field> fields;               //Land for drawing
    private Map<String, Territory> neighbors;       //Neighbors of Territory
    private int belongsTo;                          //0: neutral; 1: Player; 2: AI
    private int capX;                               //X coordinate of capital of Territory, where to display Troops
    private int capY;                               //Y coordinate of capital of Territory, where to display Troops
    private int troops;                             //Amount of troops currently in this Territory
    private boolean active;                         //is this Territory currently active?
    private Color currentColor;                     //current color for drawing in Panel

    //Constructor requires name of Territory
    //everything else is set with functions
    public Territory(String init_name){
        name = init_name;
        fields = new LinkedList<Field>();
        neighbors = new TreeMap<String, Territory>();
        troops = 0;
        active = false;
        currentColor = Color.gray;
        belongsTo = 0;
    }

    public void toggleActive(){
        active = !active;
        if(active) this.currentColor = ACTIVE;
        else {
            currentColor = PLAYERCOLORS[belongsTo];
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

    //sets Owner of territory
    public void setOwner(int owner){
        belongsTo = owner;
        currentColor = PLAYERCOLORS[belongsTo];
    }

    //returns Owner from Territory
    public int getOwner(){
        return belongsTo;
    }

    //return capital x and y coordinates of territory
    public int getCapitalX(){
        return capX;
    }
    public int getCapitalY(){
        return capY;
    }

    //adds new neighbor to attack/move troops to
    public void addNeighbor(Territory neighbor){
        neighbors.put(neighbor.getName(), neighbor);
    }

    public Map<String, Territory> getNeighbors(){
        return neighbors;
    }

    //adds new land to the territory
    public void addField(Field field){
        fields.add(field);
    }

    //returns name of Territory
    public String getName(){
        return name;
    }

    //returns all polygons of territory for drawing
    public LinkedList<Field> getFields(){
        return fields;
    }

    @Override
    //returns String with territory name, current troop count and attack options
    public String toString(){
        String territory = this.getName() + ": " + troops + " troops. Neighbors:";
        for(Map.Entry<String, Territory> terr: neighbors.entrySet()){
            territory += terr.getKey() + ",";
        }
        territory = territory.substring(1,territory.length()-1);        //just for formatting, comment out if you don't mind a colon at the end
        return territory;
    }

    @Override
    //basically compares every public variable form the two objects
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || ! this.getClass().equals(other.getClass())) {
            return false;
        }
        Territory that = (Territory) other;

        if(!that.name.equals(this.name)) return false;
        if(that.belongsTo != this.belongsTo) return false;
        if(that.capX != this.capX)return false;
        if(that.capY != this.capY)return false;
        if(that.troops != this.troops)return false;
        if(!that.fields.equals(this.fields))return false;
        if(!that.neighbors.equals(this.neighbors))return false;
        if(!that.currentColor.equals(this.color()))return false;

        return true;
    }

    public boolean existsOpponent(){
        for(Map.Entry<String, Territory> Entry: neighbors.entrySet()){
            if(Entry.getValue().getOwner() != belongsTo) return true;
        }
        return false;
    }

    public Color color(){
        return currentColor;
    }
}

