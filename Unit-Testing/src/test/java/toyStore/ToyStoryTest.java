package toyStore;

import org.junit.Before;
import org.junit.Test;

import javax.naming.OperationNotSupportedException;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;

public class ToyStoryTest {
    private ToyStore toyStore;
    private Toy toy;

    @Before
    public void setUp() {
        toyStore = new ToyStore();
        toy = new Toy("RayaToys", "12DE8L3");
    }

    @Test
    public void testConstructorShouldCreateToyStoreWithCorrectState() {
        int expectedSize = 7;
        int actualSize = toyStore.getToyShelf().size();

        assertEquals(expectedSize, actualSize);
    }

    //Add toy
    @Test(expected = IllegalArgumentException.class)
    public void testAddToyShouldThrowIfShelfNotPresent() throws OperationNotSupportedException {
        toyStore.addToy("None", toy);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToyShouldThrowIfThereIsToyOnShelf() throws OperationNotSupportedException {
        toyStore.addToy("A", toy);
        toyStore.addToy("A", new Toy("Jumbo", "A1D"));
    }

    @Test(expected = OperationNotSupportedException.class)
    public void testAddToyShouldThrowIfSameToyExistsOnShelf() throws OperationNotSupportedException {
        toyStore.addToy("A", toy);
        toyStore.addToy("A", toy);
    }

    @Test
    public void testAddToyShouldReturnMessageForSuccessIfToyAddedCorrectlyToShelf() throws OperationNotSupportedException {
        int expectedSize = getToysCount(toyStore) + 1;

        String actualMessage = toyStore.addToy("A", toy);

        int actualSize = getToysCount(toyStore);

        assertEquals(expectedSize, actualSize);

        String expectedToyId = toy.getToyId();
        String actualToyId = checkForIdOfToyInToyStore("A", toy);

        assertEquals(expectedToyId, actualToyId);

        String expectedMessage = String.format("Toy:%s placed successfully!", expectedToyId);

        assertEquals(expectedMessage, actualMessage);
    }

    //Remove toy
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveToyShouldThrowIfShelfNotPresent() {
        toyStore.removeToy("None", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveToyShouldThrowIfToyNotPresent() {
        toyStore.removeToy("A", toy);
    }

    @Test
    public void testRemoveToyShouldReturnMessageForSuccessIfToyRemovedSuccessfullyFromShelf() throws OperationNotSupportedException {
        toyStore.addToy("B", toy);

        int expectedSize = getToysCount(toyStore) - 1;

        String actualToyId = checkForIdOfToyInToyStore("B", toy);
        String actualMsg = toyStore.removeToy("B" , toy);

        int actualSize = getToysCount(toyStore);

        assertEquals(expectedSize, actualSize);

        String expectedToyId = toy.getToyId();

        assertEquals(expectedToyId, actualToyId);

        String expectedMsg = String.format("Remove toy:%s successfully!", toy.getToyId());

        assertEquals(expectedMsg, actualMsg);
    }

    private String checkForIdOfToyInToyStore(String shelf, Toy toy) {
        Toy currentToy = toyStore.getToyShelf()
                .entrySet()
                .stream()
                .filter(e -> e.getKey().equals(shelf) && e.getValue().getToyId().equals(toy.getToyId()))
                .map(Map.Entry::getValue)
                .findAny()
                .orElse(null);

        assertNotNull(currentToy);

        return currentToy.getToyId();
    }

    private int getToysCount(ToyStore toyStore) {
        assertNotNull(toyStore);

        return toyStore.getToyShelf()
                .values()
                .stream()
                .filter(Objects::nonNull)
                .mapToInt(e -> 1)
                .sum();
    }

}
