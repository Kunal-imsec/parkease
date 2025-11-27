import api from './api';

export const adminService = {
    getDashboard: async () => {
        const response = await api.get('/admin/dashboard');
        return response.data;
    },

    getPendingRequests: async () => {
        const response = await api.get('/admin/owner-requests/pending');
        return response.data;
    },

    getAllRequests: async () => {
        const response = await api.get('/admin/owner-requests');
        return response.data;
    },

    approveRequest: async (requestId) => {
        const response = await api.post(`/admin/owner-requests/${requestId}/approve`);
        return response.data;
    },

    rejectRequest: async (requestId) => {
        const response = await api.post(`/admin/owner-requests/${requestId}/reject`);
        return response.data;
    },

    getAllOwners: async () => {
        const response = await api.get('/admin/owners');
        return response.data;
    },

    toggleParkingLot: async (lotId) => {
        const response = await api.put(`/admin/parking-lots/${lotId}/toggle`);
        return response.data;
    },
};

export default adminService;
