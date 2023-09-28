package football.entities.field;

import football.entities.player.Men;

public class NaturalGrass extends BaseField<Men> {

    private static final int CAPACITY = 250;

    public NaturalGrass(String name) {
        super(name, CAPACITY);
    }

}
