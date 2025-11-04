import React, { useState } from 'react';
import axios from 'axios';
import './NewsCard.css';

const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080/api';

function NewsCard({ article, onUnsave, isSaved: initialIsSaved = false }) {
  const [isSaved, setIsSaved] = useState(initialIsSaved || article.isSaved || false);

  const handleSaveToggle = async () => {
    try {
      if (isSaved) {
        // Unsave article
        await axios.delete(`${API_URL}/news/unsave/${article.id}`);
        setIsSaved(false);
        if (onUnsave) {
          onUnsave(article.id);
        }
        alert('Article removed from saved list!');
      } else {
        // Save article
        await axios.post(`${API_URL}/news/save/${article.id}`);
        setIsSaved(true);
        alert('Article saved successfully!');
      }
    } catch (err) {
      alert(`Failed to ${isSaved ? 'unsave' : 'save'} article`);
      console.error('Error toggling save:', err);
    }
  };

  const getCategoryBadge = (category) => {
    const badges = {
      'technology': { emoji: '💻', color: '#4a90e2' },
      'business': { emoji: '💼', color: '#27ae60' },
      'sports': { emoji: '⚽', color: '#e67e22' },
      'world': { emoji: '🌍', color: '#3498db' },
      'health': { emoji: '🏥', color: '#e74c3c' },
      'science': { emoji: '🔬', color: '#9b59b6' },
      'entertainment': { emoji: '🎬', color: '#f39c12' },
      'general': { emoji: '📰', color: '#95a5a6' }
    };

    const badge = badges[category?.toLowerCase()] || badges['general'];
    return { ...badge, name: category || 'General' };
  };

  const badge = getCategoryBadge(article.category);

  return (
    <div className="news-card">
      <div className="card-content-wrapper">
        <div className="card-text-section">
          <div className="card-header">
            <span 
              className="category-badge" 
              style={{ backgroundColor: badge.color }}
            >
              {badge.emoji} {badge.name}
            </span>
            <button 
              className={`save-button ${isSaved ? 'saved' : ''}`}
              onClick={handleSaveToggle}
              title={isSaved ? 'Remove from saved' : 'Save article'}
            >
              {isSaved ? '❤️' : '🤍'}
            </button>
          </div>

          <h3 className="article-title">{article.title}</h3>

          <div className="article-meta">
            <span className="source">📡 {article.source || 'BBC News'}</span>
            {article.publishedAt && (
              <span className="date">
                📅 {new Date(article.publishedAt).toLocaleDateString()}
              </span>
            )}
          </div>

          <p className="article-content">
            {article.content?.substring(0, 200)}
            {article.content?.length > 200 ? '...' : ''}
          </p>

          <a 
            href={article.url} 
            target="_blank" 
            rel="noopener noreferrer"
            className="read-more-button"
          >
            📖 Read Full Article
          </a>
        </div>

        <div className="card-image-section">
          <img 
            src={article.imageUrl || 'https://via.placeholder.com/400x300/4a90e2/ffffff?text=BBC+News'} 
            alt={article.title}
            className="article-image"
            onError={(e) => {
              e.target.src = 'https://via.placeholder.com/400x300/4a90e2/ffffff?text=BBC+News';
            }}
          />
        </div>
      </div>
    </div>
  );
}

export default NewsCard;
