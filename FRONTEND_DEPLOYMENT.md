# 🔧 Update Backend CORS Origins

Your frontend is now configured to use the production backend!

## ✅ Frontend Updated

**Production API URL:** `https://parkease-srqk.onrender.com/api`

### Files Updated:
1. ✅ `src/services/api.js` - Now uses production URL
2. ✅ `.env.local` - Created for local development
3. ✅ `.env.production` - Created for production builds

---

## ⚠️ IMPORTANT: Update Backend CORS

You need to update the backend CORS origins to allow your frontend domain.

### Step 1: Find Your Frontend URL

After deploying to Vercel, you'll get a URL like:
```
https://parkease-frontend.vercel.app
```

### Step 2: Update CORS_ORIGINS in Render

1. **Go to Render Dashboard:** https://dashboard.render.com
2. **Select your service:** `parkease-backend`
3. **Go to Environment tab**
4. **Find `CORS_ORIGINS`**
5. **Update to:**
   ```
   https://YOUR-FRONTEND-URL.vercel.app,http://localhost:5173
   ```

**Example:**
```
CORS_ORIGINS=https://parkease-frontend.vercel.app,http://localhost:5173
```

### Step 3: Save and Redeploy

Render will automatically redeploy with the new CORS settings.

---

## 🚀 Deploy Frontend to Vercel

### Quick Deploy:

1. **Push to GitHub:**
   ```bash
   cd c:\Users\kunal\parkease
   git add .
   git commit -m "Update API URL to production backend"
   git push origin main
   ```

2. **Go to Vercel:** https://vercel.com
3. **Import your repository**
4. **Configure:**
   - Framework Preset: Vite
   - Root Directory: `parkease-frontend`
   - Build Command: `npm run build`
   - Output Directory: `dist`
   - Environment Variables:
     ```
     VITE_API_URL=https://parkease-srqk.onrender.com/api
     ```

5. **Deploy!**

---

## 🧪 Test Locally First

Before deploying, test locally:

```bash
cd c:\Users\kunal\parkease\parkease-frontend
npm run dev
```

**Your frontend will:**
- ✅ Use `http://localhost:8080/api` (from `.env.local`)
- ✅ Fall back to production URL if local backend is down

**To test with production backend locally:**
```bash
# Remove .env.local temporarily
# Or set VITE_API_URL in your terminal
$env:VITE_API_URL="https://parkease-srqk.onrender.com/api"
npm run dev
```

---

## 📝 Current Configuration

### Frontend (Local Dev):
```
API URL: http://localhost:8080/api (from .env.local)
Fallback: https://parkease-srqk.onrender.com/api
```

### Frontend (Production):
```
API URL: https://parkease-srqk.onrender.com/api (from .env.production)
```

### Backend CORS (Current):
```
CORS_ORIGINS=http://localhost:5173,https://parkease-frontend.vercel.app
```

**⚠️ Update this after deploying frontend!**

---

## ✅ Summary

1. ✅ Frontend API URL updated to production
2. ✅ Environment files created for dev/prod
3. ⚠️ **TODO:** Update backend CORS after frontend deployment
4. ⚠️ **TODO:** Deploy frontend to Vercel

**Next:** Deploy your frontend to Vercel and update the CORS origins!
