package Home;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class InventorySystem extends JFrame {

    private ArrayList<Game> games = new ArrayList<>();
    private DefaultListModel<Game> listModel = new DefaultListModel<>();
    private JList<Game> gameList = new JList<>(listModel);

    private JTextField idField = new JTextField();
    private JTextField titleField = new JTextField();
    private JTextField platformField = new JTextField();
    private JTextField genreField = new JTextField();
    private JTextField yearField = new JTextField();
    private JTextField searchField = new JTextField();

    public InventorySystem() {
        setTitle("Disc Game Inventory System");
        setSize(700, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== MENU BAR =====
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem saveItem = new JMenuItem("Save");
        JMenuItem loadItem = new JMenuItem("Load");
        JMenuItem exitItem = new JMenuItem("Exit");

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);


        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(new JScrollPane(gameList), BorderLayout.CENTER);

        searchField.setBorder(BorderFactory.createTitledBorder("Search by Title"));
        leftPanel.add(searchField, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);


        JPanel rightPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        rightPanel.add(new JLabel("ID:"));
        rightPanel.add(idField);

        rightPanel.add(new JLabel("Title:"));
        rightPanel.add(titleField);

        rightPanel.add(new JLabel("Platform:"));
        rightPanel.add(platformField);

        rightPanel.add(new JLabel("Genre:"));
        rightPanel.add(genreField);

        rightPanel.add(new JLabel("Year:"));
        rightPanel.add(yearField);

        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        rightPanel.add(addBtn);
        rightPanel.add(editBtn);
        rightPanel.add(deleteBtn);

        add(rightPanel, BorderLayout.CENTER);


        addBtn.addActionListener(e -> addGame());
        editBtn.addActionListener(e -> editGame());
        deleteBtn.addActionListener(e -> deleteGame());

        saveItem.addActionListener(e -> saveGames());
        loadItem.addActionListener(e -> loadGames());
        exitItem.addActionListener(e -> System.exit(0));

        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                searchGame(searchField.getText());
            }
        });

        gameList.addListSelectionListener(e -> loadSelectedGame());

        setLocationRelativeTo(null); // center window
        setVisible(true);
    }


    private void addGame() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String platform = platformField.getText();
            String genre = genreField.getText();
            int year = Integer.parseInt(yearField.getText());

            Game game = new Game(id, title, platform, genre, year);
            games.add(game);
            listModel.addElement(game);
            clearFields();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Check ID and Year.");
        }
    }


    private void editGame() {
        int index = gameList.getSelectedIndex();
        if (index >= 0) {
            Game game = games.get(index);

            game.setTitle(titleField.getText());
            game.setPlatform(platformField.getText());
            game.setGenre(genreField.getText());
            try {
                game.setYear(Integer.parseInt(yearField.getText()));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Year must be a number.");
                return;
            }

            // refresh the item in the list
            listModel.set(index, game);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Select a game to edit.");
        }
    }


    private void deleteGame() {
        int index = gameList.getSelectedIndex();
        if (index >= 0) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to delete this game?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                games.remove(index);      // remove from ArrayList
                listModel.remove(index);  // remove from JList
                clearFields();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a game to delete.");
        }
    }


    private void loadSelectedGame() {
        Game game = gameList.getSelectedValue();
        if (game != null) {
            idField.setText(String.valueOf(game.getId()));
            titleField.setText(game.getTitle());
            platformField.setText(game.getPlatform());
            genreField.setText(game.getGenre());
            yearField.setText(String.valueOf(game.getYear()));
        }
    }


    private void searchGame(String keyword) {
        listModel.clear();
        if (keyword == null || keyword.isBlank()) {
            for (Game g : games) {
                listModel.addElement(g);
            }
            return;
        }

        for (Game g : games) {
            if (g.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                listModel.addElement(g);
            }
        }
    }


    private void saveGames() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("games.dat"))) {
            out.writeObject(games);
            JOptionPane.showMessageDialog(this, "Games saved to games.dat");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage());
        }
    }


    @SuppressWarnings("unchecked")
    private void loadGames() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("games.dat"))) {
            games = (ArrayList<Game>) in.readObject();
            listModel.clear();
            for (Game g : games) {
                listModel.addElement(g);
            }
            JOptionPane.showMessageDialog(this, "Games loaded from games.dat");
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "No saved file found (games.dat).");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage());
        }
    }


    private void clearFields() {
        idField.setText("");
        titleField.setText("");
        platformField.setText("");
        genreField.setText("");
        yearField.setText("");
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventorySystem::new);
    }
}
