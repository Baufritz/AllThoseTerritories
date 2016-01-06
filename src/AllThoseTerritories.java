/**
 * Created by Baufritz on 06.01.2016.
 */

import java.awt.*;
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

    private void initWorldMap() {

        Map<String, Territory> TerrMap = new TreeMap<>();
        //Karte einlesen von .map Datei

        Scanner uinput = new Scanner(System.in);
        String currentline = uinput.nextLine();

        while (!(currentline == null) && !currentline.equals("")) {
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

                    /*
                    System.out.println(TerrName);       //debugging stuff

                    for (int i : xPoly) {
                        System.out.print(i + " ");
                    }
                    System.out.println();
                    for (int i : yPoly) {
                        System.out.print(i + " ");
                    }
                    */

                    if (!TerrMap.containsKey(TerrName)) {
                        System.out.println("CREATING NEW STUFF");
                        //create new Territory and add it to the World Map
                        Territory terr = new Territory(TerrName);
                        terr.addField(new Polygon(xPoly, yPoly, xPoly.length));
                        TerrMap.put(TerrName, terr);
                    } else {
                        TerrMap.get(TerrName).addField(new Polygon(xPoly, yPoly, xPoly.length));
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
                    Point p = new Point(Integer.parseInt(line[temp]), Integer.parseInt(line [temp+1]));
                    TerrMap.get(TerrName).setCapital(p);
                    break;
                case "neighbors-of":
                    //TODO
                    break;
                case "continent":
                    //TODO
                    break;
                default:
                    break;
            }
            currentline = uinput.nextLine();
        }

        this.add(new Panel(TerrMap));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new AllThoseTerritories();
            }
        });
    }

}
