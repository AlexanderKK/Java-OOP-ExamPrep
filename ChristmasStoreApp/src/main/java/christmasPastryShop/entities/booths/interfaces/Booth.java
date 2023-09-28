package christmasPastryShop.entities.booths.interfaces;

import christmasPastryShop.entities.cocktails.interfaces.Cocktail;
import christmasPastryShop.entities.delicacies.interfaces.Delicacy;

public interface Booth {
    int getBoothNumber();

    int getCapacity();

    void orderDelicacy(Delicacy food);

    void orderCocktail(Cocktail cocktail);

    boolean isReserved();

    double getPrice();

    void reserve(int numberOfPeople);

    double getBill();

    void clear();
}
