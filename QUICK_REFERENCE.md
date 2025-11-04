# Nexity60 PWA - Quick Reference Card

## 🚀 Quick Start (Development)

### Option 1: Automatic Start (Windows)
```bash
# Double-click or run:
START_PWA.bat
# or
START_PWA.ps1
```

### Option 2: Manual Start
```bash
# Terminal 1 - Backend
cd pwa-backend
mvn spring-boot:run

# Terminal 2 - Frontend
cd pwa-frontend
npm install
npm start
```

**URLs:**
- Backend: http://localhost:8080
- Frontend: http://localhost:3000
- H2 Console: http://localhost:8080/h2-console

---

## 📡 API Endpoints

| Method | Endpoint | Description | Example |
|--------|----------|-------------|---------|
| `GET` | `/api/news/trending` | Get trending news | `curl http://localhost:8080/api/news/trending` |
| `GET` | `/api/news/technology` | Get tech news | `curl http://localhost:8080/api/news/technology` |
| `GET` | `/api/news/saved` | Get saved articles | `curl http://localhost:8080/api/news/saved` |
| `POST` | `/api/news/save/1` | Save article ID 1 | `curl -X POST http://localhost:8080/api/news/save/1` |
| `DELETE` | `/api/news/unsave/1` | Unsave article ID 1 | `curl -X DELETE http://localhost:8080/api/news/unsave/1` |

**Categories:** trending, technology, business, sports, world, health, science, entertainment

---

## 📂 Project Structure

```
Nexity60/
├── pwa-backend/          # Spring Boot API
│   ├── src/main/java/
│   │   └── com/nexity60/
│   └── pom.xml
│
├── pwa-frontend/         # React PWA
│   ├── src/
│   │   ├── App.js
│   │   ├── pages/
│   │   └── components/
│   └── package.json
│
└── Docs/
    ├── PWA_README.md
    ├── DEPLOYMENT_GUIDE.md
    ├── TESTING_GUIDE.md
    └── CHECKLIST.md
```

---

## 🛠️ Common Commands

### Backend
```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Package
mvn clean package

# Test
mvn test
```

### Frontend
```bash
# Install dependencies
npm install

# Start dev server
npm start

# Build for production
npm run build

# Test
npm test
```

---

## 🎨 Component Overview

### Frontend Components
- `App.js` - Main app with routing & theme toggle
- `NewsPage.js` - News feed with category selector
- `SavedArticlesPage.js` - Saved articles list
- `NewsCard.js` - Reusable article card

### Backend Components
- `NewsController.java` - REST API endpoints
- `NewsService.java` - Business logic & RSS parsing
- `NewsArticleRepository.java` - Database access
- `NewsArticle.java` - Entity model

---

## 🎭 Features

| Feature | Status | Location |
|---------|--------|----------|
| Live News Feed | ✅ | NewsPage.js |
| Save Articles | ✅ | NewsCard.js |
| Saved Articles Page | ✅ | SavedArticlesPage.js |
| Dark Mode | ✅ | App.js, App.css |
| Category Filter | ✅ | NewsPage.js |
| PWA Install | ✅ | manifest.json |
| Offline Support | ✅ | service-worker.js |
| Responsive Design | ✅ | All CSS files |

---

## 🐛 Troubleshooting

### Backend won't start
```bash
# Check if port 8080 is in use
netstat -ano | findstr :8080

# Kill process
taskkill /PID <pid> /F

# Restart backend
mvn spring-boot:run
```

### Frontend won't connect
- ✅ Check backend is running: http://localhost:8080/api/news/trending
- ✅ Verify `.env.development` has correct API URL
- ✅ Clear browser cache (Ctrl + Shift + Delete)
- ✅ Hard reload (Ctrl + Shift + R)

### Database errors
```bash
# Access H2 Console
http://localhost:8080/h2-console

# Credentials:
# JDBC URL: jdbc:h2:mem:nexity60db
# Username: sa
# Password: (empty)
```

### Build errors
```bash
# Backend
cd pwa-backend
mvn clean install -U

# Frontend
cd pwa-frontend
rm -rf node_modules package-lock.json
npm install
```

---

## 📊 Database Schema

```sql
CREATE TABLE NEWS_ARTICLE (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    TITLE VARCHAR(500),
    URL VARCHAR(1000),
    CONTENT TEXT,
    CATEGORY VARCHAR(100),
    SOURCE VARCHAR(200),
    PUBLISHED_AT TIMESTAMP,
    IS_SAVED BOOLEAN DEFAULT FALSE
);
```

---

## 🎯 Environment Variables

### Development (`.env.development`)
```
REACT_APP_API_URL=http://localhost:8080/api
```

### Production (`.env.production`)
```
REACT_APP_API_URL=https://your-backend-url.com/api
```

---

## 🚢 Deployment Checklist

### Pre-deployment
- [ ] All features tested locally
- [ ] No console errors
- [ ] Lighthouse score 90+
- [ ] Icons created (192x192, 512x512)
- [ ] Environment variables configured

### Backend (Render)
- [ ] GitHub repo pushed
- [ ] Render account created
- [ ] Web Service created
- [ ] Build command: `mvn clean install`
- [ ] Start command: `java -jar target/nexity60-0.0.1-SNAPSHOT.jar`
- [ ] Deployed & tested

### Frontend (Netlify)
- [ ] Netlify account created
- [ ] Site created from Git
- [ ] Build command: `npm run build`
- [ ] Publish directory: `build`
- [ ] Environment variable set
- [ ] Deployed & tested

### Post-deployment
- [ ] PWA installs on mobile
- [ ] Offline mode works
- [ ] All features functional
- [ ] Performance acceptable

---

## 📈 Performance Targets

| Metric | Target | Tool |
|--------|--------|------|
| Lighthouse Performance | 90+ | Chrome DevTools |
| First Contentful Paint | < 1.5s | Lighthouse |
| Time to Interactive | < 3.0s | Lighthouse |
| API Response Time | < 500ms | Network tab |
| Bundle Size | < 500KB | webpack-bundle-analyzer |

---

## 🔗 Quick Links

- **H2 Console**: http://localhost:8080/h2-console
- **API Docs**: See DEPLOYMENT_GUIDE.md
- **Testing Guide**: TESTING_GUIDE.md
- **Architecture**: ARCHITECTURE.md
- **Checklist**: CHECKLIST.md

---

## 💡 Tips

### Development
- Use `npm start` for hot reload in frontend
- Backend restarts automatically on code changes
- Open React DevTools for debugging

### Testing
- Test mobile view with Chrome DevTools (F12 → Toggle device toolbar)
- Test offline mode with Network tab (Offline checkbox)
- Use Lighthouse for performance audits

### Deployment
- Always test locally before deploying
- Use staging environment for testing
- Monitor logs after deployment

---

## 🎓 Learning Resources

- **Spring Boot**: https://spring.io/guides
- **React**: https://react.dev/learn
- **PWA**: https://web.dev/progressive-web-apps/
- **Deployment**: DEPLOYMENT_GUIDE.md

---

## 📝 Notes

- Default database is H2 (in-memory, resets on restart)
- For production, use PostgreSQL (see DEPLOYMENT_GUIDE.md)
- Service worker only works on HTTPS (or localhost)
- PWA install requires HTTPS in production

---

## ✅ Success Indicators

You know it's working when:
- ✅ Backend shows "Started Nexity60Application" in console
- ✅ Frontend opens at http://localhost:3000
- ✅ Articles load in News Feed
- ✅ Save button adds articles to Saved page
- ✅ Dark mode toggle works
- ✅ No errors in browser console

---

**Need help?** Check the detailed guides:
- Setup: PWA_README.md
- Deploy: DEPLOYMENT_GUIDE.md
- Test: TESTING_GUIDE.md
- Checklist: CHECKLIST.md

**Quick test**: `curl http://localhost:8080/api/news/trending`

---

*Created for Nexity60 PWA Project*
