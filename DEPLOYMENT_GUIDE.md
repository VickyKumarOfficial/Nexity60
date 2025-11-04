# Nexity60 Deployment Guide

## Deployment Options

### Option 1: Render (Recommended)

#### Backend Deployment
1. Push your code to GitHub
2. Go to [Render Dashboard](https://dashboard.render.com/)
3. Click "New +" → "Web Service"
4. Connect your GitHub repository
5. Configure:
   - **Name**: `nexity60-backend`
   - **Root Directory**: `pwa-backend`
   - **Environment**: Java
   - **Build Command**: `mvn clean install`
   - **Start Command**: `java -jar target/nexity60-0.0.1-SNAPSHOT.jar`
   - **Plan**: Free
6. Add Environment Variables:
   ```
   SPRING_PROFILES_ACTIVE=prod
   JAVA_TOOL_OPTIONS=-Xmx512m
   ```
7. Click "Create Web Service"
8. Note your backend URL: `https://nexity60-backend.onrender.com`

#### Frontend Deployment
1. Go to [Render Dashboard](https://dashboard.render.com/)
2. Click "New +" → "Static Site"
3. Connect your GitHub repository
4. Configure:
   - **Name**: `nexity60-frontend`
   - **Root Directory**: `pwa-frontend`
   - **Build Command**: `npm install && npm run build`
   - **Publish Directory**: `build`
5. Add Environment Variable:
   ```
   REACT_APP_API_URL=https://nexity60-backend.onrender.com/api
   ```
6. Click "Create Static Site"

### Option 2: Koyeb

#### Backend Deployment
1. Go to [Koyeb Dashboard](https://app.koyeb.com/)
2. Click "Create App"
3. Select "GitHub" as source
4. Configure:
   - **Repository**: Your GitHub repo
   - **Branch**: main
   - **Build Pack**: Java
   - **Build Command**: `cd pwa-backend && mvn clean package`
   - **Run Command**: `java -jar pwa-backend/target/nexity60-0.0.1-SNAPSHOT.jar`
   - **Port**: 8080
5. Click "Deploy"

### Option 3: Railway

#### Backend Deployment
1. Go to [Railway](https://railway.app/)
2. Click "New Project" → "Deploy from GitHub repo"
3. Select your repository
4. Railway will auto-detect Spring Boot
5. Add environment variables if needed
6. Deploy automatically

### Option 4: Heroku

#### Backend Deployment
1. Install Heroku CLI
2. Create `Procfile` in `pwa-backend/`:
   ```
   web: java -jar target/nexity60-0.0.1-SNAPSHOT.jar
   ```
3. Create `system.properties`:
   ```
   java.runtime.version=17
   ```
4. Deploy:
   ```bash
   heroku login
   heroku create nexity60-backend
   git subtree push --prefix pwa-backend heroku main
   ```

#### Frontend Deployment (Netlify)
1. Go to [Netlify](https://app.netlify.com/)
2. Click "Add new site" → "Import an existing project"
3. Connect GitHub
4. Configure:
   - **Base directory**: `pwa-frontend`
   - **Build command**: `npm run build`
   - **Publish directory**: `pwa-frontend/build`
5. Add environment variable:
   ```
   REACT_APP_API_URL=<your-backend-url>/api
   ```
6. Deploy

## Production Configuration

### Backend Production Settings

Create `application-prod.properties` in `pwa-backend/src/main/resources/`:

```properties
# PostgreSQL Database (if using)
spring.datasource.url=${DATABASE_URL}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Production settings
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Server configuration
server.port=${PORT:8080}
server.compression.enabled=true

# CORS (update with your frontend URL)
cors.allowed.origins=${FRONTEND_URL:http://localhost:3000}
```

### Adding PostgreSQL to Backend

1. Update `pom.xml`:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

2. Add environment variable on hosting platform:
```
DATABASE_URL=postgresql://user:password@host:5432/dbname
```

### Frontend Production Build

Update `package.json` scripts:
```json
{
  "scripts": {
    "start": "react-scripts start",
    "build": "react-scripts build",
    "test": "react-scripts test",
    "eject": "react-scripts eject"
  }
}
```

## Custom Domain Setup

### Render
1. Go to your service settings
2. Click "Custom Domain"
3. Add your domain (e.g., `nexity60.com`)
4. Update DNS records as instructed
5. SSL certificate will be auto-generated

### Netlify
1. Go to "Domain settings"
2. Add custom domain
3. Update DNS records
4. SSL certificate auto-provisioned

## Performance Optimization

### Backend
- Use connection pooling
- Enable caching for RSS feeds
- Add rate limiting
- Compress responses

### Frontend
- Code splitting
- Lazy loading routes
- Image optimization
- Service worker caching

## Monitoring

### Backend Monitoring
- Add Spring Boot Actuator:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

- Enable endpoints in `application.properties`:
```properties
management.endpoints.web.exposure.include=health,info,metrics
```

### Frontend Monitoring
- Add Google Analytics
- Use Sentry for error tracking
- Monitor Core Web Vitals

## Troubleshooting

### Common Issues

1. **Backend won't start on hosting platform**
   - Check Java version matches (Java 17)
   - Verify build command completes successfully
   - Check environment variables are set

2. **Frontend can't connect to backend**
   - Verify `REACT_APP_API_URL` is correct
   - Check CORS settings in backend
   - Ensure backend is running

3. **PWA not installing**
   - Verify manifest.json is accessible
   - Check service worker registration
   - Must be served over HTTPS (except localhost)

4. **Database errors in production**
   - Check DATABASE_URL format
   - Verify PostgreSQL credentials
   - Check connection limits

## Cost Estimates

### Free Tier Options
- **Render**: Free (with limitations - sleeps after inactivity)
- **Koyeb**: Free tier available
- **Railway**: $5 credit monthly
- **Netlify**: Free for frontend
- **Vercel**: Free for frontend

### Paid Options
- **Render**: $7/month per service
- **Heroku**: $7/month per dyno
- **Railway**: Pay as you go (~$5-10/month)

## Backup & Recovery

### Database Backup
```bash
# PostgreSQL backup
pg_dump -h hostname -U username database_name > backup.sql

# Restore
psql -h hostname -U username database_name < backup.sql
```

### Code Backup
- Use Git tags for releases
- Maintain separate branches for staging/production
- Regular GitHub backups

## Security Checklist

- [ ] HTTPS enabled
- [ ] Environment variables secured
- [ ] API rate limiting enabled
- [ ] CORS properly configured
- [ ] Database credentials encrypted
- [ ] Regular dependency updates
- [ ] Security headers configured
- [ ] Input validation on all endpoints

## Next Steps After Deployment

1. Test all features in production
2. Monitor error logs
3. Set up analytics
4. Create user documentation
5. Plan feature updates
6. Set up CI/CD pipeline

---

Need help? Check the [main README](./PWA_README.md) or open an issue on GitHub.
