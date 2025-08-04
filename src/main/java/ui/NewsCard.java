// NewsCard.java - Visual component for each summary
package ui;

import model.NewsArticle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class NewsCard extends VBox {
    
    private NewsArticle article;
    private Consumer<NewsArticle> saveCallback;
    private boolean expanded = false;
    private VBox expandedContent;
    private Button expandButton;
    private Button saveButton;
    
    public NewsCard(NewsArticle article, Consumer<NewsArticle> saveCallback) {
        this.article = article;
        this.saveCallback = saveCallback;
        
        setupCard();
        createContent();
    }
    
    private void setupCard() {
        this.getStyleClass().add("news-card");
        this.setPadding(new Insets(20));
        this.setSpacing(15);
        this.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; " +
                     "-fx-border-width: 1; -fx-border-radius: 8; -fx-background-radius: 8;");
    }
    
    private void createContent() {
        // Header with title and metadata
        createHeader();
        
        // Summary preview
        createSummaryPreview();
        
        // Action buttons
        createActionButtons();
        
        // Expandable content (initially hidden)
        createExpandedContent();
    }
    
    private void createHeader() {
        VBox header = new VBox(8);
        
        // Title
        Label titleLabel = new Label(article.getTitle());
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        titleLabel.setStyle("-fx-text-fill: #2c3e50;");
        titleLabel.setWrapText(true);
        titleLabel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        
        // Metadata row
        HBox metadata = new HBox(15);
        metadata.setAlignment(Pos.CENTER_LEFT);
        
        Label sourceLabel = new Label("📰 " + article.getSource());
        sourceLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");
        
        Label categoryLabel = new Label("🏷️ " + article.getCategory());
        categoryLabel.setStyle("-fx-text-fill: #3498db; -fx-font-size: 12px;");
        
        if (article.getPublishedAt() != null) {
            Label timeLabel = new Label("🕒 " + article.getPublishedAt().format(
                DateTimeFormatter.ofPattern("MMM dd, HH:mm")));
            timeLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");
            metadata.getChildren().add(timeLabel);
        }
        
        if (article.getReadingTimeSeconds() > 0) {
            int minutes = article.getReadingTimeSeconds() / 60;
            int seconds = article.getReadingTimeSeconds() % 60;
            Label readingTimeLabel = new Label(String.format("⏱️ %dm %ds read", minutes, seconds));
            readingTimeLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 12px; -fx-font-weight: bold;");
            metadata.getChildren().add(readingTimeLabel);
        }
        
        metadata.getChildren().addAll(sourceLabel, categoryLabel);
        
        header.getChildren().addAll(titleLabel, metadata);
        this.getChildren().add(header);
    }
    
    private void createSummaryPreview() {
        if (article.getSummary() != null && !article.getSummary().isEmpty()) {
            String preview = article.getSummary();
            if (preview.length() > 200) {
                preview = preview.substring(0, 200) + "...";
            }
            
            TextFlow summaryFlow = new TextFlow();
            Text summaryText = new Text(preview);
            summaryText.setStyle("-fx-font-size: 14px; -fx-fill: #34495e;");
            summaryFlow.getChildren().add(summaryText);
            summaryFlow.setPrefWidth(Region.USE_COMPUTED_SIZE);
            
            this.getChildren().add(summaryFlow);
        }
    }
    
    private void createActionButtons() {
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        
        expandButton = new Button(expanded ? "📖 Show Less" : "📖 Read Full Summary");
        expandButton.getStyleClass().add("secondary-button");
        expandButton.setOnAction(e -> toggleExpanded());
        
        saveButton = new Button("💾 Save for Later");
        saveButton.getStyleClass().add("primary-button");
        saveButton.setOnAction(e -> saveCallback.accept(article));
        
        Button openUrlButton = new Button("🔗 Open Original");
        openUrlButton.getStyleClass().add("tertiary-button");
        openUrlButton.setOnAction(e -> {
            try {
                java.awt.Desktop.getDesktop().browse(java.net.URI.create(article.getUrl()));
            } catch (Exception ex) {
                // Handle error - could show alert
                System.err.println("Failed to open URL: " + ex.getMessage());
            }
        });
        
        buttonBox.getChildren().addAll(expandButton, saveButton, openUrlButton);
        this.getChildren().add(buttonBox);
    }
    
    private void createExpandedContent() {
        expandedContent = new VBox(15);
        expandedContent.setVisible(false);
        expandedContent.setManaged(false);
        
        // Full summary
        if (article.getSummary() != null && !article.getSummary().isEmpty()) {
            Label summaryHeader = new Label("📄 Full Summary");
            summaryHeader.setFont(Font.font("System", FontWeight.BOLD, 14));
            summaryHeader.setStyle("-fx-text-fill: #2c3e50;");
            
            TextFlow fullSummaryFlow = new TextFlow();
            Text fullSummaryText = new Text(article.getSummary());
            fullSummaryText.setStyle("-fx-font-size: 14px; -fx-fill: #34495e; -fx-line-spacing: 1.5;");
            fullSummaryFlow.getChildren().add(fullSummaryText);
            
            expandedContent.getChildren().addAll(summaryHeader, fullSummaryFlow);
        }
        
        // Key points
        if (article.getKeyPoints() != null && !article.getKeyPoints().isEmpty()) {
            Label keyPointsHeader = new Label("🎯 Key Points");
            keyPointsHeader.setFont(Font.font("System", FontWeight.BOLD, 14));
            keyPointsHeader.setStyle("-fx-text-fill: #2c3e50;");
            
            VBox keyPointsList = new VBox(5);
            for (String point : article.getKeyPoints()) {
                Label pointLabel = new Label(point);
                pointLabel.setStyle("-fx-text-fill: #34495e; -fx-font-size: 13px;");
                pointLabel.setWrapText(true);
                keyPointsList.getChildren().add(pointLabel);
            }
            
            expandedContent.getChildren().addAll(keyPointsHeader, keyPointsList);
        }
        
        this.getChildren().add(expandedContent);
    }
    
    private void toggleExpanded() {
        expanded = !expanded;
        expandedContent.setVisible(expanded);
        expandedContent.setManaged(expanded);
        expandButton.setText(expanded ? "📖 Show Less" : "📖 Read Full Summary");
    }
    
    public void markAsSaved() {
        saveButton.setText("✅ Saved");
        saveButton.setDisable(true);
    }
}