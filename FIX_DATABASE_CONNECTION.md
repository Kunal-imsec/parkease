# 🔴 Database Connection Failed - Fix Guide

## ❌ The Error

```
HikariPool-1 - Exception during pool initialization.
SQL Error: 0, SQLState: 08001
The connection attempt failed.
```

**This means:** Your backend cannot connect to Supabase database.

---

## 🔍 Root Cause

The database connection is failing. This is likely because:

1. ❌ **Missing environment variables** in Render
2. ❌ **Wrong DATABASE_URL format**
3. ❌ **Missing SPRING_DATASOURCE_USERNAME**
4. ❌ **Missing SPRING_DATASOURCE_PASSWORD**

---

## ✅ Fix: Check Render Environment Variables

### Step 1: Go to Render Environment Tab

1. **Go to:** https://dashboard.render.com
2. **Click:** `parkease-backend`
3. **Click:** "Environment" tab

---

### Step 2: Verify ALL These Variables Exist

You need **ALL 6** of these variables:

```bash
DATABASE_URL=jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require
SPRING_DATASOURCE_USERNAME=postgres.bxidlvpleddpwflfcqpw
SPRING_DATASOURCE_PASSWORD=6x#&3mUTPyQ#+/m
JWT_SECRET=T8mGhLgVmRNXPHiSKybq2ERi4tEahFV9bMLUjO/6UXEzQKJlhWYU9RGQDNItD/Pa
CORS_ORIGINS=https://YOUR-VERCEL-URL.vercel.app,http://localhost:5173
SPRING_PROFILES_ACTIVE=prod
```

---

### Step 3: Add Missing Variables

**If any are missing, add them:**

1. Click **"Add Environment Variable"**
2. Enter **Key** and **Value**
3. Click **"Add"**

**Critical Variables for Database:**

| Key | Value |
|-----|-------|
| `DATABASE_URL` | `jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require` |
| `SPRING_DATASOURCE_USERNAME` | `postgres.bxidlvpleddpwflfcqpw` |
| `SPRING_DATASOURCE_PASSWORD` | `6x#&3mUTPyQ#+/m` |

---

### Step 4: Save and Redeploy

1. Click **"Save Changes"**
2. Render will auto-redeploy
3. Wait 2-3 minutes

---

## 🎯 Common Issues

### Issue 1: Old Environment Variable Names

**If you see these OLD names, DELETE them:**
- ❌ `DATABASE_USERNAME` (old)
- ❌ `DATABASE_PASSWORD` (old)

**Use these NEW names instead:**
- ✅ `SPRING_DATASOURCE_USERNAME` (new)
- ✅ `SPRING_DATASOURCE_PASSWORD` (new)

---

### Issue 2: Wrong DATABASE_URL Format

**❌ WRONG:**
```
postgresql://postgres.bxidlvpleddpwflfcqpw:password@host:6543/postgres
```

**✅ CORRECT:**
```
jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require
```

**Key points:**
- Must start with `jdbc:postgresql://`
- No username/password in URL
- Must have `?sslmode=require`

---

### Issue 3: Missing Project ID in Username

**❌ WRONG:**
```
SPRING_DATASOURCE_USERNAME=postgres
```

**✅ CORRECT:**
```
SPRING_DATASOURCE_USERNAME=postgres.bxidlvpleddpwflfcqpw
```

**Must include:** `.bxidlvpleddpwflfcqpw` (your project ID)

---

## ✅ Complete Environment Variables Checklist

Copy these **EXACT** values into Render:

```bash
# Database Connection (NO credentials in URL!)
DATABASE_URL=jdbc:postgresql://aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres?sslmode=require

# Database Credentials (Separate variables)
SPRING_DATASOURCE_USERNAME=postgres.bxidlvpleddpwflfcqpw
SPRING_DATASOURCE_PASSWORD=6x#&3mUTPyQ#+/m

# Application Config
JWT_SECRET=T8mGhLgVmRNXPHiSKybq2ERi4tEahFV9bMLUjO/6UXEzQKJlhWYU9RGQDNItD/Pa
CORS_ORIGINS=https://your-vercel-url.vercel.app,http://localhost:5173
SPRING_PROFILES_ACTIVE=prod
```

**Replace `your-vercel-url` with your actual Vercel URL!**

---

## 🔍 How to Verify

### After Saving:

1. **Wait for redeploy** (2-3 minutes)
2. **Check logs** for:
   ```
   HikariPool-1 - Starting...
   HikariPool-1 - Start completed.
   Tomcat started on port 10000
   ```

3. **Test health check:**
   ```
   https://parkease-srqk.onrender.com/api/auth/health
   ```

**If working:**
- ✅ No connection errors
- ✅ "ParkEase Backend is running"

---

## 🚨 Critical: All 3 Database Variables Required

Your `application-prod.properties` expects:

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```

**All 3 must be set in Render!**

---

## 📝 Quick Fix Steps

1. ✅ Go to Render → Environment
2. ✅ Verify all 6 variables exist
3. ✅ Add any missing variables
4. ✅ Fix any wrong values
5. ✅ Save changes
6. ✅ Wait for redeploy
7. ✅ Test health check

**Your backend should connect successfully!** 🎉
