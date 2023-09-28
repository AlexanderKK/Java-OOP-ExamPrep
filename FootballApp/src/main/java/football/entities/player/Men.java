package football.entities.player;

public class Men extends BasePlayer {

    private static final double KG = 85.50;
    private static final int STRENGTH_INCREASE_FACTOR = 145;

    public Men(String name, String nationality, int strength) {
        super(name, nationality, KG, strength);
    }

    @Override
    public void stimulation() {
        super.setStrength(super.getStrength() + STRENGTH_INCREASE_FACTOR);
    }

}
