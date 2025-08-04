// ApplicationConfig.java - Application configuration and constants
package config;

public class ApplicationConfig {
    
    // Application Info
    public static final String APP_NAME = "Nexity60";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_DESCRIPTION = "Smart Summarized News Reader";
    
    // Reading Configuration
    public static final int TARGET_READING_TIME_SECONDS = 60;
    public static final int AVERAGE_READING_SPEED_WPM = 200;
    public static final int MAX_SUMMARY_SENTENCES = 5;
    public static final int MAX_ARTICLES_PER_CATEGORY = 10;
    
    // Network Configuration
    public static final int CONNECTION_TIMEOUT_MS = 10000;
    public static final int READ_TIMEOUT_MS = 15000;
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36";
    
    // File Storage
    public static final String STORAGE_DIRECTORY = "saved_articles";
    public static final String CONFIG_FILE = "app_config.properties";
    public static final String ARTICLES_FILE = "articles.json";
    
    // UI Configuration
    public static final int WINDOW_WIDTH = 1200;
    public static final int WINDOW_HEIGHT = 800;
    public static final int SIDEBAR_WIDTH = 250;
    
    // News Sources Configuration
    public static final String[] DEFAULT_CATEGORIES = {
        "Trending", "Politics", "Sports", "Technology", 
        "Business", "Entertainment", "Health", "Science"
    };
    
    // Logging Configuration
    public static final String LOG_LEVEL = "INFO";
    public static final String LOG_FILE = "Nexity60.log";
    
    // Feature Flags
    public static final boolean ENABLE_OFFLINE_MODE = true;
    public static final boolean ENABLE_AUTO_REFRESH = false;
    public static final boolean ENABLE_NOTIFICATIONS = false;
    public static final boolean ENABLE_DARK_MODE = true;
    
    // News Source URLs
    public static class NewsUrls {
        public static final String BBC_BASE = "https://www.bbc.com";
        public static final String BBC_NEWS = BBC_BASE + "/news";
        public static final String BBC_POLITICS = BBC_NEWS + "/politics";
        public static final String BBC_SPORTS = BBC_BASE + "/sport";
        public static final String BBC_TECHNOLOGY = BBC_NEWS + "/technology";
        public static final String BBC_BUSINESS = BBC_NEWS + "/business";
        public static final String BBC_ENTERTAINMENT = BBC_NEWS + "/entertainment-arts";
        public static final String BBC_HEALTH = BBC_NEWS + "/health";
        public static final String BBC_SCIENCE = BBC_NEWS + "/science-environment";
        
        public static final String REUTERS_BASE = "https://www.reuters.com";
        public static final String REUTERS_TECHNOLOGY = REUTERS_BASE + "/technology";
        public static final String REUTERS_BUSINESS = REUTERS_BASE + "/business";
    }
}