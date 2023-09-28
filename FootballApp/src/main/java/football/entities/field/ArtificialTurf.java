package football.entities.field;

import football.entities.player.Women;

public class ArtificialTurf extends BaseField<Women> {

    private static final int CAPACITY = 150;

    public ArtificialTurf(String name) {
        super(name, CAPACITY);
    }

}
