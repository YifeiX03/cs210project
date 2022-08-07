package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import model.Inventory;
import model.Item;
import model.Slot;
import persistence.JsonReader;
import persistence.JsonWriter;

// IMPORTANT: DOCUMENT METHODS WHEN YOU ARE DONE

// TODO:
// Create main menu - showing interact with inventory, save, load, quit
// Create inventory menu - showing add, remove, inspect, back
// Inventory menu should show items in inventory and amount
// Clicking on buttons in the menu, choosing items by typing name in pop-up window?
// Add some sort of visual component, maybe show a capybara on start up and quit?

// TODO: adding stuff
// TODO: removing stuff
// TODO: inspecting stuff

// TODO: documentation

// save and load are done
// capybara image

public class GUI extends JFrame implements ActionListener {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;

    private static final String INVENTORY_STORE = "./data/guiInventory.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel menuPanel;
    private JPanel inventoryPanel;

    private JTextArea inventoryDisplay;

    private JPanel interactionPanel;
    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel inspectPanel;

    private Inventory inventory;
    private List<Item> itemShop;

    //EFFECTS: initializes components and runs the gui
    public GUI() {
        super("aaaaaaaaaaaaaaaaa");
        initializeInventory();
        initializeItemShop();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        initializeMenuPanel();
        initializeInventoryPanel();
        initializeInteractionPanel();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    // MODIFIES: this
    // EFFECTS: initializes the menu panel, the add, remove, inspect, save, and load options are added to the
    //          left side of the screen
    private void initializeMenuPanel() {
        menuPanel = new JPanel();
        menuPanel.setBackground(new Color(157, 157, 212));
        menuPanel.setBounds(0, 0, WIDTH / 4, HEIGHT);
        menuPanel.setLayout(new GridLayout(0, 1, 0, 5));
        menuPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JButton addBtn = makeButton("Add", "addScreen", this);
        JButton removeBtn = makeButton("Remove", "removeScreen", this);
        JButton inspectBtn = makeButton("Inspect", "inspectScreen", this);
        JButton saveBtn = makeButton("Save", "save", this);
        JButton loadBtn = makeButton("Load", "load", this);

        menuPanel.add(addBtn);
        menuPanel.add(removeBtn);
        menuPanel.add(inspectBtn);
        menuPanel.add(saveBtn);
        menuPanel.add(loadBtn);

        this.add(menuPanel);
        menuPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes inventory panel, items in inventory and count is shown in the bottom of the screen
    private void initializeInventoryPanel() {
        inventoryPanel = new JPanel();
        inventoryPanel.setBackground(new Color(149, 182, 142));
        inventoryPanel.setBounds(WIDTH / 4, HEIGHT / 2, WIDTH * 3 / 4, HEIGHT / 2);

        inventoryDisplay = new JTextArea();
        inventoryDisplay.setEditable(false);
        JScrollPane scroll = new JScrollPane(inventoryDisplay);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        inventoryDisplay.setFont(inventoryDisplay.getFont().deriveFont(20f));
        inventoryDisplay.append("Items in Inventory: \n");
        showInventory(inventoryDisplay, inventory);

        scroll.setPreferredSize(new Dimension(WIDTH * 3 / 4, HEIGHT / 2));

        inventoryPanel.add(scroll);

        this.add(inventoryPanel);
        inventoryPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes the interaction panel, adding, removing, and inspecting items will be done in this panel
    //          also shows an image of a capybara when the program is started
    private void initializeInteractionPanel() {
        interactionPanel = new JPanel();
        initializeAddPanel();
        initializeRemovePanel();
        intializeInspectPanel();

        interactionPanel.setVisible(true);
        interactionPanel.setBounds(WIDTH / 4, 0, WIDTH * 3 / 4, HEIGHT / 2);
        interactionPanel.setBackground(new Color(0, 0, 0));

        ImageIcon imgIcon = new ImageIcon("./data/Capybara_portrait.jpg");
        Image img = imgIcon.getImage();
        Image imgScale = img.getScaledInstance(interactionPanel.getWidth(),
                interactionPanel.getHeight(),
                Image.SCALE_DEFAULT);
        ImageIcon imgScaled = new ImageIcon(imgScale);

        JLabel imageLabel = new JLabel(imgScaled);
        interactionPanel.add(imageLabel);

        this.add(interactionPanel);
    }

    // EFFECTS: creates an inventory panel, a panel to be used in the interaction panel
    //          returns the created panel
    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(WIDTH * 3 / 4, HEIGHT / 2));

        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates the add panel, panel that allows for user to add stuff to inventory
    private void initializeAddPanel() {
        addPanel = createInventoryPanel();
        addPanel.setBackground(new Color(193, 149, 206));

        JButton adding = makeButton("Add", "addItem", this);
        addPanel.add(adding);
    }

    // MODIFIES: this
    // EFFECTS: creates the remove panel, panel that allows for user to remove stuff from inventory
    private void initializeRemovePanel() {
        removePanel = createInventoryPanel();
        removePanel.setBackground(new Color(18, 61, 56));

        JButton removing = makeButton("Remove", "removeItem", this);
        removePanel.add(removing);
    }

    // MODIFIES: this
    // EFFECTS: creates the inspect panel, panel that allows for user to inspect items in inventory
    private void intializeInspectPanel() {
        inspectPanel = createInventoryPanel();
        inspectPanel.setBackground(new Color(194, 165, 94));

        JButton inspecting = makeButton("Inspect", "inspectItem", this);
        inspectPanel.add(inspecting);
    }

    // MODIFIES: this
    // EFFECTS: switches the interaction panel to the chosen panel
    private void switchToPanel(JPanel chosenPanel) {
        interactionPanel.removeAll();
        interactionPanel.add(chosenPanel);
        interactionPanel.repaint();
        interactionPanel.revalidate();
    }

    // MODIFIES: textArea
    // EFFECTS: displays given inventory inside of the given text area
    private void showInventory(JTextArea textArea, Inventory inventory) {
        textArea.setText("");
        for (Slot slot : inventory.getSlots()) {
            textArea.append(slot.getAmount() + " * " + slot.getItem().getName());
            textArea.append("\n");
        }
    }

    // EFFECTS: creates a new button with given text, command, and action listener
    //          then returns the new button
    private JButton makeButton(String text, String command, ActionListener al) {
        JButton btn = new JButton(text);
        btn.setActionCommand(command);
        btn.addActionListener(al);
        return btn;
    }

    // MODIFIES: this
    // EFFECTS: initializes the inventory
    private void initializeInventory() {
        inventory = new Inventory("Items");
        inventory.addItem(new Item("test", "cool", 10), 20);
        inventory.addItem(new Item("wow", "neato", 1), 5);
        inventory.addItem(new Item("neato", "pretty cool huh?", 50), 120);
    }

    // MODIFIES: this
    // EFFECTS: initializes the item shop
    private void initializeItemShop() {
        itemShop = new ArrayList<>();
        itemShop.add(new Item("Awesome Possum", "Possum that is awesome", 500));
        itemShop.add(new Item("MR KRABS", "From Sponge Bob Square Pants", 10));
    }

    // MODIFIES: this
    // EFFECTS: listens to commands given out by the buttons and runs the correct method
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addScreen")) {
            switchToPanel(addPanel);
        } else if (e.getActionCommand().equals("addItem")) {
            System.out.println("Adding...");
        } else if (e.getActionCommand().equals("removeScreen")) {
            switchToPanel(removePanel);
        } else if (e.getActionCommand().equals("removeItem")) {
            System.out.println("Removing...");
        } else if (e.getActionCommand().equals("inspectScreen")) {
            switchToPanel(inspectPanel);
        } else if (e.getActionCommand().equals("inspectItem")) {
            System.out.println("Inspecting...");
        } else if (e.getActionCommand().equals("save")) {
            save();
        } else if (e.getActionCommand().equals("load")) {
            load();
        }
    }

    // EFFECTS: saves the inventory to file
    private void save() {
        System.out.println("Saving...");
        try {
            jsonWriter = new JsonWriter(INVENTORY_STORE);
            jsonWriter.open();
            jsonWriter.write(inventory);
            jsonWriter.close();
            System.out.println("Saved successfully");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads inventory
    private void load() {
        System.out.println("Loading...");
        try {
            jsonReader = new JsonReader(INVENTORY_STORE);
            inventory = jsonReader.read();
            showInventory(inventoryDisplay, inventory);
            System.out.println("Load Successful");
        } catch (IOException e) {
            System.out.println("Unable to load");
        }
    }
}
