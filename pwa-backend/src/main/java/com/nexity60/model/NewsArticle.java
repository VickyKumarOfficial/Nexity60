package com.nexity60.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles")
public class NewsArticle {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 500)
    private String title;
    
    @Column(nullable = false, length = 1000)
    private String url;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @Column(length = 100)
    private String category;
    
    @Column(length = 100)
    private String source;
    
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "is_saved")
    private Boolean isSaved = false;
    
    @Column(length = 1000)
    private String imageUrl;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (publishedAt == null) {
            publishedAt = LocalDateTime.now();
        }
    }

    // Constructors
    public NewsArticle() {}
    
    public NewsArticle(Long id, String title, String url, String content, String category, 
                      String source, LocalDateTime publishedAt, LocalDateTime createdAt, Boolean isSaved, String imageUrl) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.content = content;
        this.category = category;
        this.source = source;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.isSaved = isSaved;
        this.imageUrl = imageUrl;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getUrl() { return url; }
    public String getContent() { return content; }
    public String getCategory() { return category; }
    public String getSource() { return source; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Boolean getIsSaved() { return isSaved; }
    public String getImageUrl() { return imageUrl; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setUrl(String url) { this.url = url; }
    public void setContent(String content) { this.content = content; }
    public void setCategory(String category) { this.category = category; }
    public void setSource(String source) { this.source = source; }
    public void setPublishedAt(LocalDateTime publishedAt) { this.publishedAt = publishedAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setIsSaved(Boolean isSaved) { this.isSaved = isSaved; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
