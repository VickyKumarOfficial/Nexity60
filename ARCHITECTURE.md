# Nexity60 PWA - Architecture Overview

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER DEVICES                            │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐       │
│  │ Desktop  │  │  Tablet  │  │  Mobile  │  │  Mobile  │       │
│  │ Browser  │  │ Browser  │  │ Browser  │  │   PWA    │       │
│  └─────┬────┘  └─────┬────┘  └─────┬────┘  └─────┬────┘       │
│        │             │             │             │             │
└────────┼─────────────┼─────────────┼─────────────┼─────────────┘
         │             │             │             │
         └─────────────┴─────────────┴─────────────┘
                        │
                        │ HTTPS
                        │
         ┌──────────────▼─────────────────┐
         │    REACT PWA FRONTEND          │
         │  (Netlify/Vercel/Render)       │
         │                                │
         │  ┌──────────────────────────┐  │
         │  │    App.js (Router)       │  │
         │  ├──────────────────────────┤  │
         │  │  NewsPage.js             │  │
         │  │  SavedArticlesPage.js    │  │
         │  │  NewsCard.js             │  │
         │  ├──────────────────────────┤  │
         │  │  Service Worker          │  │
         │  │  (Offline Cache)         │  │
         │  ├──────────────────────────┤  │
         │  │  Theme System            │  │
         │  │  (Dark/Light Mode)       │  │
         │  └──────────────────────────┘  │
         │                                │
         └────────────┬───────────────────┘
                      │
                      │ REST API (JSON)
                      │ Axios HTTP Client
                      │
         ┌────────────▼───────────────────┐
         │  SPRING BOOT BACKEND           │
         │  (Render/Koyeb/Railway)        │
         │                                │
         │  ┌──────────────────────────┐  │
         │  │  NewsController.java     │  │
         │  │  (REST API Endpoints)    │  │
         │  │  - GET /api/news/{cat}   │  │
         │  │  - GET /api/news/saved   │  │
         │  │  - POST /api/news/save   │  │
         │  │  - DELETE /api/news/...  │  │
         │  └──────────┬───────────────┘  │
         │             │                  │
         │  ┌──────────▼───────────────┐  │
         │  │  NewsService.java        │  │
         │  │  (Business Logic)        │  │
         │  │  - RSS Parsing           │  │
         │  │  - Content Expansion     │  │
         │  │  - Article Processing    │  │
         │  └──────────┬───────────────┘  │
         │             │                  │
         │  ┌──────────▼───────────────┐  │
         │  │  NewsArticleRepository   │  │
         │  │  (Spring Data JPA)       │  │
         │  └──────────┬───────────────┘  │
         │             │                  │
         │  ┌──────────▼───────────────┐  │
         │  │  NewsArticle.java        │  │
         │  │  (JPA Entity)            │  │
         │  │  - id, title, url        │  │
         │  │  - content, category     │  │
         │  │  - source, publishedAt   │  │
         │  │  - isSaved               │  │
         │  └──────────┬───────────────┘  │
         │             │                  │
         └─────────────┼──────────────────┘
                       │
         ┌─────────────▼──────────────────┐
         │      DATABASE                  │
         │  ┌──────────────────────────┐  │
         │  │  H2 (Development)        │  │
         │  │  PostgreSQL (Production) │  │
         │  │                          │  │
         │  │  Table: NEWS_ARTICLE     │  │
         │  │  - ID (PK)               │  │
         │  │  - TITLE                 │  │
         │  │  - URL                   │  │
         │  │  - CONTENT (TEXT)        │  │
         │  │  - CATEGORY              │  │
         │  │  - SOURCE                │  │
         │  │  - PUBLISHED_AT          │  │
         │  │  - IS_SAVED (BOOLEAN)    │  │
         │  └──────────────────────────┘  │
         └────────────────────────────────┘
                       │
         ┌─────────────▼──────────────────┐
         │   EXTERNAL DATA SOURCES        │
         │                                │
         │  ┌──────────────────────────┐  │
         │  │  BBC RSS Feeds           │  │
         │  │  - News API              │  │
         │  │  - XML/RSS Format        │  │
         │  │                          │  │
         │  │  Categories:             │  │
         │  │  • Technology            │  │
         │  │  • Business              │  │
         │  │  • Sports                │  │
         │  │  • World                 │  │
         │  │  • Health                │  │
         │  │  • Science               │  │
         │  │  • Entertainment         │  │
         │  └──────────────────────────┘  │
         └────────────────────────────────┘
```

## Data Flow

### 1. Fetching News Articles

```
User selects category → NewsPage.js → Axios GET request
                                          ↓
                           /api/news/{category}
                                          ↓
                              NewsController.java
                                          ↓
                               NewsService.java
                                          ↓
        Fetch from BBC RSS ← Check database cache
                ↓
         Parse XML/RSS
                ↓
      Expand article content (1,200+ chars)
                ↓
      Create NewsArticle entities
                ↓
      Save to database (Repository)
                ↓
      Return JSON to frontend
                ↓
      NewsPage displays in NewsCard components
```

### 2. Saving an Article

```
User clicks Save button → NewsCard.js → Axios POST request
                                            ↓
                            /api/news/save/{id}
                                            ↓
                               NewsController.java
                                            ↓
                                NewsService.java
                                            ↓
                     Find article by ID (Repository)
                                            ↓
                        Set isSaved = true
                                            ↓
                      Save to database (JPA)
                                            ↓
                       Return success response
                                            ↓
                          Show success message
```

### 3. Viewing Saved Articles

```
User clicks "Saved Articles" → SavedArticlesPage.js → GET request
                                                          ↓
                                          /api/news/saved
                                                          ↓
                                             NewsController.java
                                                          ↓
                                              NewsService.java
                                                          ↓
                           Repository.findByIsSavedTrue()
                                                          ↓
                                   Return saved articles (JSON)
                                                          ↓
                              Display in NewsCard components
```

## Component Hierarchy

### Frontend (React)

```
App.js (Root)
├── Header
│   ├── Logo
│   ├── Navigation Links
│   │   ├── Live News (/)
│   │   └── Saved Articles (/saved)
│   └── Theme Toggle Button
│
├── Router
│   ├── Route: / → NewsPage.js
│   │   ├── Category Selector
│   │   ├── Refresh Button
│   │   └── Articles Grid
│   │       └── NewsCard.js (repeated)
│   │           ├── Category Badge
│   │           ├── Title
│   │           ├── Meta (source, date)
│   │           ├── Content Preview
│   │           ├── Save Button
│   │           └── Read More Link
│   │
│   └── Route: /saved → SavedArticlesPage.js
│       ├── Page Title
│       ├── Refresh Button
│       └── Saved Articles Grid
│           └── NewsCard.js (repeated)
│               └── Unsave Button
│
└── Service Worker (Background)
    ├── Cache Assets
    ├── Offline Support
    └── Background Sync
```

### Backend (Spring Boot)

```
Nexity60Application.java (Main)
│
├── @RestController: NewsController
│   ├── GET /api/news/{category}
│   ├── GET /api/news/saved
│   ├── POST /api/news/save/{id}
│   └── DELETE /api/news/unsave/{id}
│
├── @Service: NewsService
│   ├── fetchNewsByCategory()
│   ├── fetchFromRSS()
│   ├── parseXML()
│   ├── expandArticleContent()
│   ├── getSavedArticles()
│   ├── saveArticle()
│   └── unsaveArticle()
│
├── @Repository: NewsArticleRepository
│   ├── findByIsSavedTrue()
│   ├── findByCategoryIgnoreCase()
│   └── save(), delete() (JPA methods)
│
└── @Entity: NewsArticle
    ├── id (Long, PK)
    ├── title (String)
    ├── url (String)
    ├── content (String, TEXT)
    ├── category (String)
    ├── source (String)
    ├── publishedAt (LocalDateTime)
    └── isSaved (Boolean)
```

## Technology Stack Layers

```
┌─────────────────────────────────────────┐
│           PRESENTATION LAYER            │
│  React 18.2 + React Router + Axios      │
│  CSS Variables, Service Worker          │
└─────────────┬───────────────────────────┘
              │
┌─────────────▼───────────────────────────┐
│          APPLICATION LAYER              │
│  Spring Boot 3.2 Controllers            │
│  REST API, CORS, Error Handling         │
└─────────────┬───────────────────────────┘
              │
┌─────────────▼───────────────────────────┐
│           BUSINESS LAYER                │
│  Spring Services, RSS Parsing           │
│  Content Expansion, Data Processing     │
└─────────────┬───────────────────────────┘
              │
┌─────────────▼───────────────────────────┐
│          PERSISTENCE LAYER              │
│  Spring Data JPA, Hibernate             │
│  Repository Pattern                     │
└─────────────┬───────────────────────────┘
              │
┌─────────────▼───────────────────────────┐
│            DATA LAYER                   │
│  H2 Database (Dev)                      │
│  PostgreSQL (Production)                │
└─────────────────────────────────────────┘
```

## Deployment Architecture

```
┌────────────────────────────────────────────────────┐
│                  INTERNET                          │
└───────────┬────────────────────────┬───────────────┘
            │                        │
┌───────────▼──────────┐  ┌──────────▼──────────────┐
│  Netlify/Vercel      │  │  Render/Koyeb/Railway   │
│  (Static Hosting)    │  │  (PaaS Hosting)         │
│                      │  │                         │
│  React PWA Frontend  │  │  Spring Boot Backend    │
│  - HTML, CSS, JS     │  │  - Java 17 Runtime      │
│  - Service Worker    │  │  - Embedded Tomcat      │
│  - Build artifacts   │  │  - JAR execution        │
│                      │  │                         │
│  CDN Distribution    │  │  PostgreSQL Database    │
│  HTTPS + Caching     │  │  (Managed or add-on)    │
└──────────────────────┘  └─────────────────────────┘
```

## Security Flow

```
Client Request (HTTPS only)
      ↓
JWT/Session (if auth added)
      ↓
CORS Validation
      ↓
Input Validation
      ↓
Business Logic
      ↓
Database (Parameterized Queries)
      ↓
Output Sanitization
      ↓
JSON Response (HTTPS)
```

## PWA Lifecycle

```
First Visit:
1. Load HTML
2. Load JavaScript
3. Register Service Worker
4. Cache static assets
5. App ready

Subsequent Visits:
1. Service Worker intercepts request
2. Return cached assets (fast!)
3. Fetch fresh data in background
4. Update cache
5. App instant load

Offline:
1. Service Worker intercepts request
2. Return cached assets
3. Show cached data
4. Display "offline" message for API calls
5. Queue updates for when online
```

## State Management

```
Frontend (React):
- Component State (useState)
- LocalStorage (theme preference)
- Service Worker Cache (offline data)

Backend (Spring Boot):
- HTTP Sessions (stateless REST)
- Database (persistent state)
- In-memory cache (optional future enhancement)
```

---

This architecture provides:
✅ **Scalability** - Can handle thousands of users
✅ **Maintainability** - Clear separation of concerns
✅ **Performance** - Caching at multiple levels
✅ **Reliability** - Works offline, error handling
✅ **Security** - HTTPS, CORS, input validation
✅ **Extensibility** - Easy to add features
