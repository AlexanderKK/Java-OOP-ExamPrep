package carShop;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CarShopTests {

    private static final String MODEL = "Hyundai";
    private static final int HORSEPOWER = 150;
    private static final double PRICE = 50000;
    private CarShop carShop;
    private Car car;

    @Before
    public void setUp()  {
        carShop = new CarShop();
        car = new Car(MODEL, HORSEPOWER, PRICE);
    }

    @Test(expected = NullPointerException.class)
    public void testAddCarShouldThrowIfCarIsNull() {
        carShop.add(null);
    }

    @Test
    public void testAddShouldAddCarToCarShop() {
        assertEquals(0, carShop.getCount());

        int expected = carShop.getCount() + 1;

        carShop.add(car);

        int actual = carShop.getCount();

        assertEquals(expected, actual);
    }

    @Test
    public void testRemoveShouldRemoveCarAndReturnIfCanWasRemovedOrNot() {
        assertFalse(carShop.remove(car));

        carShop.add(car);
        int expectedCount = carShop.getCount() - 1;
        assertTrue(carShop.remove(car));
        int actualCount = carShop.getCount();

        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testFindAllCarByModelShouldReturnListOfCarsWithSpecificModel() {
        List<Car> none = carShop.findAllCarByModel(MODEL);
        assertEquals(0, none.size());

        List<Car> expected = addCars();

        expected = expected.stream().filter(c -> c.getModel().equals(MODEL)).collect(Collectors.toList());
        List<Car> actual = carShop.findAllCarByModel(MODEL);

        assertEquals(expected, actual);
    }

    @Test
    public void testFindAllCarsWithMaxHorsePower() {
        List<Car> expected = addCars();

        expected = expected.stream().filter(c -> c.getHorsePower() > HORSEPOWER).collect(Collectors.toList());
        List<Car> actual = carShop.findAllCarsWithMaxHorsePower(HORSEPOWER);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetTheMostLuxuryCar() {
        List<Car> cars = addCars();

        Car expected = cars.stream()
                .sorted(Comparator.comparingDouble(Car::getPrice).reversed())
                .limit(1)
                .findFirst()
                .orElse(null);

        Car actual = carShop.getTheMostLuxuryCar();

        assertEquals(expected, actual);
    }

    private List<Car> addCars() {
        List<Car> expected = new ArrayList<>();

        expected.add(car);
        carShop.add(car);
        for (int i = 1; i <= 5; i++) {
            Car car = new Car("Model" + i, 10 + i, 10000 + i);

            expected.add(car);
            carShop.add(car);
        }

        return expected;
    }

}

