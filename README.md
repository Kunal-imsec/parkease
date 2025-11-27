# 🚗 ParkEase - Modern Urban Parking Management System

A full-stack parking management application featuring a stunning **Linear-inspired dark UI** with 3D animated cars, glassmorphism effects, and smooth scrolling. Built with React and Spring Boot.

![ParkEase Banner](https://img.shields.io/badge/Status-Production%20Ready-success?style=for-the-badge)
![React](https://img.shields.io/badge/React-18-blue?style=for-the-badge&logo=react)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3-green?style=for-the-badge&logo=springboot)

## ✨ Features

### 🎨 Beautiful UI/UX
- **Linear-inspired Design**: Minimalist dark theme with purple accents
- **3D Animated Landing Page**: Realistic car animations with smooth parallax scrolling
- **Glassmorphism Effects**: Modern frosted glass UI components
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile
- **Smooth Animations**: Powered by Framer Motion

### 👥 User Features
- Browse available parking lots with real-time availability
- Advanced search and filtering
- Book parking spots with flexible hourly rates
- View complete booking history
- Cancel bookings
- Interactive map integration
- Status tracking (Confirmed, Completed, Cancelled)

### 🏢 Owner Features
- Add and manage multiple parking lots
- Set custom pricing and availability
- View all bookings for owned properties
- Real-time revenue tracking
- Toggle lot availability on/off
- Edit parking lot details

### 🔐 Admin Features
- Approve/reject owner registration requests
- System-wide statistics dashboard
- Manage all owners and their status
- Monitor all parking lots
- View all bookings across the platform
- Comprehensive analytics

## 🛠️ Tech Stack

### Frontend
- **React 18** - UI library
- **Redux Toolkit** - State management
- **React Router v6** - Client-side routing
- **Framer Motion** - Animation library
- **Axios** - HTTP client
- **Vite** - Build tool

### Backend
- **Spring Boot 3** - Java framework
- **Spring Security** - Authentication & authorization
- **JWT** - Token-based authentication
- **BCrypt** - Password hashing
- **JSON File Storage** - Lightweight data persistence
- **Maven** - Dependency management

### Design System
- **Colors**: Dark (#0D0D0D), Purple accent (#5E6AD2)
- **Typography**: Inter font family
- **Effects**: Glassmorphism, smooth transitions, purple glows

## 📋 Prerequisites

Before running this project, ensure you have the following installed:

- **Node.js** (v16 or higher) - [Download](https://nodejs.org/)
- **Java JDK** (v17 or higher) - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven** (v3.6 or higher) - [Download](https://maven.apache.org/download.cgi)
- **Git** - [Download](https://git-scm.com/downloads)

## 🚀 Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/kunal-imsec/parkease.git
cd parkease
```

### 2. Backend Setup (Spring Boot)

```bash
# Navigate to backend directory
cd parkease-backend

# Install dependencies and run
mvn spring-boot:run
```

The backend will start on **http://localhost:8080**

**Backend Features:**
- REST API endpoints
- JWT authentication
- Role-based access control (USER, OWNER, ADMIN)
- JSON file-based data storage in `src/main/resources/data/`

### 3. Frontend Setup (React)

Open a new terminal window:

```bash
# Navigate to frontend directory
cd parkease-frontend

# Install dependencies
npm install

# Start development server
npm run dev
```

The frontend will start on **http://localhost:5173**

### 4. Access the Application

Open your browser and navigate to:
- **Landing Page**: http://localhost:5173/
- **Login**: http://localhost:5173/login

## 🔑 Demo Credentials

### Regular User
```
Email: john@example.com
Password: password123
Role: USER
```

### Parking Lot Owner
```
Email: owner1@parkease.com
Password: password123
Role: OWNER
```

### System Administrator
```
Email: admin@parkease.com
Password: admin123
Role: ADMIN
```

## 📱 Application Routes

| Route | Description | Access |
|-------|-------------|--------|
| `/` | Landing page with 3D car animations | Public |
| `/login` | User login | Public |
| `/register` | User registration | Public |
| `/register/owner` | Owner registration (requires admin approval) | Public |
| `/user/dashboard` | User dashboard - browse & book parking | User only |
| `/user/bookings` | View booking history | User only |
| `/owner/dashboard` | Manage parking lots & view bookings | Owner only |
| `/admin/dashboard` | System management & analytics | Admin only |

## 🏗️ Project Structure

```
parkease/
├── parkease-frontend/          # React frontend
│   ├── public/                 # Static assets (car images)
│   ├── src/
│   │   ├── components/         # React components
│   │   │   ├── admin/          # Admin dashboard
│   │   │   ├── auth/           # Login, Register
│   │   │   ├── common/         # Navbar, ProtectedRoute
│   │   │   ├── landing/        # Landing page with cars
│   │   │   ├── owner/          # Owner dashboard
│   │   │   └── user/           # User dashboard, Bookings
│   │   ├── services/           # API service layer
│   │   ├── store/              # Redux store & slices
│   │   └── App.jsx             # Main app component
│   └── package.json
│
└── parkease-backend/           # Spring Boot backend
    ├── src/main/
    │   ├── java/com/parkease/
    │   │   ├── config/         # Security, CORS config
    │   │   ├── controller/     # REST controllers
    │   │   ├── dto/            # Data transfer objects
    │   │   ├── model/          # Entity models
    │   │   ├── security/       # JWT filters
    │   │   ├── service/        # Business logic
    │   │   └── util/           # Utilities (JWT, JSON)
    │   └── resources/
    │       ├── data/           # JSON data files
    │       └── application.properties
    └── pom.xml
```

## 🎯 Key Features Explained

### Authentication Flow
1. User registers (USER or OWNER role)
2. Owner registrations require admin approval
3. Login generates JWT token
4. Token stored in localStorage
5. Protected routes validate token and role

### Booking Flow
1. User browses available parking lots
2. Selects lot and duration (hours)
3. Confirms booking
4. Booking status: CONFIRMED → COMPLETED or CANCELLED
5. Owner sees booking in their dashboard

### Owner Approval Flow
1. User registers as owner
2. Request stored in `ownerRequests.json`
3. Admin reviews in admin dashboard
4. Admin approves/rejects
5. Approved owners can login and manage lots

## 🎨 Design Highlights

- **Dark Theme**: #0D0D0D background for reduced eye strain
- **Purple Accent**: #5E6AD2 for CTAs and highlights
- **Glassmorphism**: `backdrop-filter: blur()` for modern cards
- **Smooth Animations**: 0.2s-0.3s transitions throughout
- **Inter Font**: Clean, modern typography
- **Status Badges**: Color-coded booking statuses

## 🔧 Configuration

### Backend Configuration
Edit `parkease-backend/src/main/resources/application.properties`:

```properties
server.port=8080
jwt.secret=your-secret-key-here
jwt.expiration=86400000
```

### Frontend Configuration
Edit `parkease-frontend/src/services/api.js` if backend URL changes:

```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

## 📦 Build for Production

### Frontend Build
```bash
cd parkease-frontend
npm run build
```
Output will be in `dist/` folder.

### Backend Build
```bash
cd parkease-backend
mvn clean package
java -jar target/parkease-backend-0.0.1-SNAPSHOT.jar
```

## 🐛 Troubleshooting

### Port Already in Use
- **Frontend (5173)**: Change port in `vite.config.js`
- **Backend (8080)**: Change in `application.properties`

### CORS Issues
Backend is configured for `http://localhost:5173`. Update `@CrossOrigin` annotations if frontend URL changes.

### Authentication Issues
- Clear browser localStorage
- Check JWT token expiration
- Verify credentials in `data/users.json`, `data/owners.json`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Kunal Agrawal**
- GitHub: [@kunal-imsec](https://github.com/kunal-imsec)

## 🙏 Acknowledgments

- Design inspired by [Linear.app](https://linear.app)
- Car animations using Framer Motion
- UI components styled with custom CSS

## 📸 Screenshots

### Landing Page
Beautiful 3D animated cars with smooth scrolling and purple glow effects.

### User Dashboard
Browse and book parking spots with real-time availability and interactive maps.

### Admin Dashboard
Comprehensive system management with statistics, owner approvals, and analytics.

---

**⭐ If you find this project useful, please consider giving it a star!**
