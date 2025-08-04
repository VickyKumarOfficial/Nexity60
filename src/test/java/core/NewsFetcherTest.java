// NewsFetcherTest.java - Unit tests for news fetching
package core;

import model.NewsArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class NewsFetcherTest {
    
    private NewsFetcher newsFetcher;
    
    @BeforeEach
    void setUp() {
        newsFetcher = new NewsFetcher();
    }
    
    @Test
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void testFetchNewsByCategory_ValidCategory() {
        CompletableFuture<List<NewsArticle>> future = newsFetcher.fetchNewsByCategory("Technology");
        
        assertDoesNotThrow(() -> {
            List<NewsArticle> articles = future.get();
            assertNotNull(articles);
            // Note: May be empty if network issues or no articles found
        });
    }
    
    @Test
    void testFetchNewsByCategory_InvalidCategory() {
        CompletableFuture<List<NewsArticle>> future = newsFetcher.fetchNewsByCategory("NonExistentCategory");
        
        assertDoesNotThrow(() -> {
            List<NewsArticle> articles = future.get();
            assertNotNull(articles);
        });
    }
}