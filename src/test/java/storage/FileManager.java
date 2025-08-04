// Updated FileManager.java - Remove the inline adapter and use the utils package
package storage;

import model.NewsArticle;
import utils.LocalDateTimeAdapter; // Import from utils package
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final String STORAGE_DIR = "saved_articles";
    private final String ARTICLES_FILE = "articles.json";
    private final Gson gson;
    
    public FileManager() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()) // Use from utils
                .setPrettyPrinting()
                .create();
        
        // Create storage directory if it doesn't exist
        new File(STORAGE_DIR).mkdirs();
    }
    
    /**
     * Saves articles to local storage
     */
    public void saveArticles(List<NewsArticle> articles) {
        try (FileWriter writer = new FileWriter(new File(STORAGE_DIR, ARTICLES_FILE))) {
            gson.toJson(articles, writer);
        } catch (IOException e) {
            System.err.println("Failed to save articles: " + e.getMessage());
        }
    }
    
    /**
     * Loads articles from local storage
     */
    public List<NewsArticle> loadArticles() {
        File articlesFile = new File(STORAGE_DIR, ARTICLES_FILE);
        if (!articlesFile.exists()) {
            return new ArrayList<>();
        }
        
        try (FileReader reader = new FileReader(articlesFile)) {
            Type listType = new TypeToken<List<NewsArticle>>(){}.getType();
            List<NewsArticle> articles = gson.fromJson(reader, listType);
            return articles != null ? articles : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Failed to load articles: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * Saves individual article summary
     */
    public void saveArticleSummary(NewsArticle article) {
        String filename = sanitizeFilename(article.getTitle()) + ".json";
        File file = new File(STORAGE_DIR, filename);
        
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(article, writer);
        } catch (IOException e) {
            System.err.println("Failed to save article summary: " + e.getMessage());
        }
    }
    
    /**
     * Loads a specific article summary by filename
     */
    public NewsArticle loadArticleSummary(String filename) {
        File file = new File(STORAGE_DIR, filename);
        if (!file.exists()) {
            return null;
        }
        
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, NewsArticle.class);
        } catch (IOException e) {
            System.err.println("Failed to load article summary: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets list of saved article files
     */
    public List<String> getSavedArticleFiles() {
        File dir = new File(STORAGE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json") && !name.equals(ARTICLES_FILE));
        
        List<String> filenames = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                filenames.add(file.getName());
            }
        }
        return filenames;
    }
    
    /**
     * Deletes a saved article file
     */
    public boolean deleteArticle(String filename) {
        File file = new File(STORAGE_DIR, filename);
        return file.exists() && file.delete();
    }
    
    /**
     * Gets the total number of saved articles
     */
    public int getSavedArticleCount() {
        return getSavedArticleFiles().size();
    }
    
    /**
     * Clears all saved articles
     */
    public void clearAllSavedArticles() {
        File dir = new File(STORAGE_DIR);
        File[] files = dir.listFiles((d, name) -> name.endsWith(".json"));
        
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
    }
    
    private String sanitizeFilename(String filename) {
        return filename.replaceAll("[^a-zA-Z0-9.-]", "_")
                       .substring(0, Math.min(50, filename.length()));
    }
}