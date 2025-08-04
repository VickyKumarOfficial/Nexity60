import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsArticle {
    private String title;
    private String content;
    private String url;
    private LocalDateTime publishedDate;
    private String category;
    private String summary;

    public NewsArticle(String title, String content, String url, LocalDateTime publishedDate, String category) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.publishedDate = publishedDate;
        this.category = category;
        this.summary = generateQuickSummary(content);
    }

    private String generateQuickSummary(String content) {
        if (content == null || content.length() < 100) {
            return content;
        }
        // Simple summary: first sentence + key info
        String[] sentences = content.split("\\. ");
        StringBuilder summary = new StringBuilder();
        
        if (sentences.length > 0) {
            summary.append(sentences[0]).append(". ");
        }
        
        if (sentences.length > 1 && summary.length() < 150) {
            summary.append(sentences[1]).append(". ");
        }
        
        return summary.toString();
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public LocalDateTime getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDateTime publishedDate) { this.publishedDate = publishedDate; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}
