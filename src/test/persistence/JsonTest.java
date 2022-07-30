package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

// based off of JSON serialization Demo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonTest {
    protected String ballName = "Ball";
    protected String ballDesc = "Bouncy";
    protected int ballVal = 2;
    protected int ballAmount = 20;

    protected String cubeName = "Cube";
    protected String cubeDesc = "CUBE-Y";
    protected int cubeVal = 10;
    protected int cubeAmount = 45;

    // EFFECTS: compares slot and item in slot with expected item (ball) and amount
    protected void checkSlot(Slot slot, int amount, String name, String desc, int val) {
        Item item = slot.getItem();
        assertEquals(amount, slot.getAmount());
        assertEquals(name, item.getName());
        assertEquals(desc, item.getDescription());
        assertEquals(val, item.getValue());
    }

}
