import java.awt.*;

/**
 * Created by Baufritz on 21.01.2016.
 */
public class Field{

    private String belongsTo;
    private Polygon p;

    public Field (String terr, Polygon p){
        this.belongsTo = terr;
        this.p = p;
    }

    public String belongsTo(){
        return belongsTo;
    }

    public Polygon getBorder(){
        return p;
    }

}
