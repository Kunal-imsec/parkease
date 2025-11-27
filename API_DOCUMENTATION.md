# ParkEase API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
All protected endpoints require a JWT token in the Authorization header:
```
Authorization: Bearer <token>
```

---

## 🔐 Authentication Endpoints

### Login
```http
POST /auth/login
```

**Request Body:**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "role": "USER" // USER, OWNER, or ADMIN
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "role": "USER",
  "id": "user-1",
  "name": "John Doe",
  "email": "user@example.com"
}
```

### Register User
```http
POST /auth/register/user
```

**Request Body:**
```json
{
  "email": "newuser@example.com",
  "password": "password123",
  "name": "New User",
  "phone": "+1234567890"
}
```

### Register Owner
```http
POST /auth/register/owner
```

**Request Body:**
```json
{
  "email": "owner@example.com",
  "password": "password123",
  "name": "Owner Name",
  "phone": "+1234567890",
  "businessName": "Parking Business Inc"
}
```

---

## 👨‍💼 Admin Endpoints

### Get Dashboard Statistics
```http
GET /admin/dashboard
Authorization: Bearer <admin-token>
```

**Response:**
```json
{
  "totalUsers": 10,
  "totalOwners": 5,
  "totalParkingLots": 20,
  "totalBookings": 50,
  "pendingOwnerRequests": 2,
  "activeOwners": 4,
  "activeParkingLots": 18
}
```

### Get Pending Owner Requests
```http
GET /admin/owner-requests/pending
Authorization: Bearer <admin-token>
```

### Get All Owner Requests
```http
GET /admin/owner-requests
Authorization: Bearer <admin-token>
```

### Approve Owner Request
```http
POST /admin/owner-requests/{requestId}/approve
Authorization: Bearer <admin-token>
```

### Reject Owner Request
```http
POST /admin/owner-requests/{requestId}/reject
Authorization: Bearer <admin-token>
```

### Get All Owners
```http
GET /admin/owners
Authorization: Bearer <admin-token>
```

### Toggle Parking Lot Status
```http
PUT /admin/parking-lots/{lotId}/toggle
Authorization: Bearer <admin-token>
```

---

## 🏢 Owner Endpoints

### Get Owner's Parking Lots
```http
GET /owner/parking-lots
Authorization: Bearer <owner-token>
```

### Create Parking Lot
```http
POST /owner/parking-lots
Authorization: Bearer <owner-token>
```

**Request Body:**
```json
{
  "name": "Downtown Parking",
  "address": "123 Main St",
  "city": "New York",
  "area": "Manhattan",
  "latitude": 40.7589,
  "longitude": -73.9851,
  "pricePerHour": 15.0,
  "totalSlots": 50,
  "availability": "AVAILABLE"
}
```

### Update Parking Lot
```http
PUT /owner/parking-lots/{lotId}
Authorization: Bearer <owner-token>
```

**Request Body:** Same as Create

### Delete Parking Lot
```http
DELETE /owner/parking-lots/{lotId}
Authorization: Bearer <owner-token>
```

### Update Availability
```http
PUT /owner/parking-lots/{lotId}/availability
Authorization: Bearer <owner-token>
```

**Request Body:**
```json
{
  "availability": "AVAILABLE" // or "NOT_AVAILABLE"
}
```

### Get Owner's Bookings
```http
GET /owner/bookings
Authorization: Bearer <owner-token>
```

---

## 🅿️ Parking Lot Endpoints (Public/User)

### Get Available Parking Lots
```http
GET /parking/available
```

**Response:**
```json
[
  {
    "id": "lot-1",
    "ownerId": "owner-1",
    "ownerName": "Mike Johnson",
    "name": "Downtown Plaza Parking",
    "address": "123 Main Street",
    "city": "New York",
    "area": "Manhattan",
    "latitude": 40.7589,
    "longitude": -73.9851,
    "pricePerHour": 15.0,
    "totalSlots": 50,
    "availableSlots": 45,
    "availability": "AVAILABLE",
    "active": true
  }
]
```

### Get All Parking Lots
```http
GET /parking/all
```

### Get Parking Lot by ID
```http
GET /parking/{id}
```

### Search by City
```http
GET /parking/search/city/{city}
```

### Search by Area
```http
GET /parking/search/area/{area}
```

---

## 📅 Booking Endpoints

### Create Booking
```http
POST /bookings
Authorization: Bearer <user-token>
```

**Request Body:**
```json
{
  "parkingLotId": "lot-1",
  "hours": 3
}
```

**Response:**
```json
{
  "id": "booking-1",
  "userId": "user-1",
  "userName": "John Doe",
  "userEmail": "john@example.com",
  "parkingLotId": "lot-1",
  "parkingLotName": "Downtown Plaza Parking",
  "parkingLotAddress": "123 Main Street",
  "pricePerHour": 15.0,
  "hours": 3,
  "totalPrice": 45.0,
  "bookingDate": "2025-11-27T10:30:00",
  "status": "CONFIRMED"
}
```

### Get User's Bookings
```http
GET /bookings/user
Authorization: Bearer <user-token>
```

### Get All Bookings (Admin)
```http
GET /bookings/all
Authorization: Bearer <admin-token>
```

### Cancel Booking
```http
PUT /bookings/{bookingId}/cancel
Authorization: Bearer <user-token>
```

---

## 📊 Response Codes

| Code | Description |
|------|-------------|
| 200 | Success |
| 400 | Bad Request (validation error) |
| 401 | Unauthorized (invalid/missing token) |
| 403 | Forbidden (insufficient permissions) |
| 404 | Not Found |
| 500 | Internal Server Error |

---

## 🔑 Sample Test Data

### Admin
- Email: `admin@parkease.com`
- Password: `admin123`

### Owner
- Email: `owner1@parkease.com`
- Password: `password123`

### User
- Email: `john@example.com`
- Password: `password123`

---

## 💡 Usage Examples

### Example: User Booking Flow

1. **Login as User**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123",
    "role": "USER"
  }'
```

2. **Get Available Parking**
```bash
curl http://localhost:8080/api/parking/available
```

3. **Create Booking**
```bash
curl -X POST http://localhost:8080/api/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "parkingLotId": "lot-1",
    "hours": 2
  }'
```

4. **View Bookings**
```bash
curl http://localhost:8080/api/bookings/user \
  -H "Authorization: Bearer <token>"
```

---

## 🔒 Security Notes

- All passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours
- Role-based access control enforced on all protected endpoints
- CORS enabled for frontend origin (http://localhost:5173)
