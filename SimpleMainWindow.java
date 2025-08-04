package ui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.NewsArticle;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SimpleMainWindow extends Application {
    
    private VBox newsContainer;
    private ComboBox<String> categoryComboBox;
    private Label statusLabel;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Nexity60 - Smart News Reader");
        
        // Create main layout
        BorderPane root = new BorderPane();
        
        // Header
        VBox header = createHeader();
        root.setTop(header);
        
        // News content area
        ScrollPane scrollPane = new ScrollPane();
        newsContainer = new VBox(10);
        newsContainer.setPadding(new Insets(10));
        scrollPane.setContent(newsContainer);
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);
        
        // Status bar
        statusLabel = new Label("Ready");
        statusLabel.setPadding(new Insets(5));
        root.setBottom(statusLabel);
        
        // Load sample news
        loadSampleNews();
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createHeader() {
        VBox header = new VBox(10);
        header.setPadding(new Insets(15));
        header.setStyle("-fx-background-color: #2c3e50;");
        
        Label titleLabel = new Label("Nexity60");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label subtitleLabel = new Label("Smart Summarized News Reader");
        subtitleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");
        
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER_LEFT);
        
        Label categoryLabel = new Label("Category:");
        categoryLabel.setStyle("-fx-text-fill: white;");
        
        categoryComboBox = new ComboBox<>();
        categoryComboBox.getItems().addAll("Technology", "World", "Business", "Sports", "Health");
        categoryComboBox.setValue("Technology");
        categoryComboBox.setOnAction(e -> loadSampleNews());
        
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> {
            statusLabel.setText("Refreshing news...");
            loadSampleNews();
            statusLabel.setText("News updated");
        });
        
        controls.getChildren().addAll(categoryLabel, categoryComboBox, refreshButton);
        
        header.getChildren().addAll(titleLabel, subtitleLabel, controls);
        return header;
    }
    
    private void loadSampleNews() {
        newsContainer.getChildren().clear();
        
        String category = categoryComboBox.getValue();
        List<NewsArticle> articles = generateSampleNews(category);
        
        for (NewsArticle article : articles) {
            VBox newsCard = createNewsCard(article);
            newsContainer.getChildren().add(newsCard);
        }
        
        statusLabel.setText("Loaded " + articles.size() + " articles for " + category);
    }
    
    private VBox createNewsCard(NewsArticle article) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-width: 1px;");
        
        Label titleLabel = new Label(article.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        titleLabel.setWrapText(true);
        
        Label summaryLabel = new Label(article.getSummary());
        summaryLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        summaryLabel.setWrapText(true);
        
        Label timeLabel = new Label("Published: " + article.getPublishedDate().toString());
        timeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #999;");
        
        card.getChildren().addAll(titleLabel, summaryLabel, timeLabel);
        return card;
    }
    
    private List<NewsArticle> generateSampleNews(String category) {
        List<NewsArticle> articles = new ArrayList<>();
        
        switch (category) {
            case "Technology":
                articles.add(new NewsArticle(
                    "AI Revolution Continues",
                    "Artificial intelligence advances are transforming industries worldwide. New breakthroughs in machine learning are enabling more sophisticated applications across healthcare, finance, and transportation sectors.",
                    "https://example.com/ai-revolution",
                    LocalDateTime.now().minusHours(2),
                    "Technology"
                ));
                articles.add(new NewsArticle(
                    "Quantum Computing Milestone",
                    "Researchers achieve new quantum computing milestone with 1000-qubit processor. This advancement brings quantum computers closer to solving complex real-world problems in cryptography and optimization.",
                    "https://example.com/quantum-milestone",
                    LocalDateTime.now().minusHours(4),
                    "Technology"
                ));
                break;
                
            case "World":
                articles.add(new NewsArticle(
                    "Climate Summit Outcomes",
                    "Global leaders reach consensus on new climate action framework. The agreement includes binding commitments for carbon reduction and renewable energy investment over the next decade.",
                    "https://example.com/climate-summit",
                    LocalDateTime.now().minusHours(1),
                    "World"
                ));
                articles.add(new NewsArticle(
                    "International Trade Developments",
                    "New trade agreements between major economies promise to boost global commerce. The deals focus on reducing tariffs and improving supply chain cooperation.",
                    "https://example.com/trade-developments",
                    LocalDateTime.now().minusHours(3),
                    "World"
                ));
                break;
                
            case "Business":
                articles.add(new NewsArticle(
                    "Stock Market Reaches New Heights",
                    "Major indices hit record highs amid strong corporate earnings reports. Technology and healthcare sectors lead the rally with impressive quarterly results.",
                    "https://example.com/stock-market",
                    LocalDateTime.now().minusHours(1),
                    "Business"
                ));
                articles.add(new NewsArticle(
                    "Startup Funding Surge",
                    "Venture capital investment in startups reaches unprecedented levels. Fintech and green energy companies attract the largest funding rounds this quarter.",
                    "https://example.com/startup-funding",
                    LocalDateTime.now().minusHours(5),
                    "Business"
                ));
                break;
                
            default:
                articles.add(new NewsArticle(
                    "Sample News Article",
                    "This is a sample news article for the " + category + " category. The Nexity60 app provides quick summaries of the latest news stories.",
                    "https://example.com/sample",
                    LocalDateTime.now(),
                    category
                ));
        }
        
        return articles;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
