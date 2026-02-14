# Supabase Setup Guide for ParkEase

## Overview

ParkEase now uses Supabase PostgreSQL database for data persistence with Spring Data JPA.

## Database Configuration

The application is configured to connect to your Supabase database:

- **Database URL**: `jdbc:postgresql://db.bxidlvpleddpwflfcqpw.supabase.co:5432/postgres`
- **Username**: `postgres`
- **Password**: Configured in `application.properties`

## Database Schema

The following tables will be automatically created by Hibernate:

### users
- `id` (VARCHAR, Primary Key, UUID)
- `email` (VARCHAR, Unique, Not Null)
- `password` (VARCHAR, Not Null) - BCrypt hashed
- `name` (VARCHAR, Not Null)
- `phone` (VARCHAR)
- `role` (VARCHAR, Not Null) - USER/OWNER/ADMIN
- `active` (BOOLEAN, Not Null, Default: true)

### owners
- `id` (VARCHAR, Primary Key, UUID)
- `email` (VARCHAR, Unique, Not Null)
- `password` (VARCHAR, Not Null) - BCrypt hashed
- `name` (VARCHAR)
- `phone` (VARCHAR)
- `business_name` (VARCHAR)
- `approved` (BOOLEAN, Not Null, Default: false)
- `active` (BOOLEAN, Not Null, Default: true)

### parking_lots
- `id` (VARCHAR, Primary Key, UUID)
- `owner_id` (VARCHAR, Not Null)
- `owner_name` (VARCHAR)
- `name` (VARCHAR, Not Null)
- `address` (VARCHAR)
- `city` (VARCHAR)
- `area` (VARCHAR)
- `latitude` (DOUBLE)
- `longitude` (DOUBLE)
- `price_per_hour` (DOUBLE, Not Null)
- `total_slots` (INTEGER, Not Null)
- `available_slots` (INTEGER, Not Null)
- `availability` (VARCHAR, Not Null) - AVAILABLE/NOT_AVAILABLE
- `active` (BOOLEAN, Not Null, Default: true)

### bookings
- `id` (VARCHAR, Primary Key, UUID)
- `user_id` (VARCHAR, Not Null)
- `user_name` (VARCHAR)
- `user_email` (VARCHAR)
- `parking_lot_id` (VARCHAR, Not Null)
- `parking_lot_name` (VARCHAR)
- `parking_lot_address` (VARCHAR)
- `price_per_hour` (DOUBLE)
- `hours` (INTEGER, Not Null)
- `total_price` (DOUBLE, Not Null)
- `booking_date` (TIMESTAMP, Not Null)
- `status` (VARCHAR, Not Null) - CONFIRMED/CANCELLED/COMPLETED

### owner_requests
- `id` (VARCHAR, Primary Key, UUID)
- `email` (VARCHAR, Unique, Not Null)
- `password` (VARCHAR, Not Null) - BCrypt hashed
- `name` (VARCHAR)
- `phone` (VARCHAR)
- `business_name` (VARCHAR)
- `request_date` (TIMESTAMP, Not Null)
- `status` (VARCHAR, Not Null) - PENDING/APPROVED/REJECTED

## Running the Application

1. **Build the project**:
   ```bash
   cd parkease-backend
   mvn clean install
   ```

2. **Start the server**:
   ```bash
   mvn spring-boot:run
   ```

3. **Verify database tables**:
   - Go to your Supabase dashboard
   - Navigate to Table Editor
   - You should see all 5 tables created automatically

## Migration from JSON Files

The existing JSON data files in `src/main/resources/data/` are no longer used. To migrate existing data:

1. The application will start with an empty database
2. You can manually insert data via Supabase dashboard
3. Or use the API endpoints to create new data

## Environment Variables (Production)

For production deployment, set these environment variables:

```bash
DATABASE_URL=jdbc:postgresql://db.bxidlvpleddpwflfcqpw.supabase.co:5432/postgres
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your-password-here
JWT_SECRET=your-jwt-secret-here
CORS_ORIGINS=https://your-frontend-domain.com
```

## Troubleshooting

### Connection Issues
- Verify your Supabase project is active
- Check that the database password is correct
- Ensure your IP is allowed in Supabase network settings

### Table Creation Issues
- Check `spring.jpa.hibernate.ddl-auto=update` in `application.properties`
- Review application logs for Hibernate SQL statements

### Data Not Persisting
- Verify transactions are completing successfully
- Check for any constraint violations in logs
