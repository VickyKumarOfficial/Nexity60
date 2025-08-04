
// MainWindow.java - Main GUI application entry point
package ui;

import core.CategoryManager;
import core.NewsFetcher;
import core.Summarizer;
import model.NewsArticle;
import storage.FileManager;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MainWindow extends Application {
    
    // Core components
    private NewsFetcher newsFetcher;
    private Summarizer summarizer;
    private CategoryManager categoryManager;
    private FileManager fileManager;
    
    // UI Components
    private BorderPane mainLayout;
    private VBox sidebar;
    private ScrollPane contentArea;
    private VBox articleContainer;
    private ProgressBar loadingBar;
    private Label statusLabel;
    private Button refreshButton;
    private ToggleGroup categoryToggleGroup;
    
    // State
    private String selectedCategory = "Trending";
    private List<NewsArticle> currentArticles;
    
    @Override
    public void start(Stage primaryStage) {
        initializeComponents();
        setupUI(primaryStage);
        loadSavedArticles();
    }
    
    private void initializeComponents() {
        this.newsFetcher = new NewsFetcher();
        this.summarizer = new Summarizer();
        this.categoryManager = new CategoryManager();
        this.fileManager = new FileManager();
        this.categoryToggleGroup = new ToggleGroup();
    }
    
    private void setupUI(Stage primaryStage) {
        mainLayout = new BorderPane();
        
        // Create sidebar (hamburger menu)
        createSidebar();
        
        // Create main content area
        createContentArea();
        
        // Create top toolbar
        createToolbar();
        
        // Create status bar
        createStatusBar();
        
        // Assemble layout
        mainLayout.setLeft(sidebar);
        mainLayout.setCenter(contentArea);
        mainLayout.setBottom(createStatusBar());
        
        Scene scene = new Scene(mainLayout, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        
        primaryStage.setTitle("Nexity60 - Smart Summarized News Reader");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> {
            newsFetcher.shutdown();
            Platform.exit();
        });
        primaryStage.show();
    }
    
    private void createSidebar() {
        sidebar = new VBox(10);
        sidebar.setPadding(new Insets(20));
        sidebar.setPrefWidth(250);
        sidebar.setStyle("-fx-background-color: #2c3e50;");
        
        // App title
        Label titleLabel = new Label("Nexity60");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
        
        Separator separator = new Separator();
        separator.setStyle("-fx-background-color: white;");
        
        // Category buttons
        Label categoriesLabel = new Label("Categories");
        categoriesLabel.setStyle("-fx-text-fill: #bdc3c7; -fx-font-size: 14px; -fx-font-weight: bold;");
        
        VBox categoryButtons = new VBox(5);
        
        for (String category : categoryManager.getEnabledCategories()) {
            ToggleButton categoryBtn = createCategoryButton(category);
            categoryButtons.getChildren().add(categoryBtn);
            categoryToggleGroup.getToggles().add(categoryBtn);
            
            if (category.equals(selectedCategory)) {
                categoryBtn.setSelected(true);
            }
        }
        
        // Action buttons
        VBox actionButtons = new VBox(10);
        actionButtons.setPadding(new Insets(20, 0, 0, 0));
        
        Button savedArticlesBtn = new Button("📚 Saved Articles");
        savedArticlesBtn.getStyleClass().add("sidebar-button");
        savedArticlesBtn.setOnAction(e -> showSavedArticles());
        
        Button settingsBtn = new Button("⚙️ Settings");
        settingsBtn.getStyleClass().add("sidebar-button");
        settingsBtn.setOnAction(e -> showSettings());
        
        actionButtons.getChildren().addAll(savedArticlesBtn, settingsBtn);
        
        sidebar.getChildren().addAll(
            titleLabel, separator, categoriesLabel, categoryButtons, actionButtons
        );
    }
    
    private ToggleButton createCategoryButton(String category) {
        ToggleButton btn = new ToggleButton(category);
        btn.getStyleClass().add("category-button");
        btn.setPrefWidth(200);
        btn.setOnAction(e -> {
            if (btn.isSelected()) {
                selectedCategory = category;
                fetchNewsForCategory(category);
            }
        });
        return btn;
    }
    
    private void createContentArea() {
        articleContainer = new VBox(15);
        articleContainer.setPadding(new Insets(20));
        
        contentArea = new ScrollPane(articleContainer);
        contentArea.setFitToWidth(true);
        contentArea.setStyle("-fx-background-color: #ecf0f1;");
        
        // Welcome message
        showWelcomeMessage();
    }
    
    private VBox createToolbar() {
        VBox toolbar = new VBox(10);
        toolbar.setPadding(new Insets(10, 20, 10, 20));
        toolbar.setStyle("-fx-background-color: #34495e;");
        
        HBox topRow = new HBox(10);
        topRow.setAlignment(Pos.CENTER_LEFT);
        
        refreshButton = new Button("🔄 Refresh News");
        refreshButton.getStyleClass().add("toolbar-button");
        refreshButton.setOnAction(e -> fetchNewsForCategory(selectedCategory));
        
        loadingBar = new ProgressBar();
        loadingBar.setPrefWidth(200);
        loadingBar.setVisible(false);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        Label categoryLabel = new Label("Category: " + selectedCategory);
        categoryLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        
        topRow.getChildren().addAll(refreshButton, loadingBar, spacer, categoryLabel);
        toolbar.getChildren().add(topRow);
        
        mainLayout.setTop(toolbar);
        return toolbar;
    }
    
    private HBox createStatusBar() {
        HBox statusBar = new HBox();
        statusBar.setPadding(new Insets(5, 10, 5, 10));
        statusBar.setStyle("-fx-background-color: #95a5a6;");
        
        statusLabel = new Label("Ready");
        statusLabel.setStyle("-fx-text-fill: white;");
        
        statusBar.getChildren().add(statusLabel);
        return statusBar;
    }
    
    private void showWelcomeMessage() {
        VBox welcome = new VBox(20);
        welcome.setAlignment(Pos.CENTER);
        welcome.setPadding(new Insets(50));
        
        Label titleLabel = new Label("Welcome to Nexity60");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 28));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        
        Label subtitleLabel = new Label("Get smart, summarized news in 60 seconds");
        subtitleLabel.setFont(Font.font("System", 16));
        subtitleLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        Button getStartedBtn = new Button("Get Started - Fetch Latest News");
        getStartedBtn.getStyleClass().add("primary-button");
        getStartedBtn.setOnAction(e -> fetchNewsForCategory(selectedCategory));
        
        welcome.getChildren().addAll(titleLabel, subtitleLabel, getStartedBtn);
        
        articleContainer.getChildren().clear();
        articleContainer.getChildren().add(welcome);
    }
    
    private void fetchNewsForCategory(String category) {
        updateStatus("Fetching news for " + category + "...");
        showLoading(true);
        refreshButton.setDisable(true);
        
        CompletableFuture<List<NewsArticle>> future = newsFetcher.fetchNewsByCategory(category);
        
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                currentArticles = future.get();
                
                // Generate summaries
                updateMessage("Generating summaries...");
                for (NewsArticle article : currentArticles) {
                    String summary = summarizer.generateSummary(article);
                    article.setSummary(summary);
                }
                
                return null;
            }
            
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    displayArticles(currentArticles);
                    showLoading(false);
                    refreshButton.setDisable(false);
                    updateStatus("Loaded " + currentArticles.size() + " articles for " + category);
                    
                    // Save articles
                    fileManager.saveArticles(currentArticles);
                });
            }
            
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    showLoading(false);
                    refreshButton.setDisable(false);
                    updateStatus("Failed to fetch news. Please check your internet connection.");
                    showErrorMessage("Unable to fetch news", "Please check your internet connection and try again.");
                });
            }
        };
        
        loadingBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.messageProperty());
        
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    private void displayArticles(List<NewsArticle> articles) {
        articleContainer.getChildren().clear();
        
        if (articles.isEmpty()) {
            showNoArticlesMessage();
            return;
        }
        
        Label headerLabel = new Label("Latest " + selectedCategory + " News");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: #2c3e50;");
        headerLabel.setPadding(new Insets(0, 0, 20, 0));
        
        articleContainer.getChildren().add(headerLabel);
        
        for (NewsArticle article : articles) {
            NewsCard newsCard = new NewsCard(article, this::saveArticleForLater);
            articleContainer.getChildren().add(newsCard);
        }
    }
    
    private void showNoArticlesMessage() {
        VBox noArticles = new VBox(20);
        noArticles.setAlignment(Pos.CENTER);
        noArticles.setPadding(new Insets(50));
        
        Label messageLabel = new Label("No articles found for " + selectedCategory);
        messageLabel.setFont(Font.font("System", 16));
        messageLabel.setStyle("-fx-text-fill: #7f8c8d;");
        
        Button tryAgainBtn = new Button("Try Again");
        tryAgainBtn.getStyleClass().add("secondary-button");
        tryAgainBtn.setOnAction(e -> fetchNewsForCategory(selectedCategory));
        
        noArticles.getChildren().addAll(messageLabel, tryAgainBtn);
        articleContainer.getChildren().add(noArticles);
    }
    
    private void showSavedArticles() {
        List<NewsArticle> savedArticles = fileManager.loadArticles();
        updateStatus("Showing " + savedArticles.size() + " saved articles");
        
        articleContainer.getChildren().clear();
        
        Label headerLabel = new Label("Saved Articles");
        headerLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        headerLabel.setStyle("-fx-text-fill: #2c3e50;");
        headerLabel.setPadding(new Insets(0, 0, 20, 0));
        
        articleContainer.getChildren().add(headerLabel);
        
        if (savedArticles.isEmpty()) {
            Label emptyLabel = new Label("No saved articles yet. Save articles while reading to access them later.");
            emptyLabel.setStyle("-fx-text-fill: #7f8c8d;");
            articleContainer.getChildren().add(emptyLabel);
        } else {
            for (NewsArticle article : savedArticles) {
                NewsCard newsCard = new NewsCard(article, this::saveArticleForLater);
                newsCard.markAsSaved();
                articleContainer.getChildren().add(newsCard);
            }
        }
    }
    
    private void showSettings() {
        Dialog<Void> settingsDialog = new Dialog<>();
        settingsDialog.setTitle("Settings");
        settingsDialog.setHeaderText("Configure Nexity60");
        
        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        
        // Category management
        Label categoryLabel = new Label("Enabled Categories:");
        categoryLabel.setFont(Font.font("System", FontWeight.BOLD, 14));
        
        VBox categoryCheckboxes = new VBox(10);
        String[] allCategories = {"Trending", "Politics", "Sports", "Technology", 
                                 "Business", "Entertainment", "Health", "Science"};
        
        for (String category : allCategories) {
            CheckBox checkbox = new CheckBox(category);
            checkbox.setSelected(categoryManager.isCategoryEnabled(category));
            checkbox.setOnAction(e -> {
                if (checkbox.isSelected()) {
                    categoryManager.enableCategory(category);
                } else {
                    categoryManager.disableCategory(category);
                }
                updateSidebar(); // Refresh sidebar
            });
            categoryCheckboxes.getChildren().add(checkbox);
        }
        
        content.getChildren().addAll(categoryLabel, categoryCheckboxes);
        
        settingsDialog.getDialogPane().setContent(content);
        settingsDialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        settingsDialog.showAndWait();
    }
    
    private void updateSidebar() {
        // Refresh sidebar categories
        Platform.runLater(() -> {
            createSidebar();
            mainLayout.setLeft(sidebar);
        });
    }
    
    private void loadSavedArticles() {
        Task<List<NewsArticle>> loadTask = new Task<List<NewsArticle>>() {
            @Override
            protected List<NewsArticle> call() throws Exception {
                return fileManager.loadArticles();
            }
            
            @Override
            protected void succeeded() {
                List<NewsArticle> articles = getValue();
                if (!articles.isEmpty()) {
                    Platform.runLater(() -> {
                        updateStatus("Loaded " + articles.size() + " saved articles");
                    });
                }
            }
        };
        
        Thread thread = new Thread(loadTask);
        thread.setDaemon(true);
        thread.start();
    }
    
    private void saveArticleForLater(NewsArticle article) {
        fileManager.saveArticleSummary(article);
        updateStatus("Article saved for later reading");
        
        // Show confirmation
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Article Saved");
        alert.setHeaderText(null);
        alert.setContentText("Article has been saved for offline reading.");
        alert.showAndWait();
    }
    
    private void showLoading(boolean show) {
        loadingBar.setVisible(show);
        if (show) {
            loadingBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        } else {
            loadingBar.setProgress(0);
        }
    }
    
    private void updateStatus(String message) {
        Platform.runLater(() -> statusLabel.setText(message));
    }
    
    private void showErrorMessage(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}