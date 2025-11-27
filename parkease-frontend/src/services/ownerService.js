import api from './api';

export const ownerService = {
    getParkingLots: async () => {
        const response = await api.get('/owner/parking-lots');
        return response.data;
    },

    createParkingLot: async (lotData) => {
        const response = await api.post('/owner/parking-lots', lotData);
        return response.data;
    },

    updateParkingLot: async (lotId, lotData) => {
        const response = await api.put(`/owner/parking-lots/${lotId}`, lotData);
        return response.data;
    },

    deleteParkingLot: async (lotId) => {
        const response = await api.delete(`/owner/parking-lots/${lotId}`);
        return response.data;
    },

    updateAvailability: async (lotId, availability) => {
        const response = await api.put(`/owner/parking-lots/${lotId}/availability`, { availability });
        return response.data;
    },

    getBookings: async () => {
        const response = await api.get('/owner/bookings');
        return response.data;
    },
};

export default ownerService;
