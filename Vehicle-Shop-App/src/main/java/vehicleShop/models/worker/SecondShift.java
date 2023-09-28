package vehicleShop.models.worker;

public class SecondShift extends BaseWorker {

    private static final int INITIAL_STRENGTH = 70;

    public SecondShift(String name) {
        super(name, INITIAL_STRENGTH);
    }

    @Override
    public void working() {
        int strengthDecrease = STRENGTH_DECREASE_FACTOR + 5;
        setStrength(getStrength() - strengthDecrease);

        if(getStrength() < 0) {
            setStrength(0);
        }
    }

}
