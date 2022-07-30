package persistence;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import model.Inventory;
import model.Slot;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// based off of JSON serialization Demo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReaderTest extends JsonTest{

    @Test
    void fileNotFoundTest() {
        JsonReader reader = new JsonReader("./data/funnyFile.json");
        try {
            Inventory inventory = reader.read();
            fail("Should've thrown IOException");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    void inventoryReadTestEmpty() {
        JsonReader reader = new JsonReader("./data/inventoryReadTestEmpty.json");
        try {
            Inventory inventory = reader.read();
            assertEquals("test", inventory.getName());
            assertEquals(0, inventory.length());
            assertEquals(0, inventory.getCoins());
        } catch (IOException e) {
            fail("Could not read file");
        }
    }

    @Test
    void inventoryReadTest() {
        JsonReader reader = new JsonReader("./data/inventoryReadTest.json");
        try {
            Inventory inventory = reader.read();
            assertEquals("test", inventory.getName());
            assertEquals(2, inventory.length());
            assertEquals(12, inventory.getCoins());
            ArrayList<Slot> slots = inventory.getSlots();
            checkSlot(slots.get(0), ballAmount, ballName, ballDesc, ballVal);
            checkSlot(slots.get(1), cubeAmount, cubeName, cubeDesc, cubeVal);
        } catch (IOException e) {
            fail("Could not read file");
        }
    }
}
