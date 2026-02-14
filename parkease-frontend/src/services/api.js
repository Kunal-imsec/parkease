import axios from 'axios';

// Use production backend URL (deployed on Render)
const API_BASE_URL = import.meta.env.VITE_API_URL || 'https://parkease-srqk.onrender.com/api';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Add token to requests
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;
