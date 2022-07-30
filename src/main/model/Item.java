package model;

import org.json.JSONObject;

// represents an item having a name, description, value
// once created, items will not be modified, all item pointers referencing the same item will point to that item

// toJson() method based off of JSON Serialization Demo; Link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class Item {
    private String name;
    private String description;
    private int value;

    //REQUIRES: value must be positive
    //EFFECTS: creates an item with given name, description, value
    public Item(String name, String desc, int value) {
        this.name = name;
        this.description = desc;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getValue() {
        return value;
    }

    // EFFECTS: returns this item as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("description", description);
        json.put("value", value);
        return json;
    }
}
