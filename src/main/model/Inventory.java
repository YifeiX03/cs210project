package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

//represents an inventory, an arraylist of slots and a count of total amount of coins

// toJson() method based off of JSON Serialization Demo; Link below
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class Inventory {
    private String name;
    private ArrayList<Slot> slots;
    private int coins;

    //EFFECTS: creates an empty inventory with no coins
    public Inventory(String name) {
        this.name = name;
        slots = new ArrayList<Slot>();
        coins = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds given number of an item to the inventory
    public void addItem(Item item, int count) {
        if (contains(item)) {
            findItem(item).addAmount(count);
        } else {
            slots.add(new Slot(item, count));
        }
    }

    //REQUIRES: item must be an item that exists in inventory
    //          count must be less than or equal to amount of that item stored
    //MODIFIES: this
    //EFFECTS: removes given number of items from the inventory
    //         if the amount of an item is 0, removes slot from inventory
    public void removeItem(Item item, int count) {
        if (contains(item)) {
            findItem(item).removeAmount(count);
            if (findItem(item).getAmount() == 0) {
                slots.remove(findIndex(item));
            }
        }
    }

    //EFFECTS: returns amount of slots in inventory
    public int length() {
        return slots.size();
    }

    //EFFECTS: returns true if inventory contains item, else false
    public boolean contains(Item item) {
        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                return true;
            }
        }
        return false;
    }

    //EFFECTS: returns the amount of given item in inventory
    public int getItemCount(Item item) {
        return findItem(item).getAmount();
    }

    //EFFECT: returns total value of inventory
    public int getTotalValue() {
        int total = 0;
        for (Slot slot : slots) {
            total += slot.getItem().getValue() * slot.getAmount();
        }
        return total;
    }

    //EFFECT: returns total amount of items in inventory
    public int getTotalItems() {
        int total = 0;
        for (Slot slot : slots) {
            total += slot.getAmount();
        }
        return total;
    }

    //EFFECTS: returns the slot that contains the given item
    //         if item is not found return null
    public Slot findItem(Item item) {
        for (Slot slot : slots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }
        return null;
    }

    //EFFECTS: returns the index of the slot that contains the given item
    //         if item is not found return -1
    public int findIndex(Item item) {
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    //REQUIRES: amount must be positive
    //MODIFIES: this
    //EFFECTS: adds given amount of coins
    public void addCoins(int amount) {
        coins += amount;
    }

    //REQUIRES: amount must be positive
    //MODIFIES: this
    //EFFECTS: removes given amount of coins
    public void removeCoins(int amount) {
        coins -= amount;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public int getCoins() {
        return coins;
    }

    // EFFECTS: returns this inventory as a JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("slots", slotsToJson());
        json.put("coins", coins);
        return json;
    }

    // EFFECTS: returns slots as a JSON array
    private JSONArray slotsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Slot slot : slots) {
            jsonArray.put(slot.toJson());
        }

        return jsonArray;
    }
}
