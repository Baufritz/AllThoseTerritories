import java.io.File;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Baufritz on 23.01.2016.
 *
 * Class Game
 * <- gets map name from AllThoseTerritories*
 * -> calls Map Parser
 * -> calls Panel
 * Handles game rounds, attacking, defending
 * Stores Map of Territories and Map of Continents
 *
 **/

public class Game {

    //Constructor
    public Game(){
        //TODO: Call MapParser
    }


    //TODO: Everything

    /* //just for testing
    public static void main(String[] args) {
        Scanner uinput = null;
        URL url = AllThoseTerritories.class.getResource("world.map");
        File inputMap = null;

        try {
            inputMap = new File(url.toURI());
            uinput = new Scanner(inputMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(uinput.hasNext())
            System.out.println(uinput.nextLine());

    }
    */

}
