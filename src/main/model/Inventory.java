package model;

import java.util.ArrayList;

//represents an inventory, an arraylist of slots and a count of total amount of coins
public class Inventory {
    private ArrayList<Slot> slots;
    private int coins;

    //EFFECTS: creates an empty inventory with no coins
    public Inventory() {
        slots = new ArrayList<Slot>();
        coins = 0;
    }

    //MODIFIES: this
    //EFFECTS: adds given number of an item to the inventory
    public void addItem(Item item, int count) {
        //stub
    }

    //REQUIRES: item must be an item that exists in inventory
    //          count must be less than or equal to amount of that item stored
    //MODIFIES: this
    //EFFECTS: removes given number of items from the inventory
    //         if the amount of an item is 0, removes slot from inventory
    public void removeItem(Item item, int count) {
        //stub
    }

//    //REQUIRES: item must be an item that exists in inventory
//    //          count must be less than or equal to amount of that item stored
//    //MODIFIES: this
//    //EFFECTS: selects given amount of an item from inventory
//    public void select(Item item, int count) {
//        //stub
//    }
//
//    //EFFECTS: returns the collective value of all selected items
//    public int inspectSelectedValue() {
//        return 0;
//    }
//
//    //EFFECTS: deselects all items selected
//    public void deselectAll() {
//        //stub
//    }

    //EFFECTS: returns amount of slots in inventory
    public int length() {
        return slots.size();
    }

//    //EFFECTS: returns total amount of every item in inventory
//    public int inventorySize() {
//        return 0;
//    }

//    //REQUIRES: slot number is less than inventory size
//    //EFFECTS: returns the item at the slot number
//    public Item getItemFromSlot(int position) {
//        return new Item("Test", "TESTING", 0);
//    }

    //EFFECTS: returns true if inventory contains item, else false
    public boolean contains(Item item) {
        return false;
    }

    //EFFECTS: returns the amount of given item in inventory
    public int getItemCount(Item item) {
        return 0;
    }

    //EFFECT: returns total value of inventory
    public int getTotalValue() {
        return 0;
    }

    //EFFECTS: returns the slot that contains the given item
    private Slot findItem(Item item) {
        return new Slot(item, 1);
    }

//    public ArrayList<Slot> getSlots() {
//        return slots;
//    }

    public int getCoins() {
        return coins;
    }

//    public ArrayList<Slot> getSelection() {
//        return selection;
//    }
}
