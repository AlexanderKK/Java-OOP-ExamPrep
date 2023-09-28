package christmasPastryShop.entities.booths;

import christmasPastryShop.common.ExceptionMessages;
import christmasPastryShop.entities.booths.interfaces.Booth;
import christmasPastryShop.entities.cocktails.interfaces.Cocktail;
import christmasPastryShop.entities.delicacies.interfaces.Delicacy;
import christmasPastryShop.utils.NumberUtils;

import java.util.ArrayList;
import java.util.Collection;

public abstract class BaseBooth implements Booth {

    private Collection<Delicacy> delicacyOrders;
    private Collection<Cocktail> cocktailOrders;
    private int boothNumber;
    private int capacity;
    private int numberOfPeople;
    private double pricePerPerson;
    private boolean isReserved;
    private double price;

    protected BaseBooth(int boothNumber, int capacity, double pricePerPerson) {
        this.boothNumber = boothNumber;
        setCapacity(capacity);
        this.pricePerPerson = pricePerPerson;
        this.delicacyOrders = new ArrayList<>();
        this.cocktailOrders = new ArrayList<>();
    }

    private void setCapacity(int capacity) {
        if(NumberUtils.isNegativeOrZero(capacity)) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_TABLE_CAPACITY);
        }

        this.capacity = capacity;
    }

    @Override
    public void orderDelicacy(Delicacy delicacy) {
        delicacyOrders.add(delicacy);
    }

    @Override
    public void orderCocktail(Cocktail cocktail) {
        cocktailOrders.add(cocktail);
    }

    @Override
    public int getBoothNumber() {
        return boothNumber;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isReserved() {
        return isReserved;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void reserve(int numberOfPeople) {
        if(NumberUtils.isNegativeOrZero(numberOfPeople)) {
            throw new IllegalArgumentException(ExceptionMessages.INVALID_NUMBER_OF_PEOPLE);
        }
        this.numberOfPeople = numberOfPeople;

        this.price = this.numberOfPeople * pricePerPerson;

        isReserved = true;
    }

    @Override
    public double getBill() {
        double totalPrice =
                delicacyOrders.stream().mapToDouble(Delicacy::getPrice).sum() +
                cocktailOrders.stream().mapToDouble(Cocktail::getPrice).sum() +
                getPrice();

        return totalPrice;
    }

    @Override
    public void clear() {
        this.isReserved = false;
        this.delicacyOrders.clear();
        this.cocktailOrders.clear();
        this.numberOfPeople = 0;
        this.price = 0;
    }

}
