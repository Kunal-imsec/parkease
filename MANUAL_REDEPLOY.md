# 🔄 Manual Redeploy on Render

## Why Render Didn't Auto-Redeploy

Sometimes Render doesn't automatically redeploy when you change environment variables. You need to manually trigger it.

---

## 🚀 How to Manually Redeploy

### Method 1: Manual Deploy Button (Easiest)

1. **Go to Render Dashboard:** https://dashboard.render.com
2. **Click on** `parkease-backend`
3. **Look for the "Manual Deploy" button** at the top right
4. **Click** "Manual Deploy" → "Deploy latest commit"
5. **Wait** for deployment to complete (2-3 minutes)

---

### Method 2: Via Settings

1. **Go to Render Dashboard:** https://dashboard.render.com
2. **Click on** `parkease-backend`
3. **Click** "Settings" in the left sidebar
4. **Scroll down** to find "Manual Deploy" section
5. **Click** "Deploy latest commit"

---

### Method 3: Trigger via Git Push (Alternative)

If manual deploy doesn't work, make a small change and push:

```bash
cd c:\Users\kunal\parkease\parkease-backend

# Make a small change (add a comment)
echo "# Trigger redeploy" >> README.md

# Commit and push
git add .
git commit -m "chore: Trigger Render redeploy"
git push origin main
```

Render will auto-deploy when it detects the push.

---

## ✅ Verify Deployment

### Check Deployment Status

1. On your service page, you'll see deployment status at the top
2. Wait for it to show **"Live"** (green circle)
3. Check the logs for any errors

### Test CORS is Working

1. **Go to your Vercel frontend**
2. **Open DevTools** (F12) → Console tab
3. **Try to register/login**
4. **Check for CORS errors**

**If working:**
- ✅ No CORS errors in console
- ✅ API calls succeed

**If still failing:**
- Check Render logs for errors
- Verify CORS_ORIGINS value is correct
- Make sure deployment completed successfully

---

## 🎯 What Your CORS_ORIGINS Should Be

Make sure it's set to:
```
https://YOUR-ACTUAL-VERCEL-URL.vercel.app,http://localhost:5173
```

**Example:**
```
https://parkease-frontend-abc123.vercel.app,http://localhost:5173
```

**Important:**
- No spaces between URLs
- Use your actual Vercel URL
- Include `http://localhost:5173` for local dev

---

## 🐛 Still Not Working?

### Check Render Logs

1. Go to your service → **"Logs"** tab
2. Look for errors during startup
3. Search for "CORS" in logs

### Verify Environment Variable

1. Go to **"Environment"** tab
2. Confirm `CORS_ORIGINS` shows your Vercel URL
3. Make sure there are no typos

### Check Backend is Running

Visit: https://parkease-srqk.onrender.com/api/auth/health

Should return: `ParkEase Backend is running`

---

## ✅ After Successful Redeploy

1. ✅ Deployment shows "Live"
2. ✅ Health check works
3. ✅ Test registration on Vercel frontend
4. ✅ No CORS errors in browser console

**Your app should work now!** 🎉
