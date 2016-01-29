import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

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
    private Map<String, Territory> unclaimedLand;   //Map of unclaimed territories, for setup phase
    private Map<String, Territory> Player;          //Map of PLaer Territories
    private Map<String, Territory> AI;              //Map of AI territories

    private LinkedList<Continent> continents;       //List of all continents

    private MapWindow currentWindow;

    private Territory lastClicked;
    private Territory movingTarget;

    private int phase;                              //0: claim, 1: reinforcements, 2: attack, 3: move
    private int bonustroops;


    //Constructor
    public Game(MapParser map, MapWindow window){
        territories = map.getTerritories();
        unclaimedLand = new TreeMap<>(territories);
        continents = map.getContinents();
        currentWindow = window;
        Player = new TreeMap<>();
        AI = new TreeMap<>();
        phase = 0;
        lastClicked = null;
        movingTarget = null;
    }

    public void clickEvent(Territory selected, MouseEvent me){
        switch(phase){
            case 0: //beginning phase, Players acquire their territories. Only gets called when no
                acquireTerritories(selected);
                break;
            case 1: //place reinforcements
                placeReinforcements(selected);
                break;
            case 2: //attack stuff
                attackNeighbors(selected);
                break;
            case 3: //move troops
                moveTroops(selected, me);
                break;
            case 4: //AI turn
                moveAI();
            //TODO: all the game phases
        }

        switch (GameOver()){
            case 1:
                JOptionPane.showMessageDialog(null, "You win!");
                System.exit(0);
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "You lose!");
                System.exit(0);
                break;
        }
    }

    //checks if the clicked territory is unclaimed, if yes claims it
    //then moves the game to the next phase if all territories on the map are claimed
    private void acquireTerritories(Territory selected){
        if (selected.getOwner() == 0) {
            acquire(selected, 1);                                   //Player aquires cliocked territory
            acquire(getRandomTerritory(unclaimedLand), 2);          //AI aquires a random free territory
        }
        if (unclaimedLand.size() == 0){
            phase = 1;
            bonustroops = calculateReinforcements(Player, continents, 1);
            currentWindow.updateMap("Available Reinforcements: " + bonustroops);
        }
    }

    private void placeReinforcements(Territory selected){
        if(Player.containsValue(selected)){
            selected.addTroops(1);
            bonustroops--;
            currentWindow.updateMap("Available Reinforcements: " + bonustroops);
        }
        if(bonustroops == 0){
            phase = 2;
            currentWindow.updateMap("All Reinforcements deployed. Click on a friendly territory to attack a neighbor or move.");
            lastClicked = null;
        }
    }

    private void attackNeighbors(Territory selected){
        if(lastClicked != null && !selected.equals(lastClicked)){
            if(lastClicked.Army() > 1) {
                if (Player.containsValue(selected)) {
                    phase = 3;
                    movingTarget = selected;
                    System.out.println(movingTarget);
                    currentWindow.updateMap("Left click to move, right click to return troops. Click anywhere outside the territory to end your turn.");
                }else if(selected.getNeighbors().containsValue(lastClicked)){  //ATTACK!
                    int remaining = attack(selected, lastClicked);
                    if (selected.Army() == 0) {
                        acquire(selected, 1);
                        Player.put(selected.getName(), selected);
                        AI.remove(selected.getName());
                        selected.addTroops(remaining - 1);
                        lastClicked.addTroops(-remaining);
                    }
                    currentWindow.updateMap("Attack From " + lastClicked.getName() + " to " + selected.getName() + ". Choose new territory to move or attack with");
                    lastClicked = null;
                }
                else currentWindow.updateMap("These territories are not neighbors!");
            }
            else if (Player.containsValue(selected)){
                phase = 3;
                movingTarget = selected;
                System.out.println(movingTarget);
                currentWindow.updateMap("Left click to move, right click to return troops. Click any other territory to end your turn.");
            }
        }
        else if(Player.containsValue(selected)){
            if(selected.Army() == 1) {
                currentWindow.updateMap("You can't attack or move with this territory");
            }else{
                currentWindow.updateMap("Territory " + selected );
                lastClicked = selected;
            }
        }
        checkIfTurnOver();
    }

    public void checkIfTurnOver(){
        boolean turnOver = true;
        for(Map.Entry<String,Territory> territories: Player.entrySet()){
            if(territories.getValue().Army() > 1) turnOver = false;
        }
        if(turnOver){
            phase = 4;
            JOptionPane.showMessageDialog(null, "You have no more Actions left. Computers turn.");
        }

    }

    //TODO
    private void moveTroops(Territory selected, MouseEvent me){
        System.out.println(selected);
        if(selected.equals(movingTarget)){
            if(me.getButton() == 1 && lastClicked.Army() > 1){  //left click
                selected.addTroops(1);
                lastClicked.addTroops(-1);
                currentWindow.updateMap();
            }
            if(me.getButton() == 3 && selected.Army() > 1){  //right click
                selected.addTroops(-1);
                lastClicked.addTroops(1);
                currentWindow.updateMap();
            }
        }
        else{
            currentWindow.updateMap("Turn Ended. Computer Playing");
            phase = 4;
            clickEvent(null, null);
        }

    }

    private int attack(Territory opponent, Territory friendly){
        int opTroops = (opponent.Army() >= 2? 2: 1);
        int friendlyTroops = (friendly.Army() >= 4 ? 3: friendly.Army()-1);

        int[] opRolls = new int[opTroops];
        for(int i = 0; i < opRolls.length; i++){
            opRolls[i] = (int)(Math.random() * 6 + 0.5);
        }

        int[] friendlyRolls = new int[friendlyTroops];
        for(int i = 0; i < friendlyRolls.length; i++){
            friendlyRolls[i] = (int)(Math.random() * 6 + 0.5);
        }

        Arrays.sort(opRolls);
        Arrays.sort(friendlyRolls);

        for(int i = 1; i <= Math.min(opRolls.length, friendlyRolls.length); i++){
            if(opRolls[opRolls.length-i] < friendlyRolls[friendlyRolls.length -i]){
                opponent.addTroops(-1);
                opTroops--;
            }else{
                friendly.addTroops(-1);
                friendlyTroops--;
            }
        }
        return friendlyTroops;

    }


    private int calculateReinforcements(Map<String, Territory> territories, LinkedList<Continent> continents, int player){
        int terrbonus = territories.size()/3;

        for(Continent cont : continents){
            terrbonus += cont.getBonusTroops(player);
        }

        return terrbonus;
    }

    //acquires the chosen territory for the player
    //does not check whether or not the territory is claimed by another player
    public void acquire(Territory selected, int player){
            selected.setOwner(player);
            selected.addTroops(1);
        if(player == 1) {
            Player.put(selected.getName(), selected);
            unclaimedLand.remove(selected.getName());
        }
        if(player == 2){
            AI.put(selected.getName(), selected);
            unclaimedLand.remove(selected.getName());
        }
            currentWindow.updateMap("Player " + player + " aquired" + selected.getName());
    }

    public Territory getRandomTerritory(Map<String, Territory> territories){

        int size = territories.size();
        int rand = (int)(Math.random() * (size-1) + 0.5);
        String terr = (String)territories.keySet().toArray()[rand];

        return territories.get(terr);

    }

    public int GameOver(){

        if(Player.size() == territories.size()) return 1;
        if(AI.size() == territories.size()) return 2;

        return 0;
    }

    //TODO
    private void moveAI(){
        System.out.println("AI TURN STARTED");
        //TODO: DO STUFF

        int reinforcements = calculateReinforcements(AI, continents, 2);

        while(reinforcements-- > 0){
            String terr = getRandomTerritory(AI).getName();
            AI.get(terr).addTroops(1);
            System.out.println("AI placing 1 on " + terr);
        }

        Territory attacker = getMaxTroops(AI);
        Territory defender = getMinNeighborTroops(Player, attacker);

        int attacks = 1 +(int)(Math.random() * (attacker.Army()-1));

        for(int i = 0; i < attacks && defender.getOwner() != 2; i++){
            System.out.println("AI attacking " + defender.getName() + " with " + attacker.getName());
            int remaining = attack(defender,attacker);
            if(defender.Army() == 0){
                acquire(defender,2);
                Player.remove(defender.getName());
                AI.put(defender.getName(), defender);
                defender.addTroops(remaining - 1);
                attacker.addTroops(-remaining);
            }
        }

        phase = 1;
        bonustroops = calculateReinforcements(Player, continents,1);
        currentWindow.updateMap("Computer finished. It's your turn. you have " + bonustroops + " reinforcements");
        System.out.println("AI TURN END");

    }

    private Territory getMaxTroops(Map<String, Territory> territories){
        int maxTroops = 0;
        Territory territory = null;
        for(Map.Entry<String, Territory> Entry: territories.entrySet()){
            if(Entry.getValue().existsOpponent() && Entry.getValue().Army() > maxTroops) {
                territory = Entry.getValue();
                maxTroops = territory.Army();
            }
        }
        return territory;
    }

    private Territory getMinNeighborTroops(Map<String, Territory> opponent, Territory attacker){
        int minTroops = -1;
        Territory territory = null;
        for(Map.Entry<String, Territory> Entry: opponent.entrySet()){
            if(attacker.getNeighbors().containsValue(Entry.getValue())) {
                if (minTroops == -1) {
                    territory = Entry.getValue();
                    minTroops = territory.Army();
                }
                if (Entry.getValue().Army() < minTroops) {
                    territory = Entry.getValue();
                    minTroops = territory.Army();
                }
            }
        }
        return territory;
    }

}
