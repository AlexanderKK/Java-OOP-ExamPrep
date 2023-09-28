package vehicleShop.models.worker;

public class SecondShift extends BaseWorker {

    private static final int STRENGTH = 70;
    private static final int STRENGTH_DECREASE_FACTOR = 5;

    public SecondShift(String name) {
        super(name, STRENGTH);
    }

    @Override
    public void working() {
        super.working();

        int newStrength = super.getStrength() - STRENGTH_DECREASE_FACTOR;
        if(newStrength < 0) {
            newStrength = 0;
        }

        super.setStrength(newStrength);
    }

}
