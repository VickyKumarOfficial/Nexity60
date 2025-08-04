import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SimpleNexity60 extends JFrame {
    private JComboBox<String> categoryBox;
    private JPanel newsPanel;
    private JLabel statusLabel;
    
    public SimpleNexity60() {
        setTitle("Nexity60 - Smart News Reader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        initComponents();
        loadSampleNews("Technology");
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout());
        headerPanel.setBackground(new Color(52, 73, 94));
        
        JLabel titleLabel = new JLabel("Nexity60");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        
        categoryBox = new JComboBox<>(new String[]{"Technology", "World", "Business", "Sports", "Health"});
        
        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadSampleNews((String)categoryBox.getSelectedItem());
            }
        });
        
        headerPanel.add(titleLabel);
        headerPanel.add(new JLabel("   Category: "));
        headerPanel.add(categoryBox);
        headerPanel.add(refreshBtn);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // News area
        newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(newsPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        // Status
        statusLabel = new JLabel("Ready");
        add(statusLabel, BorderLayout.SOUTH);
    }
    
    private void loadSampleNews(String category) {
        newsPanel.removeAll();
        
        List<String[]> articles = getSampleArticles(category);
        
        for (String[] article : articles) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createTitledBorder(article[0]));
            
            JTextArea content = new JTextArea(article[1]);
            content.setLineWrap(true);
            content.setWrapStyleWord(true);
            content.setEditable(false);
            content.setRows(3);
            
            JLabel timeLabel = new JLabel("Published: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd HH:mm")));
            timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            
            card.add(content);
            card.add(timeLabel);
            
            newsPanel.add(card);
        }
        
        statusLabel.setText("Loaded " + articles.size() + " articles for " + category);
        newsPanel.revalidate();
        newsPanel.repaint();
    }
    
    private List<String[]> getSampleArticles(String category) {
        List<String[]> articles = new ArrayList<>();
        
        if ("Technology".equals(category)) {
            articles.add(new String[]{"AI Breakthrough in Healthcare", 
                "New AI system demonstrates 95% accuracy in early disease detection. The technology uses advanced machine learning algorithms to analyze medical images and identify potential health issues before symptoms appear. Hospitals worldwide are beginning to adopt this revolutionary diagnostic tool."});
            articles.add(new String[]{"Quantum Computing Milestone", 
                "Scientists achieve quantum supremacy with 1000-qubit processor. This breakthrough enables solving complex problems in minutes that would take classical computers thousands of years. Applications include drug discovery, financial modeling, and climate simulation."});
            articles.add(new String[]{"5G Network Expansion Accelerates", 
                "Global 5G coverage reaches 60% of urban areas, enabling new applications in IoT, autonomous vehicles, and remote work. The improved connectivity is transforming industries and creating new business opportunities worldwide."});
        } else if ("World".equals(category)) {
            articles.add(new String[]{"Climate Summit Reaches Agreement", 
                "195 countries commit to net-zero emissions by 2050. The historic agreement includes $500 billion in climate financing and new frameworks for international cooperation on renewable energy projects."});
            articles.add(new String[]{"International Space Collaboration", 
                "New space station module launches successfully. The project involves 15 countries working together on advanced research in microgravity, medical studies, and Earth observation technologies."});
        } else if ("Business".equals(category)) {
            articles.add(new String[]{"Stock Markets Hit Record Highs", 
                "Global markets surge on strong economic data. Technology and renewable energy sectors lead gains with impressive quarterly earnings. Unemployment drops to historic lows while consumer confidence remains strong."});
            articles.add(new String[]{"Green Energy Investment Soars", 
                "Renewable energy attracts $300 billion in new investment. Solar and wind projects dominate funding as costs continue to decline and efficiency improves. The transition to clean energy accelerates globally."});
        } else if ("Sports".equals(category)) {
            articles.add(new String[]{"Olympic Records Broken", 
                "Athletes set 15 new world records at latest games. Advanced training techniques and sports science contribute to unprecedented performances. New technologies in equipment design enable athletes to reach new heights."});
        } else { // Health
            articles.add(new String[]{"Gene Therapy Success", 
                "Revolutionary treatment cures rare genetic disease. Clinical trials show 90% success rate with minimal side effects. The breakthrough offers hope for treating thousands of genetic conditions affecting millions worldwide."});
        }
        
        return articles;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleNexity60().setVisible(true);
            }
        });
    }
}
