import java.util.LinkedList;

/**
 * Created by Baufritz on 06.01.2016.
 *
 * Class Continent
 *
 * Groups territories together.
 * If a player owns all territories in a continent,
 * he gets bonus reinforcements.
 *
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
    public void addCountry(Territory toAdd){
        countries.add(toAdd);
    }

    //checks if all Territories belong to the same player
    //if yes, returns amount of bonus troops specified in constructor
    //if no, returns 0;
    public int getBonusTroops(int player){
        for(Territory country: countries){
            if(country.getOwner() != player) return 0;
        }
        return bonusTroops;
    }

    @Override
    public String toString(){
        String temp = this.name + ": \n";
        for(Territory terr: countries){
            temp += terr + " \n";
        }
        return temp;
    }

}
