// SavedArticlesView.java - Display saved articles with expanded content
package ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.NewsArticle;
import ui.theme.ColorPalette;
import ui.theme.ThemeManager;
import storage.FileManager;

import java.util.List;

/**
 * Component to display saved articles with full expanded content
 */
public class SavedArticlesView extends VBox {
    private FileManager fileManager;
    private ThemeManager themeManager;
    private VBox articlesContainer;
    private ScrollPane scrollPane;
    
    public SavedArticlesView() {
        this.fileManager = new FileManager();
        this.themeManager = ThemeManager.getInstance();
        
        initialize();
        applyTheme();
        
        // Listen for theme changes
        themeManager.addThemeChangeListener(theme -> applyTheme());
    }
    
    private void initialize() {
        setPadding(new Insets(20));
        setSpacing(15);
        
        // Header
        Label headerLabel = new Label("📚 Your Saved Articles");
        headerLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        
        // Articles container with scroll
        articlesContainer = new VBox(15);
        articlesContainer.setPadding(new Insets(10));
        articlesContainer.setAlignment(Pos.TOP_CENTER);
        
        scrollPane = new ScrollPane(articlesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.getStyleClass().add("content-area");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        
        // Refresh button
        Button refreshButton = new Button("🔄 Refresh Saved Articles");
        refreshButton.setStyle("-fx-font-weight: bold; -fx-padding: 10 20;");
        refreshButton.setOnAction(e -> loadSavedArticles());
        
        getChildren().addAll(headerLabel, refreshButton, scrollPane);
        
        // Load articles on initialization
        loadSavedArticles();
    }
    
    /**
     * Public method to refresh the saved articles list
     * Called when a new article is saved
     */
    public void refreshSavedArticles() {
        loadSavedArticles();
    }
    
    private void loadSavedArticles() {
        articlesContainer.getChildren().clear();
        
        try {
            List<String> savedFiles = fileManager.getSavedArticleFiles();
            
            if (savedFiles.isEmpty()) {
                VBox emptyBox = new VBox(10);
                emptyBox.setPadding(new Insets(40));
                emptyBox.setAlignment(Pos.CENTER);
                
                Label emptyLabel = new Label("No saved articles yet");
                emptyLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
                emptyLabel.setOpacity(0.6);
                
                Label emptySubLabel = new Label("Save articles from the news feed to view them here later");
                emptySubLabel.setFont(Font.font("Segoe UI", 13));
                emptySubLabel.setOpacity(0.5);
                
                emptyBox.getChildren().addAll(emptyLabel, emptySubLabel);
                articlesContainer.getChildren().add(emptyBox);
                return;
            }
            
            for (String filename : savedFiles) {
                NewsArticle article = fileManager.loadArticleSummary(filename);
                if (article != null) {
                    SavedArticleCard card = new SavedArticleCard(article, () -> deleteSavedArticle(filename));
                    articlesContainer.getChildren().add(card);
                }
            }
            
        } catch (Exception e) {
            Label errorLabel = new Label("Error loading saved articles: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: #d32f2f;");
            articlesContainer.getChildren().add(errorLabel);
        }
    }
    
    private void deleteSavedArticle(String filename) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Delete Article");
        confirm.setHeaderText("Delete this saved article?");
        confirm.setContentText("This action cannot be undone.");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            fileManager.deleteArticle(filename);
            loadSavedArticles();
        }
    }
    
    private void applyTheme() {
        ColorPalette.Palette palette = themeManager.getPalette();
        setStyle("-fx-background-color: " + palette.getBackground() + ";");
    }
    
    /**
     * Nested class for displaying individual saved articles
     */
    private static class SavedArticleCard extends VBox {
        private NewsArticle article;
        private Runnable onDeleteAction;
        
        SavedArticleCard(NewsArticle article, Runnable onDeleteAction) {
            this.article = article;
            this.onDeleteAction = onDeleteAction;
            
            initialize();
        }
        
        private void initialize() {
            setPadding(new Insets(16));
            setSpacing(12);
            setStyle("-fx-border-radius: 8; -fx-border-color: transparent;");
            setMaxWidth(800);
            setPrefWidth(800);
            
            // Title
            Label titleLabel = new Label(article.getTitle());
            titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
            titleLabel.setWrapText(true);
            titleLabel.setMaxWidth(750);
            titleLabel.getStyleClass().add("article-card-title");
            
            // Content - Full expanded content
            Label contentLabel = new Label(formatContent(article.getContent()));
            contentLabel.setFont(Font.font("Segoe UI", 13));
            contentLabel.setWrapText(true);
            contentLabel.setMaxWidth(750);
            contentLabel.setLineSpacing(4);
            contentLabel.getStyleClass().add("article-card-content");
            
            // Metadata
            HBox metadataBox = createMetadata();
            
            // Action buttons
            HBox actionsBox = createActions();
            
            getChildren().addAll(titleLabel, contentLabel, metadataBox, actionsBox);
        }
        
        private String formatContent(String content) {
            if (content == null) return "";
            // Keep full content for saved articles
            return content;
        }
        
        private HBox createMetadata() {
            HBox metadata = new HBox(16);
            metadata.setAlignment(Pos.CENTER_LEFT);
            metadata.setPadding(new Insets(8, 0, 0, 0));
            
            // Source
            if (article.getSource() != null) {
                Label sourceLabel = new Label("📡 " + article.getSource());
                sourceLabel.setFont(Font.font("Segoe UI", 12));
                sourceLabel.setOpacity(0.7);
                metadata.getChildren().add(sourceLabel);
            }
            
            // Category
            if (article.getCategory() != null) {
                Label categoryLabel = new Label("🏷️ " + article.getCategory());
                categoryLabel.setFont(Font.font("Segoe UI", 12));
                categoryLabel.setOpacity(0.7);
                metadata.getChildren().add(categoryLabel);
            }
            
            return metadata;
        }
        
        private HBox createActions() {
            HBox actions = new HBox(10);
            actions.setAlignment(Pos.CENTER_LEFT);
            actions.setPadding(new Insets(12, 0, 0, 0));
            
            // Open in browser button
            Button openButton = new Button("🔗 Open Article");
            openButton.setPrefWidth(130);
            openButton.setOnAction(e -> openUrl(article.getUrl()));
            
            // Delete button
            Button deleteButton = new Button("🗑️ Delete");
            deleteButton.setPrefWidth(100);
            deleteButton.setStyle("-fx-background-color: #d32f2f;");
            deleteButton.setOnAction(e -> {
                if (onDeleteAction != null) {
                    onDeleteAction.run();
                }
            });
            
            actions.getChildren().addAll(openButton, deleteButton);
            return actions;
        }
        
        private void openUrl(String url) {
            try {
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
                } else if (System.getProperty("os.name").toLowerCase().contains("mac")) {
                    Runtime.getRuntime().exec(new String[]{"open", url});
                } else {
                    Runtime.getRuntime().exec(new String[]{"xdg-open", url});
                }
            } catch (Exception e) {
                System.err.println("Failed to open URL: " + e.getMessage());
            }
        }
    }
}
