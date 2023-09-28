package christmasPastryShop.entities.delicacies;

import christmasPastryShop.entities.delicacies.interfaces.Delicacy;
import christmasPastryShop.utils.NumberUtils;
import christmasPastryShop.utils.StringUtils;
import static christmasPastryShop.common.ExceptionMessages.*;

public abstract class BaseDelicacy implements Delicacy {

    private String name;
    private double portion;
    private double price;

    protected BaseDelicacy(String name, double portion, double price) {
        setName(name);
        setPortion(portion);
        setPrice(price);
    }

    private void setName(String name) {
        if(StringUtils.isNullOrEmpty(name)) {
            throw new IllegalArgumentException(INVALID_NAME);
        }

        this.name = name;
    }

    private void setPortion(double portion) {
        if(NumberUtils.isNegativeOrZero(portion)) {
            throw new IllegalArgumentException(INVALID_PORTION);
        }

        this.portion = portion;
    }

    private void setPrice(double price) {
        if(NumberUtils.isNegativeOrZero(price)) {
            throw new IllegalArgumentException(INVALID_PRICE);
        }

        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPortion() {
        return portion;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s: ", name))
                .append(String.format("%.2fg - ", portion))
                .append(String.format("%.2f", price));

        return sb.toString();
    }

}
