package vehicleShop.models.tool;

import vehicleShop.common.ExceptionMessages;

public class ToolImpl implements Tool {

    private static final int POWER_DECREASE_FACTOR = 5;
    private int power;

    public ToolImpl(int power) {
        setPower(power);
    }

    private void setPower(int power) {
        if(power < 0) {
            throw new IllegalArgumentException(ExceptionMessages.TOOL_POWER_LESS_THAN_ZERO);
        }

        this.power = power;
    }

    @Override
    public int getPower() {
        return power;
    }

    @Override
    public void decreasesPower() {
        power -= POWER_DECREASE_FACTOR;

        if(power < 0) {
            power = 0;
        }
    }

    @Override
    public boolean isUnfit() {
        return power == 0;
    }

}
