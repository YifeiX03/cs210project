package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item gaussPChassis;
    private Item gaussPChassis2;
    private Item wrongName;
    private Item wrongDesc;
    private Item wrongVal;
    private Item basketball;
    private Slot slot;
    private HashSet<Item> items;

    @BeforeEach
    public void setUp() {
        gaussPChassis = new Item("Gauss Prime Chassis",
                "The chassis for Gauss prime",
                5000);
        gaussPChassis2 = new Item("Gauss Prime Chassis",
                "The chassis for Gauss prime",
                5000);
        wrongName = new Item("Gauss rime Chassis",
                "The chassis for Gauss prime",
                5000);
        wrongDesc = new Item("Gauss Prime Chassis",
                "The chassis for Gauss rime",
                5000);
        wrongVal = new Item("Gauss Prime Chassis",
                "The chassis for Gauss prime",
                500);
        basketball = new Item ("Basketball", "It's a basket ball", 1);
        items = new HashSet<>();
        items.add(gaussPChassis);
        slot = new Slot(basketball, 1);
    }

    @Test
    public void constructorTest() {
        assertEquals("Gauss Prime Chassis", gaussPChassis.getName());
        assertEquals("The chassis for Gauss prime", gaussPChassis.getDescription());
        assertEquals(5000, gaussPChassis.getValue());
    }

    @Test
    public void equalsTest() {
        assertTrue(gaussPChassis.equals(gaussPChassis));
        assertTrue(gaussPChassis.equals(gaussPChassis2));
        assertFalse(gaussPChassis.equals(wrongName));
        assertFalse(gaussPChassis.equals(wrongDesc));
        assertFalse(gaussPChassis.equals(wrongVal));
        assertFalse(gaussPChassis.equals(null));
        assertFalse(gaussPChassis.equals(basketball));
        assertFalse(gaussPChassis.equals(slot));
    }

    @Test
    public void hashTest() {
        assertTrue(items.contains(gaussPChassis));
        assertTrue(items.contains(gaussPChassis2));
        assertFalse(items.contains(basketball));
    }
}