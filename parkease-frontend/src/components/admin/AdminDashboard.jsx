import React, { useState, useEffect } from 'react';
import adminService from '../../services/adminService';
import parkingService from '../../services/parkingService';
import bookingService from '../../services/bookingService';
import '../owner/OwnerDashboard.css';

const AdminDashboard = () => {
    const [stats, setStats] = useState(null);
    const [ownerRequests, setOwnerRequests] = useState([]);
    const [owners, setOwners] = useState([]);
    const [parkingLots, setParkingLots] = useState([]);
    const [bookings, setBookings] = useState([]);
    const [activeTab, setActiveTab] = useState('overview');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        setLoading(true);
        try {
            const [statsData, requestsData, ownersData, lotsData, bookingsData] = await Promise.all([
                adminService.getDashboard(),
                adminService.getPendingRequests(),
                adminService.getAllOwners(),
                parkingService.getAll(),
                bookingService.getAllBookings(),
            ]);
            setStats(statsData);
            setOwnerRequests(requestsData);
            setOwners(ownersData);
            setParkingLots(lotsData);
            setBookings(bookingsData);
        } catch (error) {
            console.error('Error loading data:', error);
        } finally {
            setLoading(false);
        }
    };

    const handleApprove = async (requestId) => {
        try {
            console.log('Approving request:', requestId);
            const result = await adminService.approveRequest(requestId);
            console.log('Approval result:', result);
            alert('Owner approved successfully!');
            loadData();
        } catch (error) {
            console.error('Approval error:', error);
            alert('Approval failed: ' + (error.response?.data || error.message));
        }
    };

    const handleReject = async (requestId) => {
        try {
            await adminService.rejectRequest(requestId);
            alert('Request rejected');
            loadData();
        } catch (error) {
            console.error('Rejection error:', error);
            alert('Rejection failed: ' + (error.response?.data || error.message));
        }
    };

    if (loading) {
        return (
            <div className="dashboard-page">
                <div className="loading-state">
                    <div className="spinner"></div>
                    <p>Loading dashboard...</p>
                </div>
            </div>
        );
    }

    return (
        <div className="dashboard-page">
            <div className="dashboard-header">
                <div className="header-content">
                    <h1 className="dashboard-title">Admin Dashboard</h1>
                    <p className="dashboard-subtitle">Manage your parking system</p>
                </div>
            </div>

            <div className="dashboard-container">
                {/* Stats */}
                <div className="stats-grid">
                    <div className="stat-card">
                        <div className="stat-label">Total Users</div>
                        <div className="stat-value">{stats?.totalUsers || 0}</div>
                    </div>
                    <div className="stat-card">
                        <div className="stat-label">Total Owners</div>
                        <div className="stat-value">{stats?.totalOwners || 0}</div>
                        <div className="stat-subtext">Active: {owners.filter(o => o.active).length}</div>
                    </div>
                    <div className="stat-card">
                        <div className="stat-label">Parking Lots</div>
                        <div className="stat-value">{parkingLots.length}</div>
                        <div className="stat-subtext">Active: {parkingLots.filter(l => l.active).length}</div>
                    </div>
                    <div className="stat-card">
                        <div className="stat-label">Total Bookings</div>
                        <div className="stat-value">{bookings.length}</div>
                    </div>
                </div>

                {/* Pending Requests Alert */}
                {ownerRequests.length > 0 && (
                    <div className="alert-warning">
                        <svg className="icon" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
                        </svg>
                        <span>You have {ownerRequests.length} pending owner registration request(s) to review.</span>
                    </div>
                )}

                {/* Tabs */}
                <div className="tabs-container">
                    <button className={`tab-button ${activeTab === 'overview' ? 'active' : ''}`} onClick={() => setActiveTab('overview')}>
                        Overview
                    </button>
                    <button className={`tab-button ${activeTab === 'requests' ? 'active' : ''}`} onClick={() => setActiveTab('requests')}>
                        Requests ({ownerRequests.length})
                    </button>
                    <button className={`tab-button ${activeTab === 'owners' ? 'active' : ''}`} onClick={() => setActiveTab('owners')}>
                        Owners
                    </button>
                    <button className={`tab-button ${activeTab === 'lots' ? 'active' : ''}`} onClick={() => setActiveTab('lots')}>
                        Parking Lots
                    </button>
                    <button className={`tab-button ${activeTab === 'bookings' ? 'active' : ''}`} onClick={() => setActiveTab('bookings')}>
                        Bookings
                    </button>
                </div>

                {/* Overview Tab */}
                {activeTab === 'overview' && (
                    <div>
                        <h2 className="section-title">System Overview</h2>
                        <div className="lot-card">
                            <p style={{ color: 'rgba(200,200,255,0.5)', marginBottom: '1.5rem' }}>
                                Welcome to the ParkEase Admin Dashboard. Here you can manage all aspects of the parking system.
                            </p>
                            <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1.5rem' }}>
                                <div className="overview-box-cyan">
                                    <div className="overview-label">Total Revenue</div>
                                    <div className="overview-value-cyan">
                                        ${bookings.reduce((sum, b) => sum + b.totalPrice, 0).toFixed(2)}
                                    </div>
                                </div>
                                <div className="overview-box-green">
                                    <div className="overview-label">Average Price/Hour</div>
                                    <div className="overview-value-green">
                                        ${parkingLots.length > 0 ? (parkingLots.reduce((sum, l) => sum + l.pricePerHour, 0) / parkingLots.length).toFixed(2) : '0.00'}
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                )}

                {/* Requests Tab */}
                {activeTab === 'requests' && (
                    <div>
                        <h2 className="section-title">Pending Owner Requests</h2>
                        {ownerRequests.length === 0 ? (
                            <div className="empty-state">
                                <svg className="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                                </svg>
                                <p>No pending requests</p>
                            </div>
                        ) : (
                            <div className="lots-grid">
                                {ownerRequests.map((request) => (
                                    <div key={request.id} className="lot-card">
                                        <h3 className="lot-name">{request.name}</h3>
                                        <div style={{ marginTop: '1rem', display: 'flex', flexDirection: 'column', gap: '0.5rem' }}>
                                            <div className="detail-item">
                                                <svg className="detail-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                                                </svg>
                                                <span>{request.email}</span>
                                            </div>
                                            <div className="detail-item">
                                                <svg className="detail-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                                                </svg>
                                                <span>{request.phone}</span>
                                            </div>
                                            <div className="detail-item">
                                                <svg className="detail-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                                                </svg>
                                                <span>{request.businessName}</span>
                                            </div>
                                        </div>
                                        <div className="lot-actions" style={{ marginTop: '1.5rem' }}>
                                            <button onClick={() => handleApprove(request.id)} className="action-button primary">
                                                Approve
                                            </button>
                                            <button onClick={() => handleReject(request.id)} className="action-button danger">
                                                Reject
                                            </button>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>
                )}

                {/* Owners Tab */}
                {activeTab === 'owners' && (
                    <div>
                        <h2 className="section-title">All Owners</h2>
                        <div className="lot-card">
                            <div style={{ overflowX: 'auto' }}>
                                <table className="data-table">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Email</th>
                                            <th>Business</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {owners.map((owner) => (
                                            <tr key={owner.id}>
                                                <td>{owner.name}</td>
                                                <td>{owner.email}</td>
                                                <td>{owner.businessName}</td>
                                                <td>
                                                    <span className={`booking-status ${owner.active ? 'status-completed' : 'status-cancelled'}`}>
                                                        {owner.active ? 'ACTIVE' : 'INACTIVE'}
                                                    </span>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                )}

                {/* Parking Lots Tab */}
                {activeTab === 'lots' && (
                    <div>
                        <h2 className="section-title">All Parking Lots</h2>
                        <div className="lot-card">
                            <div style={{ overflowX: 'auto' }}>
                                <table className="data-table">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Owner</th>
                                            <th>Location</th>
                                            <th>Price/Hour</th>
                                            <th>Slots</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {parkingLots.map((lot) => (
                                            <tr key={lot.id}>
                                                <td>{lot.name}</td>
                                                <td>{lot.ownerName}</td>
                                                <td>{lot.city}, {lot.area}</td>
                                                <td>${lot.pricePerHour}</td>
                                                <td>{lot.availableSlots}/{lot.totalSlots}</td>
                                                <td>
                                                    <span className={`booking-status ${lot.availability === 'AVAILABLE' ? 'status-completed' : 'status-cancelled'}`}>
                                                        {lot.availability}
                                                    </span>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                )}

                {/* Bookings Tab */}
                {activeTab === 'bookings' && (
                    <div>
                        <h2 className="section-title">All Bookings</h2>
                        <div className="lot-card">
                            <div style={{ overflowX: 'auto' }}>
                                <table className="data-table">
                                    <thead>
                                        <tr>
                                            <th>User</th>
                                            <th>Parking Lot</th>
                                            <th>Date</th>
                                            <th>Hours</th>
                                            <th>Amount</th>
                                            <th>Status</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {bookings.map((booking) => (
                                            <tr key={booking.id}>
                                                <td>{booking.userName}</td>
                                                <td>{booking.parkingLotName}</td>
                                                <td>{new Date(booking.bookingDate).toLocaleDateString()}</td>
                                                <td>{booking.hours}h</td>
                                                <td>${booking.totalPrice.toFixed(2)}</td>
                                                <td>
                                                    <span className={`booking-status ${booking.status === 'CONFIRMED' ? 'status-confirmed' :
                                                        booking.status === 'COMPLETED' ? 'status-completed' : 'status-cancelled'
                                                        }`}>
                                                        {booking.status}
                                                    </span>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default AdminDashboard;
