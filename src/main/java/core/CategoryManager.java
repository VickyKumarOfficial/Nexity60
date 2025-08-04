package core;

import java.util.*;

public class CategoryManager {
    private Set<String> enabledCategories;
    
    public CategoryManager() {
        this.enabledCategories = new HashSet<>();
        // Default enabled categories
        this.enabledCategories.addAll(Arrays.asList(
            "Trending", "Politics", "Sports", "Technology", 
            "Business", "Entertainment", "Health", "Science"
        ));
    }
    
    public Set<String> getEnabledCategories() {
        return new HashSet<>(enabledCategories);
    }
    
    public boolean isCategoryEnabled(String category) {
        return enabledCategories.contains(category);
    }
    
    public void enableCategory(String category) {
        enabledCategories.add(category);
    }
    
    public void disableCategory(String category) {
        enabledCategories.remove(category);
    }
    
    public String getCategoryUrl(String category) {
        // Simple URL mapping for demo purposes
        switch (category.toLowerCase()) {
            case "trending": return "https://www.bbc.com/news";
            case "politics": return "https://www.bbc.com/news/politics";
            case "sports": return "https://www.bbc.com/sport";
            case "technology": return "https://www.bbc.com/news/technology";
            case "business": return "https://www.bbc.com/news/business";
            case "entertainment": return "https://www.bbc.com/news/entertainment-arts";
            case "health": return "https://www.bbc.com/news/health";
            case "science": return "https://www.bbc.com/news/science-environment";
            default: return "https://www.bbc.com/news";
        }
    }
}
