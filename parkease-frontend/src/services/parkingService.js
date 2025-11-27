import api from './api';

export const parkingService = {
    getAllAvailable: async () => {
        const response = await api.get('/parking/available');
        return response.data;
    },

    getAll: async () => {
        const response = await api.get('/parking/all');
        return response.data;
    },

    getById: async (id) => {
        const response = await api.get(`/parking/${id}`);
        return response.data;
    },

    searchByCity: async (city) => {
        const response = await api.get(`/parking/search/city/${city}`);
        return response.data;
    },

    searchByArea: async (area) => {
        const response = await api.get(`/parking/search/area/${area}`);
        return response.data;
    },
};

export default parkingService;
