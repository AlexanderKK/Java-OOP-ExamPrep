package christmasPastryShop.core;

import christmasPastryShop.common.enums.BoothType;
import christmasPastryShop.common.enums.CocktailType;
import christmasPastryShop.common.enums.DelicacyType;
import christmasPastryShop.core.interfaces.Controller;
import christmasPastryShop.entities.booths.OpenBooth;
import christmasPastryShop.entities.booths.PrivateBooth;
import christmasPastryShop.entities.cocktails.Hibernation;
import christmasPastryShop.entities.cocktails.MulledWine;
import christmasPastryShop.entities.cocktails.interfaces.Cocktail;
import christmasPastryShop.entities.delicacies.Gingerbread;
import christmasPastryShop.entities.delicacies.Stolen;
import christmasPastryShop.entities.delicacies.interfaces.Delicacy;
import christmasPastryShop.entities.booths.interfaces.Booth;
import christmasPastryShop.repositories.interfaces.BoothRepository;
import christmasPastryShop.repositories.interfaces.CocktailRepository;
import christmasPastryShop.repositories.interfaces.DelicacyRepository;

import static christmasPastryShop.common.ExceptionMessages.*;
import static christmasPastryShop.common.OutputMessages.*;

public class ControllerImpl implements Controller {

    private DelicacyRepository<Delicacy> delicacyRepository;
    private CocktailRepository<Cocktail> cocktailRepository;
    private BoothRepository<Booth> boothRepository;
    private double income;

    public ControllerImpl(DelicacyRepository<Delicacy> delicacyRepository, CocktailRepository<Cocktail> cocktailRepository, BoothRepository<Booth> boothRepository) {
        this.delicacyRepository = delicacyRepository;
        this.cocktailRepository = cocktailRepository;
        this.boothRepository = boothRepository;
    }

    @Override
    public String addDelicacy(String type, String name, double price) {
        Delicacy delicacy = delicacyRepository.getByName(name);
        if(delicacy != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }

        DelicacyType delicacyType;
        try {
            delicacyType = DelicacyType.valueOf(type);
        } catch(NullPointerException | IllegalArgumentException ignored) {
            throw new RuntimeException(INVALID_DELICACY_TYPE);
        }

        switch(delicacyType) {
            case Gingerbread:
                delicacy = new Gingerbread(name, price);
                break;
            case Stolen:
                delicacy = new Stolen(name, price);
                break;
            default:
                throw new IllegalArgumentException(INVALID_DELICACY_TYPE);
        }

        delicacyRepository.add(delicacy);

        return String.format(DELICACY_ADDED, name, type);
    }

    @Override
    public String addCocktail(String type, String name, int size, String brand) {
        Cocktail cocktail = cocktailRepository.getByName(name);
        if(cocktail != null) {
            throw new IllegalArgumentException(String.format(FOOD_OR_DRINK_EXIST, type, name));
        }

        CocktailType cocktailType;
        try {
            cocktailType = CocktailType.valueOf(type);
        } catch(NullPointerException | IllegalArgumentException ignored) {
            throw new RuntimeException(INVALID_COCKTAIL_TYPE);
        }

        switch(cocktailType) {
            case MulledWine:
                cocktail = new MulledWine(name, size, brand);
                break;
            case Hibernation:
                cocktail = new Hibernation(name, size, brand);
                break;
            default:
                throw new IllegalArgumentException(INVALID_COCKTAIL_TYPE);
        }

        cocktailRepository.add(cocktail);

        return String.format(COCKTAIL_ADDED, name, brand);
    }

    @Override
    public String orderDelicacy(String name, int boothNumber) {
        Delicacy delicacy = delicacyRepository.getByName(name);

        if(delicacy == null) {
            throw new IllegalArgumentException(String.format(DELICACY_NOT_PRESENT, name));
        }

        Booth booth = boothRepository.getByNumber(boothNumber);
        if(booth == null || !booth.isReserved()) {
            throw new IllegalArgumentException(String.format(BOOTH_NOT_PRESENT_RESERVED, boothNumber));
        }

        booth.orderDelicacy(delicacy);

        return String.format(DELICACY_ADDED_TO_BOOTH, name, boothNumber);
    }

    @Override
    public String orderCocktail(String name, int boothNumber) {
        Cocktail cocktail = cocktailRepository.getByName(name);

        if(cocktail == null) {
            throw new IllegalArgumentException(String.format(COCKTAIL_NOT_PRESENT, name));
        }

        Booth booth = boothRepository.getByNumber(boothNumber);
        if(booth == null || !booth.isReserved()) {
            throw new IllegalArgumentException(String.format(BOOTH_NOT_PRESENT_RESERVED, boothNumber));
        }

        booth.orderCocktail(cocktail);

        return String.format(COCKTAIL_ADDED_TO_BOOTH, name, boothNumber);
    }

    @Override
    public String addBooth(String type, int boothNumber, int capacity) {
        Booth booth = boothRepository.getByNumber(boothNumber);
        if(booth != null) {
            throw new IllegalArgumentException(String.format(BOOTH_EXIST, boothNumber));
        }

        BoothType boothType;
        try {
            boothType = BoothType.valueOf(type);
        } catch(NullPointerException | IllegalArgumentException ignored) {
            throw new RuntimeException(INVALID_BOOTH_TYPE);
        }

        switch(boothType) {
            case OpenBooth:
                booth = new OpenBooth(boothNumber, capacity);
                break;
            case PrivateBooth:
                booth = new PrivateBooth(boothNumber, capacity);
                break;
            default:
                throw new IllegalArgumentException(INVALID_BOOTH_TYPE);
        }

        boothRepository.add(booth);

        return String.format(BOOTH_ADDED, boothNumber);
    }

    @Override
    public String reserveBooth(int numberOfPeople) {
        Booth notReservedBooth = boothRepository.getAll().stream()
                .filter(b -> !b.isReserved() && b.getCapacity() >= numberOfPeople)
                .findFirst()
                .orElse(null);

        if(notReservedBooth == null) {
            return String.format(RESERVATION_NOT_POSSIBLE, numberOfPeople);
        }

        notReservedBooth.reserve(numberOfPeople);

        return String.format(BOOTH_RESERVED, notReservedBooth.getBoothNumber(), numberOfPeople);
    }

    @Override
    public String leaveBooth(int boothNumber) {
        Booth boothToBeCleared = boothRepository.getByNumber(boothNumber);
        if(boothToBeCleared == null || !boothToBeCleared.isReserved()) {
            throw new IllegalArgumentException(String.format(BOOTH_NOT_PRESENT_RESERVED, boothNumber));
        }

        double bill = boothToBeCleared.getBill();

        boothToBeCleared.clear();

        income += bill;

        return String.format(BILL, boothNumber, bill);
    }

    @Override
    public String getIncome() {
        return String.format(TOTAL_INCOME, income);
    }

}
