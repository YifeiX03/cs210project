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
        pear = new Item("pear", "unordinary pear", 100);
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
    public void totalValueTest() {
        assertEquals(0, inventory.getTotalValue());
        inventory.addItem(apple, 1);
        assertEquals(10, inventory.getTotalValue());
        inventory.addItem(apple, 9);
        assertEquals(100, inventory.getTotalValue());
        inventory.addItem(pear, 5);
        assertEquals(5100, inventory.getTotalValue());
    }
}