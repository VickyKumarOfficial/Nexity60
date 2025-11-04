import React, { useState, useEffect } from 'react';
import axios from 'axios';
import NewsCard from '../components/NewsCard';
import './SavedArticlesPage.css';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

function SavedArticlesPage() {
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchSavedArticles();
  }, []);

  const fetchSavedArticles = async () => {
    setLoading(true);
    try {
      const response = await axios.get(`${API_URL}/news/saved`);
      setArticles(response.data);
    } catch (err) {
      console.error('Error fetching saved articles:', err);
    } finally {
      setLoading(false);
    }
  };

  const handleUnsaveArticle = async (articleId) => {
    try {
      await axios.delete(`${API_URL}/news/unsave/${articleId}`);
      setArticles(articles.filter(article => article.id !== articleId));
      alert('Article removed from saved list');
    } catch (err) {
      alert('Failed to remove article');
      console.error('Error removing article:', err);
    }
  };

  return (
    <div className="saved-articles-page">
      <h2 className="page-title">📚 Your Saved Articles</h2>
      
      <button 
        className="refresh-button"
        onClick={fetchSavedArticles}
      >
        🔄 Refresh Saved Articles
      </button>

      <div className="articles-container">
        {loading ? (
          <div className="loading">Loading saved articles...</div>
        ) : articles.length === 0 ? (
          <div className="no-articles">
            <p>No saved articles yet</p>
            <p className="subtitle">Save articles from the news feed to view them here later</p>
          </div>
        ) : (
          articles.map(article => (
            <NewsCard 
              key={article.id}
              article={article}
              onUnsave={handleUnsaveArticle}
              isSaved={true}
            />
          ))
        )}
      </div>
    </div>
  );
}

export default SavedArticlesPage;
