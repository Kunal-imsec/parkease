import api from './api';

export const authService = {
    login: async (credentials) => {
        const response = await api.post('/auth/login', credentials);
        return response.data;
    },

    registerUser: async (userData) => {
        const response = await api.post('/auth/register/user', userData);
        return response.data;
    },

    registerOwner: async (ownerData) => {
        const response = await api.post('/auth/register/owner', ownerData);
        return response.data;
    },

    logout: () => {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
    },
};

export default authService;
