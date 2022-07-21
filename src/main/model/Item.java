package model;

// represents an item having a name, description, value
// once created, items will not be modified, all item pointers referencing the same item will point to that item
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
}
