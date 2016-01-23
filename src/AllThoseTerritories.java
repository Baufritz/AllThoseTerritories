/**
 * Created by Baufritz on 06.01.2016.
 *
 * Class AllThoseTerritories
 *
 * Resembles a game of Risk against an AI player.
 *
 */

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import javax.swing.*;

public class AllThoseTerritories extends JFrame{

    public AllThoseTerritories(){
        this.setSize(1250, 650);
        this.setResizable(false);
        this.setTitle("All those Territories");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initWorldMap();
        this.setVisible(true);
    }

    //TODO: Create new MapParser and put all of this mess in there
    private void initWorldMap() {
        Map<String, Territory> TerrMap = new TreeMap<>();
        LinkedList<Continent> ContList = new LinkedList<>();
        LinkedList<Field> Places = new LinkedList<>();
        //Karte einlesen von .map Datei

        Scanner uinput = null;
        URL url = AllThoseTerritories.class.getResource("world.map");
        File inputMap = null;

        try {
            inputMap = new File(url.toURI());
            uinput = new Scanner(inputMap);
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
                    Places.add(current);

                    //If Territory specified doesn't exist, create new Territory and add it to the World Map
                    if (!TerrMap.containsKey(TerrName)) {
                        Territory terr = new Territory(TerrName);
                        terr.addField(current);
                        TerrMap.put(TerrName, terr);
                    }
                    //else just add Field to the Territory
                    else {
                        TerrMap.get(TerrName).addField(current);
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
                    TerrMap.get(TerrName).setCapital(Integer.parseInt(line[temp]), Integer.parseInt(line [temp+1]));
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
                    Territory terr = TerrMap.get(TerrName);

                    temp++;

                    while(temp < line.length) {
                        String Neighbor = "";
                        while(temp < line.length && !line[temp].equals("-")){
                            Neighbor += " " + line[temp];
                            temp++;
                        }
                        //System.out.println(Neighbor);
                        terr.addNeighbor(TerrMap.get(Neighbor));
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

                            tempCont.addCountries(TerrMap.get(Country));
                            temp++;
                        }
                        ContList.add(tempCont);
                        System.out.println(tempCont);

                    break;

                default:
                    System.out.println("Invalid Map.");
                    break;
            }
            currentline = uinput.nextLine();
        }
        System.out.println("World Map created. Loading Window...");
        this.add(new Panel(TerrMap, Places));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new AllThoseTerritories();
            }
        });
    }
}
