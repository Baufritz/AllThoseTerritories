/**
 * Created by Baufritz on 06.01.2016.
 *
 * Class AllThoseTerritories
 *
 * Resembles a game of Risk against an AI player.
 * -> calls Map Parser
 * -> calls Panel
 *
 * Map files need to be in the same directory as AllThoseTerritories.
 *
 */

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import javax.swing.*;

public class AllThoseTerritories extends JFrame{

    private static final String map = "world.map";

    public AllThoseTerritories(){
        this.setSize(1250, 650); //size of window, DO NOT CHANGE OR IT WILL FUCK STUFF UP
        this.setLocation(10,10); //just so it doesn't get jammed into the corner, remove if you need those valuable pixels
        this.setResizable(false);
        this.setTitle("All those Territories");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MapParser GameMap = new MapParser(getMap());

        this.add(new MapWindow(GameMap));
        this.setVisible(true);
    }

    //returns a file containing the map
    private InputStream getMap(){
        InputStream url = null;
        try {
            url = AllThoseTerritories.class.getResourceAsStream(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            public void run() {
                new AllThoseTerritories();
            }
        });
    }
}
