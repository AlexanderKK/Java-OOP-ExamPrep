package vehicleShop.models.vehicle;

import vehicleShop.common.ExceptionMessages;
import static vehicleShop.utils.StringUtil.*;

public class VehicleImpl implements Vehicle {

    private static final int STRENGTH_DECREASE_FACTOR = 5;
    private String name;
    private int strengthRequired;

    public VehicleImpl(String name, int strengthRequired) {
        setName(name);
        setStrengthRequired(strengthRequired);
    }

    private void setName(String name) {
        if(isNullOrEmpty(name)) {
            throw new IllegalArgumentException(ExceptionMessages.VEHICLE_NAME_NULL_OR_EMPTY);
        }

        this.name = name;
    }

    private void setStrengthRequired(int strengthRequired) {
        if(strengthRequired < 0) {
            throw new IllegalArgumentException(ExceptionMessages.VEHICLE_STRENGTH_LESS_THAN_ZERO);
        }

        this.strengthRequired = strengthRequired;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getStrengthRequired() {
        return strengthRequired;
    }

    @Override
    public void making() {
        strengthRequired -= STRENGTH_DECREASE_FACTOR;

        if(strengthRequired < 0) {
            strengthRequired = 0;
        }
    }

    @Override
    public boolean reached() {
        return strengthRequired == 0;
    }

}
