package ui;

import model.Inventory;
import model.Item;

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
public class InventoryApp {
    private Inventory inventory;
    private Inventory selection;
    private Scanner input;
    private Boolean running;

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
            System.out.println("What is your command?");
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
        System.out.println("\tCheck items in inventory (C)");
        System.out.println("\tAdd items to inventory (A)");
        System.out.println("\tRemove items from inventory (R)");
        System.out.println("\tInspect item in inventory (I)");
        System.out.println("\tSelect items in inventory (S)");
        System.out.println("\tQuit (Q)");
    }

    //MODIFIES: this
    //EFFECTS:processes user command
    private void processCommand(String input) {
        switch (input) {
            case "C":
                System.out.println("Checking Inventory...");
                break;
            case "A":
                System.out.println("Adding to Inventory...");
                break;
            case "R":
                System.out.println("Removing from Inventory...");
                break;
            case "I":
                System.out.println("Inspecting an Item...");
                break;
            case "S":
                System.out.println("Selecting Items...");
                break;
            case "Q":
                System.out.println("Quitting...");
                quit();
                break;
            default:
                System.out.println("Invalid Command");
        }
    }

    //MODIFIES: this
    //EFFECTS: changes running to false
    private void quit() {
        running = false;
    }
}
