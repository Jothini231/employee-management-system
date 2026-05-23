import api from './api';

const API_URL = 'http://localhost:8080/api/auth';

export const loginUser = (credentials) => api.post(`${API_URL}/login`, credentials);

export const registerUser = (userData) => api.post(`${API_URL}/register`, userData);
