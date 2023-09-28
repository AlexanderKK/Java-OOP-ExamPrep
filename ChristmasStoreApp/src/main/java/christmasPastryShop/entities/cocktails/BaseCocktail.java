package christmasPastryShop.entities.cocktails;

import christmasPastryShop.entities.cocktails.interfaces.Cocktail;

import static christmasPastryShop.common.ExceptionMessages.*;
import static christmasPastryShop.utils.StringUtils.*;
import static christmasPastryShop.utils.NumberUtils.*;

public abstract class BaseCocktail implements Cocktail {

    private String name;
    private int size;
    private double price;
    private String brand;

    protected BaseCocktail(String name, int size, double price, String brand) {
        setName(name);
        setSize(size);
        setPrice(price);
        setBrand(brand);
    }

    private void setName(String name) {
        if(isNullOrEmpty(name)) {
            throw new IllegalArgumentException(INVALID_NAME);
        }

        this.name = name;
    }

    private void setSize(int size) {
        if(isNegativeOrZero(size)) {
            throw new IllegalArgumentException(INVALID_SIZE);
        }

        this.size = size;
    }

    private void setPrice(double price) {
        if(isNegativeOrZero(price)) {
            throw new IllegalArgumentException(INVALID_PRICE);
        }

        this.price = price;
    }

    private void setBrand(String brand) {
        if(isNullOrEmpty(brand)) {
            throw new IllegalArgumentException(INVALID_BRAND);
        }

        this.brand = brand;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %dml - %.2flv", name, brand, size, price);
    }

}
