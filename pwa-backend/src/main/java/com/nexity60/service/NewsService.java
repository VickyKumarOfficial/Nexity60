package com.nexity60.service;

import com.nexity60.model.NewsArticle;
import com.nexity60.repository.NewsArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NewsService {
    
    @Autowired
    private NewsArticleRepository repository;
    
    private static final int TIMEOUT_MS = 15000;
    
    public List<NewsArticle> fetchNewsByCategory(String category) {
        List<NewsArticle> articles = new ArrayList<>();
        
        try {
            String rssUrl = getBBCRSSUrl(category);
            String rssContent = downloadContent(rssUrl);
            
            if (rssContent != null && rssContent.length() > 1000) {
                articles = parseRSSContent(rssContent, category);
                
                // Save to database if not exists, or update category if URL exists
                for (NewsArticle article : articles) {
                    NewsArticle existingArticle = repository.findByUrl(article.getUrl());
                    if (existingArticle != null) {
                        // Update the category to match the current request
                        existingArticle.setCategory(category);
                        repository.save(existingArticle);
                    } else {
                        // Save new article
                        repository.save(article);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error fetching news: " + e.getMessage());
        }
        
        // Return only articles from the requested category
        return articles.stream()
            .filter(article -> category.equalsIgnoreCase(article.getCategory()))
            .toList();
    }
    
    public List<NewsArticle> getSavedArticles() {
        return repository.findByIsSavedTrue();
    }
    
    public NewsArticle saveArticle(Long id) {
        NewsArticle article = repository.findById(id).orElseThrow();
        article.setIsSaved(true);
        return repository.save(article);
    }
    
    public void unsaveArticle(Long id) {
        NewsArticle article = repository.findById(id).orElseThrow();
        article.setIsSaved(false);
        repository.save(article);
    }
    
    private String downloadContent(String urlString) {
        try {
            @SuppressWarnings("deprecation")
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setConnectTimeout(TIMEOUT_MS);
            connection.setReadTimeout(TIMEOUT_MS);
            
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                reader.close();
                return content.toString();
            }
        } catch (Exception e) {
            System.err.println("Error downloading: " + e.getMessage());
        }
        return null;
    }
    
    private List<NewsArticle> parseRSSContent(String rssContent, String category) {
        List<NewsArticle> articles = new ArrayList<>();
        
        Pattern itemPattern = Pattern.compile("<item>(.*?)</item>", Pattern.DOTALL);
        Matcher itemMatcher = itemPattern.matcher(rssContent);
        
        int count = 0;
        while (itemMatcher.find() && count < 10) {
            String itemContent = itemMatcher.group(1);
            NewsArticle article = parseRSSItem(itemContent, category);
            
            if (article != null) {
                articles.add(article);
                count++;
            }
        }
        
        return articles;
    }
    
    private NewsArticle parseRSSItem(String itemContent, String category) {
        try {
            String title = extractRSSField(itemContent, "title");
            String link = extractRSSField(itemContent, "link");
            String description = extractRSSField(itemContent, "description");
            String imageUrl = extractImageUrl(itemContent);
            
            if (title != null && link != null && description != null) {
                title = cleanText(title);
                description = cleanText(description);
                description = expandArticleContent(description, title, category);
                
                NewsArticle article = new NewsArticle();
                article.setTitle(title);
                article.setUrl(link);
                article.setContent(description);
                article.setCategory(category);
                article.setSource("BBC");
                article.setPublishedAt(LocalDateTime.now());
                article.setImageUrl(imageUrl);
                
                return article;
            }
        } catch (Exception e) {
            System.err.println("Error parsing item: " + e.getMessage());
        }
        return null;
    }
    
    private String extractRSSField(String content, String fieldName) {
        Pattern cdataPattern = Pattern.compile(
            "<" + fieldName + "><\\!\\[CDATA\\[(.*?)\\]\\]></" + fieldName + ">", 
            Pattern.DOTALL);
        Pattern regularPattern = Pattern.compile(
            "<" + fieldName + ">(.*?)</" + fieldName + ">", 
            Pattern.DOTALL);
        
        Matcher cdataMatcher = cdataPattern.matcher(content);
        if (cdataMatcher.find()) {
            return cdataMatcher.group(1).trim();
        }
        
        Matcher regularMatcher = regularPattern.matcher(content);
        if (regularMatcher.find()) {
            return regularMatcher.group(1).trim();
        }
        
        return null;
    }
    
    private String extractImageUrl(String itemContent) {
        // Try to extract image from media:thumbnail or media:content
        Pattern mediaThumbnailPattern = Pattern.compile(
            "<media:thumbnail[^>]*url=\"([^\"]+)\"", 
            Pattern.CASE_INSENSITIVE);
        Matcher mediaThumbnailMatcher = mediaThumbnailPattern.matcher(itemContent);
        if (mediaThumbnailMatcher.find()) {
            return mediaThumbnailMatcher.group(1);
        }
        
        Pattern mediaContentPattern = Pattern.compile(
            "<media:content[^>]*url=\"([^\"]+)\"", 
            Pattern.CASE_INSENSITIVE);
        Matcher mediaContentMatcher = mediaContentPattern.matcher(itemContent);
        if (mediaContentMatcher.find()) {
            return mediaContentMatcher.group(1);
        }
        
        // Try to extract from enclosure tag
        Pattern enclosurePattern = Pattern.compile(
            "<enclosure[^>]*url=\"([^\"]+)\"[^>]*type=\"image/[^\"]*\"", 
            Pattern.CASE_INSENSITIVE);
        Matcher enclosureMatcher = enclosurePattern.matcher(itemContent);
        if (enclosureMatcher.find()) {
            return enclosureMatcher.group(1);
        }
        
        // Try to extract from description's img tag
        String description = extractRSSField(itemContent, "description");
        if (description != null) {
            Pattern imgPattern = Pattern.compile(
                "<img[^>]*src=\"([^\"]+)\"", 
                Pattern.CASE_INSENSITIVE);
            Matcher imgMatcher = imgPattern.matcher(description);
            if (imgMatcher.find()) {
                return imgMatcher.group(1);
            }
        }
        
        // Return a default placeholder image
        return "https://via.placeholder.com/400x300/4a90e2/ffffff?text=BBC+News";
    }
    
    private String cleanText(String text) {
        if (text == null) return "";
        
        text = text.replaceAll("<[^>]*>", "");
        text = text.replaceAll("&quot;", "\"")
                   .replaceAll("&amp;", "&")
                   .replaceAll("&lt;", "<")
                   .replaceAll("&gt;", ">")
                   .replaceAll("&apos;", "'")
                   .replaceAll("&#39;", "'")
                   .replaceAll("&nbsp;", " ");
        text = text.replaceAll("\\s+", " ").trim();
        
        return text;
    }
    
    private String expandArticleContent(String description, String title, String category) {
        if (description != null && description.length() > 800) {
            return description;
        }
        
        StringBuilder expanded = new StringBuilder();
        expanded.append(description).append("\n\n");
        
        if (description != null && description.length() < 200) {
            expanded.append("📋 Extended Coverage: This breaking news story provides important insights into current events. ");
            expanded.append("Experts are monitoring the situation as it unfolds.\n\n");
        }
        
        expanded.append("📂 Category: ").append(category).append(" News\n\n");
        
        // Add category-specific context
        switch (category.toLowerCase()) {
            case "technology":
                expanded.append("💡 Technology Insights: This development highlights ongoing innovation in the digital landscape. ");
                expanded.append("Industry experts continue to monitor breakthroughs that could have widespread implications.\n\n");
                break;
            case "business":
                expanded.append("💼 Business Impact: This reflects market dynamics affecting investors and organizations worldwide. ");
                expanded.append("Analysts are watching these developments closely.\n\n");
                break;
            case "sports":
                expanded.append("⚽ Sports Update: This event demonstrates excellence in athletic competition. ");
                expanded.append("The sports industry continues to grow globally.\n\n");
                break;
            case "world":
                expanded.append("🌍 Global Perspective: This international story reflects our interconnected world. ");
                expanded.append("Understanding these events is crucial for global awareness.\n\n");
                break;
            case "health":
                expanded.append("🏥 Health Significance: This contributes to ongoing medical research and wellness initiatives. ");
                expanded.append("Healthcare professionals continue advancing treatment options.\n\n");
                break;
        }
        
        expanded.append("🔗 Source: BBC News");
        return expanded.toString();
    }
    
    private String getBBCRSSUrl(String category) {
        return switch (category.toLowerCase()) {
            case "technology" -> "https://feeds.bbci.co.uk/news/technology/rss.xml";
            case "business" -> "https://feeds.bbci.co.uk/news/business/rss.xml";
            case "sports" -> "https://feeds.bbci.co.uk/sport/rss.xml";
            case "world" -> "https://feeds.bbci.co.uk/news/world/rss.xml";
            case "health" -> "https://feeds.bbci.co.uk/news/health/rss.xml";
            case "science" -> "https://feeds.bbci.co.uk/news/science_and_environment/rss.xml";
            case "entertainment" -> "https://feeds.bbci.co.uk/news/entertainment_and_arts/rss.xml";
            default -> "https://feeds.bbci.co.uk/news/rss.xml";
        };
    }
}
