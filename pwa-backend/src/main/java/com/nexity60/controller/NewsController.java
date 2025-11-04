package com.nexity60.controller;

import com.nexity60.model.NewsArticle;
import com.nexity60.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@CrossOrigin(origins = "*")
public class NewsController {
    
    @Autowired
    private NewsService newsService;
    
    @GetMapping("/{category}")
    public ResponseEntity<List<NewsArticle>> getNewsByCategory(@PathVariable String category) {
        List<NewsArticle> articles = newsService.fetchNewsByCategory(category);
        return ResponseEntity.ok(articles);
    }
    
    @GetMapping("/saved")
    public ResponseEntity<List<NewsArticle>> getSavedArticles() {
        List<NewsArticle> articles = newsService.getSavedArticles();
        return ResponseEntity.ok(articles);
    }
    
    @PostMapping("/save/{id}")
    public ResponseEntity<NewsArticle> saveArticle(@PathVariable Long id) {
        NewsArticle article = newsService.saveArticle(id);
        return ResponseEntity.ok(article);
    }
    
    @DeleteMapping("/unsave/{id}")
    public ResponseEntity<Void> unsaveArticle(@PathVariable Long id) {
        newsService.unsaveArticle(id);
        return ResponseEntity.ok().build();
    }
}
