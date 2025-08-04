// Summarizer.java - Rule-based text summarization
package core;

import model.NewsArticle;
import java.util.*;
import java.util.stream.Collectors;

public class Summarizer {
    private static final int TARGET_READING_TIME_SECONDS = 60;
    private static final int AVERAGE_READING_SPEED_WPM = 200;
    private static final int TARGET_WORD_COUNT = (TARGET_READING_TIME_SECONDS * AVERAGE_READING_SPEED_WPM) / 60;
    
    /**
     * Generates a summary for the given article using extractive summarization
     */
    public String generateSummary(NewsArticle article) {
        if (article.getContent() == null || article.getContent().trim().isEmpty()) {
            System.out.println("DEBUG: No content for article: " + article.getTitle());
            return "Summary not available - no content found.";
        }
        
        String content = article.getContent();
        System.out.println("DEBUG: Generating summary for: " + article.getTitle());
        System.out.println("DEBUG: Content length: " + content.length());
        System.out.println("DEBUG: Content preview: " + content.substring(0, Math.min(100, content.length())) + "...");
        
        List<String> sentences = splitIntoSentences(content);
        System.out.println("DEBUG: Split into " + sentences.size() + " sentences");
        
        if (sentences.size() <= 3) {
            article.setReadingTimeSeconds(estimateReadingTime(content));
            System.out.println("DEBUG: Article is short, returning full content");
            return content; // Article is already short
        }
        
        // Score sentences based on multiple factors
        Map<String, Double> sentenceScores = scoreSentences(sentences, article.getTitle());
        
        // Select top sentences for summary
        List<String> topSentences = selectTopSentences(sentenceScores, sentences, TARGET_WORD_COUNT);
        
        String summary = String.join(" ", topSentences);
        article.setReadingTimeSeconds(estimateReadingTime(summary));
        
        // Extract key points
        article.setKeyPoints(extractKeyPoints(topSentences));
        
        System.out.println("DEBUG: Generated summary length: " + summary.length());
        System.out.println("DEBUG: Summary preview: " + summary.substring(0, Math.min(100, summary.length())) + "...");
        
        return summary;
    }
    
    private List<String> splitIntoSentences(String text) {
        // Simple sentence splitting (can be improved with NLP libraries)
        return Arrays.stream(text.split("[.!?]+"))
                .map(String::trim)
                .filter(s -> s.length() > 10) // Filter very short fragments
                .collect(Collectors.toList());
    }
    
    private Map<String, Double> scoreSentences(List<String> sentences, String title) {
        Map<String, Double> scores = new HashMap<>();
        
        // Word frequency analysis
        Map<String, Integer> wordFreq = calculateWordFrequency(sentences);
        Set<String> titleWords = extractKeywords(title.toLowerCase());
        
        for (String sentence : sentences) {
            double score = 0.0;
            String lowerSentence = sentence.toLowerCase();
            String[] words = lowerSentence.split("\\s+");
            
            // Position score (first few sentences are often important)
            int position = sentences.indexOf(sentence);
            if (position < 3) score += 0.3;
            
            // Title relevance score
            for (String titleWord : titleWords) {
                if (lowerSentence.contains(titleWord)) {
                    score += 0.5;
                }
            }
            
            // Word frequency score
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", "");
                if (word.length() > 3 && wordFreq.containsKey(word)) {
                    score += wordFreq.get(word) * 0.1;
                }
            }
            
            // Sentence length score (moderate length preferred)
            int wordCount = words.length;
            if (wordCount >= 10 && wordCount <= 30) {
                score += 0.2;
            }
            
            // Keyword indicators
            if (lowerSentence.contains("important") || lowerSentence.contains("significant") ||
                lowerSentence.contains("announced") || lowerSentence.contains("according")) {
                score += 0.3;
            }
            
            scores.put(sentence, score);
        }
        
        return scores;
    }
    
    private Map<String, Integer> calculateWordFrequency(List<String> sentences) {
        Map<String, Integer> freq = new HashMap<>();
        Set<String> stopWords = getStopWords();
        
        for (String sentence : sentences) {
            String[] words = sentence.toLowerCase().split("\\s+");
            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z]", "");
                if (word.length() > 3 && !stopWords.contains(word)) {
                    freq.merge(word, 1, Integer::sum);
                }
            }
        }
        
        return freq;
    }
    
    private List<String> selectTopSentences(Map<String, Double> scores, List<String> sentences, int targetWordCount) {
        List<String> selected = new ArrayList<>();
        int currentWordCount = 0;
        
        // Sort sentences by score (descending)
        List<String> sortedSentences = sentences.stream()
                .sorted((s1, s2) -> Double.compare(scores.get(s2), scores.get(s1)))
                .collect(Collectors.toList());
        
        for (String sentence : sortedSentences) {
            int sentenceWordCount = sentence.split("\\s+").length;
            if (currentWordCount + sentenceWordCount <= targetWordCount) {
                selected.add(sentence);
                currentWordCount += sentenceWordCount;
            }
            
            if (selected.size() >= 5) break; // Maximum 5 sentences
        }
        
        // Sort selected sentences by original order
        selected.sort(Comparator.comparingInt(sentences::indexOf));
        
        return selected;
    }
    
    private List<String> extractKeyPoints(List<String> sentences) {
        return sentences.stream()
                .limit(3) // Top 3 key points
                .map(s -> "• " + s)
                .collect(Collectors.toList());
    }
    
    private Set<String> extractKeywords(String text) {
        Set<String> stopWords = getStopWords();
        return Arrays.stream(text.split("\\s+"))
                .map(word -> word.replaceAll("[^a-zA-Z]", ""))
                .filter(word -> word.length() > 3)
                .filter(word -> !stopWords.contains(word))
                .collect(Collectors.toSet());
    }
    
    private Set<String> getStopWords() {
        return Set.of("this", "that", "with", "have", "will", "been", "said", "they", "them", 
                     "their", "what", "when", "where", "which", "while", "would", "could", 
                     "should", "about", "after", "before", "during", "through", "between");
    }
    
    private int estimateReadingTime(String text) {
        int wordCount = text.split("\\s+").length;
        return Math.max(10, (wordCount * 60) / AVERAGE_READING_SPEED_WPM);
    }
}