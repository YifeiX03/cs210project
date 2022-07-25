package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;

    private Item apple;
    private Item pear;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        apple = new Item("apple", "ordinary apple", 10);
        pear = new Item("pear", "unordinary pear", 1000);
    }

    @Test
    public void constructorTest() {
        assertEquals(0, inventory.length());
        assertEquals(0, inventory.getCoins());
    }

    @Test
    public void addItemTest() {
        inventory.addItem(apple, 1);
        assertTrue(inventory.contains(apple));
        assertEquals(1, inventory.length());

        inventory.addItem(pear, 1);
        assertTrue(inventory.contains(apple));
        assertTrue(inventory.contains(pear));
        assertEquals(2, inventory.length());
    }

    @Test
    public void addMultipleItemsTest() {
        inventory.addItem(apple, 4);
        assertTrue(inventory.contains(apple));
        assertEquals(1, inventory.length());
        assertEquals(4, inventory.getItemCount(apple));

        inventory.addItem(apple, 6);
        assertTrue(inventory.contains(apple));
        assertEquals(1, inventory.length());
        assertEquals(10, inventory.getItemCount(apple));

        inventory.addItem(pear, 7);
        assertTrue(inventory.contains(apple));
        assertTrue(inventory.contains(pear));
        assertEquals(2, inventory.length());
        assertEquals(10, inventory.getItemCount(apple));
        assertEquals(7, inventory.getItemCount(pear));
    }

    @Test
    public void removeItemTest() {
        inventory.addItem(apple, 1);
        inventory.removeItem(apple, 1);
        assertEquals(0, inventory.length());

        inventory.addItem(apple, 1);
        inventory.addItem(pear, 1);
        inventory.removeItem(pear, 1);
        assertEquals(1, inventory.length());
        assertTrue(inventory.contains(apple));
        inventory.removeItem(apple, 1);
        assertEquals(0, inventory.length());
    }

    @Test
    public void removeMultipleItemsTest() {
        inventory.addItem(apple, 15);
        inventory.removeItem(apple, 12);
        assertEquals(1, inventory.length());
        assertTrue(inventory.contains(apple));
        assertEquals(3, inventory.getItemCount(apple));

        inventory.addItem(pear, 9);
        assertEquals(2, inventory.length());
        assertTrue(inventory.contains(apple));
        assertEquals(3, inventory.getItemCount(apple));
        assertTrue(inventory.contains(pear));
        inventory.removeItem(pear, 7);
        assertEquals(2, inventory.length());
        assertTrue(inventory.contains(apple));
        assertEquals(3, inventory.getItemCount(apple));
        assertTrue(inventory.contains(pear));
        assertEquals(2, inventory.getItemCount(pear));
    }

    @Test
    public void removeNonExistItemTest() {
        inventory.addItem(apple, 12);
        inventory.removeItem(pear, 7);
        assertEquals(1, inventory.length());
        assertTrue(inventory.contains(apple));
        assertEquals(12, inventory.getItemCount(apple));
    }

    @Test
    public void findNonExistItemTest() {
        inventory.addItem(apple, 12);
        assertEquals(inventory.getSlots().get(0), inventory.findItem(apple));
        assertEquals(0, inventory.findIndex(apple));
        assertEquals(null, inventory.findItem(pear));
        assertEquals(-1, inventory.findIndex(pear));
    }

    @Test
    public void totalValueTest() {
        assertEquals(0, inventory.getTotalValue());
        inventory.addItem(apple, 1);
        assertEquals(10, inventory.getTotalValue());
        inventory.addItem(apple, 9);
        assertEquals(100, inventory.getTotalValue());
        inventory.addItem(pear, 5);
        assertEquals(5100, inventory.getTotalValue());
        inventory.removeItem(apple, 1);
        assertEquals(5090, inventory.getTotalValue());
    }

    @Test
    public void totalItemsTest() {
        assertEquals(0, inventory.getTotalItems());
        inventory.addItem(apple, 1);
        assertEquals(1, inventory.getTotalItems());
        inventory.addItem(apple, 3);
        assertEquals(4, inventory.getTotalItems());
        inventory.addItem(pear, 1);
        assertEquals(5, inventory.getTotalItems());
        inventory.removeItem(apple, 4);
        assertEquals(1, inventory.getTotalItems());
    }

    @Test
    public void coinsTest() {
        assertEquals(0, inventory.getCoins());
        inventory.addCoins(10);
        assertEquals(10, inventory.getCoins());
        inventory.addCoins(5);
        assertEquals(15, inventory.getCoins());
        inventory.removeCoins(7);
        assertEquals(8, inventory.getCoins());
        inventory.removeCoins(2);
        assertEquals(6, inventory.getCoins());
    }
}