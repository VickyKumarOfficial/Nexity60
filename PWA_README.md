# Nexity60 - PWA News Application

A Progressive Web App (PWA) for real-time news from BBC RSS feeds with save functionality and dark mode.

## Features

- 📰 **Live News Feed** - Real-time news from BBC RSS feeds
- 💾 **Save Articles** - Save your favorite articles for later
- 🌙 **Dark Mode** - Toggle between light and dark themes
- 📱 **PWA Support** - Install as a mobile app
- 📚 **Category Filter** - Browse news by category (Technology, Business, Sports, World, Health, Science, Entertainment)
- 📊 **Expanded Content** - Rich article summaries with 1,200-2,000 characters

## Tech Stack

### Backend
- **Spring Boot 3.2.0** - Java REST API
- **H2 Database** - In-memory database (development)
- **PostgreSQL** - Production database (optional)
- **Maven** - Build tool

### Frontend
- **React 18.2.0** - UI framework
- **React Router** - Navigation
- **Axios** - HTTP client
- **Service Worker** - PWA offline support

## Prerequisites

- **Java 17** or higher
- **Node.js 16** or higher
- **Maven 3.6+**

## Quick Start

### 1. Setup Backend

```bash
cd pwa-backend

# Build the project
mvn clean install

# Run the backend
mvn spring-boot:run
```

Backend will start at `http://localhost:8080`

### 2. Setup Frontend

```bash
cd pwa-frontend

# Install dependencies
npm install

# Start development server
npm start
```

Frontend will start at `http://localhost:3000`

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/news/{category}` | Fetch news by category |
| `GET` | `/api/news/saved` | Get all saved articles |
| `POST` | `/api/news/save/{id}` | Save an article |
| `DELETE` | `/api/news/unsave/{id}` | Remove saved article |

### Categories
- `trending` - Top stories
- `technology` - Tech news
- `business` - Business news
- `sports` - Sports updates
- `world` - World news
- `health` - Health news
- `science` - Science news
- `entertainment` - Entertainment news

## Environment Variables

### Backend (`application.properties`)
```properties
spring.datasource.url=jdbc:h2:mem:nexity60db
spring.jpa.hibernate.ddl-auto=update
```

### Frontend (`.env`)
```
REACT_APP_API_URL=http://localhost:8080/api
```

## Building for Production

### Backend
```bash
cd pwa-backend
mvn clean package
java -jar target/nexity60-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
cd pwa-frontend
npm run build
```

The build folder will contain the production-ready static files.

## Deployment

### Backend Deployment (Render/Koyeb)

1. Create a new Web Service
2. Connect your GitHub repository
3. Set build command: `mvn clean install`
4. Set start command: `java -jar target/nexity60-0.0.1-SNAPSHOT.jar`
5. Add environment variables for PostgreSQL if needed

### Frontend Deployment (Netlify/Vercel)

1. Connect your GitHub repository
2. Set build command: `npm run build`
3. Set publish directory: `build`
4. Add environment variable: `REACT_APP_API_URL=<your-backend-url>`

## PWA Installation

Once deployed, users can install Nexity60 as a mobile app:

1. Open the app in a mobile browser
2. Look for "Add to Home Screen" prompt
3. Click "Install" or "Add"
4. App will appear on your home screen like a native app

## Development

### Backend Structure
```
pwa-backend/
├── src/main/java/com/nexity60/
│   ├── Nexity60Application.java      # Main Spring Boot application
│   ├── model/
│   │   └── NewsArticle.java          # JPA entity
│   ├── repository/
│   │   └── NewsArticleRepository.java # Data repository
│   ├── service/
│   │   └── NewsService.java          # Business logic & RSS parsing
│   └── controller/
│       └── NewsController.java       # REST API endpoints
└── pom.xml                           # Maven configuration
```

### Frontend Structure
```
pwa-frontend/
├── public/
│   ├── index.html                    # HTML template
│   ├── manifest.json                 # PWA manifest
│   └── service-worker.js             # Service worker for offline
├── src/
│   ├── App.js                        # Main app component
│   ├── App.css                       # App styles
│   ├── index.js                      # React root
│   ├── pages/
│   │   ├── NewsPage.js               # News feed page
│   │   └── SavedArticlesPage.js      # Saved articles page
│   └── components/
│       └── NewsCard.js               # Reusable news card
└── package.json                      # NPM dependencies
```

## Features in Detail

### Article Expansion
Articles are automatically expanded with rich content analysis:
- **Technology**: Industry impact, innovations, market implications
- **Business**: Market analysis, economic impact, stakeholder effects
- **Sports**: Game analysis, player performance, historical context
- **World**: Geopolitical context, regional impact, expert perspectives
- **Health**: Medical implications, public health context, prevention
- **Science**: Research methodology, scientific significance, applications
- **Entertainment**: Cultural impact, industry trends, critical reception

### Dark Mode
Toggle between light and dark themes with persistent storage using localStorage.

### Offline Support
Service worker caches resources for offline access and faster loading.

## Troubleshooting

### Backend Issues
- **Port 8080 already in use**: Change port in `application.properties` with `server.port=8081`
- **Database errors**: Check H2 console at `http://localhost:8080/h2-console`

### Frontend Issues
- **API connection errors**: Verify backend is running at `http://localhost:8080`
- **CORS errors**: Backend has CORS enabled for `http://localhost:3000`

## License

MIT License - Feel free to use this project for personal or commercial purposes.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues or questions, please open an issue on GitHub.

---

Built with ❤️ by Nexity60 Team
