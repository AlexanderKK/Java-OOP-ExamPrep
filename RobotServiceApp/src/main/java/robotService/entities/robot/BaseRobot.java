package robotService.entities.robot;

import robotService.common.ConstantMessages;
import robotService.common.ExceptionMessages;

public abstract class BaseRobot implements Robot {

    private String name;
    private String kind;
    private int kilograms;
    private double price;

    public BaseRobot(String name, String kind, int kilograms, double price) {
        setName(name);
        setKind(kind);
        setKilograms(kilograms);
        setPrice(price);
    }

    private void ensureNameAndKind(String input) {
        if(input == null || input.trim().isEmpty()) {
            throw new NullPointerException(String.format(ExceptionMessages.ROBOT_NAME_KIND_CANNOT_BE_NULL_OR_EMPTY, input));
        }
    }

    @Override
    public void setName(String name) {
        ensureNameAndKind(name);

        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setKind(String kind) {
        ensureNameAndKind(kind);

        this.kind = kind;
    }

    public String getKind() {
        return kind;
    }

    public void setKilograms(int kilograms) {
        this.kilograms = kilograms;
    }

    @Override
    public int getKilograms() {
        return kilograms;
    }

    public void setPrice(double price) {
        if(price <= 0) {
            throw new IllegalArgumentException(ExceptionMessages.ROBOT_PRICE_CANNOT_BE_BELOW_OR_EQUAL_TO_ZERO);
        }

        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

}
