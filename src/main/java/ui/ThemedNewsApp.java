// ThemedNewsApp.java - Enhanced news application with light/dark mode support
package ui;

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
import storage.FileManager;
import model.NewsArticle;
import core.NewsFetcher;
import storage.FileManager;
import ui.components.EnhancedNewsCard;
import ui.components.SavedArticlesView;
import ui.theme.Theme;
import ui.theme.ThemeManager;
import ui.theme.ColorPalette;
import ui.theme.StyleManager;

import java.util.List;

/**
 * Enhanced Nexity60 News Application with Light/Dark Mode Support
 * Features modern UI with comprehensive theme system
 */
public class ThemedNewsApp extends Application {
    
    private NewsFetcher newsFetcher;
    private ThemeManager themeManager;
    private FileManager fileManager;
    private Scene mainScene;
    
    // UI Components
    private BorderPane mainLayout;
    private VBox newsContainer;
    private ComboBox<String> categorySelector;
    private ToggleButton themeToggle;
    private Button refreshButton;
    private Label statusLabel;
    private ProgressIndicator progressIndicator;
    private ScrollPane scrollPane;
    
    @Override
    public void start(Stage primaryStage) {
        try {
            initializeManagers();
            setupUI(primaryStage);
            loadNews("Trending");
            
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void initializeManagers() {
        newsFetcher = new NewsFetcher();
        themeManager = ThemeManager.getInstance();
        fileManager = new FileManager();
    }
    
    private void setupUI(Stage primaryStage) {
        mainLayout = new BorderPane();
        
        // Create header
        VBox header = createHeader();
        mainLayout.setTop(header);
        
        // Create main content
        createMainContent();
        mainLayout.setCenter(scrollPane);
        
        // Create footer
        HBox footer = createFooter();
        mainLayout.setBottom(footer);
        
        // Create scene
        mainScene = new Scene(mainLayout, 1000, 700);
        applyTheme();
        
        // Setup stage
        primaryStage.setTitle("Nexity60 - Smart News Reader");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(e -> {
            newsFetcher.shutdown();
            Platform.exit();
        });
        
        // Listen for theme changes
        themeManager.addThemeChangeListener(theme -> {
            Platform.runLater(this::applyTheme);
        });
    }
    
    private VBox createHeader() {
        VBox headerBox = new VBox(15);
        headerBox.setPadding(new Insets(20));
        headerBox.setAlignment(Pos.TOP_CENTER);
        
        // Title section
        VBox titleSection = new VBox(5);
        titleSection.setAlignment(Pos.CENTER);
        
        Label titleLabel = new Label("🌍 Nexity60");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        titleLabel.getStyleClass().add("title-label");
        
        Label subtitleLabel = new Label("Real-time news from trusted sources");
        subtitleLabel.setFont(Font.font("Segoe UI", 14));
        subtitleLabel.getStyleClass().add("subtitle-label");
        
        titleSection.getChildren().addAll(titleLabel, subtitleLabel);
        
        // Controls section
        HBox controlsBox = new HBox(15);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(0, 20, 0, 20));
        
        // Category label and selector
        Label categoryLabel = new Label("📚 Category:");
        categoryLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        
        categorySelector = new ComboBox<>();
        categorySelector.getItems().addAll(
            "Trending", "Technology", "Business", "Sports",
            "World", "Health", "Science", "Entertainment"
        );
        categorySelector.setValue("Trending");
        categorySelector.setPrefWidth(150);
        categorySelector.setStyle("-fx-font-size: 12px;");
        categorySelector.setOnAction(e -> loadNews(categorySelector.getValue()));
        
        // Refresh button
        refreshButton = new Button("🔄 Refresh");
        refreshButton.setStyle("-fx-font-weight: bold; -fx-padding: 8 16;");
        refreshButton.setOnAction(e -> loadNews(categorySelector.getValue()));
        
        // Progress indicator
        progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(20, 20);
        progressIndicator.setVisible(false);
        
        // Theme toggle button
        themeToggle = new ToggleButton(getThemeButtonText());
        themeToggle.setPrefWidth(150);
        themeToggle.setStyle("-fx-font-weight: bold; -fx-padding: 8 16;");
        themeToggle.setOnAction(e -> {
            themeManager.toggleTheme();
            themeToggle.setText(getThemeButtonText());
        });
        
        // Saved Articles button
        Button savedArticlesButton = new Button("📚 Saved Articles");
        savedArticlesButton.setPrefWidth(150);
        savedArticlesButton.setStyle("-fx-font-weight: bold; -fx-padding: 8 16;");
        savedArticlesButton.setOnAction(e -> openSavedArticlesWindow());
        
        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        controlsBox.getChildren().addAll(
            categoryLabel, categorySelector,
            refreshButton, progressIndicator,
            spacer, savedArticlesButton, themeToggle
        );
        
        headerBox.getChildren().addAll(titleSection, controlsBox);
        return headerBox;
    }
    
    private void createMainContent() {
        newsContainer = new VBox(15);
        newsContainer.setPadding(new Insets(20));
        newsContainer.setAlignment(Pos.TOP_CENTER);
        
        scrollPane = new ScrollPane(newsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.getStyleClass().add("content-area");
    }
    
    private HBox createFooter() {
        HBox footerBox = new HBox();
        footerBox.setPadding(new Insets(10, 20, 10, 20));
        footerBox.setStyle("-fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");
        
        statusLabel = new Label("🚀 Ready to fetch news...");
        statusLabel.setFont(Font.font("Segoe UI", 12));
        statusLabel.getStyleClass().add("subtitle-label");
        
        footerBox.getChildren().add(statusLabel);
        return footerBox;
    }
    
    private void loadNews(String category) {
        // Update UI state
        refreshButton.setDisable(true);
        progressIndicator.setVisible(true);
        statusLabel.setText("📡 Fetching " + category + " news...");
        newsContainer.getChildren().clear();
        
        // Add loading message
        Label loadingLabel = new Label("🔄 Loading...");
        loadingLabel.setFont(Font.font("Segoe UI", 14));
        newsContainer.getChildren().add(loadingLabel);
        
        // Create background task
        Task<List<NewsArticle>> fetchTask = new Task<List<NewsArticle>>() {
            @Override
            protected List<NewsArticle> call() throws Exception {
                return newsFetcher.fetchNewsByCategory(category).get();
            }
            
            @Override
            protected void succeeded() {
                Platform.runLater(() -> {
                    List<NewsArticle> articles = getValue();
                    displayNews(articles, category);
                    refreshButton.setDisable(false);
                    progressIndicator.setVisible(false);
                    statusLabel.setText("✅ Loaded " + articles.size() + " articles from " + category);
                });
            }
            
            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    displayError("Failed to fetch news: " + getException().getMessage());
                    refreshButton.setDisable(false);
                    progressIndicator.setVisible(false);
                    statusLabel.setText("❌ Failed to load news");
                });
            }
        };
        
        Thread fetchThread = new Thread(fetchTask);
        fetchThread.setDaemon(true);
        fetchThread.start();
    }
    
    private void displayNews(List<NewsArticle> articles, String category) {
        newsContainer.getChildren().clear();
        
        if (articles.isEmpty()) {
            Label noNewsLabel = new Label("No articles found for " + category);
            noNewsLabel.setFont(Font.font("Segoe UI", 14));
            newsContainer.getChildren().add(noNewsLabel);
            return;
        }
        
        // Category header
        Label categoryHeader = new Label("📰 " + category + " (" + articles.size() + " articles)");
        categoryHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        categoryHeader.setPadding(new Insets(0, 0, 10, 0));
        newsContainer.getChildren().add(categoryHeader);
        
        // Display articles
        for (NewsArticle article : articles) {
            EnhancedNewsCard card = new EnhancedNewsCard(article, () -> saveArticle(article));
            newsContainer.getChildren().add(card);
        }
    }
    
    private void displayError(String message) {
        newsContainer.getChildren().clear();
        
        VBox errorBox = new VBox(10);
        errorBox.setPadding(new Insets(20));
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setStyle("-fx-border-radius: 8; -fx-padding: 20;");
        
        Label errorTitle = new Label("⚠️ Error Loading News");
        errorTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        
        Label errorMessage = new Label(message);
        errorMessage.setWrapText(true);
        errorMessage.setFont(Font.font("Segoe UI", 13));
        
        Button retryButton = new Button("🔄 Try Again");
        retryButton.setOnAction(e -> loadNews(categorySelector.getValue()));
        
        errorBox.getChildren().addAll(errorTitle, errorMessage, retryButton);
        newsContainer.getChildren().add(errorBox);
    }
    
    private void saveArticle(NewsArticle article) {
        try {
            fileManager.saveArticleSummary(article);
            
            // Show success message with option to view saved articles
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Article Saved");
            alert.setHeaderText("✅ Successfully saved!");
            alert.setContentText(article.getTitle() + "\n\nClick 'View Saved Articles' to see all your saved articles.");
            
            ButtonType viewSavedButton = new ButtonType("View Saved Articles");
            ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(viewSavedButton, closeButton);
            
            alert.showAndWait().ifPresent(response -> {
                if (response == viewSavedButton) {
                    openSavedArticlesWindow();
                }
            });
            
        } catch (Exception e) {
            showAlert("Error", "Failed to save article: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    /**
     * Opens a new window showing all saved articles
     */
    private void openSavedArticlesWindow() {
        Stage savedArticlesStage = new Stage();
        savedArticlesStage.setTitle("📚 Saved Articles - Nexity60");
        
        // Create saved articles view
        SavedArticlesView savedView = new SavedArticlesView();
        
        // Create scene with theme support
        Scene savedScene = new Scene(savedView, 950, 750);
        
        // Apply current theme to the saved articles window
        ColorPalette.Palette palette = themeManager.getPalette();
        String css = themeManager.getCurrentTheme() == Theme.LIGHT ? 
            StyleManager.getLightModeCSS() : StyleManager.getDarkModeCSS();
        String cssDataUrl = "data:text/css;base64," + java.util.Base64.getEncoder()
            .encodeToString(css.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        savedScene.getStylesheets().add(cssDataUrl);
        
        savedArticlesStage.setScene(savedScene);
        savedArticlesStage.show();
        
        // Listen for theme changes to update saved articles window
        themeManager.addThemeChangeListener(theme -> {
            Platform.runLater(() -> {
                savedScene.getStylesheets().clear();
                String newCss = theme == Theme.LIGHT ? 
                    StyleManager.getLightModeCSS() : StyleManager.getDarkModeCSS();
                String newCssDataUrl = "data:text/css;base64," + java.util.Base64.getEncoder()
                    .encodeToString(newCss.getBytes(java.nio.charset.StandardCharsets.UTF_8));
                savedScene.getStylesheets().add(newCssDataUrl);
            });
        });
    }
    
    private void applyTheme() {
        ColorPalette.Palette palette = themeManager.getPalette();
        
        // Clear previous stylesheets
        mainScene.getStylesheets().clear();
        
        // Generate CSS for current theme
        String css = themeManager.getCurrentTheme() == Theme.LIGHT ? 
            StyleManager.getLightModeCSS() : StyleManager.getDarkModeCSS();
        
        // Add CSS stylesheet to scene
        String cssDataUrl = "data:text/css;base64," + java.util.Base64.getEncoder()
            .encodeToString(css.getBytes(java.nio.charset.StandardCharsets.UTF_8));
        mainScene.getStylesheets().add(cssDataUrl);
        
        // Apply background color to all containers
        String bgColor = palette.getBackground();
        mainLayout.setStyle("-fx-background-color: " + bgColor + ";");
        scrollPane.setStyle("-fx-background-color: " + bgColor + ";");
        newsContainer.setStyle("-fx-background-color: " + bgColor + ";");
    }
    
    private String getThemeButtonText() {
        return themeManager.getCurrentTheme() == Theme.LIGHT ? "🌙 Dark Mode" : "☀️ Light Mode";
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
