package ui;

import model.Inventory;
import model.Item;
import model.Slot;

import persistence.JsonWriter;
import persistence.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.Scanner;

// Inventory Application

//based on Teller app; link below
//https://github.students.cs.ubc.ca/CPSC210/TellerApp.git

// Persistence methods (saveFile() and loadFile()) based off of WorkRoomApp from JSON serialization demo; link below:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public class InventoryApp {
    private Inventory inventory;
    private Inventory selection;
    private Inventory items;
    private Scanner input;
    private Boolean running;
    private Boolean runningInventory;
    private Boolean runningSelection;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private static final String INVENTORY_STORE = "./data/inventory.json";
    private static final String ITEMS_STORE = "./data/items.json";

    //EFFECTS: runs the inventory application
    public InventoryApp() {
        runInventoryApp();
    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runInventoryApp() {
        running = true;
        String command;

        initialize();
        createItems();

        while (running) {
            showMenu();
            command = input.next();
            command = command.toUpperCase();
            processCommand(command);
        }

        System.out.println("Exiting... please give me a good grade. :)");
    }

    //MODIFIES: this
    //EFFECTS: initializes the inventory, selection, and input system
    private void initialize() {
        inventory = new Inventory("main");
        selection = new Inventory("selection");
        items = new Inventory("items");
        input = new Scanner(System.in);
        input.useDelimiter("\n");

//        jsonWriter = new JsonWriter(INVENTORY_STORE);
//        jsonReader = new JsonReader();
    }


    //MODIFIES: this
    //EFFECTS: creates the items that can be added to inventory
    private void createItems() {
        items.addItem(new Item("Grasshopper Legs", "Legs of a grasshopper, yum", 2), 9999);

        items.addItem(new Item("Spider Eyes", "Eyes of a spider, not so yum", 1), 9999);
    }

    //EFFECTS: shows main menu to user
    private void showMenu() {
        displayInventory(inventory,"inventory");
        System.out.println();
        System.out.println("Items that have been made:");
        displayItems(items);
        System.out.println();
        displayCoins();
        System.out.println("\nWhat do you want to do?\n");
        System.out.println("\tInteract with Inventory (I)");
        System.out.println("\tMake a new item (M)");
        System.out.println("\tSelect items in inventory (S)");
        System.out.println("\tSave (save)");
        System.out.println("\tLoad (load)");
        System.out.println("\tQuit (Q)");
    }

    //MODIFIES: this
    //EFFECTS:processes user command involving main menu
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
            case "SAVE":
                saveFile();
                break;
            case "LOAD":
                loadFile();
                break;
            case "Q":
                quitmain();
                break;
            default:
                System.out.println("Invalid Command\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: asks the user if they want to save, then quits the program
    private void quitmain() {
        System.out.println("Do you want to save before quitting? (Y/N)");
        String quitCommand = input.next();
        quitCommand = quitCommand.toUpperCase();
        if (quitCommand.equals("Y")) {
            saveFile();
        }
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
            displayInventory(inventory, "inventory");
            pressAnyKeyToContinue();
        }
        enterInventoryMenu();
    }

    //EFFECTS: shows the amount of coins the inventory has
    private void displayCoins() {
        System.out.println("You have " + inventory.getCoins() + " Coins");
    }

    //EFFECTS: shows total value and total items
    private void displayInventory(Inventory inventory, String name) {
        System.out.println(name + " has " + inventory.getTotalItems() + " Items");
        System.out.println(name + " has value of " + inventory.getTotalValue() + "");

        if (inventory.length() != 0) {
            System.out.println("Items in " + name + ":");
            displayItems(inventory);
        }
    }

    //EFFECTS: shows the amount of items in given inventory
    private void displayItems(Inventory inventory) {
        for (Slot slot : inventory.getSlots()) {
            System.out.println(slot.getAmount() + " x " + slot.getItem().getName());
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

    //EFFECTS: displays menu for inventory interaction
    private void showInventoryMenu() {
        displayInventory(inventory, "inventory");
        System.out.println("\nWhat do you want to do in your inventory?\n");
        System.out.println("\tAdd items (A)");
        System.out.println("\tRemove Items (R)");
        System.out.println("\tInspect Items (I)");
        System.out.println("\tGo back (B)");
    }

    //MODIFIES: this
    //EFFECTS:processes user command involving inventory
    private void processInventoryCommand(String input) {
        switch (input) {
            case "A":
                addToInventory(items, inventory);
                break;
            case "R":
                removeFromInventory(items, inventory, "inventory");
                break;
            case "I":
                inspectItem();
                break;
            case "B":
                runningInventory = false;
                break;
            default:
                System.out.println("Invalid Command\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: choose items to add to given inventory from source inventory
    private void addToInventory(Inventory source, Inventory receiver) {
        boolean choosing = true;
        while (choosing) {
            System.out.println("Available items to choose from:");
            displayItems(source);
            System.out.println("Type the name of the item you want to add, or type 'back' to go back");
            String choice = input.next();
            if (choice.equals("back")) {
                break;
            } else {
                boolean found = addingItem(source, receiver, choice);
                if (found) {
                    choosing = false;
                } else {
                    System.out.println("Item not found, make sure you're typing it exactly as it appears.");
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: finds the item, and lets the user choose how many to add
    //         then takes item from source inventory, and adds it to receiver inventory
    //         returns true if item is found, else false
    private boolean addingItem(Inventory source, Inventory receiver, String choice) {
        for (Slot slot : source.getSlots()) {
            if (slot.getItem().getName().equals(choice)) {
                System.out.println("How many?");
                int amount = Integer.parseInt(input.next());
                receiver.addItem(slot.getItem(), amount);
                source.removeItem(slot.getItem(), amount);
                return true;
            }
        }
        return false;
    }

    //MODIFIES: this
    //EFFECTS: choose items to remove from given inventory and returns it back to the source
    private void removeFromInventory(Inventory source, Inventory receiver, String name) {
        boolean choosing = true;
        while (choosing) {
            displayInventory(receiver, name);
            System.out.println("Type the name of the item you want to remove, or type 'back' to go back");
            String choice = input.next();
            if (choice.equals("back")) {
                break;
            } else {
                boolean found = removingItem(source, receiver, choice);
                if (found) {
                    choosing = false;
                } else {
                    System.out.println("Item not found, make sure you're typing it exactly as it appears.");
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: finds the item, and lets the user choose how many to remove
    //         then removes it from the given inventory, and returns it to source inventory
    //         returns true if item is found, else false
    private boolean removingItem(Inventory source, Inventory receiver, String choice) {
        for (Slot slot : receiver.getSlots()) {
            if (slot.getItem().getName().equals(choice)) {
                System.out.println("How many?");
                int amount = Integer.parseInt(input.next());
                receiver.removeItem(slot.getItem(), amount);
                source.addItem(slot.getItem(), amount);
                return true;
            }
        }
        return false;
    }

    //EFFECTS: choose items to inspect from inventory
    private void inspectItem() {
        boolean choosing = true;
        while (choosing) {
            displayInventory(inventory, "inventory");
            System.out.println("Type the name of the item you want to inspect, or type 'back' to go back");
            String choice = input.next();
            if (choice.equals("back")) {
                break;
            } else {
                boolean found = inspectingItem(inventory, choice);
                if (found) {
                    choosing = false;
                } else {
                    System.out.println("Item not found, make sure you're typing it exactly as it appears.");
                }
            }
        }
    }

    //EFFECTS: finds the item, and displays name, description, and value
    //         returns true if item is found, else false
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

    //MODIFIES: this
    //EFFECTS: lets the user make a new item
    private void makeItem() {
        System.out.println();
        System.out.println("Enter new item name:");
        String newName = input.next();

        System.out.println("Enter new item description:");
        String newDesc = input.next();

        System.out.println("Enter new item value:");
        int newVal = Integer.parseInt(input.next());

        items.addItem(new Item(newName, newDesc, newVal), 9999);
        System.out.println("Done");
        pressAnyKeyToContinue();
    }

    //EFFECTS: enters select mode
    private void selectMode() {
        System.out.println("Entering selection mode");

        runningSelection = true;
        String selectionCommand = null;

        while (runningSelection) {
            showSelectionMenu();
            selectionCommand = input.next();
            selectionCommand = selectionCommand.toUpperCase();
            processSelectionCommand(selectionCommand);
        }

    }

    //EFFECTS: shows selection menu
    private void showSelectionMenu() {
        displayInventory(selection, "selection");
        System.out.println("\nAvailable Commands");
        System.out.println("\tSelect Items from Inventory (S)");
        System.out.println("\tRemove Selections (R)");
        System.out.println("\tSell all items in Selection ($)");
        System.out.println("\tGo back (B)");
    }

    //MODIFIES: this
    //EFFECTS: processes user command involving selection
    private void processSelectionCommand(String input) {
        switch (input) {
            case "S":
                addToInventory(inventory, selection);
                break;
            case "R":
                removeFromInventory(inventory, selection, "selection");
                break;
            case "$":
                sellInventory(inventory, selection);
                break;
            case "B":
                leaveSelection();
                break;
            default:
                System.out.println("Invalid Command\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: sells selected items from source inventory, then leaves selection mode
    private void sellInventory(Inventory source, Inventory selection) {
        source.addCoins(selection.getTotalValue());
        System.out.println("Selected items sold for " + selection.getTotalValue() + "\n");
        selection.getSlots().clear();
        runningSelection = false;
        pressAnyKeyToContinue();
    }

    //MODIFIES: this
    //EFFECTS: removes items in selection when leaving the selection menu
    private void leaveSelection() {
        for (Slot slot : selection.getSlots()) {
            inventory.addItem(slot.getItem(), slot.getAmount());
        }
        runningSelection = false;
    }

    // EFFECTS: saves inventory and made items to file
    private void saveFile() {
        try {
            jsonWriter = new JsonWriter(INVENTORY_STORE);
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            jsonWriter = new JsonWriter(ITEMS_STORE);
            jsonWriter.open();
            jsonWriter.write(items);
            jsonWriter.close();
            System.out.println("Saved successfully");
            pressAnyKeyToContinue();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
            pressAnyKeyToContinue();
        }
    }

    // MODIFIES: this
    // EFFECTS: loads inventory and made items from file
    private void loadFile() {
        try {
            jsonReader = new JsonReader(INVENTORY_STORE);
            inventory = jsonReader.read();
            jsonReader = new JsonReader(ITEMS_STORE);
            items = jsonReader.read();
            System.out.println("Load Successful");
            pressAnyKeyToContinue();
        } catch (IOException e) {
            System.out.println("Unable to load");
            pressAnyKeyToContinue();
        }
    }

}
