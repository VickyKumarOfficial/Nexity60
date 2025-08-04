// NewsArticle.java - Data model for news items
package model;

import java.time.LocalDateTime;
import java.util.List;

public class NewsArticle {
    private String title;
    private String url;
    private String content;
    private String summary;
    private String category;
    private String source;
    private LocalDateTime publishedAt;
    private int readingTimeSeconds;
    private List<String> keyPoints;
    
    // Constructors
    public NewsArticle() {}
    
    public NewsArticle(String title, String url, String content, String category, String source) {
        this.title = title;
        this.url = url;
        this.content = content;
        this.category = category;
        this.source = source;
        this.publishedAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }
    
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    
    public int getReadingTimeSeconds() { return readingTimeSeconds; }
    public void setReadingTimeSeconds(int readingTimeSeconds) { this.readingTimeSeconds = readingTimeSeconds; }
    
    public List<String> getKeyPoints() { return keyPoints; }
    public void setKeyPoints(List<String> keyPoints) { this.keyPoints = keyPoints; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s", category, title, source);
    }
}