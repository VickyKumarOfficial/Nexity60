import React, { useState, useEffect } from 'react';
import axios from 'axios';
import NewsCard from '../components/NewsCard';
import './NewsPage.css';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

function NewsPage() {
  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [category, setCategory] = useState('trending');
  const [error, setError] = useState(null);

  const categories = [
    'Trending', 'Technology', 'Business', 'Sports',
    'World', 'Health', 'Science', 'Entertainment'
  ];

  useEffect(() => {
    fetchNews(category);
    // Scroll to top when category changes
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }, [category]);

  const fetchNews = async (cat) => {
    setLoading(true);
    setError(null);
    try {
      const response = await axios.get(`${API_URL}/news/${cat}`);
      setArticles(response.data);
    } catch (err) {
      setError('Failed to fetch news. Please try again.');
      console.error('Error fetching news:', err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="news-page">
      <div className="controls">
        <label className="category-label">📚 Category:</label>
        <select 
          className="category-selector"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        >
          {categories.map(cat => (
            <option key={cat} value={cat.toLowerCase()}>
              {cat}
            </option>
          ))}
        </select>
        <button 
          className="refresh-button"
          onClick={() => fetchNews(category)}
          disabled={loading}
        >
          🔄 {loading ? 'Loading...' : 'Refresh'}
        </button>
      </div>

      {error && <div className="error-message">{error}</div>}

      <h2 className="category-title">
        {category.charAt(0).toUpperCase() + category.slice(1)} News
      </h2>

      <div className="articles-container">
        {loading && articles.length === 0 ? (
          <div className="loading">Loading news...</div>
        ) : articles.length === 0 ? (
          <div className="no-articles">No articles found</div>
        ) : (
          articles.map(article => (
            <NewsCard 
              key={article.id}
              article={article}
            />
          ))
        )}
      </div>
    </div>
  );
}

export default NewsPage;
