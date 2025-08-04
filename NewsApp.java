import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;

public class NewsApp extends JFrame {
    private JComboBox<String> categoryComboBox;
    private JPanel newsPanel;
    private JScrollPane scrollPane;
    private JLabel statusLabel;
    
    public NewsApp() {
        setupUI();
        loadNews("Technology"); // Load default news
    }
    
    private void setupUI() {
        setTitle("Nexity60 - Smart News Reader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Main layout
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // News content panel
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        newsPanel.setBackground(Color.WHITE);
        
        scrollPane = new JScrollPane(newsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
        
        // Status bar
        statusLabel = new JLabel("Ready - Nexity60 Smart Reader");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        statusLabel.setBackground(new Color(240, 240, 240));
        statusLabel.setOpaque(true);
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(44, 62, 80));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Title section
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(44, 62, 80));
        
        JLabel titleLabel = new JLabel("Nexity60");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Smart Summarized News Reader");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createHorizontalStrut(15));
        titlePanel.add(subtitleLabel);
        
        // Controls section
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlsPanel.setBackground(new Color(44, 62, 80));
        
        JLabel categoryLabel = new JLabel("Category:");
        categoryLabel.setForeground(Color.WHITE);
        categoryLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        
        categoryComboBox = new JComboBox<>(new String[]{
            "Technology", "World", "Business", "Sports", "Health"
        });
        categoryComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        categoryComboBox.setPreferredSize(new Dimension(120, 30));
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setFont(new Font("Arial", Font.PLAIN, 14));
        refreshButton.setPreferredSize(new Dimension(100, 30));
        refreshButton.setBackground(new Color(52, 152, 219));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        
        // Action listeners
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                loadNews(selectedCategory);
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                statusLabel.setText("Refreshing news for " + selectedCategory + "...");
                loadNews(selectedCategory);
            }
        });
        
        controlsPanel.add(categoryLabel);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(categoryComboBox);
        controlsPanel.add(Box.createHorizontalStrut(15));
        controlsPanel.add(refreshButton);
        
        headerPanel.add(titlePanel, BorderLayout.WEST);
        headerPanel.add(controlsPanel, BorderLayout.EAST);
        
        return headerPanel;
    }
    
    private void loadNews(String category) {
        // Clear existing news
        newsPanel.removeAll();
        
        // Get sample news
        List<NewsArticle> articles = NewsService.getSampleNews(category);
        
        // Create news cards
        for (NewsArticle article : articles) {
            JPanel newsCard = createNewsCard(article);
            newsPanel.add(newsCard);
            newsPanel.add(Box.createVerticalStrut(10));
        }
        
        // Update status
        statusLabel.setText("Loaded " + articles.size() + " articles for " + category + " category");
        
        // Refresh the display
        newsPanel.revalidate();
        newsPanel.repaint();
        scrollPane.getVerticalScrollBar().setValue(0);
    }
    
    private JPanel createNewsCard(NewsArticle article) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, card.getPreferredSize().height));
        
        // Title
        JLabel titleLabel = new JLabel("<html><b>" + article.getTitle() + "</b></html>");
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Summary
        JTextArea summaryArea = new JTextArea(article.getSummary());
        summaryArea.setFont(new Font("Arial", Font.PLAIN, 13));
        summaryArea.setForeground(new Color(102, 102, 102));
        summaryArea.setLineWrap(true);
        summaryArea.setWrapStyleWord(true);
        summaryArea.setEditable(false);
        summaryArea.setOpaque(false);
        summaryArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Date and category
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
        String dateText = article.getPublishedDate().format(formatter);
        JLabel dateLabel = new JLabel("Published: " + dateText + " | Category: " + article.getCategory());
        dateLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        dateLabel.setForeground(new Color(153, 153, 153));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Add components to card
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(summaryArea);
        card.add(Box.createVerticalStrut(8));
        card.add(dateLabel);
        
        return card;
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            // Use default look and feel if system one fails
            System.out.println("Using default look and feel");
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewsApp().setVisible(true);
            }
        });
    }
}
