# 🚀 Running Backend Locally - Quick Fix

## ❌ The Problem

Your backend is trying to connect to PostgreSQL on `localhost:5432`, but you don't have PostgreSQL installed locally.

**Error:**
```
Connection refused: localhost:5432
```

---

## ✅ Solution: Use Supabase for Local Development

I've created a `.env.local` file that uses your **Supabase database** for local development.

### What I Did:

Created `parkease-backend/.env.local`:
```bash
DATABASE_URL=jdbc:postgresql://postgres.bxidlvpleddpwflfcqpw:6x#&3mUTPyQ#+/m@aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres
```

---

## 🎯 How to Run Backend Locally

### Step 1: Stop the current process
Press `Ctrl+C` in the terminal running `mvn spring-boot:run`

### Step 2: Load environment variables and run

**Option A: Using PowerShell (Recommended)**
```powershell
cd c:\Users\kunal\parkease\parkease-backend

# Load .env.local variables
Get-Content .env.local | ForEach-Object {
    if ($_ -match '^([^=]+)=(.+)$') {
        $env:($matches[1]) = $matches[2]
    }
}

# Run the backend
mvn spring-boot:run
```

**Option B: Set manually (simpler)**
```powershell
cd c:\Users\kunal\parkease\parkease-backend

$env:DATABASE_URL="jdbc:postgresql://postgres.bxidlvpleddpwflfcqpw:6x#&3mUTPyQ#+/m@aws-1-ap-southeast-1.pooler.supabase.com:6543/postgres"

mvn spring-boot:run
```

---

## 🔄 Alternative: Install PostgreSQL Locally

If you want a truly local database:

1. **Download PostgreSQL:**
   - https://www.postgresql.org/download/windows/
   - Install version 15 or 16

2. **Create database:**
   ```sql
   CREATE DATABASE parkease;
   ```

3. **Run backend:**
   ```bash
   mvn spring-boot:run
   ```
   (It will use localhost:5432 by default)

---

## ✅ Recommended Approach

**Use Supabase for local dev** because:
- ✅ No need to install PostgreSQL
- ✅ Same database as production
- ✅ See data in Supabase dashboard
- ✅ Easier to debug

---

## 🎯 Next Steps

1. **Stop current Maven process** (Ctrl+C)
2. **Set DATABASE_URL environment variable**
3. **Run `mvn spring-boot:run` again**
4. **Backend should start successfully!** ✅

Your backend will be available at: `http://localhost:8080`
