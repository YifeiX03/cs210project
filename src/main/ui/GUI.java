package ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Inventory;
import model.Item;
import model.Slot;

// IMPORTANT: DOCUMENT METHODS WHEN YOU ARE DONE

// TODO:
// Create main menu - showing interact with inventory, save, load, quit
// Create inventory menu - showing add, remove, inspect, back
// Inventory menu should show items in inventory and amount
// Clicking on buttons in the menu, choosing items by typing name in pop-up window?
// Add some sort of visual component, maybe show a capybara on start up and quit?

public class GUI extends JFrame implements ActionListener {
    private static final int HEIGHT = 800;
    private static final int WIDTH = 800;

    private JPanel menuPanel;
    private JPanel inventoryPanel;

    private JTextArea inventoryDisplay;

    private JPanel interactionPanel;
    private JPanel addPanel;
    private JPanel removePanel;
    private JPanel inspectPanel;

    private Inventory inventory;

    public GUI() {
        super("aaaaaaaaaaaaaaaaa");
        initializeInventory();
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

    private JPanel createInventoryPanel() {
        JPanel panel = new JPanel();
        panel.setVisible(true);
        panel.setPreferredSize(new Dimension(WIDTH * 3 / 4, HEIGHT / 2));

        return panel;
    }

    private void initializeAddPanel() {
        addPanel = createInventoryPanel();
        addPanel.setBackground(new Color(193, 149, 206));

        JButton adding = makeButton("Add", "addItem", this);
        addPanel.add(adding);
    }

    private void initializeRemovePanel() {
        removePanel = createInventoryPanel();
        removePanel.setBackground(new Color(18, 61, 56));

        JButton removing = makeButton("Remove", "removeItem", this);
        removePanel.add(removing);
    }

    private void intializeInspectPanel() {
        inspectPanel = createInventoryPanel();
        inspectPanel.setBackground(new Color(194, 165, 94));

        JButton inspecting = makeButton("Inspect", "inspectItem", this);
        inspectPanel.add(inspecting);
    }

    private void switchToPanel(JPanel chosenPanel) {
        interactionPanel.removeAll();
        interactionPanel.add(chosenPanel);
        interactionPanel.repaint();
        interactionPanel.revalidate();
    }

    private void showInventory(JTextArea textArea, Inventory inventory) {
        for (Slot slot : inventory.getSlots()) {
            textArea.append(slot.getAmount() + " * " + slot.getItem().getName());
            textArea.append("\n");
        }
    }

    private JButton makeButton(String text, String command, ActionListener al) {
        JButton btn = new JButton(text);
        btn.setActionCommand(command);
        btn.addActionListener(al);
        return btn;
    }

    private void initializeInventory() {
        inventory = new Inventory("Items");
        inventory.addItem(new Item("test", "cool", 10), 20);
        inventory.addItem(new Item("wow", "neato", 1), 5);
    }

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

    private void save() {
        System.out.println("Saving...");
    }

    private void load() {
        System.out.println("Loading...");
    }

//    public void actionPerformed(ActionEvent e) {
//        switch (e.getActionCommand()) {
//            case "addScreen":
//                switchToPanel(addPanel);
//                break;
//            case "addItem":
//                System.out.println("Adding...");
//                break;
//            case "removeScreen":
//                switchToPanel(removePanel);
//                break;
//            case "removeItem":
//                System.out.println("Removing...");
//                break;
//            case "inspectScreen":
//                switchToPanel(inspectPanel);
//                break;
//            case "inspectItem":
//                System.out.println("Inspecting...");
//                break;
//            case "save":
//                System.out.println("Saving...");
//                break;
//            case "load":
//                System.out.println("Loading...");
//                break;
//            default:
//                break;
//        }
//    }
}
