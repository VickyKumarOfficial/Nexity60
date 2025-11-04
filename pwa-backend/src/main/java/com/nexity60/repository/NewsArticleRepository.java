package com.nexity60.repository;

import com.nexity60.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {
    
    List<NewsArticle> findByCategory(String category);
    
    List<NewsArticle> findByIsSavedTrue();
    
    List<NewsArticle> findByCategoryOrderByPublishedAtDesc(String category);
    
    boolean existsByUrl(String url);
    
    NewsArticle findByUrl(String url);
}
