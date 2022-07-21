package model;

// represents a slot in the inventory, storing an item and the amount of the item
public class Slot {
    private Item item;
    private int amount;

    // EFFECTS: creates an empty slot
    public Slot() {
        this.item = null;
        this.amount = 0;
    }

    // EFFECTS: creates a slot with given amount of given item
    public Slot(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    // MODIFIES: this
    // EFFECTS: sets item to given item
    public void setItem(Item item) {
        this.item = item;
    }

    // REQUIRES: amount must be positive int
    // MODIFIES: this
    // EFFECTS: adds amount to the amount of the item
    public void addAmount(int amount) {
        this.amount += amount;
    }

    // REQUIRES: amount must be positive int
    // MODIFIES: this
    // EFFECTS: deducts amount from the amount of the item
    public void removeAmount(int amount) {
        this.amount -= amount;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}
