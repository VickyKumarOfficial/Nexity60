import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import './App.css';
import NewsPage from './pages/NewsPage';
import SavedArticlesPage from './pages/SavedArticlesPage';

function App() {
  const [isDarkMode, setIsDarkMode] = useState(false);

  useEffect(() => {
    // Load theme preference
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      setIsDarkMode(true);
      document.body.classList.add('dark-mode');
    }
  }, []);

  const toggleTheme = () => {
    setIsDarkMode(!isDarkMode);
    if (!isDarkMode) {
      document.body.classList.add('dark-mode');
      localStorage.setItem('theme', 'dark');
    } else {
      document.body.classList.remove('dark-mode');
      localStorage.setItem('theme', 'light');
    }
  };

  return (
    <Router>
      <div className="App">
        <header className="app-header">
          <div className="header-content">
            <h1 className="app-title">🌍 Nexity60</h1>
            <p className="app-subtitle">Real-time news from trusted sources</p>
          </div>
          <div className="header-controls">
            <Link to="/" className="nav-link">📰 Live News</Link>
            <Link to="/saved" className="nav-link">📚 Saved Articles</Link>
            <button className="theme-toggle" onClick={toggleTheme}>
              {isDarkMode ? '☀️ Light Mode' : '🌙 Dark Mode'}
            </button>
          </div>
        </header>

        <Routes>
          <Route path="/" element={<NewsPage />} />
          <Route path="/saved" element={<SavedArticlesPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
