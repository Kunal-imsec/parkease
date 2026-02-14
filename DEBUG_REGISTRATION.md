# 🐛 Registration Debugging Guide

## 🔍 Step 1: Check Backend Health

First, verify your backend is running:

**Open this URL in browser:**
```
https://parkease-srqk.onrender.com/api/auth/health
```

**Expected response:**
```
ParkEase Backend is running
```

**If you get 403 or error:**
- Backend is not running properly
- Check Render logs for errors

---

## 🔍 Step 2: Check Browser Console

1. **Go to your Vercel frontend:** https://parkease-amber.vercel.app
2. **Open DevTools:** Press `F12`
3. **Go to Console tab**
4. **Try to register**
5. **Look for errors**

### Common Errors:

#### Error 1: CORS Error
```
Access to fetch at 'https://parkease-srqk.onrender.com/api/auth/register/user' 
from origin 'https://parkease-amber.vercel.app' has been blocked by CORS policy
```

**Fix:** CORS_ORIGINS is wrong. Should be:
```
http://localhost:5173,https://parkease-amber.vercel.app
```
(No trailing slash!)

#### Error 2: Network Error
```
Network Error
```

**Possible causes:**
- Backend is down
- Wrong API URL in frontend
- Firewall blocking request

#### Error 3: 400 Bad Request
```
400 Bad Request
```

**Check response body for details:**
- Email already exists
- Password too short
- Missing required fields

#### Error 4: 500 Internal Server Error
```
500 Internal Server Error
```

**Backend error - check Render logs**

---

## 🔍 Step 3: Check Network Tab

1. **In DevTools, go to Network tab**
2. **Try to register again**
3. **Click on the failed request**
4. **Check:**
   - Request URL (should be `https://parkease-srqk.onrender.com/api/auth/register/user`)
   - Status code
   - Response body

---

## 🔍 Step 4: Check Render Logs

1. **Go to:** https://dashboard.render.com
2. **Click:** `parkease-backend`
3. **Click:** "Logs" tab
4. **Look for errors when you try to register**

**Common log errors:**

### Database Connection Error
```
HikariPool-1 - Exception during pool initialization
```
**Fix:** Check DATABASE_URL, USERNAME, PASSWORD in Environment

### CORS Error in Logs
```
CORS policy: No 'Access-Control-Allow-Origin' header
```
**Fix:** Update CORS_ORIGINS

### Validation Error
```
MethodArgumentNotValidException
```
**Fix:** Check what fields are required

---

## 🧪 Step 5: Test with cURL

Test the API directly:

```bash
curl -X POST https://parkease-srqk.onrender.com/api/auth/register/user \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "name": "Test User",
    "phone": "+1234567890"
  }'
```

**Expected response:**
```json
{
  "token": "eyJhbGc...",
  "user": {
    "id": 1,
    "email": "test@example.com",
    "name": "Test User",
    "role": "USER"
  }
}
```

**If this works but frontend doesn't:**
- Problem is in frontend
- Check API URL in frontend
- Check CORS settings

**If this fails:**
- Problem is in backend
- Check Render logs
- Check environment variables

---

## 📋 Checklist

Before asking for help, check:

- [ ] Backend health check works
- [ ] Browser console shows specific error
- [ ] Network tab shows request details
- [ ] Render logs checked for errors
- [ ] Environment variables are correct (no quotes, no trailing slashes)
- [ ] CORS_ORIGINS includes your Vercel URL
- [ ] cURL test attempted

---

## 🎯 Most Common Issues

### 1. CORS Not Updated
**Symptom:** CORS error in console  
**Fix:** Update CORS_ORIGINS in Render

### 2. Backend Not Running
**Symptom:** Health check fails  
**Fix:** Check Render logs, fix database connection

### 3. Email Already Exists
**Symptom:** "Invalid credentials" or "Email already exists"  
**Fix:** Try a different email

### 4. Wrong API URL
**Symptom:** 404 Not Found  
**Fix:** Check frontend is using correct URL

---

## 📝 What to Share for Help

If still not working, share:

1. **Error from browser console** (screenshot or text)
2. **Network tab details** (request/response)
3. **Render logs** (last 20 lines)
4. **What you tried** (registration details)

This will help diagnose the exact issue!
