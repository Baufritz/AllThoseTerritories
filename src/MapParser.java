import jdk.internal.util.xml.impl.Input;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by Baufritz on 23.01.2016.
 *
 * Class MapParser
 * Converts Map from strings to Continents, Territories and Fields
 *
 * <- gets File containing Map from AllThoseTerritories
 *
 */

public class MapParser{

    //regex strings for command matching
    public static final String PATCH_OF = "^patch-of( [A-z]+)+( [0-9]+ [0-9]+)+$";
    public static final String CAPITAL_OF = "^capital-of( [A-z]+)+( [0-9]+ [0-9]+)$";
    public static final String NEIGHBORS_OF = "^neighbors-of( [A-z]+)+ :(( [A-z]+)+ -)*( [A-z]+)+$";
    public static final String CONTINENT = "^continent( [A-z]+)+ ([0-9])+ :(( [A-z]+)+ -)*( [A-z]+)+$";

    private Map<String, Territory> territories;     //Map of all Territories without continent information
    private LinkedList<Continent> continents;       //List of all continents
    private LinkedList<Field> fields;               //List of all fields

    //public constructor, initializes territories, continents and fields, then calls parseWorldMap(); to fill them
    public MapParser(InputStream stringmap){
        this.territories = new TreeMap<>();
        this.continents = new LinkedList<>();
        this.fields = new LinkedList<>();

        parseWorldMap(stringmap);
    }

    //Reads map data from File, checks if it it a valid map and files it into Continents, Territories, and Fields.
    //calls parsePatch(), parseCapital(), parseNeighbors() and parseContinents().
    //File has to be a text file
    private void parseWorldMap(InputStream stringmap) {

        boolean isnotnull = true;

        Scanner uinput = null;
        String currentline = "";
        try {
            uinput = new Scanner(stringmap);
            //currentline = uinput.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(2);
        }

        while(isnotnull){
            currentline = uinput.nextLine();
            if(currentline.matches(PATCH_OF)) {
                parsePatch(currentline);
            }
            else if (currentline.matches(CAPITAL_OF)) {
                parseCapital(currentline);
            }
            else if (currentline.matches(NEIGHBORS_OF)){
                parseNeighbors(currentline);
            }
            else if (currentline.matches(CONTINENT)) {
                parseContinent(currentline);
            }
            else{
                System.out.println("Invalid Line: \"" + currentline + "\"");
                System.exit(1);
            }
            //if(!uinput.hasNext()) isnotnull = false;
            isnotnull = uinput.hasNext();

        }

    }

    //gets patch information from String and adds Patch to Territory
    //creates new Territory if it doesn't exist yet
    //input string has to be in correct form, should be called by parseWorldMap()
    private void parsePatch(String currentline){

        String[] line = currentline.split(" ");
        String TerrName = "";
        int temp = line.length;

        int xPoly[];
        int yPoly[];

        for (int i = 1; i < temp; i++) {
            if (!Character.isDigit(line[i].charAt(0))) {
                TerrName += " " + line[i];
            } else {
                temp = i;
            }
        }

        xPoly = new int[(line.length - temp + 1) / 2];
        yPoly = new int[(line.length - temp + 1) / 2];

        for (int i = 0; i < line.length - temp; i++) {
            if (i % 2 == 0)
                xPoly[i / 2] = Integer.parseInt(line[i + temp]);
            else
                yPoly[i / 2] = Integer.parseInt(line[i + temp]);
        }

        Territory terr = null;
        //If Territory specified in input line doesn't exist, create new Territory and add it to the World Map
        if (!territories.containsKey(TerrName)) {
            terr = new Territory(TerrName);
            territories.put(TerrName, terr);
        }
        //else just add Field to the existing Territory
        else {
            terr = territories.get(TerrName);
        }

        Field current = new Field(terr, new Polygon(xPoly, yPoly, xPoly.length));
        terr.addField(current);
        fields.add(current);
    }

    //gets capital information from String
    //Territory for Capital has to exist in map of Territories
    //input string has to be in correct form, should be called by parseWorldMap()
    private void parseCapital(String currentline){
        String[] line = currentline.split(" ");
        String TerrName = "";
        int temp = line.length;

        for (int i = 1; i < temp; i++) {
            if (!Character.isDigit(line[i].charAt(0))) {
                TerrName += " " + line[i];
            } else {
                temp = i;
            }
        }
        territories.get(TerrName).setCapital(Integer.parseInt(line[temp]), Integer.parseInt(line[temp + 1]));

    }

    //gets neighbor information from String
    //Territory for neighbors has to exist in map of Territories
    //input string has to be in correct form, should be called by parseWorldMap()
    private void parseNeighbors(String currentline){
        String[] line = currentline.split(" ");
        String TerrName = "";
        int temp = line.length;

        for (int i = 1; i < temp; i++) {
            if (!line[i].equals(":")) {
                TerrName += " " + line[i];
            } else {
                temp = i;
            }
        }
        Territory terr = territories.get(TerrName);

        temp++;

        while (temp < line.length) {
            String Neighbor = "";
            while (temp < line.length && !line[temp].equals("-")) {
                Neighbor += " " + line[temp];
                temp++;
            }
            terr.addNeighbor(territories.get(Neighbor));
            territories.get(Neighbor).addNeighbor(terr);
            temp++;
        }

    }

    //Groups Territories to Continents based on input string
    //Creates new continent if it doesn't exist yet, or adds territory to continent
    //Territories for Continent have to exist in map of Territories
    //input string has to be in correct form, should be called by parseWorldMap()
    private void parseContinent(String currentline){
        String[] line = currentline.split(" ");
        String TerrName = "";
        int temp = line.length;

        for (int i = 1; i < temp; i++) {
            if (!Character.isDigit(line[i].charAt(0))) {
                TerrName += " " + line[i];
            } else {
                temp = i;
            }
        }
        Continent tempCont = new Continent(TerrName, Integer.parseInt(line[temp]));

        temp += 2;

        while (temp < line.length) {
            String Country = "";
            while (temp < line.length && !line[temp].equals("-")) {
                Country += " " + line[temp];
                temp++;
            }

            tempCont.addCountry(territories.get(Country));
            temp++;
        }
        continents.add(tempCont);
        System.out.println(TerrName);
    }

    //returns LinkedList of Continents with associated Territories
    public LinkedList<Continent> getContinents(){
        return continents;
    }

    //returns Map of Territories without continent information
    public Map<String, Territory> getTerritories(){
        return territories;
    }

    //returns all fields of the world map
    public LinkedList<Field> getFields(){
        return fields;
    }

    //See if a Map is valid, just for testing
    /**
    public static void main(String[] args) {

        URL url = AllThoseTerritories.class.getResource("world.map");
        Scanner linemap = null;
        File inputMap = null;
        try {
            inputMap = new File(url.toURI());
            linemap = new Scanner(inputMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String currentline = linemap.nextLine();
        int count = 1;
        while (linemap.hasNext() && !currentline.equals("")){
           if(currentline.matches(PATCH_OF)){
               System.out.println(count + ": valid patch");
           }
            else if(currentline.matches(CAPITAL_OF)){
               System.out.println(count + ": valid capital");
           }
            else if(currentline.matches(NEIGHBORS_OF)){
               System.out.println(count + ": valid neighbors");
           }
            else if(currentline.matches(CONTINENT)){
               System.out.println(count + ": valid continent");
           }
            else{
               System.out.println(count + ": Invalid Line: " + currentline);
           }
            count++;
            currentline = linemap.nextLine();
        }

    }
     **/

}
