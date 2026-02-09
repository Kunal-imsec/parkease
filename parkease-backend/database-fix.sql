-- ============================================
-- ParkEase Database Schema Fix
-- ============================================
-- This script adds the missing latitude and longitude columns
-- to the bookings table in your Supabase database.
--
-- INSTRUCTIONS:
-- 1. Go to your Supabase Dashboard (https://supabase.com/dashboard)
-- 2. Select your project
-- 3. Go to SQL Editor (left sidebar)
-- 4. Copy and paste this entire script
-- 5. Click "Run" to execute
-- ============================================

-- Step 1: Add latitude column to bookings table
ALTER TABLE bookings 
ADD COLUMN IF NOT EXISTS latitude DOUBLE PRECISION DEFAULT 0.0;

-- Step 2: Add longitude column to bookings table
ALTER TABLE bookings 
ADD COLUMN IF NOT EXISTS longitude DOUBLE PRECISION DEFAULT 0.0;

-- Step 3: Verify the columns were added successfully
SELECT 
    column_name, 
    data_type, 
    is_nullable,
    column_default
FROM information_schema.columns
WHERE table_name = 'bookings'
  AND column_name IN ('latitude', 'longitude')
ORDER BY column_name;

-- Expected output:
-- column_name | data_type         | is_nullable | column_default
-- ------------|-------------------|-------------|---------------
-- latitude    | double precision  | YES         | 0.0
-- longitude   | double precision  | YES         | 0.0

-- ============================================
-- OPTIONAL: Update existing bookings with coordinates
-- ============================================
-- Uncomment the following section if you want to populate
-- existing bookings with coordinates from their parking lots

/*
UPDATE bookings b
SET 
    latitude = pl.latitude,
    longitude = pl.longitude
FROM parking_lots pl
WHERE b.parking_lot_id = pl.id
  AND (b.latitude = 0.0 OR b.latitude IS NULL);

-- Verify the update
SELECT 
    COUNT(*) as total_bookings,
    COUNT(CASE WHEN latitude != 0.0 THEN 1 END) as bookings_with_coordinates
FROM bookings;
*/
