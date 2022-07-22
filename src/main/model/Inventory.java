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

    public ArrayList<Slot> getSlots() {
        return slots;
    }

    public int getCoins() {
        return coins;
    }

//    public void test() {
//        ArrayList<String> tester = new ArrayList<String>();
//        for (Slot slot : slots) {
//            tester.add(slot.getItem().getName());
//        }
//    }

//    public ArrayList<Slot> getSelection() {
//        return selection;
//    }
}
