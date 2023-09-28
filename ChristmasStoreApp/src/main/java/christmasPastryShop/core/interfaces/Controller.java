package christmasPastryShop.core.interfaces;

public interface Controller {

   String addDelicacy(String type, String name, double price);

   String orderDelicacy(String name, int boothNumber);

   String addCocktail(String type, String name, int size, String brand);

   String orderCocktail(String name, int boothNumber);

   String addBooth(String type, int tableNumber, int capacity);

   String reserveBooth(int numberOfPeople);

   String leaveBooth(int tableNumber);

   String getIncome();

}
