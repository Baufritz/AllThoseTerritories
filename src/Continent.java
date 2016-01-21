import java.util.LinkedList;

/**
 * Created by Baufritz on 06.01.2016.
 */
public class Continent {
    int bonusTroops;
    String name;
    LinkedList<Territory> countries;
    public Continent(String name, int troops){
        this.name = name;
        this.bonusTroops = troops;
        this.countries = new LinkedList<>();
    }
    public void addCountries(Territory toAdd){
        countries.add(toAdd);
    }

    //TODO: belongstoPlayer
    //TODO: getreinforcements


    @Override
    public String toString(){
        String temp = this.name + ": \n";
        for(Territory terr: countries){
            temp += terr + " \n";
        }
        return temp;
    }

}
