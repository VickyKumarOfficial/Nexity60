// EnhancedNewsCard.java - Beautiful news card component with theme support
package ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.NewsArticle;
import ui.theme.ThemeManager;
import ui.theme.ColorPalette;

/**
 * Enhanced news card component with modern design
 * Supports light and dark modes with smooth transitions
 */
public class EnhancedNewsCard extends VBox {
    private NewsArticle article;
    private ThemeManager themeManager;
    private Runnable onSaveAction;
    
    public EnhancedNewsCard(NewsArticle article, Runnable onSaveAction) {
        this.article = article;
        this.onSaveAction = onSaveAction;
        this.themeManager = ThemeManager.getInstance();
        
        initialize();
        applyTheme();
        
        // Listen for theme changes
        themeManager.addThemeChangeListener(theme -> applyTheme());
    }
    
    private void initialize() {
        setPadding(new Insets(16));
        setSpacing(12);
        setStyle("-fx-border-radius: 8; -fx-border-color: transparent;");
        setMaxWidth(800);
        setPrefWidth(800);
        
        // Create header section
        HBox headerBox = createHeader();
        
        // Create title label
        Label titleLabel = new Label(article.getTitle());
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        titleLabel.setWrapText(true);
        titleLabel.setMaxWidth(750);
        titleLabel.getStyleClass().add("article-card-title");
        
        // Create content preview
        Label contentLabel = new Label(truncateContent(article.getContent(), 300));
        contentLabel.setFont(Font.font("Segoe UI", 13));
        contentLabel.setWrapText(true);
        contentLabel.setMaxWidth(750);
        contentLabel.getStyleClass().add("article-card-content");
        
        // Create metadata section
        HBox metadataBox = createMetadata();
        
        // Create action buttons section
        HBox actionsBox = createActions();
        
        // Add all to card
        getChildren().addAll(headerBox, titleLabel, contentLabel, metadataBox, actionsBox);
    }
    
    private HBox createHeader() {
        HBox header = new HBox(8);
        header.setAlignment(Pos.CENTER_LEFT);
        
        // Source badge
        Label sourceLabel = new Label("📡 " + article.getSource());
        sourceLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        sourceLabel.setPadding(new Insets(4, 12, 4, 12));
        sourceLabel.setStyle("-fx-border-radius: 20; -fx-padding: 4 12 4 12;");
        
        // Category badge with color
        Label categoryLabel = new Label("🏷️ " + article.getCategory());
        categoryLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 12));
        categoryLabel.setPadding(new Insets(4, 12, 4, 12));
        categoryLabel.setStyle("-fx-border-radius: 20; -fx-padding: 4 12 4 12;");
        
        // Time indicator
        Label timeLabel = new Label("⏱️ " + getRelativeTime());
        timeLabel.setFont(Font.font("Segoe UI", 11));
        
        header.getChildren().addAll(sourceLabel, categoryLabel);
        HBox.setHgrow(timeLabel, Priority.ALWAYS);
        header.getChildren().add(timeLabel);
        
        return header;
    }
    
    private HBox createMetadata() {
        HBox metadata = new HBox(16);
        metadata.setAlignment(Pos.CENTER_LEFT);
        metadata.setPadding(new Insets(8, 0, 0, 0));
        
        // Reading time
        int estimatedReadingTime = Math.max(1, article.getContent().split("\\s+").length / 200);
        Label readTimeLabel = new Label("📖 " + estimatedReadingTime + " min read");
        readTimeLabel.setFont(Font.font("Segoe UI", 12));
        readTimeLabel.setOpacity(0.7);
        
        // Word count
        int wordCount = article.getContent().split("\\s+").length;
        Label wordCountLabel = new Label("📝 " + wordCount + " words");
        wordCountLabel.setFont(Font.font("Segoe UI", 12));
        wordCountLabel.setOpacity(0.7);
        
        metadata.getChildren().addAll(readTimeLabel, wordCountLabel);
        return metadata;
    }
    
    private HBox createActions() {
        HBox actions = new HBox(10);
        actions.setAlignment(Pos.CENTER_LEFT);
        actions.setPadding(new Insets(12, 0, 0, 0));
        
        // Save button
        Button saveButton = new Button("💾 Save for Later");
        saveButton.setPrefWidth(150);
        saveButton.setOnAction(e -> {
            if (onSaveAction != null) {
                onSaveAction.run();
            }
        });
        
        // Read more button
        Button readMoreButton = new Button("🔗 Read Full Article");
        readMoreButton.setPrefWidth(150);
        readMoreButton.setOnAction(e -> {
            // Open URL in browser
            openUrl(article.getUrl());
        });
        
        actions.getChildren().addAll(saveButton, readMoreButton);
        return actions;
    }
    
    private void applyTheme() {
        ColorPalette.Palette palette = themeManager.getPalette();
        
        setStyle("-fx-background-color: " + palette.getCardBackground() + ";" +
                "-fx-border-color: " + palette.getBorder() + ";" +
                "-fx-border-width: 1;" +
                "-fx-border-radius: 8;" +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.1), 5, 0, 0, 2);");
        
        // Update all labels
        for (javafx.scene.Node node : getChildren()) {
            updateNodeTheme(node, palette);
        }
    }
    
    private void updateNodeTheme(javafx.scene.Node node, ColorPalette.Palette palette) {
        if (node instanceof Label) {
            Label label = (Label) node;
            label.setTextFill(javafx.scene.paint.Color.web(palette.getTextPrimary()));
        } else if (node instanceof VBox || node instanceof HBox) {
            javafx.scene.layout.Pane pane = (javafx.scene.layout.Pane) node;
            for (javafx.scene.Node child : pane.getChildren()) {
                updateNodeTheme(child, palette);
            }
        }
    }
    
    private String truncateContent(String content, int maxLength) {
        if (content == null) return "";
        if (content.length() <= maxLength) return content;
        return content.substring(0, maxLength) + "...";
    }
    
    private String getRelativeTime() {
        // Simple relative time display
        return "Now";
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
