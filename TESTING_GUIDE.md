# Nexity60 PWA - Testing Guide

## Quick Start Testing

### 1. Test Backend

```bash
cd pwa-backend
mvn clean install
mvn spring-boot:run
```

Expected output:
```
Started Nexity60Application in X.XXX seconds
```

### 2. Test Backend API

Open browser and test these endpoints:

**Health Check:**
```
http://localhost:8080/api/news/trending
```

Should return JSON array of news articles.

**Other Endpoints:**
```
http://localhost:8080/api/news/technology
http://localhost:8080/api/news/business
http://localhost:8080/api/news/sports
```

### 3. Test Frontend

```bash
cd pwa-frontend
npm install
npm start
```

Frontend should open at `http://localhost:3000`

## Feature Testing Checklist

### News Feed Features
- [ ] App loads without errors
- [ ] News articles display correctly
- [ ] Category selector works
- [ ] Clicking category loads new articles
- [ ] Refresh button reloads articles
- [ ] Loading indicator shows while fetching
- [ ] Error message displays if fetch fails

### Save/Unsave Features
- [ ] Click save button on an article
- [ ] Article is added to saved list
- [ ] Navigate to Saved Articles page
- [ ] Saved article appears in list
- [ ] Click unsave button
- [ ] Article is removed from list
- [ ] Refresh saved articles page
- [ ] Articles persist after refresh

### Theme Toggle
- [ ] Click "Dark Mode" button
- [ ] App switches to dark theme
- [ ] All text is readable
- [ ] Colors look good
- [ ] Click "Light Mode" button
- [ ] App switches to light theme
- [ ] Theme preference persists after page reload

### Navigation
- [ ] Click "Live News" link
- [ ] Navigate to news feed page
- [ ] Click "Saved Articles" link
- [ ] Navigate to saved articles page
- [ ] Browser back button works
- [ ] URL updates correctly

### Mobile Responsiveness
- [ ] Open app on mobile device or resize browser
- [ ] Layout adapts to smaller screen
- [ ] Text is readable
- [ ] Buttons are tappable
- [ ] No horizontal scrolling
- [ ] Cards stack vertically

### PWA Features
- [ ] Install prompt appears (or manual install works)
- [ ] App installs successfully
- [ ] Icon appears on home screen
- [ ] App opens in standalone mode (no browser UI)
- [ ] Service worker registers
- [ ] App works offline (after first visit)

## API Testing with curl

### Get Trending News
```bash
curl http://localhost:8080/api/news/trending
```

### Get Technology News
```bash
curl http://localhost:8080/api/news/technology
```

### Save Article (replace {id} with actual article ID)
```bash
curl -X POST http://localhost:8080/api/news/save/1
```

### Get Saved Articles
```bash
curl http://localhost:8080/api/news/saved
```

### Unsave Article (replace {id} with actual article ID)
```bash
curl -X DELETE http://localhost:8080/api/news/unsave/1
```

## Browser Testing

### Desktop Browsers
- [ ] Chrome (latest)
- [ ] Firefox (latest)
- [ ] Safari (latest)
- [ ] Edge (latest)

### Mobile Browsers
- [ ] Chrome Mobile (Android)
- [ ] Safari Mobile (iOS)
- [ ] Samsung Internet (Android)

## Performance Testing

### Lighthouse Audit
1. Open Chrome DevTools (F12)
2. Go to "Lighthouse" tab
3. Select "Progressive Web App"
4. Click "Generate report"
5. Check scores:
   - Performance: 90+
   - Accessibility: 90+
   - Best Practices: 90+
   - SEO: 90+
   - PWA: 100

### Network Testing
1. Open DevTools → Network tab
2. Throttle to "Slow 3G"
3. Refresh page
4. Check loading times
5. Test offline mode (Offline checkbox)

## Database Testing

### H2 Console (Development)
1. Navigate to: `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:mem:nexity60db`
3. Username: `sa`
4. Password: (leave empty)
5. Click "Connect"
6. Run SQL queries:
   ```sql
   SELECT * FROM NEWS_ARTICLE;
   SELECT * FROM NEWS_ARTICLE WHERE IS_SAVED = TRUE;
   ```

## Error Testing

### Backend Errors
- [ ] Stop backend while frontend is running
- [ ] Frontend shows error message
- [ ] Start backend again
- [ ] Frontend recovers on next fetch

### Network Errors
- [ ] Disconnect internet
- [ ] Try to fetch news
- [ ] Error message appears
- [ ] Reconnect internet
- [ ] App works again

### Invalid Data
- [ ] Test with invalid category
- [ ] Test with non-existent article ID
- [ ] Check error handling

## Load Testing

### Simple Load Test
```bash
# Install Apache Bench (comes with Apache)
# Test 100 requests, 10 concurrent
ab -n 100 -c 10 http://localhost:8080/api/news/trending
```

Expected: All requests successful (200 OK)

## Security Testing

### CORS Testing
```javascript
// Open browser console on different origin
fetch('http://localhost:8080/api/news/trending')
  .then(res => res.json())
  .then(data => console.log(data))
```

Should work from `http://localhost:3000` but fail from other origins.

### XSS Testing
- [ ] Try injecting `<script>alert('XSS')</script>` in saved articles
- [ ] Verify content is sanitized
- [ ] No scripts execute

## Accessibility Testing

### Keyboard Navigation
- [ ] Tab through all interactive elements
- [ ] Enter/Space activates buttons
- [ ] Arrow keys work in selects
- [ ] Escape closes modals (if any)

### Screen Reader Testing
- [ ] Turn on screen reader (NVDA, JAWS, VoiceOver)
- [ ] Navigate through app
- [ ] All content is announced
- [ ] Buttons have descriptive labels

### Color Contrast
- [ ] Use DevTools Contrast Checker
- [ ] All text meets WCAG AA standards
- [ ] Both light and dark modes pass

## Common Issues & Solutions

### Backend won't start
**Error:** Port 8080 already in use
**Solution:** 
```bash
# Find process using port 8080
netstat -ano | findstr :8080
# Kill process
taskkill /PID <pid> /F
```

### Frontend won't connect to backend
**Error:** CORS error or network error
**Solution:**
- Verify backend is running: `http://localhost:8080/api/news/trending`
- Check `REACT_APP_API_URL` in `.env.development`
- Clear browser cache and hard reload

### Articles not saving
**Error:** Save button doesn't work
**Solution:**
- Check backend logs for errors
- Verify H2 database connection
- Check article has valid ID

### PWA won't install
**Error:** No install prompt
**Solution:**
- Must be on HTTPS (or localhost)
- Check manifest.json is valid
- Clear browser cache
- Try manual install (Chrome: ⋮ → Install app)

### Service Worker errors
**Error:** Service worker registration failed
**Solution:**
- Check browser console for errors
- Verify service-worker.js is accessible
- Try unregistering: DevTools → Application → Service Workers → Unregister

## Testing Checklist Summary

Before deployment:
- [ ] All backend endpoints work
- [ ] All frontend pages load
- [ ] Save/unsave functionality works
- [ ] Theme toggle works
- [ ] Mobile responsive
- [ ] PWA installs correctly
- [ ] No console errors
- [ ] Lighthouse score 90+
- [ ] Works in all major browsers
- [ ] Accessible with keyboard
- [ ] Error handling works

## Next Steps After Testing

1. Fix any bugs found
2. Run tests again
3. Update documentation
4. Deploy to staging
5. Test staging environment
6. Deploy to production
7. Monitor production logs

---

Found an issue? Check the [main README](./PWA_README.md) or [Deployment Guide](./DEPLOYMENT_GUIDE.md)
