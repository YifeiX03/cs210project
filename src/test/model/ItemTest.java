package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    private Item gaussPChassis;

    @BeforeEach
    public void setUp() {
        gaussPChassis = new Item("Gauss Prime Chassis",
                "The chassis for Gauss prime",
                5000);
    }

    @Test
    public void constructorTest() {
        assertEquals("Gauss Prime Chassis", gaussPChassis.getName());
        assertEquals("The chassis for Gauss prime", gaussPChassis.getDescription());
        assertEquals(5000, gaussPChassis.getValue());
    }
}