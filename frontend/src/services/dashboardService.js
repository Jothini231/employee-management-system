import api from "./api";

const API = "http://localhost:8080/api/dashboard";

export const getDashboardStats = () => api.get(`${API}/stats`);

export const getEmployeesPerDepartment = () => api.get(`${API}/employees-per-department`);

export const getLeaveStatistics = () => api.get(`${API}/leave-statistics`);

export const getMonthlySalaryExpenses = () => api.get(`${API}/monthly-salary-expenses`);

export const getRecentActivities = () => api.get(`${API}/recent-activities`);

export const getNotifications = () => api.get(`${API}/notifications`);
