# ParkEase - Urban Parking System

A complete web application for urban parking management with role-based access control, Google Maps integration, and file-based data persistence.

## 🎯 Features

### Three User Roles

#### 👤 User
- View available parking lots on Google Maps
- Search parking by city or area
- View parking lot details (price, availability, owner, slots)
- Book parking slots
- View booking history
- Cancel bookings

#### 🏢 Parking Owner
- Register and wait for admin approval
- Create, update, and delete parking lots
- Set parking availability (Available/Not Available)
- Set pricing and number of slots
- Update location coordinates
- View bookings for owned parking lots

#### 👨‍💼 Admin
- View all parking owners
- Approve or reject owner registration requests
- View all parking lots
- Activate/deactivate parking lots
- View all user bookings
- Dashboard with system statistics

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Security**: JWT Authentication
- **Data Storage**: JSON files (no database)
- **Build Tool**: Maven

### Frontend
- **Framework**: React 18
- **Build Tool**: Vite
- **Styling**: TailwindCSS
- **State Management**: Redux Toolkit
- **Routing**: React Router
- **Maps**: Google Maps JavaScript API
- **HTTP Client**: Axios

## 📁 Project Structure

```
parkease/
├── parkease-backend/          # Spring Boot Backend
│   ├── src/main/java/com/parkease/
│   │   ├── ParkEaseApplication.java
│   │   ├── config/           # Security & CORS configuration
│   │   ├── controller/       # REST controllers
│   │   ├── service/          # Business logic
│   │   ├── model/            # Domain models
│   │   ├── dto/              # Data Transfer Objects
│   │   ├── util/             # Utilities (JWT, JSON file operations)
│   │   └── security/         # JWT authentication filter
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── data/             # JSON data files
│   │       ├── users.json
│   │       ├── owners.json
│   │       ├── parkingLots.json
│   │       ├── bookings.json
│   │       └── ownerRequests.json
│   └── pom.xml
│
└── parkease-frontend/         # React Frontend
    ├── src/
    │   ├── components/
    │   │   ├── common/       # Navbar, ProtectedRoute
    │   │   ├── auth/         # Login, Register
    │   │   ├── user/         # User dashboard, Booking history
    │   │   ├── owner/        # Owner dashboard
    │   │   └── admin/        # Admin dashboard
    │   ├── store/            # Redux store & slices
    │   ├── services/         # API services
    │   ├── App.jsx
    │   ├── main.jsx
    │   └── index.css
    ├── package.json
    └── vite.config.js
```

## 🚀 Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm
- Google Maps API Key

### Backend Setup

1. **Navigate to backend directory**
   ```bash
   cd parkease/parkease-backend
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

### Frontend Setup

1. **Navigate to frontend directory**
   ```bash
   cd parkease/parkease-frontend
   ```

2. **Install dependencies**
   ```bash
   npm install
   ```

3. **Configure Google Maps API Key**
   
   Open `src/components/user/UserDashboard.jsx` and replace:
   ```javascript
   const GOOGLE_MAPS_API_KEY = 'YOUR_GOOGLE_MAPS_API_KEY_HERE';
   ```
   
   **How to get Google Maps API Key:**
   - Go to [Google Cloud Console](https://console.cloud.google.com/)
   - Create a new project or select existing
   - Enable "Maps JavaScript API"
   - Create credentials (API Key)
   - Copy the API key

4. **Run the development server**
   ```bash
   npm run dev
   ```

   The frontend will start on `http://localhost:5173`

## 🔑 Demo Credentials

### Admin
- **Email**: admin@parkease.com
- **Password**: admin123
- **Role**: ADMIN

### Parking Owner
- **Email**: owner1@parkease.com
- **Password**: password123
- **Role**: OWNER

### User
- **Email**: john@example.com
- **Password**: password123
- **Role**: USER

## 📡 API Endpoints

### Authentication
- `POST /api/auth/login` - Login
- `POST /api/auth/register/user` - Register user
- `POST /api/auth/register/owner` - Register owner (requires approval)

### Admin
- `GET /api/admin/dashboard` - Dashboard statistics
- `GET /api/admin/owner-requests/pending` - Pending owner requests
- `POST /api/admin/owner-requests/{id}/approve` - Approve owner
- `POST /api/admin/owner-requests/{id}/reject` - Reject owner
- `GET /api/admin/owners` - All owners
- `PUT /api/admin/parking-lots/{id}/toggle` - Toggle parking lot status

### Owner
- `GET /api/owner/parking-lots` - Owner's parking lots
- `POST /api/owner/parking-lots` - Create parking lot
- `PUT /api/owner/parking-lots/{id}` - Update parking lot
- `DELETE /api/owner/parking-lots/{id}` - Delete parking lot
- `PUT /api/owner/parking-lots/{id}/availability` - Update availability
- `GET /api/owner/bookings` - Owner's bookings

### User/Public
- `GET /api/parking/available` - Available parking lots
- `GET /api/parking/{id}` - Parking lot details
- `GET /api/parking/search/city/{city}` - Search by city
- `GET /api/parking/search/area/{area}` - Search by area

### Bookings
- `POST /api/bookings` - Create booking
- `GET /api/bookings/user` - User's bookings
- `GET /api/bookings/all` - All bookings (admin)
- `PUT /api/bookings/{id}/cancel` - Cancel booking

## 🎨 Features Walkthrough

### User Flow
1. Register as a user
2. Login with user credentials
3. View parking lots on Google Maps
4. Search by city/area
5. Click on map markers to see details
6. Book a parking slot
7. View booking history
8. Cancel bookings if needed

### Owner Flow
1. Register as parking owner
2. Wait for admin approval
3. Login after approval
4. Create parking lots with location coordinates
5. Set pricing and availability
6. Manage parking lots (edit/delete)
7. View bookings for owned lots

### Admin Flow
1. Login with admin credentials
2. View dashboard statistics
3. Review pending owner requests
4. Approve/reject owner registrations
5. Manage all parking lots
6. View all bookings
7. Activate/deactivate parking lots

## 🗄️ Data Persistence

All data is stored in JSON files located at `parkease-backend/src/main/resources/data/`:

- **users.json** - User accounts
- **owners.json** - Approved parking owners
- **parkingLots.json** - Parking lot information
- **bookings.json** - Booking records
- **ownerRequests.json** - Pending owner registration requests

Data persists across server restarts.

## 🔒 Security

- JWT-based authentication
- Role-based access control (RBAC)
- Password encryption using BCrypt
- Protected routes on frontend
- CORS configuration for cross-origin requests

## 🎯 Key Features Implementation

### Google Maps Integration
- Interactive map with custom markers
- Info windows showing parking details
- Real-time availability display
- Booking directly from map

### File-Based Storage
- Thread-safe JSON file operations
- Automatic directory creation
- Pretty-printed JSON for readability
- No database required

### Booking System
- Real-time slot availability
- Automatic slot count management
- Booking confirmation
- Cancellation with slot restoration

## 🐛 Troubleshooting

### Backend Issues
- **Port 8080 already in use**: Change port in `application.properties`
- **File permission errors**: Ensure write permissions for `src/main/resources/data/`

### Frontend Issues
- **Google Maps not loading**: Verify API key is correct and Maps JavaScript API is enabled
- **CORS errors**: Ensure backend is running and CORS is properly configured
- **Module not found**: Run `npm install` again

## 📝 Notes

- Default passwords in sample data are hashed with BCrypt (password: "password123")
- Admin credentials are hardcoded in AuthService for demo purposes
- Google Maps API key must be added for map functionality
- Sample data includes NYC coordinates for parking lots

## 🚀 Production Deployment

For production deployment:
1. Replace file-based storage with a real database
2. Use environment variables for sensitive data
3. Enable HTTPS
4. Implement rate limiting
5. Add comprehensive error handling
6. Set up proper logging
7. Use production-grade secret keys

## 📄 License

This project is created for educational purposes.

---

**Built with ❤️ using Spring Boot and React**
