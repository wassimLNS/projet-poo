package gui;

import dao.PharmacieDAO;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class PharmacieGUI extends JFrame {
    private PharmacieDAO dao;
    private JTabbedPane tabbedPane;
    private DefaultTableModel clientTableModel;
    private DefaultTableModel medicamentTableModel;
    private DefaultTableModel fournisseurTableModel;
    private DefaultTableModel venteTableModel;
    private DefaultTableModel commandeTableModel;
    
    // Tables
    private JTable clientTable;
    private JTable medicamentTable;
    private JTable fournisseurTable;
    private JTable venteTable;
    private JTable commandeTable;

    // Add this class before the main PharmacieGUI class
    class NumberTextField extends JTextField {
        public NumberTextField(int columns) {
            super(columns);
            this.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c)) {
                        e.consume();  // Ignore non-digit characters
                    }
                }
            });
        }
    }

    public PharmacieGUI() {
        dao = new PharmacieDAO();
        setTitle("Gestion de Pharmacie");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Initialisation des modèles de table avec ordre standardisé des colonnes
        clientTableModel = new DefaultTableModel(new Object[]{"Nom", "Prénom", "Téléphone", "N° Sécu", "Carte Active", "Taux Réduction (%)"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        medicamentTableModel = new DefaultTableModel(new Object[]{"Référence", "Nom", "Type", "Catégorie", "Prix", "Quantité", "Date d'expiration"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        fournisseurTableModel = new DefaultTableModel(new Object[]{"Nom", "Téléphone", "Email", "Adresse"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        venteTableModel = new DefaultTableModel(new Object[]{"Date", "Client", "Nombre Articles", "Montant Total", "Montant après réduction"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        commandeTableModel = new DefaultTableModel(new Object[]{"Date", "Fournisseur", "Nombre Articles", "Montant Total", "Statut"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Création de l'interface
        createGUI();
    }

    private void refreshAllTables() {
        SwingUtilities.invokeLater(() -> {
            refreshClientTable();
            refreshFournisseurTable();
            refreshMedicamentTable();
        });
    }

    private void setupTableModels() {
        // Modèle pour la table des clients
        String[] clientColumns = {"Nom", "Prénom", "Téléphone", "N° Sécu", "Carte Active", "Taux Réduction (%)"};
        clientTableModel = new DefaultTableModel(clientColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Modèle pour la table des fournisseurs
        String[] fournisseurColumns = {"Nom", "Téléphone", "Adresse", "Email"};
        fournisseurTableModel = new DefaultTableModel(fournisseurColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Modèle pour la table des médicaments
        String[] medicamentColumns = {"Nom", "Référence", "Stock", "Prix", "Type", "Catégorie", "Date Péremption", "Détails"};
        medicamentTableModel = new DefaultTableModel(medicamentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Modèle pour la table des ventes
        String[] venteColumns = {"ID", "Date", "Client", "Montant", "Montant après réduction"};
        venteTableModel = new DefaultTableModel(venteColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void createGUI() {
        // Définir les couleurs
        Color primaryColor = new Color(0, 112, 1); 
        Color secondColor = new Color(110, 112, 100);
        
        // Créer le panneau principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Créer le panneau d'en-tête
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setPreferredSize(new Dimension(800, 25));
        
        // Ajouter l'image de gauche
        
        
        // Ajouter le titre au centre
        JLabel titleLabel = new JLabel("<html>Gestion de Pharmacie</html>", SwingConstants.CENTER);
        JLabel titleLabel1 = new JLabel("<html> LOUNES Wassim <br> REDJEDAL Saber </html>", SwingConstants.LEFT);
        JLabel titleLabel2 = new JLabel("", SwingConstants.RIGHT);
    
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(primaryColor);
        

        titleLabel1.setFont(new Font("Segoe UI", Font.BOLD, 9));
        titleLabel1.setForeground(secondColor);
        titleLabel1.setPreferredSize(new Dimension(133, 25));
        titleLabel2.setPreferredSize(new Dimension(133, 25));

        

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(titleLabel1, BorderLayout.WEST);
        headerPanel.add(titleLabel2, BorderLayout.EAST);
        
        
        // Ajouter l'image de droite
        
        
        // Ajouter le panneau d'en-tête au panneau principal
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Configurer le tabbedPane
        tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(primaryColor);
        
        // Onglet Clients
        JPanel clientPanel = createClientPanel();
        clientPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Clients", clientPanel);
        
        // Onglet Médicaments
        JPanel medicamentPanel = createMedicamentPanel();
        medicamentPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Médicaments", medicamentPanel);
        
        // Onglet Fournisseurs
        JPanel fournisseurPanel = createFournisseurPanel();
        fournisseurPanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Fournisseurs", fournisseurPanel);
        
        // Onglet Ventes
        JPanel ventePanel = createVentePanel();
        ventePanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Ventes", ventePanel);
        
        // Onglet Commandes
        JPanel commandePanel = createCommandePanel();
        commandePanel.setBackground(Color.WHITE);
        tabbedPane.addTab("Commandes", commandePanel);
        
        // Ajouter le tabbedPane au panneau principal
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Ajouter le panneau principal à la fenêtre
        add(mainPanel);
        
        refreshAllTables();
    }

    // --- AJOUT : Panel avec image de fond ---
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        private float opacity;
        private Dimension imageSize;

        public BackgroundPanel(Image image, float opacity, Dimension size) {
            this.backgroundImage = image;
            this.opacity = opacity;
            this.imageSize = size;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
                int x = (getWidth() - imageSize.width) / 2;
                int y = (getHeight() - imageSize.height) / 2;
                g2d.drawImage(backgroundImage, x, y, imageSize.width, imageSize.height, this);
                g2d.dispose();
            }
        }
    }

    private JPanel createClientPanel() {
        ImageIcon icon = new ImageIcon("src/resources/client.png");
        BackgroundPanel panel = new BackgroundPanel(icon.getImage(), 0.4f, new Dimension(450, 450));
        panel.setLayout(new BorderLayout());
        // Création de la table
        clientTable = new JTable(clientTableModel);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientTable.setOpaque(false);
        clientTable.setBackground(new Color(230, 245, 230, 0)); // transparent
        clientTable.setGridColor(new Color(200, 200, 200));
        clientTable.setSelectionBackground(new Color(200, 230, 200)); // Vert clair pour la sélection
        clientTable.setSelectionForeground(Color.BLACK); // Texte noir lors de la sélection
        // Rendre les cellules transparentes
        ((DefaultTableCellRenderer)clientTable.getDefaultRenderer(Object.class)).setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(clientTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        // Panel des boutons centré
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = createStyledButton("Ajouter");
        JButton editButton = createStyledButton("Modifier");
        JButton deleteButton = createStyledButton("Supprimer");
        JButton historyButton = createStyledButton("Historique");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(historyButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        // Actions des boutons
        addButton.addActionListener(e -> showAddClientDialog());
        editButton.addActionListener(e -> editSelectedClient(clientTable));
        deleteButton.addActionListener(e -> deleteSelectedClient(clientTable));
        historyButton.addActionListener(e -> showClientHistory(clientTable));
        return panel;
    }

    private JPanel createFournisseurPanel() {
        ImageIcon icon = new ImageIcon("src/resources/suplier.png");
        BackgroundPanel panel = new BackgroundPanel(icon.getImage(), 0.4f, new Dimension(450, 450));
        panel.setLayout(new BorderLayout());
        // Table des fournisseurs avec le modèle
        fournisseurTable = new JTable(fournisseurTableModel);
        fournisseurTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fournisseurTable.setOpaque(false);
        fournisseurTable.setBackground(new Color(230, 245, 230, 0));
        fournisseurTable.setGridColor(new Color(200, 200, 200));
        fournisseurTable.setSelectionBackground(new Color(200, 230, 200)); // Vert clair pour la sélection
        fournisseurTable.setSelectionForeground(Color.BLACK); // Texte noir lors de la sélection
        ((DefaultTableCellRenderer)fournisseurTable.getDefaultRenderer(Object.class)).setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(fournisseurTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        // Panel des boutons centré
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = createStyledButton("Ajouter");
        JButton editButton = createStyledButton("Modifier");
        JButton deleteButton = createStyledButton("Supprimer");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        // Actions des boutons
        addButton.addActionListener(e -> showAddFournisseurDialog());
        editButton.addActionListener(e -> editSelectedFournisseur(fournisseurTable));
        deleteButton.addActionListener(e -> deleteSelectedFournisseur(fournisseurTable));
        return panel;
    }

    private JPanel createMedicamentPanel() {
        ImageIcon icon = new ImageIcon("src/resources/LOGO1.png");
        BackgroundPanel panel = new BackgroundPanel(icon.getImage(), 0.3f, new Dimension(450, 450));
        panel.setLayout(new BorderLayout());
        // Table des médicaments avec le modèle
        medicamentTable = new JTable(medicamentTableModel);
        medicamentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medicamentTable.setOpaque(false);
        medicamentTable.setBackground(new Color(230, 245, 230, 0));
        medicamentTable.setGridColor(new Color(200, 200, 200));
        medicamentTable.setSelectionBackground(new Color(200, 230, 200)); // Vert clair pour la sélection
        medicamentTable.setSelectionForeground(Color.BLACK); // Texte noir lors de la sélection
        ((DefaultTableCellRenderer)medicamentTable.getDefaultRenderer(Object.class)).setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(medicamentTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = createStyledButton("Ajouter");
        JButton editButton = createStyledButton("Modifier");
        JButton deleteButton = createStyledButton("Supprimer");
        JButton peremptionButton = createStyledButton("Voir Péremptions");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(peremptionButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        // Actions des boutons
        addButton.addActionListener(e -> showAddMedicamentDialog());
        editButton.addActionListener(e -> editSelectedMedicament(medicamentTable));
        deleteButton.addActionListener(e -> deleteSelectedMedicament(medicamentTable));
        peremptionButton.addActionListener(e -> showPeremptionDialog());
        return panel;
    }

    private JPanel createVentePanel() {
        ImageIcon icon = new ImageIcon("src/resources/sales.png");
        BackgroundPanel panel = new BackgroundPanel(icon.getImage(), 0.4f, new Dimension(550, 450));
        panel.setLayout(new BorderLayout());
        // Table des ventes
        venteTable = new JTable(venteTableModel);
        venteTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        venteTable.setOpaque(false);
        venteTable.setBackground(new Color(230, 245, 230, 0));
        venteTable.setGridColor(new Color(200, 200, 200));
        venteTable.setSelectionBackground(new Color(200, 230, 200)); // Vert clair pour la sélection
        venteTable.setSelectionForeground(Color.BLACK); // Texte noir lors de la sélection
        ((DefaultTableCellRenderer)venteTable.getDefaultRenderer(Object.class)).setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(venteTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        // Panel des boutons centré
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = createStyledButton("Nouvelle Vente");
        JButton detailsButton = createStyledButton("Voir Détails");
        JButton refreshButton = createStyledButton("Rafraîchir");
        buttonPanel.add(addButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        // Actions des boutons
        addButton.addActionListener(e -> showNewVenteDialog());
        detailsButton.addActionListener(e -> showVenteDetails(venteTable));
        refreshButton.addActionListener(e -> refreshVenteTable());
        // Rafraîchissement initial
        refreshVenteTable();
        return panel;
    }

    private JPanel createCommandePanel() {
        ImageIcon icon = new ImageIcon("src/resources/commandes.png");
        BackgroundPanel panel = new BackgroundPanel(icon.getImage(), 0.4f, new Dimension(550, 450));
        panel.setLayout(new BorderLayout());
        // Table des commandes
        commandeTable = new JTable(commandeTableModel);
        commandeTable.setOpaque(false);
        commandeTable.setBackground(new Color(230, 245, 230, 0));
        commandeTable.setGridColor(new Color(200, 200, 200));
        commandeTable.setSelectionBackground(new Color(200, 230, 200)); // Vert clair pour la sélection
        commandeTable.setSelectionForeground(Color.BLACK); // Texte noir lors de la sélection
        ((DefaultTableCellRenderer)commandeTable.getDefaultRenderer(Object.class)).setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(commandeTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);
        // Panel des boutons centré
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        JButton addButton = new JButton("Nouvelle Commande");
        JButton detailsButton = new JButton("Voir Détails");
        JButton refreshButton = new JButton("Rafraîchir");
        buttonPanel.add(addButton);
        buttonPanel.add(detailsButton);
        buttonPanel.add(refreshButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        addButton.addActionListener(e -> showNewCommandeDialog());
        detailsButton.addActionListener(e -> showCommandeDetails(commandeTable));
        refreshButton.addActionListener(e -> refreshCommandeTable());
        return panel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 112, 1)); // Primary green
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(120, 30));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(0, 153, 51)); // Lighter green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(0, 112, 1)); // Primary green
            }
        });
        return button;
    }

    private void showAddClientDialog() {
        JDialog dialog = new JDialog(this, "Ajouter un client", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Création des composants
        String[] labels = {"Nom:", "Prénom:", "Téléphone:", "N° Sécurité Sociale:", "Carte Active:", "Taux Réduction (%):"};
        JTextField[] fields = new JTextField[4];
        fields[0] = new JTextField(20);  // Nom
        fields[1] = new JTextField(20);  // Prénom
        fields[2] = new NumberTextField(20);  // Téléphone
        fields[3] = new NumberTextField(20);  // N° Sécu
        JCheckBox carteBox = new JCheckBox();
        JTextField tauxField = new NumberTextField(20);

        // Ajout des composants
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            dialog.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            if (i < 4) dialog.add(fields[i], gbc);
            else if (i == 4) dialog.add(carteBox, gbc);
            else dialog.add(tauxField, gbc);
        }

        // Panel des boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            if (validateClientFields(fields[0], fields[1], fields[2])) {
                try {
                    Client client = new Client(fields[0].getText().trim(), fields[1].getText().trim(), fields[2].getText().trim());
                    client.setNumSecuriteSociale(fields[3].getText().trim());
                    client.setCarteAssuranceActive(carteBox.isSelected());
                    try {
                        double taux = Double.parseDouble(tauxField.getText().trim());
                        if (taux < 0 || taux > 100) throw new IllegalArgumentException("Le taux de réduction doit être entre 0 et 100");
                        client.setTauxReduction(taux / 100.0);
                    } catch (NumberFormatException ex) {
                        client.setTauxReduction(0.0);
                    }
                    
                    dao.ajouterClient(client);
                    refreshClientTable();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Client ajouté avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du client: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private boolean validateClientFields(JTextField nom, JTextField prenom, JTextField telephone) {
        if (nom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (prenom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le prénom est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (telephone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le téléphone est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void showAddMedicamentDialog() {
        JDialog dialog = new JDialog(this, "Ajouter un médicament", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Champs de base avec taille uniforme
        JTextField nomField = new JTextField(20);
        JTextField referenceField = new JTextField(20);
        JSpinner stockSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
        JDateChooser datePeremptionChooser = new JDateChooser();
        datePeremptionChooser.setDateFormatString("dd/MM/yyyy");
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Générique", "Spécifique"});
        JTextField prixField = new NumberTextField(20);
        JComboBox<Medicament.TypeMedicament> categorieBox = new JComboBox<>(Medicament.TypeMedicament.values());

        // Ajustement des composants pour une taille uniforme
        Dimension fieldSize = nomField.getPreferredSize();
        stockSpinner.setPreferredSize(fieldSize);
        datePeremptionChooser.setPreferredSize(fieldSize);
        typeBox.setPreferredSize(fieldSize);
        categorieBox.setPreferredSize(fieldSize);

        // Ajout des composants avec alignement
        int gridy = 0;
        
        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        dialog.add(nomField, gbc);
        gridy++;

        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Référence:"), gbc);
        gbc.gridx = 1;
        dialog.add(referenceField, gbc);
        gridy++;

        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        dialog.add(stockSpinner, gbc);
        gridy++;

        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Date Péremption:"), gbc);
        gbc.gridx = 1;
        dialog.add(datePeremptionChooser, gbc);
        gridy++;

        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        dialog.add(typeBox, gbc);
        gridy++;

        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Prix:"), gbc);
        gbc.gridx = 1;
        dialog.add(prixField, gbc);
        gridy++;

        gbc.gridx = 0; gbc.gridy = gridy;
        dialog.add(new JLabel("Catégorie:"), gbc);
        gbc.gridx = 1;
        dialog.add(categorieBox, gbc);
        gridy++;

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                // Vérification des champs obligatoires
                if (nomField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Le nom est obligatoire");
                }
                if (referenceField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La référence est obligatoire");
                }
                if (datePeremptionChooser.getDate() == null) {
                    throw new IllegalArgumentException("La date de péremption est obligatoire");
                }
                if (prixField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Le prix est obligatoire");
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String datePeremption = sdf.format(datePeremptionChooser.getDate());

                Medicament.TypeMedicament categorie = (Medicament.TypeMedicament) categorieBox.getSelectedItem();
                Medicament medicament = new Medicament(
                    nomField.getText().trim(),
                    referenceField.getText().trim(),
                    (Integer) stockSpinner.getValue(),
                    datePeremption,
                    typeBox.getSelectedItem().toString(),
                    Double.parseDouble(prixField.getText().trim()),
                    categorie
                );

                // Ajout des détails spécifiques selon la catégorie
                switch (categorie) {
                    case COMPRIME:
                        medicament.setDetailsComprime((Integer) stockSpinner.getValue());
                        break;
                    case SIROP:
                        medicament.setDetailsSirop((Integer) stockSpinner.getValue());
                        break;
                    case INJECTABLE:
                        medicament.setDetailsInjectable((Double) stockSpinner.getValue());
                        break;
                }

                dao.ajouterMedicament(medicament);
                refreshMedicamentTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, 
                    "Médicament ajouté avec succès", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Le prix doit être un nombre valide", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = gridy;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void refreshClientTable() {
        SwingUtilities.invokeLater(() -> {
            // Sauvegarder la sélection actuelle
            int selectedRow = clientTable.getSelectedRow();
            String selectedNom = selectedRow >= 0 ? (String) clientTable.getValueAt(selectedRow, 0) : null;
            String selectedPrenom = selectedRow >= 0 ? (String) clientTable.getValueAt(selectedRow, 1) : null;

            clientTableModel.setRowCount(0);
            List<Client> clients = dao.getClients();
            for (Client client : clients) {
                clientTableModel.addRow(new Object[]{
                    client.getNom(),
                    client.getPrenom(),
                    client.getTelephone(),
                    client.getNumSecuriteSociale(),
                    client.isCarteAssuranceActive(),
                    String.format("%.1f", client.getTauxReduction() * 100)
                });
            }

            // Restaurer la sélection si possible
            if (selectedNom != null && selectedPrenom != null) {
                for (int i = 0; i < clientTableModel.getRowCount(); i++) {
                    if (clientTableModel.getValueAt(i, 0).equals(selectedNom) && 
                        clientTableModel.getValueAt(i, 1).equals(selectedPrenom)) {
                        clientTable.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });
    }

    private void refreshMedicamentTable() {
        SwingUtilities.invokeLater(() -> {
            // Sauvegarder la sélection actuelle
            int selectedRow = medicamentTable.getSelectedRow();
            String selectedReference = selectedRow >= 0 ? (String) medicamentTable.getValueAt(selectedRow, 0) : null;

            medicamentTableModel.setRowCount(0);
            List<Medicament> medicaments = dao.getMedicaments();
            for (Medicament med : medicaments) {
                medicamentTableModel.addRow(new Object[]{
                    med.getReference(),
                    med.getNom(),
                    med.getType(),
                    med.getCategorie(),
                    med.getPrix(),
                    med.getQuantiteStock(),
                    med.getDatePeremption()
                });
            }

            // Restaurer la sélection si possible
            if (selectedReference != null) {
                for (int i = 0; i < medicamentTableModel.getRowCount(); i++) {
                    if (medicamentTableModel.getValueAt(i, 0).equals(selectedReference)) {
                        medicamentTable.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });
    }

    private void showClientHistory(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nom = (String) table.getValueAt(selectedRow, 0);
        String prenom = (String) table.getValueAt(selectedRow, 1);
        Client client = dao.rechercherClient(nom, prenom);

        if (client == null) {
            JOptionPane.showMessageDialog(this, "Client non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Historique des achats - " + nom + " " + prenom, true);
        dialog.setLayout(new BorderLayout());

        // Création de la table d'historique
        String[] columns = {"Date", "Médicament", "Quantité", "Prix unitaire", "Total"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable historyTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(historyTable);

        // Remplissage des données
        List<Vente> historique = dao.getHistoriqueAchatsClient(client);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Vente vente : historique) {
            for (LigneVente ligne : vente.getLignesVente()) {
                model.addRow(new Object[]{
                    dateFormat.format(vente.getDate()),
                    ligne.getMedicament().getNom(),
                    ligne.getQuantite(),
                    ligne.getMedicament().getPrix(),
                    ligne.getQuantite() * ligne.getMedicament().getPrix()
                });
            }
        }

        dialog.add(scrollPane, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dialog.dispose());
        dialog.add(closeButton, BorderLayout.SOUTH);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new PharmacieGUI().setVisible(true);
        });
    }

    // Autres méthodes à implémenter...
    private void editSelectedClient(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nom = (String) table.getValueAt(selectedRow, 0);
        String prenom = (String) table.getValueAt(selectedRow, 1);
        Client client = dao.rechercherClient(nom, prenom);

        if (client == null) {
            JOptionPane.showMessageDialog(this, "Client non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Modifier un client", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Création des champs
        JTextField nomField = new JTextField(client.getNom(), 20);
        JTextField prenomField = new JTextField(client.getPrenom(), 20);
        JTextField telephoneField = new NumberTextField(20);
        telephoneField.setText(client.getTelephone());
        JTextField secuField = new NumberTextField(20);
        secuField.setText(client.getNumSecuriteSociale());
        JCheckBox carteBox = new JCheckBox("", client.isCarteAssuranceActive());
        JTextField tauxField = new NumberTextField(20);
        tauxField.setText(String.format("%.1f", client.getTauxReduction() * 100));

        // Ajout des composants
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        dialog.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Prénom:"), gbc);
        gbc.gridx = 1;
        dialog.add(prenomField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Téléphone:"), gbc);
        gbc.gridx = 1;
        dialog.add(telephoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("N° Sécurité Sociale:"), gbc);
        gbc.gridx = 1;
        dialog.add(secuField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        dialog.add(new JLabel("Carte Active:"), gbc);
        gbc.gridx = 1;
        dialog.add(carteBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        dialog.add(new JLabel("Taux Réduction (%):"), gbc);
        gbc.gridx = 1;
        dialog.add(tauxField, gbc);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            if (validateClientFields(nomField, prenomField, telephoneField)) {
                try {
                    client.setNom(nomField.getText().trim());
                    client.setPrenom(prenomField.getText().trim());
                    client.setTelephone(telephoneField.getText().trim());
                    client.setNumSecuriteSociale(secuField.getText().trim());
                    client.setCarteAssuranceActive(carteBox.isSelected());
                    try {
                        double taux = Double.parseDouble(tauxField.getText().trim());
                        if (taux < 0 || taux > 100) {
                            throw new IllegalArgumentException("Le taux de réduction doit être entre 0 et 100");
                        }
                        client.setTauxReduction(taux / 100.0); // Conversion du pourcentage en décimal
                    } catch (NumberFormatException ex) {
                        client.setTauxReduction(0.0);
                    }
                    
                    dao.modifierClient(client);
                    refreshClientTable();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, 
                        "Client modifié avec succès", 
                        "Succès", 
                        JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this,
                        "Erreur lors de la modification du client: " + ex.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void deleteSelectedClient(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Êtes-vous sûr de vouloir supprimer ce client ?",
            "Confirmation de suppression",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String nom = (String) table.getValueAt(selectedRow, 0);
            String prenom = (String) table.getValueAt(selectedRow, 1);
            Client client = dao.rechercherClient(nom, prenom);
            if (client != null) {
                dao.supprimerClient(client);
                refreshClientTable();
            }
        }
    }

    // Autres méthodes utilitaires...
    private void showAddFournisseurDialog() {
        JDialog dialog = new JDialog(this, "Ajouter un fournisseur", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Création des labels avec largeur fixe
        JLabel[] labels = {
            new JLabel("Nom:"),
            new JLabel("Téléphone:"),
            new JLabel("Adresse:"),
            new JLabel("Email:")
        };
        
        // Définir une largeur fixe pour tous les labels
        int maxWidth = 0;
        for (JLabel label : labels) {
            maxWidth = Math.max(maxWidth, label.getPreferredSize().width);
        }
        Dimension labelDim = new Dimension(maxWidth, labels[0].getPreferredSize().height);
        for (JLabel label : labels) {
            label.setPreferredSize(labelDim);
        }

        JTextField nomField = new JTextField(20);
        JTextField telephoneField = new NumberTextField(20);
        JTextField adresseField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Ajout des composants avec alignement
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(labels[0], gbc);
        gbc.gridx = 1;
        dialog.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(labels[1], gbc);
        gbc.gridx = 1;
        dialog.add(telephoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(labels[2], gbc);
        gbc.gridx = 1;
        dialog.add(adresseField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(labels[3], gbc);
        gbc.gridx = 1;
        dialog.add(emailField, gbc);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            if (validateFournisseurFields(nomField, telephoneField, adresseField, emailField)) {
                Fournisseur fournisseur = new Fournisseur(
                    nomField.getText(),
                    telephoneField.getText(),
                    adresseField.getText(),
                    emailField.getText()
                );
                dao.ajouterFournisseur(fournisseur);
                refreshFournisseurTable();
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private boolean validateFournisseurFields(JTextField nom, JTextField telephone, JTextField adresse, JTextField email) {
        if (nom.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (telephone.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le téléphone est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (adresse.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "L'adresse est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (email.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "L'email est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void refreshFournisseurTable() {
        SwingUtilities.invokeLater(() -> {
            // Sauvegarder la sélection actuelle
            int selectedRow = fournisseurTable.getSelectedRow();
            String selectedNom = selectedRow >= 0 ? (String) fournisseurTable.getValueAt(selectedRow, 0) : null;

            fournisseurTableModel.setRowCount(0);
            for (Fournisseur fournisseur : dao.getFournisseurs()) {
                fournisseurTableModel.addRow(new Object[]{
                    fournisseur.getNom(),
                    fournisseur.getTelephone(),
                    fournisseur.getEmail(),
                    fournisseur.getAdresse()
                });
            }

            // Restaurer la sélection si possible
            if (selectedNom != null) {
                for (int i = 0; i < fournisseurTableModel.getRowCount(); i++) {
                    if (fournisseurTableModel.getValueAt(i, 0).equals(selectedNom)) {
                        fournisseurTable.setRowSelectionInterval(i, i);
                        break;
                    }
                }
            }
        });
    }

    private void showNewVenteDialog() {
        // Sélection du client
        List<Client> clients = dao.getClients();
        if (clients.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun client disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Nouvelle Vente", true);
        dialog.setLayout(new BorderLayout());

        // Panel pour la sélection du client avec affichage personnalisé
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<Client> clientCombo = new JComboBox<>(clients.toArray(new Client[0]));
        clientCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Client) {
                    Client client = (Client) value;
                    value = client.getNom() + " " + client.getPrenom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        topPanel.add(new JLabel("Client:"));
        topPanel.add(clientCombo);
        dialog.add(topPanel, BorderLayout.NORTH);

        // Table pour les médicaments de la vente
        String[] columns = {"Médicament", "Quantité", "Prix unitaire", "Total"};
        DefaultTableModel venteModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable venteTable = new JTable(venteModel);
        JScrollPane scrollPane = new JScrollPane(venteTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Panel pour ajouter des médicaments
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        List<Medicament> medicaments = dao.getMedicaments();
        JComboBox<Medicament> medicamentCombo = new JComboBox<>(medicaments.toArray(new Medicament[0]));
        medicamentCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Medicament) {
                    Medicament med = (Medicament) value;
                    value = med.getNom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        JSpinner quantiteSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        JButton addButton = new JButton("Ajouter");
        
        // Créer une vente temporaire pour gérer les calculs
        Client clientInitial = (Client) clientCombo.getSelectedItem();
        final Vente[] venteTemp = {dao.creerVente(clientInitial)};
        
        // Mettre à jour la vente quand le client change
        clientCombo.addActionListener(e -> {
            Client nouveauClient = (Client) clientCombo.getSelectedItem();
            venteTemp[0] = dao.creerVente(nouveauClient);
            // Recréer les lignes de vente avec le nouveau client
            for (int i = 0; i < venteModel.getRowCount(); i++) {
                Medicament med = (Medicament) venteModel.getValueAt(i, 0);
                int qte = (Integer) venteModel.getValueAt(i, 1);
                try {
                    venteTemp[0].ajouterLigne(med, qte);
                } catch (IllegalArgumentException ex) {
                    // Ignorer les erreurs lors de la reconstruction
                }
            }
            // Mettre à jour l'affichage
            updateVenteTable(venteModel, venteTemp[0]);
        });
        
        addButton.addActionListener(e -> {
            Medicament medicament = (Medicament) medicamentCombo.getSelectedItem();
            if (medicament == null) {
                JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner un médicament", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int quantite = (Integer) quantiteSpinner.getValue();
            
            try {
                venteTemp[0].ajouterLigne(medicament, quantite);
                updateVenteTable(venteModel, venteTemp[0]);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        addPanel.add(new JLabel("Médicament:"));
        addPanel.add(medicamentCombo);
        addPanel.add(new JLabel("Quantité:"));
        addPanel.add(quantiteSpinner);
        addPanel.add(addButton);

        // Panel pour les boutons de validation
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            if (venteTemp[0].getLignesVente().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Veuillez ajouter au moins un médicament", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // La vente est déjà créée et sauvegardée dans la DAO
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Vente enregistrée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                refreshVenteTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erreur lors de l'enregistrement de la vente: " + ex.getMessage(), 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Panel principal pour les boutons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(addPanel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void updateVenteTable(DefaultTableModel model, Vente vente) {
        model.setRowCount(0);
        for (LigneVente ligne : vente.getLignesVente()) {
            model.addRow(new Object[]{
                ligne.getMedicament(),
                ligne.getQuantite(),
                ligne.getMedicament().getPrix(),
                ligne.getMedicament().getPrix() * ligne.getQuantite()
            });
        }
    }

    private void refreshVenteTable() {
        venteTableModel.setRowCount(0);
        List<Vente> ventes = dao.getVentes();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Vente vente : ventes) {
            venteTableModel.addRow(new Object[]{
                dateFormat.format(vente.getDate()),
                vente.getClient().getNom() + " " + vente.getClient().getPrenom(),
                vente.getLignesVente().size(),
                vente.getMontantTotal(),
                vente.getMontantApresReduction()
            });
        }
    }

    private void deleteSelectedFournisseur(JTable table) {
        // Implémentation de la suppression du fournisseur sélectionné
    }

    private void deleteSelectedMedicament(JTable table) {
        // Implémentation de la suppression du médicament sélectionné
    }

    private void showPeremptionDialog() {
        // Récupérer les médicaments proches de la péremption (30 jours)
        List<Medicament> medicamentsPerimes = dao.getMedicamentsProchesPeremption(30);

        JDialog dialog = new JDialog(this, "Médicaments proches de la péremption", true);
        dialog.setLayout(new BorderLayout());

        // Créer le modèle de table
        String[] columns = {"Référence", "Nom", "Date de péremption", "Stock", "Prix"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // Créer la table et ajouter les données
        JTable table = new JTable(model);
        for (Medicament med : medicamentsPerimes) {
            model.addRow(new Object[]{
                med.getReference(),
                med.getNom(),
                med.getDatePeremption(),
                med.getQuantiteStock(),
                med.getPrix()
            });
        }

        // Ajouter la table dans un scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Ajouter un bouton pour fermer
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        // Configurer et afficher la boîte de dialogue
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showVenteDetails(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une vente", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer la vente sélectionnée
        List<Vente> ventes = dao.getVentes();
        if (selectedRow >= ventes.size()) {
            JOptionPane.showMessageDialog(this, "Vente non trouvée", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Vente vente = ventes.get(selectedRow);

        JDialog dialog = new JDialog(this, "Détails de la vente", true);
        dialog.setLayout(new BorderLayout());

        // Panel d'informations générales
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        infoPanel.add(new JLabel("Date:"));
        infoPanel.add(new JLabel(dateFormat.format(vente.getDate())));
        
        infoPanel.add(new JLabel("Client:"));
        infoPanel.add(new JLabel(vente.getClient().getNom() + " " + vente.getClient().getPrenom()));
        
        infoPanel.add(new JLabel("Montant Total:"));
        infoPanel.add(new JLabel(String.format("%.2f €", vente.getMontantTotal())));
        
        infoPanel.add(new JLabel("Montant Après Réduction:"));
        infoPanel.add(new JLabel(String.format("%.2f €", vente.getMontantApresReduction())));
        
        dialog.add(infoPanel, BorderLayout.NORTH);

        // Table des détails
        String[] columns = {"Médicament", "Quantité", "Prix unitaire", "Total"};
        DefaultTableModel detailsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1: return Integer.class;
                    case 2:
                    case 3: return Double.class;
                    default: return String.class;
                }
            }
        };
        JTable detailsTable = new JTable(detailsModel);

        // Remplissage des données
        for (LigneVente ligne : vente.getLignesVente()) {
            detailsModel.addRow(new Object[]{
                ligne.getMedicament().getNom(),
                ligne.getQuantite(),
                ligne.getMedicament().getPrix(),
                ligne.getQuantite() * ligne.getMedicament().getPrix()
            });
        }

        dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showCommandeDetails(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une commande", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer la commande sélectionnée
        List<Commande> commandes = dao.getCommandes();
        if (selectedRow >= commandes.size()) {
            JOptionPane.showMessageDialog(this, "Commande non trouvée", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Commande commande = commandes.get(selectedRow);

        JDialog dialog = new JDialog(this, "Détails de la commande", true);
        dialog.setLayout(new BorderLayout());

        // Panel d'informations générales
        JPanel infoPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        infoPanel.add(new JLabel("Date:"));
        infoPanel.add(new JLabel(dateFormat.format(commande.getDate())));
        
        infoPanel.add(new JLabel("Fournisseur:"));
        infoPanel.add(new JLabel(commande.getFournisseur().getNom()));
        
        infoPanel.add(new JLabel("Montant Total:"));
        infoPanel.add(new JLabel(String.format("%.2f €", commande.getMontantTotal())));
        
        infoPanel.add(new JLabel("Statut:"));
        infoPanel.add(new JLabel(commande.getStatut()));
        
        dialog.add(infoPanel, BorderLayout.NORTH);

        // Table des détails
        String[] columns = {"Médicament", "Quantité", "Prix unitaire", "Total"};
        DefaultTableModel detailsModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1: return Integer.class;
                    case 2:
                    case 3: return Double.class;
                    default: return String.class;
                }
            }
        };
        JTable detailsTable = new JTable(detailsModel);

        // Remplissage des données
        for (LigneCommande ligne : commande.getLignesCommande()) {
            detailsModel.addRow(new Object[]{
                ligne.getMedicament().getNom(),
                ligne.getQuantite(),
                ligne.getMedicament().getPrix(),
                ligne.getMontantTotal()
            });
        }

        dialog.add(new JScrollPane(detailsTable), BorderLayout.CENTER);

        // Bouton de fermeture
        JButton closeButton = new JButton("Fermer");
        closeButton.addActionListener(e -> dialog.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void refreshCommandeTable() {
        commandeTableModel.setRowCount(0);
        List<Commande> commandes = dao.getCommandes();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Commande commande : commandes) {
            commandeTableModel.addRow(new Object[]{
                dateFormat.format(commande.getDate()),
                commande.getFournisseur().getNom(),
                commande.getLignesCommande().size(),
                commande.getMontantTotal(),
                commande.getStatut()
            });
        }
    }

    private void showNewCommandeDialog() {
        // Sélection du fournisseur
        List<Fournisseur> fournisseurs = dao.getFournisseurs();
        if (fournisseurs.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun fournisseur disponible", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Nouvelle Commande", true);
        dialog.setLayout(new BorderLayout());

        // Panel pour la sélection du fournisseur avec affichage personnalisé
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<Fournisseur> fournisseurCombo = new JComboBox<>(fournisseurs.toArray(new Fournisseur[0]));
        fournisseurCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Fournisseur) {
                    Fournisseur f = (Fournisseur) value;
                    value = f.getNom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        topPanel.add(new JLabel("Fournisseur:"));
        topPanel.add(fournisseurCombo);
        dialog.add(topPanel, BorderLayout.NORTH);

        // Créer un modèle de table temporaire pour la commande en cours
        String[] columns = {"Médicament", "Quantité", "Prix unitaire", "Total"};
        DefaultTableModel tempModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 1: return Integer.class;
                    case 2:
                    case 3: return Double.class;
                    default: return String.class;
                }
            }
        };
        JTable tempTable = new JTable(tempModel);
        JScrollPane scrollPane = new JScrollPane(tempTable);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Panel pour ajouter des médicaments avec affichage personnalisé
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        List<Medicament> medicaments = dao.getMedicaments();
        JComboBox<Medicament> medicamentCombo = new JComboBox<>(medicaments.toArray(new Medicament[0]));
        medicamentCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Medicament) {
                    Medicament med = (Medicament) value;
                    value = med.getNom();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });
        JSpinner quantiteSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        JButton addButton = new JButton("Ajouter");
        
        addButton.addActionListener(e -> {
            Medicament medicament = (Medicament) medicamentCombo.getSelectedItem();
            if (medicament == null) {
                JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner un médicament", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int quantite = (Integer) quantiteSpinner.getValue();
            tempModel.addRow(new Object[]{
                medicament.getNom(),
                quantite,
                medicament.getPrix(),
                medicament.getPrix() * quantite
            });
        });

        addPanel.add(new JLabel("Médicament:"));
        addPanel.add(medicamentCombo);
        addPanel.add(new JLabel("Quantité:"));
        addPanel.add(quantiteSpinner);
        addPanel.add(addButton);
        dialog.add(addPanel, BorderLayout.SOUTH);

        // Panel pour les boutons de validation
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            if (tempModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(dialog, "Veuillez ajouter au moins un médicament", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Fournisseur fournisseur = (Fournisseur) fournisseurCombo.getSelectedItem();
            if (fournisseur == null) {
                JOptionPane.showMessageDialog(dialog, "Veuillez sélectionner un fournisseur", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Commande commande = dao.creerCommande(fournisseur);

                // Ajout des lignes de commande
                for (int i = 0; i < tempModel.getRowCount(); i++) {
                    String nomMedicament = (String) tempModel.getValueAt(i, 0);
                    int quantite = ((Number) tempModel.getValueAt(i, 1)).intValue();
                    Medicament medicament = medicaments.stream()
                        .filter(m -> m.getNom().equals(nomMedicament))
                        .findFirst()
                        .orElse(null);
                    
                    if (medicament != null) {
                        commande.ajouterLigne(medicament, quantite);
                    }
                }

                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Commande créée avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
                refreshCommandeTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erreur lors de la création de la commande: " + ex.getMessage(), 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Ajouter le panel des boutons
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(addPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(southPanel, BorderLayout.SOUTH);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void editSelectedFournisseur(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un fournisseur", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nom = (String) table.getValueAt(selectedRow, 0); // La colonne 0 contient le nom
        Fournisseur fournisseur = dao.getFournisseurs().stream()
            .filter(f -> f.getNom().equals(nom))
            .findFirst()
            .orElse(null);

        if (fournisseur == null) {
            JOptionPane.showMessageDialog(this, "Fournisseur non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Modifier un fournisseur", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Création des champs
        JTextField nomField = new JTextField(fournisseur.getNom(), 20);
        JTextField telephoneField = new NumberTextField(20);
        telephoneField.setText(fournisseur.getTelephone());
        JTextField adresseField = new JTextField(fournisseur.getAdresse(), 20);
        JTextField emailField = new JTextField(fournisseur.getEmail(), 20);

        // Ajout des composants
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        dialog.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Téléphone:"), gbc);
        gbc.gridx = 1;
        dialog.add(telephoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Adresse:"), gbc);
        gbc.gridx = 1;
        dialog.add(adresseField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        dialog.add(emailField, gbc);

        // Panel pour les boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            if (validateFournisseurFields(nomField, telephoneField, adresseField, emailField)) {
                fournisseur.setNom(nomField.getText().trim());
                fournisseur.setTelephone(telephoneField.getText().trim());
                fournisseur.setAdresse(adresseField.getText().trim());
                fournisseur.setEmail(emailField.getText().trim());
                
                dao.modifierFournisseur(fournisseur);
                refreshFournisseurTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, 
                    "Fournisseur modifié avec succès", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void editSelectedMedicament(JTable table) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String reference = (String) table.getValueAt(selectedRow, 0); // La colonne 0 contient la référence
        Medicament medicament = dao.getMedicaments().stream()
            .filter(m -> m.getReference().equals(reference))
            .findFirst()
            .orElse(null);

        if (medicament == null) {
            JOptionPane.showMessageDialog(this, "Médicament non trouvé", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Modifier un médicament", true);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champs de base
        JTextField nomField = new JTextField(medicament.getNom(), 20);
        JTextField referenceField = new JTextField(medicament.getReference(), 20);
        JSpinner stockSpinner = new JSpinner(new SpinnerNumberModel(medicament.getQuantiteStock(), 0, 10000, 1));
        JDateChooser datePeremptionChooser = new JDateChooser();
        datePeremptionChooser.setDateFormatString("dd/MM/yyyy");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date = sdf.parse(medicament.getDatePeremption());
            datePeremptionChooser.setDate(date);
        } catch (Exception ex) {
            // Si la date ne peut pas être parsée, on laisse la date actuelle
        }
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Générique", "Spécifique"});
        typeBox.setSelectedItem(medicament.getType());
        JTextField prixField = new NumberTextField(20);
        prixField.setText(String.valueOf(medicament.getPrix()));
        JComboBox<Medicament.TypeMedicament> categorieBox = new JComboBox<>(Medicament.TypeMedicament.values());
        categorieBox.setSelectedItem(medicament.getCategorie());

        // Champs spécifiques
        JPanel specificPanel = new JPanel(new CardLayout());
        
        // Panel pour Comprimé
        JPanel comprimePanel = new JPanel(new GridBagLayout());
        JSpinner uniteBoiteSpinner = new JSpinner(new SpinnerNumberModel(
            medicament.getCategorie() == Medicament.TypeMedicament.COMPRIME ? medicament.getUniteBoite() : 0, 
            0, 1000, 1));
        comprimePanel.add(new JLabel("Unités par boîte:"), gbc);
        comprimePanel.add(uniteBoiteSpinner);

        // Panel pour Sirop
        JPanel siropPanel = new JPanel(new GridBagLayout());
        JSpinner contenanceSpinner = new JSpinner(new SpinnerNumberModel(
            medicament.getCategorie() == Medicament.TypeMedicament.SIROP ? medicament.getContenanceMl() : 0, 
            0, 1000, 10));
        siropPanel.add(new JLabel("Contenance (ml):"), gbc);
        siropPanel.add(contenanceSpinner);

        // Panel pour Injectable
        JPanel injectablePanel = new JPanel(new GridBagLayout());
        JSpinner dosageSpinner = new JSpinner(new SpinnerNumberModel(
            medicament.getCategorie() == Medicament.TypeMedicament.INJECTABLE ? medicament.getDosageInjectable() : 0.0, 
            0.0, 100.0, 0.5));
        injectablePanel.add(new JLabel("Dosage (ml):"), gbc);
        injectablePanel.add(dosageSpinner);

        specificPanel.add(comprimePanel, "COMPRIME");
        specificPanel.add(siropPanel, "SIROP");
        specificPanel.add(injectablePanel, "INJECTABLE");

        // Afficher le bon panel selon la catégorie
        CardLayout cl = (CardLayout) specificPanel.getLayout();
        cl.show(specificPanel, medicament.getCategorie().toString());

        // Listener pour changer les champs spécifiques
        categorieBox.addActionListener(e -> {
            cl.show(specificPanel, categorieBox.getSelectedItem().toString());
        });

        // Ajout des composants
        int gridy = 0;
        
        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        dialog.add(nomField, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Référence:"), gbc);
        gbc.gridx = 1;
        dialog.add(referenceField, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Stock:"), gbc);
        gbc.gridx = 1;
        dialog.add(stockSpinner, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Date Péremption:"), gbc);
        gbc.gridx = 1;
        dialog.add(datePeremptionChooser, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        dialog.add(typeBox, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Prix:"), gbc);
        gbc.gridx = 1;
        dialog.add(prixField, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        dialog.add(new JLabel("Catégorie:"), gbc);
        gbc.gridx = 1;
        dialog.add(categorieBox, gbc);

        gbc.gridx = 0; gbc.gridy = gridy++;
        gbc.gridwidth = 2;
        dialog.add(specificPanel, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Enregistrer");
        JButton cancelButton = new JButton("Annuler");

        saveButton.addActionListener(e -> {
            try {
                // Validation des champs
                if (nomField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Le nom est obligatoire");
                }
                if (referenceField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("La référence est obligatoire");
                }
                if (datePeremptionChooser.getDate() == null) {
                    throw new IllegalArgumentException("La date de péremption est obligatoire");
                }
                if (prixField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("Le prix est obligatoire");
                }

                // Mise à jour des champs de base
                medicament.setNom(nomField.getText().trim());
                medicament.setReference(referenceField.getText().trim());
                medicament.setQuantiteStock((Integer) stockSpinner.getValue());
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                medicament.setDatePeremption(sdf.format(datePeremptionChooser.getDate()));
                medicament.setType(typeBox.getSelectedItem().toString());
                medicament.setPrix(Double.parseDouble(prixField.getText().trim()));
                medicament.setCategorie((Medicament.TypeMedicament) categorieBox.getSelectedItem());

                // Mise à jour des détails spécifiques selon la catégorie
                switch (medicament.getCategorie()) {
                    case COMPRIME:
                        medicament.setDetailsComprime((Integer) uniteBoiteSpinner.getValue());
                        break;
                    case SIROP:
                        medicament.setDetailsSirop((Integer) contenanceSpinner.getValue());
                        break;
                    case INJECTABLE:
                        medicament.setDetailsInjectable((Double) dosageSpinner.getValue());
                        break;
                }

                dao.modifierMedicament(medicament);
                refreshMedicamentTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, 
                    "Médicament modifié avec succès", 
                    "Succès", 
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Veuillez vérifier les valeurs numériques", 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    ex.getMessage(), 
                    "Erreur", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        gbc.gridx = 0; gbc.gridy = gridy++;
        gbc.gridwidth = 2;
        dialog.add(buttonPanel, gbc);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
} 