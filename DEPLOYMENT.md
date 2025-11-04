# Nexity60 Deployment Guide for Render

## Prerequisites
- GitHub account
- Render account (free tier available at https://render.com)
- Git repository pushed to GitHub

## Step 1: Push Your Code to GitHub

```bash
# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit
git commit -m "Prepare for Render deployment"

# Add GitHub remote (replace with your repo)
git remote add origin https://github.com/VickyKumarOfficial/Nexity60.git

# Push to GitHub
git push -u origin master
```

## Step 2: Deploy Backend to Render

### 2.1 Create New Web Service
1. Go to https://dashboard.render.com
2. Click **"New +"** → **"Web Service"**
3. Connect your GitHub repository
4. Select the **Nexity60** repository

### 2.2 Configure Backend Service
Fill in the following details:

- **Name**: `nexity60-backend`
- **Region**: Choose closest to you
- **Branch**: `master`
- **Root Directory**: `pwa-backend`
- **Environment**: `Java`
- **Build Command**: `mvn clean package -DskipTests`
- **Start Command**: `java -jar target/nexity60-backend-1.0.0.jar`

### 2.3 Environment Variables
Add these in the "Environment" tab:
```
JAVA_VERSION=21
MAVEN_VERSION=3.9.11
```

### 2.4 Instance Type
- Select **Free** tier for testing
- Click **"Create Web Service"**

### 2.5 Wait for Deployment
- Render will build and deploy your backend
- Copy the backend URL (e.g., `https://nexity60-backend.onrender.com`)
- **Important**: Free tier services spin down after inactivity, first request may take 50+ seconds

## Step 3: Deploy Frontend to Render

### 3.1 Create Static Site
1. Go to https://dashboard.render.com
2. Click **"New +"** → **"Static Site"**
3. Select the **Nexity60** repository

### 3.2 Configure Frontend
Fill in the following details:

- **Name**: `nexity60-frontend`
- **Region**: Choose closest to you
- **Branch**: `master`
- **Root Directory**: `pwa-frontend`
- **Build Command**: `npm install && npm run build`
- **Publish Directory**: `build`

### 3.3 Environment Variables
Add this in the "Environment" tab:
```
REACT_APP_API_URL=https://nexity60-backend.onrender.com/api
```
**Replace the URL with your actual backend URL from Step 2.5**

### 3.4 Deploy
- Click **"Create Static Site"**
- Wait for deployment to complete
- Your frontend URL will be something like `https://nexity60-frontend.onrender.com`

## Step 4: Update CORS (if needed)

If you get CORS errors, update the backend `NewsController.java`:

```java
@CrossOrigin(origins = "https://nexity60-frontend.onrender.com")
```

Then push changes and Render will auto-redeploy.

## Step 5: Test Your Deployment

1. Visit your frontend URL: `https://nexity60-frontend.onrender.com`
2. Wait for backend to wake up (first load ~50 seconds on free tier)
3. Test all features:
   - Browse news by category
   - Save/unsave articles
   - Check saved articles page
   - Test offline PWA functionality

## Important Notes for Free Tier

### Backend (Web Service)
- ✅ 750 hours/month free
- ⚠️ Spins down after 15 minutes of inactivity
- ⚠️ Cold start takes 50+ seconds
- 💡 First API call will be slow, subsequent calls are fast

### Frontend (Static Site)
- ✅ 100 GB bandwidth/month free
- ✅ No spin down issues
- ✅ Fast global CDN
- ✅ Automatic SSL certificate

### Database
- Currently using H2 in-memory database
- ⚠️ Data is lost when backend restarts
- 💡 For persistent data, upgrade to PostgreSQL on Render

## Upgrade to Persistent Database (Optional)

### Add PostgreSQL on Render
1. Create new PostgreSQL database on Render
2. Copy the database URL
3. Update `pom.xml` (PostgreSQL already included)
4. Update `application.properties`:

```properties
# Use PostgreSQL in production
spring.datasource.url=${DATABASE_URL}
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

5. Add `DATABASE_URL` environment variable in Render dashboard

## Troubleshooting

### Backend not responding
- Check logs in Render dashboard
- Verify Java version is 21
- First request takes time (cold start)

### Frontend can't reach backend
- Check REACT_APP_API_URL is correct
- Verify CORS settings
- Check browser console for errors

### Build failures
- Check build logs in Render
- Verify Node.js version (16+)
- Ensure all dependencies are in package.json

## Custom Domain (Optional)

Both backend and frontend support custom domains on Render:
1. Go to service settings
2. Click "Custom Domains"
3. Add your domain
4. Update DNS records as instructed

## Monitoring

Free tier includes:
- 📊 Basic metrics
- 📝 Log streaming
- 🔔 Email notifications for failed deploys

## Cost Estimate

**Free Tier**: $0/month
- 750 hours backend
- 100 GB bandwidth frontend
- Shared resources

**Paid Tier**: $7/month per service
- Always on
- More resources
- Priority support

---

## Quick Commands Reference

```bash
# Check build locally
cd pwa-backend
mvn clean package -DskipTests

cd ../pwa-frontend
npm install
npm run build

# Force redeploy
git commit --allow-empty -m "Trigger redeploy"
git push

# View logs
# Use Render dashboard → Service → Logs
```

## Support
- Render Docs: https://render.com/docs
- Community: https://community.render.com
- Status: https://status.render.com

---

**Your app is now ready for deployment! 🚀**
