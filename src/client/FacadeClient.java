package client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import data.Movie;
import source.Mediator;

public class FacadeClient {
    private static final Mediator mediator = Mediator.getInstance();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FacadeClient::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Application de recherche de films");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        JPanel panel = new JPanel(new BorderLayout());
        frame.add(panel);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel searchLabel = new JLabel("Recherche par :");
        JComboBox<String> searchOptions = new JComboBox<>(new String[]{"Titre de film", "Nom d'acteur"});
        JTextField inputField = new JTextField(20);
        JButton searchButton = new JButton("Rechercher");
        
        searchPanel.add(searchLabel);
        searchPanel.add(searchOptions);
        searchPanel.add(inputField);
        searchPanel.add(searchButton);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Titre");
        tableModel.addColumn("Date de sortie");
        tableModel.addColumn("Genre");
        tableModel.addColumn("Distributeur");
        tableModel.addColumn("Budget");
        tableModel.addColumn("Revenus USA");
        tableModel.addColumn("Revenus mondiaux");
        tableModel.addColumn("Réalisateurs");
        tableModel.addColumn("Acteurs");
        tableModel.addColumn("Producteurs");
        tableModel.addColumn("Résumé");

        JTable resultTable = new JTable(tableModel);
        resultTable.setRowHeight(200); // Ajustez selon vos besoins

        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setVerticalAlignment(SwingConstants.TOP); // Pour le rendu multiligne
        resultTable.setDefaultRenderer(Object.class, renderer);

        JScrollPane scrollPane = new JScrollPane(resultTable);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String search = inputField.getText();
                if (search == null || search.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Veuillez entrer un terme de recherche.", "Alerte", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean caseSensitive = true;
                ArrayList<Movie> movies = new ArrayList<>();
                if (searchOptions.getSelectedIndex() == 0) {
                    movies = mediator.getMoviesByMovieTitle(search, caseSensitive);
                } else {
                    movies = mediator.getMoviesByActorName(search, caseSensitive);
                }

                tableModel.setRowCount(0); // Efface les lignes précédentes

                if (movies.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Aucun résultat trouvé pour \"" + search + "\".", "Information", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    for (Movie movie : movies) {
                        Object[] rowData = {
                            movie.getStringTitle(),
                            movie.getStringReleaseDate(),
                            movie.getStringGenre(),
                            movie.getStringDistributor(),
                            movie.getStringBudget(),
                            movie.getStringUsaRevenue(),
                            movie.getStringWorldwideRevenue(),
                            movie.getStringHTMLDirectors(),
                            movie.getStringHTMLActors(),
                            movie.getStringHTMLProducers(),
                            movie.getSummary()
                        };
                        tableModel.addRow(rowData);
                    }
                }
            }
        });

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
