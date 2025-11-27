import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import parkingService from '../../services/parkingService';
import bookingService from '../../services/bookingService';
import './Dashboard.css';

const UserDashboard = () => {
    const [parkingLots, setParkingLots] = useState([]);
    const [selectedLot, setSelectedLot] = useState(null);
    const [searchQuery, setSearchQuery] = useState('');
    const [bookingHours, setBookingHours] = useState(2);
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState('');
    const [showBookingDialog, setShowBookingDialog] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        loadParkingLots();
    }, []);

    const loadParkingLots = async () => {
        try {
            const data = await parkingService.getAllAvailable();
            setParkingLots(data);
        } catch (error) {
            console.error('Error loading parking lots:', error);
            setMessage('Failed to load parking lots');
        }
    };

    const handleSearch = async () => {
        if (!searchQuery) {
            loadParkingLots();
            return;
        }
        try {
            const data = await parkingService.searchByCity(searchQuery);
            setParkingLots(data);
        } catch (error) {
            setMessage('Search failed');
        }
    };

    const handleBooking = async () => {
        if (!selectedLot) return;
        setLoading(true);
        setMessage('');

        try {
            await bookingService.createBooking(selectedLot.id, bookingHours);
            setMessage('Booking successful!');
            setShowBookingDialog(false);
            setTimeout(() => navigate('/user/bookings'), 1500);
        } catch (error) {
            setMessage('Booking failed: ' + (error.response?.data || error.message));
        } finally {
            setLoading(false);
        }
    };

    const openBookingDialog = (lot) => {
        setSelectedLot(lot);
        setShowBookingDialog(true);
        setBookingHours(2);
    };

    const openInMaps = (lot) => {
        const mapsUrl = `https://www.google.com/maps/search/?api=1&query=${lot.latitude},${lot.longitude}`;
        window.open(mapsUrl, '_blank');
    };

    return (
        <div className="dashboard-page">
            <div className="dashboard-header">
                <div className="header-content">
                    <h1 className="dashboard-title">Find Parking</h1>
                    <p className="dashboard-subtitle">Discover available spots near you</p>
                </div>
            </div>

            <div className="dashboard-container">
                {/* Search Bar */}
                <div className="search-section">
                    <div className="search-bar">
                        <input
                            type="text"
                            value={searchQuery}
                            onChange={(e) => setSearchQuery(e.target.value)}
                            placeholder="Search by city or area..."
                            className="search-input"
                            onKeyPress={(e) => e.key === 'Enter' && handleSearch()}
                        />
                        <button onClick={handleSearch} className="search-button">
                            <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                            </svg>
                            Search
                        </button>
                        <button onClick={() => navigate('/user/bookings')} className="bookings-button">
                            <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                            </svg>
                            My Bookings
                        </button>
                    </div>
                </div>

                {/* Message */}
                {message && (
                    <div className={`message ${message.includes('successful') ? 'success' : 'error'}`}>
                        {message}
                    </div>
                )}

                {/* Parking Lots Grid */}
                <div className="lots-section">
                    <h2 className="section-title">Available Parking ({parkingLots.length})</h2>

                    {parkingLots.length === 0 ? (
                        <div className="empty-state">
                            <svg className="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
                            </svg>
                            <p>No parking lots available</p>
                        </div>
                    ) : (
                        <div className="lots-grid">
                            {parkingLots.map((lot) => (
                                <div key={lot.id} className="lot-card">
                                    <div className="lot-header">
                                        <div>
                                            <h3 className="lot-name">{lot.name}</h3>
                                            <p className="lot-owner">{lot.ownerName}</p>
                                        </div>
                                        <div className="lot-badge">
                                            <svg className="badge-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                                            </svg>
                                        </div>
                                    </div>

                                    <div className="lot-location">
                                        <svg className="location-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                                        </svg>
                                        <div>
                                            <p>{lot.address}</p>
                                            <p className="lot-city">{lot.city}, {lot.area}</p>
                                        </div>
                                    </div>

                                    <div className="lot-stats">
                                        <div className="stat">
                                            <svg className="stat-icon price" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                                            </svg>
                                            <span className="stat-value">${lot.pricePerHour}/hr</span>
                                        </div>
                                        <div className="stat">
                                            <svg className="stat-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                                            </svg>
                                            <span>{lot.availableSlots}/{lot.totalSlots} available</span>
                                        </div>
                                    </div>

                                    <div className="lot-actions">
                                        <button onClick={() => openBookingDialog(lot)} className="action-button primary">
                                            Book Now
                                        </button>
                                        <button onClick={() => openInMaps(lot)} className="action-button secondary">
                                            <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 20l-5.447-2.724A1 1 0 013 16.382V5.618a1 1 0 011.447-.894L9 7m0 13l6-3m-6 3V7m6 10l4.553 2.276A1 1 0 0021 18.382V7.618a1 1 0 00-.553-.894L15 4m0 13V4m0 0L9 7" />
                                            </svg>
                                        </button>
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            </div>

            {/* Booking Dialog */}
            {showBookingDialog && selectedLot && (
                <div className="dialog-overlay" onClick={() => setShowBookingDialog(false)}>
                    <div className="dialog-content" onClick={(e) => e.stopPropagation()}>
                        <div className="dialog-header">
                            <div>
                                <h2 className="dialog-title">{selectedLot.name}</h2>
                                <p className="dialog-subtitle">{selectedLot.address}</p>
                            </div>
                            <button onClick={() => setShowBookingDialog(false)} className="dialog-close">
                                <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>

                        <div className="dialog-body">
                            <div className="dialog-info">
                                <div className="info-item">
                                    <svg className="info-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                                    </svg>
                                    <span>Owner: {selectedLot.ownerName}</span>
                                </div>
                                <div className="info-item">
                                    <svg className="info-icon price" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                                    </svg>
                                    <span>${selectedLot.pricePerHour} per hour</span>
                                </div>
                                <div className="info-item success">
                                    <svg className="info-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
                                    </svg>
                                    <span>{selectedLot.availableSlots} slots available</span>
                                </div>
                            </div>

                            <div className="form-group">
                                <label className="form-label">Duration (hours)</label>
                                <input
                                    type="number"
                                    min="1"
                                    max="24"
                                    value={bookingHours}
                                    onChange={(e) => setBookingHours(parseInt(e.target.value) || 1)}
                                    className="form-input"
                                />
                                <div className="total-cost">
                                    <span>Total Cost:</span>
                                    <span className="cost-value">${(selectedLot.pricePerHour * bookingHours).toFixed(2)}</span>
                                </div>
                            </div>
                        </div>

                        <div className="dialog-actions">
                            <button onClick={() => setShowBookingDialog(false)} className="action-button secondary">
                                Cancel
                            </button>
                            <button onClick={handleBooking} disabled={loading} className="action-button primary">
                                {loading ? 'Booking...' : 'Confirm Booking'}
                            </button>
                        </div>
                    </div>
                </div>
            )}
        </div>
    );
};

export default UserDashboard;
