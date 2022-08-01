package persistence;

import model.Inventory;
import model.Item;
import model.Slot;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// based off of JsonWriterTest from JSON serialization Demo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonWriterTest extends JsonTest{
    private Item ball = new Item(ballName, ballDesc, ballVal);
    private Item cube = new Item(cubeName, cubeDesc, cubeVal);

    @Test
    void fileNotFoundTest() {
        JsonWriter writer = new JsonWriter("./data/funnier\0File.json");
        try {
            writer.open();
            fail("Should've thrown IOException");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void inventoryWriteTestEmpty() {
        try {
            Inventory inventory = new Inventory("test");
            JsonWriter writer = new JsonWriter("./data/inventoryWriteTestEmpty.json");
            writer.open();
            writer.write(inventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/inventoryWriteTestEmpty.json");
            inventory = reader.read();
            assertEquals("test", inventory.getName());
            assertEquals(0, inventory.length());
            assertEquals(0, inventory.getCoins());
        } catch (IOException e) {
            fail("Expected no exceptions thrown");
        }
    }

    @Test
    void inventoryWriteTest() {
        try {
            Inventory inventory = new Inventory("test");
            inventory.addCoins(12);
            inventory.addItem(ball, ballAmount);
            inventory.addItem(cube, cubeAmount);
            JsonWriter writer = new JsonWriter("./data/inventoryWriteTest.json");
            writer.open();
            writer.write(inventory);
            writer.close();

            JsonReader reader = new JsonReader("./data/inventoryWriteTest.json");
            inventory = reader.read();
            assertEquals("test", inventory.getName());
            assertEquals(2, inventory.length());
            assertEquals(12, inventory.getCoins());
            ArrayList<Slot> slots = inventory.getSlots();
            checkSlot(slots.get(0), ballAmount, ballName, ballDesc, ballVal);
            checkSlot(slots.get(1), cubeAmount, cubeName, cubeDesc, cubeVal);
        } catch (IOException e) {
            fail("Expected no exceptions thrown");
        }
    }
}
