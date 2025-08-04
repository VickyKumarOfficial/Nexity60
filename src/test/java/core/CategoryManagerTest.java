// CategoryManagerTest.java - Unit tests for category management
package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;

import java.io.File;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryManagerTest {
    
    private CategoryManager categoryManager;
    private final String TEST_CONFIG_FILE = "test_categories.properties";
    
    @BeforeEach
    void setUp() {
        categoryManager = new CategoryManager();
    }
    
    @AfterEach
    void tearDown() {
        // Clean up test files
        File testFile = new File(TEST_CONFIG_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    void testDefaultCategories() {
        Set<String> categories = categoryManager.getEnabledCategories();
        
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.contains("Trending"));
        assertTrue(categories.contains("Politics"));
        assertTrue(categories.contains("Sports"));
    }
    
    @Test
    void testEnableDisableCategory() {
        String testCategory = "TestCategory";
        
        // Enable category
        categoryManager.enableCategory(testCategory);
        assertTrue(categoryManager.isCategoryEnabled(testCategory));
        
        // Disable category
        categoryManager.disableCategory(testCategory);
        assertFalse(categoryManager.isCategoryEnabled(testCategory));
    }
    
    @Test
    void testGetCategoryUrl() {
        String url = categoryManager.getCategoryUrl("Technology");
        
        assertNotNull(url);
        assertTrue(url.startsWith("http"));
    }
}