import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OriginalNewsApp extends Application {
    
    private VBox newsContainer;
    private ListView<String> categoryListView;
    private Label statusLabel;
    private ProgressIndicator loadingIndicator;
    private Button refreshButton;
    private Label categoryTitleLabel;
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Nexity60 - Smart Summarized News Reader");
        
        // Create main layout
        BorderPane root = new BorderPane();
        
        // Left sidebar for categories
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);
        
        // Main content area
        VBox mainContent = createMainContent();
        root.setCenter(mainContent);
        
        // Bottom status bar
        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);
        
        // Load trending news by default
        loadNews("Trending");
        
        Scene scene = new Scene(root, 1000, 700);
        
        // Add CSS styling
        scene.getStylesheets().add("data:text/css," + getCSS());
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(200);
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        
        // App title in sidebar
        Label appTitle = new Label("Nexity60");
        appTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");
        
        Label appSubtitle = new Label("Smart News Reader");
        appSubtitle.setStyle("-fx-font-size: 12px; -fx-text-fill: #bdc3c7;");
        
        // Categories section
        Label categoriesLabel = new Label("CATEGORIES");
        categoriesLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #95a5a6; -fx-padding: 20 0 10 0;");
        
        categoryListView = new ListView<>();
        categoryListView.getItems().addAll(
            "Trending",
            "Technology", 
            "World News",
            "Business",
            "Sports",
            "Health",
            "Science",
            "Entertainment"
        );
        categoryListView.getSelectionModel().select(0); // Select "Trending" by default
        categoryListView.setPrefHeight(300);
        categoryListView.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        
        // Category selection handler
        categoryListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                loadNews(newVal);
            }
        });
        
        sidebar.getChildren().addAll(appTitle, appSubtitle, categoriesLabel, categoryListView);
        return sidebar;
    }
    
    private VBox createMainContent() {
        VBox mainContent = new VBox(10);
        mainContent.setPadding(new Insets(20));
        mainContent.setStyle("-fx-background-color: #ecf0f1;");
        
        // Header with category title and refresh button
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(10);
        
        categoryTitleLabel = new Label("Trending News");
        categoryTitleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 16;");
        refreshButton.setOnAction(e -> {
            String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
            if (selectedCategory != null) {
                loadNews(selectedCategory);
            }
        });
        
        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setVisible(false);
        loadingIndicator.setPrefSize(20, 20);
        
        header.getChildren().addAll(categoryTitleLabel, spacer, loadingIndicator, refreshButton);
        
        // News container with scroll
        newsContainer = new VBox(15);
        newsContainer.setPadding(new Insets(20, 0, 0, 0));
        
        ScrollPane scrollPane = new ScrollPane(newsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        mainContent.getChildren().addAll(header, scrollPane);
        return mainContent;
    }
    
    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(10));
        statusBar.setStyle("-fx-background-color: #34495e;");
        
        statusLabel = new Label("Ready - Select a category to load news");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        
        statusBar.getChildren().add(statusLabel);
        return statusBar;
    }
    
    private void loadNews(String category) {
        // Update UI
        categoryTitleLabel.setText(category + " News");
        loadingIndicator.setVisible(true);
        refreshButton.setDisable(true);
        statusLabel.setText("Loading " + category.toLowerCase() + " news...");
        
        // Clear existing news
        newsContainer.getChildren().clear();
        
        // Create background task to simulate loading
        Task<List<NewsArticle>> task = new Task<List<NewsArticle>>() {
            @Override
            protected List<NewsArticle> call() throws Exception {
                // Simulate network delay
                Thread.sleep(1000);
                return getNewsForCategory(category);
            }
        };
        
        task.setOnSucceeded(e -> {
            List<NewsArticle> articles = task.getValue();
            
            Platform.runLater(() -> {
                // Add news cards to container
                for (NewsArticle article : articles) {
                    VBox newsCard = createNewsCard(article);
                    newsContainer.getChildren().add(newsCard);
                }
                
                // Update UI
                loadingIndicator.setVisible(false);
                refreshButton.setDisable(false);
                statusLabel.setText("Loaded " + articles.size() + " articles for " + category);
            });
        });
        
        task.setOnFailed(e -> {
            Platform.runLater(() -> {
                loadingIndicator.setVisible(false);
                refreshButton.setDisable(false);
                statusLabel.setText("Failed to load news for " + category);
            });
        });
        
        // Run task in background thread
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    private VBox createNewsCard(NewsArticle article) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-width: 1px; -fx-background-radius: 5px; -fx-border-radius: 5px;");
        
        // Title
        Label titleLabel = new Label(article.getTitle());
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        titleLabel.setWrapText(true);
        
        // Summary
        Label summaryLabel = new Label(article.getSummary());
        summaryLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #7f8c8d;");
        summaryLabel.setWrapText(true);
        
        // Footer with time and category
        HBox footer = new HBox();
        footer.setSpacing(15);
        footer.setAlignment(Pos.CENTER_LEFT);
        
        Label timeLabel = new Label("📅 " + article.getPublishedDate().format(DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm")));
        timeLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
        
        Label categoryLabel = new Label("📂 " + article.getCategory());
        categoryLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #95a5a6;");
        
        Label readTimeLabel = new Label("⏱ 60 sec read");
        readTimeLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #e74c3c;");
        
        footer.getChildren().addAll(timeLabel, categoryLabel, readTimeLabel);
        
        // Read Full Article button
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.setPadding(new Insets(10, 0, 0, 0));
        
        Button readFullButton = new Button("🔗 Read Full Article");
        readFullButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 16; -fx-background-radius: 4px; -fx-cursor: hand;");
        readFullButton.setOnMouseEntered(e -> readFullButton.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 8 16; -fx-background-radius: 4px; -fx-cursor: hand;"));
        readFullButton.setOnMouseExited(e -> readFullButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-padding: 8 16; -fx-background-radius: 4px; -fx-cursor: hand;"));
        
        readFullButton.setOnAction(e -> {
            try {
                // Update status
                statusLabel.setText("Opening article: " + article.getTitle());
                
                // Open URL in default browser
                String url = article.getUrl();
                String os = System.getProperty("os.name").toLowerCase();
                
                if (os.contains("win")) {
                    new ProcessBuilder("cmd", "/c", "start", url).start();
                } else if (os.contains("mac")) {
                    new ProcessBuilder("open", url).start();
                } else if (os.contains("nix") || os.contains("nux")) {
                    new ProcessBuilder("xdg-open", url).start();
                }
                
                // Show confirmation
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Article Opened");
                    alert.setHeaderText(null);
                    alert.setContentText("Opening article in your default browser:\n\n" + article.getTitle());
                    alert.showAndWait();
                    statusLabel.setText("Article opened in browser");
                });
                
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Unable to open article");
                    alert.setContentText("Could not open the article in your browser.\n\nURL: " + article.getUrl());
                    alert.showAndWait();
                    statusLabel.setText("Failed to open article");
                });
            }
        });
        
        buttonContainer.getChildren().add(readFullButton);
        
        card.getChildren().addAll(titleLabel, summaryLabel, footer, buttonContainer);
        return card;
    }
    
    private List<NewsArticle> getNewsForCategory(String category) {
        List<NewsArticle> articles = new ArrayList<>();
        
        switch (category) {
            case "Trending":
                articles.add(new NewsArticle(
                    "Breaking: Global AI Summit Announces Revolutionary Breakthroughs",
                    "World leaders and tech giants gather for the largest AI summit in history, announcing groundbreaking developments in artificial intelligence that promise to transform healthcare, education, and climate science. The summit reveals new partnerships between nations and private companies to ensure responsible AI development while accelerating innovation in critical areas.",
                    "https://www.bbc.com/news/technology",
                    LocalDateTime.now().minusMinutes(30),
                    "Technology"
                ));
                
                articles.add(new NewsArticle(
                    "Climate Emergency: Antarctic Ice Sheet Shows Unexpected Recovery",
                    "Surprising new satellite data reveals sections of the Antarctic ice sheet are thickening faster than expected, offering hope in the fight against climate change. Scientists attribute this unexpected development to new ocean current patterns and reduced industrial emissions. However, experts warn that other regions still face critical threats requiring immediate action.",
                    "https://www.bbc.com/news/science-environment",
                    LocalDateTime.now().minusHours(1),
                    "World"
                ));
                
                articles.add(new NewsArticle(
                    "Market Alert: Renewable Energy Stocks Surge 40% Following Policy Changes",
                    "Global renewable energy stocks experience unprecedented growth as governments worldwide implement new green energy policies. Solar and wind companies lead the surge with record-breaking quarterly earnings. Investment analysts predict this trend will continue as nations accelerate their transition to sustainable energy sources.",
                    "https://www.reuters.com/business/energy",
                    LocalDateTime.now().minusHours(2),
                    "Business"
                ));
                break;
                
            case "Technology":
                articles.add(new NewsArticle(
                    "Quantum Internet Achieves Milestone: First Inter-City Connection Established",
                    "Scientists successfully establish the world's first quantum internet connection between two major cities, marking a revolutionary step toward ultra-secure communications. The quantum network promises unbreakable encryption and instantaneous data transfer. Major tech companies are already investing billions in quantum infrastructure development.",
                    "https://www.nature.com/subjects/quantum-information",
                    LocalDateTime.now().minusHours(1),
                    "Technology"
                ));
                
                articles.add(new NewsArticle(
                    "Neural Implants Allow Paralyzed Patients to Control Devices with Thoughts",
                    "Breakthrough medical technology enables paralyzed patients to control computers and robotic devices using only their thoughts. The neural implant technology shows 98% accuracy in clinical trials. This advancement opens new possibilities for treating spinal cord injuries and neurodegenerative diseases.",
                    "https://www.technologyreview.com/",
                    LocalDateTime.now().minusHours(3),
                    "Technology"
                ));
                break;
                
            case "World News":
                articles.add(new NewsArticle(
                    "International Peace Treaty Signed: Three-Decade Conflict Officially Ends",
                    "Historic peace agreement brings an end to a 30-year regional conflict, with international mediators facilitating comprehensive terms for reconstruction and reconciliation. The treaty includes provisions for economic cooperation, cultural exchange, and joint infrastructure projects. World leaders praise this diplomatic achievement as a model for conflict resolution.",
                    "https://www.un.org/en/",
                    LocalDateTime.now().minusHours(2),
                    "World"
                ));
                break;
                
            case "Business":
                articles.add(new NewsArticle(
                    "Cryptocurrency Regulation Framework Adopted by G20 Nations",
                    "The world's largest economies agree on comprehensive cryptocurrency regulations, providing clarity for digital asset markets. The framework addresses consumer protection, anti-money laundering measures, and taxation policies. Crypto markets respond positively to the regulatory certainty, with major currencies gaining value.",
                    "https://www.bloomberg.com/crypto",
                    LocalDateTime.now().minusHours(4),
                    "Business"
                ));
                break;
                
            case "Sports":
                articles.add(new NewsArticle(
                    "Olympic Committee Announces New Sustainable Games Format",
                    "Revolutionary changes to Olympic Games focus on sustainability and cost reduction. The new format emphasizes existing venues, renewable energy, and reduced environmental impact. Athletes and environmental groups praise the initiative as a necessary evolution for the world's premier sporting event.",
                    "https://www.espn.com/olympics/",
                    LocalDateTime.now().minusHours(5),
                    "Sports"
                ));
                break;
                
            case "Health":
                articles.add(new NewsArticle(
                    "Universal Cancer Vaccine Shows 95% Success Rate in Phase 3 Trials",
                    "Groundbreaking cancer vaccine demonstrates remarkable success in preventing multiple types of cancer. The universal vaccine works by training the immune system to recognize and destroy cancer cells before tumors can form. Medical experts call this the most significant cancer prevention breakthrough in decades.",
                    "https://www.nejm.org/",
                    LocalDateTime.now().minusHours(3),
                    "Health"
                ));
                break;
                
            case "Science":
                articles.add(new NewsArticle(
                    "Mars Colony Construction Begins: First Permanent Structures Deployed",
                    "Space agencies initiate construction of humanity's first permanent Mars settlement using advanced robotics and 3D printing technology. The modular habitats will support initial crews of 50 people. This historic milestone marks the beginning of humanity's multi-planetary future.",
                    "https://www.nasa.gov/news/",
                    LocalDateTime.now().minusHours(6),
                    "Science"
                ));
                break;
                
            case "Entertainment":
                articles.add(new NewsArticle(
                    "Virtual Reality Cinema Transforms Movie Experience with Full Sensory Immersion",
                    "Revolutionary VR cinema technology allows audiences to experience movies with all five senses. The immersive technology includes haptic feedback, scent systems, and spatial audio. Major film studios are already producing content specifically for this new medium.",
                    "https://variety.com/v/digital/",
                    LocalDateTime.now().minusHours(4),
                    "Entertainment"
                ));
                break;
        }
        
        return articles;
    }
    
    private String getCSS() {
        return ".list-view { " +
               "-fx-background-color: transparent; " +
               "} " +
               ".list-cell { " +
               "-fx-background-color: transparent; " +
               "-fx-text-fill: #ecf0f1; " +
               "-fx-padding: 8 12; " +
               "-fx-font-size: 13px; " +
               "} " +
               ".list-cell:selected { " +
               "-fx-background-color: #3498db; " +
               "-fx-text-fill: white; " +
               "} " +
               ".list-cell:hover { " +
               "-fx-background-color: #34495e; " +
               "} " +
               ".scroll-pane { " +
               "-fx-background-color: transparent; " +
               "} " +
               ".scroll-pane .viewport { " +
               "-fx-background-color: transparent; " +
               "}";
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}

// NewsArticle class
class NewsArticle {
    private String title;
    private String summary;
    private String url;
    private LocalDateTime publishedDate;
    private String category;
    
    public NewsArticle(String title, String summary, String url, LocalDateTime publishedDate, String category) {
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.publishedDate = publishedDate;
        this.category = category;
    }
    
    // Getters
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getUrl() { return url; }
    public LocalDateTime getPublishedDate() { return publishedDate; }
    public String getCategory() { return category; }
}
