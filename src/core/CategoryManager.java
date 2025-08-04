
// CategoryManager.java - Manages user preferences and categories
package core;

import java.io.*;
import java.util.*;

public class CategoryManager {
    private final String CONFIG_FILE = "categories.properties";
    private Set<String> enabledCategories;
    private Map<String, String> categoryUrls;
    
    public CategoryManager() {
        this.enabledCategories = new HashSet<>();
        this.categoryUrls = new HashMap<>();
        loadConfiguration();
        initializeDefaultCategories();
    }
    
    private void initializeDefaultCategories() {
        if (enabledCategories.isEmpty()) {
            enabledCategories.addAll(Arrays.asList(
                "Trending", "Politics", "Sports", "Technology", 
                "Business", "Entertainment", "Health", "Science"
            ));
        }
        
        // Initialize category URLs
        categoryUrls.put("Trending", "https://www.bbc.com/news");
        categoryUrls.put("Politics", "https://www.bbc.com/news/politics");
        categoryUrls.put("Sports", "https://www.bbc.com/sport");
        categoryUrls.put("Technology", "https://www.bbc.com/news/technology");
        categoryUrls.put("Business", "https://www.bbc.com/news/business");
        categoryUrls.put("Entertainment", "https://www.bbc.com/news/entertainment-arts");
        categoryUrls.put("Health", "https://www.bbc.com/news/health");
        categoryUrls.put("Science", "https://www.bbc.com/news/science-environment");
    }
    
    public Set<String> getEnabledCategories() {
        return new HashSet<>(enabledCategories);
    }
    
    public void enableCategory(String category) {
        enabledCategories.add(category);
        saveConfiguration();
    }
    
    public void disableCategory(String category) {
        enabledCategories.remove(category);
        saveConfiguration();
    }
    
    public boolean isCategoryEnabled(String category) {
        return enabledCategories.contains(category);
    }
    
    public String getCategoryUrl(String category) {
        return categoryUrls.getOrDefault(category, "https://www.bbc.com/news");
    }
    
    private void loadConfiguration() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE)) {
            props.load(fis);
            String categories = props.getProperty("enabled_categories", "");
            if (!categories.isEmpty()) {
                enabledCategories.addAll(Arrays.asList(categories.split(",")));
            }
        } catch (IOException e) {
            // File doesn't exist yet, will be created on first save
        }
    }
    
    private void saveConfiguration() {
        Properties props = new Properties();
        props.setProperty("enabled_categories", String.join(",", enabledCategories));
        
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            props.store(fos, "Nexity60 Category Configuration");
        } catch (IOException e) {
            System.err.println("Failed to save configuration: " + e.getMessage());
        }
    }
}