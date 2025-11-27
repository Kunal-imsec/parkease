import { createSlice } from '@reduxjs/toolkit';

const initialState = {
    parkingLots: [],
    selectedLot: null,
    loading: false,
    error: null,
};

const parkingSlice = createSlice({
    name: 'parking',
    initialState,
    reducers: {
        setParkingLots: (state, action) => {
            state.parkingLots = action.payload;
            state.loading = false;
        },
        setSelectedLot: (state, action) => {
            state.selectedLot = action.payload;
        },
        setLoading: (state, action) => {
            state.loading = action.payload;
        },
        setError: (state, action) => {
            state.error = action.payload;
            state.loading = false;
        },
    },
});

export const { setParkingLots, setSelectedLot, setLoading, setError } = parkingSlice.actions;
export default parkingSlice.reducer;
