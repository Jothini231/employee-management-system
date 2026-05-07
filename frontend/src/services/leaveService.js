import axios from "axios";

const API = "http://localhost:8080/api/leaves";

export const getAllLeaves = () => axios.get(API);

export const applyLeave  = (data) => axios.post(API,data);

export const approveLeave = (id) => axios.put(`${API}/${id}/approve`);

export const rejectLeave = (id) => axios.put(`${API}/${id}/reject`);

export const getLeaveByEmployee = (id) => axios.get(`${API}/employee?id=${id}`);

export const getLeavesByDepartment = (id) => axios.get(`${API}/department?id=${id}`);

export const getLeavesBetweenDates = (start,end) => axios.get(`${API}/between-dates?startDate=${start}&endDate=${end}`);