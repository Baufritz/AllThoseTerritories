import java.util.LinkedList;
import java.util.Map;

/**
 * Created by Baufritz on 23.01.2016.
 *
 * Class Game
 *
 * Handles game rounds, attacking, defending
 *
 **/

public class Game {

    private Map<String, Territory> territories;     //Map of all Territories without continent information
    private LinkedList<Continent> continents;       //List of all continents
    private MapWindow currentWindow;
    private int claimed;
    private int phase;                              //0: claim, 1: reinforcements, 2: attack, 3: move
    private int turn;                               //counts turns in game. (turn%2 == 1) => Player 1 (turn%2 == 0 => Player 2)

    //Constructor
    public Game(MapParser map, MapWindow window){
        territories = map.getTerritories();
        continents = map.getContinents();
        currentWindow = window;
        claimed = 0;
        phase = 0;
        turn = 0;
    }

    public void clickEvent(Territory selected){
        switch(phase){
            case 0: //beginning phase, Players acquire their territories. Only gets called when no
                acquireTerritories(selected);
                break;
            case 1: //calculate reinforcements
                getReinforcements(selected);
                break;
            case 2: //attack stuff
                attackNeighbors(selected);
                break;
            case 3: //move troops
                moveTroops(selected);
            //TODO: all the game phases
        }
    }

    //checks if the clicked territory is unclaimed, if yes claims it
    //then moves the game to the next phase if all territories on the map are claimed
    private void acquireTerritories(Territory selected){
        if (selected.getOwner() == 0) {
            acquire(selected, (turn % 2) + 1);
            turn++;
            claimed++;
        }
        if (allClaimed()){
            currentWindow.updateMap("Attacking now.");
            phase = 1;
        }
    }

    //TODO
    private void getReinforcements(Territory selected){}

    //TODO
    private void attackNeighbors(Territory selected){}

    //TODO
    private void moveTroops(Territory selected){}

    //acquires the chosen territory for the player
    //does not check whether or not the territory is claimed by another player
    public void acquire(Territory selected, int player){
            selected.setOwner(player);
            selected.addTroops(1);
            currentWindow.updateMap("Player " + player + " aquired" + selected.getName());
    }

    //checks if all territories on the map are claimed
    public boolean allClaimed(){

        /*
        for(Map.Entry<String, Territory> territoryEntry: territories.entrySet()){
            if(territoryEntry.getValue().getOwner() == 0){
                return false;
            }
        }
        */
        return claimed == territories.size();
    }

}
