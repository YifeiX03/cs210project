package ui;

import model.Inventory;
import model.Item;
import model.Slot;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

// Inventory Application
// two inventories will be created: main and selection
// main inventory will be the real inventory
// selection will be for selecting items from main

//main idea: app will display items in inventory as a list of item:count
//           user will be able to select certain items by typing in their name
//           app will look through the slots, matching given string to name of the item in each slot
//           once item is found, app will display amount of that item
//           user will input amount they want to choose

//based on Teller app; link below
//https://github.students.cs.ubc.ca/CPSC210/TellerApp.git
public class InventoryApp {
    private Inventory inventory;
    private Inventory selection;
    private Scanner input;
    private Boolean running;
    private Boolean runningInventory;

    private ArrayList<Item> items;

    //EFFECTS: runs the inventory application
    public InventoryApp() {
        runInventoryApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runInventoryApp() {
        running = true;
        String command = null;

        initialize();
        createItems();

        while (running) {
            showMenu();
            command = input.next();
            command = command.toUpperCase();
            processCommand(command);
        }

        System.out.println("Give me a good grade please.");
    }


    //MODIFIES: this
    //EFFECTS: creates the items that can be added to inventory
    private void createItems() {
        items.add(new Item("Grasshopper Legs",
                "Legs of a grasshopper, yum",
                2));
        items.add(new Item("Spider Eyes",
                "Eyes of a spider, not so yum",
                1));
    }

    //MODIFIES: this
    //EFFECTS: initializes the inventory, selection, and input system
    private void initialize() {
        inventory = new Inventory();
        selection = new Inventory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");

        items = new ArrayList<Item>();
    }

    //EFFECTS: shows menu to user
    private void showMenu() {
        System.out.println("\nWhat do you want to do?\n");
        System.out.println("\tInteract with Inventory (I)");
        System.out.println("\tMake a new item (M)");
        System.out.println("\tSelect items in inventory (S)");
        System.out.println("\tQuit (Q)");
    }

    //MODIFIES: this
    //EFFECTS:processes user command
    private void processCommand(String input) {
        switch (input) {
            case "I":
                checkInventory();
                break;
            case "M":
                makeItem();
                break;
            case "S":
                selectMode();
                break;
            case "Q":
                quit();
                break;
            default:
                System.out.println("Invalid Command\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: changes running to false
    private void quit() {
        running = false;
    }

    //EFFECTS: pauses the program and waits for user input to continue
    private void pressAnyKeyToContinue() {
        System.out.println("Press any key to continue:");
        input.next();
    }

    //EFFECTS: displays items in inventory
    private void checkInventory() {
        if (inventory.length() == 0) {
            System.out.println("Inventory is empty.");
            pressAnyKeyToContinue();
        } else {
            displayInventory(inventory);

            pressAnyKeyToContinue();
        }
        enterInventoryMenu();
    }

    private void displayInventory(Inventory inventory) {
        System.out.println("Inventory has " + inventory.getTotalItems() + " Items");
        System.out.println("Inventory has value of " + inventory.getTotalValue() + "");
        System.out.println("Items in Inventory:");

        for (Slot slot : inventory.getSlots()) {
            System.out.println(slot.getAmount() +  " x " +  slot.getItem().getName());
        }
    }

    //MODIFIES: this
    //EFFECTS: enters inventory menu, allows for adding, removing, and inspecting of items
    private void enterInventoryMenu() {
        runningInventory = true;
        String inventoryCommand = null;

        while (runningInventory) {
            showInventoryMenu();
            inventoryCommand = input.next();
            inventoryCommand = inventoryCommand.toUpperCase();
            processInventoryCommand(inventoryCommand);
        }
    }

    private void showInventoryMenu() {
        System.out.println("\nWhat do you want to do in your inventory?\n");
        System.out.println("\tAdd items (A)");
        System.out.println("\tRemove Items (R)");
        System.out.println("\tInspect Items (I)");
        System.out.println("\tGo back (B)");
    }

    //MODIFIES: this
    //EFFECTS:processes user command
    private void processInventoryCommand(String input) {
        switch (input) {
            case "A":
                addToInventory();
                break;
            case "R":
                removeFromInventory();
                break;
            case "I":
                inspectItem();
                break;
            case "B":
                quitInventory();
                break;
            default:
                System.out.println("Invalid Command\n");
        }
    }

    private void quitInventory() {
        runningInventory = false;
    }

    //EFFECTS: choose items to add to inventory
    private void addToInventory() {
        boolean choosing = true;
        while (choosing) {
            System.out.println("Available items to choose from:");
            for (Item item : items) {
                System.out.println(item.getName());
            }
            System.out.println("Type the name of the item you want to add, or type 'back' to go back");
            String choice = input.next();
            if (choice.equals("back")) {
                break;
            } else {
                boolean found = addingItem(items, choice);
                if (found) {
                    choosing = false;
                } else {
                    System.out.println("Item not found, make sure you're typing it exactly as it appears.");
                }
            }
        }
    }

    private boolean addingItem(ArrayList<Item> items, String choice) {
        for (Item item : items) {
            if (item.getName().equals(choice)) {
                System.out.println("How many?");
                int amount = Integer.parseInt(input.next());
                inventory.addItem(item, amount);
                return true;
            }
        }
        return false;
    }

    private void removeFromInventory() {
        boolean choosing = true;
        while (choosing) {
            displayInventory(inventory);
            System.out.println("Type the name of the item you want to remove, or type 'back' to go back");
            String choice = input.next();
            if (choice.equals("back")) {
                break;
            } else {
                boolean found = removingItem(inventory, choice);
                if (found) {
                    choosing = false;
                } else {
                    System.out.println("Item not found, make sure you're typing it exactly as it appears.");
                }
            }
        }
    }

    private boolean removingItem(Inventory inventory, String choice) {
        for (Slot slot : inventory.getSlots()) {
            if (slot.getItem().getName().equals(choice)) {
                System.out.println("How many?");
                int amount = Integer.parseInt(input.next());
                inventory.removeItem(slot.getItem(), amount);
                return true;
            }
        }
        return false;
    }

    private void inspectItem() {
        boolean choosing = true;
        while (choosing) {
            displayInventory(inventory);
            System.out.println("Type the name of the item you want to inspect, or type 'back' to go back");
            String choice = input.next();
            if (choice.equals("back")) {
                break;
            } else {
                boolean found = false;
                for (Slot slot : inventory.getSlots()) {
                    if (slot.getItem().getName().equals(choice)) {
                        found = inspectingItem(inventory, choice);
                        choosing = false;
                        break;
                    }
                }
                if (found) {
                    choosing = false;
                } else {
                    System.out.println("Item not found, make sure you're typing it exactly as it appears.");
                }
            }
        }
    }

    private boolean inspectingItem(Inventory inventory, String choice) {
        for (Slot slot : inventory.getSlots()) {
            if (slot.getItem().getName().equals(choice)) {
                System.out.println("Name: " + slot.getItem().getName());
                System.out.println("Description: " + slot.getItem().getDescription());
                System.out.println("Value: " + slot.getItem().getValue());
                pressAnyKeyToContinue();
                return true;
            }
        }
        return false;
    }

    private void makeItem() {
//        System.out.println("Enter 'back' at any time to go back");
        System.out.println("Enter item name:");
        String newName = input.next();

        System.out.println("Enter item description:");
        String newDesc = input.next();

        System.out.println("Enter item value:");
        int newVal = Integer.parseInt(input.next());

        items.add(new Item(newName, newDesc, newVal));
        System.out.println("Done");
        pressAnyKeyToContinue();
    }

    private boolean isBack(String input) {
        return input.equals("back");
    }

    private void selectMode() {
        System.out.println("Entering selection mode");
    }
}
