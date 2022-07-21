package model;

// represents a slot in the inventory, storing an item and the amount of the item
public class Slot {
    private Item item;
    private int amount;

    //REQUIRES: given item must not be unique
    //EFFECTS: creates a slot with given amount of given item
    public Slot(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}
