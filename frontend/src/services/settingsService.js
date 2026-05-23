import api from "./api";

const API = "http://localhost:8080/api/settings";

export const updateProfile = (id, profileData) => api.put(`${API}/profile/${id}`, profileData);

export const changePassword = (id, passwordData) => api.put(`${API}/password/${id}`, passwordData);
