package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {
    private Slot slot1;
    private Slot slot2;

    private Item item1;
    private Item item2;

    @BeforeEach
    public void setUp() {
        item1 = new Item("Basil", "Add this to something", 10);
        item2 = new Item("Dragon Teeth", "TEETH", 50);

        slot1 = new Slot();
        slot2 = new Slot(item1, 2);
    }

    @Test
    public void constructorTest() {
        assertEquals(0, slot1.getAmount());
        assertEquals(item1, slot2.getItem());
        assertEquals(2, slot2.getAmount());
    }

    @Test
    public void setItemTest() {
        slot1.setItem(item1);
        assertEquals(item1, slot1.getItem());
        slot1.setItem(item2);
        assertEquals(item2, slot1.getItem());
    }

    @Test
    public void AmountManipulationTest() {
        slot1.addAmount(3);
        assertEquals(3, slot1.getAmount());
        slot1.addAmount(7);
        assertEquals(10, slot1.getAmount());

        slot1.removeAmount(4);
        assertEquals(6, slot1.getAmount());
        slot1.removeAmount(1);
        assertEquals(5, slot1.getAmount());
    }
}