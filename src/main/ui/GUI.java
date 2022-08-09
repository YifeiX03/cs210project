package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import model.Event;
import model.EventLog;
import model.Inventory;
import model.Item;
import model.Slot;
import persistence.JsonReader;
import persistence.JsonWriter;

// GUI class, allows user to interact with inventory through a gui.
//  menu buttons on left
//  inventory storage in bottom right
//  inventory interaction on top right
public class GUI extends JFrame implements ActionListener, WindowListener {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;

    private static final String INVENTORY_STORE = "./data/guiInventory.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel menuPanel;
    private JPanel inventoryPanel;

    private JTextArea inventoryDisplay; // displays items in inventory, part of inventory panel

    private JPanel interactionPanel; // interaction between user and inventory, holds the follwing three
    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel inspectPanel;

    // Add stuff here:
    private JComboBox<String> addComboBox;
    private JTextField addText;

    // Remove stuff here:
    private JComboBox<String> removeComboBox; // combo box that shows stuff in inventory, for remove panel
    private JTextField removeText;

    // Inspection stuff here:
    private JComboBox<String> inspectComboBox; // combo box that shows stuff that is in inventory, for insepct panel
    private JTextArea inspectText;

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

        addWindowListener(this);
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
        showInventory(inventoryDisplay, inventory);

        scroll.setPreferredSize(new Dimension(WIDTH * 3 / 4, HEIGHT / 2));

        inventoryPanel.add(scroll);

        this.add(inventoryPanel);
        inventoryPanel.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: updates the given combo box to show everything that's presently in inventory
    private void updateInventoryComboBox(JComboBox comboBox) {
        comboBox.removeAllItems();
        for (Slot slot : inventory.getSlots()) {
            comboBox.addItem(slot.getItem().getName());
        }
        comboBox.repaint();
        comboBox.revalidate();
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

        addComboBox = new JComboBox<>();
        for (Item item : itemShop) {
            addComboBox.addItem(item.getName());
        }
        addPanel.add(addComboBox);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBackground(Color.white);
        amountLabel.setOpaque(true);
        addText = new JTextField("0");
        addText.setPreferredSize(new Dimension(30, 20));
        addPanel.add(amountLabel);
        addPanel.add(addText);

        JButton adding = makeButton("Add", "addItem", this);
        addPanel.add(adding);
    }

    // MODIFIES: this
    // EFFECTS: creates the remove panel, panel that allows for user to remove stuff from inventory
    private void initializeRemovePanel() {
        removePanel = createInventoryPanel();
        removePanel.setBackground(new Color(18, 61, 56));

        removeComboBox = new JComboBox<>();
        updateInventoryComboBox(removeComboBox);
        removePanel.add(removeComboBox);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBackground(Color.white);
        amountLabel.setOpaque(true);
        removeText = new JTextField("0");
        removeText.setPreferredSize(new Dimension(30, 20));
        removePanel.add(amountLabel);
        removePanel.add(removeText);

        JButton removing = makeButton("Remove", "removeItem", this);
        removePanel.add(removing);
    }

    // MODIFIES: this
    // EFFECTS: creates the inspect panel, panel that allows for user to inspect items in inventory
    private void intializeInspectPanel() {
        inspectPanel = createInventoryPanel();
        inspectPanel.setBackground(new Color(194, 165, 94));

        inspectComboBox = new JComboBox<>();
        updateInventoryComboBox(inspectComboBox);
        inspectPanel.add(inspectComboBox);

        JButton inspecting = makeButton("Inspect", "inspectItem", this);
        inspectPanel.add(inspecting);

        inspectText = new JTextArea();
        inspectText.setEditable(false);
        inspectText.setFont(inventoryDisplay.getFont().deriveFont(20f));
        inspectText.setPreferredSize(new Dimension(WIDTH * 3 / 4, HEIGHT / 4));

        inspectPanel.add(inspectText);
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
        textArea.append("Items in Inventory: \n");
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
//        inventory.addItem(new Item("test", "cool", 10), 20);
//        inventory.addItem(new Item("wow", "neato", 1), 5);
//        inventory.addItem(new Item("neato", "pretty cool huh?", 50), 120);
    }

    // MODIFIES: this
    // EFFECTS: initializes the item shop
    private void initializeItemShop() {
        itemShop = new ArrayList<>();
        itemShop.add(new Item("Awesome Possum", "Possum that is awesome", 500));
        itemShop.add(new Item("MR KRABS", "From Sponge Bob Square Pants", 10));
        itemShop.add(new Item("test", "cool", 10));
        itemShop.add(new Item("wow", "neato", 1));
        itemShop.add(new Item("neato", "pretty cool huh?", 50));
    }

    // MODIFIES: this
    // EFFECTS: listens to commands given out by the buttons and runs the correct method
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addScreen")) {
            switchToAdd();
        } else if (e.getActionCommand().equals("addItem")) {
            addItem();
        } else if (e.getActionCommand().equals("removeScreen")) {
            switchToRemove();
        } else if (e.getActionCommand().equals("removeItem")) {
            removeItem();
        } else if (e.getActionCommand().equals("inspectScreen")) {
            switchToInspect();
        } else if (e.getActionCommand().equals("inspectItem")) {
            inspectItem();
        } else if (e.getActionCommand().equals("save")) {
            save();
        } else if (e.getActionCommand().equals("load")) {
            load();
        }
    }

    // MODIFIES: this
    // EFFECT: switches to add panel
    private void switchToAdd() {
        switchToPanel(addPanel);
    }

    // MODIFIES: this
    // EFFECT: switches to remove panel
    private void switchToRemove() {
        updateInventoryComboBox(removeComboBox);
        switchToPanel(removePanel);
    }

    // MODIFIES: this
    // EFFECT: switches to inspect panel
    private void switchToInspect() {
        updateInventoryComboBox(inspectComboBox);
        switchToPanel(inspectPanel);
    }

    // MODIFIES: this
    // EFFECT: adds selected item to inventory
    private void addItem() {
//        System.out.println("Adding...");
        String selection = addComboBox.getItemAt(addComboBox.getSelectedIndex());

        Item selectedItem = null;

        for (Item item : itemShop) {
            if (item.getName().equals(selection)) {
                selectedItem = item;
            }
        }

        int addAmount = Integer.parseInt(addText.getText());

        if (selectedItem != null) {
            inventory.addItem(selectedItem, addAmount);
            showInventory(inventoryDisplay, inventory);
        }
    }

    // MODIFIES: this
    // EFFECT: removes selected item from inventory
    private void removeItem() {
//        System.out.println("Removing...");
        String selection = removeComboBox.getItemAt(removeComboBox.getSelectedIndex());

        Item item = null;

        for (Slot slot : inventory.getSlots()) {
            if (slot.getItem().getName().equals(selection)) {
                item = slot.getItem();
            }
        }

        int removeAmount = Integer.parseInt(removeText.getText());

        if (item != null) {
            inventory.removeItem(item, removeAmount);
            showInventory(inventoryDisplay, inventory);
        }
    }

    // MODIFIES: this
    // EFFECT: inspects selected item
    private void inspectItem() {
//        System.out.println("Inspecting...");
        String selection = inspectComboBox.getItemAt(inspectComboBox.getSelectedIndex());

        Item item = null;

        for (Slot slot : inventory.getSlots()) {
            if (slot.getItem().getName().equals(selection)) {
                item = slot.getItem();
            }
        }

        if (item != null) {
            inspectText.setText("");
            inspectText.append("Name: " + item.getName());
            inspectText.append("\nDescription: " + item.getDescription());
            inspectText.append("\nValue: " + item.getValue());
        } else {
            inspectText.setText("Item not found");
        }
    }

    // EFFECTS: saves the inventory to file
    private void save() {
//        System.out.println("Saving...");
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
//        System.out.println("Loading...");
        try {
            jsonReader = new JsonReader(INVENTORY_STORE);
            inventory = jsonReader.read();
            showInventory(inventoryDisplay, inventory);
            updateInventoryComboBox(removeComboBox);
            updateInventoryComboBox(inspectComboBox);
            System.out.println("Load Successful");
        } catch (IOException e) {
            System.out.println("Unable to load");
        }
    }

    public void windowOpened(WindowEvent e) {

    }

    public void windowActivated(WindowEvent e) {

    }

    public void windowClosing(WindowEvent e) {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
    }

    public void windowClosed(WindowEvent e) {

    }

    public void windowDeactivated(WindowEvent e) {

    }

    public void windowIconified(WindowEvent e) {

    }

    public void windowDeiconified(WindowEvent e) {
    }

}
