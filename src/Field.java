import java.awt.*;

/**
 * Created by Baufritz on 21.01.2016.
 *
 * Represents a piece of land in a Territory.
 *
 * <- gets Name form assigned Territory as String and an outline as Polygon
 *
 */
public class Field{

    private Territory belongsTo;       //Name of Territory this field belongs to
    private Polygon p;              //outline of field

    //public constructor
    public Field (Territory terr, Polygon outline){
        this.belongsTo = terr;
        this.p = outline;
    }

    //returns Name of Territory this field belongs to
    public Territory belongsTo(){
        return belongsTo;
    }

    //returns outline of this field
    public Polygon getBorder(){
        return p;
    }

}
