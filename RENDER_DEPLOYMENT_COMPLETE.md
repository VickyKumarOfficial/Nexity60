# 🚀 Complete Render Deployment Guide - Nexity60 PWA

## ✅ Pre-Deployment Checklist

### Repository Status
- [x] Code pushed to GitHub: https://github.com/VickyKumarOfficial/Nexity60
- [x] `.gitignore` properly configured
- [x] All dependencies listed in `package.json` and `pom.xml`
- [x] Backend configured for production (PORT variable, CORS)

### Project Analysis Results

#### Backend (Spring Boot)
- **Technology**: Spring Boot 3.2.0, Java 21
- **Build Tool**: Maven 3.9.11
- **Database**: H2 (in-memory) - works for free tier
- **Port**: Dynamic `${PORT:8080}` ✅
- **CORS**: Enabled for all origins ✅
- **Build Command**: `mvn clean package -DskipTests`
- **JAR Location**: `target/nexity60-backend-1.0.0.jar`
- **API Endpoints**:
  - GET `/api/news/{category}`
  - GET `/api/news/saved`
  - POST `/api/news/save/{id}`
  - DELETE `/api/news/unsave/{id}`

#### Frontend (React PWA)
- **Technology**: React 18.2.0
- **Build Tool**: npm
- **API Configuration**: Environment variable `REACT_APP_API_URL`
- **Build Output**: `build/` directory
- **Service Worker**: PWA enabled ✅
- **Features**:
  - News browsing by category
  - Save/unsave articles
  - Horizontal card layout with images
  - Offline support

---

## 📋 Step-by-Step Deployment

### STEP 1: Deploy Backend (Spring Boot API)

1. **Go to Render Dashboard**
   - Visit: https://dashboard.render.com
   - Sign in with your GitHub account

2. **Create New Web Service**
   - Click **"New +"** → **"Web Service"**
   - Click **"Connect a repository"**
   - Select: **VickyKumarOfficial/Nexity60**
   - Grant necessary permissions if prompted

3. **Configure Backend Service**

   Fill in these **exact** settings:

   | Field | Value |
   |-------|-------|
   | **Name** | `nexity60-backend` |
   | **Region** | Choose closest (e.g., Singapore, Oregon) |
   | **Branch** | `master` |
   | **Root Directory** | `pwa-backend` |
   | **Runtime** | `Java` |
   | **Build Command** | `mvn clean package -DskipTests` |
   | **Start Command** | `java -jar target/nexity60-backend-1.0.0.jar` |
   | **Instance Type** | **Free** |

4. **Add Environment Variables**

   Click **"Advanced"** → **"Add Environment Variable"**:

   ```
   JAVA_VERSION = 21
   MAVEN_VERSION = 3.9.11
   ```

5. **Deploy Backend**
   - Click **"Create Web Service"**
   - Wait 5-10 minutes for build to complete
   - Watch the logs for any errors

6. **Get Backend URL**
   - Once deployed, copy the URL (e.g., `https://nexity60-backend.onrender.com`)
   - **Save this URL** - you'll need it for frontend!

7. **Test Backend**
   - Open: `https://nexity60-backend.onrender.com/api/news/trending`
   - Should return JSON array of news articles
   - ⚠️ **First request may take 50+ seconds** (cold start)

---

### STEP 2: Deploy Frontend (React Static Site)

1. **Create New Static Site**
   - Go back to Render Dashboard
   - Click **"New +"** → **"Static Site"**
   - Select **VickyKumarOfficial/Nexity60** repository

2. **Configure Frontend Service**

   | Field | Value |
   |-------|-------|
   | **Name** | `nexity60-frontend` |
   | **Region** | Same as backend |
   | **Branch** | `master` |
   | **Root Directory** | `pwa-frontend` |
   | **Build Command** | `npm install && npm run build` |
   | **Publish Directory** | `build` |

3. **Add Environment Variable**

   Click **"Advanced"** → **"Add Environment Variable"**:

   ```
   REACT_APP_API_URL = https://nexity60-backend.onrender.com/api
   ```

   **⚠️ IMPORTANT**: Replace with your **actual backend URL** from Step 1.6

4. **Deploy Frontend**
   - Click **"Create Static Site"**
   - Wait 3-5 minutes for build
   - Frontend URL will be like: `https://nexity60-frontend.onrender.com`

5. **Test Frontend**
   - Open your frontend URL
   - Wait for backend to wake up (first load is slow)
   - Try:
     - ✅ Browse different categories
     - ✅ Save an article
     - ✅ View saved articles
     - ✅ Images should load in cards

---

## 🔧 Post-Deployment Configuration

### Update Backend CORS (If Needed)

If you see CORS errors in browser console:

1. Open `pwa-backend/src/main/java/com/nexity60/controller/NewsController.java`

2. Update the `@CrossOrigin` annotation:
   ```java
   @CrossOrigin(origins = "https://nexity60-frontend.onrender.com")
   ```

3. Commit and push:
   ```bash
   git add .
   git commit -m "Update CORS for production"
   git push origin master
   ```

4. Render will auto-redeploy backend

### Update Frontend API URL (If Backend URL Changes)

1. Go to Render Dashboard → nexity60-frontend
2. Click **"Environment"**
3. Update `REACT_APP_API_URL` value
4. Click **"Save Changes"**
5. Manual redeploy required

---

## ⚠️ Important Free Tier Limitations

### Backend Behavior
- ✅ **750 hours/month** free
- ⚠️ **Spins down** after 15 minutes of inactivity
- ⚠️ **Cold start**: First request takes 50-90 seconds
- ⚠️ **Data loss**: H2 in-memory database clears on restart
- 💡 Saved articles will be lost when service restarts

### Frontend Behavior
- ✅ **100 GB bandwidth/month** free
- ✅ **No spin down** - always fast
- ✅ **Global CDN** - cached worldwide
- ✅ **Automatic SSL** certificate

### How to Minimize Cold Starts
1. Use a free uptime monitor (e.g., UptimeRobot) to ping every 14 minutes
2. Upgrade to paid tier ($7/month) for always-on service
3. First user each session will experience slow load

---

## 🗄️ Upgrade to Persistent Database (Optional)

For permanent data storage, add PostgreSQL:

### 1. Create PostgreSQL Database

1. Render Dashboard → **"New +"** → **"PostgreSQL"**
2. Name: `nexity60-db`
3. Plan: **Free** (limited to 90 days, then $7/month)
4. Click **"Create Database"**
5. Copy the **"External Database URL"**

### 2. Update Backend Configuration

Update `pwa-backend/src/main/resources/application.properties`:

```properties
# Database Configuration - PostgreSQL
spring.datasource.url=${DATABASE_URL:jdbc:h2:mem:nexity60db}
spring.datasource.username=${DB_USERNAME:sa}
spring.datasource.password=${DB_PASSWORD:}
spring.jpa.database-platform=${DB_DIALECT:org.hibernate.dialect.H2Dialect}
spring.jpa.hibernate.ddl-auto=update
```

### 3. Add Environment Variables to Backend

In Render Dashboard → nexity60-backend → Environment:

```
DATABASE_URL = postgres://user:password@host:5432/dbname
DB_USERNAME = user
DB_PASSWORD = password
DB_DIALECT = org.hibernate.dialect.PostgreSQLDialect
```

Use values from your PostgreSQL connection string.

### 4. Redeploy Backend

Changes will auto-deploy. Now your data persists!

---

## 🐛 Troubleshooting Guide

### Backend Issues

**Problem**: Build fails with "Unsupported class file major version"
- **Solution**: Verify `JAVA_VERSION=21` in environment variables

**Problem**: Backend doesn't respond
- **Solution**: 
  - Check logs in Render dashboard
  - Wait 60 seconds for cold start
  - Verify START command matches JAR filename

**Problem**: "Port already in use"
- **Solution**: Ensure `server.port=${PORT:8080}` in application.properties

### Frontend Issues

**Problem**: White screen / no content
- **Solution**:
  - Check browser console for errors
  - Verify `REACT_APP_API_URL` is correct
  - Must end with `/api`, no trailing slash

**Problem**: API calls fail with 404
- **Solution**:
  - Verify backend URL is accessible
  - Check CORS settings in backend
  - Ensure backend is not spinning down

**Problem**: Images not loading
- **Solution**:
  - Check browser console for CORS errors
  - Verify RSS feeds are accessible
  - Check network tab for failed requests

### Build Issues

**Problem**: Maven build timeout
- **Solution**: Add `-DskipTests` to build command

**Problem**: npm install fails
- **Solution**:
  - Verify `package.json` is in root directory
  - Check for typos in dependency names
  - Try clearing build cache in Render

---

## 📊 Monitoring & Logs

### View Logs

**Backend Logs**:
1. Render Dashboard → nexity60-backend
2. Click **"Logs"** tab
3. Real-time streaming logs
4. Filter by severity (info, error, warn)

**Frontend Logs**:
1. Render Dashboard → nexity60-frontend
2. Click **"Logs"** tab
3. Build logs only (static site has no runtime logs)

### Check Service Health

**Backend Health**:
- URL: `https://nexity60-backend.onrender.com/api/news/trending`
- Should return JSON with news articles
- Response time: 50-90s (cold start) or <1s (warm)

**Frontend Health**:
- URL: `https://nexity60-frontend.onrender.com`
- Should load news app
- Check browser console for errors

---

## 🔄 Continuous Deployment

### Auto-Deploy on Push

Render automatically deploys when you push to GitHub:

```bash
# Make changes
git add .
git commit -m "Update feature"
git push origin master

# Render detects push and redeploys automatically
```

### Manual Deploy

1. Render Dashboard → Select service
2. Click **"Manual Deploy"** → **"Deploy latest commit"**

### Disable Auto-Deploy

1. Service Settings → **"Auto-Deploy"**
2. Toggle off
3. Use manual deploy only

---

## 💰 Cost Breakdown

### Free Tier (Current Setup)
- **Backend**: Free (750 hrs/month with cold starts)
- **Frontend**: Free (100 GB bandwidth)
- **Total**: **$0/month**

### Production Tier (Recommended)
- **Backend**: $7/month (always on, no cold starts)
- **Frontend**: Free (or $1/month for priority support)
- **PostgreSQL**: $7/month (after 90-day trial)
- **Total**: **$14-15/month**

### Enterprise Tier
- **Backend**: $25/month (more resources)
- **Frontend**: $5/month
- **PostgreSQL**: $20/month
- **Total**: **$50/month**

---

## 🔐 Security Best Practices

### Environment Variables
- ✅ Never commit `.env` files
- ✅ Use Render's environment variable settings
- ✅ Rotate database credentials regularly

### CORS Configuration
- ⚠️ Change from `*` to specific frontend URL in production
- ✅ Update `NewsController.java` CORS annotation

### Database Security
- ✅ Use strong passwords for PostgreSQL
- ✅ Enable SSL for database connections
- ✅ Limit database access to Render's IP range

---

## 📱 Custom Domain (Optional)

### Add Custom Domain

1. **Buy a domain** (e.g., from Namecheap, GoDaddy)

2. **Add to Render**:
   - Service Settings → **"Custom Domains"**
   - Add: `nexity60.com`

3. **Update DNS Records**:
   - Type: `CNAME`
   - Name: `www` or `@`
   - Value: (Render provides this)

4. **SSL Certificate**:
   - Automatic via Let's Encrypt
   - Enabled within 24 hours

5. **Update Environment Variables**:
   - Frontend: Keep using Render URL for API
   - Backend: Update CORS to allow custom domain

---

## ✅ Final Verification Checklist

Before going live, verify:

- [ ] Backend URL returns JSON at `/api/news/trending`
- [ ] Frontend loads without errors
- [ ] Can browse all categories (Trending, Tech, Business, Sports, etc.)
- [ ] Save article works (heart icon turns red)
- [ ] Saved articles page shows saved items
- [ ] Unsave works (heart icon turns white)
- [ ] Images load in horizontal cards
- [ ] PWA installs on mobile (Add to Home Screen)
- [ ] Offline mode works (service worker)
- [ ] No console errors in browser DevTools
- [ ] Cold start time is acceptable (<90s)

---

## 🎉 Deployment Complete!

Your Nexity60 PWA is now live on Render!

**Your URLs**:
- Backend API: `https://nexity60-backend.onrender.com`
- Frontend App: `https://nexity60-frontend.onrender.com`

**Share your app**:
- On mobile, use "Add to Home Screen" for full PWA experience
- Works offline after first visit
- Updates automatically when you push new code

---

## 📚 Additional Resources

- **Render Docs**: https://render.com/docs
- **Render Community**: https://community.render.com
- **Spring Boot on Render**: https://render.com/docs/deploy-spring-boot
- **React on Render**: https://render.com/docs/deploy-create-react-app
- **Status Page**: https://status.render.com

---

## 🆘 Need Help?

**Common Issues**:
- Check this guide's Troubleshooting section
- Review Render logs for errors
- Test locally first before deploying

**Support Channels**:
- Render Community Forum
- GitHub Issues (for code problems)
- Stack Overflow (tag: render.com)

---

**Last Updated**: November 4, 2025
**Author**: Nexity60 Team
**Version**: 1.0.0
