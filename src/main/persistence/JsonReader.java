package persistence;

import model.Inventory;
import model.Slot;
import model.Item;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// represents a reader that reads inventory and made items from JSON data from file
// based off of JSON serialization Demo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader with given source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int coins = jsonObject.getInt("coins");
        Inventory inventory = new Inventory(name);
        inventory.addCoins(coins);
        addSlots(inventory, jsonObject);
        return inventory;
    }

    // MODIFIES: inventory
    // EFFECTS: parses slots from JSON object and adds them to inventory
    private void addSlots(Inventory inventory, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("slots");
        for (Object json : jsonArray) {
            JSONObject nextSlot = (JSONObject) json;
            int amount = nextSlot.getInt("amount");
            Item item = getItem(nextSlot.getJSONObject("item"));
            inventory.addItem(item, amount);
        }
    }

    // EFFECTS: parses items from JSON object and returns it
    private Item getItem(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String description = jsonObject.getString("description");
        int value = jsonObject.getInt("value");
        Item item = new Item(name, description, value);
        return item;
    }
}
