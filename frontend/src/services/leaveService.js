import api from "./api";

const API = "http://localhost:8080/api/leaves";

export const getAllLeaves = () => api.get(API);

export const applyLeave  = (data) => api.post(API,data);

export const approveLeave = (id) => api.put(`${API}/${id}/approve`);

export const rejectLeave = (id) => api.put(`${API}/${id}/reject`);

export const getLeaveByEmployee = (id) => api.get(`${API}/employee?id=${id}`);

export const getLeavesByDepartment = (id) => api.get(`${API}/department?id=${id}`);

export const getLeavesBetweenDates = (start,end) => api.get(`${API}/between-dates?startDate=${start}&endDate=${end}`);
