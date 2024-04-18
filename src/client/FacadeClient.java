package client;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import data.Movie;
import source.Mediator;

public class FacadeClient{
    private static Mediator mediator = Mediator.getInstance();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Application de recherche de films");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Recherche par:");
        JComboBox<String> searchOptions = new JComboBox<>(new String[]{"Titre de film", "Nom d'acteur"});
        JTextField inputField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        searchPanel.add(searchLabel);
        searchPanel.add(searchOptions);
        searchPanel.add(inputField);
        searchPanel.add(searchButton);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableModel.addColumn("Release Date");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Distributor");
        tableModel.addColumn("Budget");
        tableModel.addColumn("USA Revenue");
        tableModel.addColumn("Worldwide Revenue");
        tableModel.addColumn("Director");
        tableModel.addColumn("Actor");
        tableModel.addColumn("Producer");
        tableModel.addColumn("Summary");
        JTable resultTable = new JTable(tableModel);

        // Définir une hauteur de ligne fixe pour afficher tout le contenu
        resultTable.setRowHeight(100); // Changer cette valeur selon vos besoins

        // Utiliser un renderer pour rendre le texte multiligne
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(SwingConstants.TOP);
        resultTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(resultTable);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = inputField.getText();
                boolean caseSensitive = true;
                ArrayList<Movie> movies = new ArrayList<>();
                if (searchOptions.getSelectedIndex() == 0) {
                    movies = mediator.getMoviesByMovieTitle(search, caseSensitive);
                } else {
                    movies = mediator.getMoviesByActorName(search, caseSensitive);
                }

                tableModel.setRowCount(0); // Clear previous rows
                for (Movie movie : movies) {
                    Object[] rowData = {
                            movie.getTitle(),
                            movie.getReleaseDate(),
                            movie.getGenre(),
                            movie.getDistributor(),
                            movie.getBudget(),
                            movie.getUsaRevenue(),
                            movie.getWorldwideRevenue(),
                            movie.getDirectors(),
                            movie.getActors(),
                            movie.getProducers(),
                            movie.getSummary()
                    };
                    tableModel.addRow(rowData);
                }
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
