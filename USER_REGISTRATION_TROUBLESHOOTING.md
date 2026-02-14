# User Registration Troubleshooting

## Common Causes of User Registration Failure

### 1. **Email Already Registered**
**Error:** "Email already registered"

**Solution:**
- Try a different email address
- OR check if the user already exists in the database

**To check in Supabase:**
```sql
SELECT * FROM users WHERE email = 'your-email@example.com';
```

If the user exists, you can either:
- Log in with that email
- Delete the user and re-register:
  ```sql
  DELETE FROM users WHERE email = 'your-email@example.com';
  ```

---

### 2. **Missing Required Fields**
The User model requires:
- ✅ Email (must be unique)
- ✅ Password
- ✅ Name
- ✅ Phone
- ✅ Role (auto-set to "USER")

**Solution:** Make sure all fields in the registration form are filled out.

---

### 3. **Database Connection Issue**
If the backend can't connect to Supabase, registration will fail.

**Check:**
1. Look at backend console logs for database errors
2. Verify Supabase credentials in `application.properties`
3. Make sure backend is running

---

### 4. **Password Encoding Issue**
The backend uses BCrypt to encrypt passwords. If there's an issue with the PasswordEncoder bean, registration fails.

**Check backend logs for:**
- `PasswordEncoder` errors
- `BCrypt` errors

---

### 5. **CORS Issue**
If the frontend can't reach the backend API.

**Symptoms:**
- Network error in browser console (F12)
- "Failed to fetch" error

**Solution:**
- Make sure backend is running on port 8080
- Check that frontend is configured to call `http://localhost:8080`

---

## Quick Diagnostic Steps

### Step 1: Check Browser Console
1. Open browser DevTools (F12)
2. Go to Console tab
3. Try to register
4. Look for error messages (red text)
5. Share the error message

### Step 2: Check Backend Logs
1. Look at the terminal where backend is running
2. Try to register
3. Look for error messages or stack traces
4. Share the error

### Step 3: Test with Curl
Test the registration endpoint directly:

```bash
curl -X POST http://localhost:8080/api/auth/register/user \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "name": "Test User",
    "phone": "1234567890"
  }'
```

**Expected response:** `"User registered successfully"`

---

## Manual User Creation (Workaround)

If registration keeps failing, you can manually create a user in Supabase:

```sql
INSERT INTO users (id, email, password, name, phone, role, active)
VALUES (
  gen_random_uuid()::text,
  'test@example.com',
  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', -- password: 'password123'
  'Test User',
  '1234567890',
  'USER',
  true
);
```

Then log in with:
- Email: `test@example.com`
- Password: `password123`

---

## Need More Help?

Please provide:
1. **Exact error message** from the UI
2. **Browser console errors** (F12 → Console tab)
3. **Backend logs** from the terminal

This will help diagnose the specific issue!
