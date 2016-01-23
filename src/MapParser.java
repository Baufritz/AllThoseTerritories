import java.awt.*;
import java.io.File;
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
 * <- gets File containing Map from Game
 *
 *
 */

public class MapParser{

    //TODO: Write Strings for regex matching
    public static final String PATCH_OF = "";
    public static final String CAPITAL_OF = "";
    public static final String NEIGHBORS_OF = "";
    public static final String CONTINENT = "";

    private Map<String, Territory> territories;
    private LinkedList<Continent> continents;
    private LinkedList<Field> fields;

    public MapParser(File stringmap){
        Map<String, Territory> TerrMap = new TreeMap<>();
        LinkedList<Continent> ContList = new LinkedList<>();
        LinkedList<Field> Places = new LinkedList<>();

        initWorldMap(stringmap);
    }

    //Reads map data from File and files it into Continents, Territories, and Fields.
    private void initWorldMap(File stringmap) {
        Scanner uinput = null;
        try {
            uinput = new Scanner(stringmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String currentline = uinput.nextLine();

        while (uinput.hasNext() && !currentline.equals("")) {
            String[] line = currentline.split(" ");

            String TerrName = "";
            int temp = line.length;

            switch (line[0]) {
                case "patch-of":                                                        //adds new Polygon to draw to the Territory specified
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

                    Field current = new Field(TerrName, new Polygon(xPoly, yPoly, xPoly.length));
                    fields.add(current);

                    //If Territory specified doesn't exist, create new Territory and add it to the World Map
                    if (!territories.containsKey(TerrName)) {
                        Territory terr = new Territory(TerrName);
                        terr.addField(current);
                        territories.put(TerrName, terr);
                    }
                    //else just add Field to the Territory
                    else {
                        territories.get(TerrName).addField(current);
                    }

                    break;

                case "capital-of":
                    for (int i = 1; i < temp; i++) {
                        if (!Character.isDigit(line[i].charAt(0))) {
                            TerrName += " " + line[i];
                        } else {
                            temp = i;
                        }
                    }
                    territories.get(TerrName).setCapital(Integer.parseInt(line[temp]), Integer.parseInt(line [temp+1]));
                    break;

                case "neighbors-of":
                    //TODO
                    for (int i = 1; i < temp; i++) {
                        if (!line[i].equals(":")) {
                            TerrName += " " + line[i];
                        } else {
                            temp = i;
                        }
                    }
                    //System.out.println(TerrName);
                    Territory terr = territories.get(TerrName);

                    temp++;

                    while(temp < line.length) {
                        String Neighbor = "";
                        while(temp < line.length && !line[temp].equals("-")){
                            Neighbor += " " + line[temp];
                            temp++;
                        }
                        //System.out.println(Neighbor);
                        terr.addNeighbor(territories.get(Neighbor));
                        temp++;
                    }

                    break;
                case "continent":
                    for (int i = 1; i < temp; i++) {
                        if (!Character.isDigit(line[i].charAt(0))) {
                            TerrName += " " + line[i];
                        } else {
                            temp = i;
                        }
                    }
                    Continent tempCont = new Continent(TerrName, Integer.parseInt(line[temp]));

                    temp += 2;

                    while(temp < line.length) {
                        String Country = "";
                        while(temp < line.length && !line[temp].equals("-")){
                            Country += " " + line[temp];
                            temp++;
                        }

                        tempCont.addCountries(territories.get(Country));
                        temp++;
                    }
                    continents.add(tempCont);
                    System.out.println(tempCont);

                    break;

                default:
                    System.out.println("Invalid Map.");
                    break;
            }
            currentline = uinput.nextLine();
        }
    }

    //returns list of Continents with associated Territories
    public LinkedList<Continent> getContinents(){
        return continents;
    }

    //returns map of Territories for quick access
    public Map<String, Territory> getTerritories(){
        return territories;
    }

    //returns all fields
    public LinkedList<Field> getFields(){
        return fields;
    }

}
