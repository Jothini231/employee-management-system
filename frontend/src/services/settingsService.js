import axios from "axios";

const API = "http://localhost:8080/api/settings";

export const updateProfile = (id, profileData) => axios.put(`${API}/profile/${id}`, profileData);

export const changePassword = (id, passwordData) => axios.put(`${API}/password/${id}`, passwordData);
