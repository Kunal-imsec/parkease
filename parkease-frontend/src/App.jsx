import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { Provider } from 'react-redux';
import store from './store/store';
import Navbar from './components/common/Navbar';
import ProtectedRoute from './components/common/ProtectedRoute';
import LandingPage from './components/landing/LandingPage';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import OwnerRegister from './components/auth/OwnerRegister';
import UserDashboard from './components/user/UserDashboard';
import BookingHistory from './components/user/BookingHistory';
import OwnerDashboard from './components/owner/OwnerDashboard';
import AdminDashboard from './components/admin/AdminDashboard';

function App() {
    const hasVisited = localStorage.getItem('hasVisited');
    const isAuthenticated = localStorage.getItem('token');

    return (
        <Provider store={store}>
            <Router>
                <div className="App">
                    <Routes>
                        {/* Landing Page - always shown at root */}
                        <Route path="/" element={<LandingPage />} />
                        <Route path="/landing" element={<LandingPage />} />

                        {/* Public Routes */}
                        <Route path="/login" element={<><Navbar /><Login /></>} />
                        <Route path="/register" element={<><Navbar /><Register /></>} />
                        <Route path="/register/owner" element={<><Navbar /><OwnerRegister /></>} />

                        {/* User Routes */}
                        <Route
                            path="/user/dashboard"
                            element={
                                <ProtectedRoute allowedRoles={['USER']}>
                                    <>
                                        <Navbar />
                                        <UserDashboard />
                                    </>
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/user/bookings"
                            element={
                                <ProtectedRoute allowedRoles={['USER']}>
                                    <>
                                        <Navbar />
                                        <BookingHistory />
                                    </>
                                </ProtectedRoute>
                            }
                        />

                        {/* Owner Routes */}
                        <Route
                            path="/owner/dashboard"
                            element={
                                <ProtectedRoute allowedRoles={['OWNER']}>
                                    <>
                                        <Navbar />
                                        <OwnerDashboard />
                                    </>
                                </ProtectedRoute>
                            }
                        />

                        {/* Admin Routes */}
                        <Route
                            path="/admin/dashboard"
                            element={
                                <ProtectedRoute allowedRoles={['ADMIN']}>
                                    <>
                                        <Navbar />
                                        <AdminDashboard />
                                    </>
                                </ProtectedRoute>
                            }
                        />

                        {/* Unauthorized */}
                        <Route path="/unauthorized" element={
                            <div className="min-h-screen flex items-center justify-center">
                                <div className="glass-card p-8 text-center">
                                    <h1 className="text-3xl font-bold text-white mb-4">Unauthorized Access</h1>
                                    <p className="text-white">You don't have permission to access this page.</p>
                                </div>
                            </div>
                        } />

                        {/* 404 */}
                        <Route path="*" element={
                            <div className="min-h-screen flex items-center justify-center">
                                <div className="glass-card p-8 text-center">
                                    <h1 className="text-3xl font-bold text-white mb-4">404 - Page Not Found</h1>
                                    <p className="text-white">The page you're looking for doesn't exist.</p>
                                </div>
                            </div>
                        } />
                    </Routes>
                </div>
            </Router>
        </Provider>
    );
}

export default App;
