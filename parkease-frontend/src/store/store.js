import { configureStore } from '@reduxjs/toolkit';
import authReducer from './slices/authSlice';
import parkingReducer from './slices/parkingSlice';
import bookingReducer from './slices/bookingSlice';

export const store = configureStore({
    reducer: {
        auth: authReducer,
        parking: parkingReducer,
        booking: bookingReducer,
    },
});

export default store;
