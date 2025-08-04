// SummarizerTest.java - Unit tests for summarization logic
package core;

import model.NewsArticle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SummarizerTest {
    
    private Summarizer summarizer;
    private NewsArticle testArticle;
    
    @BeforeEach
    void setUp() {
        summarizer = new Summarizer();
        testArticle = new NewsArticle();
        testArticle.setTitle("Test News Article About Technology");
        testArticle.setContent("This is a test article about technology. " +
                "Technology has been advancing rapidly in recent years. " +
                "Many companies are investing heavily in artificial intelligence. " +
                "According to experts, AI will transform various industries. " +
                "The future of technology looks very promising. " +
                "Important developments are happening every day. " +
                "Significant progress has been made in machine learning. " +
                "This technology will change how we work and live.");
    }
    
    @Test
    void testGenerateSummary_ValidArticle() {
        String summary = summarizer.generateSummary(testArticle);
        
        assertNotNull(summary);
        assertFalse(summary.isEmpty());
        assertTrue(summary.length() < testArticle.getContent().length());
        assertTrue(testArticle.getReadingTimeSeconds() > 0);
    }
    
    @Test
    void testGenerateSummary_EmptyContent() {
        testArticle.setContent("");
        String summary = summarizer.generateSummary(testArticle);
        
        assertEquals("Summary not available.", summary);
    }
    
    @Test
    void testGenerateSummary_NullContent() {
        testArticle.setContent(null);
        String summary = summarizer.generateSummary(testArticle);
        
        assertEquals("Summary not available.", summary);
    }
    
    @Test
    void testGenerateSummary_ShortContent() {
        testArticle.setContent("Short article. Very brief.");
        String summary = summarizer.generateSummary(testArticle);
        
        assertEquals(testArticle.getContent(), summary);
    }
    
    @Test
    void testKeyPointsExtraction() {
        summarizer.generateSummary(testArticle);
        
        assertNotNull(testArticle.getKeyPoints());
        assertFalse(testArticle.getKeyPoints().isEmpty());
        assertTrue(testArticle.getKeyPoints().size() <= 3);
    }
}