package archeologicalExcavations;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExcavationTests {

    private Excavation excavation;
    private Archaeologist archaeologist;

    @Before
    public void setUp() {
        String nameExcavation = "Excave";
        int capacityExcavation = 5;
        excavation = new Excavation(nameExcavation, capacityExcavation);

        String nameArchaeologist = "Arch";
        double energyArchaeologist = 7.75;
        archaeologist = new Archaeologist(nameArchaeologist, energyArchaeologist);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowIfNameIsNull() {
        new Excavation(null, 3);
    }

    @Test(expected = NullPointerException.class)
    public void testConstructorShouldThrowIfNameIsEmptyOrContainsWhiteSpace() {
        new Excavation("  ", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorShouldThrowIfCapacityIsLessThanZero() {
        new Excavation("Dinosaur", -1);
    }

    @Test
    public void testConstructorShouldSetCorrectStateOfNameAndCapacity() {
        Excavation test = new Excavation("Cave", 5);
        assertEquals("Cave", test.getName());
        assertEquals(5, test.getCapacity());
    }

    @Test
    public void testGetNameShouldReturnName() {
        String actual = excavation.getName();
        assertEquals("Excave" ,actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddArchaeologistShouldThrowIfCapacityIsReached() {
        Excavation excavationTest = new Excavation("Test Capacity Overflow", 1);

        excavationTest.addArchaeologist(archaeologist);
        excavationTest.addArchaeologist(new Archaeologist("Additional", 15));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddArchaeologistShouldThrowIfArchaeologistExists() {
        Excavation excavationTest = new Excavation("TestSameArchaeologist", 10);

        excavationTest.addArchaeologist(archaeologist);
        excavationTest.addArchaeologist(archaeologist);
    }

    @Test
    public void testAddArchaeologistShouldAddCorrectArchaeologist() {
        int expected = excavation.getCount() + 1;
        excavation.addArchaeologist(archaeologist);
        int actual = excavation.getCount();

        assertEquals(expected, actual);

        excavation.addArchaeologist(new Archaeologist("New", 34));

        assertEquals(expected + 1, actual + 1);
    }

    @Test
    public void testRemoveArchaeologistShouldReturnFalseIfArchaeologistByNameNotPresent() {
        boolean removed = excavation.removeArchaeologist("Non-Existent");
        assertFalse(removed);
    }

    @Test
    public void testRemoveArchaeologistShouldReturnTrueIfArchaeologistByNameIsRemoved() {
        excavation.addArchaeologist(archaeologist);

        int expectedCount = excavation.getCount() - 1;
        boolean removed = excavation.removeArchaeologist(archaeologist.getName());
        int actualCount = excavation.getCount();

        assertEquals(expectedCount, actualCount);

        assertTrue(removed);
    }

}
