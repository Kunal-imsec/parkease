# Quick Start Guide - Supabase Migration

## ✅ Migration Complete!

All services have been migrated from JSON file storage to Supabase PostgreSQL database.

---

## Prerequisites

- **Java 17** (changed from Java 21)
- **Maven** 3.6+
- **Supabase Account** (already configured)

---

## Running the Application

### 1. Build the Project

```bash
cd parkease-backend
mvn clean install
```

### 2. Start the Server

```bash
mvn spring-boot:run
```

The application will:
- Connect to Supabase PostgreSQL
- Auto-create all 5 database tables
- Start on `http://localhost:8080`

### 3. Verify Database

Open [Supabase Dashboard](https://supabase.com/dashboard) → Table Editor

You should see:
- `users`
- `owners`
- `parking_lots`
- `bookings`
- `owner_requests`

---

## Testing Endpoints

### Register a User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123",
    "name": "Test User",
    "phone": "1234567890"
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

---

## Troubleshooting

### Java Version Error

If you see "release 21 is not found":

1. Install Java 17
2. Set `JAVA_HOME` to Java 17 path
3. Run `mvn clean install` again

### Database Connection Error

Check `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://db.bxidlvpleddpwflfcqpw.supabase.co:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=<your-password>
```

### Tables Not Created

Check console logs for Hibernate DDL statements. If tables aren't created:
```properties
spring.jpa.hibernate.ddl-auto=create
```
(Then change back to `update` after first run)

---

## What Changed

| Component | Before | After |
|-----------|--------|-------|
| **Data Storage** | JSON files | PostgreSQL database |
| **Data Access** | `JsonFileUtil` | Spring Data JPA repositories |
| **Transactions** | File locks | `@Transactional` |
| **Concurrency** | Race conditions possible | ACID guarantees |
| **Dates** | String parsing | `LocalDateTime` |

---

## Next Steps

1. ✅ **Build and run** the application
2. ✅ **Test** all endpoints
3. ✅ **Verify** data in Supabase dashboard
4. ⏳ **Migrate** existing JSON data (optional)
5. ⏳ **Deploy** to production

---

## Documentation

- [SUPABASE_SETUP.md](file:///c:/Users/kunal/parkease/SUPABASE_SETUP.md) - Detailed setup guide
- [walkthrough.md](file:///C:/Users/kunal/.gemini/antigravity/brain/362950b5-459e-40db-8c18-d877c96e5fff/walkthrough.md) - Complete migration walkthrough

---

## Support

If you encounter issues:
1. Check Supabase dashboard for connection status
2. Review application logs for errors
3. Verify Java 17 is installed
4. Ensure database credentials are correct
