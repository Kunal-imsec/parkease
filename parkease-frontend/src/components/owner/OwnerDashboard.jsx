import React, { useState, useEffect } from 'react';
import ownerService from '../../services/ownerService';
import './OwnerDashboard.css';

const OwnerDashboard = () => {
    const [parkingLots, setParkingLots] = useState([]);
    const [bookings, setBookings] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [editingLot, setEditingLot] = useState(null);
    const [formData, setFormData] = useState({
        name: '',
        address: '',
        city: '',
        area: '',
        latitude: 40.7589,
        longitude: -73.9851,
        pricePerHour: 10,
        totalSlots: 20,
        availability: 'AVAILABLE',
    });

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        try {
            const [lots, bookingsData] = await Promise.all([
                ownerService.getParkingLots(),
                ownerService.getBookings(),
            ]);
            setParkingLots(lots);
            setBookings(bookingsData);
        } catch (error) {
            console.error('Error loading data:', error);
        }
    };

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            if (editingLot) {
                await ownerService.updateParkingLot(editingLot.id, formData);
            } else {
                await ownerService.createParkingLot(formData);
            }
            setShowForm(false);
            setEditingLot(null);
            loadData();
        } catch (error) {
            alert('Operation failed: ' + (error.response?.data || error.message));
        }
    };

    const handleEdit = (lot) => {
        setEditingLot(lot);
        setFormData(lot);
        setShowForm(true);
    };

    const handleDelete = async (lotId) => {
        if (!window.confirm('Are you sure you want to delete this parking lot?')) return;
        try {
            await ownerService.deleteParkingLot(lotId);
            loadData();
        } catch (error) {
            alert('Delete failed');
        }
    };

    const toggleAvailability = async (lotId, currentAvailability) => {
        const newAvailability = currentAvailability === 'AVAILABLE' ? 'NOT_AVAILABLE' : 'AVAILABLE';
        try {
            await ownerService.updateAvailability(lotId, newAvailability);
            loadData();
        } catch (error) {
            alert('Update failed');
        }
    };

    const totalRevenue = bookings.reduce((sum, b) => sum + b.totalPrice, 0);

    return (
        <div className="dashboard-page">
            <div className="dashboard-header">
                <div className="header-content">
                    <div>
                        <h1 className="dashboard-title">Owner Dashboard</h1>
                        <p className="dashboard-subtitle">Manage your parking lots and bookings</p>
                    </div>
                    <button onClick={() => {
                        setShowForm(true);
                        setEditingLot(null);
                        setFormData({
                            name: '', address: '', city: '', area: '',
                            latitude: 40.7589, longitude: -73.9851,
                            pricePerHour: 10, totalSlots: 20, availability: 'AVAILABLE',
                        });
                    }} className="action-button primary">
                        <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
                        </svg>
                        Add Parking Lot
                    </button>
                </div>
            </div>

            <div className="dashboard-container">
                {/* Stats */}
                <div className="stats-grid">
                    <div className="stat-card">
                        <div className="stat-label">Total Parking Lots</div>
                        <div className="stat-value">{parkingLots.length}</div>
                    </div>
                    <div className="stat-card">
                        <div className="stat-label">Total Bookings</div>
                        <div className="stat-value">{bookings.length}</div>
                    </div>
                    <div className="stat-card">
                        <div className="stat-label">Total Revenue</div>
                        <div className="stat-value" style={{ color: '#5E6AD2' }}>${totalRevenue.toFixed(2)}</div>
                    </div>
                </div>

                {/* Parking Lots */}
                <h2 className="section-title">My Parking Lots</h2>
                {parkingLots.length === 0 ? (
                    <div className="empty-state">
                        <svg className="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4" />
                        </svg>
                        <p>No parking lots yet</p>
                    </div>
                ) : (
                    <div className="lots-grid">
                        {parkingLots.map((lot) => (
                            <div key={lot.id} className="lot-card">
                                <div className="lot-header">
                                    <div>
                                        <h3 className="lot-name">{lot.name}</h3>
                                        <p className="lot-owner">{lot.city}, {lot.area}</p>
                                    </div>
                                    <span className={`booking-status ${lot.availability === 'AVAILABLE' ? 'status-completed' : 'status-cancelled'}`}>
                                        {lot.availability}
                                    </span>
                                </div>

                                <div className="lot-location">
                                    <svg className="location-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                                    </svg>
                                    <p>{lot.address}</p>
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
                                        <span>{lot.availableSlots}/{lot.totalSlots} slots</span>
                                    </div>
                                </div>

                                <div className="lot-actions">
                                    <button onClick={() => handleEdit(lot)} className="action-button secondary">
                                        Edit
                                    </button>
                                    <button onClick={() => toggleAvailability(lot.id, lot.availability)} className="action-button primary">
                                        {lot.availability === 'AVAILABLE' ? 'Disable' : 'Enable'}
                                    </button>
                                    <button onClick={() => handleDelete(lot.id)} className="action-button danger">
                                        Delete
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                )}

                {/* Recent Bookings */}
                <h2 className="section-title" style={{ marginTop: '3rem' }}>Recent Bookings</h2>
                {bookings.length === 0 ? (
                    <div className="empty-state">
                        <svg className="empty-icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
                        </svg>
                        <p>No bookings yet</p>
                    </div>
                ) : (
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
                                    {bookings.slice(0, 10).map((booking) => (
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
                )}
            </div>

            {/* Form Dialog */}
            {showForm && (
                <div className="dialog-overlay" onClick={() => setShowForm(false)}>
                    <div className="dialog-content" onClick={(e) => e.stopPropagation()} style={{ maxWidth: '600px' }}>
                        <div className="dialog-header">
                            <div>
                                <h2 className="dialog-title">{editingLot ? 'Edit' : 'Add'} Parking Lot</h2>
                                <p className="dialog-subtitle">Fill in the details below</p>
                            </div>
                            <button onClick={() => setShowForm(false)} className="dialog-close">
                                <svg className="icon" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                                </svg>
                            </button>
                        </div>

                        <form onSubmit={handleSubmit} className="dialog-body" style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' }}>
                            <div className="form-group">
                                <label className="form-label">Name</label>
                                <input type="text" name="name" value={formData.name} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group">
                                <label className="form-label">City</label>
                                <input type="text" name="city" value={formData.city} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group">
                                <label className="form-label">Area</label>
                                <input type="text" name="area" value={formData.area} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group">
                                <label className="form-label">Price/Hour ($)</label>
                                <input type="number" name="pricePerHour" value={formData.pricePerHour} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group">
                                <label className="form-label">Total Slots</label>
                                <input type="number" name="totalSlots" value={formData.totalSlots} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group">
                                <label className="form-label">Availability</label>
                                <select name="availability" value={formData.availability} onChange={handleChange} className="form-input">
                                    <option value="AVAILABLE">Available</option>
                                    <option value="NOT_AVAILABLE">Not Available</option>
                                </select>
                            </div>
                            <div className="form-group">
                                <label className="form-label">Latitude</label>
                                <input type="number" step="0.0001" name="latitude" value={formData.latitude} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group">
                                <label className="form-label">Longitude</label>
                                <input type="number" step="0.0001" name="longitude" value={formData.longitude} onChange={handleChange} className="form-input" required />
                            </div>
                            <div className="form-group" style={{ gridColumn: '1 / -1' }}>
                                <label className="form-label">Address</label>
                                <textarea name="address" value={formData.address} onChange={handleChange} className="form-input" rows="2" required />
                            </div>
                            <div className="dialog-actions" style={{ gridColumn: '1 / -1', display: 'flex', gap: '1rem', padding: 0, marginTop: '1rem' }}>
                                <button type="submit" className="action-button primary" style={{ flex: 1 }}>Save</button>
                                <button type="button" onClick={() => setShowForm(false)} className="action-button secondary" style={{ flex: 1 }}>Cancel</button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default OwnerDashboard;
