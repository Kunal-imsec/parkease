import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import bookingService from '../../services/bookingService';
import './Dashboard.css';

const BookingHistory = () => {
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        loadBookings();
    }, []);

    const loadBookings = async () => {
        try {
            const data = await bookingService.getUserBookings();
            setBookings(data);
        } catch (error) {
            console.error('Error loading bookings:', error);
            setMessage('Failed to load bookings');
        } finally {
            setLoading(false);
        }
    };

    const handleCancel = async (id) => {
        if (!window.confirm('Are you sure you want to cancel this booking?')) return;

        try {
            await bookingService.cancelBooking(id);
            setMessage('Booking cancelled successfully');
            loadBookings();
        } catch (error) {
            setMessage('Failed to cancel booking');
        }
    };

    const openInMaps = (booking) => {
        const mapsUrl = `https://www.google.com/maps/search/?api=1&query=${booking.parkingLotAddress}`;
        window.open(mapsUrl, '_blank');
    };

    const getStatusColor = (status) => {
        switch (status) {
            case 'CONFIRMED': return 'status-confirmed';
            case 'COMPLETED': return 'status-completed';
            case 'CANCELLED': return 'status-cancelled';
            default: return '';
        }
    };

    return (
        <div className="dashboard-page">
            <div className="dashboard-header">
                <div className="header-content">
                    <h1 className="dashboard-title">My Bookings</h1>
                    <p className="dashboard-subtitle">View and manage your parking reservations</p>
                </div>
            </div>

            <div className="dashboard-container">
                <div className="actions-bar">
                    <button onClick={() => navigate('/user/dashboard')} className="action-button secondary">
                        <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                        </svg>
                        Back to Dashboard
                    </button>
                </div>

                {message && (
                    <div className={`message ${message.includes('success') ? 'success' : 'error'}`}>
                        {message}
                    </div>
                )}

                {loading ? (
                    <div className="loading-state">
                        <div className="spinner"></div>
                        <p>Loading bookings...</p>
                    </div>
                ) : bookings.length === 0 ? (
                    <div className="empty-state">
                        <svg className="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                        </svg>
                        <p>No bookings found</p>
                        <button onClick={() => navigate('/user/dashboard')} className="action-button primary" style={{ marginTop: '1rem' }}>
                            Find Parking
                        </button>
                    </div>
                ) : (
                    <div className="bookings-list">
                        {bookings.map((booking) => (
                            <div key={booking.id} className="booking-card">
                                <div className="booking-header">
                                    <div>
                                        <h3 className="booking-title">{booking.parkingLotName}</h3>
                                        <p className="booking-address">{booking.parkingLotAddress}</p>
                                    </div>
                                    <span className={`booking-status ${getStatusColor(booking.status)}`}>
                                        {booking.status}
                                    </span>
                                </div>

                                <div className="booking-details">
                                    <div className="detail-row">
                                        <div className="detail-item">
                                            <svg className="detail-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
                                            </svg>
                                            <div>
                                                <span className="detail-label">Booking Date</span>
                                                <span className="detail-value">{new Date(booking.bookingDate).toLocaleString()}</span>
                                            </div>
                                        </div>
                                        <div className="detail-item">
                                            <svg className="detail-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                                            </svg>
                                            <div>
                                                <span className="detail-label">Duration</span>
                                                <span className="detail-value">{booking.hours} hours</span>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="detail-row">
                                        <div className="detail-item">
                                            <svg className="detail-icon price" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                                            </svg>
                                            <div>
                                                <span className="detail-label">Total Cost</span>
                                                <span className="detail-value price">${booking.totalPrice.toFixed(2)}</span>
                                            </div>
                                        </div>
                                        <div className="detail-item">
                                            <svg className="detail-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                                            </svg>
                                            <div>
                                                <span className="detail-label">Rate</span>
                                                <span className="detail-value">${booking.pricePerHour}/hr</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div className="booking-actions">
                                    <button onClick={() => openInMaps(booking)} className="action-button secondary">
                                        <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7" />
                                        </svg>
                                        View on Map
                                    </button>
                                    {booking.status === 'CONFIRMED' && (
                                        <button onClick={() => handleCancel(booking.id)} className="action-button danger">
                                            <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                            </svg>
                                            Cancel Booking
                                        </button>
                                    )}
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default BookingHistory;
