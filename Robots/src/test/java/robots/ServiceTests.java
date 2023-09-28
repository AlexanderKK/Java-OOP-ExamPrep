package robots;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ServiceTests {

    private static final String EXPECTED_NAME = "New Service";
    private static final int EXPECTED_CAPACITY = 10;
    private final String NONEXISTENT_ROBOT_NAME = "NonExistingRobot";
    private final String EXISTENT_ROBOT_NAME = "Existent Robot";
    private Service service;

    @Before
    public void setUp() {
        service = new Service(EXPECTED_NAME, EXPECTED_CAPACITY);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowIfNameIsNull() {
        service = new Service(null, EXPECTED_CAPACITY);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowIfNameIsEmpty() {
        service = new Service("", EXPECTED_CAPACITY);
    }

    @Test
    public void testConstructorShouldSetName() {
        String actualName = service.getName();

        assertEquals(EXPECTED_NAME, actualName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorShouldThrowIfCapacityIsLessThan0() {
        String name = "Mike";
        int capacity = -1;

        service = new Service(name, capacity);
    }

    @Test
    public void testConstructorShouldSetCapacity() {
        int actualCapacity = service.getCapacity();

        assertEquals(EXPECTED_CAPACITY, actualCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddShouldThrowIfSizeIsEqualToCapacity() {
        for (int i = 1; i <= EXPECTED_CAPACITY; i++) {
            service.add(new Robot("Robot" + i));
        }

        service.add(new Robot("RobotExceedingCapacityOfCollection"));
    }

    @Test
    public void testAddShouldAddRobotToService() {
        int expectedSize = service.getCount() + 1;

        service.add(new Robot("NewRobot"));

        int actualSize = service.getCount();

        assertEquals(expectedSize, actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveShouldThrowIfRobotNotPresent() {
        service.remove(NONEXISTENT_ROBOT_NAME);
    }

    @Test
    public void testRemoveShouldRemoveRobotByName() {
        service.add(new Robot(EXISTENT_ROBOT_NAME));

        int expectedSize = service.getCount() - 1;

        service.remove(EXISTENT_ROBOT_NAME);

        int actualSize = service.getCount();

        assertEquals(expectedSize, actualSize);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForSaleShouldThrowIfRobotNotPresent() {
        service.forSale(NONEXISTENT_ROBOT_NAME);
    }

    @Test
    public void testForSaleShouldReturnRobotForSaleByName() {
        int expectedSize = service.getCount() + 1;

        service.add(new Robot(EXISTENT_ROBOT_NAME));

        int actualSize = service.getCount();

        assertEquals(expectedSize, actualSize);

        Robot robot = service.forSale(EXISTENT_ROBOT_NAME);

        assertFalse(robot.isReadyForSale());
    }

    @Test
    public void testReportShouldReturnDataForRobotsInService() {
        List<Robot> expectedRobots = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            Robot newRobot = new Robot("Robot" + i);

            service.add(newRobot);
            expectedRobots.add(newRobot);
        }

        String expectedNames  = expectedRobots.stream().map(Robot::getName).collect(Collectors.joining(", "));
        String expectedReport = String.format("The robot %s is in the service %s!", expectedNames, service.getName());

        String actualReport = service.report();

        assertEquals(expectedReport, actualReport);
    }

}
