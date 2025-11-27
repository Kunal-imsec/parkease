import api from './api';

export const bookingService = {
    createBooking: async (parkingLotId, hours) => {
        const response = await api.post('/bookings', { parkingLotId, hours });
        return response.data;
    },

    getUserBookings: async () => {
        const response = await api.get('/bookings/user');
        return response.data;
    },

    getAllBookings: async () => {
        const response = await api.get('/bookings/all');
        return response.data;
    },

    cancelBooking: async (bookingId) => {
        const response = await api.put(`/bookings/${bookingId}/cancel`);
        return response.data;
    },
};

export default bookingService;
