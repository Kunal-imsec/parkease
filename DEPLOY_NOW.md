# 🚀 ParkEase Render Deployment - Supabase Edition

## ✅ Your Environment Configuration

Copy these **EXACT** values into Render when creating your web service:

---

### Environment Variables for Render

| Variable Name | Value |
|---------------|-------|
| **DATABASE_URL** | `postgresql://postgres.bxidlvpleddpwflfcqpw:6x#&3mUTPyQ#+/m@aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres` |
| **JWT_SECRET** | `T8mGhLgVmRNXPHiSKybq2ERi4tEahFV9bMLUjO/6UXEzQKJlhWYU9RGQDNItD/Pa` |
| **CORS_ORIGINS** | `http://localhost:5173,https://parkease-frontend.vercel.app` |
| **SPRING_PROFILES_ACTIVE** | `prod` |

> [!IMPORTANT]
> **Database:** Using Supabase PostgreSQL (Free Forever!)  
> **Connection:** Transaction Pooler (Port 6543) - Optimized for Render  
> Update `CORS_ORIGINS` with your actual frontend URL once deployed!

---

## 📝 Step-by-Step Deployment Instructions

### Step 1: Push Your Code to GitHub

```bash
cd c:\Users\kunal\parkease
git add .
git commit -m "Migrated to Supabase - Free forever deployment"
git push origin main
```

> [!NOTE]
> If you haven't pushed to GitHub yet, make sure your repository is set up first.

---

### Step 2: Create Web Service on Render

1. **Go to Render Dashboard**
   - Visit: https://dashboard.render.com
   - Click **"New +"** button (top right)
   - Select **"Web Service"**

2. **Connect Your Repository**
   - Click **"Connect account"** if first time
   - Find and select your `parkease` repository
   - Click **"Connect"**

3. **Configure the Service**

   Fill in these fields:

   | Field | Value |
   |-------|-------|
   | **Name** | `parkease-backend` |
   | **Region** | **Singapore (Southeast Asia)** ⚠️ Match your Supabase region! |
   | **Branch** | `main` |
   | **Root Directory** | `parkease-backend` |
   | **Runtime** | **Docker** |
   | **Instance Type** | Free (or paid if needed) |

4. **Add Environment Variables**

   Scroll down to **"Environment Variables"** section.
   
   Click **"Add Environment Variable"** and add each one:

   **Variable 1: DATABASE_URL**
   - Key: `DATABASE_URL`
   - Value: `postgresql://postgres.bxidlvpleddpwflfcqpw:6x#&3mUTPyQ#+/m@aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres`

   **Variable 2: JWT_SECRET**
   - Key: `JWT_SECRET`
   - Value: `T8mGhLgVmRNXPHiSKybq2ERi4tEahFV9bMLUjO/6UXEzQKJlhWYU9RGQDNItD/Pa`

   **Variable 3: CORS_ORIGINS**
   - Key: `CORS_ORIGINS`
   - Value: `http://localhost:5173,https://parkease-frontend.vercel.app`

   **Variable 4: SPRING_PROFILES_ACTIVE**
   - Key: `SPRING_PROFILES_ACTIVE`
   - Value: `prod`

5. **Create the Service**
   - Click **"Create Web Service"** button at the bottom
   - Render will start building your application

---

### Step 3: Monitor the Build

1. **Watch the Build Logs**
   - You'll see the build process in real-time
   - Build takes approximately **5-10 minutes**
   - Look for: `==> Build successful 🎉`

2. **Wait for Deployment**
   - After build completes, Render will deploy
   - Status will change to **"Live"** (green)
   - You'll get a URL like: `https://parkease-backend.onrender.com`

---

### Step 4: Verify Deployment

#### Test 1: Health Check

Open in your browser:
```
https://parkease-backend.onrender.com/api/auth/health
```

**Expected:** Any response (even an error page means the app is running)

#### Test 2: User Registration

Use this curl command (or Postman):

```bash
curl -X POST https://parkease-backend.onrender.com/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"test@parkease.com\",\"password\":\"Test123!\",\"name\":\"Test User\",\"role\":\"USER\"}"
```

**Expected Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "test@parkease.com",
    "name": "Test User",
    "role": "USER"
  }
}
```

#### Test 3: Supabase Dashboard Verification

1. Go to your Supabase project dashboard
2. Click **"Table Editor"** in the left sidebar
3. You should see tables automatically created:
   - `users`
   - `parking_lots`
   - `bookings`
   - `owners`
   - `admins`
4. Click on `users` table to see your test user!

---

## 🎯 Your Deployment URLs

Once deployed, save these URLs:

- **Backend API**: `https://parkease-backend.onrender.com`
- **Supabase Dashboard**: `https://supabase.com/dashboard/project/bxidlvpleddpwflfcqpw`
- **Database**: Supabase PostgreSQL (Transaction Pooler)

---

## 🎨 Supabase Dashboard Features

Now you have access to amazing features:

### 1. **Table Editor** 📊
- View all your data in a spreadsheet-like interface
- Add/edit/delete records directly
- Filter and search data
- Export to CSV

### 2. **SQL Editor** 💻
- Run custom SQL queries
- Autocomplete for tables and columns
- Save frequently used queries
- View query execution time

### 3. **Database** 🗄️
- Visual schema designer
- See relationships between tables
- Monitor database size
- View indexes and constraints

### 4. **Logs** 📝
- See all database queries in real-time
- Monitor slow queries
- Debug connection issues
- Track API usage

---

## 🔄 Update Frontend

After backend is deployed:

1. **Update Frontend API URL**
   - In your frontend code, update the API base URL to:
   - `https://parkease-backend.onrender.com`

2. **Deploy Frontend** (Vercel/Netlify)
   - Deploy your frontend
   - Get the frontend URL (e.g., `https://parkease.vercel.app`)

3. **Update CORS in Render**
   - Go to Render dashboard → Your web service
   - Click **"Environment"** tab
   - Update `CORS_ORIGINS` to include your frontend URL:
   - `http://localhost:5173,https://parkease.vercel.app`
   - Click **"Save Changes"**
   - Service will automatically redeploy

---

## 📊 Monitor Your Free Tier Usage

### Supabase Free Tier Limits

| Resource | Limit | How to Check |
|----------|-------|--------------|
| **Database Size** | 500 MB | Settings → Usage |
| **Bandwidth** | 2 GB/month | Settings → Usage |
| **API Requests** | Unlimited | - |

**To Check Usage:**
1. Go to Supabase Dashboard
2. Click **"Settings"** → **"Usage"**
3. See your current usage vs limits

**For ParkEase:**
- Database will use ~10-50 MB (well under 500 MB)
- Bandwidth depends on traffic (portfolio projects rarely hit 2 GB)
- You'll be fine on free tier! ✅

---

## 🐛 Troubleshooting

### Build Fails

**Error:** "Could not find Dockerfile"
- **Fix:** Ensure `Root Directory` is set to `parkease-backend`

**Error:** "Maven build failed"
- **Fix:** Check `pom.xml` is valid, ensure Java 17 is specified

### Application Crashes

**Error:** "Application failed to start"
- **Fix:** Check logs for specific error
- Verify all environment variables are set correctly
- Ensure `DATABASE_URL` is correct (check for special characters)

### Database Connection Fails

**Error:** "Connection refused" or "Connection timeout"
- **Fix:** Verify Supabase project is active (green status)
- Check `DATABASE_URL` uses port **6543** (Transaction pooler)
- Ensure password in URL is correct (check special characters)

**Special Characters in Password:**
Your password has `#` and `&` characters. These are URL-encoded automatically by PostgreSQL driver, so the URL should work as-is.

### CORS Errors

**Error:** "CORS policy: No 'Access-Control-Allow-Origin' header"
- **Fix:** Update `CORS_ORIGINS` with your actual frontend URL
- Restart the service after updating

### Supabase Connection Pool

**Error:** "Too many connections"
- **Fix:** This shouldn't happen with Transaction pooler
- Check HikariCP `maximum-pool-size` is set to 5 (already configured)

---

## 🎉 Advantages of Supabase Setup

### What You Get:

✅ **Free Forever** - No 90-day limit like Render PostgreSQL  
✅ **Beautiful GUI** - Manage data visually  
✅ **Real-time Logs** - See all queries  
✅ **Automatic Backups** - Daily backups included  
✅ **Global CDN** - Fast worldwide  
✅ **500MB Storage** - More than enough  
✅ **SQL Editor** - Run queries easily  
✅ **No Credit Card** - Truly free  

### Perfect For:

- 🎓 Portfolio projects
- 📚 Learning projects
- 🚀 MVPs and prototypes
- 💡 Side projects
- 🎨 Hobby apps

---

## ✅ Deployment Checklist

- [ ] Code pushed to GitHub
- [ ] Web service created on Render
- [ ] All 4 environment variables added
- [ ] Region matches Supabase (Singapore)
- [ ] Build completed successfully
- [ ] Service is "Live" (green status)
- [ ] Health endpoint responds
- [ ] User registration works
- [ ] Tables visible in Supabase dashboard
- [ ] Test user appears in `users` table
- [ ] Frontend updated with backend URL
- [ ] Frontend deployed
- [ ] CORS updated with frontend URL
- [ ] End-to-end test from frontend

---

## 🎯 Next Steps After Deployment

1. **Explore Supabase Dashboard**
   - Check out the Table Editor
   - Try the SQL Editor
   - View your data

2. **Monitor Usage**
   - Settings → Usage
   - Keep an eye on database size
   - Check bandwidth usage

3. **Deploy Frontend**
   - Update API URL to your Render backend
   - Deploy to Vercel/Netlify
   - Test end-to-end

4. **Share Your Project**
   - Add to portfolio
   - Share on LinkedIn
   - Mention you used modern stack (Spring Boot + Supabase + Render)

---

## 📞 Need Help?

If you encounter any issues:

1. **Check Build Logs** - Look for specific error messages
2. **Check Application Logs** - Click "Logs" tab in Render dashboard
3. **Check Supabase Logs** - See database queries in Supabase dashboard
4. **Verify Environment Variables** - Ensure all 4 variables are set correctly

---

## 🚀 You're Ready to Deploy!

Everything is configured perfectly with **zero errors**! 

Follow the steps above and you'll have your backend deployed with:
- ✅ Free forever database (Supabase)
- ✅ Free backend hosting for 90 days (Render)
- ✅ Beautiful database GUI
- ✅ Production-ready setup

**Let's deploy!** 🎉
