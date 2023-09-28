package carShop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class CarShopTests {

    private CarShop carShop;
    private Car car;

    @Before
    public void setUp() {
        carShop = new CarShop();
        car = new Car("Hyundai", 120, 30000);
    }

    @Test(expected = NullPointerException.class)
    public void testAddShouldThrowIfCarIsNull() {
        carShop.add(null);
    }

    @Test
    public void testAddShouldAppendCarToCarShop() {
        int expectedCount = carShop.getCount() + 1;

        carShop.add(car);

        int actualCount = carShop.getCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testRemoveShouldRemoveCarFromCarShop() {
        carShop.add(car);

        int expectedCount = carShop.getCount() - 1;

        carShop.remove(car);

        int actualCount = carShop.getCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetCarsShouldReturnUnmodifiableListOfCars() {
        List<Car> expected = List.of(car);

        carShop.add(car);

        List<Car> actual = carShop.getCars();

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void testFindAllCarsWithMaxHorsePowerShouldReturnListOfCars() {
        List<Car> expected = fillWithCars();

        int horsePower = 100;

        expected = expected.stream()
                .filter(c -> c.getHorsePower() > horsePower)
                .collect(Collectors.toList());

        List<Car> actual = carShop.findAllCarsWithMaxHorsePower(horsePower);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllCarByModelShouldReturnListOfCarsByModel() {
        List<Car> expected = fillWithCars();

        String carModel = "Model3";

        expected = expected.stream()
                .filter(c -> c.getModel().equals(carModel))
                .collect(Collectors.toList());

        List<Car> actual = carShop.findAllCarByModel(carModel);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetTheMostLuxuryCarShouldReturnTheCarWithTheHighestPrice() {
        List<Car> cars = fillWithCars();

        Car expected = cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .limit(1)
                .findFirst()
                .orElse(null);


//                .collect(Collectors.toList());

        Car actual = carShop.getTheMostLuxuryCar();

        assertEquals(expected, actual);
    }

    private List<Car> fillWithCars() {
        List<Car> expected = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Car curCar = new Car("Model" + i, 98 + i, 30000 + i);

            expected.add(curCar);
            carShop.add(curCar);
        }

        return expected;
    }
}

